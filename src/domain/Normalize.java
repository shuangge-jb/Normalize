package domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import algorithm.SelectFromInvertedIndex;
import datastructure.Commodity;
import datastructure.CommodityMap;
import datastructure.EqualAttributeMap;
import datastructure.EqualAttributeMapIndex;
import datastructure.EqualMapIndex;
import datastructure.EqualValueMap;
import datastructure.EqualValueMapIndex;
import datastructure.InvertedIndex;
import datastructure.KeyValuePairIndex;
import datastructure.PatternGraph;

import srp.db.model.InitialProduct;
import srp.mongodb.serviceimpls.IniProductServiceImpl;
import srp.mongodb.serviceimpls.ProductServiceImpl;
import srp.mongodb.services.IniProductService;
import srp.mongodb.services.ProductService;

public class Normalize {
	private CommodityMap map = new CommodityMap();
	private KeyValuePairIndex keyValuePairIndex;
	private InvertedIndex invertedIndex;
	private PatternGraph graph;
	private EqualMapIndex equalValueMapIndex;
	private EqualMapIndex equalAttributeMapIndex;
	private Map<String, InitialProduct> iniProductMapping=new HashMap<String, InitialProduct>();
	private static final Boolean MARKED = true;
	private static final Boolean UNMARKED = false;
	private static final int DIRTY = 1;
	// 每个Entry存放所有同款商品
	private Map<String, Set<String>> mapping = new HashMap<String, Set<String>>();
	// 记录每个商品是否已归一化
	private Map<String, Boolean> mark = new HashMap<String, Boolean>();
	// 存放同款商品的代表
	private CommodityMap uniqueCommodity = new CommodityMap();

	public Normalize() {
		super();
	}

	/**
	 * 进行商品归一化
	 * 
	 * @param products
	 *            原始商品数据列表
	 * @return
	 */
	public boolean normalize(List<InitialProduct> products) {

		for (InitialProduct item : products) {
			String url = item.getSourceurl();
			item.setDirty(DIRTY);
			mark.put(url, UNMARKED);
			map.put(url, new Commodity(item));
			iniProductMapping.put(url, item);
		}
		 
		keyValuePairIndex = map.toKeyValuePairIndex();
		invertedIndex = keyValuePairIndex.toInvertedIndex();
		graph = invertedIndex.toPatternGraph();
//		// 等价值合并
		graph.transposition();
		equalValueMapIndex = graph.toEqualMapIndex(EqualValueMapIndex.class,
				EqualValueMap.class);
		equalValueMapIndex.mergeEqualMap(graph);
		graph.toEqualPatternGraph(equalValueMapIndex);
		System.out.println("_____________________");
//		// 等价属性合并
		graph.transposition();
		equalAttributeMapIndex = graph.toEqualMapIndex(
				EqualAttributeMapIndex.class, EqualAttributeMap.class);
		equalAttributeMapIndex.mergeEqualMap(graph);
		graph.toEqualPatternGraph(equalAttributeMapIndex);
		Iterator<Entry<String, Commodity>> mapIterator = map.entrySet()
				.iterator();
		while (mapIterator.hasNext()) {
			Entry<String, Commodity> entry = mapIterator.next();
			String key = entry.getKey();
			Commodity value = entry.getValue();
			if (mark.get(key) == MARKED) {
				continue;
			}
			Set<String> sameKind = getSameKind(value);
			mapping.put(key, sameKind);
			for (String item : sameKind) {
				mark.put(item, MARKED);
			}
			uniqueCommodity.put(key, value);
		}
		IniProductService iniProductService=new IniProductServiceImpl();
		ProductService handledProduct = new ProductServiceImpl();
		for (InitialProduct item : products) {
			String[]imgUrl=new String[1];
			imgUrl[0]=item.getSourceurl();
			handledProduct.insertOne(item.getName(), item.getBrand(), item.getInicategory(),imgUrl , item.getAttributes());
			iniProductService.mapping(item.getId(), item.getId());
		}
//		insertMapping();// 保存同款商品映射
//		insertProductService();// 保存归一化后的商品信息
		return false;
	}

	/**
	 * 找出指定一款商品的所有同款商品的id
	 * 
	 * @param commodity
	 * @return
	 */
	private Set<String> getSameKind(Commodity commodity) {
		// 归一化
		Set<String> result = new HashSet<String>();
		Iterator<Entry<String, Commodity>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Commodity> entry = iterator.next();
			String url = entry.getKey();
			Commodity commodity1 = map.get(url);
			System.out.println("test commodity build.");
			SelectFromInvertedIndex select = new SelectFromInvertedIndex(
					invertedIndex, equalValueMapIndex, equalAttributeMapIndex);
			// 集合每项为符合<K,V>的所有商品
			Set<CommodityMap> commoditys = select
					.getPossibleCommodityMap(commodity1);

			Filter commodityFilter = new Filter(commoditys);
			commodityFilter.removeAccordingToParameter(commodity1);
			// commodityFilter.print();
			result.addAll(commodityFilter.getMap().keySet());
		}
		return result;

	}

	/**
	 * 将归一化后，同一款商品的所有id映射存入数据库表中
	 */
	private void insertMapping() {
		IniProductService iniProduct = new IniProductServiceImpl();
		Iterator<Entry<String, Set<String>>> sameKindIterator = mapping
				.entrySet().iterator();
		while (sameKindIterator.hasNext()) {
			Entry<String, Set<String>> entry = sameKindIterator.next();
			String key = entry.getKey();
			Set<String> value = entry.getValue();
			for (String item : value) {
				iniProduct.mapping(item, key);// 保存所有同款商品映射
			}
		}
	}

	/**
	 * 保存归一化后的商品信息
	 */
	private void insertProductService() {
		ProductService handledProduct = new ProductServiceImpl();
		Iterator<Entry<String, Commodity>> uniqueIterator = uniqueCommodity
				.entrySet().iterator();
		while (uniqueIterator.hasNext()) {
			Entry<String, Commodity> entry = uniqueIterator.next();
			Commodity value = entry.getValue();
			String url = value.getUrl();
			InitialProduct product = iniProductMapping.get(url);
			List<String> imgurlList = product.getOnlineimgurls();
			String[] imgurls = new String[imgurlList.size()];
			imgurls = imgurlList.toArray(imgurls);
			handledProduct.insertOne(product.getName(), product.getBrand(),
					product.getInicategory(), imgurls, product.getAttributes());
		}
	}

	public static void main(String[] args) {
		new Normalize().normalize(null);
	}
	
	
	
}
