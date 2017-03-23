package com.wuhen.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {

	@Autowired
	RedisTemplate<String, String> r;
	
	@RequestMapping("/add")
	public String test(){
		r.opsForValue().set("test", "124", 1, TimeUnit.MINUTES);
		return "true";
	}
}
