package com.spring.security.tools;

import java.lang.reflect.Field;
import java.util.*;

public class MapTool {
    private static final org.apache.logging.log4j.Logger log =
            org.apache.logging.log4j.LogManager.getLogger(MapTool.class);
    
    public MapTool() {
    }

    public static <K, V> Map<K, V> getMap(Class<K> keyType, Class<V> valueType, Object... params) {
        Map<K, V> map = new HashMap();
        if (params == null) {
            return map;
        } else {
            for(int n = 0; n < params.length / 2; ++n) {
                map.put(keyType.cast(params[2 * n]), valueType.cast(params[2 * n + 1]));
            }

            return map;
        }
    }

    public static Map<String, Object> getSOMap(Object... params) {
        Map<String, Object> map = new HashMap();
        if (params == null) {
            return map;
        } else {
            for(int n = 0; n < params.length / 2; ++n) {
                map.put(String.class.cast(params[2 * n]), params[2 * n + 1]);
            }

            return map;
        }
    }

    public static Map<String, String> getSSMap(Object... params) {
        Map<String, String> map = new HashMap();
        if (params == null) {
            return map;
        } else {
            for(int n = 0; n < params.length / 2; ++n) {
                map.put((String)((String)params[2 * n]), (String)params[2 * n + 1]);
            }

            return map;
        }
    }

    public static <K, V> Map<K, V> getLinkedMap(Class<K> keyType, Class<V> valueType, Object... params) {
        Map<K, V> result = new LinkedHashMap();
        if (params == null) {
            return result;
        } else {
            for(int n = 0; n < params.length / 2; ++n) {
                result.put(keyType.cast(params[2 * n]), valueType.cast(params[2 * n + 1]));
            }

            return result;
        }
    }

    public static <K, V> Map<K, V> listObjToMap(List<?> list, Class<?> dataType, Class<K> keyType, String keyName, Class<V> valueType, String valueName) throws RuntimeException {
        Map<K, V> resultMap = new HashMap();
        if (list != null && list.size() != 0) {
            try {
                Field keyF = ReflectTool.getField(dataType, keyName);
                Field valueF = ReflectTool.getField(dataType, valueName);
                Iterator var9 = list.iterator();

                while(var9.hasNext()) {
                    Object obj = var9.next();
                    resultMap.put(keyType.cast(keyF.get(obj)), valueType.cast(valueF.get(obj)));
                }

                return resultMap;
            } catch (Exception var11) {
                log.error(var11.getMessage(), var11);
                throw new RuntimeException(var11.getMessage());
            }
        } else {
            return resultMap;
        }
    }

    public static <K, V> Map<K, V> listMapToMap(List<Map<?, ?>> list, Class<K> keyType, String keyName, Class<V> valueType, String valueName) throws RuntimeException {
        Map<K, V> resultMap = new HashMap();
        if (list != null && list.size() != 0) {
            try {
                Iterator var6 = list.iterator();

                while(var6.hasNext()) {
                    Map<?, ?> obj = (Map)var6.next();
                    resultMap.put(keyType.cast(obj.get(keyName)), valueType.cast(obj.get(valueName)));
                }

                return resultMap;
            } catch (Exception var8) {
                log.error(var8.getMessage(), var8);
                throw new RuntimeException(var8.getMessage());
            }
        } else {
            return resultMap;
        }
    }

}
