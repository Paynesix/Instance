/** 
 * @(#)HTTPResponse.java 1.0.0 2017年2月7日 上午9:46:52  
 *  
 * Copyright © 2017 善林金融.  All rights reserved.  
 */ 

package com.spring.security.tools.httpclient;

import java.util.Map;

/**   
 * 
 *	HTTP请求响应内容对象  
 * @author  HuYaHui
 * @version $Revision:1.0.0, $Date: 2015年2月7日 上午9:46:52 $ 
 */
public class HTTPResponse {
	private Map<String,String> headers;
	private String responseStr;
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public String getResponseStr() {
		return responseStr;
	}
	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}
	@Override
	public String toString() {
		return "HTTPResponse [headers=" + headers + ", responseStr=" + responseStr + "]";
	}
	
}
