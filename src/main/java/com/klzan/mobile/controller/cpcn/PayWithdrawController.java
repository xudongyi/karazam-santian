package com.klzan.mobile.controller.cpcn;

import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.DeviceType;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.setting.WithdrawSetting;
import com.klzan.p2p.vo.withdraw.WithdrawalVo;
import com.klzan.plugin.pay.common.PageRequest;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.WithdrawRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.math.BigDecimal;

/**
 * 提现
 */
@Controller("mobilePayWithdrawController")
@RequestMapping("/mobile/pay")
public class PayWithdrawController {

    protected static final String ERROR_VIEW = "/error";

    @Inject
    private SettingUtils settingUtils;

    @RequestMapping(value = "withdraw")
    public String index(Model model, BigDecimal amount) {
        WithdrawalVo withdrawalVo = new WithdrawalVo();
        withdrawalVo.setUserId(UserUtils.getCurrentUser().getId());
        withdrawalVo.setAmount(amount);
        WithdrawSetting withdrawSetting = settingUtils.getWithdrawSetting();
        if (withdrawalVo.getAmount().compareTo(withdrawSetting.getBeginAmount()) < 0) {
            model.addAttribute("flashMessage", String.format("提现金额必须大于%s元", withdrawSetting.getBeginAmount().toString()));
            return ERROR_VIEW;
        }
        PayModule payModule = PayPortal.withdraw.getModuleInstance();
        WithdrawRequest req = new WithdrawRequest(true, UserUtils.getCurrentUser().getId(), withdrawalVo.getAmount());
        payModule.setRequest(req);
        PageRequest param = payModule.invoking().getPageRequest();
        model.addAttribute("requestUrl", param.getRequestUrl());
        model.addAttribute("parameterMap", param.getParameterMap());
        return "payment/submit";
    }

}
