package com.klzan.p2p.dao.sysuser;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.sys.SysRoleResource;
import com.klzan.p2p.model.sys.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysUserRoleDao extends DaoSupport<SysUserRole> {

    @Autowired
    private SysRoleResourceDao sysRoleResourceDao;

    public List<Integer> getPermissionIds(Integer roleId) {
        final String sql = "select resource_id from karazam_sys_role_resource where role_id=?0 ";
        return super.findBySQL(sql, roleId);
    }

    public void updateRolePermission(Integer roleId, List<Integer> newList) {
        String sql = "DELETE FROM karazam_sys_role_resource WHERE role_id=?0";
        super.executeUpdateWithSQL(sql, roleId);
        for (Integer resourceId : newList) {
            SysRoleResource roleResource = new SysRoleResource(roleId, resourceId);
            sysRoleResourceDao.persist(roleResource);
        }
    }

    public SysUserRole findByUserIdAndRoleId(Integer userId, Integer roleId) {
        String hql = "FROM SysUserRole WHERE userId=?0 AND roleId=?1";
        return this.findUnique(hql, userId, roleId);
    }

    public void deleteUserRole(Integer roleId) {
        String hql = "FROM SysUserRole WHERE roleId=?0 ";
        List<SysUserRole> sysUserRoles = this.find(hql, roleId);
        this.batchDelete(sysUserRoles);
    }
}
