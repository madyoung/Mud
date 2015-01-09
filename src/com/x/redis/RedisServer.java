package com.x.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

public class RedisServer {
	private final Logger logger = LoggerFactory.getLogger("redis");

	private JedisPool pool = null;

	private String host;
	private int port;

	public RedisServer(String host, int port, int maxActive, int maxIdle, long maxWait, int timeout) {
		this.host = host;
		this.port = port;
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxActive(maxActive);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWait(maxWait);
		jedisPoolConfig.setTestOnBorrow(false);
		pool = new JedisPool(jedisPoolConfig, host, port, timeout);
	}

	public Jedis getJedis() throws RedisException {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
		} catch (Exception e) {
			logger.error("getJedis. ip:{}, port:{}, error:{}", new Object[] { this.host, this.port, e.getMessage() });
			throw new RedisException(e);
		}
		return jedis;
	}

	public void releaseJedis(Jedis jedis) throws RedisException {
		if (jedis == null) {
			logger.warn("releaseJedis. jedis is null~");
			return;
		}
		try {
			pool.returnResource(jedis);
		} catch (Exception e) {
			logger.error("releaseJedis. ip:{}, port:{}, error:{}", new Object[] { this.host, this.port, e.getMessage() });
			throw new RedisException(e);
		}
	}

	public void releaseBrokenJedis(Jedis jedis) throws RedisException {
		if (jedis == null) {
			logger.warn("releaseBrokenJedis. jedis is null~");
			return;
		}
		try {
			pool.returnBrokenResource(jedis);
		} catch (Exception e) {
			logger.error("releaseBrokenJedis. ip:{}, port:{}, error:{}", new Object[] { this.host, this.port, e.getMessage() });
			throw new RedisException(e);
		}
	}

	public void destroy() {
		if (pool != null) {
			pool.destroy();
			pool = null;
		}
	}

	/**
	 * get keys by pattern
	 * 
	 * @param pattern
	 * @return
	 * @throws RedisException
	 */
	public Set<String> keys(String pattern) throws RedisException {
		Set<String> set = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			set = jedis.keys(pattern);
			logger.debug("redis keys:{},value:{}", new Object[] { pattern, set });
		} catch (Exception e) {
			logger.error("redis keys:{}. err:{}", new Object[] { pattern, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
		return set;
	}

	/**
	 * get value
	 * 
	 * @param key
	 * @return
	 * @throws RedisException
	 * @throws RedisException
	 */
	public String get(String key) throws RedisException {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = jedis.get(key);
			logger.debug("redis get key ={},vaule={}", new Object[] { key, value });
		} catch (Exception e) {
			logger.error("redis get key ={},vaule={}. err:{}", new Object[] { key, value, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
		return value;
	}

	/**
	 * get value
	 * 
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public byte[] get(byte[] key) throws RedisException {
		byte[] value = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = jedis.get(key);
			logger.debug("redis get key ={}", new Object[] { new String(key) });
		} catch (Exception e) {
			logger.error("redis get key ={}. err:{}", new Object[] { new String(key), e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
		return value;
	}

	/**
	 * set value
	 * 
	 * @param key
	 * @param value
	 * @throws RedisException
	 */
	public void set(String key, String value) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
			logger.debug("reids set key ={},vaule={}", new Object[] { key, value });
		} catch (Exception e) {
			logger.error("reids set key ={},vaule={}. err:{}", new Object[] { key, value, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	/**
	 * set value
	 * 
	 * @param key
	 * @param value
	 * @throws RedisException
	 */
	public void set(byte[] key, byte[] value) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
			logger.debug("reids set key ={}", new Object[] { new String(key) });
		} catch (Exception e) {
			logger.error("reids set key ={}. err:{}", new Object[] { new String(key), e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	/**
	 * increase with increment
	 * 
	 * @param key
	 * @param integer
	 * @throws RedisException
	 */
	public void incr(String key, long integer) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.incrBy(key, integer);
			logger.debug("redis incr key ={},integer={}", new Object[] { key, integer });
		} catch (Exception e) {
			logger.error("redis incr key ={},integer={}. err:{}", new Object[] { key, integer, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	/**
	 * set hashMap
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @throws RedisException
	 */
	public void setHash(String key, String field, String value) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.hset(key, field, value);
			logger.debug("redis set hash,key={},field={},value={}", new Object[] { key, field, value });
		} catch (Exception e) {
			logger.error("redis set hash,key={},field={},value={}. err:{}", new Object[] { key, field, value, e });
			this.releaseBrokenJedis(jedis);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	/**
	 * set hashMap
	 * 
	 * @param key
	 * @param hash
	 * @throws RedisException
	 */
	public void setHash(String key, Map<String, String> hash) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.hmset(key, hash);
			logger.debug("redis set hash by map,key={},field={},value={}", new Object[] { key, hash });
		} catch (Exception e) {
			logger.error("redis set hash by map,key={},field={},value={}. err:{}", new Object[] { key, hash, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	/**
	 * get value from hashMap
	 * 
	 * @param key
	 * @param field
	 * @return
	 * @throws RedisException
	 */
	public String getHash(String key, String field) throws RedisException {
		String value = "";
		Jedis jedis = null;
		try {
			jedis = getJedis();
			value = jedis.hget(key, field);
			logger.debug("redis get hash,key={},field={},value={}", new Object[] { key, field, value });
		} catch (Exception e) {
			logger.error("redis get hash,key={},field={},value={}. err:{}", new Object[] { key, field, value, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
		return value;
	}

	/**
	 * get hashMap
	 * 
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public Map<String, String> getAllHash(String key) throws RedisException {
		Map<String, String> map = new HashMap<String, String>();
		Jedis jedis = null;
		try {
			jedis = getJedis();
			map = jedis.hgetAll(key);
			logger.debug("redis get all hash:key={},value={}", new Object[] { key, map });
		} catch (Exception e) {
			logger.error("redis get all hash:key={}. err:{}", new Object[] { key, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}

		return map;
	}

	/**
	 * delete value from hashMap
	 * 
	 * @param key
	 * @param fields
	 * @return
	 * @throws RedisException
	 */
	public long delHash(String key, String... fields) throws RedisException {
		long delNum = 0;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			delNum = jedis.hdel(key, fields);
			logger.debug("redis delete hash key={},fields={}", new Object[] { key, fields });
		} catch (Exception e) {
			logger.error("redis delete hash key={},fields={}. err:{}", new Object[] { key, fields, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
		return delNum;
	}

	/**
	 * increase hashMap with increment
	 * 
	 * @param key
	 * @param field
	 * @param integer
	 * @throws RedisException
	 */
	public void incHash(String key, String field, long integer) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.hincrBy(key, field, integer);
			logger.debug("redis inc hash,key={},field={},integer={}", new Object[] { key, field, integer });
		} catch (Exception e) {
			logger.error("redis inc hash,key={},field={},integer={}. err:{}", new Object[] { key, field, integer, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	/**
	 * set set
	 * 
	 * @param key
	 * @param members
	 * @throws RedisException
	 */
	public void addSet(String key, String... members) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.sadd(key, members);
			logger.debug("redis add set,key={},field={},value={}", new Object[] { key, members });
		} catch (Exception e) {
			logger.error("redis add set,key={},value={}. err:{}", new Object[] { key, members, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	/**
	 * delete value from set
	 * 
	 * @param key
	 * @param members
	 * @throws RedisException
	 */
	public void delSet(String key, String... members) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.srem(key, members);
			logger.debug("redis delete set,key={},value={}", new Object[] { key, members });
		} catch (Exception e) {
			logger.error("redis delete set,key={},value={}. err:{}", new Object[] { key, members, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	/**
	 * delete keys
	 * 
	 * @param keys
	 * @return
	 * @throws RedisException
	 */
	public long delKeys(String... keys) throws RedisException {
		long delNum = 0;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			delNum = jedis.del(keys);
			logger.debug("redis delete keys,key={}", new Object[] { keys });
		} catch (Exception e) {
			logger.error("redis delete keys,key={} err:{}", new Object[] { keys, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
		return delNum;
	}

	/**
	 * delete keys
	 * 
	 * @param keys
	 * @return
	 * @throws RedisException
	 */
	public long delKeys(byte[]... keys) throws RedisException {
		long delNum = 0;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			delNum = jedis.del(keys);
			logger.debug("redis delete keys,key={}", new Object[] { keys });
		} catch (Exception e) {
			logger.error("redis delete keys,key={} err:{}", new Object[] { keys, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
		return delNum;
	}

	public boolean isKeyExist(String key) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.exists(key);
		} catch (Exception e) {
			logger.error("isKeyExist error, key={},err:{}", new Object[] { key, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	/**
	 * push from head
	 * 
	 * @param key
	 * @param value
	 * @throws RedisException
	 */
	public void lpush(String key, String... value) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.lpush(key, value);
			logger.info("lpush info, key={},value:{}", new Object[] { key, value });
		} catch (Exception e) {
			logger.error("lpush error, key={},err:{}", new Object[] { key, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	/**
	 * push from tail
	 * 
	 * @param key
	 * @param value
	 * @throws RedisException
	 */
	public void rpush(String key, String... value) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.rpush(key, value);
			logger.info("rpush info, key={},value:{}", new Object[] { key, value });
		} catch (Exception e) {
			logger.error("rpush error, key={},err:{}", new Object[] { key, e });
			this.releaseBrokenJedis(jedis);
			throw new RedisException(e);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	/**
	 * push from head
	 * 
	 * @param key
	 * @param value
	 * @throws RedisException
	 */
	public void lpush(String key, String value) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.lpush(key, value);
			logger.info("lpush info, key={},value:{}", new Object[] { key, value });
		} catch (Exception e) {
			logger.error("lpush error, key={},err:{}", new Object[] { key, e });
			this.releaseBrokenJedis(jedis);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	/**
	 * pop from head
	 * 
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public String lpop(String key) throws RedisException {
		Jedis jedis = null;
		String rs = null;
		try {
			jedis = getJedis();
			rs = jedis.lpop(key);
			logger.info("lpop info, key={},value:{}", new Object[] { key, rs });
		} catch (Exception e) {
			logger.error("lpop error, key={},err:{}", new Object[] { key, e });
			this.releaseBrokenJedis(jedis);
		} finally {
			this.releaseJedis(jedis);
		}
		return rs;
	}

	/**
	 * pop from tail
	 * 
	 * @param key
	 * @return
	 * @throws RedisException
	 */
	public String rpop(String key) throws RedisException {
		Jedis jedis = null;
		String rs = null;
		try {
			jedis = getJedis();
			rs = jedis.rpop(key);
			logger.info("rpop info, key={},value:{}", new Object[] { key, rs });
		} catch (Exception e) {
			logger.error("rpop error, key={},err:{}", new Object[] { key, e });
			this.releaseBrokenJedis(jedis);
		} finally {
			this.releaseJedis(jedis);
		}
		return rs;
	}

	/**
	 * get value by index
	 * 
	 * @param key
	 * @param index
	 * @return
	 * @throws RedisException
	 */
	public String getIndex(String key, long index) throws RedisException {

		Jedis jedis = null;
		String rs = null;
		try {
			jedis = getJedis();
			rs = jedis.lindex(key, index);
			logger.info("pop info, key={},value:{}", new Object[] { key, rs });
		} catch (Exception e) {
			logger.error("push error, key={},err:{}", new Object[] { key, e });
			this.releaseBrokenJedis(jedis);
		} finally {
			this.releaseJedis(jedis);
		}
		return rs;
	}

	/**
	 * 
	 * delete value from list tail, count=1
	 * 
	 * @param key
	 * @param value
	 * @throws RedisException
	 */
	public void delList(String key, String value) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.lrem(key, -1, value);
			logger.info("delList info, key={},value:{}", new Object[] { key, value });
		} catch (Exception e) {
			logger.error("delList error, key={},err:{}", new Object[] { key, e });
			this.releaseBrokenJedis(jedis);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	/**
	 * delete value from list
	 * 
	 * @param key
	 * @throws RedisException
	 */
	public void delList(String key) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(key);
			logger.info("delList info, key={},value:{}", new Object[] { key });
		} catch (Exception e) {
			logger.error("delList error, key={},err:{}", new Object[] { key, e });
			this.releaseBrokenJedis(jedis);
		} finally {
			this.releaseJedis(jedis);
		}
	}

	public List<String> pop(String key, long start, long end) throws RedisException {
		List<String> list = null;
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Transaction t = jedis.multi();
			Response<List<String>> response = t.lrange(key, start, end - 1);
			t.ltrim(key, end, -1);
			t.exec();
			list = response.get();
			System.out.println(Thread.currentThread().getId() + "=======" + list);
		} catch (Exception e) {
			logger.error("pop error, key={},err:{}", new Object[] { key, e });
			this.releaseBrokenJedis(jedis);
		} finally {
			this.releaseJedis(jedis);
		}
		return list;

	}

	public boolean setExpireTime(String key, int seconds) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			long i = jedis.expire(key, seconds);
			if (1 == i) {
				return true;
			}
		} catch (Exception e) {
			logger.error("setExpireTime error, key={},time={}.err:{}", new Object[] { key, seconds, e });
			this.releaseBrokenJedis(jedis);
		} finally {
			this.releaseJedis(jedis);
		}
		return false;
	}

	public long getlsize(String key) throws RedisException {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.llen(key);
		} catch (Exception e) {
			// logger.error("setExpireTime error, key={},time={}.err:{}", new
			// Object[] { key, seconds, e });
			this.releaseBrokenJedis(jedis);
		} finally {
			this.releaseJedis(jedis);
		}
		return -1;
	}
}
