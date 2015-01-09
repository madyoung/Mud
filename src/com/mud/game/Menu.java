/**
 * 
 */
package com.mud.game;

/**
 * @author chenzhiwei
 * @since 2015-1-5
 */
public abstract class Menu {
	protected String message;
	private String id;
	protected String description;

	public Menu(String id, String description) {
		this.id = id;
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public abstract String getMenuString();

	public abstract void addMenu(Menu menu);

	public abstract Menu handle(String cmd);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id).append(",");
		sb.append("description=").append(description).append(",");
		sb.append("message=").append(message);
		return sb.toString();
	}
}
