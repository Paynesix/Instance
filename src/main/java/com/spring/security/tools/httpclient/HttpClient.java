package com.spring.security.tools.httpclient;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class HttpClient {
    private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);


    /**
     * 向目的URL发送post请求
     * @param url       目的url
     * @param jsonObject    发送的参数
     * @return  AdToutiaoJsonTokenData
     */
    public static String sendPostRequest(String url, JSONObject jsonObject){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toJSONString(), headers);
        String result = restTemplate.postForObject(url, formEntity, String.class);
        return result;
    }

    /**
     * 向目的URL发送post请求
     * @param url       目的url
     * @param jsonObject    发送的参数
     * @return  AdToutiaoJsonTokenData
     */
    public static String sendFormPostRequest(String url, MultiValueMap jsonObject){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded");
        headers.setContentType(type);
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(jsonObject, headers);
        String result = restTemplate.postForObject(url, formEntity, String.class);
        return result;
    }


    /**
     * 向目的URL发送post请求
     * @param url       目的url
     * @param jsonObject    发送的参数
     * @return  AdToutiaoJsonTokenData
     */
    public static ResponseEntity<Resource> downloadPostRequest(String url, MultiValueMap jsonObject){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded");
        headers.setContentType(type);
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(jsonObject, headers);
        ResponseEntity<Resource> entity = restTemplate.postForEntity(url, formEntity, Resource.class);
        return entity;
    }

    /**
     * 向目的URL发送get请求
     * @param url       目的url
     * @param params    发送的参数
     * @param headers   发送的http头，可在外部设置好参数后传入
     * @return  String
     */
    public static String sendGetRequest(String url, MultiValueMap<String, String> params, HttpHeaders headers){
        RestTemplate client = new RestTemplate();

        HttpMethod method = HttpMethod.GET;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);

        return response.getBody();
    }
}
