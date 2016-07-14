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
	 * ���ݵȼ����Լ��Ϻ͵ȼ�ֵ����,�ӵ����������ҳ����п��ܵ���Ʒ,ÿ���������Ӧ��set�е�һ��Ԫ��
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
			// ����ĳ���Ե����еȼ�����
			EqualMap equalAttributeMap = this.equalAttributeMapIndex
					.searchEqualThings(key);
			// ȷ���ҵ���key��value������
			if ((equalAttributeMap == null) || (equalAttributeMap.size() == 0)) {
				System.out
						.println("(equalAttributeMap == null) || (equalAttributeMap.size() == 0)");
				continue;
			}
			// ����ĳֵ�����еȼ�ֵ
			EqualMap equalValueMap = this.equalValueMapIndex
					.searchEqualThings(value);
			if ((equalValueMap == null) || (equalValueMap.size() == 0)) {
				System.out
						.println("(equalValueMap == null) || (equalValueMap.size() == 0)");
				continue;
			}
			CommodityMap map =putCommodity(equalAttributeMap, equalValueMap);
			set.add(map);//���Ϸ���<key,value>��������Ʒ��ɵļ���
		}
		System.out.println("getPossibleCommodityMap: set size: " + set.size());
		System.out.println("getPossibleCommodityMap finish.");
		return set;
	}

	/**
	 * ��һ�����Եĵȼ����Լ��Ϻ�һ��ֵ�ĵȼ�ֵ���������п��ܵ����ȥ���ҵ������������ҵ���������Ʒ�ŵ�map��
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
				
				ClusterMap clusterMap = invertedIndex.get(attribute);// ȷ���ҵ��ļ�����Ԫ��
				if (clusterMap != null && (clusterMap.size() > 0)) {
					CommodityMap target = invertedIndex.get(attribute).get(
							value);// ȷ���ҵ��ļ�����Ԫ��
					if (target != null && (target.size() > 0)) {
						map.putAll(target);// ����Ʒ���������������Ʒ����ӵ�map��
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
