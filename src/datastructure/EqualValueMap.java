package datastructure;

import java.util.List;


import algorithm.Similarity;

/**
 * EqualMap的子类，覆盖了getEqualMapSimilarity方法，
 * 使用的时候把EqualValueMap.class传入PatternGraph的toEqualMapIndex方法，自动创建对象
 * 
 * @author Administrator
 *
 */
public class EqualValueMap extends EqualMap {
	/**
	 * graph参数传入Similarity的静态方法中. 返回最小相似度
	 */
	@Override
	public double getEqualMapSimilarity(final PatternGraph graph, EqualMap map2) {
		
		//System.out.println(this.toString()+"EqualValueMap getEqualMapSimilarity");
		double similarity = 1;

		List<String> arrayOfMap1 = toList();
		List<String> arrayOfMap2 = map2.toList();
		//System.out.println("arrayOfMap1.size:  " + arrayOfMap1.size());
		//System.out.println("arrayOfMap2.size:  " + arrayOfMap2.size());

		for (int i = 0; i < arrayOfMap1.size(); i++) {
			String key1 = arrayOfMap1.get(i);
			for (int j = i; j < arrayOfMap2.size(); j++) {
				String key2 = arrayOfMap2.get(j);
				double temp = Similarity.getValueSimilarity(key1, key2);
				similarity = Math.min(similarity, temp);

			}
		}

		return similarity;

	}

}
