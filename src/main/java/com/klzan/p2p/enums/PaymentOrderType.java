package com.klzan.p2p.enums;

import com.klzan.plugin.pay.BusinessType;

/**
 * 订单类型
 */
public enum PaymentOrderType implements IEnum {
    OPEN_ACCOUNT("开户", BusinessType.OPEN_ACCOUNT),
    OPEN_ACCOUNT_QUERY("开户查询", BusinessType.OPEN_ACCOUNT),

    USER_LOGIN("登录", BusinessType.USER_LOGIN),

    USER_INFO_QUERY("账户查询", BusinessType.COMMON_QUERY),

    TRADE_QUERY("交易查询", BusinessType.COMMON_QUERY),

    BALANCE_QUERY("余额查询", BusinessType.COMMON_QUERY),

    RECHARGE("充值", BusinessType.RECHARGE),
    RECHARGE_QUERY("充值查询", BusinessType.RECHARGE),

    BINDCARD_BIND("银行卡绑定", BusinessType.RECHARGE),
    BINDCARD_UNBIND("银行卡解绑", BusinessType.RECHARGE),
    BINDCARD_QUERY("绑卡查询", BusinessType.RECHARGE),

    SIGNED("签约", BusinessType.RECHARGE),
    TERMINATION("解约", BusinessType.RECHARGE),
    SIGNED_QUERY("签约查询", BusinessType.RECHARGE),

    ORG_TRANSFER("机构支付账户单笔转账", BusinessType.RECHARGE),
    ORG_TRANSFER_QUERY("机构支付账户单笔转账查询", BusinessType.RECHARGE),

    MOBILE_RECHARGE("充值", BusinessType.MOBILE_RECHARGE),

    WITHDRAWAL("提现", BusinessType.WITHDRAW),
    WITHDRAWAL_QUERY("提现查询", BusinessType.WITHDRAW),

    REGIST_PROJECT("标的登记", BusinessType.REG_PROJECT),

    ASSURE_PROJECT("追加登记", BusinessType.ASSURE_PROJECT),

    INVESTMENT("投资", BusinessType.FROZEN),
    INVESTMENT_QUERY("投资查询", BusinessType.FROZEN),

    MOBILE_INVESTMENT("投资", BusinessType.FROZEN),

    AUTO_INVESTMENT("自动投资", BusinessType.AUTO_SIGN),
    AUTO_INVESTMENT_QUERY("自动投资查询", BusinessType.AUTO_SIGN),
    AUTO_INVESTMENT_SIGN("自动投资签约", BusinessType.AUTO_SIGN),

    COMB_FREEZE("红包", BusinessType.COMB_FREEZE),

    INVESTMENT_CANCEL("撤销投资", BusinessType.UNFREEZE),

    LENDING("出借", BusinessType.TRANSFER),
    LENDING_FEE("出借服务费", BusinessType.TRANSFER),

    LENDING_CANCEL("撤销出借", BusinessType.UNFREEZE),

    REPAYMENT_FROZEN("还款冻结", BusinessType.FROZEN),
    REPAYMENT("还款", BusinessType.TRANSFER),
    REPAYMENT_QUERY("还款查询", BusinessType.TRANSFER),
    REPAYMENT_FEE("还款服务费", BusinessType.TRANSFER),
    RECOVERY("回款", BusinessType.TRANSFER),
    RECOVERY_FEE("回款服务费", BusinessType.TRANSFER),

    SETTLEMENT_BATCH("结算", BusinessType.TRANSFER),
    SETTLEMENT_BATCH_QUERY("结算查询", BusinessType.TRANSFER),

    /** 债权转让 */
    TRANSFER_FROZEN("债权转让冻结", BusinessType.FROZEN),
    TRANSFER("债权转让", BusinessType.TRANSFER),

    /** 债权转让服务费 */
    TRANSFER_FEE("债权转让服务费"),

    AUTO_REPAYMENT_SIGN("自动还款签约", BusinessType.AUTO_SIGN),

    REPAYMENT_EARLY_FROZEN("提前还款冻结", BusinessType.FROZEN),
    REPAYMENT_EARLY("提前还款", BusinessType.TRANSFER),
    REPAYMENT_EARLY_FEE("提前还款服务费", BusinessType.TRANSFER),
    RECOVERY_EARLY("提前回款", BusinessType.TRANSFER),
    RECOVERY_EARLY_FEE("提前回款服务费", BusinessType.TRANSFER),

    REPAYMENT_SETTLEMENT("还款结算"),

    REFERRAL_FEE_SETTLEMENT_FROZEN("推荐费结算冻结", BusinessType.FROZEN),
    REFERRAL_FEE_SETTLEMENT("推荐费结算", BusinessType.UNFREEZE),

    PALTFORM_SERVICE_FEE("平台收取服务费"),

    COUPON_SETTLEMENT("优惠券结算"),

    INVESTMENT_REBATE_SETTLEMENT("投资返利结算"),

    REFUND("退款"),
    ;

    private String displayName;
    private BusinessType businessType;

    PaymentOrderType(String displayName) {
        this.displayName = displayName;
    }


    PaymentOrderType(String displayName, BusinessType businessType) {
        this.displayName = displayName;
        this.businessType = businessType;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }
}