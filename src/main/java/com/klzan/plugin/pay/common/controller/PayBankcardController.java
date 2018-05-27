package com.klzan.plugin.pay.common.controller;

import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.plugin.pay.common.PageRequest;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.BankcardBindRequest;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 开户
 */
@Controller
@RequestMapping("/pay/bankcard")
public class PayBankcardController {

    protected static final String ERROR_VIEW = "/error";

    @Autowired
    private PaymentOrderService paymentOrderService;

    @RequestMapping(value = "bind")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model, Boolean verify, String bindingSystemNo, RedirectAttributes redirectAttributes) throws Exception {
        try {
            User currentUser = UserUtils.getCurrentUser();
            if (null == currentUser) {
                redirectAttributes.addFlashAttribute("flashMessage", "未登录");
                return "redirect:/";
            }
            Integer userId = currentUser.getId();
            List<PaymentOrder> orders = paymentOrderService.findList(userId, PaymentOrderStatus.PROCESSING, PaymentOrderType.BINDCARD_BIND);
            if (null != orders && orders.size() > 1) {
                redirectAttributes.addFlashAttribute("flashMessage", "您有处理中的绑卡，请稍候再试");
                return "redirect:/";
            }
            PayModule payModule = PayPortal.bankcard_bind.getModuleInstance();
            BankcardBindRequest req = new BankcardBindRequest();
            req.setUserId(userId);
            if(BooleanUtils.isTrue(verify)){
                req.setBindingSystemNo(bindingSystemNo);
            }
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

    @RequestMapping(value = "unbind")
    public String openAccountQuery(Model model, HttpServletRequest request, HttpServletResponse response, String bindingSystemNo) throws Exception {
        try {
            PayModule payModule = PayPortal.bankcard_unbind.getModuleInstance();
            BankcardBindRequest req = new BankcardBindRequest();
            req.setUserId(UserUtils.getCurrentUser().getId());
            req.setBindingSystemNo(bindingSystemNo);
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


}
