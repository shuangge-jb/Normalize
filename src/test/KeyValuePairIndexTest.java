package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import datastructure.Commodity;
import datastructure.CommodityMap;
import datastructure.KeyValuePairIndex;

public class KeyValuePairIndexTest {

	@Test
	public void testKeyValuePairIndex() {
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
		
		CommodityMap map=new CommodityMap();
		map.put(commodity1.getUrl(), commodity1);
		map.put(commodity2.getUrl(), commodity2);
		//map.print();
		KeyValuePairIndex index=map.toKeyValuePairIndex();
		index.print();
		
		
	}

}
