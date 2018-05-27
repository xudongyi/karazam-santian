package com.klzan.p2p.model.sys;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "karazam_sys_user_role")
public class SysUserRole extends BaseModel {
	private Integer userId;

	private Integer roleId;

	public SysUserRole() {
	}

	public SysUserRole(Integer userId, Integer roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getRoleId() {
		return roleId;
	}
}