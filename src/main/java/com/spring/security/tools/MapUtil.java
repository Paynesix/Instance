package com.spring.security.tools;

import com.fasterxml.jackson.databind.JavaType;

import java.util.HashMap;
import java.util.Map;

/**
* @ClassName: MapUtil
* @Description: JavaBean与Map进行互转
* @author qingzheng
* @date 2019年4月23日
*/
public class MapUtil {
	
	private static JsonMapper mapper = JsonMapper.nonEmptyMapper();
	private static JavaType type = mapper.contructMapType(HashMap.class, String.class, Object.class);
	
	/***
     * 将对象转换为传入类型的对象
     * @param
     * @param object
     * @param beanClass
     * @author qingzheng
     * @date 2019年4月23日
     * @return
     */
	public static <T> T toBean(Object object, Class<T> beanClass) {
        return (T) mapper.fromJson(mapper.toJson(object), beanClass);
    }
   
    /***
     * 将对象转换为Map<String,Object>
     * @param object
     * @author qingzheng
     * @date 2019年4月23日
     * @return
     */
    public static Map<String, Object> toMap(Object object) {
		Map<String, Object> map = mapper.fromJson(mapper.toJson(object),type);
        return map;
    }
    
}
