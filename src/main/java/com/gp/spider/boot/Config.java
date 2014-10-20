package com.gp.spider.boot;

public class Config {
	private int executeSleep = 200;
	private int threadPoolSize = 1;

	
	public int getExecuteSleep() {
		return executeSleep;
	}

	public void setExecuteSleep(int executeSleep) {
		this.executeSleep = executeSleep;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}
}
