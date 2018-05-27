package com.klzan.mobile.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.PaymentOrderMethod;
import com.klzan.p2p.enums.RechargeBusinessType;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.bank.BankService;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.RechargeSetting;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.business.Request;
import com.klzan.p2p.vo.recharge.RechargeVo;
import com.klzan.p2p.vo.user.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12 0012.
 * 充值
 *
 */
@Controller("mobileUCRechargeController")
@RequestMapping("/mobile/uc")
public class RechargeController extends BaseController {

    @Inject
    private BankService bankService;

    @Inject
    private UserService userService;

    @Inject
    private SettingUtils settingUtils;
    @Inject
    private BusinessService businessService;
    @Inject
    private RepaymentService repaymentService;

    @RequestMapping(value = "/confirm",method = RequestMethod.POST)
    @ResponseBody
    public Result recharge(@CurrentUser User user, @RequestBody RechargeVo rechargeVo/*, @RequestBody String mobileType*/) {
        if(user==null){
          return  Result.error("请先登录");
        }
        UserVo userVo = userService.getUserById(user.getId());
        if (StringUtils.isBlank(userVo.getIdNo())) {

            return Result.error("请实名认证") ;
        }
        if (StringUtils.isBlank(userVo.getPayAccountNo())) {

            return Result.error("请开通托管账户");
        }
        RechargeSetting setting = settingUtils.getRechargeSetting();
        if (!setting.getEnable()) {
            return Result.error("系统繁忙，请稍后使用充值功能");
        }
        //------环迅银行卡绑定校验------
//        IDetailResponse query = businessService.query("01", user.getIpsAccountNo());
//        if(rechargeVo.getAmount().compareTo(new BigDecimal(10000))>0){
//            try {
//                IpsPayCommonQueryResponse commonResponse = (IpsPayCommonQueryResponse)query;
//                UserInfoResponse userInfo = commonResponse.getUserInfo();
//                if (StringUtils.isBlank(userInfo.getBankCard())) {
//                    return  Result.error("请先绑定银行卡");
//                }
//                if(userInfo.getuCardStatus().equals("0")){
//                    return Result.error("未上传身份认证");
//                }
//            } catch (Exception e) {
//                return  Result.error(e.getMessage());
//            }
//        }
        Map<String,Object> map=new HashMap<>();
        try {
                rechargeVo.setUserId(user.getId());
                rechargeVo.setPaymentMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
                rechargeVo.setRechargeBusType(RechargeBusinessType.GENERAL);
                Request recharge = businessService.appRecharge(user, rechargeVo);
                map=recharge.getParameterMap();
            }catch (Exception e) {
                return Result.error("error",e.getMessage());
                }
        return Result.success("success",map);
    }
}
