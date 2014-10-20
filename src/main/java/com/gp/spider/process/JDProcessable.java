package com.gp.spider.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gp.spider.base.Page;
import com.gp.spider.utils.HtmlXParser;
import com.gp.spider.utils.JSONUtils;
import com.gp.spider.utils.RegExp;

public class JDProcessable implements Processable {
	final Logger log= LoggerFactory.getLogger(getClass());
	public void process(Page page) {
		String rawHtml = page.getRawHtml();
		if(rawHtml==null){
			return;
		}
		String url = page.getUrl();
		if(url.startsWith("http://item.jd.com/")){
			parseProduct(page,rawHtml,url);
		}
		
		String as=(new HtmlXParser(page.getRawHtml())).select("//a").getAttributeByName("href");
		if(as!=null){
			String[] splits = as.split(",");
			for (String split : splits) {
				if(split.startsWith("javascript:")){
					continue;
				}
				page.addTargetUrl(split);
			}
		}
	}
	private void parseProduct(Page page, String rawHtml, String url) {
		String style = new HtmlXParser(rawHtml).select("//div[@class='breadcrumb']//a").text();
		page.addField("style", style);
		String name = new HtmlXParser(rawHtml).select("//div[@id='name']").text();
		page.addField("name", name);
		//请求的价格
		String skuid = RegExp.parse("\\d+", url);
		String priceUrl="http://p.3.cn/prices/get?skuId=J_"+skuid;
		String priceJSON=JSONUtils.parseFromUrl(priceUrl);
		String price = JSONUtils.parseJSONArrayIndex0(priceJSON, "p");
		page.addField("price", price);
		
	}

}
