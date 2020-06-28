package com.spring.security.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Description
 * @Author lizhou
 * @Version 1.0.0
 * @Since 1.0
 * @Date 2019-08-20
 */
@Entity
@Table(name = "t_billc_bill_batch", schema = "hc_hiz_jsszxybill_center")
@EntityListeners(AuditingEntityListener.class)
public class BatchEntity {
    private Long id;
    private Integer batchState;
    private String billDate;
    private String merchantId;
    private String merchantName;
    private Long hisId;
    private String hisName;
    private String channel;
    private String batchChannel;
    private String batchChannelName;
    private String channelName;
    private BigDecimal serivceAmt;
    private BigDecimal settleAmt;
    private BigDecimal settleRefundAmt;
    private Integer settleRefundCount;
    private Integer settleCount;
    private BigDecimal tradeAmt;
    private BigDecimal tradeRefundAmt;
    private Integer tradeCount;
    private Integer tradeRefundCount;
    private BigDecimal hisAmt;
    private BigDecimal hisRefundAmt;
    private Integer hisRefundCount;
    private Integer hisCount;
    private BigDecimal flatAmt;
    private BigDecimal manyAmt;
    private BigDecimal shortAmt;
    private BigDecimal shortDoubtAmt;
    private BigDecimal manyDoubtAmt;
    private BigDecimal frontManyAmt;
    private BigDecimal frontShortAmt;
    private BigDecimal processAmt;
    private Integer manyCount;
    private Integer shortCount;
    private Integer billStatus;
    private Integer billResult;
    private String memo;
    private String bizSource;
    private String bizType;
    private Integer billMode;
    private Timestamp createTime;
    private Timestamp updateTime;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "biz_type")
    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    @Basic
    @Column(name = "biz_source")
    public String getBizSource() {
        return bizSource;
    }

    public void setBizSource(String bizSource) {
        this.bizSource = bizSource;
    }

    @Basic
    @Column(name = "batch_state")
    public Integer getBatchState() {
        return batchState;
    }

    public void setBatchState(Integer batchState) {
        this.batchState = batchState;
    }

    @Basic
    @Column(name = "bill_date")
    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
    

    @Basic
    @Column(name = "merchant_name")
    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Basic
    @Column(name = "merchant_id")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    @Basic
    @Column(name = "his_id")
    public Long getHisId() {
        return hisId;
    }

    public void setHisId(Long hisId) {
        this.hisId = hisId;
    }

    @Basic
    @Column(name = "his_name")
    public String getHisName() {
        return hisName;
    }

    public void setHisName(String hisName) {
        this.hisName = hisName;
    }

    @Basic
    @Column(name = "channel")
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Basic
    @Column(name = "channel_name")
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Basic
    @Column(name = "serivce_amt")
    public BigDecimal getSerivceAmt() {
        return serivceAmt;
    }

    public void setSerivceAmt(BigDecimal serivceAmt) {
        this.serivceAmt = serivceAmt;
    }

    @Basic
    @Column(name = "settle_amt")
    public BigDecimal getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(BigDecimal settleAmt) {
        this.settleAmt = settleAmt;
    }

    @Basic
    @Column(name = "settle_refund_amt")
    public BigDecimal getSettleRefundAmt() {
        return settleRefundAmt;
    }

    public void setSettleRefundAmt(BigDecimal settleRefundAmt) {
        this.settleRefundAmt = settleRefundAmt;
    }

    @Basic
    @Column(name = "settle_refund_count")
    public Integer getSettleRefundCount() {
        return settleRefundCount;
    }

    public void setSettleRefundCount(Integer settleRefundCount) {
        this.settleRefundCount = settleRefundCount;
    }

    @Basic
    @Column(name = "settle_count")
    public Integer getSettleCount() {
        return settleCount;
    }

    public void setSettleCount(Integer settleCount) {
        this.settleCount = settleCount;
    }

    @Basic
    @Column(name = "trade_amt")
    public BigDecimal getTradeAmt() {
        return tradeAmt;
    }

    public void setTradeAmt(BigDecimal tradeAmt) {
        this.tradeAmt = tradeAmt;
    }

    @Basic
    @Column(name = "trade_refund_amt")
    public BigDecimal getTradeRefundAmt() {
        return tradeRefundAmt;
    }

    public void setTradeRefundAmt(BigDecimal tradeRefundAmt) {
        this.tradeRefundAmt = tradeRefundAmt;
    }

    @Basic
    @Column(name = "trade_count")
    public Integer getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        this.tradeCount = tradeCount;
    }

    @Basic
    @Column(name = "trade_refund_count")
    public Integer getTradeRefundCount() {
        return tradeRefundCount;
    }

    public void setTradeRefundCount(Integer tradeRefundCount) {
        this.tradeRefundCount = tradeRefundCount;
    }

    @Basic
    @Column(name = "his_amt")
    public BigDecimal getHisAmt() {
        return hisAmt;
    }

    public void setHisAmt(BigDecimal hisAmt) {
        this.hisAmt = hisAmt;
    }

    @Basic
    @Column(name = "his_refund_amt")
    public BigDecimal getHisRefundAmt() {
        return hisRefundAmt;
    }

    public void setHisRefundAmt(BigDecimal hisRefundAmt) {
        this.hisRefundAmt = hisRefundAmt;
    }

    @Basic
    @Column(name = "his_refund_count")
    public Integer getHisRefundCount() {
        return hisRefundCount;
    }

    public void setHisRefundCount(Integer hisRefundCount) {
        this.hisRefundCount = hisRefundCount;
    }

    @Basic
    @Column(name = "his_count")
    public Integer getHisCount() {
        return hisCount;
    }

    public void setHisCount(Integer hisCount) {
        this.hisCount = hisCount;
    }

    @Basic
    @Column(name = "flat_amt")
    public BigDecimal getFlatAmt() {
        return flatAmt;
    }

    public void setFlatAmt(BigDecimal flatAmt) {
        this.flatAmt = flatAmt;
    }

    @Basic
    @Column(name = "many_amt")
    public BigDecimal getManyAmt() {
        return manyAmt;
    }

    public void setManyAmt(BigDecimal manyAmt) {
        this.manyAmt = manyAmt;
    }

    @Basic
    @Column(name = "short_amt")
    public BigDecimal getShortAmt() {
        return shortAmt;
    }

    public void setShortAmt(BigDecimal shortAmt) {
        this.shortAmt = shortAmt;
    }

    @Basic
    @Column(name = "short_doubt_amt")
    public BigDecimal getShortDoubtAmt() {
        return shortDoubtAmt;
    }

    public void setShortDoubtAmt(BigDecimal shortDoubtAmt) {
        this.shortDoubtAmt = shortDoubtAmt;
    }

    @Basic
    @Column(name = "many_doubt_amt")
    public BigDecimal getManyDoubtAmt() {
        return manyDoubtAmt;
    }

    public void setManyDoubtAmt(BigDecimal manyDoubtAmt) {
        this.manyDoubtAmt = manyDoubtAmt;
    }

    @Basic
    @Column(name = "front_many_amt")
    public BigDecimal getFrontManyAmt() {
        return frontManyAmt;
    }

    public void setFrontManyAmt(BigDecimal frontManyAmt) {
        this.frontManyAmt = frontManyAmt;
    }

    @Basic
    @Column(name = "front_short_amt")
    public BigDecimal getFrontShortAmt() {
        return frontShortAmt;
    }

    public void setFrontShortAmt(BigDecimal frontShortAmt) {
        this.frontShortAmt = frontShortAmt;
    }

    @Basic
    @Column(name = "process_amt")
    public BigDecimal getProcessAmt() {
        return processAmt;
    }

    public void setProcessAmt(BigDecimal processAmt) {
        this.processAmt = processAmt;
    }

    @Basic
    @Column(name = "many_count")
    public Integer getManyCount() {
        return manyCount;
    }

    public void setManyCount(Integer manyCount) {
        this.manyCount = manyCount;
    }

    @Basic
    @Column(name = "short_count")
    public Integer getShortCount() {
        return shortCount;
    }

    public void setShortCount(Integer shortCount) {
        this.shortCount = shortCount;
    }

    @Basic
    @Column(name = "bill_status")
    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }

    @Basic
    @Column(name = "bill_result")
    public Integer getBillResult() {
        return billResult;
    }

    public void setBillResult(Integer billResult) {
        this.billResult = billResult;
    }

    @Basic
    @Column(name = "bill_mode")
    public Integer getBillMode() {
        return billMode;
    }

    public void setBillMode(Integer billMode) {
        this.billMode = billMode;
    }

    @Basic
    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

   @Basic
    @CreatedDate
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @LastModifiedDate
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
    @Basic
    @Column(name = "batch_channel")
    public String getBatchChannel() {
        return batchChannel;
    }

    public void setBatchChannel(String batchChannel) {
        this.batchChannel = batchChannel;
    }

    @Basic
    @Column(name = "batch_channel_name")
    public String getBatchChannelName() {
        return batchChannelName;
    }

    public void setBatchChannelName(String batchChannelName) {
        this.batchChannelName = batchChannelName;
    }

    @Override
    public String toString() {
        return "BatchEntity{" +
                "id=" + id +
                ", batchState=" + batchState +
                ", billDate='" + billDate + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", hisId=" + hisId +
                ", hisName='" + hisName + '\'' +
                ", channel='" + channel + '\'' +
                ", channelName='" + channelName + '\'' +
                ", serivceAmt=" + serivceAmt +
                ", settleAmt=" + settleAmt +
                ", settleRefundAmt=" + settleRefundAmt +
                ", settleRefundCount=" + settleRefundCount +
                ", settleCount=" + settleCount +
                ", tradeAmt=" + tradeAmt +
                ", tradeRefundAmt=" + tradeRefundAmt +
                ", tradeCount=" + tradeCount +
                ", tradeRefundCount=" + tradeRefundCount +
                ", hisAmt=" + hisAmt +
                ", hisRefundAmt=" + hisRefundAmt +
                ", hisRefundCount=" + hisRefundCount +
                ", hisCount=" + hisCount +
                ", flatAmt=" + flatAmt +
                ", manyAmt=" + manyAmt +
                ", shortAmt=" + shortAmt +
                ", shortDoubtAmt=" + shortDoubtAmt +
                ", manyDoubtAmt=" + manyDoubtAmt +
                ", frontManyAmt=" + frontManyAmt +
                ", frontShortAmt=" + frontShortAmt +
                ", processAmt=" + processAmt +
                ", manyCount=" + manyCount +
                ", shortCount=" + shortCount +
                ", billStatus=" + billStatus +
                ", billResult=" + billResult +
                ", memo='" + memo + '\'' +
                ", bizSource='" + bizSource + '\'' +
                ", billMode=" + billMode +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
