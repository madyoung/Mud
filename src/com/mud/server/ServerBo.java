package com.mud.server;

import java.sql.SQLException;

import com.xunlei.game.api.Result;
import com.xunlei.game.api.ResultException;
import com.xunlei.game.module.util.C3P0DataSourceUtil;

import com.xunlei.game.kit.id.UuidGen;

import org.slf4j.LoggerFactory;

import com.mud.vo.UseridToWxidVo;
import com.mud.vo.UseridsVo;
import com.mud.vo.IdCounterVo;

public class ServerBo {
	private static ServerBo instance = new ServerBo();
	private DaoFactory daoFactory;
	private IdGenServer idGenServer;

	private UuidGen uuidGen = new UuidGen();

	private ServerBo() {
		daoFactory = new DaoFactory(C3P0DataSourceUtil.createDataSource("db.properties"), LoggerFactory.getLogger("db"));
	}

	public void init() {
		try {
			idGenServer = new IdGenServer();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static ServerBo getInstance() {
		return instance;
	}

	public String createId() {
		return uuidGen.createId();
	}

	public DaoFactory getDaoFactory() {
		return daoFactory;
	}

	/**
	 * @return the idGenServer
	 */
	public IdGenServer getIdGenServer() {
		return idGenServer;
	}

	public String addUseridToWxid(UseridToWxidVo useridToWxidVo) throws ResultException, SQLException {
		String id = useridToWxidVo.getId();
		daoFactory.getUseridToWxidDao().save(useridToWxidVo);
		return id;
	}

	public Result deleteUseridToWxid(String id) throws SQLException {
		daoFactory.getUseridToWxidDao().delete(id);
		return Result.OK;
	}

	public Result deleteUseridToWxid(String[] ids) throws SQLException {
		daoFactory.getUseridToWxidDao().deleteList(ids);
		return Result.OK;
	}

	public int totalUseridToWxid() throws ResultException, SQLException {
		int total = daoFactory.getUseridToWxidDao().count();
		return total;
	}

	public UseridToWxidVo viewUseridToWxid(String id) throws ResultException, SQLException {
		UseridToWxidVo vo = daoFactory.getUseridToWxidDao().query(id);
		return vo;
	}

	public String addUserids(UseridsVo useridsVo) throws ResultException, SQLException {
		String id = useridsVo.getId();
		daoFactory.getUseridsDao().save(useridsVo);
		return id;
	}

	public Result deleteUserids(String id) throws SQLException {
		daoFactory.getUseridsDao().delete(id);
		return Result.OK;
	}

	public Result deleteUserids(String[] ids) throws SQLException {
		daoFactory.getUseridsDao().deleteList(ids);
		return Result.OK;
	}

	public int totalUserids() throws ResultException, SQLException {
		int total = daoFactory.getUseridsDao().count();
		return total;
	}

	public UseridsVo viewUserids(String id) throws ResultException, SQLException {
		UseridsVo vo = daoFactory.getUseridsDao().query(id);
		return vo;
	}

	public String addIdCounter(IdCounterVo idCounterVo) throws ResultException, SQLException {
		String id = idCounterVo.getId();
		daoFactory.getIdCounterDao().save(idCounterVo);
		return id;
	}

	public Result deleteIdCounter(String id) throws SQLException {
		daoFactory.getIdCounterDao().delete(id);
		return Result.OK;
	}

	public Result deleteIdCounter(String[] ids) throws SQLException {
		daoFactory.getIdCounterDao().deleteList(ids);
		return Result.OK;
	}

	public int totalIdCounter() throws ResultException, SQLException {
		int total = daoFactory.getIdCounterDao().count();
		return total;
	}

	public IdCounterVo viewIdCounter(String id) throws ResultException, SQLException {
		IdCounterVo vo = daoFactory.getIdCounterDao().query(id);
		return vo;
	}

}