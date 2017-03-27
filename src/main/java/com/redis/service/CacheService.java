package com.redis.service;

/**
 * 描述: 缓存服务
 */
public interface CacheService {
	
	/**
	 * 描述: 存储一个字符串 
	 */
	public void set(String key, String value);
	
	/**
	 * 描述: 获得一个值
	 */
	public <T> T get(String key);
	
	/**
	 * 描述: 存储一个字符串, 在指定时间后删除
	 */
	public void set(String key, String value, int seconds);
	
	/**
	 * 描述：存储一个字符串，返回旧值
	 */
	public String getSet(String key, String value);
	
	/**
	 * 描述: 删除一个键值对
	 */
	public void remove(String key);
	
	/**给某个存储的值累加1**/
	public void incr(String key);
}
