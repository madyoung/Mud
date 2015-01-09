/**
 * 
 */
package com.mud.server;

import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mud.util.HttpUtil;
import com.mud.util.PropertiesFileUtil;

/**
 * @author chenzhiwei
 * @since 2014-12-2 下午3:02:46
 */
public class WXAccessTokenServer {
	private static final Logger log = LoggerFactory.getLogger(WXAccessTokenServer.class);
	private static final String PROPERTIES_FILE = "wxAccessToken.properties";
	private long getTime;
	private String accessToken;
	private long expiresIn;

	private String accessTokenUrl;
	private int timeout;

	public WXAccessTokenServer() {
		try {
			Properties properties = PropertiesFileUtil.getPropertiesFromResource(PROPERTIES_FILE);
			getTime = Long.parseLong(properties.getProperty("getTime", "0"));
			accessToken = properties.getProperty("accessToken", "");
			expiresIn = Long.parseLong(properties.getProperty("expiresIn", "0"));
			accessTokenUrl = properties.getProperty("accessTokenUrl");
			timeout = Integer.parseInt(properties.getProperty("timeout"));

			if (isAccessTokenExpired() || accessToken.isEmpty()) {
				flushAccessToken();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the getTime
	 */
	public long getGetTime() {
		return getTime;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public long getNextFlushTime() {
		return expiresIn - 600 - ((System.currentTimeMillis() - getTime) / 1000);
	}

	/**
	 * 判断accessToken是否过期，提前10min(600s)
	 * 
	 * @return
	 */
	public boolean isAccessTokenExpired() {
		return ((System.currentTimeMillis() - getTime) / 1000) >= (expiresIn - 600);
	}

	public void flushAccessToken() throws Exception {
		Map<String, Object> response = HttpUtil.sendHttps(accessTokenUrl, timeout, timeout);

		getTime = System.currentTimeMillis();
		accessToken = (String) response.get("access_token");
		expiresIn = (Long) response.get("expires_in");

		Properties properties = new Properties();
		properties.setProperty("getTime", getTime + "");
		properties.setProperty("expiresIn", expiresIn + "");
		properties.setProperty("accessToken", accessToken);

		properties.setProperty("timeout", timeout + "");
		properties.setProperty("accessTokenUrl", accessTokenUrl);
		PropertiesFileUtil.setPropertiesToResource(properties, PROPERTIES_FILE);
		log.info("getTime={},accessToken={},expiresIn={}", new Object[] { getTime, accessToken, expiresIn });
	}
}
