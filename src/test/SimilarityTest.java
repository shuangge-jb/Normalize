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
		
		String key2OfFeatures1 = new String("�۸�");
		String value2OfFeatures1 = new String("5638");
		features1.put(key2OfFeatures1, value2OfFeatures1);
		String key3OfFeatures1 = new String("��ַ");
		String value3OfFeatures1 = new String(
				"http://item.yhd.com/item/53945207");
		features1.put(key3OfFeatures1, value3OfFeatures1);
		String key4OfFeatures1 = new String("�ͺ�");
		String value4OfFeatures1 = new String("iPhone 6S");
		features1.put(key4OfFeatures1, value4OfFeatures1);
		String key5OfFeatures1 = new String("����");
		String value5OfFeatures1 = new String(
				"Apple ƻ�� iPhone 6s (A1700) 64G ��ɫ �ƶ���ͨ����4G ȫ��ͨ�ֻ�");
		features1.put(key5OfFeatures1, value5OfFeatures1);
		Commodity commodity1 = new Commodity(features1);

		Map<String, String> features2 = new HashMap<String, String>();
		String key1OfFeatures2 = new String("���ܻ�");
		String value1OfFeatures2 = new String("���ܻ�");
		features2.put(key1OfFeatures2, value1OfFeatures2);
		String key2OfFeatures2 = new String("�۸�");
		String value2OfFeatures2 = new String("5638");
		features2.put(key2OfFeatures2, value2OfFeatures2);
		String key3OfFeatures2 = new String("��ַ");
		String value3OfFeatures2 = new String(
				"http://item.yhd.com/item/53945207");
		features2.put(key3OfFeatures2, value3OfFeatures2);
		String key4OfFeatures2 = new String("�ͺ�");
		String value4OfFeatures2 = new String("iPhone 6S");
		features2.put(key4OfFeatures2, value4OfFeatures2);
		String key5OfFeatures2 = new String("����");
		String value5OfFeatures2 = new String(
				"Apple ƻ�� iPhone 6s (A1700) 64G õ���ɫ  �ƶ���ͨ����4G ȫ��ͨ�ֻ�");
		features2.put(key5OfFeatures2, value5OfFeatures2);
		Commodity commodity2 = new Commodity(features2);
		double similarity = Similarity
				.getItemSimilarity(commodity1, commodity2);
		System.out.println(similarity);
	}

	@Test
	public void testGetTitleSimilarity() {
		
		Map<String, String> features1 = new HashMap<String, String>();
		String key1OfFeatures1 = new String("���ܻ�");
		String value1OfFeatures1 = new String("���ܻ�");
		features1.put(key1OfFeatures1, value1OfFeatures1);
		String key2OfFeatures1 = new String("�۸�");
		String value2OfFeatures1 = new String("5638");
		features1.put(key2OfFeatures1, value2OfFeatures1);
		String key3OfFeatures1 = new String("��ַ");
		String value3OfFeatures1 = new String(
				"http://item.yhd.com/item/53945207");
		features1.put(key3OfFeatures1, value3OfFeatures1);
		String key4OfFeatures1 = new String("�ͺ�");
		String value4OfFeatures1 = new String("iPhone 6S");
		features1.put(key4OfFeatures1, value4OfFeatures1);
		String key5OfFeatures1 = new String("����");
		String value5OfFeatures1 = new String(
				"Apple ƻ�� iPhone 6s (A1700) 64G ��ɫ �ƶ���ͨ����4G ȫ��ͨ�ֻ�");
		features1.put(key5OfFeatures1, value5OfFeatures1);
		Commodity commodity1 = new Commodity(features1);

		Map<String, String> features2 = new HashMap<String, String>();
		String key1OfFeatures2 = new String("���ܻ�");
		String value1OfFeatures2 = new String("���ܻ�");
		features2.put(key1OfFeatures2, value1OfFeatures2);
		String key2OfFeatures2 = new String("�۸�");
		String value2OfFeatures2 = new String("5638");
		features2.put(key2OfFeatures2, value2OfFeatures2);
		String key3OfFeatures2 = new String("��ַ");
		String value3OfFeatures2 = new String(
				"http://item.yhd.com/item/53945207");
		features2.put(key3OfFeatures2, value3OfFeatures2);
		String key4OfFeatures2 = new String("�ͺ�");
		String value4OfFeatures2 = new String("iPhone 6S");
		features2.put(key4OfFeatures2, value4OfFeatures2);
		String key5OfFeatures2 = new String("����");
		String value5OfFeatures2 = new String(
				"Apple ƻ�� iPhone 6s (A1700) 64G õ���ɫ  �ƶ���ͨ����4G ȫ��ͨ�ֻ�");
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
