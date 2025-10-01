package com.spring.security.tools.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 日期时间工具类
 * 基于Java 8+的java.time包，线程安全
 */
public class DateTimeUtils {

    // 常用日期时间格式
    public static final String PATTERN_FULL = "yyyyMMddHHmmss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final String PATTERN_CHINESE = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 获取当前完整日期时间（年月日时分秒）
     * @return 格式化后的字符串（默认yyyy-MM-dd HH:mm:ss）
     */
    public static String getCurrentDateTime() {
        return format(LocalDateTime.now(), PATTERN_FULL);
    }

    /**
     * 获取当前日期（年月日）
     * @return 格式化后的字符串（默认yyyy-MM-dd）
     */
    public static String getCurrentDate() {
        return format(LocalDate.now(), PATTERN_DATE);
    }

    /**
     * 获取当前时间（时分秒）
     * @return 格式化后的字符串（默认HH:mm:ss）
     */
//    public static String getCurrentTime() {
//        return format(LocalTime.now(), PATTERN_TIME);
//    }

    /**
     * 格式化日期时间
     * @param dateTime 要格式化的LocalDateTime对象
     * @param pattern 格式模板
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null || pattern == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化日期
     * @param date 要格式化的LocalDate对象
     * @param pattern 格式模板
     * @return 格式化后的字符串
     */
    public static String format(LocalDate date, String pattern) {
        if (date == null || pattern == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 字符串解析为LocalDateTime
     * @param dateTimeStr 日期时间字符串
     * @param pattern 格式模板
     * @return LocalDateTime对象
     */
    public static LocalDateTime parseDateTime(String dateTimeStr, String pattern) {
        if (dateTimeStr == null || pattern == null) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 计算两个时间的差值（秒）
     * @param start 开始时间
     * @param end 结束时间
     * @return 秒数差（end - start）
     */
    public static long getSecondsBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.SECONDS.between(start, end);
    }

    /**
     * 时间加减（分钟）
     * @param dateTime 基准时间
     * @param minutes 要加减的分钟数（正数加，负数减）
     * @return 计算后的时间
     */
    public static LocalDateTime plusMinutes(LocalDateTime dateTime, long minutes) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.plusMinutes(minutes);
    }

    /**
     * 日期加减（天数）
     * @param date 基准日期
     * @param days 要加减的天数（正数加，负数减）
     * @return 计算后的日期
     */
    public static LocalDate plusDays(LocalDate date, long days) {
        if (date == null) {
            return null;
        }
        return date.plusDays(days);
    }

    // 测试方法
    public static void main(String[] args) {
        System.out.println("当前完整时间: " + getCurrentDateTime());
        System.out.println("当前日期: " + getCurrentDate());
//        System.out.println("当前时间: " + getCurrentTime());
        System.out.println("中文格式时间: " + format(LocalDateTime.now(), PATTERN_CHINESE));
        
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        System.out.println("明天此时: " + format(tomorrow, PATTERN_FULL));
        
        long seconds = getSecondsBetween(LocalDateTime.now(), tomorrow);
        System.out.println("距离明天此时的秒数: " + seconds);
    }
}
