package com.spring.security.tools.httpclient;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.net.ProxySelector;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Http工具类
 *
 * @author HuYaHui
 */
public class HttpClientUtil {
    private static Log logger = LogFactory.getLog(HttpClientUtil.class);
    public static final String CHARSET_UTF8 = StandardCharsets.UTF_8 + "";
    private static PoolingHttpClientConnectionManager cm = null;

    public static HttpClientConnectionManager getConnectionManager(int i) {
        SSLContextBuilder builder = SSLContexts.custom();
        try {

            builder.loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType)
                        throws java.security.cert.CertificateException {
                    return true;
                }
            });
            SSLContext sslContext = builder.build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext, new NoopHostnameVerifier());
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("https", sslsf)
                    .register("http", new PlainConnectionSocketFactory())
                    .build();
            if (i == 0) {
                if (cm == null) {
                    cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                    // Increase max total connection to 200
                    cm.setMaxTotal(5000);
                    // Increase default max connection per route to 20
                    cm.setDefaultMaxPerRoute(5000);
                    //验证不活动的连接
//					cm.setValidateAfterInactivity(ms);
                }
                return cm;
            } else {
                BasicHttpClientConnectionManager cm = new BasicHttpClientConnectionManager(socketFactoryRegistry);
                return cm;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static {
        try {
            //初始化连接池
            HttpClients.custom()
                    .setConnectionManager(getConnectionManager(0))
                    .setRoutePlanner(new SystemDefaultRoutePlanner(ProxySelector.getDefault()))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建httpclient，可选创建方式
     *
     * @param strategy 0:PoolingHttpClientConnectionManager,1:BasicHttpClientConnectionManager
     * @return
     */
    public static CloseableHttpClient getHttpClient(int strategy) {
        HttpClientConnectionManager hccm = null;
        if (strategy == 0) {
            hccm = cm;
        } else {
            hccm = getConnectionManager(1);
        }
        return HttpClients.custom()
                .setConnectionManager(hccm)
                .setRoutePlanner(new SystemDefaultRoutePlanner(ProxySelector.getDefault()))
                .build();
    }

    public static HTTPResponse postData(HTTPEntity entity) throws Exception {
        return postDataRtnResponse(entity, HTTPResponse.class);
    }

    public static String postStrData(HTTPEntity entity) throws Exception {
        return postDataRtnResponse(entity, String.class);
    }

    public static <T> T postDataRtnResponse(HTTPEntity entity, Class<T> cls) throws Exception {
        return postDataRtnResponse(entity, cls, null, true);
    }

    @SuppressWarnings("unchecked")
    public static <T> T postDataRtnResponse(HTTPEntity entity, Class<T> cls
            , CloseableHttpClient closeableHttpClient, boolean closeClient) throws Exception {
        //参数
        Map<String, String> map = entity.getMap();
        //url
        String url = entity.getUrl();
        //字符
        String chareset = entity.getChareset();
        //超时时间(毫秒)
        int HTTP_TIMEOUT = entity.getHTTP_TIMEOUT();
        //代理ip
        String proxyIp = entity.getProxyIp();
        //代理端口
        int proxyPort = entity.getProxyPort();
        Map<String, String> headers = entity.getHeaders();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = HttpClientFactory.getRequestConfig(HTTP_TIMEOUT, proxyIp, proxyPort);
        httpPost.setConfig(RequestConfig.copy(requestConfig).build());
        CloseableHttpResponse response = null;

        try {
            //设置头信息
            if (headers != null && headers.size() != 0) {
                for (String key : headers.keySet()) {
                    httpPost.setHeader(key, headers.get(key));
                }
            } else {
                Map<String, String> default_headers = HttpClientFactory.chromeBrowser();
                for (String key : default_headers.keySet()) {
                    httpPost.setHeader(key, default_headers.get(key));
                }
            }
            //设置post参数
            if (map != null && map.size() != 0) {

                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (String paramName : map.keySet()) {
                    nvps.add(new BasicNameValuePair(paramName, map.get(paramName)));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, chareset));
            } else if (StringUtils.isNotBlank(entity.getJsonParam())) {
                String contentType = headers.get("Content-Type");
                StringEntity jsonEntity = new StringEntity(entity.getJsonParam(), "utf-8");
                if (StringUtils.isNotBlank(contentType)) {
                    jsonEntity.setContentType(contentType);
                } else {
                    jsonEntity.setContentType("application/json");
                }
                jsonEntity.setContentEncoding("UTF-8");
                httpPost.setEntity(jsonEntity);

            }

            if (closeableHttpClient == null) {
                HttpClientContext context = entity.getContext();
                CloseableHttpClient httpClient = getHttpClient(1);
                if (context == null) {
                    response = httpClient.execute(httpPost);
                } else {
                    response = httpClient.execute(httpPost, context);
                }
            } else {
                response = closeableHttpClient.execute(httpPost);
            }
            HttpEntity entityRep = response.getEntity();
            if (entityRep != null) {
                if (cls.equals(String.class)) {
                    String str = "";
                    if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode() && entityRep != null) {
                        str = EntityUtils.toString(response.getEntity(), StringUtils.isBlank(chareset) ? CHARSET_UTF8 : chareset);
                    } else {
                        logger.debug(url + "连接异常:" + response.getStatusLine().getStatusCode());
                    }
                    response.getEntity().getContent();
                    EntityUtils.consume(response.getEntity());
                    return (T) str;
                } else if (cls.equals(HTTPResponse.class)) {
                    HTTPResponse res = new HTTPResponse();
                    String str = EntityUtils.toString(response.getEntity(), StringUtils.isBlank(entity.getChareset()) ? CHARSET_UTF8 : entity.getChareset());
                    res.setResponseStr(str);

                    Map<String, String> resheaders = new HashMap<>();
                    for (Header h : response.getAllHeaders()) {
                        if (resheaders.containsKey(h.getName())) {
                            String value = resheaders.get(h.getName());
                            resheaders.put(h.getName(), value + "; " + h.getValue());
                        } else {
                            resheaders.put(h.getName(), h.getValue());

                        }
                    }
                    res.setHeaders(resheaders);
                    EntityUtils.consume(response.getEntity());
                    return (T) res;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            if (entity.isPrintExceptionLog()) {
                e.printStackTrace();
            }
        } finally {
            if (closeClient == true && closeableHttpClient != null) {
                closeableHttpClient.close();
            }
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    public static String getData(HTTPEntity entity) throws Exception {
        return getDataRtnResponse(entity, String.class);
    }

    public static <T> T getDataRtnResponse(HTTPEntity entity, Class<T> cls) throws Exception {
        return getDataRtnResponse(entity, cls, null, true);
    }

    public static <T> T getDataRtnResponse(HTTPEntity entity, Class<T> cls,
                                           CloseableHttpClient closeableHttpClient, boolean closeClient) throws Exception {
        //url
        String url = entity.getUrl();
        //字符
        String chareset = entity.getChareset();
        Map<String, String> headers = entity.getHeaders();
        if (StringUtils.isBlank(url)) {
            throw new Exception("url不能为空!");
        }
        long start = System.currentTimeMillis();
        CloseableHttpResponse response = null;

        HttpGet httpGet = new HttpGet(url);
        //超时时间(毫秒)
        int HTTP_TIMEOUT = entity.getHTTP_TIMEOUT();
        //代理ip
        String proxyIp = entity.getProxyIp();
        //代理端口
        int proxyPort = entity.getProxyPort();

        // 设置超时时间跟代理信息
        RequestConfig requestConfig = HttpClientFactory.getRequestConfig(HTTP_TIMEOUT, proxyIp, proxyPort);
        httpGet.setConfig(RequestConfig.copy(requestConfig).build());

        try {
            httpGet.setHeader("Host",
                    new URL(url).getHost());
            if (headers != null && headers.size() != 0) {
                for (String key : headers.keySet()) {
                    httpGet.setHeader(key, headers.get(key));
                }
            } else {
                Map<String, String> default_headers = HttpClientFactory.chromeBrowser();
                for (String key : default_headers.keySet()) {
                    httpGet.setHeader(key, default_headers.get(key));
                }
            }
            if (closeableHttpClient == null) {
                HttpClientContext context = entity.getContext();

                long s = System.currentTimeMillis();
                CloseableHttpClient httpClient = getHttpClient(0);
                if (context == null) {
                    response = httpClient.execute(httpGet);
                } else {
                    response = httpClient.execute(httpGet, context);
                }
            } else {
                response = closeableHttpClient.execute(httpGet);
            }
            HttpEntity entityRep = response.getEntity();
            if (entityRep != null) {
                Header ceheader = entityRep.getContentEncoding();
                if (ceheader != null) {
                    for (HeaderElement element : ceheader.getElements()) {
                        //如果网站有gip压缩，解压
                        if (element.getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                            break;
                        } else if (element.getName().equalsIgnoreCase("deflate")) {
                            response.setEntity(new DeflateDecompressingEntity(response.getEntity()));
                            break;
                        }
                    }
                }


                if (cls.equals(String.class)) {
                    String str = "";
                    if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode() && entityRep != null) {
                        str = EntityUtils.toString(response.getEntity(), StringUtils.isBlank(chareset) ? CHARSET_UTF8 : chareset);
                    }
                    EntityUtils.consume(response.getEntity());
                    return (T) str;
                } else if (cls.equals(HTTPResponse.class)) {
                    HTTPResponse res = new HTTPResponse();
                    String str = EntityUtils.toString(response.getEntity(), StringUtils.isBlank(entity.getChareset()) ? CHARSET_UTF8 : entity.getChareset());
                    res.setResponseStr(str);

                    Map<String, String> resheaders = new HashMap<>();
                    for (Header h : response.getAllHeaders()) {
                        if (resheaders.containsKey(h.getName())) {
                            String value = resheaders.get(h.getName());
                            resheaders.put(h.getName(), value + "; " + h.getValue());
                        } else {
                            resheaders.put(h.getName(), h.getValue());

                        }
                    }
                    res.setHeaders(resheaders);
                    EntityUtils.consume(response.getEntity());
                    return (T) res;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            throw new Exception("http请求获取数据异常，url：" + url + ",耗时：" + (System.currentTimeMillis() - start), e);
        } finally {
            if (closeClient == true && closeableHttpClient != null) {
                closeableHttpClient.close();
            }
            if (response != null) {
                //response.getEntity().getContent().close();
                response.close();
            }
            // 释放连接
            httpGet.releaseConnection();
            httpGet.abort();
        }
        return null;
    }

}
