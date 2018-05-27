/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.enums;

public enum MaterialType implements IEnum {

    CAR_PICTURE("车辆图片"),

    OTHERS("其他");

    private String displayName;

    MaterialType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}