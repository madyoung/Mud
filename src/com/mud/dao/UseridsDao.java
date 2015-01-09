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

import com.mud.vo.UseridsVo;

public class UseridsDao extends BaseDao<UseridsVo> {
 	
 	public static final String TABLE = "userids";
	public static final String FIELD = "id,userid,create_time";
	public static final String insertSql = "insert into " + TABLE + " (" + FIELD + ") values (?,?,?)";	
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
	
	public UseridsDao(DataSource dataSource,Logger log){
 		super(dataSource, log);
 	}
	
	public int save(UseridsVo useridsVo) throws SQLException {
		return save(insertSql, useridsVo);
	}
	
	public int[] saveList(List<UseridsVo> useridsVoList) throws SQLException{
		return saveList(insertSql, useridsVoList);
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
		if (condition.get("create_time") != null) {
			sql.append(split);
			sql.append("create_time").append("=?");
			paramList.add(condition.get("create_time"));
		}

		return update(sql.toString(), paramList.toArray(new String[0]));
	}	
	
	public int[] updateList(String sql, List<UseridsVo> list) throws SQLException {
		String[] ids = new String[list.size()];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = list.get(i).getId();
		}

		return batchUpdate(sql, ids);
	}
	
	public int updateUseridsVo(String table, UseridsVo useridsVo) throws SQLException{
		StringBuilder sql = new StringBuilder();
		if (table == null) {
			table = TABLE;
		}
		sql.append(" update ").append(table).append(" set ") ;
		List<Object> paramList = new ArrayList<Object>();
		String seperator = "";

		sql.append(seperator);
		sql.append("userid=?");
		paramList.add(useridsVo.getUserid());
		seperator = ",";			

		sql.append(seperator);
		sql.append("create_time=?");
		paramList.add(useridsVo.getCreate_time());
		seperator = ",";

		sql.append(" where id=?");
		paramList.add(useridsVo.getId());
		return update(sql.toString(),paramList.toArray());
	}
	
	public UseridsVo query(String id) throws SQLException {
		return query(querySqlWithId, new String[] { id });
	}
	
	public UseridsVo query(Map<String, String> condition) throws SQLException {
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
		if (condition.get("create_time") != null) {
			sql.append(split);
			sql.append("create_time=?");
			paramList.add(condition.get("create_time"));
			split = AND;
		}

		return query(sql.toString(), paramList.toArray(new String[0]));
	}

	public List<UseridsVo> queryList(Map<String, String> condition, boolean isPage, int page, int perPage) throws SQLException {
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

	public List<UseridsVo> queryList(boolean isPage, int page, int perPage) throws SQLException {
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
	protected void setRow(PreparedStatement ps, UseridsVo object) throws SQLException {
		ps.setString(1, object.getId());
		ps.setLong(2, object.getUserid());
		ps.setString(3, object.getCreate_time());
	}
	
	@Override
	protected UseridsVo mapRow(ResultSet rs) throws SQLException {
		UseridsVo useridsVo = new UseridsVo();
		useridsVo.setId(rs.getString(1));
		useridsVo.setUserid(rs.getLong(2));
		useridsVo.setCreate_time(rs.getString(3));
		return useridsVo;
	}
}
