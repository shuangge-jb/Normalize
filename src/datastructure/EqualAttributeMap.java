package datastructure;

import java.util.Iterator;


import algorithm.Similarity;

/**
 * EqualMap的子类，覆盖了getEqualMapSimilarity方法，
 * 使用的时候把EqualAttributeMap.class传入PatternGraph的toEqualMapIndex方法，自动创建对象
 * 
 * @author Administrator
 *
 */
public class EqualAttributeMap extends EqualMap {
	/**
	 * 计算两个等价属性集合的范围相似度. graph参数传入Similarity的静态方法. 返回最小相似度
	 */
	@Override
	public double getEqualMapSimilarity(final PatternGraph graph, EqualMap map2) {
		// TODO
		//System.out.println("EqualAttributeMap getEqualMapSimilarity");
		double similarity = 1;
		Iterator<String> iteratorOfMap1 = this.iterator();
		while (iteratorOfMap1.hasNext()) {
			String attribute1 = iteratorOfMap1.next();
			Iterator<String> iteratorOfMap2 = map2.iterator();
			while (iteratorOfMap2.hasNext()) {
				String attribute2 = iteratorOfMap2.next();
				double valueSimilarity = Similarity.getValueSimilarity(
						attribute1, attribute2);
				double rangeSimilarity = Similarity.getRangeSimilarity(graph,
						attribute1, attribute2);
				double average = (valueSimilarity + rangeSimilarity) / 2;
				similarity = Math.min(similarity, average);
			}
		}
		return similarity;
	}

}
