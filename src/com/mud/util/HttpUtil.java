package com.mud.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xunlei.game.util.JsonUtil;

public class HttpUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger("proxy");

	private static final int cacheLength = 2048;

	public static Map<String, Object> sendHttp(String urlString, int connTimeout, int readTimeout) throws IOException {
		long startTime = System.currentTimeMillis();

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		conn.setConnectTimeout(connTimeout);
		conn.setReadTimeout(readTimeout);
		String respString = null;
		try {
			respString = readString(conn.getInputStream());
			long cost = System.currentTimeMillis() - startTime;
			LOGGER.info("Send http request success, request: {}, cost: {}, response: {}", new Object[] { urlString, cost, respString });
		} catch (IOException e) {
			long cost = System.currentTimeMillis() - startTime;
			LOGGER.error("Send http request fail, request: " + urlString + ", cost: " + cost, e);
			throw e;
		} finally {
			if ((conn != null) && (conn instanceof HttpURLConnection)) {
				((HttpURLConnection) conn).disconnect();
			}
		}
		return (Map<String, Object>) JsonUtil.toMap(respString);
	}
	
	public static  Map<String, Object> sendHttps(String urlString, int connTimeout, int readTimeout) throws IOException {
		long startTime = System.currentTimeMillis();

		URL url = new URL(urlString);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setConnectTimeout(connTimeout);
		conn.setReadTimeout(readTimeout);
		String respString = null;
		try {
			respString = readString(conn.getInputStream());
			long cost = System.currentTimeMillis() - startTime;
			LOGGER.info("Send http request success, request: {}, cost: {}, response: {}", new Object[] { urlString, cost, respString });
		} catch (IOException e) {
			long cost = System.currentTimeMillis() - startTime;
			LOGGER.error("Send http request fail, request: {}, cost: {}, e" + urlString + ", cost: " + cost, e);
			throw e;
		} finally {
			if ((conn != null) && (conn instanceof HttpURLConnection)) {
				((HttpURLConnection) conn).disconnect();
			}
		}
		return (Map<String, Object>) JsonUtil.toMap(respString);
	}
	
	public static String request(String urlString, int connTimeout, int readTimeout) throws IOException {
		long startTime = System.currentTimeMillis();

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		conn.setConnectTimeout(connTimeout);
		conn.setReadTimeout(readTimeout);
		String respString = null;
		try {
			respString = readString(conn.getInputStream());
			long cost = System.currentTimeMillis() - startTime;
			LOGGER.info("Send http request success, request: {}, cost: {}, response: {}", new Object[] { urlString, cost, respString });
		} catch (IOException e) {
			long cost = System.currentTimeMillis() - startTime;
			LOGGER.error("Send http request fail, request: " + urlString + ", cost: " + cost, e);
			throw e;
		} finally {
			if ((conn != null) && (conn instanceof HttpURLConnection)) {
				((HttpURLConnection) conn).disconnect();
			}
		}
		return respString;
	}

	public static String readString(InputStream inputStream) throws IOException {
		StringBuilder buff = new StringBuilder();
		BufferedReader reader = null;
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = reader.readLine()) != null) {
				buff.append(line);
			}
		} finally {
			close(reader);
		}
		return buff.toString();
	}

	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 发送post方式http请求
	 * 
	 */
	public static byte[] requestByPost(String url, byte[] request) throws IOException {
		HttpURLConnection urlConnection = null;
		OutputStream out = null;
		InputStream in = null;
		ByteArrayOutputStream bais = null;
		byte[] response = null;
		try {
			urlConnection = (HttpURLConnection) new URL(url).openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setUseCaches(false);
			if (request != null) {
				out = urlConnection.getOutputStream();
				out.write(request);
				out.flush();
				out.close();
			}
			in = urlConnection.getInputStream();
			bais = new ByteArrayOutputStream();
			byte[] bytes = new byte[cacheLength];
			int byteIn = in.read(bytes);
			while (byteIn != -1) {
				bais.write(bytes, 0, byteIn);
				byteIn = in.read(bytes);
			}
			response = bais.toByteArray();
		} catch (IOException e) {
			throw e;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return response;
	}

}
