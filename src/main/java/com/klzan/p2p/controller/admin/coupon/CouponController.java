/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.coupon;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.CouponSource;
import com.klzan.p2p.enums.CouponType;
import com.klzan.p2p.enums.PeriodUnit;
import com.klzan.p2p.model.Coupon;
import com.klzan.p2p.service.coupon.CouponService;
import com.klzan.p2p.vo.coupon.CouponRule;
import com.klzan.p2p.vo.coupon.CouponVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 优惠券管理
 * Created by zhutao Date: 2017/06/01
 *
 * @version: 1.0
 */
@Controller("adminCouponController")
@RequestMapping("admin/coupon")
public class CouponController extends BaseAdminController {

    @Resource
    private CouponService couponService;

    @RequestMapping("list")
    @RequiresPermissions("coupon:admin:list")
    public String list(ModelMap modelMap){
        //优惠券类型
        modelMap.addAttribute("couponTypes", CouponType.values());
        return "admin/coupon/list";
    }

    @RequestMapping("list.do")
    @RequiresPermissions("coupon:admin:list")
    @ResponseBody
    public Object listDo(PageCriteria pageCriteria,HttpServletRequest request){

        return couponService.findAllCouponPage(pageCriteria,request);
    }

    @RequestMapping("create")
    @RequiresPermissions("coupon:admin:create")
    public String create(ModelMap modelMap){
        //优惠券类型
        modelMap.addAttribute("couponTypes", CouponType.values());
        modelMap.addAttribute("periodUnits", PeriodUnit.values());
        modelMap.addAttribute("couponSources", CouponSource.values());
        return "admin/coupon/create";
    }

    @RequestMapping("create.do")
    @RequiresPermissions("coupon:admin:create")
    @ResponseBody
    public Result createDo(CouponVo vo, ModelMap modelMap){

        String json = JsonUtils.toJson(vo.getCouponRule());
        Coupon coupon = new Coupon(
                vo.getCouponSource(), vo.getCouponType(), vo.getIsRandomAmount(),vo.getRandomAmountMix(),
                vo.getRandomAmountMax(),vo.getAvailable(), vo.getAmount(),new Integer(vo.getCouponNumber()),
                vo.getAvailableByCategory(), vo.getInvalidDate(), json
        );
        Boolean canCreate = couponService.canCreate(coupon);
        if (canCreate){
            couponService.merge(coupon);
            return Result.success();
        }else {
            return Result.error("当前来源: '"+coupon.getCouponSource().getDisplayName()+"'存在可用且未到期的优惠券分类");
        }
    }

    @RequestMapping("update/{id}")
    @RequiresPermissions("coupon:admin:update")
    public String update(ModelMap modelMap,@PathVariable Integer id){
        //优惠券类型
        modelMap.addAttribute("couponTypes", CouponType.values());
        modelMap.addAttribute("periodUnits", PeriodUnit.values());
        //优惠券来源
        modelMap.addAttribute("couponSources", CouponSource.values());
        //操作
        Coupon coupon = couponService.get(id);
        modelMap.addAttribute("coupon",coupon);
        CouponRule couponRule = JsonUtils.toObject(coupon.getRule(), CouponRule.class);
        modelMap.addAttribute("couponRule",couponRule);
        return "admin/coupon/update";
    }

    @RequestMapping("update.do/{id}")
    @RequiresPermissions("coupon:admin:update")
    @ResponseBody
    public Result updateDo(CouponVo vo,@PathVariable Integer id){
        Coupon coupon = couponService.get(id);
        String json = JsonUtils.toJson(vo.getCouponRule());
        coupon.setCouponType(vo.getCouponType());
        coupon.setIsRandomAmount(vo.getIsRandomAmount());
        coupon.setRandomAmountMix(vo.getRandomAmountMix());
        coupon.setRandomAmountMax(vo.getRandomAmountMax());
        coupon.setAvailable(vo.getAvailable());
        coupon.setAmount(vo.getAmount());
        coupon.setCouponNumber(new Integer(vo.getCouponNumber()));
        coupon.setAvailableByCategory(vo.getAvailableByCategory());
        coupon.setInvalidDate(vo.getInvalidDate());
        coupon.setRule(json);
        coupon.setCouponSource(vo.getCouponSource());
        try{
            couponService.update(coupon);
            return Result.success();
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("修改失败");
        }
    }

    @RequestMapping(value = "delete.do/{id}",method = RequestMethod.DELETE)
    @RequiresPermissions("coupon:admin:delete")
    @ResponseBody
    public Result deleteDo(@PathVariable String id, ModelMap modelMap){
        try {
            couponService.remove(new Integer(id));
            return Result.success();
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("删除数据异常");
        }
    }
}
