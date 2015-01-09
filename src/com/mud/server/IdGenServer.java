/**
 * 
 */
package com.mud.server;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mud.exception.IdGenException;
import com.mud.vo.IdCounterVo;
import com.mud.vo.UseridToWxidVo;
import com.mud.vo.UseridsVo;
import com.xunlei.game.util.DateUtil;

/**
 * @author chenzhiwei
 * @since 2014-12-8 下午3:57:08
 */
public class IdGenServer {
	protected AtomicLong index = new AtomicLong(0);
	private String id;
	private static final Logger log = LoggerFactory.getLogger("idGen");

	/**
	 * @throws SQLException
	 * 
	 */
	public IdGenServer() throws SQLException {
		IdCounterVo idCounterVo = ServerBo.getInstance().getDaoFactory().getIdCounterDao().query("select * from mud.id_counter", null);
		if (idCounterVo == null || idCounterVo.getCounter() < 0) {
			throw new IllegalStateException("query id counter error." + idCounterVo);
		}
		id = idCounterVo.getId();
		index.set(idCounterVo.getCounter());
	}

	private UseridsVo generateUserid() throws SQLException {
		long counter = index.incrementAndGet();
		if (counter <= 0) {
			return null;
		}

		String currentDate = DateUtil.currentString();
		String sql = "update mud.id_counter set counter=" + counter
				+ ",edit_time=\"" + currentDate + "\" where id=\"" + id + "\" and counter<" + counter;

		if (ServerBo.getInstance().getDaoFactory().getIdCounterDao().update(sql) == 0) {
			// 处理可能由于并发造成的更新数据库异常
			IdCounterVo idCounterVo = ServerBo.getInstance().getDaoFactory().getIdCounterDao().query("select * from mud.id_counter", null);
			if (idCounterVo == null || idCounterVo.getCounter() < counter) {
				return null;
			}
		}

		UseridsVo useridsVo = new UseridsVo();
		useridsVo.setId(ServerBo.getInstance().createId());
		useridsVo.setUserid(counter);
		useridsVo.setCreate_time(DateUtil.currentString());
		ServerBo.getInstance().getDaoFactory().getUseridsDao().save(useridsVo);

		return useridsVo;
	}

	public UseridToWxidVo queryUseridByWxid(String wxid) throws SQLException {
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("wxid", wxid);
		UseridToWxidVo useridToWxidVo = ServerBo.getInstance().getDaoFactory().getUseridToWxidDao().query(condition);
		return useridToWxidVo;
	}

	public UseridToWxidVo generateUseridByWxid(String wxid) throws SQLException, IdGenException {
		// 先查询wxid是否已存在
		// ......
		// ......

		UseridsVo useridsVo = generateUserid();
		UseridToWxidVo useridToWxidVo = null;
		if (useridsVo != null) {
			useridToWxidVo = new UseridToWxidVo();
			useridToWxidVo.setId(ServerBo.getInstance().createId());
			useridToWxidVo.setCreate_time(useridsVo.getCreate_time());
			useridToWxidVo.setUserid(useridsVo.getUserid());
			useridToWxidVo.setWxid(wxid);
			ServerBo.getInstance().getDaoFactory().getUseridToWxidDao().save(useridToWxidVo);
		} else {
			throw new IdGenException();
		}

		log.info("bind:wxid={},userid={}", new Object[] { wxid, useridsVo.getUserid() });
		return useridToWxidVo;
	}

	public void unbindUseridByWxid(String wxid) {
		log.info("unbind:wxid={}", new Object[] { wxid });
	}
}
