package com.gp.spider.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.gp.spider.utils.JSONUtils;

public class Page {
	private String url;
	private String rawHtml;
	private Map<String, String> values = new HashMap<String, String>();
	private List<String> targetUrls = new ArrayList<String>();
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRawHtml() {
		return rawHtml;
	}
	public void setRawHtml(String rawHtml) {
		this.rawHtml = rawHtml;
	}
	public Map<String, String> getValues() {
		return values;
	}
	public void setValues(Map<String, String> values) {
		this.values = values;
	}
	public List<String> getTargetUrls() {
		return targetUrls;
	}
	public void setTargetUrls(List<String> targetUrls) {
		this.targetUrls = targetUrls;
	}
	/*
	 * 结果转换为json
	 */
	public String valuesToJSON(){
		return JSONUtils.parseMap(getValues());
	}
	/**
	 * 添加
	 * @param nextUrl
	 */
	public void addTargetUrl(String nextUrl){
		this.targetUrls.add(nextUrl);
	}
	
	public void addField(String key,String value){
		this.values.put(key, value);
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
