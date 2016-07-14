package datastructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * 商品集合
 * 
 * @author Administrator
 *
 */
public class CommodityMap {
	private Map<String, Commodity> commoditys;

	public CommodityMap(Map<String, Commodity> commoditys) {
		super();
		this.commoditys = commoditys;
	}

	public CommodityMap() {
		super();
		commoditys = new HashMap<String, Commodity>();
	}

	public Map<String, Commodity> getCommoditys() {
		return commoditys;
	}

	public Set<String> keySet() {
		return commoditys.keySet();
	}

	public Commodity put(String key, Commodity value) {
		return commoditys.put(key, value);
	}

	public void putAll(CommodityMap value) {
		commoditys.putAll(value.getCommoditys());
	}

	public void putAll(Map<String, Commodity> value) {
		commoditys.putAll(value);
	}

	public Commodity get(String key) {
		return commoditys.get(key);
	}

	public Set<Map.Entry<String, Commodity>> entrySet() {
		return commoditys.entrySet();
	}

	public boolean contains(String url) {
		return commoditys.containsKey(url);
	}

	public void print() {

		Iterator<Entry<String, Commodity>> iterator = commoditys.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Commodity> entry = (Entry<String, Commodity>) iterator
					.next();

			String url = entry.getKey();
			System.out.println("url= " + url);
			Commodity commodity = entry.getValue();
			// System.out.println("features size: "+commodity.size());
			System.out.println("commodity title: " + commodity.getTitle());

		}
		// System.out.println("CommodityMap print finish.");
	}

	public int size() {
		return commoditys.size();
	}

	/**
	 * 遍历商品集合，取出所有的键值对和键值对所在的商品(<<A,V>,W>) 从商品集到商品键值对索引
	 * 
	 * @return
	 */
	public KeyValuePairIndex toKeyValuePairIndex() {
		KeyValuePairIndex resultSet = new KeyValuePairIndex();

		Iterator<Entry<String, Commodity>> iterator = commoditys.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Commodity> entry = (Entry<String, Commodity>) iterator
					.next();

			Commodity commodity = entry.getValue();
			KeyValuePairIndex keyValueIndexOfOneCommodity = commodity
					.getKeyValuePairIndex();
			resultSet.putAll(keyValueIndexOfOneCommodity);

		}
		return resultSet;
	}
}
