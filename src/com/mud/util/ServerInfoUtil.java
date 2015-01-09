/**
 * 
 */
package com.mud.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author chenzhiwei
 * @since 2014-11-6 下午4:26:30
 */
public class ServerInfoUtil {

	public static String getServerPath() {
		return ServerInfoUtil.class.getResource("/").toString();
	}

	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "unknown-host";
		}
	}

}
