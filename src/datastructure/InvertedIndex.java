package datastructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ��������
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
	 * ��ClusterMap�ж�Ӧ(key,value)��CommodityMap��������Ʒ
	 * 
	 * @param key
	 *            ��Ʒ������
	 * @param value
	 *            ��Ʒ����
	 * @return
	 */
	public Commodity put(String featureKey, String featureValue,
			Commodity commodity) {

		if (containsKeyAndValue(featureKey, featureValue)) {
			// ����(key,value)����Ʒ����
			this.get(featureKey).get(featureValue)
					.put(commodity.getUrl(), commodity);
			//System.out.println("����(key,value)����Ʒ����");
		} else {
			// ��key��û��value
			if (containsKey(featureKey)
					&& (!containsValue(featureKey, featureValue))) {
				CommodityMap commodityMap = new CommodityMap();
				commodityMap.put(commodity.getUrl(), commodity);
				this.get(featureKey).put(featureValue, commodityMap);
				//System.out.println("��key��û��value");
			} else {
				// key��value��������
				CommodityMap commodityMap = new CommodityMap();
				commodityMap.put(commodity.getUrl(), commodity);
				ClusterMap clusterMap=new ClusterMap();
				clusterMap.put(featureValue, commodityMap);
				index.put(featureKey, clusterMap);
				//System.out.println("key��value��������");
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
	 * ����index��ÿ��Ԫ�أ������
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
	 * �жϼ����Ƿ���������ļ�ֵ��
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean containsKeyAndValue(String key, String value) {
		return containsKey(key) && containsValue(key, value);// ���ڸ����Բ��Ҷ�Ӧ��clusterMap�д�������ֵ
	}

	public boolean containsKey(String key) {
		return index.containsKey(key);
	}

	public boolean containsValue(String key, String value) {
		if (key == null) {
			return false;
		}
		return index.get(key).containsKey(value);// ��ClusterMap���ж��Ƿ��м�Ϊvalue����
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
