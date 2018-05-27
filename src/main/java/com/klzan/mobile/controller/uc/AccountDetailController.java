package com.klzan.mobile.controller.uc;

import com.jcraft.jsch.SftpException;
import com.klzan.core.Result;
import com.klzan.core.util.Base64;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.SftpUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.mobile.vo.UserAvatarVo;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.user.*;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/17 0017.
 */
@Controller("mobileUCAccountDetailController")
@RequestMapping("/mobile/uc/accountDetail")
public class AccountDetailController {

    @Inject
    private UserService userService;

    @Inject
    private UserFinanceService userFinanceService;

    @Inject
    private RepaymentPlanService repaymentPlanService;

    @Inject
    private SettingUtils setting;

    @Inject
    private UserCommonService userCommonService;
    @Inject
    private UserInfoService userInfoService;
    @Inject
    private ReferralFeeService referralFeeService;
    @Inject
    private BorrowingService borrowingService;
    @Inject
    private InvestmentService investmentService;
    @Inject
    private PayUtils payUtils;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Result index(@CurrentUser User user){
        if (user == null) {
            return Result.error("账号未登录");
        }
//        String avatar = user.getAvatar();
//        String name = user.getName();
//        String loginName = user.getLoginName();
//        Map<String, Object> map = new HashMap<>();
//        UserFinance userFinance = userFinanceService.getByUserId(user.getId());
//        BigDecimal yesterdayfee = BigDecimal.ZERO;//昨天应该收利息
//        BigDecimal yesterdayReal = BigDecimal.ZERO;//昨天实际收利息
//
//        Date yesterdayMaxDay = DateUtils.getMaxDateOfDay(DateUtils.addDays(new Date(), -1));
//        Date yesterdayMinDay = DateUtils.getMinDateOfDay(DateUtils.addDays(new Date(), -1));
//        // List< RepaymentPlan > repaymentPlans = repaymentPlanService.alreadyProfit(user.getId());
//        //查询昨天之前未还款的还款计划
//        List<RepaymentPlan> yesterdayfeeRepaymentPlan = repaymentPlanService.countYesterdayWaitingProfit(user.getId());
//        for (RepaymentPlan repaymentPlan : yesterdayfeeRepaymentPlan) {
//            //排除昨天之前未生成的还款计划
//            Investment investment = investmentService.get(repaymentPlan.getInvestment());
//            if (DateUtils.addDays(DateUtils.getMinDateOfDay(new Date()), -1).getTime() > investment.getCreateDate().getTime()) {
//                //计算昨天实际还款的利息
//                if (repaymentPlan.getState() == RepaymentState.REPAID
//                        && repaymentPlan.getPaidDate().getTime() > yesterdayMinDay.getTime()
//                        && repaymentPlan.getPaidDate().getTime() < yesterdayMaxDay.getTime()) {
//                    yesterdayReal = yesterdayReal.add(repaymentPlan.getRecoveryAmount().subtract(repaymentPlan.getCapital()).subtract(repaymentPlan.getTransferFeeIn()));
//                    //计算昨天应该收到的利息
//                } else {
//                    Borrowing borrowing = borrowingService.get(repaymentPlan.getBorrowing());
//                    BigDecimal periodAmount = BigDecimal.ZERO;
//                    Date createDate = new Date(0);
//                    Date payDate = repaymentPlan.getRepaymentRecordPayDate();
//                    if (repaymentPlan.getRepaymentRecordPeriod() == 1) {
//                        createDate = repaymentPlan.getCreateDate();
//                    } else {
//                        for (RepaymentPlan plan : repaymentPlanService.findList(borrowing.getId())) {
//                            if (plan.getRepaymentRecordPeriod() == (repaymentPlan.getRepaymentRecordPeriod() - 1)) {
//                                createDate = plan.getRepaymentRecordPayDate();
//                            }
//                        }
//                    }
//                    Integer days = new Double(DateUtils.getDaysOfTwoDate(payDate, createDate)).intValue();
//                    if (days < borrowing.getPeriod()) {
//                        days++;
//                    }
//                    periodAmount = periodAmount.add(repaymentPlan.getPaidSeriousOverdueInterest()
//                            .add(repaymentPlan.getRepaymentRecordInterest())
//                            .add(repaymentPlan.getOverdueInterest())
//                            .subtract(repaymentPlan.getRecoveryRecoveryFee()));
//                    yesterdayfee = yesterdayfee.add(periodAmount.divide(new BigDecimal(days), 2, BigDecimal.ROUND_HALF_UP));
//                }
//            }
//        }
//        //推荐费
//        List<ReferralFee> referralFees = referralFeeService.alreadySettlement(user.getId());
//        if (referralFees != null && referralFees.size() != 0) {
//            for (ReferralFee referralFee : referralFees) {
//                if (referralFee.getPaymentDate().getTime() > yesterdayMinDay.getTime() && referralFee.getPaymentDate().getTime() < yesterdayMaxDay.getTime())
//                    yesterdayfee = yesterdayfee.add(referralFee.getReferralFee());
//            }
//        }
//        map.put("inviteCode", user.getInviteCode());
//        map.put("yesterdayfee", yesterdayfee.add(yesterdayReal));
//        //资产总额
//        map.put("balance", userFinance.getBalance());
//        String assets = userCommonService.getUserAssets(user.getId()).get("allCapitalSum").toString();
//        map.put("allCapitalSum", assets);
//        //可用余额
//        map.put("available", userFinance.getAvailable());
//        map.put("avatar", setting.getDfsUrl() + avatar);
//        map.put("name", name);
//        map.put("loginName", loginName);
//        map.put("gesPassword", user.getGesPassword());
//        UserInfo userInfo = userInfoService.getUserInfo(user.getId());
//        UserVo userVo = userService.getUserById(user.getId());
//        map.put("realName", userInfo.getRealName());
//        map.put("idNo", userInfo.getIdNo());
//        map.put("userType", userVo.getType());
//        // 是否设置手势密码
//        map.put("isSetGesPassword", StringUtils.isBlank(user.getGesPassword()) ? false : true);
//        // 是否实名认证
//        map.put("isAuthIdNo", StringUtils.isBlank(userInfo.getIdNo()) ? false : true);
//        // 是否托管开户
//        map.put("isOpenEscrow", userVo.hasPayAccount() ? true : false);
//        // 是否绑卡
//        List<CpcnBankCard> userBankCards = payUtils.getUserBankCards(user.getId());
//        map.put("isBindCard", userBankCards.isEmpty() ? false : true);
        UserInfoRequest userInfoRequest = new UserInfoRequest();
        userInfoRequest.setUserId(user.getId());
        PayModule payModule = PayPortal.bankcard_bind_query.getModuleInstance();
        payModule.setRequest(userInfoRequest);
        payModule.invoking().getResponse();
        return Result.success("success", userCommonService.accountDetail(user.getId()));
    }
    @RequestMapping(value = "/modifyName",method = RequestMethod.POST)
    @ResponseBody
    public Result modifyName(@CurrentUser User user,@RequestBody Map<String,Object> name){
        if(StringUtils.isBlank(name.get("name").toString())){
            return Result.error();
        }

        user.setName(name.get("name").toString());
        userService.merge(user);
        return Result.success();
    }
    //上传图片
    @RequestMapping(value = "/avatar/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result avatarUpload(@CurrentUser User user, @RequestBody UserAvatarVo userAvatarVo){
        if(user==null){
            return Result.error("请先登录");
        }
        long time = DateUtils.getTime();
        String fileName = user.getId() + "_" + time + "_src." + userAvatarVo.getExtension();
        SftpUtils sftp = new SftpUtils();
        String avatarDir = "user";
        try {
            sftp.connect().upload(avatarDir, fileName, Base64.decode(userAvatarVo.getAvatarData())).disconnect();
        } catch (SftpException e) {
            e.printStackTrace();
        }
        finally {
            sftp.disconnect();
        }

        user.setAvatar(avatarDir + "/" + fileName);
        userService.merge(user);

        return Result.success("success",setting.getDfsUrl()+user.getAvatar());
    }

}
