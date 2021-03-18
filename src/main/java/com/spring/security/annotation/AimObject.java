package com.spring.security.annotation;

import java.lang.annotation.*;

/**
 * @program: springBootStudy
 * @Date: 2020/7/10 20:54
 * @Author: Inxu
 * @Description:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface AimObject {
    Class value() ;
}

