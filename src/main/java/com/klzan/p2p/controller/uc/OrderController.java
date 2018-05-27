package com.klzan.p2p.controller.uc;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.OrderType;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.Order;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户订单
 * Created by suhao Date: 2017/4/23 Time: 15:38
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("uc/order")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @RequestMapping
    public String index(OrderType orderType, Model model) {
        model.addAttribute("orderTypes", OrderType.values());
        model.addAttribute("orderType", orderType);
        model.addAttribute("recordStatus", RecordStatus.values());
        return "uc/order/index";
    }

    @RequestMapping("data")
    @ResponseBody
    public PageResult<Order> data(@CurrentUser User currentUser, PageCriteria criteria, OrderType method, HttpServletRequest request) {
        return orderService.findPage(buildQueryCriteria(criteria, request), currentUser.getId(), method);
    }

}
