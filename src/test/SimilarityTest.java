package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


import datastructure.Commodity;
import algorithm.Similarity;

public class SimilarityTest {

	@Test
	public void testGetValueSimilarity() {
		
		double similarity = Similarity.getValueSimilarity(
				"4G",
				"4G");
		System.out.println(similarity);
	}

	@Test
	public void testGetRangeSimilarity() {
		
	}

	@Test
	public void testGetAttributeSimilarity() {
		
	}

	@Test
	public void testGetItemSimilarity() {
		Map<String, String> features1 = new HashMap<String, String>();
		
		String key2OfFeatures1 = new String("价格");
		String value2OfFeatures1 = new String("5638");
		features1.put(key2OfFeatures1, value2OfFeatures1);
		String key3OfFeatures1 = new String("网址");
		String value3OfFeatures1 = new String(
				"http://item.yhd.com/item/53945207");
		features1.put(key3OfFeatures1, value3OfFeatures1);
		String key4OfFeatures1 = new String("型号");
		String value4OfFeatures1 = new String("iPhone 6S");
		features1.put(key4OfFeatures1, value4OfFeatures1);
		String key5OfFeatures1 = new String("标题");
		String value5OfFeatures1 = new String(
				"Apple 苹果 iPhone 6s (A1700) 64G 金色 移动联通电信4G 全网通手机");
		features1.put(key5OfFeatures1, value5OfFeatures1);
		Commodity commodity1 = new Commodity(features1);

		Map<String, String> features2 = new HashMap<String, String>();
		String key1OfFeatures2 = new String("智能机");
		String value1OfFeatures2 = new String("智能机");
		features2.put(key1OfFeatures2, value1OfFeatures2);
		String key2OfFeatures2 = new String("价格");
		String value2OfFeatures2 = new String("5638");
		features2.put(key2OfFeatures2, value2OfFeatures2);
		String key3OfFeatures2 = new String("网址");
		String value3OfFeatures2 = new String(
				"http://item.yhd.com/item/53945207");
		features2.put(key3OfFeatures2, value3OfFeatures2);
		String key4OfFeatures2 = new String("型号");
		String value4OfFeatures2 = new String("iPhone 6S");
		features2.put(key4OfFeatures2, value4OfFeatures2);
		String key5OfFeatures2 = new String("标题");
		String value5OfFeatures2 = new String(
				"Apple 苹果 iPhone 6s (A1700) 64G 玫瑰金色  移动联通电信4G 全网通手机");
		features2.put(key5OfFeatures2, value5OfFeatures2);
		Commodity commodity2 = new Commodity(features2);
		double similarity = Similarity
				.getItemSimilarity(commodity1, commodity2);
		System.out.println(similarity);
	}

	@Test
	public void testGetTitleSimilarity() {
		
		Map<String, String> features1 = new HashMap<String, String>();
		String key1OfFeatures1 = new String("智能机");
		String value1OfFeatures1 = new String("智能机");
		features1.put(key1OfFeatures1, value1OfFeatures1);
		String key2OfFeatures1 = new String("价格");
		String value2OfFeatures1 = new String("5638");
		features1.put(key2OfFeatures1, value2OfFeatures1);
		String key3OfFeatures1 = new String("网址");
		String value3OfFeatures1 = new String(
				"http://item.yhd.com/item/53945207");
		features1.put(key3OfFeatures1, value3OfFeatures1);
		String key4OfFeatures1 = new String("型号");
		String value4OfFeatures1 = new String("iPhone 6S");
		features1.put(key4OfFeatures1, value4OfFeatures1);
		String key5OfFeatures1 = new String("标题");
		String value5OfFeatures1 = new String(
				"Apple 苹果 iPhone 6s (A1700) 64G 金色 移动联通电信4G 全网通手机");
		features1.put(key5OfFeatures1, value5OfFeatures1);
		Commodity commodity1 = new Commodity(features1);

		Map<String, String> features2 = new HashMap<String, String>();
		String key1OfFeatures2 = new String("智能机");
		String value1OfFeatures2 = new String("智能机");
		features2.put(key1OfFeatures2, value1OfFeatures2);
		String key2OfFeatures2 = new String("价格");
		String value2OfFeatures2 = new String("5638");
		features2.put(key2OfFeatures2, value2OfFeatures2);
		String key3OfFeatures2 = new String("网址");
		String value3OfFeatures2 = new String(
				"http://item.yhd.com/item/53945207");
		features2.put(key3OfFeatures2, value3OfFeatures2);
		String key4OfFeatures2 = new String("型号");
		String value4OfFeatures2 = new String("iPhone 6S");
		features2.put(key4OfFeatures2, value4OfFeatures2);
		String key5OfFeatures2 = new String("标题");
		String value5OfFeatures2 = new String(
				"Apple 苹果 iPhone 6s (A1700) 64G 玫瑰金色  移动联通电信4G 全网通手机");
		features2.put(key5OfFeatures2, value5OfFeatures2);
		Commodity commodity2 = new Commodity(features2);
		double similarity = Similarity
				.getTitleSimilarity(commodity1, commodity2);
		System.out.println("item similarity: "+similarity);
		
		
	}

	@Test
	public void testGetPriceSimilarity() {
		
	}

	

	

}
