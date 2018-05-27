/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.GoodsOrderStatus;
import com.klzan.p2p.enums.GoodsType;
import com.klzan.p2p.enums.PointType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.goods.GoodsFollowService;
import com.klzan.p2p.service.goods.GoodsService;
import com.klzan.p2p.service.point.PointRecordService;
import com.klzan.p2p.service.user.UserPointService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户积分
 * @author: chenxinglin
 */
@Controller("webUCPointController")
@RequestMapping("/uc/point")
public class PointController extends BaseController {

    /**
     * 索引重定向URL
     */
    private static final String INDEX_REDIRECT_URL = "redirect:/uc/point";

    /**
     * 模板路径
     */
    private static final String TEMPLATE_PATH = "/uc/point";

    @Inject
    private PointRecordService pointRecordService;

    @Inject
    private UserPointService userPointService;

    @Inject
    private GoodsFollowService goodsFollowService;

    @Inject
    private GoodsService goodsService;

    /**
     * 列表
     */
    @RequestMapping
    public String investments(@CurrentUser User currentUser, ModelMap model, RedirectAttributes redirectAttributes) {
        if(currentUser == null){
            redirectAttributes.addFlashAttribute("flashMessage", "用户不存在");
            return INDEX_REDIRECT_URL;
        }
        UserPoint userPoint = userPointService.findByUserId(currentUser.getId());
        model.addAttribute("userPoint", userPoint);

        model.addAttribute("pointTypes", PointType.values());
        model.addAttribute("goodsOrderStatus", GoodsOrderStatus.values());
        model.addAttribute("goodsTypes", GoodsType.values());
        return TEMPLATE_PATH + "/index";
    }

    /**
     * 积分记录数据
     */
    @RequestMapping("data")
    @ResponseBody
    public PageResult<PointRecord> data(@CurrentUser User currentUser, PageCriteria criteria, PointType type, Integer month, HttpServletRequest request) {
        criteria.setRows(5);
        return pointRecordService.findPage(buildQueryCriteria(criteria, request), currentUser.getId(), type, month);
    }

    /**
     * 关注商品数据
     */
    @RequestMapping("follow/data")
    @ResponseBody
    public PageResult<GoodsFollow> follow_data(@CurrentUser User currentUser, PageCriteria criteria, HttpServletRequest request) {
        criteria.setRows(5);
        return goodsFollowService.findPage(buildQueryCriteria(criteria, request), currentUser.getId());
    }

    /**
     * 可购
     */
    @RequestMapping("canbuy")
    public String canbuy(@CurrentUser User currentUser, ModelMap model, RedirectAttributes redirectAttributes) {
        if(currentUser == null){
            redirectAttributes.addFlashAttribute("flashMessage", "未登录");
            return INDEX_REDIRECT_URL;
        }
        UserPoint userPoint = userPointService.findByUserId(currentUser.getId());
        model.addAttribute("userPoint", userPoint);
        return "/mall/canbuy";
    }

    /**
     * 可购数据
     */
    @RequestMapping("canbuy/data")
    @ResponseBody
    public PageResult<Goods> canbuydata(@CurrentUser User currentUser, PageCriteria criteria, HttpServletRequest request) {
        UserPoint userPoint = userPointService.findByUserId(currentUser.getId());
        criteria.setRows(20);
        PageResult<Goods> page = goodsService.findPage(buildQueryCriteria(criteria, request), null, null, userPoint.available());
        return page;
    }

//    /**
//     * 签到
//     * @param currentUser
//     * @return
//     */
//    @RequestMapping("sign_in")
//    @ResponseBody
//    public Result investments(@CurrentUser User currentUser) {
//        if(currentUser == null){
//            return Result.error("用户不存在");
//        }
//        try {
//            userPointService.signIn(currentUser.getId());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.error("签到失败");
//        }
//        return Result.success("签到成功");
//    }

}





