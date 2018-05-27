package com.klzan.p2p.model.sys;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "karazam_sys_role_resource")
public class SysRoleResource extends BaseModel {
	private Integer resourceId;

	private Integer roleId;

	public SysRoleResource() {
	}

	public SysRoleResource(Integer roleId, Integer resourceId) {
		this.roleId = roleId;
		this.resourceId = resourceId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public Integer getRoleId() {
		return roleId;
	}

}