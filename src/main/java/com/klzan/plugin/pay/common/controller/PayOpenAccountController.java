package com.klzan.plugin.pay.common.controller;//package com.klzan.plugin.pay.cpcn.controller;

import com.klzan.core.Result;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.plugin.pay.common.PageRequest;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.common.module.PayModule;
//import com.klzan.plugin.pay.cpcn.service.CpcnPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 开户
 */
@Controller
@RequestMapping("/pay")
public class PayOpenAccountController{

    protected static final String ERROR_VIEW = "/error";

    @Autowired
    private PaymentOrderService paymentOrderService;

    @RequestMapping(value = "open-account")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception {
        try {

            PaymentOrder paymentOrder = paymentOrderService.find(UserUtils.getCurrentUser().getId(), PaymentOrderStatus.PROCESSING, PaymentOrderType.OPEN_ACCOUNT);
            PayModule payModule = PayPortal.open_account.getModuleInstance();
            if(paymentOrder != null){
                payModule.setSn(paymentOrder.getOrderNo());
                payModule.setExistOrder(true);
            }
            UserInfoRequest req = new UserInfoRequest();
            req.setUserId(UserUtils.getCurrentUser().getId());
            if(false){
                req.setMobile(true);
            }
            payModule.setRequest(req);
            PageRequest param = payModule.invoking().getPageRequest();
            model.addAttribute("requestUrl", param.getRequestUrl());
            model.addAttribute("parameterMap", param.getParameterMap());
            return "/payment/submit";
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_VIEW;
        }
    }

    @RequestMapping(value = "open-account/query")
    @ResponseBody
    public Result openAccountQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            PayModule payModule = PayPortal.open_account_query.getModuleInstance();
            UserInfoRequest req = new UserInfoRequest();
            req.setUserId(UserUtils.getCurrentUser().getId());
            payModule.setRequest(req);
            Response bResponse = payModule.invoking().getResponse();

            return Result.success("", bResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
    }


}
