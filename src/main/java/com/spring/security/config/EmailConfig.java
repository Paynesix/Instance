package com.spring.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    public static String HOST;
    public static Integer PORT;
    public static String USERNAME;
    public static String PASSWORD;
    public static String EMAIL_FORM;
    public static String TIME_OUT;
    public static String PROTOCOL;
    public static String AUTH;

    @Value("${mail.smtp.auth}")
    public void setAUTH(String AUTH) {
        EmailConfig.AUTH = AUTH;
    }
    @Value("${mail.transport.protocol}")
    public void setPROTOCOL(String PROTOCOL) {
        EmailConfig.PROTOCOL = PROTOCOL;
    }
    @Value("${mailTimeout}")
    public void setTimeOut(String TIME_OUT) {
        EmailConfig.TIME_OUT = TIME_OUT;
    }
    @Value("${mailFrom}")
    public void setEmailForm(String EMAIL_FORM) {
        EmailConfig.EMAIL_FORM = EMAIL_FORM;
    }
    @Value("${mailHost}")
    public void setHOST(String HOST) {
        EmailConfig.HOST = HOST;
    }
    @Value("${mailPort}")
    public void setPORT(Integer PORT) {
        EmailConfig.PORT = PORT;
    }
    @Value("${mailUsername}")
    public void setUSERNAME(String USERNAME) {
        EmailConfig.USERNAME = USERNAME;
    }
    @Value("${mailPassword}")
    public void setPASSWORD(String PASSWORD) {
        EmailConfig.PASSWORD = PASSWORD;
    }
}
