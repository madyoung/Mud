/**
 * 
 */
package com.mud.service;

import com.xunlei.game.api.service.plain.http.HttpPlainRequest;
import com.xunlei.game.api.service.plain.http.HttpPlainService;

/**
 * @author chenzhiwei
 * @since 2014-12-1 下午3:04:22
 */
public class Echo extends HttpPlainService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xunlei.game.api.service.plain.http.HttpPlainService#service(com.xunlei
	 * .game.api.service.plain.http.HttpPlainRequest)
	 */
	@Override
	public void service(HttpPlainRequest request) throws Exception {
		request.getResponse().setData(request.getRemoteAddress().toString().getBytes("utf-8"));

	}

}
