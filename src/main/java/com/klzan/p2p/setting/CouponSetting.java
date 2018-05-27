/*
 * Copyright 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.setting;

import java.io.Serializable;

/**
 * Setting - 优惠券设置
 */
public class CouponSetting implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 982827572983485140L;

    /** 是否开启优惠券功能 */
    private Boolean couponEnable;

    public Boolean getCouponEnable() {
        return couponEnable;
    }

    public void setCouponEnable(Boolean couponEnable) {
        this.couponEnable = couponEnable;
    }
}