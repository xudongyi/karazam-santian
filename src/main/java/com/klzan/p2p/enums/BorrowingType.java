package com.klzan.p2p.enums;

/**
 * 借款类型
 */
public enum BorrowingType implements IEnum {

    CREDIT("信用借款", "车商贷"),

    GUARANTEE("担保借款", "质押贷"),

    MORTGAGE("抵押借款", "抵押贷"),

    TRANSFER("转让借款", "转让借款");

    private String displayName;
    private String alias;

    BorrowingType(String displayName, String alias) {
        this.displayName = displayName;
        this.alias = alias;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public String getAlias() {
        return alias;
    }
}
