package com.klzan.p2p.vo.sysuser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Authorization implements Serializable {
    private Integer userId;
    private List<Integer> roleIds;

    public Authorization() {
    }

    public Authorization(Integer userId, List<Integer> roleIds) {
        this.userId = userId;
        this.roleIds = roleIds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getRoleIds() {
        if (roleIds == null) {
            roleIds = new ArrayList<>();
        }
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

}
