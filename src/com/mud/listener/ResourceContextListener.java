/**
 * 
 */
package com.mud.listener;

import com.mud.resources.ConfigConstants;
import com.mud.server.ServerBo;
import com.mud.server.ServerManager;
import com.mud.util.PropertiesFileUtil;
import com.xunlei.game.api.service.Context;
import com.xunlei.game.api.service.ContextListener;

/**
 * @author chenzhiwei
 * @since 2014-12-2 下午6:10:57
 */
public class ResourceContextListener implements ContextListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xunlei.game.api.service.ContextListener#contextDestroyed(com.xunlei
	 * .game.api.service.Context)
	 */
	@Override
	public void contextDestroyed(Context context) {
		context.getLog().log("Mud stop.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xunlei.game.api.service.ContextListener#contextInitialized(com.xunlei
	 * .game.api.service.Context)
	 */
	@Override
	public void contextInitialized(Context context) {
		ConfigConstants.init(PropertiesFileUtil.getPropertiesFromResource("config.properties", "utf-8"));
		context.getLog().log("init ConfigConstants.");
		ServerManager.getInstance();
		context.getLog().log("init ServerManager.");
		ServerBo.getInstance();
		ServerBo.getInstance().init();
		context.getLog().log("init ServerBo.");
		context.getLog().log("Mud start successfully.");
	}
}
