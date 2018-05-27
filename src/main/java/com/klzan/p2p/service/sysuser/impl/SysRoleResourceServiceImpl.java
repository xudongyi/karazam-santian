package com.klzan.p2p.service.sysuser.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.model.sys.SysRoleResource;
import com.klzan.p2p.service.sysuser.SysRoleResourceService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by suhao Date: 2017/3/17 Time: 17:44
 *
 * @version: 1.0
 */
@Service
public class SysRoleResourceServiceImpl extends BaseService<SysRoleResource> implements SysRoleResourceService {
    @Override
    public List<SysRoleResource> findRoleResources(Integer roleId) {
        StringBuilder hql = new StringBuilder("FROM SysRoleResource WHERE roleId=?0 ");
        return this.find(hql.toString(), roleId);
    }

    @Override
    public Set<Integer> findResourceIdsByRole(Integer roleId) {
        List<SysRoleResource> roleResources = findRoleResources(roleId);
        Set<Integer> ids = new HashSet<>();
        for (SysRoleResource roleResource : roleResources) {
            ids.add(roleResource.getResourceId());
        }
        return ids;
    }
}
