/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.borrowing;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.BorrowingContactsType;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.BorrowingContacts;
import com.klzan.p2p.service.borrowing.BorrowingContactsService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.vo.borrowing.BorrowingContactsVo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 借款联系人
 * @author: chenxinglin
 */
@Controller("huashanMngBorrowingContactsController")
@RequestMapping("admin/borrowing/contacts")
public class BorrowingContactsController extends BaseAdminController {

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private BorrowingContactsService borrowingContactsService;

    /**
     * 列表
     * @param model
     * @return
     */
    @RequiresPermissions("borrowing:contacts")
    @RequestMapping(method = RequestMethod.GET)
    public String list(PageCriteria PageCriteria, ModelMap model) {

        //页面数据
        model.addAttribute("contactsTypes", BorrowingContactsType.values());//标的联系人类型

        return "admin/borrowing_contacts/list";
    }

    /**
     * 列表json
     * @param pageCriteria
     * @return request
     */
    @RequiresPermissions("borrowing:contacts")
    @RequestMapping(value="/list.json",method = RequestMethod.GET)
    @ResponseBody
    public Object listData(PageCriteria pageCriteria, BorrowingContactsType filter_LIKES_type, String filter_LIKES_name, String filter_LIKES_mobile, String filter_LIKES_telephone, Date filter_createDateStart, Date filter_createDateEnd) {
       PageResult<BorrowingContacts> list = borrowingContactsService.findList(pageCriteria, filter_LIKES_type, filter_LIKES_name, filter_LIKES_mobile, filter_LIKES_telephone, filter_createDateStart, filter_createDateEnd);
        return getPageResult(list);
    }

    /**
     * 查看
     * @param model
     * @return
     */
    @RequiresPermissions("contacts:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Integer id, Model model) {

        //操作
        model.addAttribute("action", "view");

        //借款联系人
        model.addAttribute("contacts", borrowingContactsService.get(id));
        List<Borrowing> list = borrowingService.findAll();
        model.addAttribute("borrowings",list);
        //页面数据
        model.addAttribute("contactsTypes", BorrowingContactsType.values());//标的联系人类型
        return "admin/borrowing_contacts/view";
    }

    /**
     * 新增
     * @param model
     * @return
     */
    @RequiresPermissions("contacts:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createView(Model model) {

        //操作
        model.addAttribute("action", "create");
        List<Borrowing> list = borrowingService.findAll();
        model.addAttribute("borrowings",list);
        //页面数据
        model.addAttribute("contactsTypes", BorrowingContactsType.values());//标的联系人类型

        return "admin/borrowing_contacts/create";
    }

    /**
     * 新增
     * @param vo
     * @param request
     * @return
     */
    @RequiresPermissions("contacts:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createData(BorrowingContactsVo vo, HttpServletRequest request) {
        try {
            borrowingContactsService.create(vo);
            return "admin/borrowing_contacts/list";
        } catch (Exception e) {
            logger.error("新增失败", e);
            return "admin/borrowing_contacts/list";
        }
    }

    /**
     * 修改
     * @param model
     * @return
     */
    @RequiresPermissions("contacts:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateView(@PathVariable("id") Integer id, Model model) {

        //操作
        model.addAttribute("action", "update");

        //借款联系人
        model.addAttribute("contacts", borrowingContactsService.get(id));
        List<Borrowing> list = borrowingService.findAll();
        model.addAttribute("borrowings",list);
        //页面数据
        model.addAttribute("contactsTypes", BorrowingContactsType.values());//标的联系人类型
        return "admin/borrowing_contacts/update";
    }

    /**
     * 修改
     * @param vo
     * @param request
     * @return
     */
    @RequiresPermissions("contacts:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result updateData(BorrowingContactsVo vo, HttpServletRequest request) {
        System.out.println(WebUtils.getRemoteIp(request));
        try {
            borrowingContactsService.update(vo);
            return Result.success();
        } catch (Exception e) {
            logger.error("修改借款失败", e);
            return Result.error("修改借款失败");
        }
    }

    @RequiresPermissions("contacts:delete")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id) {
        try {
            borrowingContactsService.remove(id);
            return "admin/borrowing_contacts/list";
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return "admin/borrowing_contacts/list";
        }
    }

    /**
     * 借款人集合(JSON)
     */
    @RequestMapping(value = "/borrowing/json", method = RequestMethod.GET)
    @ResponseBody
    public List<Borrowing> borrowerAsJson() {
        List<Borrowing> list = borrowingService.findAll();
        return list;
    }


}
