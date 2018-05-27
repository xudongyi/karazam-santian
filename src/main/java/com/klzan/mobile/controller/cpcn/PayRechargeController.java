package com.klzan.mobile.controller.cpcn;

import com.klzan.core.Result;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.plugin.pay.common.PageRequest;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.*;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * Created by suhao Date: 2017/11/23 Time: 20:04
 *
 * @version: 1.0
 */
@Controller("mobilePayRechargeController")
@RequestMapping("/mobile/pay")
public class PayRechargeController {

    protected static final String ERROR_VIEW = "/error";

    @RequestMapping(value = "recharge")
    public String index(BigDecimal amount, Model model) {
        try {
            PayModule payModule = PayPortal.recharge.getModuleInstance();
            RechargeRequest req = new RechargeRequest(true, UserUtils.getCurrentUser().getId(), amount);
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

    @RequestMapping(value = "recharge", method = RequestMethod.POST)
    @ResponseBody
    public Result rechargePost(BigDecimal amount, Model model) {
        try {
            PayModule payModule = PayPortal.recharge.getModuleInstance();
            RechargeRequest req = new RechargeRequest(false, UserUtils.getCurrentUser().getId(), amount);
            payModule.setRequest(req);
            PageRequest param = payModule.invoking().getPageRequest();
            return Result.success("", param);
        } catch (Exception e) {
            return Result.error();
        }
    }

    @RequestMapping("bal_query")
    @ResponseBody
    public Result balQuery() {
        PayModule payModule = PayPortal.account_balance_query.getModuleInstance();
        UserInfoRequest req = new UserInfoRequest();
        req.setUserId(UserUtils.getCurrentUser().getId());
        payModule.setRequest(req);
        Response response = payModule.invoking().getResponse();
        return Result.success("", response.getTxResponse());
    }

    @RequestMapping("bind_card_query")
    @ResponseBody
    public Result bindCardQuery() {
        PayModule payModule = PayPortal.bankcard_bind_query.getModuleInstance();
        UserInfoRequest req = new UserInfoRequest();
        req.setUserId(UserUtils.getCurrentUser().getId());
        payModule.setRequest(req);
        Response response = payModule.invoking().getResponse();
        return Result.success("", response.getTxResponse());
    }

    @RequestMapping("signed_query")
    @ResponseBody
    public Result signedQuery(String type) throws Exception {
        if (!StringUtils.equals(type, "20") && !StringUtils.equals(type, "60")) {
            return Result.error();
        }
        PayModule payModule = PayPortal.payment_account_signed_query.getModuleInstance();
        SignedRequest req = new SignedRequest();
        req.setUserId(UserUtils.getCurrentUser().getId());
        req.setAgreementType(type);
        payModule.setRequest(req);
        Response response = payModule.invoking().getResponse();
        return Result.success("", response.getTxResponse());
    }

    @RequestMapping("auto_invest_query")
    @ResponseBody
    public Result autoInvestQuery(String sn) throws Exception {
        PayModule payModule = PayPortal.invest_auto_query.getModuleInstance();
        SnRequest req = new SnRequest();
        req.setSn(sn);
        payModule.setRequest(req);
        Response response = payModule.invoking().getResponse();
        return Result.success("", response.getTxResponse());
    }

}
