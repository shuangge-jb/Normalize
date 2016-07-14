package datastructure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * �������ֵ�Ͷ�Ӧ����Ʒ����
 * 
 * @author Administrator
 *
 */
public class ClusterMap {
	// ��һ������Ϊ����ֵvalue���ڶ�������Ϊ(key,value)��Ӧ����Ʒ����
	private Map<String, CommodityMap> map;

	public ClusterMap() {
		super();
		map=new HashMap<String, CommodityMap>();
	}

	public ClusterMap(Map<String, CommodityMap> map) {
		super();
		this.map = map;
	}

	public CommodityMap put(String featureValue, CommodityMap commodityMap) {
		return this.map.put(featureValue, commodityMap);
	}

	public CommodityMap get(String featureValue) {
		return map.get(featureValue);
	}

	public int size() {
		return map.size();
	}
	public void print(){
		Iterator<Entry<String, CommodityMap>> iterator=map.entrySet().iterator();
		while(iterator.hasNext()){
//			Map.Entry<String, CommodityMap> entry=iterator.next();
//			String featureValue=entry.getKey();//��ȡ����ֵ
			//System.out.println("featureValue= "+featureValue);
//			CommodityMap commodityMap=entry.getValue();
			//System.out.println("commodityMap size: "+commodityMap.size());
			//commodityMap.print();
			//System.out.println("ClusterSet print finish.");
			
		}
	}
	public boolean containsKey(String key){
		return map.containsKey(key);
	}
	public EdgeMap toEdgeMap(){
		EdgeMap edgeMap=new EdgeMap();
		Iterator<Entry<String, CommodityMap>> iterator=map.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String, CommodityMap> entry= iterator.next();
			String featureValue=entry.getKey();
			//System.out.println("featureValue: "+featureValue);
			CommodityMap commodityMap=entry.getValue();
			int commodityMapLength=commodityMap.size();
			//System.out.println("commodityMapLength:"+commodityMapLength);
			edgeMap.put(featureValue, commodityMapLength);
		}
		//System.out.println("edgeMap size:"+edgeMap.size());
		return edgeMap;
	}
}
