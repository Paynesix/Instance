package com.spring.security.tools;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class SignUtil {

    private static final org.apache.logging.log4j.Logger log =
            org.apache.logging.log4j.LogManager.getLogger(MapTool.class);

    /**
     * TODO 待优化，使用JSONObject.toJSONString(json,SerializerFeature.SortField.MapSortField)进行排序
     * @param json
     * @param appSecret
     * @return
     */
    public static String getSign(JSONObject json, String appSecret) {
        JSONObject copyJson = SerializationUtils.clone(json);
        copyJson.put("sign", "");
        ArrayList<String> list = new ArrayList<>();
        Set<String> keys = copyJson.keySet();
        for (String key : keys) {
            String val = copyJson.getString(key);
            if (StringUtils.isNotBlank(val)) {
                list.add(key + "=" + val + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        
        String sign= MD5.MD5Encode(sb.toString() + "key=" + appSecret).toUpperCase();
        log.info("-->json:"+json.toJSONString()+"\n md5:"+(sb.toString() + "key=" + appSecret)+ "\n sign:"+sign);
        return sign;
    }

    public static String getSign(Map<String, Object> map, String appSecret) {
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null && !"".equals(entry.getValue())) {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + appSecret;
        return MD5.MD5Encode(result).toUpperCase();
    }

    /**
     * 从API返回的XML数据里面重新计算一次签名
     *
     * @param responseString API返回的XML数据
     * @return 新鲜出炉的签名
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws DocumentException
     */
    public static String getSignFromResponseString(String responseString, String appSecret) throws Exception {
        Map<String, Object> map = XMLParser.getMapFromXML(responseString);
        // 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
        map.put("sign", "");
        // 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
        return SignUtil.getSign(map, appSecret);
    }

    /**
     * 
     * 比较两个签名. <br/>
     * 
     * @author liujingcheng
     * @date: 2017年4月1日 上午10:27:00
     * @version 1.0
     *
     * @param appSecret
     * @return
     */
    public static Boolean compareResultSign(String returnMsg, String appSecret) throws Exception {
        boolean flag = returnMsg.contains("S");
        Map<String, Object> map = XMLParser.getMapFromXML(returnMsg);
        //存在返回值，并且内容大于2
        if (flag || map.size() > 2) {
            String backSign = (String) map.get("sign");
            if (StringUtils.isBlank(backSign)) {
                return false;
            }
            map.put("sign", "");
            String newSign = SignUtil.getSign(map, appSecret);
            if (Objects.equals(backSign, newSign)) {
                return true;
            }
        }
        return true;
    }

    public static void main(String[] args){
        String content = "{\"app_id\":\"1067350281152364544\",\"merchant_code\":\"1130300000261\",\"bill_date\":\"20200109\",\"bill_type\":\"1\",\"sign_type\":\"MD5\",\"sign\":\"4F8F213B20C4CC5E8D0AEEC91A8F7BD6\"}";
        String signKey="A783530D4CB0C23E4DA55AF201ADA0E4";
        System.out.println(getSign((JSONObject) JSONObject.parse(content), signKey));
    }
}
