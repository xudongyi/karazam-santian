package com.klzan.p2p.enums;

/**
 * 投资状态
 */
public enum InvestmentState implements IEnum {

    INVESTING("投资中"),

    PAID("已冻结"),

    SUCCESS("成功"),

    FAILURE("失败");

    private String displayName;

    InvestmentState(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}