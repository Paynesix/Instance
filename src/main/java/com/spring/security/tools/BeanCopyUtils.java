package com.spring.security.tools;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 对象复制
 * @author HuYaHui
 * @date: 2019年4月1日
 */
public class BeanCopyUtils {
    // 使用WeakHashMap缓存,在内存不足时会自动释放
    private final static Map<String, BeanCopier> BEAN_COPIER_MAP = new WeakHashMap<>();
    private final static Map<String, Converter> CONVERTER_MAP = new WeakHashMap<>();
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();
    private BeanCopyUtils(){}
    /**
     * 创建BeanCopier，并缓存
     * @param source
     * @param target
     * @param useConverter
     * @return
     */
    private static BeanCopier getBeanCopier(Object source, Object target, boolean useConverter) {
        String key = generateKey(source,target,useConverter);
        BeanCopier bc = BEAN_COPIER_MAP.get(key);
        if (null == bc) {
            synchronized (lock1) {
                bc = BEAN_COPIER_MAP.get(key);
                if (null == bc) {
                    bc = BeanCopier.create(source.getClass(),target.getClass(),useConverter);
                    BEAN_COPIER_MAP.put(key,bc);
//                    System.out.println("Create BeanCopier with key:" + key);
                }
            }
        }
        return bc;
    }
    /**
     * 复制对象属性
     * @param src
     * @param target
     */
    public static void copy(Object src,Object target) {
        BeanCopier bc = getBeanCopier(src, target, false);
        bc.copy(src,target,null);
    }

    /**
     * 使用自定义的属性转换器复制对象属性
     * @param source
     * @param target
     * @param converter
     */
    public static void copy(Object source, Object target, Converter converter) {
        BeanCopier bc = getBeanCopier(source,target,true);
        bc.copy(source,target,converter);
    }
    /**
     * 对象属性复制，只复制fields中指定的属性，每个属性用逗号分隔
     * @param src
     * @param target
     * @param fields
     */
    public static void copyWithFields(Object src,Object target,final String fields) {
        BeanCopier bc = getBeanCopier(src,target,true);
        bc.copy(src, target, newConverter(src,target,fields,true));
    }
    /**
     * 对象属性复制，排除指定属性
     * @param src
     * @param target
     * @param fields
     */
    public static void copyWithoutFields(Object src,Object target,final String fields) {
        BeanCopier bc = getBeanCopier(src,target,true);
        bc.copy(src, target, newConverter(src,target,fields,false));
    }
    /**
     * new属性转换器，
     * @param fields 需要复制或排除的属性
     * @param fieldCopyFlag 属性复制标识 true:表明fields为需要复制的属性；false:表明fields是需要排除复制的属性
     * @return
     */
    private static Converter newConverter(Object src, Object target, final String fields, final boolean fieldCopyFlag) {
        String key = buildConverterkey(src,target,fields,fieldCopyFlag);
        Converter converter = CONVERTER_MAP.get(key);
        if (null == converter) {
            synchronized (lock2) {
                converter = CONVERTER_MAP.get(key);
                if (null == converter) {
                    converter = new Converter() {
                        @SuppressWarnings("rawtypes")
                        @Override
                        public Object convert(Object fieldValue, Class fieldType, Object methodName) {
                            String field = methodName.toString().substring(3); // 得到属性名，如Name
                            field = field.substring(0,1).toLowerCase() + field.substring(1); // 将首字母小写
                            if ((fieldCopyFlag && fields.contains(field)) || (!fieldCopyFlag && !fields.contains(field))) {
                                return fieldValue;
                            }
                            return null;
                        }
                    };
                    CONVERTER_MAP.put(key,converter);
                    System.out.println("Created Converter with key:" + key);
                }
            }
        }
        return converter;
    }
    private static String generateKey(Object src,Object target,boolean useConverter) {
        return src.getClass().getName() + target.getClass().getName() + String.valueOf(useConverter);
    }
    private static String buildConverterkey(Object src,Object target,String fields,boolean copyFlag) {
        String baseKey = generateKey(src,target,true);
        String key = baseKey + fields + String.valueOf(copyFlag);
        return key;
    }
}
