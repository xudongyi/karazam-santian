package com.klzan.p2p.event.message;

import com.klzan.core.util.StringUtils;

import java.io.Serializable;

public final class UserLoginMessage implements Serializable {

	private static final long serialVersionUID = -1964393411567264866L;
	/**
	 * 注册用户ID
	 */
	private Integer userId;
	/**
	 * 注册手机号
	 */
	private String mobile;

	private String loginName;

	private Boolean corp;

	public UserLoginMessage(Integer userId, String mobile, String loginName, Boolean corp) {
		this.userId = userId;
		this.mobile = mobile;
		this.loginName = loginName;
		this.corp = corp;
	}

	public UserLoginMessage(Integer userId) {
		this.userId = userId;
	}

	public UserLoginMessage() {
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLoginName() {
		return loginName;
	}

	public Boolean getCorp() {
		return corp;
	}

	public void setCorp(Boolean corp) {
		this.corp = corp;
	}

	public boolean validate() {
		if(userId == null || StringUtils.isBlank(mobile) || StringUtils.isBlank(loginName)) {
			return false;
		}
		return true;
	}

}
