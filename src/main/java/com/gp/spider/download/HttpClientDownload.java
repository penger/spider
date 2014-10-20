package com.gp.spider.download;

import java.text.SimpleDateFormat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gp.spider.base.Page;

public class HttpClientDownload implements Downloadable {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	public Page download(String url) {
		Page page = new Page();
		page.setUrl(url);
		String rawHtml = execute(url);
		page.setRawHtml(rawHtml);
		return page;
	}
	
	public String execute(String url) {
		String rawHtml = null;
		//构造HttpClient
		final HttpClientBuilder builder = HttpClients.custom();
		//Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36
//		builder.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)");
		builder.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.124 Safari/537.36");
		final CloseableHttpClient httpClient = builder.build();
		
		CookieStore basicCookieStore = new BasicCookieStore();
		Cookie cookie =new BasicClientCookie2();
		cookie.
		basicCookieStore.addCookie(cookie);
		
		Builder custom = RequestConfig.custom();
		HttpHost proxy = new HttpHost("172.27.18.80",8080);
		custom.setProxy(proxy);
		String cookieSpec="SSUDB=VRU1gyQmpEQjkzb0JydDZJRUVCbTZ-Zk1RRlBFTXJTLXBwR2gtSzFSfjlySDlSQVFBQUFBJCQAAAAAAAAAAAEAAACSODEMZ29uZ3BlbmdsbHBwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP0fWFH9H1hRfj; xdisk=y6EA0SoGxssVTXhW0zT2SunOs9xarr1L; atk=3.be2c12d39e9a64832d188c48c370978d.2592000.1377223015.2737963469-418972; H_PS_TIPFLAG=O; H_PS_TIPCOUNT=2; BDUSS=UJPS2JzVWdJLUdXUHhuRTItWnM2TUxIUzFyVnljODZpbEJZWHQ5dDRVakJTdjFUQVFBQUFBJCQAAAAAAAAAAAEAAACSODEMZ29uZ3BlbmdsbHBwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMG91VPBvdVTY; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; BDRCVFR[NtQ5J6PUyHm]=UeZgb9h16FmpL9Bmy-bIi4WUvY; BDRCVFR[OVOzLHN3HUT]=9xWipS8B-FspA7EnHc1QhPEUf; shifen[10866015299_92220]=1409895639; bdshare_firstime=1410240765985; __tbclk=aHR0cDovL3d3dy5iYWlkdS5jb20vbGluaz91cmw9QXhWQ19SN0x6TFVsSlhtN3luWENKZWZxTUtwd3Y4SVNKcUQ1cGo5QUFqWVFyNXBYdzZ4TU0xdlhvbDI0alE0dThfQXdMdV8zVmpwT0t0M2pocUVKOHE=; BAIDUID=ECDC3326FEEB07CBDA6078B46E40B8F7:FG=1; shifen[8905603359_55466]=1410838727; BDSFRCVID=6TPsJeC62ma41hRxQzwa-jFgl2AvHYoTH6aIOgNcGpwtC2FY2qFsEG0PtvlQpYD-LGC_ogKK0mOTHUvP; H_BDCLCKID_SF=JJkJoKIbJIvbfP0k5b5HhRtW-q5jetcyf5PLVPOF5l8-hC-mMtccM650Mq63e4Tl5bPHa-bO3K5xOKQpytr20JtFhGOM0PIJt2uqofcN3KJmfhC9bT3v5DrX-N-t2-biWbRL2MbdQ4QmbCL6j5-BDT3M5pJfeJ3HKCoeLbj--TrEDIvIQfR5y4LdjG5N2jbdJeLq_RTLQ668sR0mjJDM-j3y3-Aq5xc0-N6e5Ru25hb8MKOtj65AQfbQ0hOuqP-jW5ILoJ8bJR7JOpkxhfnxyhLB0aCDJ6-Dtb4tV-35b5rsHI8CDKTjhPrM5fQlbMT-0bFfKhn8Wn3KM66F5fnY5f_A3G5i-U0jJGn7_Jjx3JOPSU8wMloS5fAZh4ryyMQxtNRJ0DnjtpvhKfQwjPjobUPUDMJ9LUkqJG4E_C_2JD0WhKv65nt_K44HbN-qJI62aKDsoR7Y-hcqEIL4WlbkD4k7jxnMWh4L2ecM_I5oaM7zSMbSj4Qz5hFJMlO9a5QK2en3-hvJMp5nhMJmXj7JDMP0qJ7734oy523ion6vQpnlfxtuj60aej3XjGRf-b-XaC52QJ7babQMqPbvK4__-P4DqxbXqMQq057Z0l8KtqOBeq5jDTjx-6KNqf6lK-AHW237obrmWIQHDU-6Mju-BTFkbMCJ5-5uWDT4KKJxbPQSVtJXQKcGeq0ehUJiB5OLBan7-4QxfD_WhKK6j6K35n-Wqlr2-PoH5CoKsJOOaCvEDInRy4oTj6DlbRb4KxTOQ5npaT6_3J3ShlvJyIcD3MvB-fnrQxjhy6T4of78JPQ8VljMQft20b0BhbQjXx0L2DnJ3b7jWhk2Dq72yho-05TBDGK8Jjkef5vfL5rsabnobKPk-PnVeTK70hbZKxtqtDjK_IjSLtnGJ43pqt6PbP7XLqj7X4RnWncKal7jBJOnq-3mLT5PWjDLQlb405OTbTcQb4Jw3T6dspO3hPJvyT8DXnO7LR5TfD7H3KC-JK0-hf5; MCITY=-%3A; BAIDUPSID=ECDC3326FEEB07CBDA6078B46E40B8F7; H_PS_BDC=1; H_PS_645EC=edc3Nv28FfIUE%2F6j1mSTuOQbIL1s3ygCvSfPBZF5jW2fseW9b76pBqix8d4vgGVgCoiV; BD_CK_SAM=1; BD_HOME=1; H_PS_PSSID=7734_9009_1452_7802_9144_6506_6018_8593_7825_9106_8940_7799_8767_7962_9192_8973_9051_9023_9188_9195_8458; BD_UPN=123143; Hm_lvt_3d143f0a07b6487f65609d8411e5464f=1411370329,1411435530,1411621173,1413338769; Hm_lpvt_3d143f0a07b6487f65609d8411e5464f=1413440376";
		custom.setCookieSpec(cookieSpec);
		custom.set
		RequestConfig requestConfig = custom.build();
		
		final HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		
		CloseableHttpResponse response = null;
		final long start = System.currentTimeMillis();
		try {
			//处理response
			response = httpClient.execute(httpGet);
			final HttpEntity entity = response.getEntity();
			if(entity!=null) {
                rawHtml = EntityUtils.toString(entity);  
                EntityUtils.consume(entity);  
			}
			logger.info("解析{}成功,耗时{}毫秒", url, (System.currentTimeMillis()-start));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("解析{}失败,耗时{}毫秒", url, (System.currentTimeMillis()-start));
		} finally {
			try {
				if(response!=null)response.close();
				if(httpClient!=null)httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rawHtml;
	}
	
	public static void main(String[] args) {
		HttpClientDownload httpClientDownload = new HttpClientDownload();
		String execute=null;
		execute = httpClientDownload.execute("http://www.csdn.net");
		System.out.println(execute);
//		execute = httpClientDownload.execute("http://www.jd.com");
//		System.out.println(execute);
//		execute = httpClientDownload.execute("http://10.6.159.96:9085/solr/#/~java-properties");
//		System.out.println(execute);
	}

}
