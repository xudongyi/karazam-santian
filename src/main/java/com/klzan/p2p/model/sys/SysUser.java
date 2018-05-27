package com.klzan.p2p.model.sys;

import com.klzan.p2p.enums.GenderType;
import com.klzan.p2p.enums.UserStatus;
import com.klzan.p2p.model.base.BaseUserModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户-后台管理用户
 * @author suhao
 */
@Entity
@Table(name = "karazam_sys_user")
public class SysUser extends BaseUserModel {
	/**
	 * 用户编号
	 */
	private String userNo;

	/** default constructor */
	public SysUser() {
	}

	public SysUser(String loginName, String name, String password, Date birthday, GenderType gender, String email, String mobile, UserStatus status, String description) {
		this.loginName = loginName;
		this.name = name;
		this.password = password;
		this.birthday = birthday;
		this.gender = gender;
		this.email = email;
		this.mobile = mobile;
		this.status = status;
		this.description = description;
	}

	public String getUserNo() {
		return userNo;
	}

	public void update(String loginName, String name, String password, Date birthday, GenderType gender, String email, String mobile, UserStatus status, String description) {
		this.loginName = loginName;
		this.name = name;
		this.password = password;
		this.birthday = birthday;
		this.gender = gender;
		this.email = email;
		this.mobile = mobile;
		this.description = description;
	}

	public void updateUserNo(String userNo) {
		this.userNo = userNo;
	}

}