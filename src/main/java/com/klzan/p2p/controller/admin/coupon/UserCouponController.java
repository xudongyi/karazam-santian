/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.coupon;

import com.klzan.core.page.PageCriteria;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.CouponType;
import com.klzan.p2p.service.coupon.UserCouponService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户优惠券管理
 * Created by zhutao Date: 2017/06/01
 *
 * @version: 1.0
 */
@Controller("adminUserCouponController")
@RequestMapping("admin/userCoupon")
public class UserCouponController extends BaseAdminController {

    @Resource
    private UserCouponService userCouponService;

    @RequestMapping("list")
    public String list(ModelMap modelMap){
        //优惠券类型
        modelMap.addAttribute("couponTypes", CouponType.values());
        return "admin/coupon/user_coupon/list";
    }

    @RequestMapping("list.do")
    @ResponseBody
    public Object listDo(PageCriteria pageCriteria,HttpServletRequest request){

        return userCouponService.findAllCouponUserPage(pageCriteria,request,null,null);
    }
}
