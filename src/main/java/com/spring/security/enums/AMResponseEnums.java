package com.spring.security.enums;


/**
 * Created by xy on 20-4-16.
 */
public enum AMResponseEnums {

    SUCCESS("0", "OK"),
    PARAMS_EMPTY("OCR-P-100","请求参数为空!"),
    SIGNATURE_EMPTY("OCR-P-101","签名为空!"),
    TIMESTAMP_DELAY("OCR-P-102","时间戳超时!"),
    PHOTO_URL_EMPTY("OCR-P-103","照片地址为空!"),
    PARTNER_EMPTY("OCR-P-104","合作商为空!"),

    SIGNATURE_ERROR_VERIFY("OCR-C-200","验签有误!"),
    SIGNATURE_ERROR_ADD("OCR-C-201","加签有误!"),
    NO_MATCH_KEY("OCR-C-202","无匹配的公私钥!"),
    NO_MATCH_PARTNER("OCR-C-203","无匹配的合作商!"),

    PHOTO_RECOGNITION_ERROR("OCR-R-300","商汤图片识别失败!"),
    PHOTO_NUMBER_ERROR("OCR-R-304","图片身份证号识别失败!"),
    PHOTO_NAME_ERROR("OCR-R-305","图片姓名识别失败!"),
    NULL_POINTER_EXCEPTION("OCR-R-301","姓名和身份证号识别失败!"),
    WEIGHT_NAME_SET_ERROR("OCR-R-302","姓名权重设置超过阈值【0.87-0.91】!"),
    WEIGHT_NUMBER_SET_ERROR("OCR-R-303","身份证权重设置超过阈值【0.87-0.91】!"),

    ERROR_PARAMS_FORM("OCR-U-400","请求传参格式错误!"),
    CONNECTION_ERROR("OCR-U-401","网络连接失败!"),
    METHOD_NOT_ALLOWED("OCR-U-402","不合法的请求方式!"),
    NOT_FOUND("OCR-U-404","找不到请求路径!"),

    DATABASE_ERROR("OCR-S-500","数据库异常!"),
    BOUND_STATEMENT_NOT_FOUNT("OCR-S-501","数据库异常!"),

    MD5_CODE("MD5-S-001","MD5编码异常!"),

    PARAMS_EMPTY_ERROR("AI-100","参数为空!"),
    DATE_PERIOD_ERROR("AI-101","日期间隔过长!")/*,
    DATABASE_ERROR("AI-100","数据库异常!"),
    DATABASE_ERROR("AI-100","数据库异常!")*/;

    private String code;
    private String msg;

    AMResponseEnums(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
