package com.gp.spider.repository;

public interface Repository {

	public String pool();
	public void addLow(String url);
	public void  addHigh(String url);
}
