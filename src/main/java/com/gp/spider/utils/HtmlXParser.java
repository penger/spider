package com.gp.spider.utils;

import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class HtmlXParser {
	private HtmlCleaner htmlCleaner=new HtmlCleaner();
	private List<TagNode> tagNodes=new ArrayList<TagNode>();
	private String rawHtml;
	
	public HtmlXParser(String rawHtml){
		this.rawHtml=rawHtml;
	}
	
	public HtmlXParser select(String xpath) {
		final TagNode rootNode = htmlCleaner.clean(rawHtml);
		try {
			final Object[] aArr = rootNode.evaluateXPath(xpath);
			if(aArr!=null && aArr.length>0) {
				for (Object object : aArr) {
					if(object instanceof TagNode) {
						TagNode tag = (TagNode) object;
						tagNodes.add(tag);
					}
				}
			}
		} catch (XPatherException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return this;
	}

	public String text() {
		StringBuffer sb = new StringBuffer();
		for(TagNode tag : tagNodes) {
			sb.append(tag.getText().toString().trim());
			sb.append(",");
		}
		if(sb.length()>0) {
			return sb.substring(0, sb.length()-1);
		}
		return sb.toString();
	}

	public String getAttributeByName(String name) {
		StringBuffer sb = new StringBuffer();
		for(TagNode tag : tagNodes) {
			sb.append(tag.getAttributeByName(name));
			sb.append(",");
		}
		if(sb.length()>0) {
			return sb.substring(0, sb.length()-1);
		}
		return sb.toString();
	}
	
}
