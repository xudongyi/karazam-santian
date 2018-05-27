/*
 * Copyright 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.setting;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Setting - 推荐设置
 */
public class ReferralSetting implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 982827572983485140L;

    /** 提成是否启用 */
    private Boolean referralEnable;

    /** 开始时间 */
    private String beginDate;

    /** 结束时间 */
    private String endDate;

    /** 提成费率 */
    private BigDecimal referralRate;

    /** 提成结算时间 */
    private Integer settlementDays;

    public Boolean getReferralEnable() {
        return referralEnable;
    }

    public void setReferralEnable(Boolean referralEnable) {
        this.referralEnable = referralEnable;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getReferralRate() {
        return referralRate;
    }

    public void setReferralRate(BigDecimal referralRate) {
        this.referralRate = referralRate;
    }

    public Integer getSettlementDays() {
        return settlementDays;
    }

    public void setSettlementDays(Integer settlementDays) {
        this.settlementDays = settlementDays;
    }
}