/**
 * 
 */
package com.mud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mud.server.ServerManager;
import com.xunlei.game.api.service.plain.http.HttpPlainRequest;
import com.xunlei.game.api.service.plain.http.HttpPlainService;

/**
 * @author chenzhiwei
 * @since 2014-12-2 上午10:20:04
 */
public class WXVerifyURLService extends HttpPlainService {
	private static Logger log = LoggerFactory.getLogger(WXVerifyURLService.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xunlei.game.api.service.plain.http.HttpPlainService#service(com.xunlei
	 * .game.api.service.plain.http.HttpPlainRequest)
	 */
	@Override
	public void service(HttpPlainRequest httpPlainRequest) throws Exception {
		String signature = httpPlainRequest.getQuery("signature");
		String timestamp = httpPlainRequest.getQuery("timestamp");
		String nonce = httpPlainRequest.getQuery("nonce");
		String echostr = httpPlainRequest.getQuery("echostr");

		if (ServerManager.getInstance().getWxServer().verifyUrl(signature, timestamp, nonce)) {
			httpPlainRequest.getResponse().setData(echostr.getBytes("utf-8"));
		}
		log.info("signature={},timestamp={},nonce={},echostr={}",
				new String[] { signature, timestamp, nonce, echostr });
	}
}
