/**
 * 
 */
package com.mud.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mud.server.ServerManager;
import com.xunlei.game.api.service.ContextTask;

/**
 * @author chenzhiwei
 * @since 2014-12-2 下午5:59:53
 */
public class FlushAccessTokenTask extends ContextTask {
	private static final Logger log = LoggerFactory.getLogger("task");

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		long nextFlushTime = 0;
		long getTime = ServerManager.getInstance().getWxAccessTokenServer().getGetTime();
		String accessToken = ServerManager.getInstance().getWxAccessTokenServer().getAccessToken();
		long expiresIn = ServerManager.getInstance().getWxAccessTokenServer().getExpiresIn();
		boolean flush = ServerManager.getInstance().getWxAccessTokenServer().isAccessTokenExpired();
		try {
			if (flush) {
				ServerManager.getInstance().getWxAccessTokenServer().flushAccessToken();
				getTime = ServerManager.getInstance().getWxAccessTokenServer().getGetTime();
				accessToken = ServerManager.getInstance().getWxAccessTokenServer().getAccessToken();
				expiresIn = ServerManager.getInstance().getWxAccessTokenServer().getExpiresIn();
			}
			nextFlushTime = ServerManager.getInstance().getWxAccessTokenServer().getNextFlushTime();
			if (nextFlushTime <= 0) {
				ServerManager.getInstance().getWxAccessTokenServer().flushAccessToken();
				getTime = ServerManager.getInstance().getWxAccessTokenServer().getGetTime();
				accessToken = ServerManager.getInstance().getWxAccessTokenServer().getAccessToken();
				expiresIn = ServerManager.getInstance().getWxAccessTokenServer().getExpiresIn();
				nextFlushTime = ServerManager.getInstance().getWxAccessTokenServer().getNextFlushTime();
			}
		} catch (Exception e) {
			// 邮件通知
			log.error("{},{}", new Object[] { FlushAccessTokenTask.class, e });
			nextFlushTime = expiresIn;
		} finally {
			log.info("{}:getTime={},accessToken={},expiresIn={},flush={},nextFlushTime={}",
					new Object[] { FlushAccessTokenTask.class, getTime, accessToken, expiresIn, flush, nextFlushTime });
			reExecute(nextFlushTime);
		}
	}
}
