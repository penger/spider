package com.gp.spider.utils;

public class Sleeper {
	public static void sleep(long milliSeconds) {
		try {
			Thread.currentThread().sleep(milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public static void main(String[] args) {
		Sleeper.sleep(3000);

	}

}
