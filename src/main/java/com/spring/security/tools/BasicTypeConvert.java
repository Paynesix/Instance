package com.spring.security.tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @Description 基初类型之间的转化
 * @Author xy
 * @Date 2020/6/10 16:19
 * @Version 1.0
 * @Since JDK 1.8
 */
public class BasicTypeConvert {

    // 相除得到百分比 2/4 = 50.00%
    public static String dts(Long x, Long y) {
        if (x == null) return "0";
        if (y == null) return "-";
        return String.format("%.2f", ((x.doubleValue() / y.doubleValue()) * 100)) + "%";
    }

    // 相加减得到数字 2/4 = 50.00%
    public static String mts(Long x, Long y) {
        return String.valueOf((x == null ? 0L : x) - (y == null ? 0L : y));
    }

    // 相除得到double 2/4 = 0.50000
    public static double dtd(Long x, Long y) {
        if (x == null) return 0;
        if (y == null) return 0;
        DecimalFormat format = new DecimalFormat("0.00000");
        return Double.parseDouble(format.format(x.doubleValue() / y.doubleValue()));
    }

    // double format to %, 0.50000 -> 50.00%
    public static String dtsf(double x) {
        if (x == 0) return "0.00%";
        return String.format("%.2f", x * 100) + "%";
    }

    // BigDecimal subtract to String
    public static String bigDecimalSubToString(BigDecimal o, BigDecimal t) {
        if(o == null) return "0";
        if(t == null) return "0";
        return o.subtract(t).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    // BigDecimal add to String
    public static String bigDecimalAddToString(BigDecimal o, BigDecimal t) {
        if(o == null) return "0";
        if(t == null) return "0";
        return o.add(t).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    // BigDecimal to String
    public static String bigDecimalToString(BigDecimal o) {
        return o == null ? "0.00" : o.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }


    /**
     * @Title: double add
     * @Description: 多个浮点型数字相加
     * @param nums
     * @return
     * @author wxy
     * @Date 2020/8/19 9:38
     * @version 1.0.0
     * @throws
     **/
    public static double add(double... nums) {
        double rs = 0.0D;

        try {
            BigDecimal result = new BigDecimal(0);
            double[] var8 = nums;
            int var7 = nums.length;

            for(int var6 = 0; var6 < var7; ++var6) {
                double d = var8[var6];
                BigDecimal b2 = new BigDecimal(Double.toString(d));
                result = result.add(b2);
            }

            rs = result.doubleValue();
            return rs;
        } catch (Exception var10) {
            throw new RuntimeException(var10.getMessage(), var10);
        }
    }
}
