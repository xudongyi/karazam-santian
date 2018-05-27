package com.klzan.p2p.service.sysuser;

import com.klzan.p2p.model.sys.SysRoleResource;

import java.util.List;
import java.util.Set;

public interface SysRoleResourceService {

    List<SysRoleResource> findRoleResources(Integer roleId);

    Set<Integer> findResourceIdsByRole(Integer roleId);
}
