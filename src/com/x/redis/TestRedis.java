/**
 * 
 */
package com.x.redis;

/**
 * @author chenzhiwei
 * @since 2014-12-11 上午11:17:34
 */
public class TestRedis {
	public static void main(String[] args) throws Exception {
		String host = "10.11.25.101";
		int port = 6379;
		int maxActive = 100;
		int maxIdle = 20;
		long maxWait = 1000;
		int timeout = 2000;
		RedisServer redisServer = new RedisServer(host, port, maxActive, maxIdle, maxWait, timeout);
		//redisServer
	}
}
