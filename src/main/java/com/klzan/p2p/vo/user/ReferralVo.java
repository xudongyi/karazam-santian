package com.klzan.p2p.vo.user;

import com.klzan.p2p.common.vo.BaseVo;

import java.math.BigDecimal;

public class ReferralVo extends BaseVo {

	/**
	 * 推荐人真实姓名
	 */
	private  String realName;
	/**
	被推荐人真实姓名
	*/
	private  String reRealName;
	/**
     * 推荐人
     */
    private Integer userId;
    /**
     * 推荐人姓名
     */
    private String userNickName;
    /**
     * 推荐人手机号
     */
    private String userMobile;
    /**
     * 被推荐人
     */
    private Integer reUserId;
    /**
     * 被推荐人姓名
     */
    private String reUserNickName;
    /**
     * 被推荐人手机号
     */
    private String reUserMobile;
    /**
     * 推荐费率
     */
    private BigDecimal referralFeeRate;
    /**
     * 是否有效
     */
	private Boolean available;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getReRealName() {
		return reRealName;
	}

	public void setReRealName(String reRealName) {
		this.reRealName = reRealName;
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
	public BigDecimal getReferralFeeRate() {
		return referralFeeRate;
	}
	public void setReferralFeeRate(BigDecimal referralFeeRate) {
		this.referralFeeRate = referralFeeRate;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getReUserMobile() {
		return reUserMobile;
	}

	public void setReUserMobile(String reUserMobile) {
		this.reUserMobile = reUserMobile;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getReUserNickName() {
		return reUserNickName;
	}

	public void setReUserNickName(String reUserNickName) {
		this.reUserNickName = reUserNickName;
	}

}
