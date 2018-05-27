/*
 * Copyright 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.setting;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Setting - 自动投标设置
 */
public class AutoInvestmentSetting implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 982827572983485140L;

    /** 是否开启自动投标签约 */
    private Boolean autoInvestmentSign;

    /** 是否开启自动投标 */
    private Boolean autoInvestment;

    /** 单笔投标金额最多占标的金额的比例 */
    private BigDecimal autoInvestmentPercent;

    public Boolean getAutoInvestmentSign() {
        return autoInvestmentSign;
    }

    public void setAutoInvestmentSign(Boolean autoInvestmentSign) {
        this.autoInvestmentSign = autoInvestmentSign;
    }

    public Boolean getAutoInvestment() {
        return autoInvestment;
    }

    public void setAutoInvestment(Boolean autoInvestment) {
        this.autoInvestment = autoInvestment;
    }

    public BigDecimal getAutoInvestmentPercent() {
        return autoInvestmentPercent;
    }

    public void setAutoInvestmentPercent(BigDecimal autoInvestmentPercent) {
        this.autoInvestmentPercent = autoInvestmentPercent;
    }
}