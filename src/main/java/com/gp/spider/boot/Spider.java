package com.gp.spider.boot;

import java.awt.image.ConvolveOp;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gp.spider.base.Page;
import com.gp.spider.download.Downloadable;
import com.gp.spider.download.HttpClientDownload;
import com.gp.spider.duplicatable.BloomFilterDuplicatable;
import com.gp.spider.duplicatable.Duplicatable;
import com.gp.spider.process.Processable;
import com.gp.spider.repository.MemoryRepository;
import com.gp.spider.repository.Repository;
import com.gp.spider.store.ConsoleStore;
import com.gp.spider.store.Storable;
import com.gp.spider.utils.Sleeper;

public class Spider {
	final Logger logger = LoggerFactory.getLogger(getClass());
	private Config config=new Config();
	private Downloadable downloadable=new HttpClientDownload();
	private Processable processable;
	private Storable storable = new ConsoleStore();
	private Repository repository = new MemoryRepository();
	private Duplicatable duplicatable = new BloomFilterDuplicatable();
	private ThreadPool threadPool;
	
	/**
	 * 单独运行
	 */
	public void runLocal(){
		while(true){
			String url = repository.pool();
			if(url==null){
				logger.info("已经解析完了,等待中...");
			}else{
				//下载
				Page page = downloadable.download(url);
				//解析
				processable.process(page);
				//目标url放入到队列中
				List<String> targetUrls = page.getTargetUrls();
				for (String target : targetUrls) {
					if(duplicatable.isInclude(target)){
						continue;
					}
					duplicatable.add(target);
					if(target.startsWith("http://list.jd.com/")){
						repository.addHigh(target);
					}else{
						repository.addLow(target);
					}
				}
				storable.store(page);
			}
			Sleeper.sleep(1000);
		}
	}
	
	public void start(){
		check();
		initComponent();
		while(!Thread.currentThread().isInterrupted()){
			final String url = repository.pool();
			if(url==null	){
				logger.info("任务已经完成,等待新任务");
			}else{
				threadPool.run(new Runnable() {
					
					@Override
					public void run() {
						//下载
						Page page = downloadable.download(url);
						//解析
						processable.process(page);
						//目标url放入到队列中
						List<String> targetUrls = page.getTargetUrls();
						for (String target : targetUrls) {
							if(duplicatable.isInclude(target)){
								continue;
							}
							duplicatable.add(target);
							if(target.startsWith("http://list.jd.com/")){
								repository.addHigh(target);
							}else{
								repository.addLow(target);
							}
						}
						storable.store(page);
					}
				});
			}
			Sleeper.sleep(1000);
		}
	}

	private void check() {
		if(processable==null){
			String errorMessage="请设置爬虫";
			logger.error(errorMessage);
			throw new RuntimeException(errorMessage);
		}
	}
	
	private void initComponent() {
		threadPool = new FixedThreadPool(this.config.getThreadPoolSize());
		
//		logger.info("=====================================================");
//		logger.info("downloadable是{}", getDownloadable().getClass().getName());
//		logger.info("processable是{}", getProcessable().getClass().getName());
//		logger.info("storable是{}", getProcessable().getClass().getName());
//		logger.info("repository是{}", getRepository().getClass().getName());
//		logger.info("duplicatable是{}", getDuplicatable().getClass().getName());
//		logger.info("threadPool是{}", getThreadPool().getClass().getName());
//		logger.info("=====================================================");
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Downloadable getDownloadable() {
		return downloadable;
	}

	public void setDownloadable(Downloadable downloadable) {
		this.downloadable = downloadable;
	}

	public Processable getProcessable() {
		return processable;
	}

	public void setProcessable(Processable processable) {
		this.processable = processable;
	}

	public Storable getStorable() {
		return storable;
	}

	public void setStorable(Storable storable) {
		this.storable = storable;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public Duplicatable getDuplicatable() {
		return duplicatable;
	}

	public void setDuplicatable(Duplicatable duplicatable) {
		this.duplicatable = duplicatable;
	}

	public ThreadPool getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPool threadPool) {
		this.threadPool = threadPool;
	}
	
	
}
