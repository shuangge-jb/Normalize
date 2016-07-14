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
	 * 返回两个集合key的并集，结果集是新创建的集合,和原来的两个集合无关
	 * 
	 * @param edgeMap2
	 * @return
	 */
	public Set<String> unionKeySet(EdgeMap edgeMap2) {
		Set<String> keyOfMap1 = this.keySet();// 保留原来的edgeMap1
		Set<String> keyOfMap2 = edgeMap2.keySet();// keySet的结果不允许修改
		Set<String> newKeySet = new HashSet<String>(keyOfMap1);// 在edgeMap1的基础上addAll
		newKeySet.addAll(keyOfMap2);// 形成key的并集
		return newKeySet;
	}

	/**
	 * 返回两个集合key的交集，结果集是新创建的集合,和原来的两个集合无关
	 * 
	 * @param edgeMap2
	 * @return
	 */
	// TODO
	public Set<String> intersectionKeySet(EdgeMap edgeMap2) {
		Set<String> keyOfMap1 = this.keySet();// 保留原来的edgeMap1
		Set<String> keyOfMap2 = edgeMap2.keySet();// keySet的结果不允许修改
		Set<String> newKeySet = new HashSet<String>(keyOfMap1);// 在edgeMap1的基础上addAll
		newKeySet.retainAll(keyOfMap2);//取交集
		return newKeySet;
	}

	/**
	 * 把两个边集合并，相同value的权重相加，返回新创建的边集
	 * 
	 * @param edgeMap2
	 * @return
	 */
	public EdgeMap unionMap(EdgeMap edgeMap2) {
		EdgeMap result = new EdgeMap();
		Set<String> keyOfMap1 = this.keySet();// 保留原来的edgeMap1
		Set<String> keyOfMap2 = edgeMap2.keySet();// keySet的结果不允许修改
		Set<String> newKeySet = unionKeySet(edgeMap2);// 在edgeMap1的基础上addAll

		Iterator<String> iterator = newKeySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			// System.out.println("key: "+key);
			boolean ifMap1Contains = keyOfMap1.contains(key);
			// System.out.println("contains key: "+ifMap1Contains);
			boolean ifMap2Contains = keyOfMap2.contains(key);
			// 在两个集合都出现过
			if ((ifMap1Contains == true) && (ifMap2Contains == true)) {
				int weight1 = this.get(key);
				int weight2 = edgeMap2.get(key);
				result.put(key, weight1 + weight2);
			} else {
				// 只在集合1出现过
				if (ifMap1Contains == true) {
					int weight1 = this.get(key);
					result.put(key, weight1);
				} else {
					// 只在集合2出现过
					int weight2 = edgeMap2.get(key);
					result.put(key, weight2);
				}

			}
		}
		this.map = result.getMap();
		return this;
	}
}
