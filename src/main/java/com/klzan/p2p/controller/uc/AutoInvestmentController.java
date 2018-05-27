package com.klzan.p2p.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.enums.OperationMethod;
import com.klzan.p2p.enums.SignType;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserAutoInvestmentRank;
import com.klzan.p2p.service.investment.AutoInvestmentService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.user.UserAutoInvestmentRankService;
import com.klzan.p2p.service.user.UserMetaService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.investment.InvestmentRecordSimpleVo;
import com.klzan.p2p.vo.user.UserAutoInvestVo;
import com.klzan.p2p.vo.user.UserVo;
import com.klzan.plugin.pay.common.PayUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动投标
 * Created by suhao Date: 2017/6/9 Time: 14:12
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("/uc/autoInvestment")
public class AutoInvestmentController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMetaService userMetaService;

    @Autowired
    private InvestmentRecordService investmentRecordService;

    @Autowired
    private UserAutoInvestmentRankService userAutoInvestmentRankService;

    @Autowired
    private AutoInvestmentService autoInvestmentService;

    @Autowired
    private PayUtils payUtils;

    @Autowired
    private SettingUtils setting;

    @RequestMapping
    public String index(@CurrentUser User currentUser, Model model, RedirectAttributes redirectAttributes) {
        Integer userId = currentUser.getId();
        UserVo userVo = userService.getUserById(userId);
        UserAutoInvestVo autoInvestVo = userMetaService.convertToBean(SignType.AUTO_INVESTMENT_SIGN, userId, UserAutoInvestVo.class);
        if (!userVo.hasPayAccount()) {
            redirectAttributes.addFlashAttribute("flashMessage", "请开通托管账户");
            return "redirect:/uc/security";
        }
        if (!setting.getAutoInvestmentSetting().getAutoInvestmentSign()) {
            redirectAttributes.addFlashAttribute("flashMessage", "自动投标签约功能未开放");
            return "redirect:/uc/security";
        }
        CpcnPayAccountInfo accountInfo = payUtils.getCpcnPayAccountInfo(userId);
        if (StringUtils.isBlank(accountInfo.getInvestAgreementNo())) {
            redirectAttributes.addFlashAttribute("flashMessage", "请先自动投标签约");
            return "redirect:/uc/security";
        }
        if (null == autoInvestVo.getAutoInvestSign() || !autoInvestVo.getAutoInvestSign()) {
            return "uc/auto_investment/setting";
        }
        UserAutoInvestmentRank rank = userAutoInvestmentRankService.findByUserId(userId);
        model.addAttribute("autoInvest", autoInvestVo);
        model.addAttribute("rank", rank);
        return "uc/auto_investment/index";
    }

    @RequestMapping("setting")
    public String setting(@CurrentUser User currentUser, Model model, RedirectAttributes redirectAttributes) {
        Integer userId = currentUser.getId();
        UserVo userVo = userService.getUserById(userId);
        UserAutoInvestVo autoInvestVo = userMetaService.convertToBean(SignType.AUTO_INVESTMENT_SIGN, userId, UserAutoInvestVo.class);
        if (!userVo.hasPayAccount()) {
            redirectAttributes.addFlashAttribute("flashMessage", "请开通托管账户");
            return "redirect:/uc/security";
        }
        if (!setting.getAutoInvestmentSetting().getAutoInvestmentSign()) {
            redirectAttributes.addFlashAttribute("flashMessage", "自动投标签约功能未开放");
            return "redirect:/uc/security";
        }
        UserAutoInvestmentRank rank = userAutoInvestmentRankService.findByUserId(userId);
        model.addAttribute("autoInvest", autoInvestVo);
        model.addAttribute("rank", rank);
        return "uc/auto_investment/setting";
    }

    /**
     * 自动投标/还款签约
     */
    @RequestMapping(value = "auto_sign", method = RequestMethod.POST)
    @ResponseBody
    public Result autoSign(@CurrentUser User user, HttpServletRequest req) {
        Map<String, Object> requestParamMap = WebUtils.getRequestParamMap(req);
        String json = JsonUtils.toJson(requestParamMap);
        try {
            UserAutoInvestVo autoSign = JsonUtils.toObject(json, UserAutoInvestVo.class);
            autoSign.setUserId(user.getId());
            autoInvestmentService.createOrUpdateSign(autoSign);
            return Result.success(SUCCESS_MSG);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }


    @RequestMapping(value = "setting/{status}", method = RequestMethod.POST)
    public String autoInvest(@CurrentUser User currentUser, @PathVariable Boolean status, RedirectAttributes redirectAttributes) {
        Integer userId = currentUser.getId();
        userAutoInvestmentRankService.updateOpenStatus(userId, status);
        redirectAttributes.addFlashAttribute("flashMessage", status ? "自动投标已开启" : "自动投标已关闭");
        return "redirect:/uc/autoInvestment";
    }

    @RequestMapping("record")
    public String record(@CurrentUser User currentUser) {
        return "uc/auto_investment/record";
    }

    @RequestMapping("data")
    @ResponseBody
    public PageResult<InvestmentRecordSimpleVo> investmentRecords(@CurrentUser User currentUser, Date startDate, Date endDate, PageCriteria criteria) {
        Map<String, Object> map = new HashedMap();
        map.put("investor", currentUser.getId());
        map.put("operationMethod", OperationMethod.AUTO.name());
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        return investmentRecordService.findPage(criteria, map, InvestmentState.PAID, InvestmentState.SUCCESS);
    }

    @RequestMapping("rankInfos")
    @ResponseBody
    public Result getRankInfos(@CurrentUser User currentUser) {
        Integer userId = currentUser.getId();
        UserAutoInvestmentRank rank = userAutoInvestmentRankService.findByUserId(userId);

        Map map = new HashMap();
        Integer hasSign = userAutoInvestmentRankService.findHasSign();
        Integer effectiveSign = userAutoInvestmentRankService.findEffectiveSign();
        Integer userRank = userAutoInvestmentRankService.getUserRank(userId);
        map.put("hasSign", hasSign);
        map.put("effectiveSign", effectiveSign);
        if (null != rank) {
            map.put("userRank", userRank);
        } else {
            map.put("userRank", null);
        }
        return Result.success(SUCCESS_MSG, map);
    }

    @RequestMapping("agreement")
    public String agreement(@CurrentUser User currentUser, Model model) {
        UserVo user = userService.getUserById(currentUser.getId());
        model.addAttribute("user", user);
        return "agreement/auto_invest_sign_agreement";
    }
}
