/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.uc;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.CouponState;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserCoupon;
import com.klzan.p2p.service.coupon.UserCouponService;
import com.klzan.p2p.vo.coupon.CouponRule;
import com.klzan.p2p.vo.coupon.UserCouponVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户优惠券管理
 * Created by zhutao Date: 2017/06/01
 *
 * @version: 1.0
 */
@Controller("ucUserCouponController")
@RequestMapping("uc/userCoupon")
public class UserCouponController extends BaseAdminController {

    @Resource
    private UserCouponService userCouponService;

    @RequestMapping
    public String list(ModelMap modelMap){
        return "uc/coupon/index";
    }

    @RequestMapping("data/{state}")
    @ResponseBody
    public Object listDo(@PathVariable String state, @CurrentUser User user, PageCriteria pageCriteria, HttpServletRequest request){
        PageResult<UserCouponVo> result = userCouponService.findAllCouponUserPage(pageCriteria,request,state,user);
        for(UserCouponVo userCouponVo:result.getRows()){
            if (userCouponVo.getCouponState() == CouponState.UNUSED){
                if (userCouponVo.getUserInvalidDate().before(new Date())) {
                    UserCoupon userCoupon = userCouponService.get(userCouponVo.getId());
                    userCoupon.setCouponState(CouponState.OVERDUE);
                    userCouponService.merge(userCoupon);
                }
            }
        }
        result = userCouponService.findAllCouponUserPage(pageCriteria,request,state,user);
        for(UserCouponVo userCouponVo:result.getRows()){
            CouponRule couponRule = JsonUtils.toObject(userCouponVo.getUserRule(), CouponRule.class);
            userCouponVo.setRule(couponRule);
        }
        return result;
    }
}
