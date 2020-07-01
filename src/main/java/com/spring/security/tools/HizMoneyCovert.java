package com.spring.security.tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class HizMoneyCovert extends MoneyCovert{

    /**
     * 
     * 鑾峰彇鏁存暟閲戦. <br/>
     * 
     * @author Andy
     * @date: 2017骞�11鏈�17鏃� 涓嬪崍5:18:54
     * @version 1.0
     *
     * @param fee
     * @return
     */
    public static int getIntFee(BigDecimal fee) {
        if (fee == null) {
            return 0;
        }
        // 鏁存暟鐩存帴杩斿洖
        if (new BigDecimal(fee.intValue()).compareTo(fee) == 0) {
            return fee.intValue();
        } else if (new BigDecimal(fee.intValue()).compareTo(fee) > 0) {
            // 璐熸暟
            return fee.intValue();
        } else {
            // 姝ｆ暟
            return fee.intValue() + 1;
        }
    }

    /**
     * 
     * 鏀寔Long绫诲瀷鐨勬暟鎹浆鎹�. <br/>
     * 
     * @author Andy
     * @date: 2018骞�2鏈�1鏃� 涓婂崍11:05:28
     * @version 1.0
     *
     * @param money
     * @return
     */
    public static String formatToYuan(Long money) {
        if (money == null) {
            return "0.00";
        }
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100)).doubleValue());
    }



}
