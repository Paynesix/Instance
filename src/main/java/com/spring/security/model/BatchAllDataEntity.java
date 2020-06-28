package com.spring.security.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 对账批次统计实体类
 * @Author lizhou
 * @Version 1.0.0
 * @Since 1.0
 * @Date 2019-08-20
 */
@Data
public class BatchAllDataEntity {

    private String channel;
    private String channelName;
    private BigDecimal hisTotalTranFee;
    private Long hisTranNumber;
    private BigDecimal hisTotalRefundFee;
    private Long hisRefundNumber;
    private BigDecimal channelTotalTranFee;
    private Long channelTranNumber;
    private BigDecimal channelTotalRefundFee;
    private Long channelRefundNumber;
    private Long mannyError;
    private Long shortError;

    public BatchAllDataEntity() { }

    public BatchAllDataEntity(BigDecimal hisTotalTranFee, Long hisTranNumber,
                              BigDecimal hisTotalRefundFee, Long hisRefundNumber,
                              BigDecimal channelTotalTranFee, Long channelTranNumber,
                              BigDecimal channelTotalRefundFee, Long channelRefundNumber,
                              Long mannyError, Long shortError) {
        if (hisTranNumber==null){
            hisTranNumber=0L;
        }
        if (hisRefundNumber==null){
            hisRefundNumber=0L;
        }
        if (channelTranNumber==null){
            channelTranNumber=0L;
        }
        if (channelRefundNumber==null){
            channelRefundNumber=0L;
        }
        if (mannyError==null){
            mannyError=0L;
        }
        if (shortError==null){
            shortError=0L;
        }
        this.hisTotalTranFee = hisTotalTranFee;
        this.hisTranNumber = hisTranNumber;
        this.hisTotalRefundFee = hisTotalRefundFee;
        this.hisRefundNumber = hisRefundNumber;
        this.channelTotalTranFee = channelTotalTranFee;
        this.channelTranNumber = channelTranNumber;
        this.channelTotalRefundFee = channelTotalRefundFee;
        this.channelRefundNumber = channelRefundNumber;
        this.mannyError = mannyError;
        this.shortError = shortError;
    }

    public BatchAllDataEntity(String channel, String channelName, BigDecimal hisTotalTranFee, Long hisTranNumber,
                              BigDecimal hisTotalRefundFee, Long hisRefundNumber,
                              BigDecimal channelTotalTranFee, Long channelTranNumber,
                              BigDecimal channelTotalRefundFee, Long channelRefundNumber,
                              Long mannyError, Long shortError) {
        if (hisTranNumber==null){
            hisTranNumber=0L;
        }
        if (hisRefundNumber==null){
            hisRefundNumber=0L;
        }
        if (channelTranNumber==null){
            channelTranNumber=0L;
        }
        if (channelRefundNumber==null){
            channelRefundNumber=0L;
        }
        if (mannyError==null){
            mannyError=0L;
        }
        if (shortError==null){
            shortError=0L;
        }
        this.hisTotalTranFee = hisTotalTranFee;
        this.hisTranNumber = hisTranNumber;
        this.hisTotalRefundFee = hisTotalRefundFee;
        this.hisRefundNumber = hisRefundNumber;
        this.channelTotalTranFee = channelTotalTranFee;
        this.channelTranNumber = channelTranNumber;
        this.channelTotalRefundFee = channelTotalRefundFee;
        this.channelRefundNumber = channelRefundNumber;
        this.mannyError = mannyError;
        this.shortError = shortError;
        this.channel = channel;
        this.channelName = channelName;
    }
}
