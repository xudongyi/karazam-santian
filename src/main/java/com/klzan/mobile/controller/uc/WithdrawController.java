package com.klzan.mobile.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
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
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
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
 *提现
 * @version: 1.0
 */
@Controller("mobileUCwithdrawController")
@RequestMapping("/mobile/uc/withdraw")
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

//    @RequestMapping(value = "index",method = RequestMethod.GET)
//    public Result index(@CurrentUser User user,  HttpServletRequest request) {
//        if (!userInfoService.hasCertification(user.getId())) {
//          return Result.error("请实名认证");
//        }
//        WithdrawSetting setting = settingUtils.getWithdrawSetting();
//        if (!setting.getEnable()) {
//            return Result.error("系统繁忙，请稍后使用提现功能");
//        }
//        // 生成密钥
//        RSAPublicKey publicKey = rsaService.generateKey(request);
//        UserFinance userFinance = userFinanceService.findByUserId(user.getId());
//        Map<String,Object> map= new HashMap<>();
//        map.put("beginAmount", setting.getBeginAmount());
//        map.put("userFinance", userFinance);
//        map.put("modules", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
//        map.put("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
//        return Result.success("success",map);
//    }
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Result withdraw(@CurrentUser User user,@RequestBody WithdrawalVo withdrawalVo) {
        withdrawalVo.setUserId(user.getId());
        WithdrawSetting withdrawSetting = settingUtils.getWithdrawSetting();
        if (withdrawalVo.getAmount().compareTo(withdrawSetting.getBeginAmount()) < 0) {
         return Result.error(String.format("提现金额必须大于%s元", withdrawSetting.getBeginAmount().toString()));
        }
        WithdrawRecord record = withdrawService.addRecord(withdrawalVo);
        Request withdraw = businessService.withdraw(user, record);
        return Result.success("success",withdraw.getParameterMap());
    }

    /**
     * 验证余额
     */
    @RequestMapping(value = "/check_balance", method = RequestMethod.POST)
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
    public Result calculateFee(BigDecimal amount) {
        Map<String, Object> data = new HashMap<>();
        DecimalFormat format = new DecimalFormat(",###.##");
        WithdrawalFeeVo withdrawalFeeVo = withdrawService.calculateFee(UserUtils.getCurrentUser().getId(), amount);
        // 总提现费用
        data.put("fee", withdrawalFeeVo.getFee());
        // 到账金额
        data.put("amount", format.format(withdrawalFeeVo.getArrivalAmount()));
        return Result.success("success",data);
    }
}
