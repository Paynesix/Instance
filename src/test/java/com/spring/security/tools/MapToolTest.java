package com.spring.security.tools;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxy
 * @Title:
 * @Description:
 * @date 2020.11.18 19:42
 **/
@SpringBootTest
@Slf4j
public class MapToolTest {

    @Test
    public void map2Json(){
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", "b");
        JSONObject jsonObject = MapTool.map2Json(map);
        log.info("=====================>:{}", jsonObject);
        log.info("=====================>:{}", JSONObject.toJSONString(jsonObject));
        map = new HashMap<>();
        jsonObject = MapTool.map2Json(map);
        log.info("=====================>:{}", jsonObject);
        Assert.assertEquals(map, jsonObject);
    }

    @Test
    public void json2Map(){
        JSONObject json = new JSONObject();
        json.put("a", 1);
        json.put("b", "b");
        Map<String, Object> stringObjectMap = MapTool.json2Map(json);
        log.info("=====================>:{}", stringObjectMap);
        log.info("=====================>:{}", JSONObject.toJSONString(stringObjectMap));
        json = new JSONObject();
        stringObjectMap = MapTool.json2Map(json);
        log.info("=====================>:{}", stringObjectMap);

    }

    @Test
    public void jsonStr2Map(){
        String jsonStr = "";
        Map<String, Object> stringObjectMap = MapTool.jsonStr2Map(jsonStr);
        log.info("=====================>:{}", stringObjectMap);
        log.info("=====================>:{}", JSONObject.toJSONString(stringObjectMap));
        stringObjectMap = MapTool.jsonStr2Map(null);
        log.info("=====================>:{}", stringObjectMap);

    }


}
