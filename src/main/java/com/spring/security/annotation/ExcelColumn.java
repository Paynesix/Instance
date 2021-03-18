package com.spring.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel导入对应模板
 *
 * @auther: zhuweirong
 * @date: 2021\1\21 0021 19:50
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {

    /**
     * 对应excel的列号，从0开始
     */
    int index();

    /**
     * 是否必填
     */
    boolean required() default false;

    /**
     * 列名
     */
    String name() default "";

    /**
     * 对应字段类型，用于校验与设值就
     */
    Class fieldType() default String.class;

    /**
     * 是否数据重复检测的标识，支持多个字段
     */
    boolean dataUniqueKey() default false;

}
