/**
 * 
 */
package com.mud.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenzhiwei
 * @since 2014-12-9 下午5:56:46
 */
public class Player {
	public static final Logger log = LoggerFactory.getLogger("playercmd");
	private int gameid = 0;

	private LinkedList<Menu> commands = new LinkedList<Menu>();

	private long userid;

	public static final String INVALID_COMMAND = "囧，请输入正确指令";

	public int story = 0;

	private Scene scene;
	private Map<Long, Item> specialItems = new HashMap<Long, Item>();

	/**
	 * @param userid
	 */
	public Player(long userid) {
		this.userid = userid;
	}

	/**
	 * @return the userid
	 */
	public long getUserid() {
		return userid;
	}

	/**
	 * @param content
	 */
	public String play(String command) {
		String result = INVALID_COMMAND;

		try {
			Menu menu = null;
			if (commands.isEmpty()) {
				menu = MenuFactory.createGameSelectMenu();
				commands.add(menu);
				result = menu.getMenuString();
			} else {
				menu = commands.getLast().handle(command);
				if (menu == null) {
					result = result + "\n" + commands.getLast().getMenuString();
				} else {
					commands.removeLast();
					commands.add(menu);
					result = menu.getMenuString();
				}
			}
		} finally {
			log.info("userid={},cmd={},reply={}", new Object[] { command, result });
		}
		return result;
	}
}
