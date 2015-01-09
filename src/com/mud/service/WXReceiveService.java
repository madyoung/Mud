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
 * @since 2014-12-2 上午11:24:23
 */
public class WXReceiveService extends HttpPlainService {
	private static final Logger log = LoggerFactory.getLogger("service");

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xunlei.game.api.service.plain.http.HttpPlainService#service(com.xunlei
	 * .game.api.service.plain.http.HttpPlainRequest)
	 */
	@Override
	public void service(HttpPlainRequest request) throws Exception {
		String encrypt_type = request.getQuery("encrypt_type");
		String msg_signature = request.getQuery("msg_signature");
		String signature = request.getQuery("signature");
		String timestamp = request.getQuery("timestamp");
		String nonce = request.getQuery("nonce");

		byte[] data = request.getData();
		if (data == null) {
			return;
		}
		String reqData = new String(data, "utf-8");

		String resData = ServerManager.getInstance().getWxServer().handle(reqData, msg_signature, timestamp, nonce);

		request.getResponse().setData(resData.getBytes());

		log.info("WXReceiveService:signature={},timestamp={},nonce={},encrypt_type={},msg_signature={},reqData={},resData={}",
				new String[] { signature, timestamp, nonce, encrypt_type, msg_signature, reqData, resData });

	}

}
