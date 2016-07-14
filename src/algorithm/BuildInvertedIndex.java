package algorithm;

import datastructure.CommodityMap;
import datastructure.InvertedIndex;
import datastructure.KeyValuePairIndex;

/**
 * �㷨1��������������
 * 
 * @author Administrator
 *
 */
public class BuildInvertedIndex {
	/**
	 * ��������������������
	 * 
	 * @return
	 */

	public static InvertedIndex buildIndex(CommodityMap commodityMap) {
		KeyValuePairIndex index=commodityMap.toKeyValuePairIndex();
		return index.toInvertedIndex();
	}

	

}
