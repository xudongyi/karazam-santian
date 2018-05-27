package com.klzan.mobile.controller.cpcn;

import com.klzan.p2p.common.util.UserUtils;
import com.klzan.plugin.pay.common.PageRequest;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 开户
 */
@Controller("mobilePayLoginController")
@RequestMapping("/mobile/pay")
public class PayLoginController {

    protected static final String ERROR_VIEW = "/error";

    @RequestMapping(value = "login")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception {
        try {
            PayModule payModule = PayPortal.login.getModuleInstance();
            UserInfoRequest req = new UserInfoRequest();
            req.setUserId(UserUtils.getCurrentUser().getId());
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
