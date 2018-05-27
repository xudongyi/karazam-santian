/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.enums;

public enum LinkType implements IEnum {

    BORROWING("借款"),

    OTHERS("其他");

    private String displayName;

    LinkType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}