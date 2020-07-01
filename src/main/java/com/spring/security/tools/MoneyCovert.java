package com.spring.security.tools;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MoneyCovert {
    public MoneyCovert() {
    }

    public static String formatMoney(BigDecimal money) {
        if (money == null) {
            money = BigDecimal.ZERO;
        }

        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(money.doubleValue());
    }

    public static String formatToYuan(Integer money) {
        if (money == null) {
            return "0.00";
        } else {
            DecimalFormat format = new DecimalFormat("0.00");
            return format.format(BigDecimal.valueOf((long) money).divide(BigDecimal.valueOf(100L)).doubleValue());
        }
    }

    public static int covertFenMoney(String money) {
        if (StringUtils.isBlank(money)) {
            return 0;
        } else {
            BigDecimal amount = BigDecimal.valueOf(Double.valueOf(money));
            return covertFenMoney(amount);
        }
    }

    public static int coverMathFen(String money) {
        if (StringUtils.isBlank(money)) {
            return 0;
        } else {
            BigDecimal amount = BigDecimal.valueOf(Double.valueOf(money));
            return amount.multiply(BigDecimal.valueOf(100L)).intValue();
        }
    }

    public static int covertFenMoney(BigDecimal money) {
        return money != null && money.compareTo(BigDecimal.ZERO) > 0 ? money.multiply(BigDecimal.valueOf(100L)).intValue() : 0;
    }
}

