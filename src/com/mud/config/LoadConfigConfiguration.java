/**
 * 
 */
package com.mud.config;

import java.util.Map;

import com.mud.resources.ConfigConstants;
import com.mud.util.PropertiesFileUtil;
import com.xunlei.game.api.service.OneOperation;

/**
 * @author chenzhiwei
 * @since 2014-12-4 下午5:09:25
 */
public class LoadConfigConfiguration implements OneOperation {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xunlei.game.api.service.OneOperation#destroy(java.util.Map)
	 */
	@Override
	public void destroy(Map<String, String> arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xunlei.game.api.service.OneOperation#init(java.util.Map)
	 */
	@Override
	public void init(Map<String, String> arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xunlei.game.api.service.OneOperation#operate(java.util.Map)
	 */
	@Override
	public void operate(Map<String, String> arg0) {
		System.out.println(ConfigConstants.THREAD_SIZE);
		ConfigConstants.init(PropertiesFileUtil.getPropertiesFromResource("config.properties", "utf-8"));
		System.out.println(ConfigConstants.THREAD_SIZE);
	}

}
