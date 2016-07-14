package algorithm;

import datastructure.CommodityMap;
import datastructure.InvertedIndex;
import datastructure.KeyValuePairIndex;

/**
 * 算法1，建立倒排索引
 * 
 * @author Administrator
 *
 */
public class BuildInvertedIndex {
	/**
	 * 建立倒排索引，并返回
	 * 
	 * @return
	 */

	public static InvertedIndex buildIndex(CommodityMap commodityMap) {
		KeyValuePairIndex index=commodityMap.toKeyValuePairIndex();
		return index.toInvertedIndex();
	}

	

}
