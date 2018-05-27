package com.klzan.p2p.vo.user;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.ReferralFeeState;

import java.math.BigDecimal;
import java.util.Date;

public class ReferralFeeVo extends BaseVo {
	 /**
     * 推荐关系ID
     */
    private Integer referralId;
	/**
	 * 推荐人id
	 */
	private Integer userId;
	private String userNickName;
	private String userMobile;
	/**
	 * 被推荐人ID
	 */
	private Integer reUserId;
	private String reUserNickName;
	private String reUserMobile;
    /**
     * 状态
     */
    private ReferralFeeState state;
	private String stateStr;
    /**
     * 推荐金额
     */
    private BigDecimal referralAmt;
    /**
     * 推荐费率
     */
    private BigDecimal referralFeeRate;
    /**
     * 推荐费
     */
    private BigDecimal referralFee;

    /**
     * 计划结算日期
     */
    private Date planPaymentDate;
    /**
     * 实际结算日期
     */
    private Date paymentDate;
    /**
     * 备注
     */
    private String memo;
    /**
     * 操作员
     */
    private String operator;
    /**
     * IP
     */
    private String ip;
    /**
     * 借款
     */
    private Integer borrowing;
    /**
     * 投资
     */
    private Integer investment;
	public Integer getReferralId() {
		return referralId;
	}
	public void setReferralId(Integer referralId) {
		this.referralId = referralId;
	}
	public ReferralFeeState getState() {
		if(state==null&& StringUtils.isNotBlank(stateStr)){
			return ReferralFeeState.valueOf(stateStr);
		}
		return state;
	}

	public String getStateStr() {
		if(StringUtils.isNotBlank(stateStr)){
			return ReferralFeeState.valueOf(stateStr).getDisplayName();
		}
		return stateStr;
	}

	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	public void setState(ReferralFeeState state) {
		this.state = state;
	}
	public BigDecimal getReferralAmt() {
		return referralAmt;
	}
	public void setReferralAmt(BigDecimal referralAmt) {
		this.referralAmt = referralAmt;
	}
	public BigDecimal getReferralFeeRate() {
		return referralFeeRate;
	}
	public void setReferralFeeRate(BigDecimal referralFeeRate) {
		this.referralFeeRate = referralFeeRate;
	}
	public BigDecimal getReferralFee() {
		return referralFee;
	}
	public void setReferralFee(BigDecimal referralFee) {
		this.referralFee = referralFee;
	}
	public Date getPlanPaymentDate() {
		return planPaymentDate;
	}
	public void setPlanPaymentDate(Date planPaymentDate) {
		this.planPaymentDate = planPaymentDate;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getBorrowing() {
		return borrowing;
	}
	public void setBorrowing(Integer borrowing) {
		this.borrowing = borrowing;
	}
	public Integer getInvestment() {
		return investment;
	}
	public void setInvestment(Integer investment) {
		this.investment = investment;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getReUserId() {
		return reUserId;
	}

	public void setReUserId(Integer reUserId) {
		this.reUserId = reUserId;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getReUserNickName() {
		return reUserNickName;
	}

	public void setReUserNickName(String reUserNickName) {
		this.reUserNickName = reUserNickName;
	}

	public String getReUserMobile() {
		return reUserMobile;
	}

	public void setReUserMobile(String reUserMobile) {
		this.reUserMobile = reUserMobile;
	}
}
