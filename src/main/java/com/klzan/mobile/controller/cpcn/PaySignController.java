package com.klzan.mobile.controller.cpcn;

import com.klzan.core.Result;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.plugin.pay.common.PageRequest;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.common.dto.SignedRequest;
import com.klzan.plugin.pay.common.dto.TerminationRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 开户
 */
@Controller("mobilePaySignController")
@RequestMapping("/mobile/pay")
public class PaySignController {

    protected static final String ERROR_VIEW = "/error";

    @RequestMapping(value = "signed")
    public String index(Model model, String agreementType, RedirectAttributes redirectAttributes) throws Exception {
        try {
            PayModule payModule = PayPortal.signed.getModuleInstance();
            SignedRequest req = new SignedRequest();
            req.setUserId(UserUtils.getCurrentUser().getId());
            req.setAgreementType(agreementType);
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

    @RequestMapping(value = "termination")
    @ResponseBody
    public Result openAccountQuery(String agreementNo) throws Exception {
        try {
            PayModule payModule = PayPortal.termination.getModuleInstance();
            TerminationRequest req = new TerminationRequest();
            req.setUserId(UserUtils.getCurrentUser().getId());
            req.setAgreementNo(agreementNo);
            payModule.setRequest(req);
            Response response = payModule.invoking().getResponse();
            if (response.isSuccess()){
                return Result.success();
            }
            return Result.error();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }


}
