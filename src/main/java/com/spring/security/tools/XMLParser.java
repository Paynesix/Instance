package com.spring.security.tools;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * Description: xml解析<br/>
 *
 * @author liujingcheng
 * @date: 2017年1月22日 下午11:23:35
 * @version 1.0
 * @since JDK 1.7
 */
public class XMLParser {
    /**
     * 从RefunQueryResponseString里面解析出退款订单数据
     * 
     * @return 因为订单数据有可能是多个，所以返回一个列表
     * @throws DocumentException
     */

    @SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromXML(String xmlString) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Document document = DocumentHelper.parseText(xmlString);
        Element root = document.getRootElement();
        Iterator<Element> it = root.elementIterator();
        while (it.hasNext()) {
            Element element = it.next();
            map.put(element.getName(), element.getTextTrim());
        }

        return map;
    }

    public static String objToXml(Map<String, Object> xmlMap) {
        StringBuilder sb = new StringBuilder(1000);
        sb.append("<xml>\n");
        for (Map.Entry<String, Object> entry : xmlMap.entrySet()) {
            String key = entry.getKey();
            Object var = entry.getValue();
            if (var == null) {
                continue;
            }
            sb.append("<").append(key).append(">");
            if (var instanceof String) {
                sb.append("<![CDATA[");
                sb.append(var);
                sb.append("]]>");
            } else {
                sb.append(var);
            }
            sb.append("</").append(key).append(">\n");
        }
        sb.append("</xml>\n");
        return sb.toString();
    }

    public static String objToXml(Map<String, Object> xmlMap, String rootNode) {
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(rootNode).append(">\n");
        for (Map.Entry<String, Object> entry : xmlMap.entrySet()) {
            String key = entry.getKey();
            Object var = entry.getValue();
            if (var == null) {
                continue;
            }
            sb.append("<").append(key).append(">");
            if (var instanceof String) {
                sb.append("<![CDATA[");
                sb.append(var);
                sb.append("]]>");
            } else {
                sb.append(var);
            }
            sb.append("</").append(key).append(">\n");
        }
        sb.append("</").append(rootNode).append(">\n");
        return sb.toString();
    }
}
