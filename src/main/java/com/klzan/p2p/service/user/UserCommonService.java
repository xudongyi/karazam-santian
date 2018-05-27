package com.klzan.p2p.service.user;

import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.withdraw.WithdrawService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.user.UserVo;
import com.klzan.plugin.pay.common.PayUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suhao Date: 2017/1/20 Time: 19:05
 *
 * @version:
 */
@Service
public class UserCommonService {
    @Inject
    private WithdrawService withdrawService;
    @Inject
    private InvestmentRecordService investmentRecordService;
    @Inject
    private UserService userService;
    @Inject
    private UserFinanceService userFinanceService;
    @Inject
    private RepaymentPlanService repaymentPlanService;
    @Inject
    private SettingUtils setting;
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

    public Map<String, Object> getUserAssets(Integer userId) {
        UserFinance userFinance = userFinanceService.findByUserId(userId);
        BigDecimal withdrawing = BigDecimal.ZERO;
        List<WithdrawRecord> withdrawings = withdrawService.findWithdrawing(userId);
        for (WithdrawRecord record : withdrawings) {
            withdrawing = withdrawing.add(record.getTotalAmount());
        }
        BigDecimal investFrozen = BigDecimal.ZERO;
        List<InvestmentRecord> investmentRecords = investmentRecordService.findListByUserId(userId);
        for (InvestmentRecord investmentRecord : investmentRecords) {
            if (investmentRecord.getState()== InvestmentState.PAID){
                investFrozen = investFrozen.add(investmentRecord.getAmount());
            }
        }
        BigDecimal watingCapital = BigDecimal.ZERO;
        BigDecimal watingProfits = BigDecimal.ZERO;
        List<RepaymentPlan> waitingRepaymentPlans = repaymentPlanService.waitingProfit(userId);
        for (RepaymentPlan repaymentPlan : waitingRepaymentPlans) {
            Borrowing borrowing = borrowingService.get(repaymentPlan.getBorrowing());
            if (borrowing.getProgress() == BorrowingProgress.REPAYING) {
                watingCapital = watingCapital.add(repaymentPlan.getRepaymentRecord().getCapital());
                watingProfits = watingProfits.add(repaymentPlan.getRepaymentRecord().getInterest()).add(repaymentPlan.getOverdueInterest()).add(repaymentPlan.getSeriousOverdueInterest());
            }
        }
        BigDecimal alreadyProfits = BigDecimal.ZERO;
        List<RepaymentPlan> alreadyEepaymentPlans = repaymentPlanService.alreadyProfit(userId);
        for (RepaymentPlan repaymentPlan : alreadyEepaymentPlans) {
            alreadyProfits = alreadyProfits.add(repaymentPlan.getPayedProfits());
        }
        BigDecimal alreadyReferralFees = BigDecimal.ZERO;
        List<ReferralFee> referralFees = referralFeeService.alreadySettlement(userId);
        for (ReferralFee referralFee : referralFees) {
            if (referralFee.getState()== ReferralFeeState.PAID){
                alreadyReferralFees = alreadyReferralFees.add(referralFee.getReferralFee());
            }
        }
        BigDecimal withdrawFee = BigDecimal.ZERO;
        List<WithdrawRecord> withdraweds = withdrawService.findByUser(userId, RecordStatus.SUCCESS);
        for (WithdrawRecord withdrawed : withdraweds) {
            withdrawFee = withdrawFee.add(withdrawed.getAllFee());
        }
        Map<String, Object> map = new HashedMap();
        DecimalFormat format = new DecimalFormat("0.00");
        // 可用余额
        map.put("available", format.format(userFinance.getAvailable()));
        // 提现中的金额
        map.put("withdrawing", format.format(withdrawing));
        // 投资冻结金额
        map.put("investFrozen", format.format(investFrozen));
        // 待收本金
        map.put("watingCapital", format.format(watingCapital));
        // 待收收益
        map.put("watingProfits", format.format(watingProfits));
        // 已收收益
        map.put("alreadyProfits", format.format(alreadyProfits.add(alreadyReferralFees)));
        // 已收推荐费
        map.put("alreadyReferralFees", format.format(alreadyReferralFees));
        // 提现手续费
        map.put("withdrawFee", format.format(withdrawFee));
        map.put("allCapitalSum", format.format(userFinance.getAvailable().add(withdrawing).add(investFrozen).add(watingCapital).add(watingProfits)));
        map.put("alreadyProfitsSum", format.format(alreadyProfits.add(alreadyReferralFees)));
        return map;
    }

