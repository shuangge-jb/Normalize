package datastructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 倒排索引
 * 
 * @author Administrator
 *
 */
public class InvertedIndex {
	private Map<String, ClusterMap> index;

	public InvertedIndex() {
		super();
		index = new HashMap<String, ClusterMap>();
	}

	public InvertedIndex(Map<String, ClusterMap> index) {
		super();
		this.index = index;
	}

	/**
	 * 在ClusterMap中对应(key,value)的CommodityMap中增加商品
	 * 
	 * @param key
	 *            商品属性名
	 * @param value
	 *            商品对象
	 * @return
	 */
	public Commodity put(String featureKey, String featureValue,
			Commodity commodity) {

		if (containsKeyAndValue(featureKey, featureValue)) {
			// 存在(key,value)的商品集合
			this.get(featureKey).get(featureValue)
					.put(commodity.getUrl(), commodity);
			//System.out.println("存在(key,value)的商品集合");
		} else {
			// 有key，没有value
			if (containsKey(featureKey)
					&& (!containsValue(featureKey, featureValue))) {
				CommodityMap commodityMap = new CommodityMap();
				commodityMap.put(commodity.getUrl(), commodity);
				this.get(featureKey).put(featureValue, commodityMap);
				//System.out.println("有key，没有value");
			} else {
				// key，value均不存在
				CommodityMap commodityMap = new CommodityMap();
				commodityMap.put(commodity.getUrl(), commodity);
				ClusterMap clusterMap=new ClusterMap();
				clusterMap.put(featureValue, commodityMap);
				index.put(featureKey, clusterMap);
				//System.out.println("key，value均不存在");
			}
		}
		return commodity;
	}

	public ClusterMap get(String key) {
		return index.get(key);
	}

	public int size() {
		return index.size();
	}

	/**
	 * 遍历index中每个元素，并输出
	 */
	public void print() {
		Iterator<Entry<String, ClusterMap>> iterator = index.entrySet()
				.iterator();
		while (iterator.hasNext()) {

			Map.Entry<String, ClusterMap> entry = (Entry<String, ClusterMap>) iterator
					.next();

			String featureKey = entry.getKey();
			System.out.println("key= " + featureKey);
			ClusterMap clusterMap = entry.getValue();
			//System.out.println("clusterMap size: " + clusterMap.size());
			clusterMap.print();
		}
	}

	/**
	 * 判断集合是否包含给定的键值对
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean containsKeyAndValue(String key, String value) {
		return containsKey(key) && containsValue(key, value);// 存在该属性并且对应的clusterMap中存在数据值
	}

	public boolean containsKey(String key) {
		return index.containsKey(key);
	}

	public boolean containsValue(String key, String value) {
		if (key == null) {
			return false;
		}
		return index.get(key).containsKey(value);// 从ClusterMap中判断是否含有键为value的项
	}
	public PatternGraph toPatternGraph(){
		PatternGraph graph=new PatternGraph();
		Iterator<Entry<String, ClusterMap>> iterator=index.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, ClusterMap> entry=iterator.next();
			String key=entry.getKey();
			ClusterMap clusterMap=entry.getValue();
			EdgeMap edgeMap=clusterMap.toEdgeMap();
			graph.put(key, edgeMap);
		}
		return graph;
		
	}
	
}
