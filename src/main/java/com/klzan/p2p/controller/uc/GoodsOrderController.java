/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.GoodsOrderStatus;
import com.klzan.p2p.enums.GoodsShippingStatus;
import com.klzan.p2p.enums.GoodsType;
import com.klzan.p2p.enums.PointType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.content.AreaService;
import com.klzan.p2p.service.goods.GoodsOrderService;
import com.klzan.p2p.service.goods.GoodsService;
import com.klzan.p2p.service.user.UserPointService;
import com.klzan.p2p.service.user.UserShippingAddressService;
import com.klzan.p2p.vo.goods.GoodsExchangeVo;
import com.klzan.p2p.vo.user.UserShippingAddressVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品订单
 * @author: chenxinglin
 */
@Controller("webUCGoodsOrderController")
@RequestMapping("/uc/goods_order")
public class GoodsOrderController extends BaseController {

    /**
     * 索引重定向URL
     */
    private static final String INDEX_REDIRECT_URL = "redirect:/uc/goods_order";

    /**
     * 模板路径
     */
    private static final String TEMPLATE_PATH = "/uc/goods_order";

    @Inject
    private GoodsOrderService goodsOrderService;

    @Inject
    private GoodsService goodsService;

    @Inject
    private UserShippingAddressService addressService;

    @Inject
    private UserPointService userPointService;

    /**
     * 商品订单数据
     */
    @RequestMapping("data")
    @ResponseBody
    public PageResult<GoodsOrder> data(@CurrentUser User currentUser, PageCriteria criteria, GoodsType type, Integer month, GoodsOrderStatus status, HttpServletRequest request) {
        criteria.setRows(5);
        return goodsOrderService.findPage(buildQueryCriteria(criteria, request), currentUser.getId(), type, status, month);
    }

    /**
     * 商品订单详情
     */
    @RequestMapping("detail/{id}")
    public String detail(@CurrentUser User currentUser, @PathVariable Integer id, ModelMap modelMap) {
        if(id == null){
//            return Result.error("参数错误");
        }
        GoodsOrder goodsOrder = goodsOrderService.get(id);
        if(goodsOrder == null){
//            return Result.error("订单不存在");
        }
        modelMap.addAttribute("goodsOrder", goodsOrder);
        modelMap.addAttribute("goods", goodsService.get(goodsOrder.getGoods()));
        return "/uc/goods_order/order_detail";
    }

    /**
     * 提交订单（发起）
     */
    @RequestMapping("add")
    @ResponseBody
    public Result add(@CurrentUser User currentUser, GoodsExchangeVo vo, HttpServletRequest request) {
        if(currentUser == null){
            return Result.error("未登录");
        }
        if(vo == null || vo.getGoodsId()==null || vo.getPoint()==null || vo.getAddress()==null || vo.getQuantity()==null){
            return Result.error("参数错误");
        }
        Goods goods = goodsService.get(vo.getGoodsId());
        if(goods == null){
            return Result.error("商品不存在");
        }
        if(goods.getStock().intValue() < vo.getQuantity()){
            return Result.error("商品库存不足");
        }
        UserShippingAddress address = addressService.get(vo.getAddress());
        if(address == null){
            return Result.error("收货地址不存在");
        }
        if(!currentUser.getId().equals(address.getUserId())){
            return Result.error("拒绝访问");
        }
        if(vo.getPoint().intValue() != goods.getPoint().intValue() * vo.getQuantity().intValue()){
            return Result.error("参数错误");
        }
        UserPoint userPoint = userPointService.findByUserId(currentUser.getId());
        if(vo.getPoint().intValue() > userPoint.available()){
            return Result.error("积分余额不足");
        }

        try {
            GoodsOrder goodsOrder = goodsOrderService.submit(currentUser, vo);
            return Result.success(goodsOrder.getId().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("操作失败");
        }
    }

    /**
     * 确认订单（支付/兑换）
     */

    /**
     * 撤销订单
     */
    @RequestMapping("cancel")
    @ResponseBody
    public Result cancel(@CurrentUser User currentUser, Integer orderId, HttpServletRequest request) {
        if(currentUser == null){
            return Result.error("未登录");
        }
        if(orderId == null){
            return Result.error("参数错误");
        }
        GoodsOrder goodsOrder = goodsOrderService.get(orderId);
        if(goodsOrder == null){
            return Result.error("订单不存在");
        }
        if(goodsOrder.getLogisticsStatus().equals(GoodsShippingStatus.shipped)){
            return Result.error("订单已发货，仍要撤销，请联系客服");
        }
        if(!currentUser.getId().equals(goodsOrder.getUserId())){
            return Result.error("拒绝访问");
        }

        try {
            goodsOrderService.cancel(goodsOrder);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("操作失败");
        }
    }


    /**
     * 确认收货
     */
    @RequestMapping("receive")
    @ResponseBody
    public Result receive(@CurrentUser User currentUser, Integer orderId, HttpServletRequest request) {
        if(currentUser == null){
            return Result.error("未登录");
        }
        if(orderId == null){
            return Result.error("参数错误");
        }
        GoodsOrder goodsOrder = goodsOrderService.get(orderId);
        if(goodsOrder == null){
            return Result.error("订单不存在");
        }
        if(!goodsOrder.getLogisticsStatus().equals(GoodsShippingStatus.shipped)){
            return Result.error("参数错误");
        }
        if(!currentUser.getId().equals(goodsOrder.getUserId())){
            return Result.error("拒绝访问");
        }

        try {
            goodsOrderService.receive(goodsOrder);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("操作失败");
        }
    }

    /**
     * 退货/换货 ？？？
     */

}





