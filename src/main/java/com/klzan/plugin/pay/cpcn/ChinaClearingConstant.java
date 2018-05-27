package com.klzan.plugin.pay.cpcn;

/**
 * 常量 - 中金托管
 */
public class ChinaClearingConstant {

    /** 1111-用户类型 */
    public static class UserType {
        /* 11-个人用户 */
        public static final String personal = "11";
        /* 12-企业用户 */
        public static final String enterprise = "12";
    }

    /** 1151-签约类型 */
    public static class AgreementType {
        /* 10-支付账户扣款 */
        public static final String a = "10";
        /* 20-自动投资签约 */
        public static final String b = "20";
    }

    /** 1211-付息方式 */
    public static class InterestPayType {
        /* 11-到期与本金一起归还 */
        public static final String last_period = "0";
        /* 12-每月固定日期支付 */
        public static final String each_period_fixed_day = "1";
        /* 12-每月不确定日期支付 */
        public static final String each_period_indefinite_date = "2";
    }

    /** 1211-融资人账户类型 */
    public static class GuaranteeAccountType {
        /* 0-无担保人 */
        public static final String none = "0";
        /* 11-个人支付账户 */
        public static final String personal = "11";
        /* 12-企业支付账户 */
        public static final String enterprise = "12";
    }

    /** 1235-转账用途 */
    public static class TransferUsage {
        /* 10-P2P平台服务费 */
        public static final String platform_fee = "10";
        /* 20-担保公司担保费 */
        public static final String guarantee_fee = "20";
    }

    /** 1237-结算类型 1241-还款类型 */
    public static class SettlementType {
        /* 10-投资人 */
        public static final String investor = "10";
        /* 20-融资人 */
        public static final String financier = "20";
        /* 30-担保人 */
        public static final String guarantee = "30";
        /* 40-P2P平台方 */
        public static final String platform = "40";
        /* 50-债权人 */
        public static final String creditor = "50";
    }

    /** 1241-还款方式 */
    public static class RepaymentWay {
        /* 10-银行卡还款 */
        public static final String bank_card = "10";
        /* 20-支付账户还款 */
        public static final String pay_account = "20";
    }



    /** 2001-账户类型 */
    public static class AccountType2001 {
        /* 11-个人账户 */
        public static final String personal = "11";
        /* 12-企业账户 */
        public static final String enterprise = "12";
        /* 21-机构账户 */
        public static final String organization = "21";
    }
    /** 2001-订单类型 */
    public static class OrderType2001 {
        /* 10-快捷充值     */
        public static final String QUICK_RECHARGE = "10";
        /* 11-B2C充值 */
        public static final String B2C_RECHARGE = "11";
        /* 12-B2B充值 */
        public static final String B2B_RECHARGE = "12";
        /* 20-支付账户还款 */
        public static final String ACC_REPAYMENT = "20";
        /* 21-银行卡还款 */
        public static final String BANK_REPAYMENT = "21";
    }
    /** 2001-订单类型 */
    public static class Status2001 {
        /* 10-成功     */
        public static final String success = "10";
        /* 20-失败 */
        public static final String failure = "20";
    }

    public static String getAccountType2001Des(String accountType){
        String accountTypeDes = "";
        if(accountType != null){
            if(accountType.equals(AccountType2001.personal)){
                accountTypeDes = "个人账户";
            }
            if(accountType.equals(AccountType2001.enterprise)){
                accountTypeDes = "企业账户";
            }
            if(accountType.equals(AccountType2001.organization)){
                accountTypeDes = "机构账户";
            }
        }
        return accountTypeDes;
    }

    public static String getOrderType2001Des(String orderType){
        String orderTypeDes = "";
        if(orderType != null){
            if(orderType.equals(OrderType2001.QUICK_RECHARGE)){
                orderTypeDes = "快捷充值";
            }
            if(orderType.equals(OrderType2001.B2C_RECHARGE)){
                orderTypeDes = "B2C充值";
            }
            if(orderType.equals(OrderType2001.B2B_RECHARGE)){
                orderTypeDes = "B2B充值";
            }
            if(orderType.equals(OrderType2001.ACC_REPAYMENT)){
                orderTypeDes = "支付账户还款";
            }
            if(orderType.equals(OrderType2001.BANK_REPAYMENT)){
                orderTypeDes = "银行卡还款";
            }
        }
        return orderTypeDes;
    }

    public static String getStatus2001Des(String status){
        String statusDes = "";
        if(status != null){
            if(status.equals(Status2001.success)){
                statusDes = "成功";
            }
            if(status.equals(Status2001.failure)){
                statusDes = "失败";
            }
        }
        return statusDes;
    }

}
