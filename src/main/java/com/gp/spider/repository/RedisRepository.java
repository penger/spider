package com.gp.spider.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gp.spider.utils.RedisUtil;

public class RedisRepository implements Repository {
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static String highKey="high";
	public static String lowKey="low";
	
	final RedisUtil redisUtil=new RedisUtil();
	
	public String pool() {
		String lpop = redisUtil.lpop(highKey);
		if(lpop!=null){
			return lpop;
		}
		return redisUtil.lpop(lowKey);
	}
	
	public void addLow(String url) {
		redisUtil.rpush(lowKey, url);
	}
	
	public void addHigh(String url) {
		redisUtil.rpush(highKey, url);

	}

}
