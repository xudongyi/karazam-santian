/*
 * Copyright 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.vo.investment;

import com.klzan.p2p.enums.DeviceType;
import com.klzan.p2p.enums.OperationMethod;
import com.klzan.p2p.enums.PaymentOrderMethod;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Bean - 项目投资
 * 
 * @author Karazam Team
 * @version 1.0
 */
public class InvestVo implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 4512607631841269542L;

    /** 操作方式 */
    private OperationMethod operationMethod;

    /** 支付方式 */
    private PaymentOrderMethod paymentMethod;

    /** 借款ID */
    @NotNull
    private Integer projectId;

    /** 金额 */
    @Min(0)
    @Digits(integer = 19, fraction = 2)
    private BigDecimal amount;

    /** 支付密码 */
    /*@Pattern(regexp = "^[^\\s&\"<>]+$")*/
    private String payPassword;

    /** 图形验证码/短信验证码 */
    @Length(max = 6)
    private String captcha;

    /** 银行代码*/
    private String bankCode;

    /** 投资人 */
    private Integer investor;
    
    /** 订单号 */
    private String sn;

    /** 银行卡ID */
    private Integer bankCardId;

    /** 投标来源 */
    private DeviceType deviceType;

    /**
     * 优惠券
     */
    private Integer coupon;
    /**
     * 优惠金额
     */
    private BigDecimal preferentialAmount;

    public BigDecimal getPreferentialAmount() {
        return preferentialAmount;
    }

    public void setPreferentialAmount(BigDecimal preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

    public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OperationMethod getOperationMethod() {
        return operationMethod;
    }

    public void setOperationMethod(OperationMethod operationMethod) {
        this.operationMethod = operationMethod;
    }

    public PaymentOrderMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentOrderMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getInvestor() {
        return investor;
    }

    public void setInvestor(Integer investor) {
        this.investor = investor;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public Integer getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Integer bankCardId) {
        this.bankCardId = bankCardId;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}