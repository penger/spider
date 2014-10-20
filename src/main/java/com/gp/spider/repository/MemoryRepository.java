package com.gp.spider.repository;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemoryRepository implements Repository {
	final Logger logger = LoggerFactory.getLogger(getClass());
	private ConcurrentLinkedQueue<String> high=new ConcurrentLinkedQueue<String>();
	private ConcurrentLinkedQueue<String> low=new ConcurrentLinkedQueue<String>();
	
	public String pool() {
		if(!high.isEmpty()){
			return high.poll();
		}
		if(low.isEmpty()){
			return low.poll();
		}
		return null;
	}

	public void addLow(String url) {
		low.add(url);
	}
	public void addHigh(String url) {
		high.add(url);
	}

}
