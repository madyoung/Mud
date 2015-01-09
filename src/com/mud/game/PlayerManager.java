/**
 * 
 */
package com.mud.game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenzhiwei
 * @since 2014-12-9 下午5:54:41
 */
public class PlayerManager {
	public static final Logger log = LoggerFactory.getLogger("player");
	private static Map<Long, Player> players = new ConcurrentHashMap<Long, Player>();

	/**
	 * @param userid
	 * @return
	 */
	public static Player getPlayer(long userid) {
		Player player = players.get(userid);

		if (player == null) {
			player = new Player(userid);
			players.put(player.getUserid(), player);
			log.info("create:userid={}", player.getUserid());
		}
		return player;
	}

	public static void exit(Player player) {
		players.remove(player.getUserid());
		log.info("exit:userid={}", player.getUserid());
	}

	/**
	 * @param player
	 */
	public static void start(Player player) {
		log.info("start:userid={}", player.getUserid());
	}
}
