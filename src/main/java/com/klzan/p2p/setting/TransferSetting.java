/*
 * Copyright 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.setting;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Setting - 债权转让设置
 */
public class TransferSetting implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 982827572983485140L;

    /** 是否开启债权转让 */
    private Boolean transferEnable;

    /** 转让冻结时间 */
    private Integer forzenDays;

    /** 转让可购时间 */
    private Integer purchaseDays;

    public Boolean getTransferEnable() {
        return transferEnable;
    }

    public void setTransferEnable(Boolean transferEnable) {
        this.transferEnable = transferEnable;
    }

    public Integer getForzenDays() {
        return forzenDays;
    }

    public void setForzenDays(Integer forzenDays) {
        this.forzenDays = forzenDays;
    }

    public Integer getPurchaseDays() {
        return purchaseDays;
    }

    public void setPurchaseDays(Integer purchaseDays) {
        this.purchaseDays = purchaseDays;
    }
}