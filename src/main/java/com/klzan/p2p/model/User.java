package com.klzan.p2p.model;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.GenderType;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.base.BaseUserModel;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * 用户-会员
 */
@Entity
@Table(name = "karazam_user")
public class User extends BaseUserModel {
	public static final String PRINCIPAL_ATTR_NAME = "kuser";
	/** “用户名”Cookie名称 */
	public static final String USERNAME_COOKIE_NAME = "KUserName";
	/**
	 * 用户类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private UserType type;
	/**
	 * 支付密码
	 */
	private String payPassword;
	/**
	 * 手势密码
	 */
	private String gesPassword;
	/**
	 * 邀请码
	 */
	private String inviteCode;
	/**
	 * 环迅账号
	 */
	@Transient
	@Deprecated
	// TODO 即将删除
	private String payAccountNo;
	/**
	 * 企业法人手机号
	 */
	private String legalMobile;

	/** default constructor */
	public User() {
	}

	public User(UserType type, String loginName, String name, String password, Date birthday, GenderType gender,String mobile, String description) {
		this.type = type;
		this.loginName = loginName;
		this.name = name;
		this.password = password;
		this.birthday = birthday;
		this.gender = gender;
		this.mobile = mobile;
		this.description = description;
	}

	/**
	 * 用户注册
	 * @param mobile
	 * @param password
	 */
	public User(String mobile, String password, UserType type) {
		this.mobile = mobile;
		this.loginName = mobile;
		this.name = mobile;
		this.password = password;
		this.type = type;
		this.inviteCode = DigestUtils.md5Hex(DateUtils.getTime() + "" + UUID.randomUUID());
	}

    /**
     * 适用于第三方登录时创建初始用户
     * @param nickname
     */
    public User(String nickname, String loginName, String password, UserType type) {
        this.name = nickname;
        this.loginName = loginName;
        this.password = password;
		this.type = type;
		this.inviteCode = DigestUtils.md5Hex(DateUtils.getTime() + "" + UUID.randomUUID());
    }

    public void update(String loginName, String name, String password, Date birthday, GenderType gender, String mobile, String description) {
		this.loginName = loginName;
		this.name = name;
		this.password = password;
		this.birthday = birthday;
		this.gender = gender;
		this.mobile = mobile;
		this.description = description;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getGesPassword() {
		return gesPassword;
	}

	public void setGesPassword(String gesPassword) {
		this.gesPassword = gesPassword;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getPayAccountNo() {
		return payAccountNo;
	}

	public void setPayAccountNo(String payAccountNo) {
		this.payAccountNo = payAccountNo;
	}

	public String getLegalMobile() {
		return legalMobile;
	}

	public void setLegalMobile(String legalMobile) {
		this.legalMobile = legalMobile;
	}

	/**
	 * 验证支付密码
	 *
	 * @param password 密码
	 * @return 验证是否通过
	 */
	@Transient
	public boolean verifyPayPassword(String password) {
		return StringUtils.equals(DigestUtils.md5Hex(password), getPayPassword());
	}

	/**
	 * 设置新支付密码
	 *
	 * @param password 密码
	 */
	@Transient
	public void setNewPayPassword(String password) {
		setPayPassword(DigestUtils.md5Hex(password));
	}

	/**
	 * 判断支付密码是否完善
	 *
	 * @return 支付密码是否完善
	 */
	@Transient
	public boolean perfectPayPassword() {
		return StringUtils.isNotBlank(getPayPassword());
	}

	/**
	 * 是否存在支付账户
	 */
	@Transient
	public boolean verifyPayAccount()	{
		return StringUtils.isNotBlank(getPayAccountNo());
	}

}