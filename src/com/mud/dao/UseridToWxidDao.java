package com.mud.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;

import com.xunlei.game.codegen.database.datatype.DefaultValue;
import com.xunlei.game.codegen.database.BaseDao;
import com.xunlei.game.codegen.util.StringParse;

import com.mud.vo.UseridToWxidVo;

public class UseridToWxidDao extends BaseDao<UseridToWxidVo> {
 	
 	public static final String TABLE = "userid_to_wxid";
	public static final String FIELD = "id,userid,wxid,create_time";
	public static final String insertSql = "insert into " + TABLE + " (" + FIELD + ") values (?,?,?,?)";	
	public static final String querySqlWithId = "select " + FIELD + " from " + TABLE + " where id=?";
	public static final String querySql = "select " + FIELD + " from " + TABLE;
	public static final String deleteSqlWithId = "delete from " + TABLE + " where id=?";
	public static final String deleteSql = "delete from " + TABLE;
	public static final String countSql = "select count(1) from " + TABLE;
	public static final String BLANK = " ";
	public static final String WHERE = " where ";
	public static final String AND = " and ";
	public static final String LIMIT = " limit ";
	public static final String UPDATE = " update ";
	public static final String SET = " set ";
	public static final String EMPTY_STRING = "";
	public static final String COMMA = ",";
	
	public UseridToWxidDao(DataSource dataSource,Logger log){
 		super(dataSource, log);
 	}
	
	public int save(UseridToWxidVo useridToWxidVo) throws SQLException {
		return save(insertSql, useridToWxidVo);
	}
	
	public int[] saveList(List<UseridToWxidVo> useridToWxidVoList) throws SQLException{
		return saveList(insertSql, useridToWxidVoList);
	}

	public int delete(String id) throws SQLException {
		return update(deleteSqlWithId, new String[] { id });
	}
	
	public int[] deleteList(String[] ids) throws SQLException {
		return batchUpdate(deleteSqlWithId, ids);
	}
	
	public int delete(Map<String, String> condition) throws SQLException {
		StringBuilder sql = new StringBuilder(deleteSql);
		List<String> paramList = new ArrayList<String>();
		String split = WHERE;
		
		if (condition.get("id") != null) {
			sql.append(split);
			sql.append("id=?");
			paramList.add(condition.get("id"));
			split = AND;
		}
		if (condition.get("userid") != null) {
			sql.append(split);
			sql.append("userid=").append(StringParse.parseLong(condition.get("userid")));
			split = AND;
		}
		if (condition.get("wxid") != null) {
			sql.append(split);
			sql.append("wxid=?");
			paramList.add(condition.get("wxid"));
			split = AND;
		}
		if (condition.get("create_time") != null) {
			sql.append(split);
			sql.append("create_time=?");
			paramList.add(condition.get("create_time"));
			split = AND;
		}

		return update(sql.toString(), paramList.toArray(new String[0]));
	}
	
	public int update(Map<String, String> fields, Map<String, String> condition) throws SQLException {
		return update(TABLE, fields, condition);
	}
	
	public int update(String table, Map<String, String> fields, Map<String, String> condition) throws SQLException {
		StringBuilder sql = new StringBuilder();
		if (table == null) {
			table = TABLE;
		}
		sql.append(UPDATE).append(table).append(SET);
		List<String> paramList = new ArrayList<String>();
		String seperator = EMPTY_STRING;

		if (fields.get("id") != null) {
			sql.append(seperator);
			sql.append("id=?");
			paramList.add(fields.get("id"));
			seperator = COMMA;
		}
		if (fields.get("userid") != null) {
			sql.append(seperator);
			sql.append("userid=").append(StringParse.parseLong(fields.get("userid")));
			seperator = COMMA;
		}
		if (fields.get("wxid") != null) {
			sql.append(seperator);
			sql.append("wxid=?");
			paramList.add(fields.get("wxid"));
			seperator = COMMA;
		}
		if (fields.get("create_time") != null) {
			sql.append(seperator);
			sql.append("create_time=?");
			paramList.add(fields.get("create_time"));
			seperator = COMMA;
		}

		String split = WHERE;
		if (condition.get("id") != null) {
			sql.append(split);
			sql.append("id").append("=?");
			paramList.add(condition.get("id"));
		}
		split = AND;
		if (condition.get("userid") != null) {
			sql.append(split);
			sql.append("userid=").append(StringParse.parseLong(condition.get("userid")));
		}
		if (condition.get("wxid") != null) {
			sql.append(split);
			sql.append("wxid").append("=?");
			paramList.add(condition.get("wxid"));
		}
		if (condition.get("create_time") != null) {
			sql.append(split);
			sql.append("create_time").append("=?");
			paramList.add(condition.get("create_time"));
		}

		return update(sql.toString(), paramList.toArray(new String[0]));
	}	
	
