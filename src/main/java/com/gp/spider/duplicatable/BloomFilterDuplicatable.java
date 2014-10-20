package com.gp.spider.duplicatable;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class BloomFilterDuplicatable implements Duplicatable {

	final Logger logger = LoggerFactory.getLogger(getClass());
	final BloomFilter<CharSequence> bloomfilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), 99999, 0.001);

	public void add(String target) {
		bloomfilter.put(target);
	}

	public boolean isInclude(String target) {
		return bloomfilter.mightContain(target);
	}


}
