package datastructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * �ȼۼ�������,һ��ʼ��ģʽͼ��ÿ��entry����һ���ȼۼ���,Ȼ��ϲ��ȼۼ���
 * 
 * @author Administrator
 *
 */
public class EqualMapIndex {

	protected Map<String, EqualMap> equalMapIndex;
	protected double threshold;// ȡֵ������

	public EqualMapIndex() {
		super();
		equalMapIndex = new HashMap<String, EqualMap>();
		threshold = 0;
	}

	public EqualMapIndex(double threshold) {
		super();
		equalMapIndex = new HashMap<String, EqualMap>();
		this.threshold = threshold;
	}

	public EqualMapIndex(Map<String, EqualMap> equalMapIndex, double threshold) {
		super();
		this.equalMapIndex = equalMapIndex;
		this.threshold = threshold;
	}

	public EqualMap put(String key, EqualMap equalMap) {
		return equalMapIndex.put(key, equalMap);
	}

	public Set<Entry<String, EqualMap>> entrySet() {
		return equalMapIndex.entrySet();
	}

	/**
	 * �ϲ����еȼۼ��ϣ��ڼ���Value�������EqualValueMap��EqualAttributeMap���в�ͬ��ʵ�ְ汾
	 * graph������EqualValueMap��ʵ�ְ汾�������ã�
	 * ��EqualAttributeMap��ʵ�ְ汾�й�Similarity��ľ�̬�������� Ϊ��ͳһ�ӿڣ�������������ʵ�ְ汾������graph����
	 * ֻ��һ��iterator
	 */
	public void mergeEqualMap(final PatternGraph graph) {
		// �ڵ�0��Ԫ��֮ǰ
		for (int index = 0; index < equalMapIndex.size(); index++) {
			Iterator<Entry<String, EqualMap>> iterator = equalMapIndex
					.entrySet().iterator();
			// ���Ե�ǰ���Ԫ��
			for (int cursor = 0; cursor < index; cursor++) {
				iterator.next();
			}
			// ȡ�õ�1��ֵ
			Entry<String, EqualMap> thisEntry = iterator.next();
			String thisKey = thisEntry.getKey();
			// �����еȼۼ��������Ƚϣ����ƶȸߵĺϲ�
			while (iterator.hasNext()) {
				// ȡ��2��ֵ
				Entry<String, EqualMap> nextEntry = iterator.next();
				String nextKey = nextEntry.getKey();
				EqualMap thisEqualMap = thisEntry.getValue();
				EqualMap nextEqualMap = nextEntry.getValue();
				double similarity = thisEqualMap.getEqualMapSimilarity(graph,
						nextEqualMap);// ��������������ƶȷ���������ʱ���;���

				// System.out.println("similarity: " + similarity);
				// ���ƶȴ�����ֵ��Ҫ�ϲ��ȼۼ���
				if (isToMergeEqualMap(similarity)) {
					// String thisSetName = thisEntry.getKey();
					// System.out.print("key1: "+thisSetName);
					// String nextSetName = nextEntry.getKey();
					// System.out.print("key2: "+nextSetName);
					// System.out.println(" similarity: " + similarity);
					mergeEqualMap(iterator, thisKey, nextKey);
					// System.out.println("*****merge*****");
				}
			}
		}
		System.out.println("-----mergeEqualMap finish.-----");
	}

	/**
	 * �����ñ����õ�iterator
	 * 
	 * @param iterator
	 * @param key
	 * @return
	 */
	public boolean removeEntry(
			Iterator<Entry<String, EqualMap>> nextEntryIterator) {

		// while (iterator.hasNext()) {
		// Entry<String, EqualMap> entry = iterator.next();
		// String mapKey = entry.getKey();
		// //System.out.println("mapKey.equals(key): " + mapKey.equals(key));
		// if (mapKey.equals(key)) {
		// System.out.println("mapKey.equals(key) ");
		// iterator.remove();
		// break;
		// }

		// }

		// System.out.println("removeEntry finish.");
		return true;
	}

	/**
	 * �ϲ�����key��Ӧ�ĵȼۼ��� �����ñ����õ�iterator
	 * 
	 * @param keyOfEqualMap1
	 * @param keyOfEqualMap2
	 */
	public void mergeEqualMap(
			Iterator<Entry<String, EqualMap>> nextEntryIterator,
			final String keyOfEqualMap1, final String keyOfEqualMap2) {
		EqualMap equalMap1 = equalMapIndex.get(keyOfEqualMap1);
		EqualMap equalMap2 = equalMapIndex.get(keyOfEqualMap2);
		equalMap1.addAll(equalMap2);
		// ɾ���ڶ�������
		removeEntry(nextEntryIterator);
		// System.out.println("*****mergeEqualMap finish.*****");
	}

	public boolean isToMergeEqualMap(final double equalValueMapSimilarity) {
		return equalValueMapSimilarity > threshold ? true : false;
	}

	public void print() {
		Iterator<Entry<String, EqualMap>> iterator = equalMapIndex.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, EqualMap> entry = iterator.next();
			String key = entry.getKey();
			System.out.println("�ȼ۴���: " + key);
			EqualMap equalMap = entry.getValue();
			// equalMap.print();
			System.out.println(equalMap.size());

		}
		// System.out.println("EqualMapIndex print finish.");
	}

	/**
	 * ���Ҹ������Ի�ֵ�ĵȼۼ��ϣ�����Ҳ���������null
	 * 
	 * @param string
	 * @return
	 */
	public EqualMap searchEqualThings(String string) {
		EqualMap result = null;
		Iterator<Entry<String, EqualMap>> iterator = this.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, EqualMap> entry = iterator.next();
			EqualMap equalMap = entry.getValue();
			if (equalMap.contains(string)) {
				result = equalMap;
			}
		}
		return result;
	}

	/**
	 * ���Ҹ�������/ֵ�ĵȼ۱�ʾ
	 * 
	 * @param string
	 * @return
	 */
	public String searchEqualExpression(String string) {

		EqualMap resultSet = searchEqualThings(string);
		if (resultSet.size() > 0) {
			return resultSet.getEqualMap().iterator().next();

		} else {
			return null;
		}
	}

	public EqualMap get(String key) {
		return equalMapIndex.get(key);
	}
}
