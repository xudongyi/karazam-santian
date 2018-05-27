//package com.klzan.plugin.pay.common.module;
//
//import com.klzan.plugin.pay.common.PayPortal;
//import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
//import payment.api.tx.TxBaseRequest;
//import payment.api.tx.p2p.Tx3237Request;
//
///**
// * 项目转账结算
// * Created by suhao Date: 2017/11/22 Time: 14:45
// *
// * @version: 1.0
// */
//public class PayProjectTransferSettlementModule extends PayModule {
//
//    @Override
//    public PayPortal getPayPortal() {
//        return PayPortal.project_transfer_settlement;
//    }
//
//    @Override
//    public TxBaseRequest getReqParam() {
//        String transferNo = "";
//        String projectNo = "";
//        long amount = Long.parseLong("0");
//        String payerPaymentAccountType = "";
//        String payerPaymentAccountName = "";
//        String payerPaymentAccountNumber = "";
//        String payeeAccountType = "";
//        String payeeBankID = "";
//        String payeeBankAccountName = "";
//        String payeeBankAccountNumber = "";
//        String payeeBankBranchName = "";
//        String payeeBankProvince = "";
//        String payeeBankCity = "";
//        String payeePaymentAccountName = "";
//        String payeePaymentAccountNumber = "";
//        String transferUsage = "";
//        String remark = "";
//
//        // 创建交易请求对象
//        Tx3237Request request = new Tx3237Request();
//        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
//        request.setTransferNo(transferNo);
//        request.setProjectNo(projectNo);
//        request.setAmount(amount);
//        request.setPayerPaymentAccountType(payerPaymentAccountType);
//        request.setPayerPaymentAccountName(payerPaymentAccountName);
//        request.setPayerPaymentAccountNumber(payerPaymentAccountNumber);
//        request.setPayeeAccountType(payeeAccountType);
//        request.setPayeeBankID(payeeBankID);
//        request.setPayeeBankAccountName(payeeBankAccountName);
//        request.setPayeeBankAccountNumber(payeeBankAccountNumber);
//        request.setPayeeBankBranchName(payeeBankBranchName);
//        request.setPayeeBankProvince(payeeBankProvince);
//        request.setPayeeBankCity(payeeBankCity);
//        request.setPayeePaymentAccountName(payeePaymentAccountName);
//        request.setPayeePaymentAccountNumber(payeePaymentAccountNumber);
//        request.setTransferUsage(transferUsage);
//        request.setRemark(remark);
//
//        return request;
//    }
//
//}
