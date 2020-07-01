package com.spring.security.tools;

import com.spring.security.exception.AMException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ReflectTool {
    private Boolean isMap = false;
    private Map<String, Field> fields = null;

    public static Field getField(Class<?> cls, String fieldName) throws NoSuchFieldException {
        Field f = null;
        Class clazz = cls;

        while(clazz != Object.class) {
            try {
                f = clazz.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } catch (Exception var5) {
                clazz = clazz.getSuperclass();
            }
        }

        throw new NoSuchFieldException("类" + cls.getName() + "找不到属性" + fieldName);
    }

    public ReflectTool(Object o, List<String> fieldNames) throws NoSuchFieldException {
        if (o instanceof Map) {
            this.isMap = true;
        } else {
            this.fields = new HashMap(fieldNames.size());
            Class<?> cls = o.getClass();
            Iterator var5 = fieldNames.iterator();

            while(var5.hasNext()) {
                Object fieldName = var5.next();
                Field f = null;
                Class clazz = cls;

                while(clazz != Object.class) {
                    try {
                        f = clazz.getDeclaredField(fieldName.toString());
                        f.setAccessible(true);
                        break;
                    } catch (Exception var9) {
                        clazz = clazz.getSuperclass();
                    }
                }

                if (f == null) {
                    throw new NoSuchFieldException("类" + cls.getName() + "找不到属性" + fieldName);
                }

                this.fields.put(fieldName.toString(), f);
            }
        }

    }

    public Object get(Object o, String fieldName) {
        if (this.isMap) {
            return ((Map)o).get(fieldName);
        } else {
            try {
                return ((Field)this.fields.get(fieldName)).get(o);
            } catch (Exception var4) {
                throw new AMException("10010001", var4.getMessage());
            }
        }
    }
}
