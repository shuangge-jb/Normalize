package test;

import static org.junit.Assert.*;

import org.junit.Test;

import datastructure.EdgeMap;

public class EdgeMapTest {

	@Test
	public void testPut() {
		EdgeMap map=new EdgeMap();
		String str="white";
		int length=1;
		map.put(str, length);
	}

	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testPrint() {
		fail("Not yet implemented");
	}

}
