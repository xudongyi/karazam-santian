package com.klzan.p2p.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klzan.core.util.SpringUtils;
import com.klzan.p2p.enums.CouponState;
import com.klzan.p2p.model.base.BaseModel;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.service.user.impl.UserServiceImpl;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券--用户  中间表
 */
@Entity
@Table(name = "karazam_user_coupon")
public class UserCoupon extends BaseModel {

    /**
     * 优惠券名称
     */
    private String title;
    /**
     * 关联优惠券
     */
    @Column(nullable = false)
    private Integer coupon;

    /**
     * 所属用户
     */
    @Column(nullable = false)
    private Integer user;
    /**
     * 优惠券状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private CouponState couponState;
    /**
     * 优惠券金额
     */
    @Column(nullable = false)
    private BigDecimal amount;
    /**
     * 有效期结束时间
     */
    private Date invalidDate;
    /**
     *红包使用（红包投资该字段记录borrowing的ID，如为抵扣充值手续费该字段记录充值记录ID）
     */
    private Integer usedId;
    /**
     * 红包使用时间
     */
    private Date usedDate;
    /**
     * 红包订单号
     */
    private String orderNo;
    /**
     * 优惠券规则
     * @return
     */
    private String rule;

    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUsedId() {
        return usedId;
    }

    public void setUsedId(Integer usedId) {
        this.usedId = usedId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
        return couponState;
    }

    public void setCouponState(CouponState couponState) {
        this.couponState = couponState;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    @Transient
    @JsonProperty("username")
    public String getUsername() {
        UserService userService = SpringUtils.getBean(UserServiceImpl.class);
        if(userService!=null){
            User user = userService.get(getUser());
            if(user!=null){
                return user.getLoginName();
            }
        }
        return "";
    }

}
