package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 推荐关系表
 */
@Entity
@Table(name = "karazam_referral")
public class Referral extends BaseModel {
    /**
     * 推荐人
     */
    @Column(nullable = false)
    private Integer userId;

    /**
     * 被推荐人
     */
    @Column(nullable = false, updatable = false)
    private Integer reUserId;

    /**
     * 推荐费率
     */
    @Column(nullable = false, precision = 5, scale = 3)
    private BigDecimal referralFeeRate;

    /**
     * 是否有效
     */
    private Boolean available = true;

    public Referral() {
    }

    public Referral(Integer userId, Integer reUserId, BigDecimal referralFeeRate, Boolean available) {
		super();
		this.userId = userId;
		this.reUserId = reUserId;
		this.referralFeeRate = referralFeeRate;
		this.available = available;
	}

    public void update(Integer userId, BigDecimal referralFeeRate, Boolean available) {
		this.userId = userId;
		this.referralFeeRate = referralFeeRate;
		this.available = available;
	}

    public Integer getUserId() {
        return userId;
    }

    public Integer getReUserId() {
        return reUserId;
    }

    public BigDecimal getReferralFeeRate() {
        return referralFeeRate;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setReUserId(Integer reUserId) {
        this.reUserId = reUserId;
    }

    public void setReferralFeeRate(BigDecimal referralFeeRate) {
        this.referralFeeRate = referralFeeRate;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

}
