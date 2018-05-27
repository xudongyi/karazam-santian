/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.controller;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.content.AdService;
import com.klzan.p2p.service.goods.GoodsCategoryService;
import com.klzan.p2p.service.goods.GoodsOrderService;
import com.klzan.p2p.service.goods.GoodsService;
import com.klzan.p2p.service.user.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 商城
 * @author: chenxinglin
 */
@Controller("webMallController")
@RequestMapping("/mall")
public class MallController extends BaseController {

    /**
     * 索引重定向URL
     */
    private static final String INDEX_REDIRECT_URL = "redirect:/mall";

    /**
     * 模板路径
     */
    private static final String TEMPLATE_PATH = "/mall";

    @Inject
    private GoodsCategoryService goodsCategoryService;

    @Inject
    private GoodsService goodsService;

    @Inject
    private GoodsOrderService goodsOrderService;

    @Inject
    private UserPointService userPointService;

    @Inject
    private UserShippingAddressService addressService;

    @Inject
    private AdService adService;

    /**
     * 首页
     */
    @RequestMapping
    public String index(ModelMap model) {
        List<Goods> gifts = goodsService.findList(null, GoodsType.gift, 6, null);
        List<Goods> benefits = goodsService.findList(null, GoodsType.benefit, 8, null);
        model.addAttribute("gifts", gifts);
        model.addAttribute("benefits", benefits);
        model.addAttribute("banners", adService.findAdByIdent("MALL_BANNER"));
        return TEMPLATE_PATH + "/index";
    }

    /**
     * 礼品店
     */
    @RequestMapping("gift")
    public String gift(ModelMap model) {
        List<Goods> hots = goodsService.findList(null, null, 4, true);
        model.addAttribute("hots", hots);
        return TEMPLATE_PATH + "/gift";
    }

    /**
     * 实惠购
     */
    @RequestMapping("benefit")
    public String benefit(ModelMap model) {
        List<Goods> hots = goodsService.findList(null, null, 4, true);
        model.addAttribute("hots", hots);
        return TEMPLATE_PATH + "/benefit";
    }

    /**
     * 列表数据
     */
    @RequestMapping("data")
    @ResponseBody
    public PageResult<Goods> data(PageCriteria criteria, GoodsType type, HttpServletRequest request) {
        criteria.setRows(4);
        if(type!=null && type.equals(GoodsType.benefit)){
            criteria.setRows(12);
        }
        PageResult<Goods> page = goodsService.findPage(buildQueryCriteria(criteria, request), null, type, null);
        return page;
    }

    /**
     * 商品
     */
    @RequestMapping("goods/{goodsCategory}")
    public String goods(@RequestParam Integer goodsCategory, ModelMap model, RedirectAttributes redirectAttributes) {
        if(goodsCategory == null){
            redirectAttributes.addFlashAttribute("flashMessage", "参数错误");
            return INDEX_REDIRECT_URL;
        }
        model.addAttribute("goods", goodsService.findList(goodsCategory));
        return TEMPLATE_PATH + "/list";
    }

    /**
     * 商品详情
     */
    @RequestMapping("goods/detail/{id}")
    public String goodsDetail(@PathVariable Integer id, ModelMap model, RedirectAttributes redirectAttributes) {
       if(id == null){
            redirectAttributes.addFlashAttribute("flashMessage", "参数错误");
            return INDEX_REDIRECT_URL;
        }
        model.addAttribute("goods", goodsService.get(id));
        List<GoodsOrder> goodsOrders = goodsOrderService.findListByGoods(id);
        model.addAttribute("goodsOrders", goodsOrders);
        User currentUser = UserUtils.getCurrentUser();
        if(currentUser != null){
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("userPoint", userPointService.findByUserId(currentUser.getId()));
            model.addAttribute("addresses", addressService.findListByUserId(currentUser.getId()));
        }
        return TEMPLATE_PATH + "/detail";
    }

    /**
     * 关注商品
     */
    @RequestMapping("follow")
    @ResponseBody
    public Result follow(Integer goodsId) {
        User currentUser = UserUtils.getCurrentUser();
        if(currentUser==null){
            return Result.error("未登录");
        }
        Goods goods = goodsService.get(goodsId);
        if(goods==null){
            return Result.error("商品不存在");
        }
        try {
            goodsService.follow(currentUser, goods);
            return Result.success("成功");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("失败");
        }
    }

}





