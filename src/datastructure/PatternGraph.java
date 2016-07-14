package datastructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 模式图
 * 
 * @author Administrator
 *
 */
public class PatternGraph {
	private Map<String, EdgeMap> map;

	public PatternGraph() {
		super();
		map = new HashMap<String, EdgeMap>();
	}

	public PatternGraph(Map<String, EdgeMap> map) {
		super();
		this.map = map;
	}

	public Map<String, EdgeMap> getMap() {
		return map;
	}

	public EdgeMap put(String key, EdgeMap edgeMap) {
		return map.put(key, edgeMap);
	}

	/**
	 * 用于转置时往新的map里面放数据
	 * 
	 * @param key
	 * @param value
	 * @param weight
	 * @return
	 */
	private int put(String key, String value, int weight) {
		if (map.containsKey(key)) {
			// 存在key
			EdgeMap edgeMap = map.get(key);
			edgeMap.put(value, weight);
		} else {
			// 不存在key
			EdgeMap edgeMap = new EdgeMap();
			edgeMap.put(value, weight);
			map.put(key, edgeMap);
		}
		return weight;
	}
	/**
	 * 取出的edgeMap不可修改
	 * @param key
	 * @return
	 */
	public final EdgeMap get(String key){
		return map.get(key);
	}
	public int get(String featureKey, String featureValue) {
		EdgeMap edgeMap = map.get(featureKey);
		if (edgeMap == null) {
			System.out.println("featureKey 不存在.");
			return -1;
		}
		return edgeMap.get(featureValue);
	}

	public boolean remove(String key, EdgeMap edgeMap) {
		Iterator<Entry<String, EdgeMap>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, EdgeMap> entry = iterator.next();
			if (entry.getKey().equals(key)) {
				iterator.remove();
				return true;
			}

		}
		return false;
	}

	public Set<Entry<String, EdgeMap>> entrySet() {
		return map.entrySet();
	}

	public int size() {
		return map.size();
	}

	public void print() {
		Iterator<Entry<String, EdgeMap>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, EdgeMap> entry = iterator.next();
			String featureKey = entry.getKey();
			System.out.println("key= " + featureKey);
			EdgeMap edgeMap = entry.getValue();
			edgeMap.print();
		}
		System.out.println("PatternGraph print finish. ");
	}

	/**
	 * 将图转置，key变为value,value变为key,返回对象本身
	 * 
	 * @param graph
	 * @return
	 */
	public PatternGraph transposition() {
		PatternGraph result = new PatternGraph();
		Iterator<Entry<String, EdgeMap>> iterator = this.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, EdgeMap> entry = iterator.next();
			String key = entry.getKey();
			EdgeMap edgeMap = entry.getValue();
			Iterator<Entry<String, Integer>> edgeMapIterator = edgeMap
					.entrySet().iterator();
			while (edgeMapIterator.hasNext()) {
				Entry<String, Integer> edgeMapEntry = edgeMapIterator.next();
				String value = edgeMapEntry.getKey();
				Integer weight = edgeMapEntry.getValue();
				result.put(value, key, weight);
			}
		}
		this.map = result.getMap();
		return this;
	}

	/**
	 * 等价集合索引,等价集合以反射的形式创建 传入不同类型的等价集合索引，构建初始的等价集合索引,
	 * 索引的key为entry的key,value为等价集合,每个等价集合只包含一个key 返回该索引本身
	 * 
	 * @param result
	 * @return
	 */
	public EqualMapIndex toEqualMapIndex(
			Class<? extends EqualMapIndex> equalMapIndex,
			Class<? extends EqualMap> equalMap) {

		EqualMapIndex result = null;
		try {
			result = (EqualMapIndex) equalMapIndex.newInstance();

			Iterator<Entry<String, EdgeMap>> iterator = this.map.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Entry<String, EdgeMap> entry = iterator.next();
				String keyOfPatternGraph = entry.getKey();

				EqualMap valueOfResult = (EqualMap) equalMap.newInstance();
				valueOfResult.add(keyOfPatternGraph);
				String keyOfResult = keyOfPatternGraph;
				result.put(keyOfResult, valueOfResult);
			}

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 传入合并后的等价集合索引对象，构建新的模式图(把属性替换掉),返回对象本身
	 * 
	 * @param equalMapIndex
	 * @return
	 */
	public PatternGraph toEqualPatternGraph(final EqualMapIndex equalMapIndex) {
		PatternGraph result = new PatternGraph();
		Iterator<Entry<String, EqualMap>> iterator = equalMapIndex.entrySet()
				.iterator();
		// 遍历每个等价集合
		while (iterator.hasNext()) {
			Entry<String, EqualMap> entry = iterator.next();
			String equalValueRepresent = entry.getKey();
			EqualMap equalMap = entry.getValue();
			EdgeMap edgeMap = new EdgeMap();
			Set<String> stringSet = equalMap.getEqualMap();
			Iterator<String> equalMapIterator = stringSet.iterator();
			// 遍历等价集合中的每个元素
			while (equalMapIterator.hasNext()) {
				String value = equalMapIterator.next();
				EdgeMap edgeMap2 = map.get(value);
				edgeMap = edgeMap.unionMap(edgeMap2);// 两个集合取并集，将相同的value的权重相加
			}
			result.put(equalValueRepresent, edgeMap);
		}
		this.map = result.getMap();
		return this;
	}
	/**
	 * 前置条件：模式图为<A,<V,W>> 商品属性对应的值域的相似度
	 * 
	 * @param key1
	 * @param key2
	 * @return
	 */
	public double getRangeSimilarity(String attribute1, String attribute2) {

		// 找到两个<V,W>集合
		EdgeMap valueMap1 = this.get(attribute1);
		EdgeMap valueMap2 = this.get(attribute2);

		Set<String> intersectionKey = valueMap1.intersectionKeySet(valueMap2);// 取V的交集
		Set<String> unionKey = valueMap1.unionKeySet(valueMap2);// 取V的并集
		return intersectionKey.size() / unionKey.size();// 相似度计算公式：交集的元素个数除以并集的元素个数

	}

}
