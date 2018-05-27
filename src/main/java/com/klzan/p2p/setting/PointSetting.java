/*
 * Copyright 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.setting;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Setting - 积分设置
 */
public class PointSetting implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 982827572983485140L;

    /** 注册送积分开启 */
    private Boolean registEnable;

    /** 注册积分 */
    private Integer registPoints;

    /** 签到送积分开启 */
    private Boolean signInEnable;

    /** 签到积分 */
    private Integer signInPoints;

    /** 还款送积分开启 */
    private Boolean repayEnable;

    /** 投资送积分开启 */
    private Boolean investEnable;

    /** 推荐送积分开启 */
    private Boolean referralEnable;

    /** 推荐人积分 */
    private Integer referrerPoints;

    /** 被推荐人积分 */
    private Integer referralPoints;

    public Boolean getRegistEnable() {
        return registEnable;
    }

    public void setRegistEnable(Boolean registEnable) {
        this.registEnable = registEnable;
    }

    public Integer getRegistPoints() {
        return registPoints;
    }

    public void setRegistPoints(Integer registPoints) {
        this.registPoints = registPoints;
    }

    public Boolean getSignInEnable() {
        return signInEnable;
    }

    public void setSignInEnable(Boolean signInEnable) {
        this.signInEnable = signInEnable;
    }

    public Integer getSignInPoints() {
        return signInPoints;
    }

    public void setSignInPoints(Integer signInPoints) {
        this.signInPoints = signInPoints;
    }

    public Boolean getRepayEnable() {
        return repayEnable;
    }

    public void setRepayEnable(Boolean repayEnable) {
        this.repayEnable = repayEnable;
    }

    public Boolean getInvestEnable() {
        return investEnable;
    }

    public void setInvestEnable(Boolean investEnable) {
        this.investEnable = investEnable;
    }

    public Boolean getReferralEnable() {
        return referralEnable;
    }

    public void setReferralEnable(Boolean referralEnable) {
        this.referralEnable = referralEnable;
    }

    public Integer getReferrerPoints() {
        return referrerPoints;
    }

    public void setReferrerPoints(Integer referrerPoints) {
        this.referrerPoints = referrerPoints;
    }

    public Integer getReferralPoints() {
        return referralPoints;
    }

    public void setReferralPoints(Integer referralPoints) {
        this.referralPoints = referralPoints;
    }
}