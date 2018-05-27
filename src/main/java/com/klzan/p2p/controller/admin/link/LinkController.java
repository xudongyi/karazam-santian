/*
 * Copyright 2015-2017 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.controller.admin.link;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.FriendLinkType;
import com.klzan.p2p.model.Links;
import com.klzan.p2p.service.links.LinksService;
import com.klzan.p2p.vo.links.LinksVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Controller - 友情链接
 *
 * @author Karazam Team
 * @version 1.0
 */
@Controller
@RequestMapping("admin/link")
public class LinkController extends BaseAdminController {
    @Resource
    private LinksService linksService;

    /**
     * 列表
     */
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions("content:link:list")
    public String listDispater() {
        return template("link/list");
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("content:link:list")
    @ResponseBody
    public Map<String, Object> list(PageCriteria pageCriteria, HttpServletRequest request) {
        ParamsFilter.buildFromHttpRequest(request, pageCriteria);
        PageResult<LinksVo> pageResult = linksService.findPageByCategory(pageCriteria);
        return (Map<String, Object>) getPageResult(pageResult);
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/addEdit", method = RequestMethod.GET)
    @RequiresPermissions("content:link:add")
    public String addEdit(ModelMap model) {
        model.addAttribute("types", FriendLinkType.values());
        return template("link/add");
    }

    /**
     * 编辑
     */
    @RequestMapping(value = "/updateEdit/{id}", method = RequestMethod.GET)
    @RequiresPermissions("content:link:update")
    public String updateEdit(@PathVariable Integer id, ModelMap model) {
        Links links = linksService.get(id);
        model.addAttribute("links", links);
        model.addAttribute("types", FriendLinkType.values());
        return template("link/edit");
    }


    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("content:link:add")
    @ResponseBody
    public Result save(LinksVo vo) {

        // 验证名称是否存在
        if (isNameExist(vo.getName())) {
            return Result.error("名称已经存在");
        }
        Links links = new Links();
        if (FriendLinkType.IMAGE.getDisplayName().equals(vo.getTypeStr())) {
            links.setLogo(vo.getLogo());
            links.setType(FriendLinkType.IMAGE);
        } else {
            links.setLogo(null);
            links.setType(FriendLinkType.TEXT);
        }
        setObj(vo, links);
        linksService.saveLink(links);
        return Result.success("保存成功");
    }

    /**
     * 更新
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @RequiresPermissions("content:link:update")
    @ResponseBody
    public Result update(@PathVariable Integer id, LinksVo vo) {
        Links links = linksService.get(id);
        // 验证名称是否唯一
        if (isNameExist(links.getName(), vo.getName())) {
            return Result.error("名称已经存在");
        }
        if (links == null) {
            return Result.error("更新失败，链接信息已被删除");
        }
        if (FriendLinkType.IMAGE.getDisplayName().equals(vo.getType().getDisplayName())) {
            links.setLogo(vo.getLogo());
            links.setType(FriendLinkType.IMAGE);
        } else {
            links.setLogo(null);
            links.setType(FriendLinkType.TEXT);
        }
        setObj(vo, links);
        linksService.update(links);
        return Result.success("更新成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{friendLinkType}/batch_delete", method = RequestMethod.POST)
    @RequiresPermissions("content:link:delete")
    @ResponseBody
    public Result delete(@PathVariable FriendLinkType friendLinkType, Long[] ids) {
//        friendLinkService.delete(friendLinkType, ids);
        return Result.success("删除成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresPermissions("content:link:delete")
    @ResponseBody
    public Result deleteLink(@PathVariable Integer id) {
        linksService.remove(id);
        return Result.success("删除成功");
    }

    /**
     * 检查名称
     */
    @RequestMapping(value = "/{friendLinkType}/check_name", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkName(@PathVariable FriendLinkType friendLinkType, String name, String previousName) {
        if (StringUtils.isBlank(name)) {
            return false;
        }
        // 验证名称是否唯一
//        if (friendLinkService.nameUnique(friendLinkType, previousName, name)) {
//            return true;
//        } else {
//            return false;
//        }
        return true;
    }

    private Boolean isNameExist(String name) {
        if (linksService.isNameExist(name)) {
            return true;
        }
        return false;
    }

    private Boolean isNameExist(String oldName, String newName) {
        if (oldName.equals(newName)) {
            return false;
        }
        if (linksService.isNameExist(newName)) {
            return true;
        }
        return false;
    }

    private void setObj(LinksVo vo, Links links) {
        links.setLogo(vo.getLogo());
        links.setType(vo.getType());
        links.setSort(vo.getSort());
        links.setDescription(vo.getDescription());
        links.setName(vo.getName());
        links.setTarget(vo.getTarget());
        links.setUrl(vo.getUrl());
        links.setVisible(vo.getVisible());
    }
}