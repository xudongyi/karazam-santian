package com.klzan.p2p.model;

import com.klzan.p2p.enums.CouponSource;
import com.klzan.p2p.enums.CouponType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券
 */
@Entity
@Table(name = "karazam_coupon")
public class Coupon extends BaseModel {

    /**
     * 优惠券来源
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CouponSource couponSource;
    /**
     * 优惠券类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CouponType couponType;
    /**
     * 金额是否随机
     */
    private Boolean isRandomAmount;
    /**
     * 随机金额最大值
     */
    private Integer randomAmountMix;
    /**
     * 随机金额最小值
     */
    private Integer randomAmountMax;
    /**
     * 优惠券是否可用
     */
    private Boolean available;

    /**
     * 优惠券面额（现金红包，体验金红包）
     */
    private BigDecimal amount;
    /**
     * 优惠券总数
     */
    @Column(nullable = false)
    private Integer couponNumber;

    /**
     * 是否以优惠券种类的失效日期为准（true:以种类的失效日期为准；false:以具体的用户红包失效日期为准）
     */
    private Boolean availableByCategory;

    /**
     * 该类型红包失效日期
     */
    private Date invalidDate;
    /**
     * 规则
     */
    private String rule;

    public Coupon() {
    }

    public Coupon(CouponSource couponSource,CouponType couponType, Boolean isRandomAmount,Integer randomAmountMix,Integer randomAmountMax,
                  Boolean available, BigDecimal amount, Integer couponNumber, Boolean availableByCategory, Date invalidDate, String rule) {
        this.couponSource = couponSource;
        this.couponType = couponType;
        this.isRandomAmount = isRandomAmount;
        this.randomAmountMix = randomAmountMix;
        this.randomAmountMax = randomAmountMax;
        this.available = available;
        this.amount = amount;
        this.couponNumber = couponNumber;
        this.availableByCategory = availableByCategory;
        this.invalidDate = invalidDate;
        this.rule = rule;
    }

    public Integer getRandomAmountMix() {
        return randomAmountMix;
    }

    public void setRandomAmountMix(Integer randomAmountMix) {
        this.randomAmountMix = randomAmountMix;
    }

    public Integer getRandomAmountMax() {
        return randomAmountMax;
    }

    public void setRandomAmountMax(Integer randomAmountMax) {
        this.randomAmountMax = randomAmountMax;
    }

    public CouponSource getCouponSource() {
        return couponSource;
    }

    public void setCouponSource(CouponSource couponSource) {
        this.couponSource = couponSource;
    }

    public Boolean getAvailableByCategory() {
        return availableByCategory;
    }

    public void setAvailableByCategory(Boolean availableByCategory) {
        this.availableByCategory = availableByCategory;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public Integer getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(Integer couponNumber) {
        this.couponNumber = couponNumber;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getIsRandomAmount() {
        return isRandomAmount;
    }

    public void setIsRandomAmount(Boolean isRandomAmount) {
        this.isRandomAmount = isRandomAmount;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


}
