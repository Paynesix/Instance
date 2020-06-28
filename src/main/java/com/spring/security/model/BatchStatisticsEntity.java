package com.spring.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description 对账批次统计实体类
 * @Author lizhou
 * @Version 1.0.0
 * @Since 1.0
 * @Date 2019-08-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStatisticsEntity {
    private String channel;
    private BigDecimal hisTotalTranFee;
    private Long hisTranNumber;
    private BigDecimal hisTotalRefundFee;
    private Long hisRefundNumber;
    private BigDecimal channelTotalTranFee;
    private Long channelTranNumber;
    private BigDecimal channelTotalRefundFee;
    private Long channelRefundNumber;
    private String merchantId;

}
