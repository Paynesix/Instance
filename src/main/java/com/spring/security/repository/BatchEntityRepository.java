package com.spring.security.repository;

import com.spring.security.model.BatchAllDataEntity;
import com.spring.security.model.BatchEntity;
import com.spring.security.model.BatchStatisticsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface BatchEntityRepository extends JpaRepository<BatchEntity, Long>, JpaSpecificationExecutor<BatchEntity> {


    @Query(value = "select u from BatchEntity u" +
            " where (u.billDate >= :beginBillDate and  u.billDate <= :endBillDate or ( :beginBillDate is null and :endBillDate is null))" +
            " and (u.hisId = :hisId or :hisId is null )" +
            " and (u.channel = :channel or :channel is null )" +
            " and (u.billResult = :billResult or :billResult is null)"+
            " and (u.merchantId = :merchantId or :merchantId is null)")
    Page<BatchEntity> findAllBatchByParams(@Param("beginBillDate") String beginBillDate,
                                           @Param("endBillDate") String endBillDate,
                                           @Param("hisId") Long hisId,
                                           @Param("channel") String channel,
                                           @Param("billResult") Integer billResult,
                                           @Param("merchantId") String merchantId,
                                           Pageable pageable);

    List<BatchEntity> findByMerchantIdAndBillDateAndHisIdAndChannel(String merchantId, String billDate, Long hisId, String channel);


    @Query("select  new com.gzhc365.web.bill.model.BatchStatisticsEntity(u.channel," +
            "sum(u.hisAmt)," +
            "sum(u.hisCount)," +
            "sum(u.hisRefundAmt)," +
            "sum(u.hisRefundCount)," +
            "sum(u.settleAmt)," +
            "sum(u.settleCount)," +
            "sum(u.settleRefundAmt)," +
            "sum(u.settleRefundCount)," +
            "u.merchantId)" +
            " from BatchEntity u " +
            " where (u.billDate >= :beginBillDate and  u.billDate <= :endBillDate or ( :beginBillDate is null and :endBillDate is null))" +
            " and (u.channel = :channel or :channel is null )" +
            " and (u.hisId = :hisId or :hisId is null )" +
            " and (u.merchantId = :merchantId or :merchantId is null )" +
            "group by u.channel,u.merchantId")
    List<BatchStatisticsEntity> sumChannelData(@Param("beginBillDate") String beginBillDate,
                                               @Param("endBillDate") String endBillDate,
                                               @Param("hisId") Long hisId,
                                               @Param("channel") String channel,
                                               @Param("merchantId") String merchantId);


    @Query("select  new com.gzhc365.web.bill.model.BatchAllDataEntity(" +
            "sum(u.hisAmt)," +
            "sum(u.hisCount)," +
            "sum(u.hisRefundAmt)," +
            "sum(u.hisRefundCount)," +
            "sum(u.settleAmt)," +
            "sum(u.settleCount)," +
            "sum(u.settleRefundAmt)," +
            "sum(u.settleRefundCount)," +
            "sum(u.manyCount)," +
            "sum(u.shortCount))" +
            " from BatchEntity u " +
            " where (u.billDate >= :beginBillDate and  u.billDate <= :endBillDate )" +
            " and (u.channel = :channel or :channel is null )" +
            " and (u.hisId = :hisId or :hisId is null )" +
            " and (u.merchantId = :merchantId or :merchantId is null )"+
            " and (u.bizType = :bizType or :bizType is null )"
            )
    BatchAllDataEntity sumAllData(@Param("beginBillDate") String beginBillDate,
                                  @Param("endBillDate") String endBillDate,
                                  @Param("hisId") Long hisId,
                                  @Param("channel") String channel,
                                  @Param("merchantId") String merchantId,
                                  @Param("bizType") String bizType);

    @Query("select  new com.gzhc365.web.bill.model.BatchAllDataEntity(" +
            "u.channel, u.channelName,"+
            "sum(u.hisAmt)," +
            "sum(u.hisCount)," +
            "sum(u.hisRefundAmt)," +
            "sum(u.hisRefundCount)," +
            "sum(u.settleAmt)," +
            "sum(u.settleCount)," +
            "sum(u.settleRefundAmt)," +
            "sum(u.settleRefundCount)," +
            "sum(u.manyCount)," +
            "sum(u.shortCount))" +
            " from BatchEntity u " +
            " where (u.billDate >= :beginBillDate and  u.billDate <= :endBillDate )" +
            " and (u.channel = :channel or :channel is null )" +
            " and (u.batchChannel = :batchChannel or :batchChannel is null )" +
            " and (u.hisId = :hisId or :hisId is null )" +
            " and (u.merchantId = :merchantId or :merchantId is null )"+
            " and (u.bizType = :bizType or :bizType is null )" +
            "group by u.channel,u.channelName"
            )
    List<BatchAllDataEntity> groupSumAllData(@Param("beginBillDate") String beginBillDate,
                                             @Param("endBillDate") String endBillDate,
                                             @Param("hisId") Long hisId,
                                             @Param("channel") String channel,
                                             @Param("batchChannel") String batchChannel,
                                             @Param("merchantId") String merchantId,
                                             @Param("bizType") String bizType);


    @Query(nativeQuery = true, value =
            "select id as id, " +
                    "his_id as hisId, his_name as name, " +
                    "merchant_id as merchantId, merchant_name as merchantName, " +
                    "bill_date as billDate, " +
                    "concat(bill_result,'') as billResult, channel as channel, channel_name as channelName, " +
                    "batch_channel as batchChannel, batch_channel_name as batchChannelName, " +
                    "sum(his_amt) as hisAmt,    sum(his_count) as hisCount,  " +
                    "sum(his_refund_amt) as hisRefundAmt,  sum(his_refund_count) as hisRefundCount, " +
                    "sum(settle_amt) as settleAmt,  sum(settle_count) as settleCount, " +
                    "sum(settle_refund_amt) as settleRefundAmt, sum(settle_refund_count) as settltRefundCount, " +
                    "update_time as updateTime, create_time as createTime " +
                    "from t_billc_bill_batch " +
                    "where bill_date >= :beginBillDate " +
                    "and bill_date <= :endBillDate " +
                    "and (his_id = :hisId or :hisId is null) " +
                    "and (channel = :channel or :channel is null) " +
                    "and (batch_channel = :batchChannel or :batchChannel is null) " +
                    "and (merchant_id= :merchantId or :merchantId is null) " +
                    "and (merchant_name = :merchantName  or :merchantName is null) " +
                    "and (bill_result = :billResult or :billResult is null) " +
                    "group by bill_date, channel  " +
                    "order by create_time desc "
    )
    List<Map> findByBillDateAndChannel(@Param("beginBillDate") String beginBillDate,
                                       @Param("endBillDate") String endBillDate,
                                       @Param("hisId") Long hisId,
                                       @Param("channel") String channel,
                                       @Param("batchChannel") String batchChannel,
                                       @Param("merchantId") String merchantId,
                                       @Param("merchantName") String merchantName,
                                       @Param("billResult") Integer billResult);

    @Query(nativeQuery = true, value =
            "select count(1) from (SELECT 1 " +
                    "from t_billc_bill_batch " +
                    "where bill_date >= :beginBillDate " +
                    "and bill_date <= :endBillDate " +
                    "and (his_id = :hisId or :hisId is null) " +
                    "and (channel = :channel or :channel is null) " +
                    "and (batch_channel = :batchChannel or :batchChannel is null) " +
                    "and (merchant_id= :merchantId or :merchantId is null) " +
                    "and (merchant_name = :merchantName  or :merchantName is null) " +
                    "and (bill_result = :billResult or :billResult is null) " +
                    "group by bill_date, channel ) tab  "
    )
    Integer findBatchCountByBillDateAndChannel(@Param("beginBillDate") String beginBillDate,
                                               @Param("endBillDate") String endBillDate,
                                               @Param("hisId") Long hisId,
                                               @Param("channel") String channel,
                                               @Param("batchChannel") String batchChannel,
                                               @Param("merchantId") String merchantId,
                                               @Param("merchantName") String merchantName,
                                               @Param("billResult") Integer billResult);

}
