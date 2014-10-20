package com.gp.spider.utils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.htmlcleaner.HtmlCleaner;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtils {

	public static String parseFromUrl(String url){
		try {
			return (new HtmlCleaner()).clean(new URL(url)).getText().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String parseJSONArrayIndex0(String jsonString,String key){
		JSONArray jsonArray = new JSONArray(jsonString);
		JSONObject object = (JSONObject) jsonArray.get(0);
		return object.getString(key);
	}
	/**
	 * 将map转换为json串
	 * @param values
	 * @return
	 */
	public static String parseMap(Map<String,String> values){
		JSONObject jsonObject = new JSONObject();
		Set<Entry<String, String>> entrySet = values.entrySet();
		for (Entry<String, String> entry : entrySet) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}
		return jsonObject.toString();
	}
	
	public static void main(String[] args) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("a", "b");
		hashMap.put("aa", "bb");
		String parseString = JSONUtils.parseMap(hashMap);
		System.out.println(parseString);
		System.out.println(JSONUtils.parseJSONArrayIndex0(parseString, "a"));
	}
}
