package com.spring.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 统一管理配置中自定义属性，保存到当前对象
 *
 * @author HuYaHui
 * @date: 2019年3月23日
 */
@Configuration
public class Properties {
    
    public static String alipayPostUrl;
    /**
     * 证书地址
     */
    public static String CERT_LOCAL_PATH;
    /**
     * 微信退款url
     */
    public static String wxRefundURL;

    public static Integer alipayConnectTimeout;
    public static Integer alipayReadTimeout;

    @Value("${alipay.connect.timeout}")
    public void setAlipayConnectTimeout(Integer alipayConnectTimeout) {
        Properties.alipayConnectTimeout = alipayConnectTimeout;
    }

    @Value("${alipay.read.timeout}")
    public void setAlipayReadTimeout(Integer alipayReadTimeout) {
        Properties.alipayReadTimeout = alipayReadTimeout;
    }

    @Value("${alipay.post.url}")
    public void setAlipayPostUrl(String alipayPostUrl) {
        Properties.alipayPostUrl = alipayPostUrl;
    }

    @Value("${wx.merchant.key.file.path}")
    public void setCERT_LOCAL_PATH(String cERT_LOCAL_PATH) {
        Properties.CERT_LOCAL_PATH = cERT_LOCAL_PATH;
    }

    @Value("${wx.refund.url}")
    public void setWxRefundURL(String wxRefundURL) {
        Properties.wxRefundURL = wxRefundURL;
    }

}