    public Map<String, Object> accountDetail(Integer userId) {
        User user = userService.get(userId);
        String avatar = user.getAvatar();
        String name = user.getName();
        String loginName = user.getLoginName();
        Map<String, Object> map = new HashMap<>();
        UserFinance userFinance = userFinanceService.getByUserId(user.getId());
        BigDecimal yesterdayfee = BigDecimal.ZERO;//昨天应该收利息
        BigDecimal yesterdayReal = BigDecimal.ZERO;//昨天实际收利息

        Date yesterdayMaxDay = DateUtils.getMaxDateOfDay(DateUtils.addDays(new Date(), -1));
        Date yesterdayMinDay = DateUtils.getMinDateOfDay(DateUtils.addDays(new Date(), -1));
        // List< RepaymentPlan > repaymentPlans = repaymentPlanService.alreadyProfit(user.getId());
        //查询昨天之前未还款的还款计划
        List<RepaymentPlan> yesterdayfeeRepaymentPlan = repaymentPlanService.countYesterdayWaitingProfit(user.getId());
        for (RepaymentPlan repaymentPlan : yesterdayfeeRepaymentPlan) {
            //排除昨天之前未生成的还款计划
            Investment investment = investmentService.get(repaymentPlan.getInvestment());
            if (DateUtils.addDays(DateUtils.getMinDateOfDay(new Date()), -1).getTime() > investment.getCreateDate().getTime()) {
                //计算昨天实际还款的利息
                if (repaymentPlan.getState() == RepaymentState.REPAID
                        && repaymentPlan.getPaidDate().getTime() > yesterdayMinDay.getTime()
                        && repaymentPlan.getPaidDate().getTime() < yesterdayMaxDay.getTime()) {
                    yesterdayReal = yesterdayReal.add(repaymentPlan.getRecoveryAmount().subtract(repaymentPlan.getCapital()).subtract(repaymentPlan.getTransferFeeIn()));
                    //计算昨天应该收到的利息
                } else {
                    Borrowing borrowing = borrowingService.get(repaymentPlan.getBorrowing());
                    BigDecimal periodAmount = BigDecimal.ZERO;
                    Date createDate = new Date(0);
                    Date payDate = repaymentPlan.getRepaymentRecordPayDate();
                    if (repaymentPlan.getRepaymentRecordPeriod() == 1) {
                        createDate = repaymentPlan.getCreateDate();
                    } else {
                        for (RepaymentPlan plan : repaymentPlanService.findList(borrowing.getId())) {
                            if (plan.getRepaymentRecordPeriod() == (repaymentPlan.getRepaymentRecordPeriod() - 1)) {
                                createDate = plan.getRepaymentRecordPayDate();
                            }
                        }
                    }
                    Integer days = new Double(DateUtils.getDaysOfTwoDate(payDate, createDate)).intValue();
                    if (days < borrowing.getPeriod()) {
                        days++;
                    }
                    periodAmount = periodAmount.add(repaymentPlan.getPaidSeriousOverdueInterest()
                            .add(repaymentPlan.getRepaymentRecordInterest())
                            .add(repaymentPlan.getOverdueInterest())
                            .subtract(repaymentPlan.getRecoveryRecoveryFee()));
                    yesterdayfee = yesterdayfee.add(periodAmount.divide(new BigDecimal(days), 2, BigDecimal.ROUND_HALF_UP));
                }
            }
        }
        //推荐费
        List<ReferralFee> referralFees = referralFeeService.alreadySettlement(user.getId());
        if (referralFees != null && referralFees.size() != 0) {
            for (ReferralFee referralFee : referralFees) {
                if (referralFee.getPaymentDate().getTime() > yesterdayMinDay.getTime() && referralFee.getPaymentDate().getTime() < yesterdayMaxDay.getTime())
                    yesterdayfee = yesterdayfee.add(referralFee.getReferralFee());
            }
        }
        map.put("inviteCode", user.getInviteCode());
        map.put("yesterdayfee", yesterdayfee.add(yesterdayReal));
        //资产总额
        map.put("balance", userFinance.getBalance());
        String assets = this.getUserAssets(user.getId()).get("allCapitalSum").toString();
        map.put("allCapitalSum", assets);
        //可用余额
        map.put("available", userFinance.getAvailable());
        map.put("avatar", setting.getDfsUrl() + avatar);
        map.put("name", name);
        map.put("loginName", loginName);
        map.put("gesPassword", user.getGesPassword());
        UserInfo userInfo = userInfoService.getUserInfo(user.getId());
        UserVo userVo = userService.getUserById(user.getId());
        map.put("realName", userInfo.getRealName());
        map.put("idNo", userInfo.getIdNo());
        map.put("userType", userVo.getType());
        // 是否设置手势密码
        map.put("isSetGesPassword", StringUtils.isBlank(user.getGesPassword()) ? false : true);
        // 是否实名认证
        map.put("isAuthIdNo", StringUtils.isBlank(userInfo.getIdNo()) ? false : true);
        // 是否托管开户
        map.put("isOpenEscrow", userVo.hasPayAccount() ? true : false);
        // 是否绑卡
        List<CpcnBankCard> userBankCards = payUtils.getUserBankCards(user.getId());
        map.put("isBindCard", userBankCards.isEmpty() ? false : true);

        // 支付账户信息
        CpcnPayAccountInfo accountInfo = payUtils.getCpcnPayAccountInfo(userId);
        map.put("hasChargeAgreementNo", (null != accountInfo && StringUtils.isNotBlank(accountInfo.getChargeAgreementNo())) ? true : false);
        map.put("chargeAgreementNo", (null != accountInfo && StringUtils.isNotBlank(accountInfo.getChargeAgreementNo())) ? accountInfo.getChargeAgreementNo() : "");

        return map;
    }
}
