/**
 * 
 */
package com.mud.game;

import java.util.LinkedList;

/**
 * @author chenzhiwei
 * @since 2015-1-6
 */
public class DialogMenu extends Menu {

	/**
	 * @param id
	 * @param description
	 */
	public DialogMenu(String id, String description) {
		super(id, description);
	}

	private LinkedList<String> dialogs = new LinkedList<String>();

	private Menu next;

	public void appendDialog(String dialog) {
		this.dialogs.add(dialog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mud.game.Menu#getMenuString()
	 */
	@Override
	public String getMenuString() {
		return dialogs.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mud.game.Menu#handle(java.lang.String)
	 */
	@Override
	public Menu handle(String cmd) {
		dialogs.pollFirst();
		if (dialogs.isEmpty()) {
			return next;
		} else {
			return this;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mud.game.Menu#addMenu(com.mud.game.Menu)
	 */
	@Override
	public void addMenu(Menu menu) {
		this.next = menu;
	}
}
