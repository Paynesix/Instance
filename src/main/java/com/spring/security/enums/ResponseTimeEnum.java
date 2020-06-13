package com.spring.security.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description ResponseTimeEnum
 * @Author xy
 * @Date 2020/6/1 11:05
 * @Version 1.0
 * @Since JDK 1.8
 */
public enum ResponseTimeEnum {

    LESS1("less1Second", "<1s"),
    BETWEEN1T3("less3Second","1-3s"),
    BETWEEN3T10("less10Second","3-10s"),
    THAN10("than10Second","超过10s");

    private String code;
    private String message;
    ResponseTimeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseTimeEnum toEnum(String code){
        for (ResponseTimeEnum typeEnum : values()) {
            if(StringUtils.equals(typeEnum.getCode(),code)){
                return typeEnum;
            }
        }
        return null;
    }

    public static String nameOf(String code){
        for (ResponseTimeEnum typeEnum : values()) {
            if(StringUtils.equals(typeEnum.getCode(),code)){
                return typeEnum.getMessage();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
