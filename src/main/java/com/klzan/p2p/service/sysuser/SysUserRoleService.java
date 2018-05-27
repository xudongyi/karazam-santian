package com.klzan.p2p.service.sysuser;

import com.klzan.p2p.model.sys.SysUserRole;

import java.util.List;

public interface SysUserRoleService {

    List<SysUserRole> findUserRoles(Integer userId);

    List<Integer> findRoleIdList(Integer userId);

    void updateUserRole(Integer userId, List<Integer> newRoleList);
}
