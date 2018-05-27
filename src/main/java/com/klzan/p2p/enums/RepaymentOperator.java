package com.klzan.p2p.enums;

/**
 * 还款方式
 */
public enum RepaymentOperator implements IEnum {

    BORROWER("借款人"),

    ADMIN_AGENT("平台代理"),

    ADMIN_INSTEAD("平台代还"),

    ;

    private String displayName;

    RepaymentOperator(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}