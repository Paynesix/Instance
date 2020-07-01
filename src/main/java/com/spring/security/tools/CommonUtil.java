package com.spring.security.tools;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.*;

public class CommonUtil {

    public static <T> T getUnionResultFromXML(String xml, Class<T> t) throws Exception {
        T result = t.newInstance();
        Document document = parseText(xml);
        List<Field> fields = getDeclaredField(t);
        Element root = document.getRootElement();
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            @SuppressWarnings("unchecked")
            Iterator<Element> it = root.elementIterator();
            while (it.hasNext()) {
                Element element = it.next();
                if (field.getName().equals(element.getName())) {
                    field.setAccessible(true);
                    if (StringUtils.isBlank(element.getText())) {
                        continue;
                    }
                    if (field.getType().equals(Integer.class)) {
                        field.set(result, Integer.parseInt(element.getText()));
                    } else if (field.getType().equals(Long.class)) {
                        field.set(result, Long.valueOf(element.getText()));
                    } else {
                        field.set(result, element.getText());
                    }
                    break;
                }
            }
        }
        return result;
    }

    private static Document parseText(String text) throws DocumentException, SAXException {
        Document result = null;
        SAXReader reader = new SAXReader();
        reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
        reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        String encoding = getEncoding(text);
        InputSource source = new InputSource(new StringReader(text));
        source.setEncoding(encoding);
        result = reader.read(source);
        // if the XML parser doesn't provide a way to retrieve the encoding,
        // specify it manually
        if (result.getXMLEncoding() == null) {
            result.setXMLEncoding(encoding);
        }
        return result;
    }

    private static String getEncoding(String text) {
        String result = null;
        String xml = text.trim();
        if (xml.startsWith("<?xml")) {
            int end = xml.indexOf("?>");
            String sub = xml.substring(0, end);
            StringTokenizer tokens = new StringTokenizer(sub, " =\"\'");
            while (tokens.hasMoreTokens()) {
                String token = tokens.nextToken();
                if ("encoding".equals(token)) {
                    if (tokens.hasMoreTokens()) {
                        result = tokens.nextToken();
                    }
                    break;
                }
            }
        }

        return result;
    }

    private static List<Field> getDeclaredField(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            } catch (Exception e) {
                continue;
            }
        }
        return fields;
    }
}
