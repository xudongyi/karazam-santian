///*
// * Copyright (c) 2016 klzan.com. All rights reserved.
// * Support: http://www.klzan.com
// */
//
//package com.klzan.plugin.pay.cpcn.service;
//
//import com.klzan.core.Result;
////import com.klzan.core.persist.Transactions;
//import com.klzan.core.util.SpringUtils;
////import com.klzan.p2p.plugin.china_clearing.Request;
////import com.klzan.p2p.plugin.china_clearing.TxCodeType;
////import com.klzan.p2p.plugin.china_clearing.business.BusinessService;
//import com.klzan.plugin.pay.cpcn.Request;
//import com.klzan.plugin.pay.cpcn.TxCodeType;
//import com.klzan.plugin.pay.cpcn.business.BusinessService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import payment.api.notice.*;
//import payment.api.tx.TxBaseRequest;
//
//import java.util.Map;
//
///**
// * 中金接口
// */
//@Service
//@Transactional/*(Transactions.P2P)*/
//public class ChinaClearingService {
//
//    protected static Logger logger = LoggerFactory.getLogger(ChinaClearingService.class);
//
////    @Autowired
////    private ChinaClearingPlugin chinaClearingPlugin;
//
//    /**
//     * 获取请求参数
//     * @param type 接口
//     * @param sn 订单号
//     * @param txRequest 接口参数
//     * @return
//     * @throws Exception
//     */
//    public Request processlocal(TxCodeType type, String sn, TxBaseRequest txRequest) throws Exception {
//        return this.processlocal(type, sn, txRequest, null);
//    }
//
//    /**
//     * 获取请求参数
//     * @param type 接口
//     * @param sn 订单号
//     * @param txRequest 接口参数
//     * @param params 业务参数
//     * @return
//     * @throws Exception
//     */
//    public Request processlocal(TxCodeType type, String sn, TxBaseRequest txRequest, Map<String, Object> params) throws Exception {
//
//        BusinessService businessService = getBusinessService(type);
//
//        if(businessService!=null){
//            businessService.before(sn, txRequest, params);
//        }
//
//        Request request_ = ChinaClearingPlugin.launch(txRequest);
//
//        if(businessService!=null) {
//            businessService.after(sn, txRequest, params, null);
//        }
//        return request_;
//
//    }
//
//    /**
//     * 获取请求结果
//     * @param type 接口
//     * @param sn 订单号
//     * @param txRequest 接口参数
//     * @return
//     * @throws Exception
//     */
//    public Result process(TxCodeType type, String sn, TxBaseRequest txRequest) throws Exception {
//        return this.process(type, sn, txRequest, null);
//    }
//
//    /**
//     * 获取请求结果
//     * @param type 接口
//     * @param sn 订单号
//     * @param txRequest 接口参数
//     * @param params 业务参数
//     * @return
//     * @throws Exception
//     */
//    public Result process(TxCodeType type, String sn, TxBaseRequest txRequest, Map<String, Object> params) throws Exception {
//
//        BusinessService businessService = getBusinessService(type);
//
//        if(businessService!=null){
//            businessService.before(sn, txRequest, params);
//        }
//
//        Result result = ChinaClearingPlugin.launch(type, txRequest);
//
//        if(businessService!=null) {
//            result = businessService.after(sn, txRequest, params, result);
//        }
//
//        return result;
//    }
//
//    /**
//     * 通知
//     * @param type 接口
//     * @param sn 订单号
//     * @param notice 通知
//     * @return
//     * @throws Exception
//     */
//    public Result notice(TxCodeType type, String sn, NoticeRequest notice) throws Exception {
//
//        BusinessService businessService = getBusinessService(type);
//        if(businessService!=null) {
//            return businessService.notice(sn, notice);
//        }
//        return null;
//
//    }
//
//    /**
//     * 获取业务bean
//     */
//    public BusinessService getBusinessService(TxCodeType type) throws Exception {
//        if (type.getClazz()!=null && SpringUtils.getBean(type.getClazz())!=null){
//            return (BusinessService) SpringUtils.getBean(type.getClazz());
//        }else{
//            return null;
//        }
//    }
//
//}
