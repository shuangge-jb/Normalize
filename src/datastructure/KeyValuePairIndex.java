package datastructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import datastructure.KeyValuePair;

/**
 * 键值对索引 <<A,V>,W>
 * 
 * @author Administrator
 *
 */
public class KeyValuePairIndex {
	private Map<KeyValuePair, Commodity> keyValueIndex;

	public KeyValuePairIndex() {
		super();
		keyValueIndex = new HashMap<KeyValuePair, Commodity>();
	}

	public KeyValuePairIndex(Map<KeyValuePair, Commodity> keyValueIndex) {
		super();
		this.keyValueIndex = keyValueIndex;
	}

	public Commodity put(KeyValuePair key, Commodity value) {
		return keyValueIndex.put(key, value);
	}

	public void putAll(KeyValuePairIndex map) {
		this.putAll(map.getKeyValueIndex());
	}

	public void putAll(Map<KeyValuePair, Commodity> map) {
		keyValueIndex.putAll(map);
	}

	public Commodity get(String key) {
		return keyValueIndex.get(key);
	}

	public Set<Map.Entry<KeyValuePair, Commodity>> entrySet() {
		return keyValueIndex.entrySet();
	}

	private Map<KeyValuePair, Commodity> getKeyValueIndex() {
		return keyValueIndex;
	}

	public void setKeyValueIndex(Map<KeyValuePair, Commodity> keyValueIndex) {
		this.keyValueIndex = keyValueIndex;
	}

	public void print() {
		Iterator<Entry<KeyValuePair, Commodity>> iterator = keyValueIndex
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<KeyValuePair, Commodity> entry = (Entry<KeyValuePair, Commodity>) iterator
					.next();
			KeyValuePair key = entry.getKey();
			System.out.print("KeyValuePair hashcode: " + key.hashCode() + "\n");
			String keyStr = key.getKey();
			String valueStr = key.getValue();
			Commodity value = entry.getValue();

			System.out.print("key=" + keyStr + "\t");
			System.out.print("value=" + valueStr + "\t");
			System.out.print("commodity=" + value.getUrl() + "\n");

		}
		System.out.println("KeyValuePairIndex print finish.");
	}

	/**
	 * 把键值对索引转化为倒排索引
	 * 
	 * @param index
	 * @return
	 */
	public InvertedIndex toInvertedIndex() {
		InvertedIndex invertedIndex = new InvertedIndex();
		Iterator<Entry<KeyValuePair, Commodity>> iterator = keyValueIndex
				.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<KeyValuePair, Commodity> entry = (Entry<KeyValuePair, Commodity>) iterator
					.next();
			KeyValuePair pair = entry.getKey();
			String key = pair.getKey();
			String value = pair.getValue();
			Commodity commodity = entry.getValue();
			invertedIndex.put(key, value, commodity);

		}
		return invertedIndex;

	}
	
}
