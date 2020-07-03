package com.spring.security;

import com.alibaba.fastjson.JSONObject;
import com.spring.security.enums.BatchChannelEnum;
import com.spring.security.tools.EnumUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Hutengfei
 * @Description:
 * @Date Create in 2019/9/3 15:17
 */
public class Test {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
        // $2a$10$t.KHXk9REl/OL/1xJeeOneDtJeDkZHHEFHQ4olHLBHRcQFgMin81G
        // $2a$10$UJDzmy6czjhlDUJKwFhD2.bTLGnfOFOE6pCLoVbKY3jya6Xd80S06
        // $2a$10$YWNcsAajGcvYsshiH1/8zODwBj.2.h9vwaOQp3CPgvx5sGRkqjfpm
        String s = File.separator;
        String ss = System.getProperty("line.separator");
        String sss = System.lineSeparator();
        System.out.println(s);
        System.out.println(ss + "==========");
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
}
