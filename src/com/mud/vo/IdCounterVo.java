package com.mud.vo;

import java.util.HashMap;
import java.util.Map;

public class IdCounterVo {
	private String id;
	private long counter;
	private String edit_time;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}	
	
	public long getCounter() {
		return this.counter;
	}
	
	public void setCounter(long counter) {
		this.counter = counter;
	}	
	
	public String getEdit_time() {
		return this.edit_time;
	}
	
	public void setEdit_time(String edit_time) {
		this.edit_time = edit_time;
	}	

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("counter", counter);
		map.put("edit_time", edit_time);
		return map;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id).append(",");
		sb.append("counter=").append(counter).append(",");
		sb.append("edit_time=").append(edit_time).append(",");
		
		return sb.toString();
	}

}
