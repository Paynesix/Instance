package com.spring.security.tools.util;

/**
 * @program: git
 * @description:
 * @author: wxy
 * @create: 2021-03-17 16:53
 * @Version 1.0
 **/

import com.spring.security.annotation.AimField;
import com.spring.security.annotation.AimObject;
import com.spring.security.annotation.AimOther;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.rmi.NoSuchObjectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @program: springBootStudy
 * @Date: 2020/7/10 20:59
 * @Author: Inxu
 * @Description: 对象属性复制类
 */
public class BeanCopyUtil {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String DATE = "Date";
    private static final String LOCAL_DATE_TIME = "LocalDateTime";

    /**
     * 对象属性赋值方法
     * 将 S 中的属性值，按照属性名的对应关系, 复制到 T 中
     *
     * @param s   源对象数据
     * @param t   目标对象 类的实例
     * @param <S> 泛型
     * @param <T> 泛型
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ParseException
     * @throws NoSuchObjectException
     */
    public static <S, T> T copy(S s, T t)
            throws Exception {

        Class<?> clazz = s.getClass();
        AimObject type_AimObject = clazz.getAnnotation(AimObject.class);
        Class type_Class = type_AimObject.value();
        if (!type_Class.isAssignableFrom(t.getClass())) {
            throw new NoSuchObjectException("注解的映射类与目标类不匹配.");
        }
        // 获取要映射的类的实例
        Object newInstance = null;
        if (null == t) {
            newInstance = type_Class.newInstance();
        } else {
            newInstance = t;
        }
        // 1 : 反射得到 源对象的属性列表
        Field[] s_Fields = clazz.getDeclaredFields();
        for (Field sField : s_Fields) {
            sField.setAccessible(true);
            // 2: 获取源对象中， 属性上需要映射的类
            AimObject field_AimObject = sField.getAnnotation(AimObject.class);
            if (null != field_AimObject) {
                // 如果源对象中的 对象属性 的映射类 和源对象映射的类一致, 继续延用当前的 对象(newInstance)
                if (field_AimObject.value().isAssignableFrom(type_Class)) {
                    copy(sField.get(s), newInstance);
                } else {
                    continue;
                }
                field_AimObject = null;
            }
            // 3: 获取源对象中属性的映射
            AimField aimField = sField.getAnnotation(AimField.class);
            if (null == aimField && null == field_AimObject) {
                continue;
            }
            // Class field_orm_other_obj = aimField.clazz();
            // 获取目标类的属性名
            String target_Field = aimField.value();
            // 获取目标类的 属性类型
            Class target_Field_Type = aimField.type();
            // 获取源对象 的 属性值 , 最终把它赋值给目标类的属性 target_Field
            Object o = sField.get(s);
            if (null == o) {
                continue;
            }
            /*获取源对象属性的类型*/
            Class<?> s_field_Type = sField.getType();
            // 赋值
            setTargetFieldValueByNameAndValue(newInstance, target_Field, target_Field_Type, o, s_field_Type);
        }
        return t;
    }

    /**
     * 对象属性赋值方法
     * 将 S 属性中的 @AimOther注解 ,根据属性名和指定的类， 复制到 T
     *
     * @param s   源对象
     * @param t   目标对象
     * @param <S> 泛型
     * @param <T> 泛型
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchObjectException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws ParseException
     * @throws InvocationTargetException
     */
    public static <S, T> T copyPartByAimOther(S s, T t)
            throws Exception {
        if (null == s || null == t) {
            throw new NullPointerException("源对象 或者 目标对象 不能为空.");
        }
        Field[] fields = s.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            AimOther type_Aim_other = field.getAnnotation(AimOther.class);
            if (null == type_Aim_other) {
                continue;
            }
            Class type_Class = type_Aim_other.clazz();
            if (!type_Class.isAssignableFrom(t.getClass())) {
                continue;
            }
            String target_Field = type_Aim_other.value();
            Class target_Field_Type = type_Aim_other.type();
            Class<?> s_field_Type = field.getType();
            Object o = field.get(s);
            if (null == o) {
                continue;
            }
            setTargetFieldValueByNameAndValue(t, target_Field, target_Field_Type, o, s_field_Type);
        }
        return t;
    }

    /**
     * @param s
     * @param t
     * @param <S>
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws ParseException
     * @throws InstantiationException
     * @throws NoSuchObjectException
     * @throws NoSuchFieldException
     */
    public static <S, T> T copyPartByAimObjectOnField(S s, T t)
            throws Exception {
        if (null == s || null == t) {
            throw new NullPointerException("源对象 或者 目标对象 不能为空.");
        }
        Field[] fields = s.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            // 获取源对象中， 属性上需要映射的类
            AimObject field_AimObject = field.getAnnotation(AimObject.class);
            if (null == field_AimObject) {
                continue;
            }
            t = copy(field.get(s), t);
        }

        return t;
    }

    /**
     * 为目标类的属性 通过属性名赋值
     *
     * @param t                 目标类
     * @param fieldName         目标类的变量名
     * @param target_Field_Type 变量类型
     * @param value             源数据
     * @param s_field_Type      源数据 类型
     * @param <T>               泛型
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static <T> void setTargetFieldValueByNameAndValue(T t,
                                                             String fieldName,
                                                             Class target_Field_Type,
                                                             Object value,
                                                             Class<?> s_field_Type)
            throws IllegalAccessException, NoSuchFieldException, ParseException {

        Field field = t.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        // 如果与映射属性的类型不一致， 转换成目标属性的类型
        if (!target_Field_Type.isAssignableFrom(s_field_Type)) {
            // 时间转 String
            if ((DATE).equals(s_field_Type.getSimpleName()) || s_field_Type.getSimpleName().equals(LOCAL_DATE_TIME)) {
                value = covertDate2String(s_field_Type.getSimpleName(), value);
            } else {
                value = convertValueByType(target_Field_Type, value.toString());
            }
        }
        field.set(t, value);
    }

    /**
     * 将 值 进行类型转换
     *
     * @param target_Field_Type 目标类变量的类型
     * @param val               值
     * @return
     * @throws ParseException
     */
    public static Object convertValueByType(Class target_Field_Type, String val) throws ParseException {
        String typeName = target_Field_Type.getSimpleName();
        Object o = null;
        switch (typeName) {
            case "Integer":
                o = Integer.parseInt(val);
                break;
            case "Long":
                o = Long.parseLong(val);
                break;
            case "Double":
                o = Double.parseDouble(val);
                break;
            case "Short":
                o = Short.parseShort(val);
                break;
            case "Byte":
                o = Byte.parseByte(val);
                break;
            case "Date":
                o = SIMPLE_DATE_FORMAT.parse(val);
                break;
            case "LocalDateTime":
                o = LocalDateTime.parse(val, DATE_TIME_FORMATTER);
                break;
            default:
                o = val;
                break;
        }

        return o;
    }

    public static Object covertDate2String(String typeName, Object value) {
        Object o = null;
        switch (typeName) {
            case "Date":
                Date date = (Date) value;
                o = SIMPLE_DATE_FORMAT.format(date);
                break;
            case "LocalDateTime":
                LocalDateTime parse = (LocalDateTime) value;
                o = parse.format(DATE_TIME_FORMATTER);
                break;
            default:
                o = value;
                break;
        }

        return o;
    }
}
