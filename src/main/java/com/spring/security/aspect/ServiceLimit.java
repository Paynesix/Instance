package com.spring.security.aspect;

/**
 * @Description limit
 * @Author xy
 * @Date 2020/4/15 12:16
 * @Version 1.0
 * @Since JDK 1.8
 */
public @interface ServiceLimit {
    String description()  default "";
}
