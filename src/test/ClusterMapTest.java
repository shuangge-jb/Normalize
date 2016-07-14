package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import datastructure.ClusterMap;
import datastructure.Commodity;
import datastructure.CommodityMap;
import datastructure.EdgeMap;

public class ClusterMapTest {

	@Test
	public void testToEdgeMap() {
		Map<String,String>features1=new HashMap<String,String>();
		String key1OfFeatures1=new String("color");
		String value1OfFeatures1=new String("black");
		features1.put(key1OfFeatures1,value1OfFeatures1);
		String key2OfFeatures1=new String("price");
		String value2OfFeatures1=new String("100");
		
		features1.put(key2OfFeatures1,value2OfFeatures1);
		String key3OfFeatures1=new String("ÍøÖ·");
		String value3OfFeatures1=new String("www.baidu.com");
		features1.put(key3OfFeatures1,value3OfFeatures1);
		Commodity commodity1=new Commodity(features1);
		//commodity1.print();
		
		Map<String,String>features2=new HashMap<String,String>();
		String key1OfFeatures2=new String("color");
		String value1OfFeatures2=new String("white");
		features2.put(key1OfFeatures2,value1OfFeatures2);
		String key2OfFeatures2=new String("price");
		String value2OfFeatures2=new String("100");
		features2.put(key2OfFeatures2,value2OfFeatures2);
		String key3OfFeatures2=new String("ÍøÖ·");
		String value3OfFeatures2=new String("www.google.com");
		features2.put(key3OfFeatures2,value3OfFeatures2);
		Commodity commodity2=new Commodity(features2);
		//commodity2.print();
		
		CommodityMap map1=new CommodityMap();
	map1.put(commodity1.getUrl(), commodity1);
		map1.put(commodity2.getUrl(), commodity2);
		ClusterMap clusterMap=new ClusterMap();
		clusterMap.put(value2OfFeatures1, map1);
		
		CommodityMap map2=new CommodityMap();
		map2.put(commodity2.getUrl(), commodity2);
		clusterMap.put(value1OfFeatures2, map2);
		
		EdgeMap edgeMap=clusterMap.toEdgeMap();
		edgeMap.print();
		
	}
@Test
public void testPut(){
	ClusterMap map=new ClusterMap();
	String key=new String("price");
	CommodityMap commodityMap=new CommodityMap();
	map.put(key, commodityMap);
}
}