	public int[] updateList(String sql, List<UseridToWxidVo> list) throws SQLException {
		String[] ids = new String[list.size()];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = list.get(i).getId();
		}

		return batchUpdate(sql, ids);
	}
	
	public int updateUseridToWxidVo(String table, UseridToWxidVo useridToWxidVo) throws SQLException{
		StringBuilder sql = new StringBuilder();
		if (table == null) {
			table = TABLE;
		}
		sql.append(" update ").append(table).append(" set ") ;
		List<Object> paramList = new ArrayList<Object>();
		String seperator = "";

		sql.append(seperator);
		sql.append("userid=?");
		paramList.add(useridToWxidVo.getUserid());
		seperator = ",";			

		sql.append(seperator);
		sql.append("wxid=?");
		paramList.add(useridToWxidVo.getWxid());
		seperator = ",";

		sql.append(seperator);
		sql.append("create_time=?");
		paramList.add(useridToWxidVo.getCreate_time());
		seperator = ",";

		sql.append(" where id=?");
		paramList.add(useridToWxidVo.getId());
		return update(sql.toString(),paramList.toArray());
	}
	
	public UseridToWxidVo query(String id) throws SQLException {
		return query(querySqlWithId, new String[] { id });
	}
	
	public UseridToWxidVo query(Map<String, String> condition) throws SQLException {
		StringBuilder sql = new StringBuilder(querySql);
		List<String> paramList = new ArrayList<String>();
		String split = WHERE;
		
		if (condition.get("id") != null) {
			sql.append(split);
			sql.append("id=?");
			paramList.add(condition.get("id"));
			split = AND;
		}
		if (condition.get("userid") != null) {
			sql.append(split);
			sql.append("userid=").append(StringParse.parseLong(condition.get("userid")));
			split = AND;
		}
		if (condition.get("wxid") != null) {
			sql.append(split);
			sql.append("wxid=?");
			paramList.add(condition.get("wxid"));
			split = AND;
		}
		if (condition.get("create_time") != null) {
			sql.append(split);
			sql.append("create_time=?");
			paramList.add(condition.get("create_time"));
			split = AND;
		}

		return query(sql.toString(), paramList.toArray(new String[0]));
	}

	public List<UseridToWxidVo> queryList(Map<String, String> condition, boolean isPage, int page, int perPage) throws SQLException {
		StringBuilder sql = new StringBuilder(querySql);
		List<String> paramList = new ArrayList<String>();
		String split = WHERE;
		
		if (condition.get("id") != null) {
			sql.append(split);
			sql.append("id=?");
			paramList.add(condition.get("id"));
			split = AND;
		}
		if (condition.get("userid") != null) {
			sql.append(split);
			sql.append("userid=").append(StringParse.parseLong(condition.get("userid")));
			split = AND;
		}
		if (condition.get("wxid") != null) {
			sql.append(split);
			sql.append("wxid=?");
			paramList.add(condition.get("wxid"));
			split = AND;
		}
		if (condition.get("create_time") != null) {
			sql.append(split);
			sql.append("create_time=?");
			paramList.add(condition.get("create_time"));
			split = AND;
		}
			
		if (isPage) {
			int start = (page - 1) * perPage;
			sql.append(LIMIT).append(start).append(",").append(perPage);
		}

		return queryList(sql.toString(), paramList.toArray(new String[0]));
	}

	public List<UseridToWxidVo> queryList(boolean isPage, int page, int perPage) throws SQLException {
		StringBuilder sb = new StringBuilder(querySql);
		
		if (isPage) {
			int start = (page - 1) * perPage;
			sb.append(LIMIT).append(start).append(",").append(perPage);
		}
		return queryList(sb.toString(), null);
	}
	
	public int count() throws SQLException {
		return queryForInt(countSql);
	}
	
	@Override
	protected void setRow(PreparedStatement ps, UseridToWxidVo object) throws SQLException {
		ps.setString(1, object.getId());
		ps.setLong(2, object.getUserid());
		ps.setString(3, object.getWxid());
		ps.setString(4, object.getCreate_time());
	}
	
	@Override
	protected UseridToWxidVo mapRow(ResultSet rs) throws SQLException {
		UseridToWxidVo useridToWxidVo = new UseridToWxidVo();
		useridToWxidVo.setId(rs.getString(1));
		useridToWxidVo.setUserid(rs.getLong(2));
		useridToWxidVo.setWxid(rs.getString(3));
		useridToWxidVo.setCreate_time(rs.getString(4));
		return useridToWxidVo;
	}
}
