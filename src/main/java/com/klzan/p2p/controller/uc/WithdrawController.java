package com.klzan.p2p.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.common.service.RSAService;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserFinance;
import com.klzan.p2p.model.WithdrawRecord;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserInfoService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.service.withdraw.WithdrawService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.setting.WithdrawSetting;
import com.klzan.p2p.vo.business.Request;
import com.klzan.p2p.vo.withdraw.WithdrawalFeeVo;
import com.klzan.p2p.vo.withdraw.WithdrawalVo;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.common.PageRequest;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.RechargeRequest;
import com.klzan.plugin.pay.common.dto.WithdrawRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import com.klzan.plugin.pay.ips.comquery.vo.IpsPayCommonQueryResponse;
import com.klzan.plugin.pay.ips.comquery.vo.UserInfoResponse;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.interfaces.RSAPublicKey;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suhao Date: 2016/12/14 Time: 20:06
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("uc/withdraw")
public class WithdrawController extends BaseController {
    @Inject
    private UserFinanceService userFinanceService;
    @Inject
    private WithdrawService withdrawService;
    @Inject
    private RSAService rsaService;
    @Inject
    private UserService userService;
    @Inject
    private UserInfoService userInfoService;
    @Inject
    private SettingUtils settingUtils;
    @Inject
    private DataConvertService dataConvertService;
    @Inject
    private BusinessService businessService;
    @Inject
    private Environment environment;

    @RequestMapping(method = RequestMethod.GET)
    public String index(@CurrentUser User user, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (!userInfoService.hasCertification(user.getId())) {
            redirectAttributes.addFlashAttribute("flashMessage", "请实名认证");
            return "redirect:/uc/security";
        }
//        if (StringUtils.isBlank(user.getIpsAccountNo())) {
//            redirectAttributes.addFlashAttribute("flashMessage", "请开通托管账户");
//            return "redirect:/uc/security";
//        }
        WithdrawSetting setting = settingUtils.getWithdrawSetting();
        if (!setting.getEnable()) {
            redirectAttributes.addFlashAttribute("flashMessage", "平台暂停提现功能，如需了解详情，请通过服务热线咨询。");
            return "redirect:/uc/security";
        }
//        IDetailResponse query = businessService.query("01", user.getIpsAccountNo());
//        try {
//            IpsPayCommonQueryResponse commonResponse = (IpsPayCommonQueryResponse)query;
//            UserInfoResponse userInfo = commonResponse.getUserInfo();
//            if (StringUtils.isBlank(userInfo.getBankCard())) {
//                model.addAttribute("hasWithdrawCard", false);
//            } else {
//                model.addAttribute("hasWithdrawCard", true);
//            }
//        } catch (Exception e) {
//            model.addAttribute("hasWithdrawCard", false);
//        }
        model.addAttribute("hasWithdrawCard", true);
        // 生成密钥
        RSAPublicKey publicKey = rsaService.generateKey(request);
        UserFinance userFinance = userFinanceService.findByUserId(user.getId());
        model.addAttribute("beginAmount", setting.getBeginAmount());
        model.addAttribute("userFinance", userFinance);
        model.addAttribute("modules", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        model.addAttribute("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
        return "uc/withdraw/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String withdraw(@CurrentUser User user, WithdrawalVo withdrawalVo, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        withdrawalVo.setUserId(user.getId());
        WithdrawSetting withdrawSetting = settingUtils.getWithdrawSetting();
        if (withdrawalVo.getAmount().compareTo(withdrawSetting.getBeginAmount()) < 0) {
            redirectAttributes.addFlashAttribute("flashMessage", String.format("提现金额必须大于%s元", withdrawSetting.getBeginAmount().toString()));
            return "redirect:/uc/withdraw";
        }
        PayModule payModule = PayPortal.withdraw.getModuleInstance();
        WithdrawRequest req = new WithdrawRequest(false, UserUtils.getCurrentUser().getId(), withdrawalVo.getAmount());
        payModule.setRequest(req);
        PageRequest param = payModule.invoking().getPageRequest();
        model.addAttribute("requestUrl", param.getRequestUrl());
        model.addAttribute("parameterMap", param.getParameterMap());
        return "payment/submit";
    }

    /**
     * 验证余额
     */
    @RequestMapping(value = "check_balance", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkBalance(BigDecimal amount) {
        UserFinance userFinance = userFinanceService.findByUserId(UserUtils.getCurrentUser().getId());
        return userFinance.getAvailable().compareTo(amount) > -1;
    }

    /**
     * 计算服务费
     */
    @RequestMapping(value = "/calculate_fee", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> calculateFee(BigDecimal amount) {
        Map<String, Object> data = new HashMap<>();
        DecimalFormat format = new DecimalFormat(",###.##");
        WithdrawalFeeVo withdrawalFeeVo = withdrawService.calculateFee(UserUtils.getCurrentUser().getId(), amount);
        data.put("fee", withdrawalFeeVo.getFee());
        data.put("amount", format.format(withdrawalFeeVo.getArrivalAmount()));
        return data;
    }

    @RequestMapping(value = "json")
    @ResponseBody
    public PageResult<WithdrawRecord> json(@CurrentUser User user, PageCriteria criteria, RecordStatus status, HttpServletRequest request) {
        return withdrawService.findPageByUser(user.getId(), buildQueryCriteria(criteria, request), status);
    }

    @RequestMapping(value = "count")
    @ResponseBody
    public Result count(@CurrentUser User user) {
        List<WithdrawRecord> list = withdrawService.findByUser(user.getId());
        BigDecimal allAmount = BigDecimal.ZERO;
        BigDecimal successAmount = BigDecimal.ZERO;
        for (WithdrawRecord record : list) {
            allAmount = allAmount.add(record.getAmount());
            if (record.getStatus() == RecordStatus.SUCCESS) {
                successAmount = successAmount.add(record.getAmount());
            }
        }
        Map map = new HashMap();
        map.put("allAmount", allAmount);
        map.put("successAmount", successAmount);
        return Result.success("成功", map);
    }

}
