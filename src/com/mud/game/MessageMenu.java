/**
 * 
 */
package com.mud.game;

import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * @author chenzhiwei
 * @since 2015-1-6
 */
public class MessageMenu extends Menu {

	private Map<String, Menu> menus = new TreeMap<String, Menu>();

	/**
	 * @param id
	 * @param description
	 */
	public MessageMenu(String id, String description) {
		super(id, description);
	}

	@Override
	public void addMenu(Menu menu) {
		menus.put(menu.getId(), menu);
	}

	@Override
	public String getMenuString() {
		StringBuilder sb = new StringBuilder();
		sb.append(message);
		for (Entry<String, Menu> entry : menus.entrySet()) {
			sb.append("\n");
			sb.append(entry.getValue().getId()).append("、").append(entry.getValue().getDescription());
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mud.game.Menu#handle(java.lang.String)
	 */
	@Override
	public Menu handle(String cmd) {
		return menus.get(cmd);
	}

}
