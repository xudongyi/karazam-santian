//package com.klzan.plugin.pay.common.module;
//
//import com.klzan.plugin.pay.common.PayPortal;
//import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
//import payment.api.tx.TxBaseRequest;
//import payment.api.tx.p2p.Tx3231Request;
//
///**
// * 项目结算
// * Created by suhao Date: 2017/11/22 Time: 14:45
// *
// * @version: 1.0
// */
//public class PayProjectSettlementModule extends PayModule {
//
//    @Override
//    public PayPortal getPayPortal() {
//        return PayPortal.project_settlement;
//    }
//
//    @Override
//    public TxBaseRequest getReqParam() {
//        // 创建交易请求对象
//        String serialNumber = getSn();
//        String projectNo = "";
//        String paymentNo = "";
//        int settlementType = 0;
//        int accountType = 0;
//        String bankId = "";
//        String bankAccountName = "";
//        String bankAccountNumber = "";
//        String bankBranchName = "";
//        String bankProvince = "";
//        String bankCity = "";
//        String paymentAccountName = "";
//        String paymentAccountNumber = "";
//        long amount = Long.parseLong("0");
//        String remark = "";
//        String settlementUsage = "";
//
//        // 创建交易请求对象
//        Tx3231Request request = new Tx3231Request();
//        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
//        request.setSerialNumber(serialNumber);
//        request.setProjectNo(projectNo);
//        request.setPaymentNo(paymentNo);
//        request.setSettlementType(settlementType);
//        request.setAccountType(accountType);
//        request.setBankID(bankId);
//        request.setBankAccountName(bankAccountName);
//        request.setBankAccountNumber(bankAccountNumber);
//        request.setBankBranchName(bankBranchName);
//        request.setBankProvince(bankProvince);
//        request.setBankCity(bankCity);
//        request.setPaymentAccountName(paymentAccountName);
//        request.setPaymentAccountNumber(paymentAccountNumber);
//
//        request.setAmount(amount);
//        request.setRemark(remark);
//
//        request.setSettlementUsage(settlementUsage);
//
//        return request;
//    }
//
//}
