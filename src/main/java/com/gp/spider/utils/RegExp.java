package com.gp.spider.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExp {

	/**
	 * 返回匹配的字符串
	 * @param express
	 * @param content
	 * @return
	 */
	public static String parse(String express,String content){
		Pattern pattern = Pattern.compile(express);
		Matcher matcher = pattern.matcher(content);
		if(matcher.find()){
			return matcher.group(0);
		}
		return null;
	}
	
	public static void main(String[] args) {
		String parse = RegExp.parse("\\d{2}", "abc233dfdsdf2345");
		System.out.println(parse);
	}
}
