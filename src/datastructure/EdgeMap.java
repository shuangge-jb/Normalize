package datastructure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class EdgeMap {
	private Map<String, Integer> map;

	public EdgeMap() {
		super();
		map = new HashMap<String, Integer>();
		// System.out.println("map hashcode:"+map);
	}

	public EdgeMap(Map<String, Integer> map) {
		super();
		this.map = map;
	}

	/*
	 * public Integer put(String value, Integer length) { return map.put(value,
	 * length); }
	 */

	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

	public Integer put(String featureValue, int length) {

		return map.put(featureValue, length);
	}

	public int get(String featureValue) {
		// System.out.println("featureValue: "+featureValue);
		return map.get(featureValue).intValue();
	}

	public Set<Entry<String, Integer>> entrySet() {
		return map.entrySet();
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	public int size() {
		return map.size();
	}

	public void print() {
		Iterator<Entry<String, Integer>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Integer> entry = iterator.next();
			String featureValue = entry.getKey();
			System.out.print("  value= " + featureValue);
			int weight = entry.getValue();
			System.out.println("  weight= " + weight);
		}
		// System.out.println("EdgeMap print finish. ");
	}

	/**
	 * ������������key�Ĳ�������������´����ļ���,��ԭ�������������޹�
	 * 
	 * @param edgeMap2
	 * @return
	 */
	public Set<String> unionKeySet(EdgeMap edgeMap2) {
		Set<String> keyOfMap1 = this.keySet();// ����ԭ����edgeMap1
		Set<String> keyOfMap2 = edgeMap2.keySet();// keySet�Ľ���������޸�
		Set<String> newKeySet = new HashSet<String>(keyOfMap1);// ��edgeMap1�Ļ�����addAll
		newKeySet.addAll(keyOfMap2);// �γ�key�Ĳ���
		return newKeySet;
	}

	/**
	 * ������������key�Ľ�������������´����ļ���,��ԭ�������������޹�
	 * 
	 * @param edgeMap2
	 * @return
	 */
	// TODO
	public Set<String> intersectionKeySet(EdgeMap edgeMap2) {
		Set<String> keyOfMap1 = this.keySet();// ����ԭ����edgeMap1
		Set<String> keyOfMap2 = edgeMap2.keySet();// keySet�Ľ���������޸�
		Set<String> newKeySet = new HashSet<String>(keyOfMap1);// ��edgeMap1�Ļ�����addAll
		newKeySet.retainAll(keyOfMap2);//ȡ����
		return newKeySet;
	}

	/**
	 * �������߼��ϲ�����ͬvalue��Ȩ����ӣ������´����ı߼�
	 * 
	 * @param edgeMap2
	 * @return
	 */
	public EdgeMap unionMap(EdgeMap edgeMap2) {
		EdgeMap result = new EdgeMap();
		Set<String> keyOfMap1 = this.keySet();// ����ԭ����edgeMap1
		Set<String> keyOfMap2 = edgeMap2.keySet();// keySet�Ľ���������޸�
		Set<String> newKeySet = unionKeySet(edgeMap2);// ��edgeMap1�Ļ�����addAll

		Iterator<String> iterator = newKeySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			// System.out.println("key: "+key);
			boolean ifMap1Contains = keyOfMap1.contains(key);
			// System.out.println("contains key: "+ifMap1Contains);
			boolean ifMap2Contains = keyOfMap2.contains(key);
			// ���������϶����ֹ�
			if ((ifMap1Contains == true) && (ifMap2Contains == true)) {
				int weight1 = this.get(key);
				int weight2 = edgeMap2.get(key);
				result.put(key, weight1 + weight2);
			} else {
				// ֻ�ڼ���1���ֹ�
				if (ifMap1Contains == true) {
					int weight1 = this.get(key);
					result.put(key, weight1);
				} else {
					// ֻ�ڼ���2���ֹ�
					int weight2 = edgeMap2.get(key);
					result.put(key, weight2);
				}

			}
		}
		this.map = result.getMap();
		return this;
	}
}
