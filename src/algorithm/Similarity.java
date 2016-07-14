package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import tokenizer.Tokenizer;
import datastructure.Commodity;
import datastructure.EdgeMap;
import datastructure.PatternGraph;

public class Similarity {
	public static double YUZHI = 0.2 ;
	/**
	 * 商品值的相似度(句子的相似度)
	 * 
	 * @param key1
	 * @param key2
	 * @return
	 */
	public static double getValueSimilarity(
			String key1, String key2) {
//		long startTime = System.currentTimeMillis();
//		clientSocket.send(key1, key2);
//		double result = clientSocket.response();
//		long endTime = System.currentTimeMillis();
//		// System.out.println("use time: "+(endTime-startTime)+" ms.");
//		return result;
		int size = 0 , size2 = 0 ;
		List<String>list1=Tokenizer.tokenize(key1);
		List<String>list2=Tokenizer.tokenize(key2);
	    if ( list1 != null && ( size = list1.size() ) > 0 && list2 != null && ( size2 = list2.size() ) > 0 ) {
	        
	    	Map<String, double[]> T = new HashMap<String, double[]>();
	        
	        //T1和T2的并集T
	    	String index = null ;
	        for ( int i = 0 ; i < size ; i++ ) {
	        	index = list1.get(i) ;
	            if( index != null){
	            	double[] c = T.get(index);
	                c = new double[2];
	                c[0] = 1;	//T1的语义分数Ci
	                c[1] = YUZHI;//T2的语义分数Ci
	                T.put( index, c );
	            }
	        }
	 
	        for ( int i = 0; i < size2 ; i++ ) {
	        	index = list2.get(i) ;
	        	if( index != null ){
	        		double[] c = T.get( index );
	        		if( c != null && c.length == 2 ){
	        			c[1] = 1; //T2中也存在，T2的语义分数=1
	                }else {
	                    c = new double[2];
	                    c[0] = YUZHI; //T1的语义分数Ci
	                    c[1] = 1; //T2的语义分数Ci
	                    T.put( index , c );
	                }
	            }
	        }
	            
	        //开始计算，百分比
	        Iterator<String> it = T.keySet().iterator();
	        double s1 = 0 , s2 = 0, Ssum = 0;  //S1、S2
	        while( it.hasNext() ){
	        	double[] c = T.get( it.next() );
	        	Ssum += c[0]*c[1];
	        	s1 += c[0]*c[0];
	        	s2 += c[1]*c[1];
	        }
	        //百分比
	        return Ssum / Math.sqrt( s1*s2 );
	    } else {
	        try {
				throw new Exception("传入参数有问题！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		return 0;
	}

	/**
	 * 商品属性对应的值域的相似度. 前置条件：模式图为<A,<V,W>>
	 * 
	 * @param key1
	 * @param key2
	 * @return
	 */
	public static double getRangeSimilarity(PatternGraph graph,
			String attribute1, String attribute2) {

		// 找到两个<V,W>集合
		EdgeMap valueMap1 = graph.get(attribute1);
		EdgeMap valueMap2 = graph.get(attribute2);

		Set<String> intersectionKey = valueMap1.intersectionKeySet(valueMap2);// 取V的交集
		Set<String> unionKey = valueMap1.unionKeySet(valueMap2);// 取V的并集
		return intersectionKey.size() / unionKey.size();// 相似度计算公式：交集的元素个数除以并集的元素个数

	}

	/**
	 * 商品属性的相似度. 前置条件：模式图为<A,<V,W>>,商品值域相似度已算好
	 * 
	 * @param attribute1
	 * @param attribute2
	 * @return
	 */
	public static double getAttributeSimilarity(
			PatternGraph graph, String attribute1, String attribute2) {

		double value = getValueSimilarity(attribute1, attribute2);
		double range = getRangeSimilarity(graph, attribute1, attribute2);
		return (value + range) / 2;
	}

	/**
	 * 数据项相似度，不计权重 ω
	 * 
	 * @param commodity1
	 * @param commodity2
	 * @return
	 */
	public static double getItemSimilarity(Commodity Wa, Commodity Wi) {
		int numberOfType1 = Wa.entryEqualsKeySet(Wi).size();// key,value都相同的entry个数
		System.out.println("entryEqualsKeySet size: " + numberOfType1);
		int numberOfType2 = Wi.complementKeySet(Wa).size();// Wi有,Wa无的key个数
		System.out.println("Wa complementKeySet size: " + numberOfType2);
		int numberOfType3 = Wa.complementKeySet(Wi).size();// Wa有,Wi无的key个数
		System.out.println("Wi complementKeySet size: " + numberOfType3);
		int numberOfType4 = Wa.valueConflictKeySet(Wi).size();// key相同,value不同的entry个数
		System.out.println("valueConflictKeySet size: " + numberOfType4);
		return (double) (numberOfType1*1.2 - numberOfType4)
				/ (numberOfType1 + numberOfType2*0.5 + numberOfType3 + numberOfType4);// 数据项相似度公式
	}

	/**
	 * 标题的Tanimoto 相似度
	 * 
	 * @param Wa
	 *            指定商品
	 * 
	 * @param Wb
	 * 
	 * @return
	 */
	public static double getTitleSimilarity(
			Commodity Wa, Commodity Wb) {
		String titleA = Wa.getTitle();
		String titleB = Wb.getTitle();
		List<String> k1 = Tokenizer.tokenize(titleA);// Wa的关键词向量
		List<String> k2 = Tokenizer.tokenize(titleB);// Wb的关键词向量
		Map<String, Double> map1 = Commodity.toStandardizationWeightVectorMap(
				k1, Wa);
		
		Map<String, Double> map2 = Commodity.toStandardizationWeightVectorMap(
				 k2, Wb);
		List<String> weightVectorTemplate = Similarity.addAll(k1, k2);// 统一两个权重向量的顺序
		
		List<Double> weightVectorA = getNewWeightVector(weightVectorTemplate,
				map1);
		List<Double> weightVectorB = getNewWeightVector(weightVectorTemplate,
				map2);
		
		Vector wka = new Vector(weightVectorA);
		Vector wkb = new Vector(weightVectorB);
//		System.out.println(wka.moldSquare());
//		System.out.println(wkb.moldSquare());
//		System.out.println(wka.transvection(wkb));
//		System.out
//				.println("---title similarity: "
//						+ wka.transvection(wkb)
//						/ (wka.moldSquare() + wkb.moldSquare() - wka
//								.transvection(wkb)));
		return wka.transvection(wkb)
				/ (wka.moldSquare() + wkb.moldSquare() - wka.transvection(wkb));// 标题相似度公式

	}

	private static List<String> addAll(List<String> k1, List<String> k2) {
		// 用Set去重复，再转化为List
		List<String> k1List = new ArrayList<String>(new HashSet<String>(k1));
		List<String> k2List = new ArrayList<String>(new HashSet<String>(k2));
		k1List.addAll(k2List);
		return k1List;
	}

	private static List<Double> getNewWeightVector(
			List<String> weightVectorTemplate, Map<String, Double> map) {
		List<Double> weightVector = new ArrayList<Double>();// 权重向量
		for (String item : weightVectorTemplate) {
			double weight = (map.get(item) == null) ? 0 : map.get(item);// 如果有不在map1中的属性，则权重为0
			weightVector.add(weight);
			//System.out.println("new weightVector add weight: " + weight);
		}
		return weightVector;
	}

	/**
	 * 价格相似度
	 * 
	 * @param Wa
	 * @param Wb
	 * @return
	 */
	public static double getPriceSimilarity(Commodity Wa, Commodity Wb) {
		double price1 = Wa.getPrice();
		double price2 = Wb.getPrice();
		return Math.sqrt(1 - (Math.abs(price1 - price2) / Math.max(price1,
				price2)));

	}

	/**
	 * 可信度
	 * 
	 * @param itemSim
	 * @param titleSim
	 * @param priceSim
	 * @return
	 */
	public static double getSimilarity(double itemSim, double titleSim,
			double priceSim) {
		return (itemSim + titleSim + priceSim) / 3;

	}

	/**
	 * 接口，供外部调用
	 * 
	 * @param socket
	 * @param Wa
	 * @param Wi
	 * @return
	 */
	public static double getSimilarity( Commodity Wa,
			Commodity Wi) {
		double itemSim = getItemSimilarity(Wa, Wi);
		double titleSim = getTitleSimilarity( Wa, Wi);
		double priceSim = getPriceSimilarity(Wa, Wi);
		return (itemSim + titleSim + priceSim) / 3;

	}

}
