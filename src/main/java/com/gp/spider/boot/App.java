package com.gp.spider.boot;

import com.gp.spider.duplicatable.BloomFilterDuplicatable;
import com.gp.spider.process.JDProcessable;
import com.gp.spider.repository.MemoryRepository;
import com.gp.spider.store.FileStore;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Spider spider = new Spider();
    	spider.setProcessable(new JDProcessable());
    	spider.setRepository(new MemoryRepository());
    	spider.setDuplicatable(new BloomFilterDuplicatable());
//    	spider.setStorable(new FileStore());
    	spider.getRepository().addHigh("http://www.jd.com");
//    	spider.start();
    	spider.runLocal();
    }
}
