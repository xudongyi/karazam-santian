package com.klzan.p2p.vo.coupon;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.CouponSource;
import com.klzan.p2p.enums.CouponState;
import com.klzan.p2p.enums.CouponType;
import com.klzan.p2p.model.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券--用户  中间表
 */
public class UserCouponVo extends BaseModel {

    private String title;
    /**
     * 关联优惠券
     */
    private Integer coupon;

    /**
     * 所属用户
     */
    private Integer user;
    /**
     * 优惠券状态
     */
    private CouponState couponState;
    private String couponStateStr;
    /**
     * 用户优惠券有效期
     */
    private Date userInvalidDate;
    /**
     * 该类优惠券有效期
     */
    private Date couponInvalidDate;
    /**
     * 优惠券使用时间
     */
    private Date usedDate;
    /**
     * 用户优惠券规则
     * @return
     */
    private String userRule;
    /**
     * 该类优惠券使用规则
     */
    private String couponRule;

    private CouponRule rule;
    /************************用户信息***************************/
    private String mobile;
    private String realName;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    /************************优惠券种类信息**********************/
    private BigDecimal amount;
    private CouponType couponType;
    private String couponTypeStr;
    private CouponSource couponSource;
    private String couponSourceStr;

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

    public void setCouponTypeStr(String couponTypeStr) {
        this.couponTypeStr = couponTypeStr;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public CouponState getCouponState() {
        if (StringUtils.isNotBlank(couponStateStr)){
            return CouponState.valueOf(couponStateStr);
        }
        return couponState;
    }

    public void setCouponState(CouponState couponState) {
        this.couponState = couponState;
    }

    public String getCouponStateStr() {
        if (StringUtils.isNotBlank(couponStateStr)){
            return CouponState.valueOf(couponStateStr).getDisplayName();
        }
        return couponStateStr;
    }

    public void setCouponStateStr(String couponStateStr) {
        this.couponStateStr = couponStateStr;
    }

    public Date getUserInvalidDate() {
        return userInvalidDate;
    }

    public void setUserInvalidDate(Date userInvalidDate) {
        this.userInvalidDate = userInvalidDate;
    }

    public Date getCouponInvalidDate() {
        return couponInvalidDate;
    }

    public void setCouponInvalidDate(Date couponInvalidDate) {
        this.couponInvalidDate = couponInvalidDate;
    }

    public String getUserRule() {
        return userRule;
    }

    public void setUserRule(String userRule) {
        this.userRule = userRule;
    }

    public String getCouponRule() {
        return couponRule;
    }

    public void setCouponRule(String couponRule) {
        this.couponRule = couponRule;
    }

    public CouponRule getRule() {
        return rule;
    }

    public void setRule(CouponRule rule) {
        this.rule = rule;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }
}
