package com.klzan.p2p.dao.sysuser;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.sys.SysRoleResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysRoleResourceDao extends DaoSupport<SysRoleResource> {

    public void deleteRoleResource(Integer roleId) {
        String hql = "FROM SysRoleResource WHERE roleId=?0 ";
        List<SysRoleResource> sysRoleResources = this.find(hql, roleId);
        this.batchDelete(sysRoleResources);
    }

    public SysRoleResource getByRoleAndResource(Integer roleId, Integer resourceId) {
        String hql = "FROM SysRoleResource WHERE roleId=?0 AND resourceId=?1 ";
        return findUnique(hql, roleId, resourceId);
    }
}
