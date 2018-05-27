package com.klzan.p2p.service.sysuser.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.sysuser.SysResourceDao;
import com.klzan.p2p.dao.sysuser.SysRoleDao;
import com.klzan.p2p.dao.sysuser.SysRoleResourceDao;
import com.klzan.p2p.dao.sysuser.SysUserRoleDao;
import com.klzan.p2p.model.sys.SysRole;
import com.klzan.p2p.model.sys.SysRoleResource;
import com.klzan.p2p.service.sysuser.SysResourceService;
import com.klzan.p2p.service.sysuser.SysRoleService;
import com.klzan.p2p.vo.sysuser.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleServiceImpl extends BaseService<SysRole> implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysResourceDao sysResourceDao;
    @Autowired
    private SysRoleResourceDao sysRoleResourceDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysResourceService sysResourceService;

    @Override
    public SysRole createRole(SysRoleVo roleVo) {
        SysRole role = new SysRole(roleVo.getName(), roleVo.getRoleCode(), roleVo.getDescription(), roleVo.getSort());
        return sysRoleDao.persist(role);
    }

    @Override
    public SysRole updateRole(SysRoleVo roleVo) {
        return sysRoleDao.update(roleVo);
    }

    @Override
    public void deleteRole(Integer roleId) {
        sysRoleDao.delete(roleId);
        sysRoleResourceDao.deleteRoleResource(roleId);
        sysUserRoleDao.deleteUserRole(roleId);
    }

    @Override
    public Set<String> findPermissions(Integer... roleIds) {
        Set<Integer> resourceIds = new HashSet<>();
        for (Integer roleId : roleIds) {
            SysRole role = super.get(roleId);
            if (role != null) {
                List<Integer> resourceIdList = sysResourceDao.findResourceIds(roleId);
                resourceIds.addAll(resourceIdList);
            }
        }
        return sysResourceService.findPermissions(resourceIds);
    }

    @Override
    public PageResult<SysRoleVo> findPageRoles(PageCriteria pageCriteria) {
        return sysRoleDao.findPageRoles(pageCriteria);
    }

    @Override
    public List<Integer> getPermissionIds(Integer roleId) {
        return sysRoleDao.findPermissionIds(roleId);
    }

    @Override
    public void updateRolePermission(Integer roleId, List<Integer> newResourcesIdList) {
        List<Integer> resourceIdList = getPermissionIds(roleId);
        // 删除
        for (Integer _resourceId : resourceIdList) {
            if (!newResourcesIdList.contains(_resourceId)) {
                SysRoleResource roleResource = sysRoleResourceDao.getByRoleAndResource(roleId, _resourceId);
                sysRoleResourceDao.delete(roleResource);
            }
        }

        // 添加
        for (Integer _resourceId : newResourcesIdList) {
            if (!resourceIdList.contains(_resourceId)) {
                if (null == _resourceId) {
                    continue;
                }
                SysRoleResource roleResource = new SysRoleResource(roleId, _resourceId);
                sysRoleResourceDao.persist(roleResource);
            }
        }
    }

    @Override
    public Set<String> findRoles(Integer... roleIds) {
        Set<String> roles = new HashSet<>();
        for(Integer roleId : roleIds) {
            SysRole role = sysRoleDao.get(roleId);
            if(role != null) {
                roles.add(role.getRoleCode());
            }
        }
        return roles;
    }
}