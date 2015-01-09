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

import com.mud.vo.IdCounterVo;

public class IdCounterDao extends BaseDao<IdCounterVo> {
 	
 	public static final String TABLE = "id_counter";
	public static final String FIELD = "id,counter,edit_time";
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
	
	public IdCounterDao(DataSource dataSource,Logger log){
 		super(dataSource, log);
 	}
	
	public int save(IdCounterVo idCounterVo) throws SQLException {
		return save(insertSql, idCounterVo);
	}
	
	public int[] saveList(List<IdCounterVo> idCounterVoList) throws SQLException{
		return saveList(insertSql, idCounterVoList);
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
		if (condition.get("counter") != null) {
			sql.append(split);
			sql.append("counter=").append(StringParse.parseLong(condition.get("counter")));
			split = AND;
		}
		if (condition.get("edit_time") != null) {
			sql.append(split);
			sql.append("edit_time=?");
			paramList.add(condition.get("edit_time"));
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
		if (fields.get("counter") != null) {
			sql.append(seperator);
			sql.append("counter=").append(StringParse.parseLong(fields.get("counter")));
			seperator = COMMA;
		}
		if (fields.get("edit_time") != null) {
			sql.append(seperator);
			sql.append("edit_time=?");
			paramList.add(fields.get("edit_time"));
			seperator = COMMA;
		}

		String split = WHERE;
		if (condition.get("id") != null) {
			sql.append(split);
			sql.append("id").append("=?");
			paramList.add(condition.get("id"));
		}
		split = AND;
		if (condition.get("counter") != null) {
			sql.append(split);
			sql.append("counter=").append(StringParse.parseLong(condition.get("counter")));
		}
		if (condition.get("edit_time") != null) {
			sql.append(split);
			sql.append("edit_time").append("=?");
			paramList.add(condition.get("edit_time"));
		}

		return update(sql.toString(), paramList.toArray(new String[0]));
	}	
	
	public int[] updateList(String sql, List<IdCounterVo> list) throws SQLException {
		String[] ids = new String[list.size()];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = list.get(i).getId();
		}

		return batchUpdate(sql, ids);
	}
	
	public int updateIdCounterVo(String table, IdCounterVo idCounterVo) throws SQLException{
		StringBuilder sql = new StringBuilder();
		if (table == null) {
			table = TABLE;
		}
		sql.append(" update ").append(table).append(" set ") ;
		List<Object> paramList = new ArrayList<Object>();
		String seperator = "";

		sql.append(seperator);
		sql.append("counter=?");
		paramList.add(idCounterVo.getCounter());
		seperator = ",";			

		sql.append(seperator);
		sql.append("edit_time=?");
		paramList.add(idCounterVo.getEdit_time());
		seperator = ",";

		sql.append(" where id=?");
		paramList.add(idCounterVo.getId());
		return update(sql.toString(),paramList.toArray());
	}
	
	public IdCounterVo query(String id) throws SQLException {
		return query(querySqlWithId, new String[] { id });
	}
	
	public IdCounterVo query(Map<String, String> condition) throws SQLException {
		StringBuilder sql = new StringBuilder(querySql);
		List<String> paramList = new ArrayList<String>();
		String split = WHERE;
		
		if (condition.get("id") != null) {
			sql.append(split);
			sql.append("id=?");
			paramList.add(condition.get("id"));
			split = AND;
		}
		if (condition.get("counter") != null) {
			sql.append(split);
			sql.append("counter=").append(StringParse.parseLong(condition.get("counter")));
			split = AND;
		}
		if (condition.get("edit_time") != null) {
			sql.append(split);
			sql.append("edit_time=?");
			paramList.add(condition.get("edit_time"));
			split = AND;
		}

		return query(sql.toString(), paramList.toArray(new String[0]));
	}

	public List<IdCounterVo> queryList(Map<String, String> condition, boolean isPage, int page, int perPage) throws SQLException {
		StringBuilder sql = new StringBuilder(querySql);
		List<String> paramList = new ArrayList<String>();
		String split = WHERE;
		
		if (condition.get("id") != null) {
			sql.append(split);
			sql.append("id=?");
			paramList.add(condition.get("id"));
			split = AND;
		}
		if (condition.get("counter") != null) {
			sql.append(split);
			sql.append("counter=").append(StringParse.parseLong(condition.get("counter")));
			split = AND;
		}
		if (condition.get("edit_time") != null) {
			sql.append(split);
			sql.append("edit_time=?");
			paramList.add(condition.get("edit_time"));
			split = AND;
		}
			
		if (isPage) {
			int start = (page - 1) * perPage;
			sql.append(LIMIT).append(start).append(",").append(perPage);
		}

		return queryList(sql.toString(), paramList.toArray(new String[0]));
	}

	public List<IdCounterVo> queryList(boolean isPage, int page, int perPage) throws SQLException {
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
	protected void setRow(PreparedStatement ps, IdCounterVo object) throws SQLException {
		ps.setString(1, object.getId());
		ps.setLong(2, object.getCounter());
		ps.setString(3, object.getEdit_time());
	}
	
	@Override
	protected IdCounterVo mapRow(ResultSet rs) throws SQLException {
		IdCounterVo idCounterVo = new IdCounterVo();
		idCounterVo.setId(rs.getString(1));
		idCounterVo.setCounter(rs.getLong(2));
		idCounterVo.setEdit_time(rs.getString(3));
		return idCounterVo;
	}
}
