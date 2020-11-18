package com.spring.security.tools;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author sunChangXing
 * @create 2018-11-07 15:05
 * @desc 时间日期类
 **/
public class DateTimeUtil {
    private static Logger LOG = LoggerFactory.getLogger(DateTimeUtil.class);
    /**
     * yyyyMMdd HH:mm:s 格式
     */
    public final static String FRIST_FORMAT_TYPE = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyyMMdd HH:mm:ss
     */
    public final static String SECOND_FORMAT_TYPE = "yyyyMMdd HH:mm:ss";

    /**
     * yyyyMMddHHmmss
     */
    public final static String THIRD_FORMAT_TYPE = "yyyyMMddHHmmss";

    /**
     * YYYYMMDD HH:MM:SS
     */
    public final static String FOURTH_FORMAT_TYPE = "YYYYMMDD HH:MM:SS";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public final static String FIFTH_FORMAT_TYPE = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyyMMdd
     */
    public final static String SIXTH_FORMAT_TYPE = "yyyyMMdd";

    /**
     * yyyyMM
     */
    public final static String SEVENTH_FORMAT_TYPE = "yyyyMM";


    /**
     * yyyy/MM/dd
     */
    public final static String EIGHT_FORMAT_TYPE = "yyyy/MM/dd";



    /**
     * 使用参数Format格式化Date成字符串
     *
     * @param date    日期
     * @param pattern 格式
     * @return
     */
    public static String format(Date date, String pattern) {
        if (Objects.isNull(date)) {
            return null;
        }
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 获取当前日期时间
     * @param pattern
     * @return
     */
    public static String getCurrentTime(String pattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);//设置日期格式
            return df.format(new Date());// new Date()为获取当前系统时间
        } catch (Exception e) {
            LOG.error("e:{}", e);
        }
        return null;
    }

    /**
     * 验证订单数据类型
     * @param date
     * @param pattern
     * @return
     */
    public static boolean vaildDateFormat(String date, String pattern) {
        if (StringUtils.isBlank(date) || StringUtils.isBlank(pattern)) {
            return false;
        }
        boolean b = true;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            format.setLenient(false);
            format.parse(date);
        } catch (ParseException e) {
            b = false;
        }
        return b;
    }




    /**
     * 格式化时间字符串
     *
     * @param dateStr 日期时间
     * @param pattern 格式
     * @return
     */
    public static Date formatToDate(String dateStr, String pattern) {
        if (Objects.isNull(dateStr)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            LOG.error("DateTimeUtil.formatToDate, dateStr={}, pattern={}, e={}", dateStr, pattern, e);
            return null;
        }
    }

    /**
     * 格式化时间字符串
     * @param dateStr
     * @param currentPattern
     * @param targetPattern
     * @return
     */
    public static String formatToStr(String dateStr, String currentPattern, String targetPattern) {
        Date date = formatToDate(dateStr, currentPattern);
        return format(date, targetPattern);
    }


}
