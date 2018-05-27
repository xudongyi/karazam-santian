/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.vo.coupon;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.CouponSource;
import com.klzan.p2p.enums.CouponType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhutao Date: 2017/06/01
 *
 * @version:
 */
public class CouponVo extends BaseVo {

    private CouponRule couponRule;

    /**
     * 优惠券类型
     */
    private CouponType couponType;
    private String couponTypeStr;
    /**
     * 优惠券 来源
     */
    private CouponSource couponSource;
    private String couponSourceStr;
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
    private String couponNumber;
    /**
     * 是否以优惠券种类的失效日期为准（true:以种类的失效日期为准；false:以具体的用户红包失效日期为准）
     */
    private Boolean availableByCategory;

    /**
     * 该类型红包失效日期
     */
    private Date invalidDate;
    /**
     * 使用规则
     */
    private String rule;

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

    public String getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(String couponNumber) {
        this.couponNumber = couponNumber;
    }

    public CouponRule getCouponRule() {
        return couponRule;
    }

    public void setCouponRule(CouponRule couponRule) {
        this.couponRule = couponRule;
    }

    public CouponType getCouponType() {
        if (StringUtils.isNotBlank(couponTypeStr)){
            return CouponType.valueOf(couponTypeStr);
        }
        return couponType;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public String getCouponTypeStr() {
        if (StringUtils.isNotBlank(couponTypeStr)){
            return CouponType.valueOf(couponTypeStr).getDisplayName();
        }
        return couponTypeStr;
    }
    public CouponSource getCouponSource() {
        if(StringUtils.isNotBlank(couponSourceStr)){
            return CouponSource.valueOf(couponSourceStr);
        }
        return couponSource;
    }

    public void setCouponSource(CouponSource couponSource) {
        this.couponSource = couponSource;
    }

    public String getCouponSourceStr() {
        if(StringUtils.isNotBlank(couponSourceStr)){
            return CouponSource.valueOf(couponSourceStr).getDisplayName();
        }
        return couponSourceStr;
    }

    public void setCouponSourceStr(String couponSourceStr) {
        this.couponSourceStr = couponSourceStr;
    }
    public void setCouponTypeStr(String couponTypeStr) {
        this.couponTypeStr = couponTypeStr;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
    public Boolean getIsRandomAmount() {
        return isRandomAmount;
    }

    public void setIsRandomAmount(Boolean isRandomAmount) {
        this.isRandomAmount = isRandomAmount;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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
}
