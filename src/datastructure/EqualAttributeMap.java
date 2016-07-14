package datastructure;

import java.util.Iterator;


import algorithm.Similarity;

/**
 * EqualMap�����࣬������getEqualMapSimilarity������
 * ʹ�õ�ʱ���EqualAttributeMap.class����PatternGraph��toEqualMapIndex�������Զ���������
 * 
 * @author Administrator
 *
 */
public class EqualAttributeMap extends EqualMap {
	/**
	 * ���������ȼ����Լ��ϵķ�Χ���ƶ�. graph��������Similarity�ľ�̬����. ������С���ƶ�
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
