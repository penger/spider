package com.gp.spider.store;

import java.io.File;


import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gp.spider.base.Page;

public class FileStore implements Storable {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	private String basePath;
	private int flag = 1;
	
	public FileStore() {
		this(".", 1);
	}
	
	public FileStore(String basePath) {
		this(basePath, 1);
	}
	
	public FileStore(String basePath, int flag) {
		this.basePath = basePath;
		this.flag = flag;
		checkExist();
	}
	private void checkExist() {
		final File dir = new File(basePath);
		if(!dir.exists()) {
			dir.mkdirs();
		}else {
			if(!dir.isDirectory()) {
				dir.delete();
				dir.mkdirs();
			}
		}
	}
	@Override
	public void store(Page page) {
		String valuesToJSON = page.valuesToJSON();
		try {
			FileUtils.write(new File(basePath+File.pathSeparator+fileName()), valuesToJSON);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String fileName(){
		switch(flag){
		case 1:
			return DateUtils.formatDate(new Date(), "yyyy-MM-dd-HH-mm-ss-SSS");
			default:
			return null;
		}
	}

}
