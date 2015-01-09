package com.mud.server;

import javax.sql.DataSource;
import org.slf4j.Logger;
import com.mud.dao.*;

public class DaoFactory {

	protected Logger dbLog;
	protected DataSource dataSource;
	
	private IdCounterDao idCounterDao;
	private UseridToWxidDao useridToWxidDao;
	private UseridsDao useridsDao;

	public DaoFactory(DataSource dataSource,Logger dbLog) {
		this.dataSource = dataSource;
		this.dbLog = dbLog;
		init();
	}
	
	private void init() {
		idCounterDao = new IdCounterDao(dataSource, dbLog);
		useridToWxidDao = new UseridToWxidDao(dataSource, dbLog);
		useridsDao = new UseridsDao(dataSource, dbLog);
	}
	
	public DataSource getDataSource() {
		return this.dataSource;
	}
	
	public IdCounterDao getIdCounterDao() {
		return this.idCounterDao;
	}
	 
	public UseridToWxidDao getUseridToWxidDao() {
		return this.useridToWxidDao;
	}
	 
	public UseridsDao getUseridsDao() {
		return this.useridsDao;
	}
	 
}
