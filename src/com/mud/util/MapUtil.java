/**
 * 
 */
package com.mud.util;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author chenzhiwei
 * @since 2015-1-6
 */
public class MapUtil {
	public static String print(Map<?, ?> map) {

		StringBuilder sb = new StringBuilder();
		String seperator = "";
		for (Entry<?, ?> entry : map.entrySet()) {
			sb.append(seperator).append(entry);
			seperator = ",";
		}
		return sb.toString();

	}

}
