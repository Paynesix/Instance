package com.spring.security.tools;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MoneyCovertUtil extends MoneyCovert {
    private static MathContext mc = new MathContext(4, RoundingMode.DOWN);
    /**
     *
     * 获取整数金额. <br/>
     *
     * @author Andy
     * @date: 2017年11月17日 下午5:18:54
     * @version 1.0
     *
     * @param fee
     * @return
     */
    public static int getIntFee(BigDecimal fee) {
        if (fee == null) {
            return 0;
        }
        // 整数直接返回
        if (new BigDecimal(fee.intValue()).compareTo(fee) == 0) {
            return fee.intValue();
        } else if (new BigDecimal(fee.intValue()).compareTo(fee) > 0) {
            // 负数
            return fee.intValue();
        } else {
            // 正数
            return fee.intValue() + 1;
        }
    }

    /**
     *
     * 支持Long类型的数据转换. <br/>
     *
     * @author Andy
     * @date: 2018年2月1日 上午11:05:28
     * @version 1.0
     *
     * @param money
     * @return
     */
    public static String formatToYuan(Long money) {
        if (money == null) {
            return "0.0000";
        }
        DecimalFormat format = new DecimalFormat("0.0000");
        return format.format(BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100)).doubleValue());
    }

    /**
     * 元后保留4位小数
     * @param money
     * @return
     */
    public static String formatToYuan(BigDecimal money) {
        if (money == null) {
            return "0.00";
        }
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(money.divide(BigDecimal.valueOf(100)).doubleValue());
    }

    public static String formatToYuanAbs(BigDecimal money) {
        if (money == null) {
            return "0.00";
        }
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(money.abs().divide(BigDecimal.valueOf(100)).doubleValue());
    }

    public static BigDecimal intToBigDecimal(int money) {
        return BigDecimal.valueOf(Double.valueOf(money));
    }

    public static BigDecimal longToBigDecimal(Long money) {
        return BigDecimal.valueOf(Double.valueOf(money));
    }

    public static BigDecimal stringToBigDecimal(String money) {
        return BigDecimal.valueOf(Double.valueOf(money));
    }

    public static BigDecimal mul(BigDecimal a, BigDecimal b) {
        return a.multiply(b, mc);
    }

    public static BigDecimal absDecimal(BigDecimal bigDecimal){
        if(bigDecimal ==null){
            return new BigDecimal(0);
        }
        return bigDecimal.abs();
    }

    public static Integer absDecimalIntValue(BigDecimal bigDecimal){
        if(bigDecimal ==null){
            return new BigDecimal(0).intValue();
        }
        return bigDecimal.abs().intValue();
    }

    public static Long absDecimalLongValue(BigDecimal bigDecimal){
        if(bigDecimal ==null){
            return new BigDecimal(0).longValue();
        }
        return bigDecimal.abs().longValue();
    }
}
