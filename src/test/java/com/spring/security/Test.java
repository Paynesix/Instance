package com.spring.security;

import com.alibaba.fastjson.JSONObject;
import com.spring.security.enums.BatchChannelEnum;
import com.spring.security.tools.EnumUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Hutengfei
 * @Description:
 * @Date Create in 2019/9/3 15:17
 */
public class Test {
    public static void main(String[] args) throws ParseException {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        System.out.println(encoder.encode("123456"));
//        // $2a$10$t.KHXk9REl/OL/1xJeeOneDtJeDkZHHEFHQ4olHLBHRcQFgMin81G
//        // $2a$10$UJDzmy6czjhlDUJKwFhD2.bTLGnfOFOE6pCLoVbKY3jya6Xd80S06
//        // $2a$10$YWNcsAajGcvYsshiH1/8zODwBj.2.h9vwaOQp3CPgvx5sGRkqjfpm
//        String s = File.separator;
//        String ss = System.getProperty("line.separator");
//        String sss = System.lineSeparator();
//        System.out.println(s);
//        System.out.println(ss + "==========");
        testDateUtils();
    }

    public void enumTests(){
        Class<BatchChannelEnum> clazz = BatchChannelEnum.class;
        Map<Object, String> enumToMap = EnumUtils.EnumToMap(clazz);
        List<JSONObject> recordList = enumToMap.entrySet().stream().map(o->{
            JSONObject item = new JSONObject();
            item.put("batchChannel", o.getKey());
            item.put("batchChannelName", o.getValue());
            return item;
        }).collect(Collectors.toList());

        recordList.stream().forEach(o-> System.out.println("=============>:" + o.toJSONString()));
    }

    /**
     * 字符串的日期格式的计算
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String smdate,String bdate) throws ParseException{

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();

        cal.setTime(sdf.parse(smdate));

        long time1 = cal.getTimeInMillis();

        cal.setTime(sdf.parse(bdate));

        long time2 = cal.getTimeInMillis();

        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));

    }

    public static void testDateUtils() throws ParseException {
        String begindate = "2021-01-15 00:00";//开始日期
        String enddate = "2021-01-17 00:00";//结束日期
        int i = daysBetween(begindate, enddate);
        System.out.println("====> i ：" + i);
    }
}
