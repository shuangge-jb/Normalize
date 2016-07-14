package domain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import algorithm.Similarity;
import datastructure.Commodity;
import datastructure.CommodityMap;

/**
 * 过滤出在所有k个数据项的k个商品集合Z1 ,Z2 ,... ,Zk 中出现了超过φ*k 次的商品
 * 
 * @author Administrator
 *
 */
public class Filter {
	private CommodityMap map;
	private Map<String, Double> similarity;// 保留下来的商品的可信度
	private static final double PERCENT = 0.1;
	private static final double ITEM = 0.1;
	private static final double TITLE = 0.71;
	private static final double PRICE = 0.79;

	/**
	 * 过滤出在商品集合中出现超过size*percent次的商品,将Set<CommodityMap>变成CommodityMap
	 * 
	 * @param set
	 * @param percent
	 *            规定在商品集合中出现的百分比
	 * @return
	 */

	public CommodityMap filter(Set<CommodityMap> set) {
		System.out.println("filter start.");
		
		int size = set.size();
		System.out.println("commodityMap set's size:" + size);
		Map<String, Integer> occurTimes = countOccurTimes(set);
		CommodityMap commodityMap = new CommodityMap();
		Iterator<Entry<String, Integer>> occurTimesIterator = occurTimes
				.entrySet().iterator();
		while (occurTimesIterator.hasNext()) {
			Entry<String, Integer> entry = occurTimesIterator.next();
			String url = entry.getKey();
			int times = entry.getValue();
			if (times > PERCENT * size) {
				Commodity value = searchCommodityFromSet(url, set);
				if (value != null) {
					commodityMap.put(url, value);
				}
			}
		}
		System.out.println("commodityMap size: " + commodityMap.size());
		return commodityMap;

	}

	/**
	 * @return the map
	 */
	public CommodityMap getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(CommodityMap map) {
		this.map = map;
	}

	private Map<String, Integer> countOccurTimes(Set<CommodityMap> set) {
		// 统计每个商品在集合中出现的次数
		Map<String, Integer> occurTimes = new HashMap<String, Integer>();
		Iterator<CommodityMap> setIterator = set.iterator();
		while (setIterator.hasNext()) {
			CommodityMap item = setIterator.next();
			Iterator<Entry<String, Commodity>> itemIterator = item.entrySet()
					.iterator();
			while (itemIterator.hasNext()) {
				Entry<String, Commodity> entry = itemIterator.next();
				String url = entry.getKey();
				if (occurTimes.containsKey(url) == false) {
					occurTimes.put(url, 1);//新增的项，用1初始化
				} else {
					int value = occurTimes.get(url);
					occurTimes.put(url, value + 1);
				}
			}
		}
		Iterator<Entry<String, Integer>> countIterator=occurTimes.entrySet().iterator();
		while(countIterator.hasNext()){
			Entry<String, Integer> entry=countIterator.next();
			String key=entry.getKey();
			int value=entry.getValue();
			System.out.print("  key= "+key);
			System.out.println("  value= "+value);
		}
		return occurTimes;
	}

	public Filter() {
		super();
		similarity = new HashMap<String, Double>();
	}

	public Filter(CommodityMap map) {
		super();
		this.map = map;
		similarity = new HashMap<String, Double>();
	}

	public Filter(Set<CommodityMap> set) {
		similarity = new HashMap<String, Double>();
		this.map = filter(set);
	}

	/**
	 * 从set中找出url对应的商品
	 * 
	 * @param url
	 * @param set
	 * @return
	 */
	private Commodity searchCommodityFromSet(String url, Set<CommodityMap> set) {
		Iterator<CommodityMap> setIterator = set.iterator();
		while (setIterator.hasNext()) {
			CommodityMap map = setIterator.next();
			if (map.contains(url)) {
				return map.get(url);
			}
		}
		return null;
	}

	public CommodityMap removeAccordingToParameter(
			Commodity wa) {
		System.out.println("removeAccordingToParameter start.");
		
		Iterator<Entry<String, Commodity>> iterator = this.map.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, Commodity> entry = iterator.next();
			String url = entry.getKey();
			
			Commodity commodity = entry.getValue();
			System.out.println("commodity title: "+commodity.getTitle());
			double itemSimilarity = Similarity.getItemSimilarity(wa, commodity);// 算数据项相似度
			System.out.println("itemSimilarity: " + itemSimilarity);
			if (itemSimilarity < ITEM) {
				System.out.println("itemSimilarity < ITEM");
				iterator.remove();
				continue;// 不进行下面的操作
			}
			double titleSimilarity = Similarity.getTitleSimilarity(wa,
					commodity);
			System.out.println("titleSimilarity: " + titleSimilarity);
			if (titleSimilarity < TITLE) {
				System.out.println("titleSimilarity < ITEM");
				iterator.remove();
				continue;// 不进行下面的操作
			}
			double priceSimilarity = Similarity.getPriceSimilarity(wa,
					commodity);
			System.out.println("priceSimilarity: " + priceSimilarity);
			if (priceSimilarity < PRICE) {
				System.out.println("priceSimilarity < ITEM");
				iterator.remove();
				continue;// 不进行下面的操作
			}
			double commoditySimilarity = Similarity.getSimilarity(wa,
					commodity);
			System.out.println("commoditySimilarity: " + commoditySimilarity);
			similarity.put(url, commoditySimilarity);
			System.out.println("similarity.put(url, commoditySimilarity)");
		}

		return this.map;
	}

	public void print() {
//		Iterator<Entry<String, Commodity>> mapIterator = map.entrySet()
//				.iterator();
		System.out.println("filter result size: " + map.size());
//		while (mapIterator.hasNext()) {
//			Entry<String, Commodity> entry = mapIterator.next();
//			String url = entry.getKey();
//			System.out.println("url: " + url);
//			double valueSimilarity = similarity.get(url);
//			System.out.println("similarity: " + valueSimilarity);
//		}
	}

}
