package com.spring.security.config;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.NDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class Log4jMdcFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        NDC.push(getIpAddr((HttpServletRequest) request) + "-" + maxLengthRandom(18));
        chain.doFilter(request, response);
        NDC.pop();
    }

    public static long maxLengthRandom(int num) {
        return (long) ((Math.random() * (num - 1) + 1) * Math.pow(10, num - 1));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip) && ip.indexOf(",") != -1) {
            ip = ip.split(",")[0];
        }

        return ip;
    }
}
