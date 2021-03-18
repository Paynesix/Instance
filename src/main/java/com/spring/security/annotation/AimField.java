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
@Target(ElementType.FIELD)
public @interface AimField {
    /*目标对象的属性名*/
    String value();
    /*目标对象的属性类型 基本类型 */
    Class type();

}

