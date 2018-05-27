package com.klzan.plugin.pay;

/**
 * Created by suhao on 2017/3/14.
 */
public enum BusinessType {
    OPEN_ACCOUNT("用户开户", "user.register", "redirect:/uc/security"),
    QUERY_BANK("银行列表", "query.bankQuery"),
    REG_PROJECT("项目登记", "project.regProject"),
    ASSURE_PROJECT("追加登记", "project.assureProject"),
    RECHARGE("充值", "trade.deposit", "redirect:/uc/order"),
    WITHDRAW("提现", "trade.withdraw", "redirect:/uc/order"),
    COMB_FREEZE("红包", "trade.combFreeze"),
    AUTO_SIGN("自动签约", "user.autoSign", "redirect:/uc/security"),
    COMMON_QUERY("查询", "query.commonQuery"),
    FROZEN("冻结", "trade.freeze"),
    UNFREEZE("解冻", "trade.unFreeze"),
    TRANSFER("转账", "trade.transfer"),
    USER_LOGIN("用户登录"),
    WITHDRAW_REFUND_TICKET("提现退票"),
    MOBILE_RECHARGE("移动充值","app.deposit"),
    ;

    private String displayName;
    private String operationType;
    private String redirectPath;

    BusinessType(String displayName) {
        this.displayName = displayName;
    }

    BusinessType(String displayName, String operationType) {
        this.displayName = displayName;
        this.operationType = operationType;
    }

    BusinessType(String displayName, String operationType, String redirectPath) {
        this.displayName = displayName;
        this.operationType = operationType;
        this.redirectPath = redirectPath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getOperationType() {
        return operationType;
    }

    public String getRedirectPath() {
        return redirectPath;
    }
}
