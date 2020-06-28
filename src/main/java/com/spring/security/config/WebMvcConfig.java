package com.spring.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 虚拟目录映射
 * @author HuYaHui
 * @date: 2020年4月14日
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${bill.download.filepath}")
    private String locationDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String url="/api/merchant/jsszxybill/file/**";
        registry.addResourceHandler(url).addResourceLocations("file:"+locationDir);
    }
}
