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
	// ÿ��Entry�������ͬ����Ʒ
	private Map<String, Set<String>> mapping = new HashMap<String, Set<String>>();
	// ��¼ÿ����Ʒ�Ƿ��ѹ�һ��
	private Map<String, Boolean> mark = new HashMap<String, Boolean>();
	// ���ͬ����Ʒ�Ĵ���
	private CommodityMap uniqueCommodity = new CommodityMap();

	public Normalize() {
		super();
	}

	/**
	 * ������Ʒ��һ��
	 * 
	 * @param products
	 *            ԭʼ��Ʒ�����б�
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
//		// �ȼ�ֵ�ϲ�
		graph.transposition();
		equalValueMapIndex = graph.toEqualMapIndex(EqualValueMapIndex.class,
				EqualValueMap.class);
		equalValueMapIndex.mergeEqualMap(graph);
		graph.toEqualPatternGraph(equalValueMapIndex);
		System.out.println("_____________________");
//		// �ȼ����Ժϲ�
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
//		insertMapping();// ����ͬ����Ʒӳ��
//		insertProductService();// �����һ�������Ʒ��Ϣ
		return false;
	}

	/**
	 * �ҳ�ָ��һ����Ʒ������ͬ����Ʒ��id
	 * 
	 * @param commodity
	 * @return
	 */
	private Set<String> getSameKind(Commodity commodity) {
		// ��һ��
		Set<String> result = new HashSet<String>();
		Iterator<Entry<String, Commodity>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Commodity> entry = iterator.next();
			String url = entry.getKey();
			Commodity commodity1 = map.get(url);
			System.out.println("test commodity build.");
			SelectFromInvertedIndex select = new SelectFromInvertedIndex(
					invertedIndex, equalValueMapIndex, equalAttributeMapIndex);
			// ����ÿ��Ϊ����<K,V>��������Ʒ
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
	 * ����һ����ͬһ����Ʒ������idӳ��������ݿ����
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
				iniProduct.mapping(item, key);// ��������ͬ����Ʒӳ��
			}
		}
	}

	/**
	 * �����һ�������Ʒ��Ϣ
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
