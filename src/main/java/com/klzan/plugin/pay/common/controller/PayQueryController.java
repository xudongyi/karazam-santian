package com.klzan.plugin.pay.common.controller;

import com.klzan.core.exception.PayException;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.plugin.pay.common.FormatUtil;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单查询
 */
@Controller
@RequestMapping("/pay")
public class PayQueryController /*extends BaseController*/ {

    @Autowired
    protected PaymentOrderService paymentOrderService;
    @Autowired
    protected PayUtils payUtils;

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "query")
    @ResponseBody
    public Response query(HttpServletRequest request) throws Exception {
        try {
            String sn = request.getParameter("sn");
            return payUtils.query(sn);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error();
        }
    }

    /**
     *
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "query-page")
    public String querypage(HttpServletRequest request, Model model) throws Exception {
        Response response = null;
        try {
            String sn = request.getParameter("sn");
            model.addAttribute("orderNo", sn);
            response = payUtils.query(sn);
        } catch (Exception e) {
            e.printStackTrace();
            response = Response.error();
        }
        model.addAttribute("result", FormatUtil.formatJson(JsonUtils.toJson(response)));
        return "orderquerycpcn/index";
    }

}
