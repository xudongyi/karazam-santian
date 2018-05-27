package com.klzan.p2p.model.sys;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "karazam_sys_role")
public class SysRole extends BaseModel {
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色标识 程序中判断使用,如"admin"
     */
    private String roleCode;
    /**
     * 角色描述
     */
    private String description;
    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private Boolean available = Boolean.TRUE;
    private Integer sort = 0;

    public SysRole() {
    }

    public SysRole(String name, String roleCode, String description, Integer sort) {
        this.name = name;
        this.roleCode = roleCode;
        this.description = description;
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public Integer getSort() {
        return sort;
    }

    public void update(String name, String roleCode, String description, Integer sort) {
        this.name = name;
        this.roleCode = roleCode;
        this.description = description;
        this.sort = sort;
    }
}
