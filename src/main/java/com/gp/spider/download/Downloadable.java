package com.gp.spider.download;

import com.gp.spider.base.Page;

public interface Downloadable {

	public abstract Page download(String url);
}
