package com.klzan.p2p.enums;

/**
 * 验证码类型
 */
public enum CaptchaType implements IEnum {

	ADMIN_LOGIN("后台登录"),

	LOGIN("登录"),

	REGIST("注册"),

	MODIFY_PASSWORD("修改密码"),

	FIND_PASSWORD("找回密码"),

	RESET_PASSWORD("重置密码"),

	INVESTMENT("投资"),

	BORROWING_APPLY("借款申请");

	private String displayName;

	CaptchaType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}