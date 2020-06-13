package com.spring.security.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description GranularityEnum
 * @Author xy
 * @Date 2020/6/1 11:05
 * @Version 1.0
 * @Since JDK 1.8
 */
public enum ReportEnum {

    //显示粒度  1日 2周 3月
    ORG_DATA("1", "OCR识别原始数据表"),
    STATISTICS_DATA("2","OCR请求量统计表");

    private String code;
    private String message;
    ReportEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ReportEnum toEnum(String code){
        for (ReportEnum typeEnum : values()) {
            if(StringUtils.equals(typeEnum.getCode(),code)){
                return typeEnum;
            }
        }
        return null;
    }

    public static String nameOf(String code){
        ReportEnum[] values = ReportEnum.values();
        for(ReportEnum enums:values){
            if(enums.getCode().equals(code)){
                return enums.getMessage();
            }
        }
        return new String();
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
