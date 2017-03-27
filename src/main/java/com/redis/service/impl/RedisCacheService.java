package com.redis.service.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.redis.service.CacheService;

/**
 * 描述: 基于Redis实现的缓存服务 
 */
@Service
public class RedisCacheService implements CacheService {
	
//	@Value("${spring.redis.host}")
	private String host = "localhost";
	
//	@Value("${spring.redis.port}")
	private int port = 6397;
	
//	@Value("${spring.redis.password}")
	private String password = "admin";
	
	private JedisPool jedisPool;
	
//	Jedis jedis = new Jedis("localhost", 6379);
	
		

	
//	public RedisCacheService(){
//		jedis = new Jedis("localhost", 6379);
//		jedis.auth("admin");
//	}
	
	@PostConstruct
	public void init() {
		LOG.info("获得Redis配置参数: {} - {}", host, port);
	
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setJmxEnabled(true);
		jedisPoolConfig.setJmxNamePrefix("jedis");
		jedisPoolConfig.setMinIdle(0);
		jedisPoolConfig.setMaxIdle(10);
		jedisPoolConfig.setMaxTotal(50);
		jedisPoolConfig.setBlockWhenExhausted(true);
		
		if(password != null && !password.trim().equals("")) {
			jedisPool = new JedisPool(jedisPoolConfig, host, port, 800, password);
		} else {
			jedisPool = new JedisPool(jedisPoolConfig, host, port);
		}
	}

	@Override
	public void set(String key, String value) {
		Jedis jedis = new Jedis("localhost", 6379);///.getResource();
		jedis.auth("admin");
		jedis.set(key, value);
		jedis.close();
	}
	
	public String getSet(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String oldValue = jedis.getSet(key, value);
		jedis.close();
		
		return oldValue;
	}
	
	public void set(String key, String value, int seconds) {
		Jedis jedis = jedisPool.getResource();
		String hasValue = jedis.get(key);
		if (hasValue == null)
			jedis.set(key, value, "NX", "EX", seconds);
		else 
			jedis.set(key, value, "XX", "EX", seconds);
		
		jedis.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key) {
		Object value;
		
		Jedis jedis = jedisPool.getResource();
		value = jedis.get(key);
		jedis.close();
		
		return (T)value;
	}
	
	@Override
	public void remove(String key) {
		Jedis jedis = jedisPool.getResource();
		jedis.del(key);
		jedis.close();
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(RedisCacheService.class);

	@Override
	public void incr(String key) {
		Jedis jedis = jedisPool.getResource();
		//设置value累加1
		jedis.incr(key);
		//设置两秒之后失效
		jedis.expire(key, 2);
		jedis.close();
	}
}
