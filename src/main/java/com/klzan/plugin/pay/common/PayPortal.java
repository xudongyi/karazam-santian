package com.klzan.plugin.pay.common;


import com.klzan.core.exception.PayException;
import com.klzan.core.util.SpringUtils;
import com.klzan.plugin.pay.common.business.*;
import com.klzan.plugin.pay.common.dto.*;
import com.klzan.plugin.pay.common.module.*;

/**
 * 支付入口
 */
public enum PayPortal {

    // 操作类
    open_account("开户", "0000", true, true, PayOpenAccountModule.class, BusinessOpenAccountService.class, UserInfoRequest.class),
    login("登录", "0000", true, true, PayLoginModule.class, null, UserInfoRequest.class),
//    add_withdraw("提现银行卡添加", "0000", true, true, PayLoginModule.class, null, UserInfoRequest.class),
//    delete_withdraw("提现银行卡删除", "0000", true, true, PayLoginModule.class, null, UserInfoRequest.class),
    signed("支付账户签约", "0000", true, true, PaySignedModule.class, BusinessSignedService.class, SignedRequest.class),
    termination("支付账户解约", "0000", false, false, PayTerminationModule.class, BusinessTerminationService.class, TerminationRequest.class),
    bankcard_bind("用户支付账户银行卡绑定", "0000", true, true, PayBankcardBindModule.class, BusinessBankcardBindService.class, BankcardBindRequest.class),
    bankcard_unbind("用户支付账户银行卡解绑", "0000", true, true, PayBankcardUnbindModule.class, BusinessBankcardUnbindService.class, UserInfoRequest.class),
    org_transfer("机构支付账户单笔转账", "0000", false, true, PayOrgTransferModule.class, BusinessOrgTransferService.class, OrgTransferRequest.class),

    // 支付类
    recharge("充值", "0000", true, true, PayRechargeModule.class, BusinessRechargeService.class, RechargeRequest.class),
    withdraw("提现", "0000", true, true, PayWithdrawModule.class, BusinessWithdrawService.class, WithdrawRequest.class),
    invest("P2P项目支付", "0000", true, true, PayInvestModule.class, BusinessInvestmentService.class, InvestmentRequest.class),
    invest_auto("P2P项目自动投资", "0000", false, true, PayInvestAutoModule.class, BusinessInvestmentAutoService.class, InvestmentAutoRequest.class),
//    project_settlement("P2P项目结算", "0000", false, true, PayProjectSettlementModule.class, BusinessProjectSettlementService.class, ProjectSettlementRequest.class),
    repayment("P2P项目还款", "0000", false, true, PayRepaymentModule.class, BusinessRepaymentService.class, RepaymentRequest.class),
//    project_transfer_settlement("P2P项目转账结算", "0000", false, true, PayProjectTransferSettlementModule.class, BusinessProjectTransferSettlementService.class, ProjectTransferSettlementRequest.class),
    project_settlement_batch("P2P项目批量结算", "0000", false, true, PayProjectSettlementBatchModule.class, BusinessProjectSettlementBatchService.class, ProjectSettlementBatchRequest.class),

    // 查询类
    open_account_query("开户查询", "0000", false, false, PayOpenAccountQueryModule.class, BusinessOpenAccountQueryService.class, UserInfoRequest.class),
    account_balance_query("余额查询", "0000", false, true, PayAccountBalanceQueryModule.class, BusinessAccountBalanceQueryService.class, UserInfoRequest.class),
    bankcard_bind_query("银行账户绑定状态查询", "0000", false, true, PayBankcardBindQueryModule.class, BusinessBankcardBindQueryService.class, UserInfoRequest.class),
    recharge_query("充值查询", "0000", false, true, PayRechargeQueryModule.class, BusinessRechargeQueryService.class, SnRequest.class),
    withdraw_query("提现查询", "0000", false, true, PayWithdrawQueryModule.class, BusinessWithdrawQueryService.class, SnRequest.class),
    payment_account_signed_query("用户支付账户签约查询", "0000", false, true, PaySignedQueryModule.class, BusinessSignedQueryService.class, SignedRequest.class),
    invest_auto_query("P2P项目自动投资查询", "0000", false, true, PayInvestAutoQueryModule.class, BusinessInvestmentAutoQueryService.class, SnRequest.class),
    invest_query("P2P项目支付查询", "0000", false, true, PayInvestQueryModule.class, BusinessInvestmentQueryService.class, SnRequest.class),
//    project_settlement_query("P2P项目结算查询", "0000", false, true, PayProjectSettlementQueryModule.class, BusinessProjectSettlementQueryService.class, SnRequest.class),
    repayment_query("P2P项目还款查询", "0000", false, true, PayRepaymentQueryModule.class, BusinessRepaymentQueryService.class, SnRequest.class),
//    project_transfer_settlement_query("P2P项目转账结算查询", "0000", false, true, PayProjectTransferSettlementQueryModule.class, BusinessProjectTransferSettlementQueryService.class, SnRequest.class),
    project_settlement_batch_query("P2P项目批量结算查询", "0000", false, true, PayProjectSettlementBatchQueryModule.class, BusinessProjectSettlementBatchQueryService.class, SnRequest.class),
    org_transfer_query("机构支付账户单笔转账查询", "0000", true, true, PayOrgTransferQueryModule.class, BusinessOrgTransferQueryService.class, SnRequest.class),

    ;

    //名称
    private String alias;

    //编码
    private String code;

    //是否跳转页面
    private Boolean page;

    //是否有通知
    private Boolean notice;

    //支付组件Class
    private Class module;

    //业务组件Class
    private Class service;

    //业务请求参数Class
    private Class request;

    PayPortal(String alias) {
        this.alias = alias;
    }

    PayPortal(String alias, String code, Boolean page, Boolean notice, Class module, Class service, Class request) {
        this.alias = alias;
        this.code = code;
        this.page = page;
        this.notice = notice;
        this.module = module;
        this.service = service;
        this.request = request;
    }

    public String getAlias() {
        return alias;
    }

    public String getCode() {
        return code;
    }

    public Boolean getPage() {
        return page;
    }

    public Boolean getNotice() {
        return notice;
    }

    public Class getServiceClazz() {
        return service;
    }

    public Class getModuleClazz() {
        return module;
    }

    public Class getRequestClazz() {
        return request;
    }

    public PayModule getModuleInstance() {
        try {
            return (PayModule)getModuleClazz().newInstance();
        } catch (InstantiationException e) {
            throw new PayException("支付异常:实例化组件");
        } catch (IllegalAccessException e) {
            throw new PayException("支付异常:实例化组件");
        }
    }

//    public Request getRequestInstance() {
//        try {
//            return (Request)getRequestClazz().newInstance();
//        } catch (InstantiationException e) {
//            throw new PayException("支付异常:实例化组件");
//        } catch (IllegalAccessException e) {
//            throw new PayException("支付异常:实例化组件");
//        }
//    }

    public BusinessService getService(){
        if(getServiceClazz() == null){
//            throw new PayException("支付组件异常：业务Bean未配置");
            return null;
        }
        BusinessService businessService = (BusinessService) SpringUtils.getBean(getServiceClazz());
        if(businessService == null){
            throw new PayException("支付组件异常：业务Bean不存在");
        }
        return businessService;
    }
}
