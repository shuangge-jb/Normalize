package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


import datastructure.Commodity;
import datastructure.CommodityMap;
import datastructure.EqualAttributeMap;
import datastructure.EqualAttributeMapIndex;
import datastructure.EqualMapIndex;
import datastructure.EqualValueMap;
import datastructure.EqualValueMapIndex;
import datastructure.InvertedIndex;
import datastructure.KeyValuePairIndex;
import datastructure.PatternGraph;

public class PatternGraphTest {

	@Test
	public void testTransposition() {
		 Map<String, String> features1 = new HashMap<String, String>();
		 String key1OfFeatures1 = new String("color");
		 String value1OfFeatures1 = new String("black");
		 features1.put(key1OfFeatures1, value1OfFeatures1);
		 String key2OfFeatures1 = new String("price");
		 String value2OfFeatures1 = new String("100");
		
		 features1.put(key2OfFeatures1, value2OfFeatures1);
		 String key3OfFeatures1 = new String("网址");
		 String value3OfFeatures1 = new String("www.baidu.com");
		 features1.put(key3OfFeatures1, value3OfFeatures1);
		 Commodity commodity1 = new Commodity(features1);
		 // commodity1.print();
		
		 Map<String, String> features2 = new HashMap<String, String>();
		 String key1OfFeatures2 = new String("color");
		 String value1OfFeatures2 = new String("white");
		 features2.put(key1OfFeatures2, value1OfFeatures2);
		 String key2OfFeatures2 = new String("price");
		 String value2OfFeatures2 = new String("100");
		 features2.put(key2OfFeatures2, value2OfFeatures2);
		 String key3OfFeatures2 = new String("网址");
		 String value3OfFeatures2 = new String("www.google.com");
		 features2.put(key3OfFeatures2, value3OfFeatures2);
		 Commodity commodity2 = new Commodity(features2);
		 // commodity2.print();
		
		 CommodityMap map = new CommodityMap();
		 map.put(commodity1.getUrl(), commodity1);
		 map.put(commodity2.getUrl(), commodity2);
		 // map.print();
		 KeyValuePairIndex keyValuePairIndex = map.toKeyValuePairIndex();
		 // index.print();
		 InvertedIndex invertedIndex = keyValuePairIndex.toInvertedIndex();
		 // System.out.println("invertedIndex size:"+invertedIndex.size());
		 PatternGraph graph = invertedIndex.toPatternGraph();
		 graph.transposition();
		 graph.print();
		 System.out.println("*****************************");
		 graph.transposition();
		 graph.print();

//		FileToJSON fileToJSON = new FileToJSON("D:/myeclipse/Normalize/input/");
//		List<String> json = fileToJSON.toJSON();
//		for (String str : json) {
//
//			JSONToMap jsonToMap = new JSONToMap(str);
//			CommodityMap map = jsonToMap.toMap(str);
//			KeyValuePairIndex keyValuePairIndex = map.toKeyValuePairIndex();
//			InvertedIndex invertedIndex = keyValuePairIndex.toInvertedIndex();
//
//			PatternGraph graph = invertedIndex.toPatternGraph();
//			// 等价值合并
//			graph.transposition();
//			EqualMapIndex equalValueMapIndex = graph.toEqualMapIndex(
//					EqualValueMapIndex.class, EqualValueMap.class);
//			equalValueMapIndex.mergeEqualMap(graph);
//			// equalValueMapIndex.mergeEqualMap(value4OfFeatures1,value4OfFeatures2);
//			graph.toEqualPatternGraph(equalValueMapIndex);
//			// graph.print();
//			// 等价属性合并
//			graph.transposition();
//			EqualMapIndex equalAttributeMapIndex = graph.toEqualMapIndex(
//					EqualAttributeMapIndex.class, EqualAttributeMap.class);
//			equalAttributeMapIndex.mergeEqualMap(graph);
//			// equalAttributeMapIndex.mergeEqualMap(key1OfFeatures1,key1OfFeatures2);
//			graph.toEqualPatternGraph(equalAttributeMapIndex);
//			graph.print();
//			
//			System.out.println("******************");
//		}

	}

	@Test
	public void testToEqualPatternGraph() {
		Map<String, String> features1 = new HashMap<String, String>();
		String key1OfFeatures1 = new String("color");
		String value1OfFeatures1 = new String("black");
		features1.put(key1OfFeatures1, value1OfFeatures1);
		String key2OfFeatures1 = new String("price");
		String value2OfFeatures1 = new String("100");
		features1.put(key2OfFeatures1, value2OfFeatures1);
		String key3OfFeatures1 = new String("网址");
		String value3OfFeatures1 = new String("www.baidu.com");
		features1.put(key3OfFeatures1, value3OfFeatures1);
		String key4OfFeatures1 = new String("型号");
		String value4OfFeatures1 = new String("iphone6PLUS");
		features1.put(key4OfFeatures1, value4OfFeatures1);
		Commodity commodity1 = new Commodity(features1);
		// commodity1.print();

		Map<String, String> features2 = new HashMap<String, String>();
		String key1OfFeatures2 = new String("颜色");
		String value1OfFeatures2 = new String("white");
		features2.put(key1OfFeatures2, value1OfFeatures2);
		String key2OfFeatures2 = new String("price");
		String value2OfFeatures2 = new String("100");
		features2.put(key2OfFeatures2, value2OfFeatures2);
		String key3OfFeatures2 = new String("网址");
		String value3OfFeatures2 = new String("www.google.com");
		features2.put(key3OfFeatures2, value3OfFeatures2);
		String key4OfFeatures2 = new String("型号");
		String value4OfFeatures2 = new String("苹果6PLUS");
		features2.put(key4OfFeatures2, value4OfFeatures2);
		Commodity commodity2 = new Commodity(features2);
		// commodity2.print();

		CommodityMap map = new CommodityMap();
		map.put(commodity1.getUrl(), commodity1);
		map.put(commodity2.getUrl(), commodity2);
		// map.print();
		KeyValuePairIndex keyValuePairIndex = map.toKeyValuePairIndex();
		InvertedIndex invertedIndex = keyValuePairIndex.toInvertedIndex();
		PatternGraph graph = invertedIndex.toPatternGraph();
		// 等价值合并
		graph.transposition();
		EqualMapIndex equalValueMapIndex = graph.toEqualMapIndex(
				EqualValueMapIndex.class, EqualValueMap.class);
		
		equalValueMapIndex.mergeEqualMap(graph);
		// equalValueMapIndex.mergeEqualMap(value4OfFeatures1,value4OfFeatures2);
		graph.toEqualPatternGraph(equalValueMapIndex);
		// graph.print();
		// 等价属性合并
		graph.transposition();
		EqualMapIndex equalAttributeMapIndex = graph.toEqualMapIndex(
				EqualAttributeMapIndex.class, EqualAttributeMap.class);
		equalAttributeMapIndex.mergeEqualMap(graph);
		// equalAttributeMapIndex.mergeEqualMap(key1OfFeatures1,key1OfFeatures2);
		graph.toEqualPatternGraph(equalAttributeMapIndex);
		graph.print();
		
	}

}
