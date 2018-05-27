package com.klzan.mobile.controller.cpcn;

import com.klzan.p2p.common.util.UserUtils;
import com.klzan.plugin.pay.common.PageRequest;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.BankcardBindRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 开户
 */
@Controller("mobilePayBankcardController")
@RequestMapping("/mobile/pay/bankcard")
public class PayBankcardController {

    protected static final String ERROR_VIEW = "/error";

    @RequestMapping(value = "bind")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model, Boolean verify, String bindingSystemNo, RedirectAttributes redirectAttributes) throws Exception {
        try {
            PayModule payModule = PayPortal.bankcard_bind.getModuleInstance();
            BankcardBindRequest req = new BankcardBindRequest();
            req.setUserId(UserUtils.getCurrentUser().getId());
            if(BooleanUtils.isTrue(verify)){
                req.setBindingSystemNo(bindingSystemNo);
            }
            req.setMobile(true);
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
            req.setMobile(true);
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
