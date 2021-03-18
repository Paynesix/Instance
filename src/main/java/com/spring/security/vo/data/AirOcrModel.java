package com.spring.security.vo.data;

import com.spring.security.annotation.AimField;
import com.spring.security.annotation.AimObject;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 票务扫描机票推送原始数据
 *
 * @auther: wuxianyi@baoku.com
 * @date: 2021\1\21 0021 19:25
 */
@Data
@AimObject(AirBillModel.class)
public class AirOcrModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @AimField(value = "invoiceType", type = String.class)
    private String invoiceType;// 发票类型 invoiceType

     @AimField(value = "namecn", type = String.class)
    private String name;//乘客姓名 name

     @AimField(value = "certnum", type = String.class)
    private String userCardNo;//乘客证件号码 userCardNo

     @AimField(value = "printNumber", type = String.class)
    private String printNumber;//印刷序号 printNumber

     @AimField(value = "ticketNo", type = String.class)
    private String eticketNumber;//电子客票号码 eticketNumber

     @AimField(value = "checkCode", type = String.class)
    private String checkCode;//校验码 checkCode

     @AimField(value = "invoiceDate", type = String.class)
    private String billingDate;//开票日期 billingDate

     @AimField(value = "openUnit", type = String.class)
    private String issueBy;//填开单位 issueBy

     @AimField(value = "saleUnitCode", type = String.class)
    private String agentcode;//销售单位代号 agentcode

     @AimField(value = "zj", type = BigDecimal.class)
    private BigDecimal totalAmount;//合计金额 totalAmount

     @AimField(value = "zsf", type = String.class)
    private String tax;//合计税额 tax

     @AimField(value = "fueltax", type = String.class)
    private String fuelSurcharge;//燃油附加费 fuelSurcharge

     @AimField(value = "tkttax", type = String.class)
    private String caacDevelopmentFund;//民航发展基金 caacDevelopmentFund

     @AimField(value = "insurance", type = String.class)
    private String insurance;//保险费 insurance

     @AimField(value = "belongidLabel", type = String.class)
    private String internationalFlag;//国内国际标签 internationalFlag

     @AimField(value = "ticketmoney", type = String.class)
    private String amountTax;//票面金额 amountTax

     @AimField(value = "orgname", type = String.class)
    private String from;//出发地 flights[0].from

     @AimField(value = "dstname", type = String.class)
    private String to;//到达地 flights[0].to

     @AimField(value = "flightnum", type = String.class)
    private String flightNumber;//航班号 flights[0].flightNumber

     @AimField(value = "createdate", type = String.class)
    private String date;//日期 flights[0].date

     @AimField(value = "createtime", type = String.class)
    private String time;//时间 flights[0].time

     @AimField(value = "seatType", type = String.class)
    private String seat;//座位类型 flights[0].seat

     @AimField(value = "carriername", type = String.class)
    private String carrier;//承运人 flights[0].carrier

}
