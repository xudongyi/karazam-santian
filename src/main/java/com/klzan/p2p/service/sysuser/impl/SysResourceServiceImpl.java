package com.klzan.p2p.service.sysuser.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.sysuser.SysResourceDao;
import com.klzan.p2p.enums.ResourceType;
import com.klzan.p2p.model.sys.SysResource;
import com.klzan.p2p.service.sysuser.SysResourceService;
import com.klzan.p2p.vo.sysuser.SysResourceVo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysResourceServiceImpl extends BaseService<SysResource> implements SysResourceService {

    @Autowired
    private SysResourceDao sysResourceDao;

    @Override
    public SysResource create(SysResourceVo resourceVo) {
        SysResource parent = findOne(resourceVo.getParentId());
        SysResource resource = new SysResource(resourceVo.getName(), resourceVo.getType(), resourceVo.getPermission(), parent.makeSelfAsParentIds(), resourceVo.getUrl(), resourceVo.getIcon(), resourceVo.getParentId(), resourceVo.getSort(), resourceVo.getDescription());
        return sysResourceDao.persist(resource);
    }

    @Override
    public SysResource update(SysResourceVo resourceVo) {
        SysResource parent = findOne(resourceVo.getParentId());
        SysResource resource = findOne(resourceVo.getId());
        resource.update(resourceVo.getName(), resourceVo.getType(), resourceVo.getPermission(), parent.makeSelfAsParentIds(), resourceVo.getUrl(), resourceVo.getIcon(), resourceVo.getParentId(), resourceVo.getSort(), resourceVo.getDescription());
        return sysResourceDao.update(resource);
    }

    @Override
    public void delete(Integer resourceId) {
        sysResourceDao.delete(resourceId);
    }

    @Override
    public SysResource findOne(Integer resourceId) {
        return sysResourceDao.findOne(resourceId);
    }

    @Override
    public List<SysResource> findAll() {
        return sysResourceDao.findAll();
    }

    @Override
    public Set<String> findPermissions(Set<Integer> resourceIds) {
        Set<String> permissions = new HashSet<>();
        for(Integer resourceId : resourceIds) {
            SysResource resource = findOne(resourceId);
            if(resource != null && !StringUtils.isEmpty(resource.getPermission())) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }

    @Override
    public List<SysResource> findMenus(Set<String> permissions) {
        List<SysResource> allResources = findAll();
        List<SysResource> menus = new ArrayList<>();
        for(SysResource resource : allResources) {
            if(resource.isRootNode()) {
                continue;
            }
            if(resource.getType() != ResourceType.MENU) {
                continue;
            }
            if(!hasPermission(permissions, resource)) {
                continue;
            }
            menus.add(resource);
        }
        return menus;
    }

    @Override
    public List<SysResourceVo> findMenus() {
        return sysResourceDao.findMenus();
    }

    @Override
    public List<SysResourceVo> findNotRootList() {
        return sysResourceDao.findNotRootList();
    }

    @Override
    public List<SysResourceVo> getMenuOperation(Integer parentId) {
        return sysResourceDao.getMenuOperation(parentId);
    }

    @Override
    public List<SysResource> findMenusByParent(Integer parentId) {
        return sysResourceDao.findChildren(parentId);
    }

    private boolean hasPermission(Set<String> permissions, SysResource resource) {
        if(StringUtils.isEmpty(resource.getPermission())) {
            return true;
        }
        for(String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(resource.getPermission());
            if(p1.implies(p2) || p2.implies(p1)) {
                return true;
            }
        }
        return false;
    }
}
