package datastructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * ģʽͼ
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
	 * ����ת��ʱ���µ�map���������
	 * 
	 * @param key
	 * @param value
	 * @param weight
	 * @return
	 */
	private int put(String key, String value, int weight) {
		if (map.containsKey(key)) {
			// ����key
			EdgeMap edgeMap = map.get(key);
			edgeMap.put(value, weight);
		} else {
			// ������key
			EdgeMap edgeMap = new EdgeMap();
			edgeMap.put(value, weight);
			map.put(key, edgeMap);
		}
		return weight;
	}
	/**
	 * ȡ����edgeMap�����޸�
	 * @param key
	 * @return
	 */
	public final EdgeMap get(String key){
		return map.get(key);
	}
	public int get(String featureKey, String featureValue) {
		EdgeMap edgeMap = map.get(featureKey);
		if (edgeMap == null) {
			System.out.println("featureKey ������.");
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
	 * ��ͼת�ã�key��Ϊvalue,value��Ϊkey,���ض�����
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
	 * �ȼۼ�������,�ȼۼ����Է������ʽ���� ���벻ͬ���͵ĵȼۼ���������������ʼ�ĵȼۼ�������,
	 * ������keyΪentry��key,valueΪ�ȼۼ���,ÿ���ȼۼ���ֻ����һ��key ���ظ���������
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
	 * ����ϲ���ĵȼۼ����������󣬹����µ�ģʽͼ(�������滻��),���ض�����
	 * 
	 * @param equalMapIndex
	 * @return
	 */
	public PatternGraph toEqualPatternGraph(final EqualMapIndex equalMapIndex) {
		PatternGraph result = new PatternGraph();
		Iterator<Entry<String, EqualMap>> iterator = equalMapIndex.entrySet()
				.iterator();
		// ����ÿ���ȼۼ���
		while (iterator.hasNext()) {
			Entry<String, EqualMap> entry = iterator.next();
			String equalValueRepresent = entry.getKey();
			EqualMap equalMap = entry.getValue();
			EdgeMap edgeMap = new EdgeMap();
			Set<String> stringSet = equalMap.getEqualMap();
			Iterator<String> equalMapIterator = stringSet.iterator();
			// �����ȼۼ����е�ÿ��Ԫ��
			while (equalMapIterator.hasNext()) {
				String value = equalMapIterator.next();
				EdgeMap edgeMap2 = map.get(value);
				edgeMap = edgeMap.unionMap(edgeMap2);// ��������ȡ����������ͬ��value��Ȩ�����
			}
			result.put(equalValueRepresent, edgeMap);
		}
		this.map = result.getMap();
		return this;
	}
	/**
	 * ǰ��������ģʽͼΪ<A,<V,W>> ��Ʒ���Զ�Ӧ��ֵ������ƶ�
	 * 
	 * @param key1
	 * @param key2
	 * @return
	 */
	public double getRangeSimilarity(String attribute1, String attribute2) {

		// �ҵ�����<V,W>����
		EdgeMap valueMap1 = this.get(attribute1);
		EdgeMap valueMap2 = this.get(attribute2);

		Set<String> intersectionKey = valueMap1.intersectionKeySet(valueMap2);// ȡV�Ľ���
		Set<String> unionKey = valueMap1.unionKeySet(valueMap2);// ȡV�Ĳ���
		return intersectionKey.size() / unionKey.size();// ���ƶȼ��㹫ʽ��������Ԫ�ظ������Բ�����Ԫ�ظ���

	}

}
