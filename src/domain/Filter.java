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
 * ���˳�������k���������k����Ʒ����Z1 ,Z2 ,... ,Zk �г����˳�����*k �ε���Ʒ
 * 
 * @author Administrator
 *
 */
public class Filter {
	private CommodityMap map;
	private Map<String, Double> similarity;// ������������Ʒ�Ŀ��Ŷ�
	private static final double PERCENT = 0.1;
	private static final double ITEM = 0.1;
	private static final double TITLE = 0.71;
	private static final double PRICE = 0.79;

	/**
	 * ���˳�����Ʒ�����г��ֳ���size*percent�ε���Ʒ,��Set<CommodityMap>���CommodityMap
	 * 
	 * @param set
	 * @param percent
	 *            �涨����Ʒ�����г��ֵİٷֱ�
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
		// ͳ��ÿ����Ʒ�ڼ����г��ֵĴ���
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
					occurTimes.put(url, 1);//���������1��ʼ��
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
	 * ��set���ҳ�url��Ӧ����Ʒ
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
			double itemSimilarity = Similarity.getItemSimilarity(wa, commodity);// �����������ƶ�
			System.out.println("itemSimilarity: " + itemSimilarity);
			if (itemSimilarity < ITEM) {
				System.out.println("itemSimilarity < ITEM");
				iterator.remove();
				continue;// ����������Ĳ���
			}
			double titleSimilarity = Similarity.getTitleSimilarity(wa,
					commodity);
			System.out.println("titleSimilarity: " + titleSimilarity);
			if (titleSimilarity < TITLE) {
				System.out.println("titleSimilarity < ITEM");
				iterator.remove();
				continue;// ����������Ĳ���
			}
			double priceSimilarity = Similarity.getPriceSimilarity(wa,
					commodity);
			System.out.println("priceSimilarity: " + priceSimilarity);
			if (priceSimilarity < PRICE) {
				System.out.println("priceSimilarity < ITEM");
				iterator.remove();
				continue;// ����������Ĳ���
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
