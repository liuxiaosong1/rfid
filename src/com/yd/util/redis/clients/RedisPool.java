package com.yd.util.redis.clients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.yd.util.redis.clients.jedis.Jedis;
import com.yd.util.redis.clients.jedis.JedisPool;
import com.yd.util.redis.clients.jedis.JedisPoolConfig;
import com.yd.util.redis.clients.util.StrUtil;

public class RedisPool {
	
	
	public static JedisPool pool = null;
	public static List<JedisPool> pools = null;
	public static final int JedisPoolSize = 6; //　用于6服务器集群，非切片服务器需要设置为１
	
	public static String defhost;
	public static int defPort;
	public static String host = "127.0.0.1";
	public static int port = 6379; // 分布式服务起始端口，默认6个
	public static int timeout = 0; // 分布式服务起始端口，默认6个

	static {
		Properties pro = new Properties();
		try {
			pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc.properties"));
			defhost = pro.getProperty("yd.redis.de.ip");
			defPort = Integer.parseInt(pro.getProperty("yd.redis.de.redis.port"));
			host = pro.getProperty("yd.redis.distributed.ip");
			port =Integer.parseInt(pro.getProperty("yd.redis.distributed.redis.port"));
			timeout =Integer.parseInt(pro.getProperty("yd.redis.distributed.redis.timeout"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void initPool(){
		JedisPoolConfig config = new JedisPoolConfig();
		pool = new JedisPool(config, defhost, defPort);		
		System.out.println("====Init OK! (RedisPool.pool)");	
	}
	
	public static void initPools(){
		pools = new ArrayList<JedisPool>(JedisPoolSize);
		
		for(int iPool= 0; iPool < JedisPoolSize; iPool++) {
			pools.add(iPool, new JedisPool(new JedisPoolConfig(), host, port+iPool,60000));
		}		
		System.out.println("====Init OK! (RedisPool.pools)");	
	}
	
	public static JedisPool getPool() {
		if (pool == null) {
			initPool();
		}
		return pool;
	}

	public static JedisPool getPool(int i) {
		if (pools == null) {
			initPools();
		}
		return pools.get(i);
	}

	public static void returnResource(JedisPool pool, Jedis redis) {
		if (redis != null) {
			pool.returnResource(redis);
		}
	}

	public static String ds_set(String key, String val) {
		String value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			try {
				value = jedis.ds_set(key, val);
			} catch (Exception e) {
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(pool, jedis);
		}
		if (value == null) {
			value = "";
		}
		return value;
	}

	public static String set(String key, String val) {
		String value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			try {
				value = jedis.set(key, val);
			} catch (Exception e) {
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(pool, jedis);
		}
		if (value == null) {
			value = "";
		}
		return value;
	}
	
	public static long sadd(final String key, final String... members) {
		long value = 0;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			try {
				value = jedis.sadd(key, members);
			} catch (Exception e) {
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(pool, jedis);
		}
		return value;
	}
	
	public static String get(String key) {
		String value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			try {
				value = jedis.get(key);
			} catch (Exception e) {
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(pool, jedis);
		}
		if (value == null) {
			value = "";
		}
		return value;
	}
	

	public static byte[] ds_get(byte[] key) {
		byte[] value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			try {
				value = jedis.ds_get(key);
			} catch (Exception e) {
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(pool, jedis);
		}
		return value;
	}

	public static byte[] ds_getByHash(String key) {
		int iPool = StrUtil.getHash(key,RedisPool.JedisPoolSize);

		byte[] value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool(iPool);
			jedis = pool.getResource();
			try {
				value = jedis.ds_get(key.getBytes()); // 二进制存储，需要通过byte[]查询
			} catch (Exception e) {
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(pool, jedis);
		}
		return value;
	}

	public static String ds_get(String key) {
		String value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			try {
				value = jedis.ds_get(key);
			} catch (Exception e) {
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(pool, jedis);
		}
		if (value == null) {
			value = "";
		}
		return value;
	}
	public static long del(String key) {
		long value = 0;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			try {
				value = jedis.del(key);
			} catch (Exception e) {
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(pool, jedis);
		}
		
		return value;
	}

	public static String ds_getget(String key) {
		String value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			try {
				value = jedis.ds_get(key);
				value = jedis.ds_get(value);
			} catch (Exception e) {
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(pool, jedis);
		}
		if (value == null) {
			value = "";
		}
		return value;
	}

	public static Map<String, String> ds_hgetAll(String key) {
		Map<String, String> value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			try {
				if(key != null && ( !key.isEmpty() ) ) {
					value = jedis.ds_hgetAll(key);
				}
			} catch (Exception e) {
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(pool, jedis);
		}
		return value;
	}

	public static String get2key(String s1, String s2) {
		String value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			try {
				value = jedis.get(s1);
				if (value == null) {
					value = jedis.get(s2);
				}
			} catch (Exception e) {
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			returnResource(pool, jedis);
		}
		if (value == null) {
			value = "";
		}
		return value;
	}
	
	public static void setExpire(String key, int seconds) {
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			try {
				jedis.expire(key, seconds);
			} catch (Exception e) {
				
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			// e.printStackTrace();
		} finally {
			returnResource(pool, jedis);
		}
	}
	
}