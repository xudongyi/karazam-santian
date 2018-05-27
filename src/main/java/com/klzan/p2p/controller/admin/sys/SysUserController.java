/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.sys;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.service.sysuser.SysUserRoleService;
import com.klzan.p2p.service.sysuser.SysUserService;
import com.klzan.p2p.vo.sysuser.SysUserVo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("admin/sysuser")
public class SysUserController extends BaseAdminController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @RequiresPermissions("sysuser:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request) {
        model.addAttribute("userList", sysUserService.findAll());
        return TEMPLATE_ADMIN_PATH + "sysuser/index";
    }

    /**
     * 获取用户json
     */
    @RequiresPermissions("sysuser:view")
    @RequestMapping(value="json",method = RequestMethod.GET)
    @ResponseBody
    public Object getData(PageCriteria pageCriteria, HttpServletRequest request) {
        ParamsFilter.buildFromHttpRequest(request, pageCriteria);
        PageResult<SysUserVo> users = sysUserService.findUsers(pageCriteria);
        return sysUserService.findUsers(pageCriteria);
    }

    @RequiresPermissions("sysuser:create")
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(Integer id, Model model) {
        model.addAttribute("action", "create");
        return TEMPLATE_ADMIN_PATH + "sysuser/form";
    }

    @RequiresPermissions("sysuser:create")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public Result create(SysUserVo userVo) {
        try {
            sysUserService.createUser(userVo);
            return Result.success();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return Result.error(e.getMessage());
        }
    }

    @RequiresPermissions("sysuser:update")
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("sysUser", sysUserService.get(id));
        model.addAttribute("action", "update");
        return TEMPLATE_ADMIN_PATH + "sysuser/form";
    }

    @RequiresPermissions("sysuser:update")
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public Result update(SysUserVo userVo) {
        try {
            sysUserService.updateUser(userVo);
            return Result.success();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return Result.error(e.getMessage());
        }
    }

    @RequiresPermissions("sysuser:delete")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result delete(@PathVariable("id") Integer id) {
        try {
            sysUserService.remove(id);
            return Result.success();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return Result.error(e.getMessage());
        }
    }

    @RequiresPermissions("sysuser:delete")
    @RequestMapping(value = "deletes", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deletes(Integer[] ids) {
        try {
            for (Integer id : ids) {
                sysUserService.remove(id);
            }
            return Result.success();
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return Result.error(e.getMessage());
        }
    }

    @RequiresPermissions("sysuser:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", sysUserService.get(id));
        model.addAttribute("op", "修改密码");
        return TEMPLATE_ADMIN_PATH + "sysuser/changePassword";
    }

    @RequiresPermissions("sysuser:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public String changePassword(@PathVariable("id") Integer id, String newPassword, RedirectAttributes redirectAttributes) {
        sysUserService.changePassword(id, newPassword);
        redirectAttributes.addFlashAttribute("msg", "修改密码成功");
        return "redirect:/sysuser";
    }

    /**
     * 弹窗页-用户拥有的角色
     *
     * @param userId
     * @param model
     * @return
     */
    @RequiresPermissions("sysuser:user:role")
    @RequestMapping(value = "{userId}/userRole")
    public String getUserRole(@PathVariable("userId") Integer userId, Model model) {
        model.addAttribute("userId", userId);
        return template("sysuser/role");
    }

    @RequiresPermissions("sysuser:user:role")
    @RequestMapping(value = "{userId}/role")
    @ResponseBody
    public List<Integer> getRoleIdList(@PathVariable("userId") Integer userId) {
        return sysUserRoleService.findRoleIdList(userId);
    }

    /**
     * 修改用户拥有的角色
     *
     * @param userId
     * @param newRoleList
     * @return
     */
    @RequiresPermissions("sysuser:user:role")
    @RequestMapping(value = "{userId}/updateRole")
    @ResponseBody
    public Result updateUserRole(@PathVariable("userId") Integer userId,@RequestBody List<Integer> newRoleList) {
        try {
            sysUserRoleService.updateUserRole(userId, newRoleList);
            return Result.success();
        } catch (Exception e) {
            return Result.error();
        }
    }

    /**
     * Ajax请求校验loginName是否唯一。
     */
    @RequestMapping(value = "checkLoginName")
    @ResponseBody
    public Boolean checkLoginName(String loginName) {
        if (sysUserService.findByLoginName(loginName) == null) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

}
