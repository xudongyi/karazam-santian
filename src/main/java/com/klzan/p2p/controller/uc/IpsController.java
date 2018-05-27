package com.klzan.p2p.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.SignType;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.vo.business.Request;
import com.klzan.p2p.vo.user.AbstractUserMeta;
import com.klzan.p2p.vo.user.UserAutoInvestVo;
import com.klzan.p2p.vo.user.UserAutoRepayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 环迅登录
 * Created by suhao Date: 2017/4/8 Time: 12:10
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("uc/ips")
public class IpsController extends BaseController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "open_acc", method = RequestMethod.GET)
    public String openAccount(@CurrentUser User user, Model model) {
        Request request = businessService.openAccount(user);
        model.addAttribute("requestUrl", request.getRequestUrl());
        model.addAttribute("parameterMap", request.getParameterMap());
        return "payment/submit";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(@CurrentUser User user, Model model) {
        Request request = businessService.login(user);
        model.addAttribute("requestUrl", request.getRequestUrl());
        model.addAttribute("parameterMap", request.getParameterMap());
        return "payment/submit";
    }

    @RequestMapping(value = "balance", method = RequestMethod.GET)
    @ResponseBody
    public Object balance(@CurrentUser User user) {
        return businessService.balanceQuery(user);
    }

    /**
     * 自动投标/还款签约
     */
    @RequestMapping(value = "auto_sign", method = RequestMethod.POST)
    @ResponseBody
    public Result autoSign(@CurrentUser User user, HttpServletRequest req, SignType signType) {
        Map<String, Object> requestParamMap = WebUtils.getRequestParamMap(req);
        requestParamMap.remove("signType");
        AbstractUserMeta autoSign;
        String json = JsonUtils.toJson(requestParamMap);
        if (signType == SignType.AUTO_INVESTMENT_SIGN) {
            autoSign = JsonUtils.toObject(json, UserAutoInvestVo.class);
        } else {
            autoSign = JsonUtils.toObject(json, UserAutoRepayVo.class);
        }
        try {
            Request request = businessService.autoSign(user, signType, autoSign);
            return Result.success(SUCCESS_MSG, request);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 自动投标/还款解约
     */
    @RequestMapping(value = "un_sign", method = RequestMethod.GET)
    public String unSign(@CurrentUser User user, SignType signType, RedirectAttributes redirectAttributes) {
        businessService.unSign(user, signType);
        redirectAttributes.addFlashAttribute("flashMessage", "解约成功");
        return "redirect:/uc/security";
    }

}
