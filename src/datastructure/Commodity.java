package datastructure;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import srp.db.model.InitialProduct;
import algorithm.Similarity;

/**
 * ��װ�˸���Ʒ����key-value��ɵ�hashMap, ������Ϊһ��hashMap
 * 
 * @author Administrator
 *
 */
public class Commodity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3290949893367135375L;
	private Map<String, String> features;

	public Commodity() {
		super();

	}

	public Commodity(Map<String, String> features) {
		super();
		this.features = features;
	}

	public Commodity(InitialProduct product) {
		features = new HashMap<String, String>();
		features.put("����", product.getName());
		features.put("�ͺ�", product.getBrand());
		features.put("�۸�", product.getPrices().get(0));
		Map<String,String>attributes=product.getAttributes();
		features.putAll(attributes);
		features.put("��ַ", product.getSourceurl());
	}

	public String getUrl() {
		return features.get("url");
	}

	public Map<String, String> getFeatures() {
		return features;
	}

	public Set<Entry<String, String>> entrySet() {
		return features.entrySet();
	}

	// public String getType() {
	// return features.get("�ͺ�");
	// }

	public double getPrice() {
		return Double.parseDouble(features.get("�۸�"));
	}

	public String getTitle() {
		return features.get("����");
	}

	public String put(String key, String value) {
		return features.put(key, value);
	}

	public KeyValuePairIndex getKeyValuePairIndex() {
		KeyValuePairIndex resultSet = new KeyValuePairIndex();

		Iterator<Entry<String, String>> iterator = features.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>) iterator
					.next();
			String key = entry.getKey();
			String value = entry.getValue();
			KeyValuePair keyValuePair = new KeyValuePair(key, value);
			resultSet.put(keyValuePair, this);
		}
		return resultSet;
	}

	public void print() {
		Iterator<Entry<String, String>> iterator = features.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>) iterator
					.next();
			String key = entry.getKey();
			String value = entry.getValue();
			System.out.print(key + "----");
			System.out.println(value);

		}
		System.out.println("Commodity print finish.");
	}

	public int size() {
		return features.size();
	}

	public Set<String> keySet() {
		return features.keySet();
	}

	/**
	 * ������key���ϵĲ���
	 * 
	 * @param thatCommodity
	 * @return
	 */
	public Set<String> unionKeySet(Commodity thatCommodity) {
		Set<String> keySet = features.keySet();
		Set<String> thatKeySet = thatCommodity.keySet();
		Set<String> result = new HashSet<String>(keySet);
		result.addAll(thatKeySet);
		return result;
	}

	/**
	 * ������key���ϵĽ���
	 * 
	 * @param thatCommodity
	 * @return
	 */
	public Set<String> intersectionKeySet(Commodity thatCommodity) {
		Set<String> keySet = keySet();
		Set<String> thatKeySet = thatCommodity.keySet();
		Set<String> result = new HashSet<String>(keySet);
		result.retainAll(thatKeySet);// ֻ�����ڵڶ��������д��ڵ�Ԫ��
		return result;
	}

	/**
	 * ������key���ϵĲ
	 * 
	 * @param commodity
	 * @return
	 */
	public Set<String> complementKeySet(Commodity commodity) {
		Set<String> result = unionKeySet(commodity);
		Set<String> thatKeySet = commodity.keySet();
		result.removeAll(thatKeySet);// ���ڵڶ��������д��ڵ�Ԫ��ɾȥ
		return result;
	}

	/**
	 * �������ԣ�ֵ����ȵ�entry
	 * 
	 * @param commodity
	 * @return
	 */
	public Set<String> entryEqualsKeySet(Commodity commodity) {
		Set<String> keySet = intersectionKeySet(commodity);
		Set<String> result = new HashSet<String>();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (features.get(key).equals(commodity.getFeatures().get(key))) {
				result.add(key);
			}
		}
		return result;
	}

	/**
	 * ����������ȣ�ֵ����ȵ�entry
	 * 
	 * @param commodity
	 * @return
	 */
	public Set<String> valueConflictKeySet(Commodity commodity) {
		Set<String> keySet = intersectionKeySet(commodity);
		Set<String> result = new HashSet<String>();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (!features.get(key).equals(commodity.getFeatures().get(key))) {
				result.add(key);
			}
		}
		return result;
	}

	/**
	 * �γɱ�׼��Ȩ����������
	 * 
	 * @param keyWordVector
	 *            �����Ĺؼ�������
	 * @param Wa
	 * @return
	 */
	public static Map<String, Double> toStandardizationWeightVectorMap(
			List<String> keyWordVector, Commodity w) {
		Map<String, Double> map = new HashMap<String, Double>();// w�Ĺؼ������ƶȼ���(���Լ�����������ؼ������ƶ�)
		double sum = 0;
		// ����һ���ʺ���������ֵ�����ƶȣ���ͣ���Ϊ����ʵĹؼ������ƶ�
		for (String item : keyWordVector) {
			double valueSimilarity = sumValueSimilarity(item, w);
			map.put(item, valueSimilarity);
			sum += valueSimilarity;
			// System.out.println("sum: "+sum);
		}
		// �ؼ������ƶȱ�׼��
		for (String item : keyWordVector) {
			double standardizationWeight = map.get(item) / sum;
			// System.out.println("standardizationWeight: "+standardizationWeight);
			map.put(item, standardizationWeight);
		}
		return map;
	}

	private static double sumValueSimilarity(String title, Commodity commodity) {
		double sum = 0;

		Iterator<Entry<String, String>> iterator = commodity.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			String value = entry.getValue();
			sum += Similarity.getValueSimilarity(title, value);
		}

		return sum;

	}

}
