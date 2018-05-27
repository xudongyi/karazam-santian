package com.klzan.p2p.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.PaymentOrderMethod;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.Bank;
import com.klzan.p2p.model.RechargeRecord;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserFinance;
import com.klzan.p2p.service.bank.BankService;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.recharge.RechargeService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.RechargeSetting;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.business.Request;
import com.klzan.p2p.vo.recharge.RechargeVo;
import com.klzan.p2p.vo.user.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suhao Date: 2016/12/7 Time: 14:21
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("uc/recharge")
public class RechargeController extends BaseController {

    @Autowired
    private UserFinanceService userFinanceService;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankService bankService;

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private SettingUtils settingUtils;

    @RequestMapping(method = RequestMethod.GET)
    public String index(@CurrentUser User user, Model model, RedirectAttributes redirectAttributes) {
        UserVo userVo = userService.getUserById(user.getId());
        if (StringUtils.isBlank(userVo.getIdNo())) {
            redirectAttributes.addFlashAttribute("flashMessage", "请实名认证");
            return "redirect:/uc/security";
        }
        if (StringUtils.isBlank(userVo.getPayAccountNo())) {
            redirectAttributes.addFlashAttribute("flashMessage", "请开通托管账户");
            return "redirect:/uc/security";
        }
        RechargeSetting setting = settingUtils.getRechargeSetting();
        if (!setting.getEnable()) {
            redirectAttributes.addFlashAttribute("flashMessage", "系统繁忙，请稍后使用充值功能");
            return "redirect:/uc/security";
        }

        UserFinance userFinance = userFinanceService.findByUserId(user.getId());
        List<Bank> banks = bankService.findList();

        model.addAttribute("banks", banks);
        model.addAttribute("userFinance", userFinance);
        model.addAttribute("user", user);
        model.addAttribute("repaymentRechargeEnable", false);
        model.addAttribute("hasIdCard", true);
        return "uc/recharge/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Result recharge(@CurrentUser User user, RechargeVo rechargeVo, Model model, RedirectAttributes redirectAttributes) {
        try {
            //充值金额大于十万时增加判断
            Map map = new HashMap();
//            String bankCardStatus = "";
//            String idCardStatus = "";
//            if (new BigDecimal(10000).compareTo(rechargeVo.getAmount())<=0){
//                try {
//                    IDetailResponse query = businessService.query("01", user.getIpsAccountNo());
//                    if (query == null) {
//                        map.put("bankCardStatus","未绑定银行卡");
//                        map.put("idCardStatus","需先绑定银行卡");
//                        return Result.error("接口查询出错",map);
//                    }
//                    IpsPayCommonQueryResponse commonResponse = (IpsPayCommonQueryResponse)query;
//                    UserInfoResponse userInfo = commonResponse.getUserInfo();
//                    map.put("userInfo",userInfo);
//                    if (StringUtils.isBlank(userInfo.getuCardStatus())) {
//                        map.put("bankCardStatus","已绑定");
//                        map.put("idCardStatus","未上传身份证");
//                        return Result.error("未上传身份证",map);
//                    } else {
//                        if ("0".equals(userInfo.getuCardStatus())){
//                            map.put("bankCardStatus","已绑定");
//                            map.put("idCardStatus","未上传身份证");
//                            return Result.error("未上传身份证",map);
//                        }else if ("1".equals(userInfo.getuCardStatus())){
//                            map.put("bankCardStatus","已绑定");
//                            map.put("idCardStatus","审核成功");
//                        }else if ("2".equals(userInfo.getuCardStatus())){
//                            map.put("bankCardStatus","已绑定");
//                            map.put("idCardStatus","审核拒绝");
//                            return Result.error("审核拒绝",map);
//                        }else if ("3".equals(userInfo.getuCardStatus())){
//                            map.put("bankCardStatus","已绑定");
//                            map.put("idCardStatus","已上传,审核中");
//                            return Result.error("已上传,审核中",map);
//                        }else if ("4".equals(userInfo.getuCardStatus())){
//                            map.put("bankCardStatus","已绑定");
//                            map.put("idCardStatus","已上传,未推送审核");
//                            return Result.error("已上传,未推送审核",map);
//                        }else {
//                            map.put("bankCardStatus","未知");
//                            map.put("idCardStatus","未知");
//                        }
//                    }
//                } catch (Exception e) {
//                    map.put("bankCardStatus","环迅接口异常,请稍后再试...");
//                    map.put("idCardStatus","环迅接口异常,请稍后再试...");
//                    return Result.error("查询异常",map);
//                }
//            }
            rechargeVo.setUserId(user.getId());
            rechargeVo.setPaymentMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
            Request recharge = businessService.recharge(user, rechargeVo);
            model.addAttribute("requestUrl", recharge.getRequestUrl());
            model.addAttribute("parameterMap", recharge.getParameterMap());
            map.put("requestUrl", recharge.getRequestUrl());
            map.put("parameterMap", recharge.getParameterMap());
            return Result.success("成功",map);
        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("flashMessage", e.getMessage());
            return Result.error("失败");
        }
    }

    @RequestMapping(value = "json")
    @ResponseBody
    public PageResult<RechargeRecord> json(@CurrentUser User user, PageCriteria criteria, RecordStatus status, HttpServletRequest request) {
        return rechargeService.findPageByUser(user.getId(), buildQueryCriteria(criteria, request), status);
    }

    @RequestMapping(value = "count")
    @ResponseBody
    public Result count(@CurrentUser User user) {
        List<RechargeRecord> list = rechargeService.findByUser(user.getId());
        BigDecimal allAmount = BigDecimal.ZERO;
        BigDecimal successAmount = BigDecimal.ZERO;
        for (RechargeRecord record : list) {
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
