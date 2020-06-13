package com.spring.security.tools.httpclient;

import org.apache.http.client.protocol.HttpClientContext;

import java.util.HashMap;
import java.util.Map;

/**
 * http请求信息对象
 * @author HuYaHui
 *
 */
public class HTTPEntity {
	//head
	private Map<String,String> headers=new HashMap<String,String>();
	//post参数
	private Map<String,String> map;
	private String jsonParam;
	//url
	private String url;
	//urls
	private String[] urls;
	//字符
	private String chareset="UTF-8";
	//超时时间(毫秒)
	private int HTTP_TIMEOUT=60000;
	//代理ip
	private String proxyIp;
	//代理端口
	private int proxyPort;
	private HttpClientContext context;

	//默认不打印异常
	private boolean printExceptionLog=true;

	public HTTPEntity() {
		super();
	}
	public HTTPEntity(String url,String jsonParam) {
		super();
		this.url=url;
		this.jsonParam = jsonParam;
	}


	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public boolean isPrintExceptionLog() {
		return printExceptionLog;
	}
	public void setPrintExceptionLog(boolean printExceptionLog) {
		this.printExceptionLog = printExceptionLog;
	}
	public HTTPEntity(String url) {
		super();
		this.url = url;
	}
	//get请求构造对象
	public HTTPEntity(String url, String chareset,String cookie) {
		super();
		this.url = url;
		this.chareset = chareset;
		Map<String, String> _headers=new HashMap<String, String>();
		_headers.put("Cookie", cookie);
		this.headers=_headers;

	}

	public String getJsonParam() {
		return jsonParam;
	}
	public void setJsonParam(String jsonParam) {
		this.jsonParam = jsonParam;
	}

	public HTTPEntity(Map<String, String> map, String url, String chareset,
					  int hTTP_TIMEOUT) {
		super();
		this.map = map;
		this.url = url;
		this.chareset = chareset;
		HTTP_TIMEOUT = hTTP_TIMEOUT;
	}
	public HTTPEntity(Map<String, String> map, String url, String chareset,
					  int hTTP_TIMEOUT,String proxyIp,int proxyPort) {
		super();
		this.map = map;
		this.url = url;
		this.chareset = chareset;
		HTTP_TIMEOUT = hTTP_TIMEOUT;
		this.proxyIp=proxyIp;
		this.proxyPort=proxyPort;
	}

	public HTTPEntity(Map<String, String> map, String url, String chareset,
					  int hTTP_TIMEOUT,Map<String,String> headers) {
		super();
		this.map = map;
		this.url = url;
		this.chareset = chareset;
		HTTP_TIMEOUT = hTTP_TIMEOUT;
		this.headers=headers;
	}
	public HTTPEntity(Map<String, String> map, String url, String chareset,
					  int hTTP_TIMEOUT,Map<String,String> headers,String proxyIp,int proxyPort) {
		super();
		this.map = map;
		this.url = url;
		this.chareset = chareset;
		HTTP_TIMEOUT = hTTP_TIMEOUT;
		this.proxyIp=proxyIp;
		this.proxyPort=proxyPort;
		this.headers=headers;
	}
	@Deprecated
	public HTTPEntity(Map<String, String> map, String url, String chareset,
					  int hTTP_TIMEOUT,String proxyIp,int proxyPort,Map<String,String> headers) {
		super();
		this.map = map;
		this.url = url;
		this.chareset = chareset;
		HTTP_TIMEOUT = hTTP_TIMEOUT;
		this.proxyIp=proxyIp;
		this.proxyPort=proxyPort;
		this.headers=headers;
	}

	public HTTPEntity(Map<String, String> headers, Map<String, String> map,
					  String[] urls, String chareset, int hTTP_TIMEOUT, String proxyIp,
					  int proxyPort) {
		super();
		this.headers = headers;
		this.map = map;
		this.urls = urls;
		this.chareset = chareset;
		HTTP_TIMEOUT = hTTP_TIMEOUT;
		this.proxyIp = proxyIp;
		this.proxyPort = proxyPort;
	}
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getChareset() {
		return chareset;
	}
	public void setChareset(String chareset) {
		this.chareset = chareset;
	}
	public int getHTTP_TIMEOUT() {
		return HTTP_TIMEOUT;
	}
	public void setHTTP_TIMEOUT(int hTTP_TIMEOUT) {
		HTTP_TIMEOUT = hTTP_TIMEOUT;
	}
	public String getProxyIp() {
		return proxyIp;
	}
	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}
	public int getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}
	public String[] getUrls() {
		return urls;
	}
	public void setUrls(String[] urls) {
		this.urls = urls;
	}
	public HttpClientContext getContext() {
		return context;
	}
	public void setContext(HttpClientContext context) {
		this.context = context;
	}


}
