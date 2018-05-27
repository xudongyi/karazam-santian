///*
// * Copyright (c) 2016 klzan.com. All rights reserved.
// * Support: http://www.klzan.com
// */
//package com.klzan.plugin.pay.cpcn.service;
//
//import com.klzan.core.Result;
//import com.klzan.core.exception.BusinessProcessException;
//import com.klzan.plugin.pay.common.Request;
//import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
//import com.klzan.plugin.pay.cpcn.TxCodeType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import payment.api.tx.p2p.*;
//import payment.api.tx.paymentaccount.Tx4231Request;
//import payment.api.tx.paymentaccount.Tx4232Request;
//
//import java.util.Map;
//
///**
// * 支付调用 - 中金托管
// */
//@Component
//public class CpcnPayService {
//
//    protected static Logger logger = LoggerFactory.getLogger(CpcnPayService.class);
//
////    /**
////     * 开户 4231 页面
////     * @return
////     * @throws Exception
////     */
////    public static Result invoking(Map params){
////        try {
////
////            String methodName = "invoking" + "txcode";
////
//////            Method[] methods = CpcnPayService.class.getDeclaredMethods();
//////            CpcnPayService.class.getDeclaredMethod(methodName).invoke();
////
////            return invoking4231(params);
////        } catch (Exception e) {
////            throw new BusinessProcessException("开户异常");
////        }
////    }
//
//    /**
//     * 开户 4231 页面
////     * @param phoneNumber
////     * @param userName
////     * @param identificationNumber
////     * @param userType
////     * @param sn
//     * @return
//     * @throws Exception
//     */
//    public static Result invoking4231(Map params) {
//        try {
//
//            String phoneNumber = (String) params.get("phoneNumber");
//            String userName = (String) params.get("userName");
//            String identificationNumber = (String) params.get("identificationNumber");
//            String userType = (String) params.get("userType");
//            String sn = (String) params.get("sn");
//
//            Tx4231Request request = new Tx4231Request();
//            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
//            request.setPhoneNumber(phoneNumber);
//            request.setUserName(userName);
//            request.setIdentificationNumber(identificationNumber);
//            request.setUserType(userType);
//            request.setPageURL(ChinaClearingConfig.PAGE_URL + sn);
//            return CpcnPlugin.process(request);
//        } catch (Exception e) {
//            throw new BusinessProcessException("开户异常");
//        }
//    }
//
//    /**
//     * 开户查询 4232
//     * @param phoneNumber
//     * @param userType
//     * @return
//     * @throws Exception
//     */
//    public Result openAccountQuery(String phoneNumber, String userType) {
//        try {
//            Tx4232Request request = new Tx4232Request();
//            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
//            request.setPhoneNumber(phoneNumber);
//            request.setUserType(userType);
//            Result result = CpcnPlugin.process(request, TxCodeType.open_account_query);
//            return result;
//        } catch (Exception e) {
//            throw new BusinessProcessException("开户查询异常");
//        }
//
//    }
//
//    /**
//     * 项目支付-3211
//     * @param projectId 平台项目ID
//     * @param projectNo 项目编号
//     * @param paymentNo 支付交易流水号
//     * @param amount 支付金额，单位：分
//     * @param projectName 项目名称
//     * @param projectScale 项目发行规模,单位：分
//     * @param returnRate 预期年化收益率，1150 代表 11.50%
//     * @param projectPeriod 项目投资期限，单位：天
//     * @param startDate 起息日，格式：yyyyMMdd
//     * @param endDate 到期日，格式：yyyyMMdd
//     * @param loanerPaymentAccountNumber 投资人支付账户号码
//     * @param loaneeAccountType 融资人账户类型： 11=个人账户 12=企业账户 20=支付账户
//     * @param loaneeBankID 融资人银行账户开户行
//     * @param loaneeBankAccountName 融资人银行账户名称
//     * @param loaneeBankAccountNumber 融资人银行账户号码
//     * @param loaneePaymentAccountName 融资人支付账户名称
//     * @param loaneePaymentAccountNumber 融资人支付账户号码
//     * @param guaranteeAccountType 担保人账户类型： 0=无担保人 11=个人账户 12=企业账户 20=支付账户
//     * @param guaranteeBankID 担保人银行账户开户行
//     * @param guaranteeBankAccountName 担保人银行账户名称
//     * @param guaranteeBankAccountNumber 担保人银行账户号码
//     * @param guaranteePaymentAccountName 担保人名称
//     * @param guaranteePaymentAccountNumber 担保人账户
//     * @param investmentType 投资类型： 10=项目直投 20=债权转让
//     * @return {@link Request}
//     */
//    public Result invest(Integer projectId, String projectNo, String paymentNo, long amount, String projectName, String projectScale, int returnRate, int projectPeriod, String startDate, String endDate, String loanerPaymentAccountNumber, int loaneeAccountType, String loaneeBankID, String loaneeBankAccountName, String loaneeBankAccountNumber, String loaneePaymentAccountName, String loaneePaymentAccountNumber, String guaranteeAccountType, String guaranteeBankID, String guaranteeBankAccountName, String guaranteeBankAccountNumber, String guaranteePaymentAccountName, String guaranteePaymentAccountNumber, String investmentType) {
//        try {
//            // 创建交易请求对象
//            Tx3211Request request = new Tx3211Request();
//            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
//            request.setProjectNo(projectNo);
//            request.setPaymentNo(paymentNo);
//            request.setAmount(amount);
//            request.setProjectName(projectName);
//            request.setProjectURL(ChinaClearingConfig.PROJECT_URL + projectId);
//            request.setProjectScale(projectScale);
//            request.setReturnRate(returnRate);
//            request.setProjectPeriod(projectPeriod);
//            request.setStartDate(startDate);
//            request.setEndDate(endDate);
//            request.setLoanerPaymentAccountNumber(loanerPaymentAccountNumber);
//            request.setLoaneeAccountType(loaneeAccountType);
//            request.setLoaneeBankID(loaneeBankID);
//            request.setLoaneeBankAccountName(loaneeBankAccountName);
//            request.setLoaneeBankAccountNumber(loaneeBankAccountNumber);
//            request.setLoaneePaymentAccountName(loaneePaymentAccountName);
//            request.setLoaneePaymentAccountNumber(loaneePaymentAccountNumber);
//            request.setGuaranteeAccountType(guaranteeAccountType);
//            request.setGuaranteeBankID(guaranteeBankID);
//            request.setGuaranteeBankAccountName(guaranteeBankAccountName);
//            request.setGuaranteeBankAccountNumber(guaranteeBankAccountNumber);
//            request.setGuaranteePaymentAccountName(guaranteePaymentAccountName);
//            request.setGuaranteePaymentAccountNumber(guaranteePaymentAccountNumber);
//            request.setPageURL(ChinaClearingConfig.PAGE_URL + paymentNo);
//            request.setInvestmentType(investmentType);
//            /**
//             * 支付方式: 0=全部支付（账户余额+银行卡） 10=账户余额支付 20=银行卡支付
//             */
//            request.setPaymentWay("10");
//
//            return CpcnPlugin.process(request);
//        } catch (Exception e) {
//            throw new BusinessProcessException("项目支付异常");
//        }
//    }
//
//    /**
//     * 项目支付查询-3216
//     * @param paymentNo 支付交易号
//     * @return {@link Result}
//     */
//    public Result investQuery(String paymentNo) {
//        try {
//            // 创建交易请求对象
//            Tx3216Request request = new Tx3216Request();
//            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
//            request.setPaymentNo(paymentNo);
//
//            return CpcnPlugin.process(request, TxCodeType.invest_query);
//        } catch (Exception e) {
//            throw new BusinessProcessException("项目支付查询异常");
//        }
//    }
//
//    /**
//     * P2P项目结算-3231
//     * @param serialNumber 结算交易流水号
//     * @param projectNo 项目编号
//     * @param paymentNo 支付交易流水号，还款给投资人时需要
//     * @param settlementType 结算类型 10=投资人 20=融资人 30=担保人 40=P2P 平台方
//     * @param accountType 账户类型： 11=个人账户 12=企业账户 20=支付账户 （当 SettlementType=40 时，AccountType 必须 是 20） （当 SettlementType=10 时，AccountType 可以 是 11，20） （当 SettlementType=20 或 30 时，AccountType 可以是 11,12 或 20）
//     * @param bankId 银行账户开户行（AccountType=11 或 12 时， 该项必填）
//     * @param bankAccountName 银行账户名称（AccountType=11 或 12 时，该 项必填）
//     * @param bankAccountNumber 银行账户号码（AccountType=11 或 12 时，该 项必填）
//     * @param bankBranchName 银行账户分支行（AccountType=11 或 12 时， 该项必填）
//     * @param bankProvince 省份（AccountType=11 或 12 时，该项必填）
//     * @param bankCity 城市（AccountType=11 或 12 时，该项必填）
//     * @param paymentAccountName 中金支付账户名称（AccountType=20 时，该项 必填）
//     * @param paymentAccountNumber 中金支付账户号码（AccountType=20 时，该项 必填）
//     * @param amount 结算金额，单位：分
//     * @param remark 备注
//     * @param settlementUsage 结算用途： 10=融资人融资款 20=担保公司担保费 30=P2P 平台服务费 40=投资收益 50=投资撤回退款（指募集期内投资人的投资反 悔的主动退款申请） 60=投资超募退款 70=债权转让回款
//     * @return {@link Result}
//     */
//    public Result projectSettlement(String serialNumber, String projectNo, String paymentNo, int settlementType, int accountType, String bankId, String bankAccountName, String bankAccountNumber, String bankBranchName, String bankProvince, String bankCity, String paymentAccountName, String paymentAccountNumber, long amount, String remark, String settlementUsage) {
//        try {
//            // 创建交易请求对象
//            Tx3231Request request = new Tx3231Request();
//            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
//            request.setSerialNumber(serialNumber);
//            request.setProjectNo(projectNo);
//            request.setPaymentNo(paymentNo);
//            request.setSettlementType(settlementType);
//            request.setAccountType(accountType);
//            request.setBankID(bankId);
//            request.setBankAccountName(bankAccountName);
//            request.setBankAccountNumber(bankAccountNumber);
//            request.setBankBranchName(bankBranchName);
//            request.setBankProvince(bankProvince);
//            request.setBankCity(bankCity);
//            request.setPaymentAccountName(paymentAccountName);
//            request.setPaymentAccountNumber(paymentAccountNumber);
//            request.setAmount(amount);
//            request.setRemark(remark);
//            request.setSettlementUsage(settlementUsage);
//
//            return CpcnPlugin.process(request, TxCodeType.project_settlement);
//        } catch (Exception e) {
//            throw new BusinessProcessException("P2P项目结算异常");
//        }
//    }
//
//    /**
//     * P2P项目结算查询-3236
//     * @param serialNumber 结算交易流水号
//     * @return {@link Result}
//     */
//    public Result projectSettlementQuery(String serialNumber) {
//        try {
//            // 创建交易请求对象
//            Tx3231Request request = new Tx3231Request();
//            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
//            request.setSerialNumber(serialNumber);
//
//            return CpcnPlugin.process(request, TxCodeType.project_settlement_query);
//        } catch (Exception e) {
//            throw new BusinessProcessException("P2P项目结算查询异常");
//        }
//    }
//
//    /**
//     * P2P项目账户转账结算
//     * @param transferNo 转账流水号
//     * @param projectNo P2P 项目编号
//     * @param amount 转账金额，单位：分
//     * @param payerPaymentAccountType 转出账户类型： 10=用户支付账户 20=机构支付账户
//     * @param payerPaymentAccountName 转出中金支付账户名称
//     * @param payerPaymentAccountNumber 转出中金支付账户号码
//     * @param payeeAccountType 转入人账户类型： 11=个人账户 12=企业账户 21=机构支付账户 22=用户支付账户
//     * @param payeeBankID 转入人银行账户开户行
//     * @param payeeBankAccountName 转入人银行账户名称
//     * @param payeeBankAccountNumber 转入人银行账户号码
//     * @param payeeBankBranchName 分支行名称
//     * @param payeeBankProvince 分支行省份
//     * @param payeeBankCity 分支行城市
//     * @param payeePaymentAccountName 转入中金支付账户名称
//     * @param payeePaymentAccountNumber 转入中金支付账户号码
//     * @param transferUsage 转账用途： 10=P2P 平台服务费 20=担保公司担保费 30=P2P 平台返现
//     * @param remark 备注
//     * @return {@link Result}
//     */
//    public Result projectTransferSettlement(String transferNo, String projectNo, long amount, String payerPaymentAccountType, String payerPaymentAccountName, String payerPaymentAccountNumber, String payeeAccountType, String payeeBankID, String payeeBankAccountName, String payeeBankAccountNumber, String payeeBankBranchName, String payeeBankProvince, String payeeBankCity, String payeePaymentAccountName, String payeePaymentAccountNumber, String transferUsage, String remark) {
//        try {
//            // 创建交易请求对象
//            Tx3237Request request = new Tx3237Request();
//            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
//            request.setTransferNo(transferNo);
//            request.setProjectNo(projectNo);
//            request.setAmount(amount);
//            request.setPayerPaymentAccountType(payerPaymentAccountType);
//            request.setPayerPaymentAccountName(payerPaymentAccountName);
//            request.setPayerPaymentAccountNumber(payerPaymentAccountNumber);
//            request.setPayeeAccountType(payeeAccountType);
//            request.setPayeeBankID(payeeBankID);
//            request.setPayeeBankAccountName(payeeBankAccountName);
//            request.setPayeeBankAccountNumber(payeeBankAccountNumber);
//            request.setPayeeBankBranchName(payeeBankBranchName);
//            request.setPayeeBankProvince(payeeBankProvince);
//            request.setPayeeBankCity(payeeBankCity);
//            request.setPayeePaymentAccountName(payeePaymentAccountName);
//            request.setPayeePaymentAccountNumber(payeePaymentAccountNumber);
//            request.setTransferUsage(transferUsage);
//            request.setRemark(remark);
//
//            return CpcnPlugin.process(request, TxCodeType.project_transfer_settlement);
//        } catch (Exception e) {
//            throw new BusinessProcessException("P2P项目账户转账结算异常");
//        }
//    }
//
//    /**
//     * P2P项目账户转账结算查询
//     * @param transferNo 转账流水号
//     * @return {@link Result}
//     */
//    public Result projectTransferSettlementQuery(String transferNo) {
//        try {
//            // 创建交易请求对象
//            Tx3238Request request = new Tx3238Request();
//            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
//            request.setTransferNo(transferNo);
//
//            return CpcnPlugin.process(request, TxCodeType.project_transfer_settlement_query);
//        } catch (Exception e) {
//            throw new BusinessProcessException("P2P项目账户转账结算查询异常");
//        }
//    }
//
//}
