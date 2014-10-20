package com.gp.spider.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gp.spider.base.Page;

public class ConsoleStore implements Storable {
	final Logger log= LoggerFactory.getLogger(getClass());
	public void store(Page page) {
		log.info(page.toString());

	}
	public static void main(String[] args) {
		Page page = new Page();
		page.setUrl("a");
		page.setRawHtml("b");
		ConsoleStore consoleStore = new ConsoleStore();
		consoleStore.store(page);
	}

}
