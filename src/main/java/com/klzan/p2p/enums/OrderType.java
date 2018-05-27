package com.klzan.p2p.enums;

/**
 * 平台订单业务类型
 */
public enum OrderType implements IEnum {

    BORROWING("借款", OrderSign.CREDIT), //出借

    INVESTMENT("投资", OrderSign.DEBIT),

    REPAYMENT("还款", OrderSign.DEBIT),

    REPAYMENT_EARLY("提前还款", OrderSign.DEBIT),

    RECOVERY("回款", OrderSign.CREDIT),

    RECOVERY_EARLY("提前回款", OrderSign.CREDIT),

    TRANSFER_IN("债权受让", OrderSign.DEBIT),

    TRANSFER_OUT("债权转让", OrderSign.CREDIT),

    RECHARGE("充值", OrderSign.CREDIT),

    WITHDRAW("提现", OrderSign.DEBIT),

    REFERRAL("推荐", OrderSign.CREDIT),

    COUPON("红包", OrderSign.CREDIT),

    REFUND("退款", OrderSign.CREDIT),

    ;

    private String displayName;
    private OrderSign sign;

    OrderType(String displayName, OrderSign sign) {
        this.displayName = displayName;
        this.sign = sign;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public OrderSign getSign() {
        return sign;
    }
}