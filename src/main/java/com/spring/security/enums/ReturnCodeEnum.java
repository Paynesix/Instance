package com.spring.security.enums;

/**
 * Created by Enzo Cotter on 20-4-16.
 */
public enum ReturnCodeEnum {
    SUCCESS(0, "成功"),
    FAILED(1,"失败"),
    ERROR(99,"未知错误");

    private Integer code;
    private String message;
    ReturnCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
