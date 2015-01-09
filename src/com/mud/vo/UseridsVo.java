package com.mud.vo;

import java.util.HashMap;
import java.util.Map;

public class UseridsVo {
	private String id;
	private long userid;
	private String create_time;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}	
	
	public long getUserid() {
		return this.userid;
	}
	
	public void setUserid(long userid) {
		this.userid = userid;
	}	
	
	public String getCreate_time() {
		return this.create_time;
	}
	
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}	

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("userid", userid);
		map.put("create_time", create_time);
		return map;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id).append(",");
		sb.append("userid=").append(userid).append(",");
		sb.append("create_time=").append(create_time).append(",");
		
		return sb.toString();
	}

}
