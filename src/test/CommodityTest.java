package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import datastructure.Commodity;

public class CommodityTest {

	@Test
	public void testGetUrl() {
		Map<String,String>map=new HashMap<String,String>();
		map.put("ÍøÖ·", "www.baidu.com");
		Commodity commodity=new Commodity(map);
		String url=commodity.getUrl();
		System.out.println(url);
	}
	@Test
	public void testEntryEqualsKeySet(){
		
	}
	@Test
	public void testValueConflictKeySet(){
		
	}
	
}
