package com.spring.security.tools.httpclient;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.SSLContext;
import java.net.ProxySelector;
import java.util.HashMap;
import java.util.Map;

/**
 * @author HuYaHui
 */
public class HttpClientFactory {
	private static final int TIMEOUT=10000;
	public static String USER_AGENT="User-Agent";
	public static String COOKIE="Cookie";
	public static String REFERER="Referer";

	public static Map<String,String> chromeBrowser(){
		Map<String,String> headers=new HashMap<String,String>();
		headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36");
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");
		headers.put("Accept-Encoding", "gzip, deflate");
		headers.put("Cache-Control", "max-age=0");
		return headers;
	}
	
	public static CloseableHttpClient getDefaultHttpClient(String url,RequestConfig config) throws Exception{
		BasicHttpClientConnectionManager cm = new BasicHttpClientConnectionManager(ignoringSSL()); 
	    cm.setSocketConfig(
	    		SocketConfig.custom().
	    			setSoTimeout(config.getSocketTimeout()==0?TIMEOUT:config.getSocketTimeout()).build());
		HttpClientBuilder builder = HttpClients.custom()
	    		.setConnectionManager(cm)
    			.setRoutePlanner(new SystemDefaultRoutePlanner(ProxySelector.getDefault()))
	    		.setDefaultRequestConfig(config);     
		CloseableHttpClient httpClient = builder.build();
		return httpClient;
	}
	
	private static Registry<ConnectionSocketFactory> ignoringSSL() throws Exception{
		SSLContextBuilder builder = SSLContexts.custom();
	    builder.loadTrustMaterial(null, new TrustStrategy(){
			public boolean isTrusted(
					java.security.cert.X509Certificate[] chain,
					String authType)
					throws java.security.cert.CertificateException {
				return true;
			}
	    });
	    SSLContext sslContext = builder.build();
	    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
        sslContext,new NoopHostnameVerifier());
	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
	            .<ConnectionSocketFactory> create()
	            .register("https", sslsf)
	            .register("http", new PlainConnectionSocketFactory())
	            .build();
	    return socketFactoryRegistry;
	}
	public static RequestConfig getRequestConfig(int timeout,
			String proxyIp,int proxyPort){
		Builder requestBuilder = RequestConfig.custom()
				.setSocketTimeout(timeout==0?TIMEOUT:timeout)
				.setConnectTimeout(timeout==0?TIMEOUT:timeout)
				.setConnectionRequestTimeout(timeout==0?TIMEOUT:timeout);
		if(StringUtils.isNotBlank(proxyIp) && proxyPort!=0){
	    	HttpHost proxy = new HttpHost(proxyIp, proxyPort);
	    	return requestBuilder.setProxy(proxy).build();    
	    }else{
			return requestBuilder.build();
	    }
	}
}
