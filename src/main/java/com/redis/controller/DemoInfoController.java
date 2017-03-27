package com.redis.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.redis.service.DemoInfoService;
import com.redis.service.impl.RedisCacheService;


/**
 * 
 * 测试类.
 * 
 * 
 * @version v.0.1
 */

@RestController
@RequestMapping("/redis")
public class DemoInfoController {

	@Autowired
	DemoInfoService demoInfoService;
	
	@Autowired
	RedisCacheService redisCacheService;

	@RequestMapping("/test")
	public String test() {
//		redisCacheService.init();
		redisCacheService.set("reKey:"+UUID.randomUUID(), UUID.randomUUID().toString());
		return "reKey ok";
	}

	@RequestMapping("/delete")
	public @ResponseBody String delete(long id) {
		demoInfoService.deleteFromCache(id);
		return "ok";
	}

	@RequestMapping("/test1")
	public @ResponseBody String test1() {
		demoInfoService.test();
		System.out.println("DemoInfoController.test1()");
		return "ok";
	}
	
	
//	@RequestMapping("ab")
//	public String ab(){
//		jedis.set("name","minxr");//向key-->name中放入了value-->minxr   
//		System.out.println(jedis.get("name"));//执行结果：minxr   
//	}

}
