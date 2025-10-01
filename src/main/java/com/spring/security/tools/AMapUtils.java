package com.spring.security.tools;

import com.alibaba.fastjson.JSON;
import com.spring.security.tools.httpclient.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AMapUtils {

    private static Logger logger = LoggerFactory.getLogger(AMapUtils.class);

    public static final String GET_URL = "http://restapi.amap.com/v3/geocode/geo?key=6024942adc5759978a23dfa885dbb198&address=%s";
    public static final String GET_ADDR_URL = "https://restapi.amap.com/v3/geocode/regeo?key=6024942adc5759978a23dfa885dbb198&radius=1000&location=%s";

    /**
     * 经纬度正则表达式
     */
    private static Pattern pattern = Pattern.compile("\"location\":\"(\\d+\\.\\d+),(\\d+\\.\\d+)\"");

    /**
     * 获取经纬度信息
     *
     * @param address 详细地址
     * @return [经度，纬度]：纬度范围-90~90，经度范围-180~180
     */
    public static Double[] getLatAndLonByAddress(String address) {
        try {
//            String pAddress = URLEncoder.encode(address, "UTF-8");
//            String requestUrl = String.format(GET_URL, pAddress);
            String requestUrl = String.format(GET_URL, address);
            MultiValueMap<String, String> params = new HttpHeaders();
            HttpHeaders headers = new HttpHeaders();
            String result = HttpClient.sendGetRequest(requestUrl, params, headers);
            Matcher matcher = pattern.matcher(result);
            if (matcher.find() && matcher.groupCount() == 2) {
                Double[] gps = new Double[2];
                gps[0] = Double.valueOf(matcher.group(1));
                gps[1] = Double.valueOf(matcher.group(2));
                return gps;
            }
        } catch (Exception e) {
            logger.error("MapUtils getLatAndLonByAddress 获取地理位置信息失败", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取经纬度信息
     *
     * @param lngAndlat [经度，纬度]：纬度范围-90~90，经度范围-180~180
     * @return address 详细地址
     */
    public static String getAddressByLatAndLon(String lngAndlat) {
        try {
            String requestUrl = String.format(GET_ADDR_URL, lngAndlat);
            MultiValueMap<String, String> params = new HttpHeaders();
            HttpHeaders headers = new HttpHeaders();
            String result = HttpClient.sendGetRequest(requestUrl, params, headers);
            return result;
        } catch (Exception e) {
            logger.error("MapUtils getLatAndLonByAddress 获取地理位置信息失败", e);
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // [112.819549,28.347458]
        // [112.909066,28.278819]
        System.out.println(JSON.toJSON(getLatAndLonByAddress("湖南省长沙市望城区月亮岛地铁西")));
        System.out.println(JSON.toJSON(getAddressByLatAndLon("112.908466, 28.280093")));
    }

}
