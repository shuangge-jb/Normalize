package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import algorithm.BuildInvertedIndex;
import datastructure.Commodity;
import datastructure.CommodityMap;
import datastructure.InvertedIndex;

public class BuildInvertedIndexTest {

	@Test
	public void testBuildIndex() {

		Map<String, Commodity>innerMap=new HashMap<String, Commodity>();
		Map<String, String> feature1=new HashMap<String, String>();
		feature1.put("color", "white");
		feature1.put("Æ·ÅÆ", "iphone6");
		String index1=new String("1");
		innerMap.put(index1, new Commodity(feature1));
		
		Map<String, String> feature2=new HashMap<String, String>();
		feature2.put("color", "white");
		feature2.put("Æ·ÅÆ", "iphone5S");
		String index2=new String("2");
		innerMap.put(index2, new Commodity(feature2));
		System.out.println(innerMap.containsKey(index1));
		CommodityMap commodityMap=new CommodityMap(innerMap);
		InvertedIndex index = BuildInvertedIndex.buildIndex(commodityMap);
		index.print();

	}

}
