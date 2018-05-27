///*
// * Copyright (c) 2016 klzan.com. All rights reserved.
// * Support: http://www.klzan.com
// */
//
//package com.klzan.plugin.pay.cpcn.service;
//
//import com.klzan.core.Result;
//import com.klzan.p2p.enums.PaymentOrderType;
//import com.klzan.p2p.model.PaymentOrder;
//import com.klzan.p2p.service.capital.PaymentOrderService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * 支付订单查询 - 中金托管
// */
//@Component
//public class CpcnPayQueryService {
//
//    protected static Logger logger = LoggerFactory.getLogger(CpcnPayQueryService.class);
//
//    @Autowired
//    private CpcnPayService payService;
//    @Autowired
//    private PaymentOrderService paymentOrderService;
//
//    /**
//     * 类型支持检查
//     * @param paymentOrder
//     * @return
//     */
//    public boolean checkType(PaymentOrder paymentOrder){
//        PaymentOrderType[] types = PaymentOrderType.values();
//        for(PaymentOrderType type : types){
//            if(paymentOrder.getType().equals(type)){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 订单查询
//     * @param sn
//     * @return
//     */
//    public Result query(String sn){
//        Result result = null;
//        try {
//            if(sn == null){
//                return Result.error("订单号不存在");
//            }
//            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
//            if(paymentOrder == null){
//                return Result.error("订单不存在");
//            }
//            if(!this.checkType(paymentOrder)){
//                return Result.error("非富民订单");
//            }
//            switch (paymentOrder.getType()){
////                case cpcn_open_account:{
////                    result = payService.openAccountQuery(paymentOrder.getUserId());
////                    break;
////                }case cpcn_bank_account_bind:{
////                    Map<String, Object> params = JsonUtils.toObject(paymentOrder.getBusi_req_params(), HashMap.class);
////                    String bindingNo = (String)params.get("bindingNo");
////                    result = payService.bankAccountBindQuery(bindingNo);
////                    break;
////                }case cpcn_bank_account_unbind:{
////                    Map<String, Object> params = JsonUtils.toObject(paymentOrder.getBusi_req_params(), HashMap.class);
////                    String bindingNo = (String)params.get("bindingNo");
////                    result = payService.bankAccountBindQuery(bindingNo);
////                    break;
////                }case cpcn_recharge:{
////                    result = payService.rechargeQuery(sn);
////                    break;
////                }case cpcn_withdraw:{
////                    result = payService.withdrawQuery(sn);
////                    break;
////                }case cpcn_payment_account_signed:{
////                    Map<String, Object> params = JsonUtils.toObject(paymentOrder.getBusi_req_params(), HashMap.class);
////                    String agreementNo = (String)params.get("agreementNo");
////                    result = payService.paymentAccountSignedQuery(agreementNo);
////                    break;
////                }case cpcn_payment_account_termination:{
////                    Map<String, Object> params = JsonUtils.toObject(paymentOrder.getBusi_req_params(), HashMap.class);
////                    String agreementNo = (String)params.get("agreementNo");
////                    result = payService.paymentAccountSignedQuery(agreementNo);
////                    break;
//////                }case cpcn_payment_account_org_transfer:{
//////                    result = payService.paymentAccountOrgTransferQuery(sn);
//////                    break;
////                }case cpcn_project_publish:{
////                    result = payService.projectPublishQuery(paymentOrder.getBorrowing());
////                    break;
////                }case cpcn_invest:{
////                    result = payService.investQuery(sn);
////                    break;
////                }case cpcn_transfer:{
////                    result = payService.transferQuery(sn);
////                    break;
//////                }case cpcn_refund:{
//////                    result = payService.refundQuery(sn);
//////                    break;
//////                }case cpcn_project_transfer_settlement:{
//////                    result = payService.projectTransferSettlementQuery(sn);
//////                    break;
////                }case cpcn_project_transfer_settlement_batch:{
////                    result = payService.projectTransferSettlementBatchQuery(sn);
////                    break;
////                }case cpcn_repayment:{
////                    result = payService.repaymentQuery(sn);
////                    break;
////                }default:{
////                    result = Result.error(String.format("订单[%s]类型[%s]不支持,或非富民订单", sn, paymentOrder.getType()));
////                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            result = Result.error();
//        }
//        return result;
//    }
//
//
//}
