package com.spring.security.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description GranularityEnum
 * @Author xy
 * @Date 2020/6/1 11:05
 * @Version 1.0
 * @Since JDK 1.8
 */
public enum GranularityEnum {
    
    //显示粒度  1日 2周 3月
    DAY("1", "日"),
    WEEKEND("2","周"),
    MONTH("4","月"),
    YEAR("3","年");

    private String code;
    private String message;
    GranularityEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static GranularityEnum toEnum(String code){
        for (GranularityEnum typeEnum : values()) {
            if(StringUtils.equals(typeEnum.getCode(),code)){
                return typeEnum;
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
