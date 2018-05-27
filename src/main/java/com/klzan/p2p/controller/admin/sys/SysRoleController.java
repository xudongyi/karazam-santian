/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.sys;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.model.sys.SysRole;
import com.klzan.p2p.service.sysuser.SysResourceService;
import com.klzan.p2p.service.sysuser.SysRoleService;
import com.klzan.p2p.vo.sysuser.SysRoleVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/role")
public class SysRoleController extends BaseAdminController {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysResourceService resourceService;

    /**
     * 默认页面
     *
     * @return
     */
    @RequiresPermissions("role:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return template("role/index");
    }

    /**
     * 角色集合(JSON)
     */
    @RequiresPermissions("role:view")
    @RequestMapping(value = "json", method = RequestMethod.GET)
    @ResponseBody
    public PageResult<SysRoleVo> getRoleJson(PageCriteria pageCriteria) {
        return roleService.findPageRoles(pageCriteria);
    }

    /**
     * 获取角色拥有的权限ID集合
     *
     * @param id
     * @return
     */
    @RequiresPermissions("role:view")
    @RequestMapping("{id}/json")
    @ResponseBody
    public List<Integer> getRolePermissions(@PathVariable("id") Integer id) {
        List<Integer> permissionIdList = roleService.getPermissionIds(id);
        return permissionIdList;
    }

    /**
     * 修改角色权限
     *
     * @param id
     * @param newResourcesIdList
     * @return
     */
    @RequiresPermissions("role:update")
    @RequestMapping(value = "{id}/updatePermission")
    @ResponseBody
    public Result updateRolePermission(@PathVariable("id") Integer id, @RequestBody List<Integer> newResourcesIdList) {
        try {
            roleService.updateRolePermission(id, newResourcesIdList);
            return Result.success();
        } catch (Exception e) {
            return Result.error();
        }
    }

    @RequiresPermissions("role:create")
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        model.addAttribute("role", new SysRole());
        model.addAttribute("action", "create");
        return template("role/form");
    }

    @RequiresPermissions("role:create")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(String name, String roleCode, String description, Integer sort) {
        try {
            SysRoleVo roleVo = new SysRoleVo();
            roleVo.setName(name);
            roleVo.setRoleCode(roleCode);
            roleVo.setDescription(description);
            roleVo.setSort(sort);
            roleService.createRole(roleVo);
            return Result.success();
        } catch (Exception e) {
            return Result.error();
        }
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("role", roleService.get(id));
        model.addAttribute("action", "update");
        return template("role/form");
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public Result update(SysRoleVo roleVo) {
        try {
            roleService.updateRole(roleVo);
            return Result.success();
        } catch (Exception e) {
            return Result.error();
        }
    }

    @RequiresPermissions("role:delete")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result delete(@PathVariable("id") Integer id) {
        try {
            roleService.deleteRole(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error();
        }
    }

}
