//package com.klzan.plugin.pay.common.module;
//
//import com.klzan.plugin.pay.common.PayPortal;
//import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
//import payment.api.tx.TxBaseRequest;
//import payment.api.tx.p2p.Tx3236Request;
//
///**
// * 项目结算查询
// * Created by suhao Date: 2017/11/22 Time: 14:45
// *
// * @version: 1.0
// */
//public class PayProjectSettlementQueryModule extends PayModule {
//
//    @Override
//    public PayPortal getPayPortal() {
//        return PayPortal.project_settlement_query;
//    }
//
//    @Override
//    public TxBaseRequest getReqParam() {
//        String serialNumber = "";
//
//        // 创建交易请求对象
//        Tx3236Request request = new Tx3236Request();
//        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
//        request.setSerialNumber(serialNumber);
//
//        return request;
//    }
//
//}
