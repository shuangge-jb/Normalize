package algorithm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import datastructure.ClusterMap;
import datastructure.Commodity;
import datastructure.CommodityMap;
import datastructure.EqualMap;
import datastructure.EqualMapIndex;
import datastructure.InvertedIndex;

public class SelectFromInvertedIndex {
	private InvertedIndex invertedIndex;

	private EqualMapIndex equalValueMapIndex;
	private EqualMapIndex equalAttributeMapIndex;

	public SelectFromInvertedIndex() {
		super();

	}

	public SelectFromInvertedIndex(InvertedIndex invertedIndex,
			EqualMapIndex equalValueMapIndex,
			EqualMapIndex equalAttributeMapIndex) {
		super();
		this.invertedIndex = invertedIndex;
		this.equalValueMapIndex = equalValueMapIndex;
		this.equalAttributeMapIndex = equalAttributeMapIndex;
		System.out.println("SelectFromInvertedIndex build.");
	}

	/**
	 * 根据等价属性集合和等价值集合,从倒排索引中找出所有可能的商品,每个数据项对应于set中的一个元素
	 * 
	 * @param wa
	 * @return
	 */
	public Set<CommodityMap> getPossibleCommodityMap(Commodity wa) {
		Set<CommodityMap> set = new HashSet<CommodityMap>();
		
		Iterator<Entry<String, String>> iterator = wa.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String key = entry.getKey();
			String value = entry.getValue();
			// 查找某属性的所有等价属性
			EqualMap equalAttributeMap = this.equalAttributeMapIndex
					.searchEqualThings(key);
			// 确保找到的key和value有数据
			if ((equalAttributeMap == null) || (equalAttributeMap.size() == 0)) {
				System.out
						.println("(equalAttributeMap == null) || (equalAttributeMap.size() == 0)");
				continue;
			}
			// 查找某值的所有等价值
			EqualMap equalValueMap = this.equalValueMapIndex
					.searchEqualThings(value);
			if ((equalValueMap == null) || (equalValueMap.size() == 0)) {
				System.out
						.println("(equalValueMap == null) || (equalValueMap.size() == 0)");
				continue;
			}
			CommodityMap map =putCommodity(equalAttributeMap, equalValueMap);
			set.add(map);//加上符合<key,value>的所有商品组成的集合
		}
		System.out.println("getPossibleCommodityMap: set size: " + set.size());
		System.out.println("getPossibleCommodityMap finish.");
		return set;
	}

	/**
	 * 用一个属性的等价属性集合和一个值的等价值集合中所有可能的组合去查找倒排索引，把找到的所有商品放到map中
	 * 
	 * @param equalAttributeMap
	 * @param equalValueMap
	 */
	private CommodityMap putCommodity(EqualMap equalAttributeMap,
			EqualMap equalValueMap) {
		CommodityMap map = new CommodityMap();
		Iterator<String> attributeIterator = equalAttributeMap.iterator();
		while (attributeIterator.hasNext()) {
			String attribute = attributeIterator.next();
			
			Iterator<String> valueIterator = equalValueMap.iterator();
			while (valueIterator.hasNext()) {
				String value = valueIterator.next();
				
				ClusterMap clusterMap = invertedIndex.get(attribute);// 确保找到的集合有元素
				if (clusterMap != null && (clusterMap.size() > 0)) {
					CommodityMap target = invertedIndex.get(attribute).get(
							value);// 确保找到的集合有元素
					if (target != null && (target.size() > 0)) {
						map.putAll(target);// 把商品集合里面的所有商品都添加到map中
						System.out.print("  attribute: "+attribute);
						System.out.println("  value: "+value);
						target.print();
					}
				}

			}

		}
		System.out.println("putCommodity: map size: " + map.size());
		return map;
	}

}
