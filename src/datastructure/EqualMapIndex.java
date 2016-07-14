package datastructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 等价集合索引,一开始把模式图的每个entry当作一个等价集合,然后合并等价集合
 * 
 * @author Administrator
 *
 */
public class EqualMapIndex {

	protected Map<String, EqualMap> equalMapIndex;
	protected double threshold;// 取值见论文

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
	 * 合并所有等价集合，在集合Value项的子类EqualValueMap，EqualAttributeMap中有不同的实现版本
	 * graph参数在EqualValueMap的实现版本中无作用，
	 * 在EqualAttributeMap的实现版本中供Similarity类的静态方法调用 为了统一接口，本方法的所有实现版本都加上graph参数
	 * 只用一个iterator
	 */
	public void mergeEqualMap(final PatternGraph graph) {
		// 在第0个元素之前
		for (int index = 0; index < equalMapIndex.size(); index++) {
			Iterator<Entry<String, EqualMap>> iterator = equalMapIndex
					.entrySet().iterator();
			// 忽略掉前面的元素
			for (int cursor = 0; cursor < index; cursor++) {
				iterator.next();
			}
			// 取得第1个值
			Entry<String, EqualMap> thisEntry = iterator.next();
			String thisKey = thisEntry.getKey();
			// 将所有等价集合两两比较，相似度高的合并
			while (iterator.hasNext()) {
				// 取第2个值
				Entry<String, EqualMap> nextEntry = iterator.next();
				String nextKey = nextEntry.getKey();
				EqualMap thisEqualMap = thisEntry.getValue();
				EqualMap nextEqualMap = nextEntry.getValue();
				double similarity = thisEqualMap.getEqualMapSimilarity(graph,
						nextEqualMap);// 具体调用哪种相似度方法由运行时类型决定

				// System.out.println("similarity: " + similarity);
				// 相似度大于阈值，要合并等价集合
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
	 * 必须用遍历用的iterator
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
	 * 合并两个key对应的等价集合 必须用遍历用的iterator
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
		// 删除第二个集合
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
			System.out.println("等价代表: " + key);
			EqualMap equalMap = entry.getValue();
			// equalMap.print();
			System.out.println(equalMap.size());

		}
		// System.out.println("EqualMapIndex print finish.");
	}

	/**
	 * 查找给定属性或值的等价集合，如果找不到，返回null
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
	 * 查找给定属性/值的等价表示
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
