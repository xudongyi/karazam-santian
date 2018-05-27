package com.klzan.plugin.pay.cpcn;


//import com.klzan.plugin.pay.cpcn.business.*;

/**
 * 接口类型/编号 - 中金托管
 */
public enum TxCodeType {

    open_account("开户", "1111", null),
    open_account_query("开户查询", "1112", null),
//    open_account_notice("开户通知", "1113", CpcnOpenAccountService.class),
//
//    bank_account_bind("银行账户绑定", "1121", CpcnBankAccountBindService.class),
//    bank_account_bind_notice("银行账户绑定状态通知", "1122", CpcnBankAccountBindService.class),
//    bank_account_bind_query("银行账户绑定状态查询", "1123", CpcnBankAccountBindQueryService.class),
//    bank_account_unbind("银行账户解绑", "1125", CpcnBankAccountBindService.class),
//
//    recharge("充值", "1131", CpcnRechargeService.class),
//    recharge_query("充值查询", "1132", CpcnRechargeQueryService.class),
//    recharge_notice("充值成功通知", "1133", CpcnRechargeService.class),
////    recharge_org("机构账户充值", "1135", null),  //虚拟接口
//
//    withdraw("提现", "1141", CpcnWithdrawService.class),
//    withdraw_query("提现查询", "1142", CpcnWithdrawQueryService.class),
//    withdraw_notice("提现通知", "1143", CpcnWithdrawService.class),
////    withdraw_org("机构账户提现", "1145", null),  //虚拟接口
////    withdraw_refund_reverse("提现退款冲正", "1146", null),  //虚拟接口
////    fee_transfer("通道服务费划转", "1148", null),  //虚拟接口
//
//    payment_account_signed("用户支付账户签约", "1151", CpcnPaymentAccountSignedService.class),
//    payment_account_termination("用户支付账户解约", "1152", CpcnPaymentAccountTerminationService.class),
//    payment_account_signed_query("用户支付账户签约查询", "1153", CpcnPaymentAccountSignedQueryService.class),
//    payment_account_signed_notice("用户支付账户签约成功通知", "1154", CpcnPaymentAccountSignedService.class),
//
//    payment_account_org_transfer("机构支付账户单笔转账", "1161", CpcnPaymentAccountOrgTransferService.class),
//    payment_account_org_transfer_query("机构支付账户单笔转账查询", "1162", CpcnPaymentAccountOrgTransferQueryService.class),
////    payment_account_org_transfer_batch("机构支付账户批量转账", "1163", null),  //二期实现
////    payment_account_org_transfer_batch_query("机构支付账户批量转账查询", "1164", null),  //二期实现
//
//    payment_account_balance_batch_query("支付账户余额批量查询", "1171", CpcnPaymentAccountBalanceBatchQueryService.class),
//    payment_account_transaction_detail_query("支付账户交易明细查询", "1172", CpcnPaymentAccountTransactionDetailQueryService.class),
//
//    payment_account_login("支付账户登录", "1181", CpcnLoginService.class),
//
//    project_publish("P2P项目信息发布", "1211", CpcnProjectPublishService.class),
//    project_publish_query("P2P项目信息查询", "1212", CpcnProjectPublishQueryService.class),
    invest("P2P项目支付", "3211", null),
    invest_query("P2P项目支付查询", "3216", null),
    project_settlement("P2P项目结算", "3231", null),
    project_settlement_query("P2P项目结算查询", "3236", null),
    project_transfer_settlement("P2P项目账户转账结算", "3237", null),
    project_transfer_settlement_query("P2P项目账户转账结算查询", "3238", null),
//    invest_notice("P2P支付成功通知", "1223", CpcnInvestService.class),
//
////    invest_auto("P2P项目自动投资", "1225", null),     //二期实现
////    invest_auto_query("P2P项目自动投资查询", "1226", null),     //二期实现
//
//    transfer("P2P项目债权支付", "1227", CpcnTransferService.class),
//    transfer_query("P2P项目债权支付查询", "1228", CpcnTransferQueryService.class),
//
//    refund("P2P项目超募退款", "1233", CpcnRefundService.class),
//    refund_query("P2P项目超募退款查询", "1234", CpcnRefundQueryService.class),
//
//    project_transfer_settlement("P2P项目账户转账结算", "1235", CpcnProjectTransferSettlementService.class),
//    project_transfer_settlement_query("P2P项目账户转账结算查询", "1236", CpcnProjectTransferSettlementQueryService.class),
//
//    project_transfer_settlement_batch("P2P项目批量结算", "1237", CpcnProjectTransferSettlementBatchService.class),
//    project_transfer_settlement_batch_query("P2P项目批量结算查询", "1238", CpcnProjectTransferSettlementBatchQueryService.class),
//
//    repayment("P2P项目还款", "1241", CpcnRepaymentService.class),
//    repayment_query("P2P项目还款查询", "1242", CpcnRepaymentQueryService.class),
//
//    account_checking("交易分页对账", "2001", CpcnAccountCheckingService.class),
//    account_checking_confirm("商户对账确认", "2010", CpcnAccountCheckingConfirmService.class),

    ;

    private String alias;

    private String code;

    private Class clazz;

    TxCodeType(String alias, String code, Class clazz) {
        this.alias = alias;
        this.code = code;
        this.clazz = clazz;
    }

    public String getAlias() {
        return alias;
    }

    public String getCode() {
        return code;
    }

    public Class getClazz() {
        return clazz;
    }
}
