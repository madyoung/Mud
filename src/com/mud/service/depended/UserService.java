/**
 * 
 */
package com.mud.service.depended;

import com.xunlei.game.api.service.DependedService;

/**
 * @author chenzhiwei
 * @since 2014-12-23 上午11:13:13
 */
public class UserService extends DependedService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xunlei.game.api.service.DependedService#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xunlei.game.api.service.DependedService#init()
	 */
	@Override
	public void init() throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xunlei.game.api.service.DependedService#service()
	 */
	@Override
	public boolean service() {
		String userName = super.getConfig("userName");

		return true;
	}

}
