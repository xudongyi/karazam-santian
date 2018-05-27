///*
// * Copyright (c) 2016 klzan.com. All rights reserved.
// * Support: http://www.klzan.com
// */
//
//package com.klzan.plugin.pay.cpcn.service;
//
//import com.klzan.core.util.DateUtils;
//import com.klzan.core.util.StringUtils;
////import com.klzan.enums.p2p.CpcnAccountState;
////import com.klzan.enums.p2p.InvestmentState;
////import com.klzan.model.p2p.*;
////import com.klzan.model.user.User;
////import com.klzan.oauth.core.util.UserUtils;
//import com.klzan.p2p.dao.borrowing.BorrowingDao;
//import com.klzan.p2p.dao.investment.InvestmentRecordDao;
////import com.klzan.p2p.plugin.china_clearing.ChinaClearingConfig;
////import com.klzan.p2p.plugin.china_clearing.ChinaClearingConstant;
////import com.klzan.p2p.service.customer.CorporationLegalService;
////import com.klzan.p2p.service.customer.HuaShanUserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.inject.Inject;
//import java.math.BigDecimal;
//import java.util.List;
//
///**
// *
// */
//@Component
//public class PayUtils {
//
////    protected static Logger logger = LoggerFactory.getLogger(PayUtils.class);
////
////    @Autowired
////    private HuaShanUserService huaShanUserService;
////    @Autowired
////    private CorporationLegalService corporationLegalService;
////    @Autowired
////    private BorrowingDao borrowingDao;
////    @Inject
////    private InvestmentRecordDao investmentRecordDao;
////
////    /**
////     * 金额转换（元转分）
////     */
////    public static String convertToFen(BigDecimal amount){
////        return String.valueOf(amount.multiply(new BigDecimal(100)).intValue());
////    }
////
////    /**
////     * 金额转换（分转元）
////     */
////    public static BigDecimal convertToYuan(String amount){
////        return new BigDecimal(amount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
////    }
////
////    /**
////     * 金额转换（分转元）
////     */
////    public static BigDecimal convertToYuan(long amount){
////        return new BigDecimal(amount).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
////    }
////
////    /* 用户ID */
////    public Integer getUserId(){
////        return getUserId(null);
////    }
////    public Integer getUserId(Integer userId){
////        User user;
////        if(userId == null){
////            user = UserUtils.getCurrentUser();
////        }else {
////            user = huaShanUserService.find(userId);
////        }
////        return user.getId();
////    }
////
////    /* 用户ID */
////    public String getMobile(){
////        return getMobile(null);
////    }
////    public String getMobile(Integer userId){
////        User user;
////        if(userId == null){
////            user = UserUtils.getCurrentUser();
////        }else {
////            user = huaShanUserService.find(userId);
////        }
////        return user.getMobile();
////    }
////
////    /* 用户类型 */
////    public String getUserType(){
////        return getUserType(null);
////    }
////    public String getUserType(Integer userId){
////        User user;
////        if(userId == null){
////            user = UserUtils.getCurrentUser();
////        }else {
////            user = huaShanUserService.find(userId);
////        }
////        if(user.getCorp()){
////            return ChinaClearingConstant.UserType.enterprise;
////        }
////        return ChinaClearingConstant.UserType.personal;
////    }
////
////    /* 用户支付账户号码 */
////    public String getPaymentAccountNumber(){
////        return getPaymentAccountNumber(null);
////    }
////    public String getPaymentAccountNumber(Integer userId){
////        User user = null;
////        if(userId == null){
////            user = UserUtils.getCurrentUser();
////        }else {
////            user = huaShanUserService.find(userId);
////        }
////        UserFinance userFinance = huaShanUserService.getUserFinance(user.getId());
////        return userFinance.getPaymentAccountNumber();
////    }
////
////    /**
////     * 用户姓名/法人姓名
////     */
////    public String getPaymentAccountUsername(){
////        return getPaymentAccountUsername(null);
////    }
////    public String getPaymentAccountUsername(Integer userId){
////        User user = null;
////        if(userId == null){
////            user = UserUtils.getCurrentUser();
////        }else {
////            user = huaShanUserService.find(userId);
////        }
////        if(user.getCorp()){
////            UserFinance userFinance = huaShanUserService.getUserFinance(user.getId());
////            if(userFinance.getCpcnAccountState().equals(CpcnAccountState.registered)){
////                return userFinance.getPaymentAccountUsername();
////            }
////            CorporationLegal corporationLegal = corporationLegalService.findCorporationLegalByUserId(user.getId());
////            return corporationLegal.getCorporationName();
////        }else {
////            UserInfo userInfo = huaShanUserService.getUserInfo(user.getId());
////            return userInfo.getRealName();
////        }
////    }
////
////    /**
////     * 用户身份证号/法人身份证
////     */
////    public String getIdNo(){
////        return getIdNo(null);
////    }
////    public String getIdNo(Integer userId){
////        User user = null;
////        if(userId == null){
////            user = UserUtils.getCurrentUser();
////        }else {
////            user = huaShanUserService.find(userId);
////        }
////        if(user.getCorp()){
////            CorporationLegal corporationLegal = corporationLegalService.findCorporationLegalByUserId(user.getId());
////            return corporationLegal.getCorporationIdCard();
////        }else {
////            UserInfo userInfo = huaShanUserService.getUserInfo(user.getId());
////            return userInfo.getIdNo();
////        }
////    }
////
////    /**
////     * 通过项目ID生成项目编号
////     */
////    public String generateProjectNo(Integer borrowingId){
////        String projectNo = "";
////        if(borrowingId != null){
////            Borrowing borrowing = borrowingDao.find(borrowingId);
////            if(borrowing != null){
////                projectNo = projectNo + ChinaClearingConfig.INSTITUTION_ID;
////                projectNo = projectNo + DateUtils.format(borrowing.getCreateDate(), DateUtils.DATE_PATTERN_NUMBER);
////                projectNo = projectNo + borrowing.getId().intValue();
////            }
////        }
////        System.out.println(projectNo);
////        return projectNo;
////    }
////
////    /**
////     * 通过项目ID获取项目编号
////     */
////    public String getProjectNo(Integer borrowingId){
////        String projectNo = "";
////        if(borrowingId != null){
////            Borrowing borrowing = borrowingDao.find(borrowingId);
////            if(borrowing != null){
////                projectNo = borrowing.getCpcnProjectNo();
////            }
////        }
////        return projectNo;
////    }
////
////    /**
////     * 通过项目编号获取项目
////     */
////    public Borrowing getBorrowing(String projectNo){
////        Borrowing borrowing = null;
////        if(StringUtils.isNotBlank(projectNo)){
////            borrowing = borrowingDao.findByCpcnProjectNo(projectNo);
////        }
////        return borrowing;
////    }
////
////    /**
////     * 支付投资交易流水号，还款给投资人时需要
////     */
////    public String getPaymentNo(Integer borrowingId, Integer userId){
////        List<InvestmentRecord> investmentRecords = investmentRecordDao.findList(borrowingId, userId);
////        for(InvestmentRecord investmentRecord : investmentRecords){
////            if(investmentRecord.getState().equals(InvestmentState.success) && investmentRecord.getAmount().compareTo(investmentRecord.getTransferedAmount())>0){
////                return investmentRecord.getOrderNo();
////            }
////        }
////        throw new RuntimeException("不存在投资流水号");
////    }
//
//
//}
