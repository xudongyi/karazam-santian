/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.sys;

import com.klzan.core.Result;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.ResourceType;
import com.klzan.p2p.model.sys.SysResource;
import com.klzan.p2p.model.sys.SysUserRole;
import com.klzan.p2p.security.sysuser.ShiroSysUser;
import com.klzan.p2p.service.sysuser.SysResourceService;
import com.klzan.p2p.service.sysuser.SysRoleResourceService;
import com.klzan.p2p.service.sysuser.SysUserRoleService;
import com.klzan.p2p.vo.sysuser.MenuTree;
import com.klzan.p2p.vo.sysuser.SysResourceVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;

@Controller
@RequestMapping("admin/resource")
public class SysResourceController extends BaseAdminController {

    @Inject
    private SysResourceService sysResourceService;
    @Inject
    private SysUserRoleService sysUserRoleService;
    @Inject
    private SysRoleResourceService sysRoleResourceService;

    @ModelAttribute("types")
    public ResourceType[] resourceTypes() {
        return ResourceType.values();
    }

    /**
     * 默认页面
     */
    @RequiresPermissions("resource:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return TEMPLATE_ADMIN_PATH + "resource/index";
    }

    /**
     * 菜单集合(JSON)
     */
    @RequiresPermissions("resource:view")
    @RequestMapping(value = "menu/json", method = RequestMethod.GET)
    @ResponseBody
    public List<SysResourceVo> menuDate() {
        List<SysResourceVo> menuList = sysResourceService.findMenus();
        return menuList;
    }

    /**
     * 权限集合(JSON)
     */
    @RequiresPermissions("resource:view")
    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public List<SysResourceVo> getData() {
        List<SysResourceVo> permissionList = sysResourceService.findNotRootList();
        return permissionList;
    }

    /**
     * 获取菜单下的操作
     */
    @RequiresPermissions("resource:view")
    @RequestMapping("ope/json")
    @ResponseBody
    public Map<String, Object> menuOperationDate(Integer pid) {
        try {
            List<SysResourceVo> menuOperList = sysResourceService.getMenuOperation(pid);
            Map<String, Object> map = new HashMap<>();
            map.put("rows", menuOperList);
            map.put("total", menuOperList.size());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加权限跳转
     */
    @RequiresPermissions("resource:create")
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("resource", new SysResource());
        model.addAttribute("action", "create");
        return template("resource/form");
    }

    /**
     * 添加菜单跳转
     */
    @RequiresPermissions("resource:create")
    @RequestMapping(value = "menu/create", method = RequestMethod.GET)
    public String menuCreateForm(Model model) {
        model.addAttribute("resource", new SysResource());
        model.addAttribute("action", "create");
        return TEMPLATE_ADMIN_PATH + "resource/menu/form";
    }

    /**
     * 添加权限/菜单
     */
    @RequiresPermissions("resource:create")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(SysResourceVo resourceVo, Model model) {
        try {
            sysResourceService.create(resourceVo);
            return Result.success();
        } catch (Exception e) {
            return Result.error();
        }
    }

    /**
     * 修改权限跳转
     */
    @RequiresPermissions("resource:update")
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("resource", sysResourceService.findOne(id));
        model.addAttribute("action", "update");
        return TEMPLATE_ADMIN_PATH + "resource/form";
    }

    /**
     * 修改菜单跳转
     */
    @RequiresPermissions("resource:update")
    @RequestMapping(value = "menu/update/{id}", method = RequestMethod.GET)
    public String updateMenuForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("resource", sysResourceService.findOne(id));
        model.addAttribute("action", "update");
        return template("resource/menu/form");
    }

    /**
     * 修改权限/菜单
     */
    @RequiresPermissions("resource:update")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(SysResourceVo resourceVo, Model model) {
        try {
            sysResourceService.update(resourceVo);
            return Result.success();
        } catch (Exception e) {
            return Result.error();
        }
    }

    /**
     * 删除权限
     */
    @RequiresPermissions("resource:delete")
    @RequestMapping(value = "delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable("id") Integer id) {
        try {
            sysResourceService.delete(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error();
        }
    }

    /**
     * 当前登录用户的权限集合
     */
    @RequestMapping("i/menu.json")
    @ResponseBody
    public Object userMenus() {
        ShiroSysUser user = UserUtils.getCurrentShiroSysUser();
        List<SysUserRole> userRoles = sysUserRoleService.findUserRoles(user.getId());
        Set<Integer> userResourceIds = new HashSet<>();
        for (SysUserRole userRole : userRoles) {
            Set<Integer> resourceIds = sysRoleResourceService.findResourceIdsByRole(userRole.getRoleId());
            userResourceIds.addAll(resourceIds);
        }

        MenuTree menuTree = recursiveUserTree(1, userResourceIds);
        List<MenuTree> children = menuTree.getChildren();
        if (!children.isEmpty() && children.size() >= 1) {
            children.get(0).setState("open");
            setFirstOpen(children.get(0).getChildren());
        }
        return children;
    }

    public MenuTree recursiveUserTree(int cid, Set<Integer> userResourceIds) {
        SysResource resource = sysResourceService.findOne(cid);
        MenuTree node = new MenuTree(resource.getId(), resource.getParentId(), resource.getSort(), resource.getName(), resource.getUrl(), resource.getIcon(), new ArrayList<>());
        List<SysResource> childs = sysResourceService.findMenusByParent(cid);
        for(SysResource child : childs){
            if (child.getType() == ResourceType.BUTTON) {
                continue;
            }
            if (!userResourceIds.contains(child.getId())) {
                continue;
            }
            MenuTree n = recursiveUserTree(child.getId(), userResourceIds); //递归
            node.getChildren().add(n);
        }
        if (!node.getChildren().isEmpty()) {
            node.setState("closed");
        } else {
            node.setState("open");
        }

        return node;
    }

    public MenuTree recursiveTree(int cid) {
        SysResource sysResource = sysResourceService.findOne(cid);
        MenuTree node = new MenuTree(sysResource.getId(), sysResource.getParentId(), sysResource.getSort(), sysResource.getName(), sysResource.getUrl(), sysResource.getIcon(), new ArrayList<>());
        List<SysResource> childs = sysResourceService.findMenusByParent(cid);
        for(SysResource child : childs){
            if (child.getType() == ResourceType.BUTTON) {
                continue;
            }
            MenuTree n = recursiveTree(child.getId()); //递归
            node.getChildren().add(n);
        }

        return node;
    }

    public void setFirstOpen(List<MenuTree> menuTrees) {
        for (MenuTree menuTree : menuTrees) {
            menuTree.setState("open");
            List<MenuTree> children = menuTree.getChildren();
            if (!children.isEmpty()) {
                setFirstOpen(children);
            }
        }
    }

}
