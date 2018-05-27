package com.klzan.p2p.vo.sysuser;

import com.klzan.p2p.common.vo.BaseVo;

public class SysRoleVo extends BaseVo {
    /** serialVersionUID */
    private static final long serialVersionUID = -3703154275817120085L;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色标识
     */
    private String roleCode;
    /**
     * 角色描述,UI界面显示使用
     */
    private String description;
    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private Boolean available;

    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
