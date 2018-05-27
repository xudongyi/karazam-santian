///*
// * Copyright (c) 2016 klzan.com. All rights reserved.
// * Support: http://www.klzan.com
// */
//
//package com.klzan.plugin.pay.common;
//
//import com.klzan.core.Result;
//import com.klzan.plugin.pay.common.module.PayModule;
//import com.klzan.plugin.pay.cpcn.service.CpcnPayService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 支付接口路由
// * 注：处理相关业务、路由具体接口
// */
//@Service
//@Transactional
//public class PayRouteService implements PayService{
//
//    public static Map map = new HashMap();
//
//    protected static Logger logger = LoggerFactory.getLogger(PayRouteService.class);
//
//    @Autowired
//    private CpcnPayService cpcnPayService;
//    @Autowired
//    private CpcnPayService cpcnPayServiceB;
//    @Autowired
//    private CpcnPayService cpcnPayServiceC;
//
//    @Override
//    public Result invoking(PayModule payRequest) {
//
//        // 接口调用
//        Result result;
//        if(true){
//            result = payRequest.invoking();
//        }
//
//        return result;
//    }
//
//    @Override
//    public Result notice(PayModule payRequest) {
//        return payRequest.notice();
//    }
//
//
//}
