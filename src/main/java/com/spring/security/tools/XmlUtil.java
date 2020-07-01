package com.spring.security.tools;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

public class XmlUtil {
    private static String DEFAULT_CHARSET = "utf-8";
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static XmlMapper xmlMapper = new XmlMapper();
    public static JSONObject xmlToJson(String xml) throws Exception {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes(DEFAULT_CHARSET)));
        Element rootElement = document.getRootElement();
        return iterateElement(rootElement);
    }

    private static JSONObject iterateElement(Element element) {
        List jiedian = element.elements();
        Element et = null;
        JSONObject obj = new JSONObject();
        List list = null;
        for (int i = 0; i < jiedian.size(); i++) {
            list = new LinkedList();
            et = (Element) jiedian.get(i);
            if (et.getTextTrim().equals("")) {
                if (et.elements().size() == 0)
                    continue;
                if (obj.containsKey(et.getName())) {
                    list = (List) obj.get(et.getName());
                }
                list.add(iterateElement(et));
                obj.put(et.getName(), list);
            } else {
                obj.put(et.getName(), et.getTextTrim());
            }
        }
        return obj;
    }
   
    
    public static JSONObject xml2json(String xmlString) throws Exception {
        StringWriter w = new StringWriter();
        JsonParser jp = xmlMapper.getFactory().createParser(xmlString);
        JsonGenerator jg = objectMapper.getFactory().createGenerator(w);
        while (jp.nextToken() != null) {
            jg.copyCurrentEvent(jp);
        }
        jp.close();
        jg.close();
        
        return JSONObject.parseObject(w.toString());
    }
    
    public static String jsonToXml(String jsonObject){
        String xml;
        try {
            JsonNode root = objectMapper.readTree(jsonObject);
            xml = xmlMapper.writeValueAsString(root);
            return xml;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

//    public static void main(String[] args) {
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("resultCode", 1);
//        jsonObject.put("resultDesc", 2);
//        jsonObject.put("orderStatus", "3");
//        System.out.println(jsonToXml(jsonObject));
//    }
}
