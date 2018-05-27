/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.capital;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.PaymentOrderMethod;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.util.ExcelView;
import com.klzan.p2p.vo.capital.PaymentOrderVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 支付订单
 * Created by zhutao Date: 2017/04/12
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("admin/paymentOrder")
public class PaymentOrderController extends BaseAdminController {

    @Inject
    private PaymentOrderService paymentOrderService;

    /**
     * 支付订单首页
     * @param model
     * @return
     */
    @RequiresPermissions("capital:paymentOrder:view")
    @RequestMapping(method = RequestMethod.GET)
    public String index(@RequestParam(defaultValue = "false") Boolean toshow, Model model, Boolean notice) {
        model.addAttribute("hasMendRecharge", SecurityUtils.getSubject().isPermitted("capital:paymentOrder:order:mend"));
        model.addAttribute("types", PaymentOrderType.values());
        model.addAttribute("statuses", PaymentOrderStatus.values());
        model.addAttribute("methods", PaymentOrderMethod.values());
        model.addAttribute("notice", notice);
        model.addAttribute("toshow", toshow);
        return "admin/capital/payment_order_index";
    }

    /**
     * 前台支付订单数据
     * @param criteria
     * @param request
     * @return
     */
    @RequiresPermissions("capital:paymentOrder:order:view")
    @RequestMapping(value="/order/json",method = RequestMethod.GET)
    @ResponseBody
    public PageResult getPaymentOrders(@RequestParam(defaultValue = "false") Boolean toshow, PageCriteria criteria, HttpServletRequest request) {
        PageResult<PaymentOrderVo> result = paymentOrderService.findPage(buildQueryCriteria(criteria, request), false);
        if (!toshow) {
            for (PaymentOrderVo paymentOrderVo : result.getRows()) {
                paymentOrderVo.setMobile("");
            }
        }
        return result;
    }

    /**
     * 后台支付订单数据
     * @param criteria
     * @param request
     * @return
     */
    @RequiresPermissions("capital:paymentOrder:bgorder:view")
    @RequestMapping(value="/bgorder/json", method = RequestMethod.GET)
    @ResponseBody
    public PageResult getBgPaymentOrders(@RequestParam(defaultValue = "false") Boolean toshow, PageCriteria criteria, HttpServletRequest request) {
        ParamsFilter.buildFromHttpRequest(request, criteria);
        PageResult<PaymentOrderVo> result = paymentOrderService.findPage(criteria, true);
        if (!toshow) {
            for (PaymentOrderVo paymentOrderVo : result.getRows()) {
                paymentOrderVo.setMobile("");
            }
        }
        return result;
    }

    @RequestMapping(value="/findAllOrder", method = RequestMethod.POST)
    @ResponseBody
    public Result findAllOrder(HttpServletRequest request) {
        List result = paymentOrderService.findAllOrder(false,request.getParameter("mobile"),request.getParameter("orderNo"),
                request.getParameter("type"),request.getParameter("status"),request.getParameter("method"),request.getParameter("startCreateDate"),request.getParameter("endCreateDate"));
        return Result.success("成功",result);
    }
    @RequestMapping(value="/findAllBgOrder", method = RequestMethod.POST)
    @ResponseBody
    public Result findAllBgOrder(HttpServletRequest request) {
        List result = paymentOrderService.findAllOrder(true,request.getParameter("mobile"),request.getParameter("orderNo"),
                request.getParameter("type"),request.getParameter("status"),request.getParameter("method"),request.getParameter("startCreateDate"),request.getParameter("endCreateDate"));
        return Result.success("成功",result);
    }

    /**
     * 描述：导出Excel
     * @param request
     * @throws Exception
     */
    @RequestMapping(value="exportPaymentOrder")
    public ModelAndView exportPaymentOrder(ModelMap model, HttpServletRequest request){

        List<PaymentOrderVo> result = paymentOrderService.findAllOrder(false,request.getParameter("filter_LIKES_mobile"),request.getParameter("filter_LIKES_order_no"),
                request.getParameter("filter_EQS_p.type"),request.getParameter("filter_EQS_p.status"),request.getParameter("filter_EQS_method"),
                request.getParameter("filter_GTD_p.create_date"),request.getParameter("filter_LTD_p.create_date"));
        String[] properties = { "id","mobile", "orderNo", "createDate", "typeStr", "statusStr",
                "methodStr", "amount","memo"};
        String[] titles = { "订单ID", "手机号", "订单号", "创建时间", "订单类型", "订单状态",
                "支付方式","金额","备注"};
        String filename = "order_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
        BigDecimal amount = new BigDecimal(0);
        for (PaymentOrderVo vo:result){
            amount = amount.add(vo.getAmount());
        }
        String[] contents = {"金额共计:"+amount+"元",
                "导出条数：" + result.size(), "操作日期：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) };
        return new ModelAndView(new ExcelView(filename, null, properties, titles, null, null, result,
                contents), model);
    }
    /**
     * 描述：导出Excel
     * @param request
     * @throws Exception
     */
    @RequestMapping(value="exportPaymentBgOrder")
    public ModelAndView exportPaymentBgOrder(ModelMap model, HttpServletRequest request){

        List<PaymentOrderVo> result = paymentOrderService.findAllOrder(true,request.getParameter("filter_LIKES_mobile"),request.getParameter("filter_LIKES_order_no"),
                request.getParameter("filter_EQS_p.type"),request.getParameter("filter_EQS_p.status"),request.getParameter("filter_EQS_method"),
                request.getParameter("filter_GTD_p.create_date"),request.getParameter("filter_LTD_p.create_date"));
        String[] properties = { "id","mobile", "orderNo", "createDate", "typeStr", "statusStr",
                "methodStr", "amount","memo"};
        String[] titles = { "订单ID", "手机号", "订单号", "创建时间", "订单类型", "订单状态",
                "支付方式","金额","备注"};
        String filename = "bgOrder_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
        BigDecimal amount = new BigDecimal(0);
        for (PaymentOrderVo vo:result){
            amount = amount.add(vo.getAmount());
        }
        String[] contents = {"金额共计:"+amount+"元",
                "导出条数：" + result.size(), "操作日期：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) };
        return new ModelAndView(new ExcelView(filename, null, properties, titles, null, null, result,
                contents), model);
    }
}
