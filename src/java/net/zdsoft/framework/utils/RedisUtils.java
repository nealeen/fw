package net.zdsoft.framework.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import net.zdsoft.framework.config.FrameworkEvn;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

public class RedisUtils {

	private static Logger logger = Logger.getLogger(RedisUtils.class);

	private static JedisPool pool;

	public static int TIME_FOREEVER = 0;
	public static int TIME_FIVE_SECONDS = 5;
	public static int TIME_HALF_MINUTE = 30;
	public static int TIME_ONE_MINUTE = 60;
	public static int TIME_FIVE_MINUTES = 300;
	public static int TIME_TEN_MINUTES = 600;
	public static int TIME_HALF_HOUR = 1800;
	public static int TIME_ONE_HOUR = 3600;
	public static int TIME_ONE_DAY = 86400;
	public static int TIME_ONE_WEEK = 604800;
	public static int TIME_ONE_MONTH = 2592000;
	public static int TIME_MAX_TIME = 25920000;

	public static String KEY_PREFIX = "eis.v6.";

	// private static ShardedJedisPool shardedJedisPool;

	// 初始化redis客户端信息
	static {
		if (FrameworkEvn.newInstance().isRedisEnable()) {
			// 加载redis配置文件
			ResourceBundle bundle = ResourceBundle.getBundle("conf/redis");
			if (bundle == null) {
				throw new IllegalArgumentException("[redis.properties] is not found!");
			}
			// 创建jedis池配置实例
			JedisPoolConfig config = new JedisPoolConfig();
			// 设置池配置项值
			config.setMaxTotal(100);
			config.setMaxWaitMillis(Integer.valueOf(bundle.getString("redis.maxWait")));
			config.setMaxIdle(Integer.valueOf(bundle.getString("redis.maxIdle")));
			config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.testOnBorrow")));
			// 根据配置实例化jedis池
			pool = new JedisPool(config, bundle.getString("redis.ip"), Integer.valueOf(bundle.getString("redis.port")));

			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					check();
				}
			}, 10000, 60000);
		}
	}

	public static Jedis getJedis() {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis;
		} catch (Exception e) {
		}
		return jedis;
	}

	public static Jedis getJedis(Jedis jedis) {
		if (jedis != null)
			return jedis;
		try {
			jedis = pool.getResource();
			return jedis;
		} catch (Exception e) {
		}
		return jedis;
	}

	public static boolean check() {
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = getJedis();
			jedis.get("");
		} catch (JedisException e) {
			try {
				logger.warn("redis服务异常，重新连接……");
				// 加载redis配置文件
				ResourceBundle bundle = ResourceBundle.getBundle("conf/redis");
				if (bundle == null) {
					throw new IllegalArgumentException("[redis.properties] is not found!");
				}
				// 创建jedis池配置实例
				JedisPoolConfig config = new JedisPoolConfig();
				// 设置池配置项值
				config.setMaxTotal(Integer.valueOf(bundle.getString("redis.maxActive")));
				config.setMaxWaitMillis(Integer.valueOf(bundle.getString("redis.maxWait")));
				config.setMaxIdle(Integer.valueOf(bundle.getString("redis.maxIdle")));
				config.setTestOnBorrow(Boolean.valueOf(bundle.getString("redis.testOnBorrow")));
				// 根据配置实例化jedis池
				pool = new JedisPool(config, bundle.getString("redis.ip"), Integer.valueOf(bundle.getString("redis.port")));
			} catch (Exception e1) {
				return false;
			}
			success = false;
		} finally {
			returnResource(jedis, success);
		}
		return true;
	}

	public static String get(String key, int time, RedisInterface<String> ri) {
		return get(null, key, time, ri);
	}

	public static String get(Jedis jedis, String key, int time, RedisInterface<String> ri) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return ri.queryData();
		final String keyf = KEY_PREFIX + key;
		byte[] json = get(jedis, keyf.getBytes());
		if (json == null) {
			String t = ri.queryData();
			if (t == null)
				return null;

			byte[] bytes = SerializationUtils.serialize(t);
			if (time > 0)
				set(jedis, keyf.getBytes(), bytes, time);
			else
				set(jedis, keyf.getBytes(), bytes);
			return t;
		} else {
			return SerializationUtils.deserialize(json);
		}
	}

	public static <T> T getObject(String key, int time, RedisInterface<T> ri) {
		return getObject(null, key, time, 0, ri);
	}

	public static <T> T getObject(Jedis jedis, String key, int time, RedisInterface<T> ri) {
		return getObject(jedis, key, time, 0, ri);
	}

	public static <T> T getObject(String key, int time, int maxCount, RedisInterface<T> ri) {
		return getObject(null, key, time, maxCount, ri);
	}

	public static <T> T getObject(Jedis jedis, String key, int time, int maxCount, RedisInterface<T> ri) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return ri.queryData();
		final String keyf = KEY_PREFIX + key;
		byte[] json = get(jedis, keyf.getBytes());
		if (json == null) {
			T t = ri.queryData();
			if (maxCount > 0) {
				if (CollectionUtils.size(t) > maxCount) {
					return t;
				}
			}
			if (t == null)
				return null;

			byte[] bytes = SerializationUtils.serialize(t);
			if (time > 0)
				set(jedis, keyf.getBytes(), bytes, time);
			else
				set(jedis, keyf.getBytes(), bytes);
			return t;
		} else {
			return SerializationUtils.deserialize(json);
		}
	}

	public static List<String> getList(String key, RedisInterface<List<String>> ri) {
		return getList(null, key, ri);
	};

	public static List<String> getList(Jedis jedis, String key, RedisInterface<List<String>> ri) {
		final String keyf = KEY_PREFIX + key;
		List<String> ids = lrange(keyf);
		if (CollectionUtils.isEmpty(ids)) {
			ids = ri.queryData();
			if (ids != null) {
				lpush(keyf, ids.toArray(new String[0]));
			}
		}
		return ids;
	};

	public static Set<String> getSet(String key, RedisInterface<Set<String>> ri) {
		return getSet(null, key, ri);
	};

	public static Set<String> getSet(Jedis jedis, String key, RedisInterface<Set<String>> ri) {
		Set<String> ids = smembers(jedis, key);
		if (CollectionUtils.isEmpty(ids)) {
			ids = ri.queryData();
			if (ids != null) {
				sadd(jedis, key, ids.toArray(new String[0]));
			}
		}
		return ids;
	};

	public static <T> T getObject(String key, RedisInterface<T> ri) {
		return getObject(key, 0, ri);
	}

	public static <T> T getObject(Jedis jedis, String key, RedisInterface<T> ri) {
		return getObject(jedis, key, 0, ri);
	}

	public static String get(String key, RedisInterface<String> ri) {
		return get(key, 0, ri);
	}

	public static String get(Jedis jedis, String key, RedisInterface<String> ri) {
		return get(jedis, key, 0, ri);
	}

	/**
	 * @deprecated 这个效率较差，不用
	 * @param key
	 * @param redisTemplate
	 * @return
	 */
	public static <T> String get(final String key, final RedisTemplate<String, T> redisTemplate) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return null;

		String cache = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] keys = redisTemplate.getStringSerializer().serialize(key);
				byte[] value = conn.get(keys);
				return redisTemplate.getStringSerializer().deserialize(value);
			}
		});
		return cache;
	}

	/**
	 * @deprecated 效率较差，不用
	 * @param key
	 * @param value
	 * @param time
	 * @param redisTemplate
	 */
	public static <T> void set(String key, final String value, final int time, final RedisTemplate<String, T> redisTemplate) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return;
		final String keyf = KEY_PREFIX + key;
		redisTemplate.execute(new RedisCallback<T>() {
			@Override
			public T doInRedis(RedisConnection conn) throws DataAccessException {
				byte[] keys = redisTemplate.getStringSerializer().serialize(keyf);
				byte[] values = redisTemplate.getStringSerializer().serialize(value);
				conn.set(keys, values);
				if (time > 0) {
					redisTemplate.expire(keyf, time, TimeUnit.SECONDS);
				}
				return null;
			}
		});
	}

	/**
	 * 查找
	 * 
	 * @param key
	 * @param value
	 */
	public static Set<String> keys(String key) {
		return keys(null, key);
	}

	public static Set<String> keys(Jedis jedis, String key) {
		final String keyf = KEY_PREFIX + key;
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return new HashSet<String>();
		Jedis jedis2 = getJedis(jedis);
		boolean success = true;
		try {
			return jedis2.keys(keyf);
		} catch (JedisException e) {
			logger.error("jedis set error", e);
			success = false;
			return new HashSet<String>();
		} finally {
			if (jedis == null)
				returnResource(jedis2, success);
		}
	}

	/**
	 * 单次存储
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(String key, String value) {
		set(key, value, 0);
	}

	public static void set(Jedis jedis, String key, String value) {
		set(jedis, key, value, 0);
	}

	public static <T> void setObject(String key, T t, int seconds) {
		setObject(null, key, t, seconds);
	}

	public static <T> void setObject(Jedis jedis, String key, T t, int seconds) {
		byte[] bytes = SerializationUtils.serialize(t);
		if (seconds > 0)
			set(jedis, (KEY_PREFIX + key).getBytes(), bytes, seconds);
		else
			set(jedis, (KEY_PREFIX + key).getBytes(), bytes);
	}

	public static <T> void setObject(String key, T t) {
		setObject(null, key, t);
	}

	public static <T> void setObject(Jedis jedis, String key, T t) {
		setObject(jedis, key, t, 0);
	}

	private static void set(Jedis jedis, byte[] key, byte[] value) {
		set(jedis, key, value, 0);
	}

	/**
	 * 保存数据
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *                保存时间，单位：秒
	 */
	public static void set(String key, String value, int seconds) {
		set(null, key, value, seconds);
	}

	public static void set(Jedis jediso, String key, String value, int seconds) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return;
		final String keyf = KEY_PREFIX + key;
		Jedis jedis = getJedis(jediso);
		boolean success = true;
		try {
			if (seconds > 0)
				jedis.setex(keyf, seconds, value);
			else
				jedis.set(keyf, value);
		} catch (JedisException e) {
			logger.error("jedis set error", e);
			success = false;
		} finally {
			if (jediso == null)
				returnResource(jedis, success);
		}
	}

	private static void set(Jedis jediso, byte[] key, byte[] value, int seconds) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return;
		Jedis jedis = getJedis(jediso);
		boolean success = true;
		try {
			if (seconds > 0)
				jedis.setex(key, seconds, value);
			else
				jedis.setex(key, TIME_MAX_TIME, value);
		} catch (JedisException e) {
			logger.error("jedis set error", e);
			success = false;
		} finally {
			if (jediso == null)
				returnResource(jedis, success);
		}
	}

	/**
	 * 添加hash对象
	 * 
	 * @param key
	 * @param hash
	 */
	public static void setHash(String key, Map<String, String> hash) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return;
		final String keyf = KEY_PREFIX + key;
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = getJedis();
			jedis.hmset(keyf, hash);
		} catch (JedisException e) {
			logger.error("jedis setHash error", e);
			success = false;
		} finally {
			returnResource(jedis, success);
		}
	}

	/**
	 * 设置hash中某个域的值(已存在的会覆盖)
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void hset(String key, String field, String value) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return;
		final String keyf = KEY_PREFIX + key;
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = getJedis();
			jedis.hset(keyf, field, value);
		} catch (JedisException e) {
			logger.error("jedis setHashField error", e);
			success = false;
		} finally {
			returnResource(jedis, success);
		}
	}

	public static <T> void hsetObject(String key, String field, T t) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return;
		final String keyf = KEY_PREFIX + key;
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = getJedis();
			jedis.hset(keyf.getBytes(), field.getBytes(), SerializationUtils.serialize(t));
		} catch (JedisException e) {
			logger.error("jedis setHashField error", e);
			success = false;
		} finally {
			returnResource(jedis, success);
		}
	}

	/**
	 * 批量删除hash域中多个值
	 * 
	 * @param key
	 * @param fields
	 */
	public static void hdel(String key, String... fields) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return;
		final String keyf = KEY_PREFIX + key;
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = getJedis();
			jedis.hdel(keyf, fields);
		} catch (JedisException e) {
			logger.error("jedis batchRemoveHashField error", e);
			success = false;
		} finally {
			returnResource(jedis, success);
		}
	}

	/**
	 * 获取hash中某个域的值
	 * 
	 * @param key
	 * @param field
	 */
	public static String hget(String key, String field) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return null;
		final String keyf = KEY_PREFIX + key;
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = getJedis();
			return jedis.hget(keyf, field);
		} catch (JedisException e) {
			logger.error("jedis getHashField error", e);
			success = false;
			return null;
		} finally {
			returnResource(jedis, success);
		}
	}

	public static <T> T hgetObject(String key, String field) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return null;
		final String keyf = KEY_PREFIX + key;
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = getJedis();
			return SerializationUtils.deserialize(jedis.hget(keyf.getBytes(), field.getBytes()));
		} catch (JedisException e) {
			logger.error("jedis getHashField error", e);
			success = false;
			return null;
		} finally {
			returnResource(jedis, success);
		}
	}

	/**
	 * 判断hash中的某个域是否存在
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public static boolean hexists(String key, String field) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return false;
		final String keyf = KEY_PREFIX + key;
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = getJedis();
			return jedis.hexists(keyf, field);
		} catch (JedisException e) {
			logger.error("jedis checkHExists error", e);
			success = false;
			return false;
		} finally {
			returnResource(jedis, success);
		}
	}

	/**
	 * 将名称为key的hash中field的value增加integer
	 * 
	 * @param key
	 * @param field
	 * @param value
	 */
	public static void hincrby(String key, String field, int value) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return;
		final String keyf = KEY_PREFIX + key;
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = getJedis();
			jedis.hincrBy(keyf, field, value);
		} catch (JedisException e) {
			logger.error("jedis hincrby error", e);
			success = false;
			return;
		} finally {
			returnResource(jedis, success);
		}
	}

	@SuppressWarnings("deprecation")
	public static void returnResource(Jedis jedis, boolean success) {
		if (jedis == null)
			return;
		if (!success) {
			pool.returnBrokenResource(jedis);
		} else {
			pool.returnResource(jedis);
		}
	}

	@SuppressWarnings("deprecation")
	public static void returnResource(Jedis jedis) {
		if (jedis == null)
			return;
		pool.returnResource(jedis);
	}

	/**
	 * 从redis获取key对应的value值
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return get(null, key);
	}

	public static String get(Jedis jediso, String key) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return null;
		Jedis jedis = getJedis(jediso);
		boolean success = true;
		try {
			return jedis.get(KEY_PREFIX + key);
		} catch (JedisException e) {
			logger.error("jedis set error", e);
			success = false;
		} finally {
			if (jediso == null)
				returnResource(jedis, success);
		}
		return null;
	}

	private static byte[] get(Jedis jediso, byte[] key) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return null;
		Jedis jedis = getJedis(jediso);
		boolean success = true;
		try {
			return jedis.get(key);
		} catch (JedisException e) {
			logger.error("jedis set error", e);
			success = false;
		} finally {
			if (jediso == null)
				returnResource(jedis, success);
		}
		return null;
	}

	private static byte[] get(byte[] key) {
		return get(null, key);
	}

	/**
	 * 获取对象，如果是字符型，请用get方法
	 * 
	 * @param key
	 * @return
	 */
	public static <T> T getObject(String key) {
		String keyf = KEY_PREFIX + key;
		byte[] bytes = get(keyf.getBytes());
		return SerializationUtils.deserialize(bytes);
	}

	public static <T> T getObject(Jedis jedis, String key) {
		String keyf = KEY_PREFIX + key;
		byte[] bytes = get(jedis, keyf.getBytes());
		return SerializationUtils.deserialize(bytes);
	}

	/**
	 * 批量删除key对应的value值
	 * 
	 * @param keys
	 * @return
	 */
	public static long del(String... keys) {
		return del(null, keys);
	}

	public static long del(Jedis jediso, String... keys) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return 0;
		Jedis jedis = getJedis(jediso);
		boolean success = true;
		try {
			List<String> list = new ArrayList<String>();
			for (String s : keys) {
				if (StringUtils.startsWith(s, KEY_PREFIX))
					list.add(s);
				else
					list.add(KEY_PREFIX + s);
			}
			return jedis.del(list.toArray(new String[0]));
		} catch (JedisException e) {
			logger.error("jedis remove error", e);
			success = false;
			return 0;
		} finally {
			if (jediso == null)
				returnResource(jedis, success);
		}
	}

	public static long delBeginWith(String keyMatcher) {
		Set<String> keys = keys(keyMatcher + "*");
		return del(keys.toArray(new String[0]));
	}

	/**
	 * 数值增加value , -1表示失败
	 * 
	 * @param key
	 * @param value
	 * 
	 */
	public static Long incrby(String key, int value) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return -1L;
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = getJedis();
			return jedis.incrBy(KEY_PREFIX + key, value);
		} catch (JedisException e) {
			logger.error("jedis incrby error", e);
			success = false;
			return -1L;
		} finally {
			returnResource(jedis, success);
		}
	}

	/**
	 * 设置过期时间
	 * 
	 * @param key
	 * @param seconds
	 */
	public static void expire(String key, int seconds) {
		expire(null, key, seconds);
	}

	public static void expire(Jedis jediso, String key, int seconds) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return;
		Jedis jedis = getJedis(jediso);
		boolean success = true;
		try {
			jedis.expire(KEY_PREFIX + key, seconds);
		} catch (JedisException e) {
			logger.error("jedis expire error", e);
			success = false;
			return;
		} finally {
			if (jediso == null)
				returnResource(jedis, success);
		}
	}

	/**
	 * 数值减少value
	 * 
	 * @param key
	 * @param value
	 */
	public static Long decrby(String key, int value) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return -1L;
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = getJedis();
			return jedis.decrBy(KEY_PREFIX + key, value);
		} catch (JedisException e) {
			logger.error("jedis decrby error", e);
			success = false;
			return -1L;
		} finally {
			returnResource(jedis, success);
		}
	}

	/**
	 * 列表长度
	 * 
	 * @param key
	 * @return
	 */
	public static Long llen(String key) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return -1L;
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = getJedis();
			return jedis.llen(KEY_PREFIX + key);
		} catch (JedisException e) {
			logger.error("jedis lpush error", e);
			success = false;
			return -1L;
		} finally {
			returnResource(jedis, success);
		}
	}

	/**
	 * 在list的头部添加数据
	 * 
	 * @param key
	 * @param values
	 */
	public static void lpush(String key, String... values) {
		lpush(null, key, values);
	}

	public static void lpush(Jedis jediso, String key, String... values) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return;
		boolean success = true;
		Jedis jedis = getJedis(jediso);
		try {
			jedis.lpush(KEY_PREFIX + key, values);
		} catch (JedisException e) {
			logger.error("jedis lpush error", e);
			success = false;
		} finally {
			if (jediso == null)
				returnResource(jedis, success);
		}
	}

	/**
	 * 在list的尾部添加数据
	 * 
	 * @param key
	 * @param values
	 */
	public static void rpush(String key, String... values) {
		rpush(null, key, values);
	}

	public static void rpush(Jedis jediso, String key, String... values) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return;
		Jedis jedis = getJedis(jediso);
		boolean success = true;
		try {
			jedis.rpush(KEY_PREFIX + key, values);
		} catch (JedisException e) {
			logger.error("jedis rpush error", e);
			success = false;
		} finally {
			if (jediso == null)
				returnResource(jedis, success);
		}
	}

	public static List<String> lrange(String key) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return new ArrayList<String>();
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = getJedis();
			return jedis.lrange(KEY_PREFIX + key, 0, -1);
		} catch (JedisException e) {
			logger.error("jedis get error", e);
			success = false;
			return null;
		} finally {
			returnResource(jedis, success);
		}
	}

	/**
	 * 添加元素member
	 * 
	 * @param key
	 * @param member
	 */
	public static void sadd(String key, String... members) {
		sadd(null, key, members);
	}

	public static void sadd(Jedis jediso, String key, String... members) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return;
		Jedis jedis = getJedis(jediso);
		boolean success = true;
		try {
			jedis.sadd(KEY_PREFIX + key, members);
		} catch (JedisException e) {
			logger.error("jedis sadd error", e);
			success = false;
			return;
		} finally {
			if (jediso == null)
				returnResource(jedis, success);
		}
	}

	public static void srem(String key, String... members) {
		srem(null, key, members);
	}

	public static void srem(Jedis jediso, String key, String... members) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return;
		Jedis jedis = getJedis(jediso);
		boolean success = true;
		try {
			jedis.srem(KEY_PREFIX + key, members);
		} catch (JedisException e) {
			logger.error("jedis sadd error", e);
			success = false;
			return;
		} finally {
			if (jediso == null)
				returnResource(jedis, success);
		}
	}

	/**
	 * 获取key对应的所有元素
	 * 
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key) {
		return smembers(null, key);
	}

	public static Set<String> smembers(Jedis jediso, String key) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return new HashSet<String>();
		Jedis jedis = getJedis(jediso);
		boolean success = true;
		try {
			return jedis.smembers(KEY_PREFIX + key);
		} catch (JedisException e) {
			logger.error("jedis smembers error", e);
			success = false;
			return null;
		} finally {
			if (jediso == null)
				returnResource(jedis, success);
		}
	}

	/**
	 * 返回key中所对应的所有键及对应值
	 * 
	 * @param key
	 */
	public static Map<String, String> hgetAll(String key) {
		return hgetAll(null, key);
	}

	public static Map<String, String> hgetAll(Jedis jediso, String key) {
		if (!FrameworkEvn.newInstance().isRedisEnable())
			return new HashMap<String, String>();
		Jedis jedis = getJedis(jediso);
		boolean success = true;
		try {
			jedis = getJedis();
			return jedis.hgetAll(KEY_PREFIX + key);
		} catch (JedisException e) {
			logger.error("jedis hgetall error", e);
			success = false;
			return null;
		} finally {
			if (jediso == null)
				returnResource(jedis, success);
		}
	}
}
