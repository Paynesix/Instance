//package com.spring.security.tools;
//
//import com.gzhc365.unified.pay.constants.PayConstants;
//import com.gzhc365.unified.pay.enums.PayStatusEnum;
//import com.gzhc365.unified.pay.enums.RefundStatusEnum;
//import com.gzhc365.unified.pay.enums.WeixinTradeStatusEnum;
//import org.apache.commons.lang3.StringUtils;
//
///**
// * 支付渠道状态处理工具<br/>
// *
// * @author liujingcheng
// * @version 1.0
// * @date: 2018年1月2日 下午3:29:12
// * @since JDK 1.7
// */
//public class StatusUtils {
//
//    /**
//     * 处理支付宝公用代码错误. <br/>
//     *
//     * @param code
//     * @param subCode
//     * @return
//     * @author liujingcheng
//     * @date: 2018年1月2日 下午3:45:19
//     * @version 1.0
//     */
//    public static String getAlipayBizResultCode(String code, String subCode) {
//
//        if (StringUtils.equals(code, "10000")) {
//            return PayConstants.RESULT_CODE_SUCCESS;
//        }
//        if (StringUtils.equals(code, "20001") && StringUtils.equalsAny(subCode, "aop.invalid-auth-token",
//                "aop.auth-token-time-out", "aop.invalid-app-auth-token", "aop.invalid-app-auth-token-no-api",
//                "aop.app-auth-token-time-out", "aop.no-product-reg-by-partner")) {
//            return PayConstants.RESULT_CODE_FAIL;
//        }
//
//        if (StringUtils.equals(code, "40001") && StringUtils.equalsAny(subCode, "isv.missing-method", "isv.missing-signature",
//                "isv.missing-signature-type", "isv.missing-signature-key", "isv.missing-app-id", "isv.missing-timestamp",
//                "isv.missing-version", "isv.decryption-error-missing-encrypt-type")) {
//            return PayConstants.RESULT_CODE_FAIL;
//        }
//        if (StringUtils.equals(code, "40002")
//                && StringUtils.equalsAny(subCode, "isv.not-support-app-auth", "isv.missing-signature-config",
//                "isv.decryption-error-not-valid-encrypt-key", "isv.decryption-error-not-valid-encrypt-type",
//                "isv.invalid-digest", "isv.invalid-charset", "isv.invalid-timestamp", "isv.invalid-app-id",
//                "isv.invalid-encrypt-type", "isv.invalid-signature", "isv.invalid-signature-type", "isv.invalid-format",
//                "isv.invalid-method", "isv.invalid-file-size", "isv.invalid-file-extension", "isv.invalid-parameter")) {
//            return PayConstants.RESULT_CODE_FAIL;
//        }
//        if (StringUtils.equals(code, "40006")
//                && StringUtils.equalsAny(subCode, "isv.insufficient-isv-permissions", "isv.insufficient-user-permissions")) {
//            return PayConstants.RESULT_CODE_FAIL;
//        }
//        if (StringUtils.equals(code, "40004") && StringUtils.equalsAny(subCode,
//
//                /**** 当面付 *****/
//                "ACQ.INVALID_PARAMETER", "ACQ.MERCHANT_STATUS_NOT_NORMAL", "ACQ.MERCHANT_AGREEMENT_INVALID",
//                "ACQ.MERCHANT_AGREEMENT_NOT_EXIST", "ACQ.AGREEMENT_STATUS_NOT_NORMAL", "ACQ.AGREEMENT_INVALID",
//                "ACQ.AGREEMENT_NOT_EXIST", "ACQ.SUB_MERCHANT_TYPE_INVALID", "ACQ.SUB_MERCHANT_CREATE_FAIL",
//                "ACQ.INVALID_STORE_ID", "ACQ.USER_FACE_PAYMENT_SWITCH_OFF", "ACQ.NO_PAYMENT_INSTRUMENTS_AVAILABLE",
//                "ACQ.PAYMENT_REQUEST_HAS_RISK", "ACQ.ERROR_BUYER_CERTIFY_LEVEL_LIMIT", "ACQ.SELLER_BEEN_BLOCKED",
//                "ACQ.BUYER_PAYMENT_AMOUNT_MONTH_LIMIT_ERROR", "ACQ.BEYOND_PER_RECEIPT_RESTRICTION", "ACQ.BEYOND_PAY_RESTRICTION",
//                "ACQ.BUYER_PAYMENT_AMOUNT_DAY_LIMIT_ERROR", "ACQ.PAYMENT_FAIL", "ACQ.MOBILE_PAYMENT_SWITCH_OFF",
//                "ACQ.PULL_MOBILE_CASHIER_FAIL", "ACQ.BUYER_ENABLE_STATUS_FORBID", "ACQ.TRADE_BUYER_NOT_MATCH",
//                "ACQ.BUYER_SELLER_EQUAL", "ACQ.ERROR_BALANCE_PAYMENT_DISABLE", "ACQ.BUYER_BANKCARD_BALANCE_NOT_ENOUGH",
//                "ACQ.BUYER_BALANCE_NOT_ENOUGH", "ACQ.TRADE_HAS_CLOSE", "ACQ.CONTEXT_INCONSISTENT",
//                "ACQ.PAYMENT_AUTH_CODE_INVALID", "ACQ.TOTAL_FEE_EXCEED", "ACQ.PARTNER_ERROR", "ACQ.EXIST_FORBIDDEN_WORD",
//                "ACQ.ACCESS_FORBIDDEN",
//                /********** 查询 **********/
//                "ACQ.TRADE_NOT_EXIST",
//                /******** 撤销 *******/
//                "ACQ.SELLER_BALANCE_NOT_ENOUGH", "ACQ.REASON_TRADE_BEEN_FREEZEN",
//                /************** 退款 **************/
//                "ACQ.REFUND_AMT_NOT_EQUAL_TOTAL", "ACQ.TRADE_HAS_FINISHED", "ACQ.TRADE_STATUS_ERROR",
//                "ACQ.DISCORDANT_REPEAT_REQUEST", "ACQ.REASON_TRADE_REFUND_FEE_ERR", "ACQ.TRADE_NOT_ALLOW_REFUND")) {
//            return PayConstants.RESULT_CODE_FAIL;
//        }
//
//        return PayConstants.RESULT_CODE_UNKNOW;
//    }
//
//    /**
//     * 判断当前的订单状态是否是最终订单状态. <br/>
//     *
//     * @param orginStatus 原订单状态
//     * @param checkStatus 需要更新的订单状态
//     * @return
//     * @author liujingcheng
//     * @date: 2018年2月26日 下午3:09:26
//     * @version 1.0
//     */
//    public static boolean checkOrderStatusContinue(String orginStatus, String checkStatus) {
//        // 以下四种订单状态不允许再更新状态,非U状态的不允许再更新为U
//        if (StringUtils.equalsAny(orginStatus, PayStatusEnum.CANCELED.getCode(), PayStatusEnum.FAILURE.getCode(),
//                PayStatusEnum.REVOKE.getCode(), PayStatusEnum.SUCCESS.getCode())
//                || (StringUtils.equalsAny(orginStatus)
//                && PayStatusEnum.INIT.getCode().equals(checkStatus))) {
//            return false;
//        }
//        return true;
//
//    }
//
//    /**
//     * 微信的状态转系统状态
//     *
//     * @param wxTradeStatus
//     * @return
//     */
//    public static String transOrderStatus(String wxTradeStatus) {
//        switch (WeixinTradeStatusEnum.toEnum(wxTradeStatus)) {
//            case SUCCESS:
//                return PayStatusEnum.SUCCESS.getCode();
//            case REFUND:
//                return PayStatusEnum.REFUND.getCode();
//            case NOTPAY:
//            case USERPAYING:
//                return PayStatusEnum.PAYING.getCode();
//            case REVOKED:
//                return PayStatusEnum.REVOKE.getCode();
//            case CLOSED:
//                return PayStatusEnum.CANCELED.getCode();
//            case PAYERROR:
//                return PayStatusEnum.FAILURE.getCode();
//            default:
//                return PayStatusEnum.UNKNOW.getCode();
//        }
//    }
//
//    /**
//     * 支付宝状态改成平台状态
//     * @param alipayTradeStatus
//     * @return
//     */
//    public static String transAlipayStatus(String alipayTradeStatus) {
//        if(StringUtils.isBlank(alipayTradeStatus)){
//            return null;
//        }
//        switch (alipayTradeStatus) {
//            case "WAIT_BUYER_PAY":
//                return PayStatusEnum.PAYING.getCode();
//            case "TRADE_CLOSED":
//                return PayStatusEnum.CANCELED.getCode();
//            case "TRADE_SUCCESS":
//                return PayStatusEnum.SUCCESS.getCode();
//            default:
//                return PayStatusEnum.UNKNOW.getCode();
//        }
//    }
//
//    public static String transCrmPayStatus(String status) {
//        if(StringUtils.isBlank(status)){
//            return null;
//        }
//        switch (status) {
//            case "10":
//                return PayStatusEnum.PAYING.getCode();
//            case "20":
//                return PayStatusEnum.SUCCESS.getCode();
//            case "30":
//                return PayStatusEnum.CANCELED.getCode();
//            case "60":
//                return PayStatusEnum.REFUND.getCode();
//            default:
//                return PayStatusEnum.UNKNOW.getCode();
//        }
//    }
//
//    public static String transXFTRefundOrderStatus(String status) {
//        if(StringUtils.isBlank(status)){
//            return null;
//        }
//        switch (status) {
//            case "01":
//                return RefundStatusEnum.FAILURE.getCode();
//            case "09":
//                return RefundStatusEnum.REFUNDING.getCode();
//            case "00":
//                return RefundStatusEnum.SUCCESS.getCode();
//            default:
//                return PayStatusEnum.UNKNOW.getCode();
//        }
//    }
//    public static String transXFTOrderStatus(String status) {
//        if(StringUtils.isBlank(status)){
//            return null;
//        }
////    state   详情
////    00  支付成功
////    01  支付失败
////    03  部分退款
////    04  全部退款
////    09  待支付
////    98  已关闭
//        switch (status) {
//            case "09":
//                return PayStatusEnum.PAYING.getCode();
//            case "00":
//                return PayStatusEnum.SUCCESS.getCode();
//            case "01":
//                return PayStatusEnum.CANCELED.getCode();
//            case "03":
//                return PayStatusEnum.PARTIALSUCCESS.getCode();
//            case "04":
//                return PayStatusEnum.REFUND.getCode();
//            case "06":
//                return PayStatusEnum.REVOKE.getCode();
//            case "98":
//                return PayStatusEnum.CANCELED.getCode();
//            default:
//                return PayStatusEnum.UNKNOW.getCode();
//        }
//    }
//
//    public static String transOutTradeStatus(String tradeStatus) {
//        if (StringUtils.isBlank(tradeStatus)) {
//            return "UNKNOW";
//        }
//        switch (PayStatusEnum.toEnum(tradeStatus)) {
//            case INIT:
//                return "NOTPAY";
//            case PAYING:
//                return "USERPAYING";
//            case REFUND:
//                return "REFUND";
//            case REVOKE:
//                return "REVOKE";
//            case FAILURE:
//                return "FAIL";
//            case SUCCESS:
//                return "SUCCESS";
//            case CANCELED:
//                return "CLOSE";
//            default:
//                return "UNKNOW";
//        }
//    }
//
//    public static String transRefundStatus(String orginStatus) {
//        if (StringUtils.isBlank(orginStatus)) {
//            return "UNKNOW";
//        }
//        switch (RefundStatusEnum.toEnum(orginStatus)) {
//            case SUCCESS:
//                return "SUCCESS";
//            case REFUNDING:
//                return "PROCESSING";
//            case FAILURE:
//                return "FAIL";
//            default:
//                return "UNKNOW";
//
//        }
//    }
//}
