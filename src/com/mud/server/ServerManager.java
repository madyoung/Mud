/**
 * 
 */
package com.mud.server;

/**
 * @author chenzhiwei
 * @since 2014-12-2 下午5:02:25
 */
public class ServerManager {
	
	private static ServerManager instance = new ServerManager();
	
	private WXServer wxServer;
	private WXAccessTokenServer wxAccessTokenServer;
	
	/**
	 * 
	 */
	private ServerManager() {
		wxServer = new WXServer();
		wxAccessTokenServer = new WXAccessTokenServer();
	}
	
	
	public static ServerManager getInstance() {
		return instance;
	}
	/**
	 * @return the wxAccessTokenServer
	 */
	public WXAccessTokenServer getWxAccessTokenServer() {
		return wxAccessTokenServer;
	}
	
	/**
	 * @return the wxServer
	 */
	public WXServer getWxServer() {
		return wxServer;
	}

}
