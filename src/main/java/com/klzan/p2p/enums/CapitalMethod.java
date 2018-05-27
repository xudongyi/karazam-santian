package com.klzan.p2p.enums;

/**
 * 资金方式
 */
public enum CapitalMethod implements IEnum {
    ORG_TRANSFER("机构支付账户单笔转账"),

    ADMIN_RECHARGE("后台扣费"),

    ADMIN_CHARGE("后台转账"),

    RECHARGE("充值"),

    RECHARGE_FEE("充值服务费"),

    WITHDRAW("提现"),

    WITHDRAW_FEE("提现服务费"),

    REFUND("退款"),

    GUARANTEE("担保"),

    INVESTMENT("投资"),

    INVESTMENT_FEE("投资服务费"),

    BORROWING("借款"),

    BORROWING_FEE("借款服务费"),

    REPAYMENT("还款"),

    REPAYMENT_FEE("还款服务费"),

    REPAYMENT_OVERDUE_INTEREST("还款逾期利息"),

    REPAYMENT_OVERDUE_FEE("还款逾期服务费"),

    RECOVERY("回收"),

    RECOVERY_FEE("回收服务费"),

    RECOVERY_OVERDUE_INTEREST("回收逾期利息"),

    RECOVERY_OVERDUE_FEE("回收逾期服务费"),

    REFERRAL_FEE("推荐费"),

    INVESTMENT_REBATE("投资返利"),

    TRANSFER("债权转让"),

    TRANSFEREE("债权受让"),

    TRANSFER_OUT_FEE("转出方服务费"),

    TRANSFER_IN_FEE("转入方服务费"),

    EXPERIENCE("体验标还款"),

    COUPON("优惠券"),

    GUARANTEE_REPAY("担保代偿还款"),

    PLATFORM_TRANSFER("平台划账");

    private String displayName;

    CapitalMethod(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}