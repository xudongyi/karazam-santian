package com.klzan.p2p.service.user.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.user.UserFinanceDao;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.user.ReferralFeeService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.service.withdraw.WithdrawService;
import com.klzan.p2p.vo.capital.UserFinanceVo;
import com.klzan.p2p.vo.user.UserVo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suhao Date: 2017/4/5 Time: 14:17
 *
 * @version: 1.0
 */
@Service
public class UserFinanceServiceImpl extends BaseService<UserFinance> implements UserFinanceService {
    @Inject
    private WithdrawService withdrawService;
    @Inject
    private InvestmentService investmentService;
    @Inject
    private RepaymentPlanService repaymentPlanService;
    @Inject
    private ReferralFeeService referralFeeService;
    @Inject
    private UserFinanceDao userFinanceDao;
    @Inject
    private UserService userService;
    @Inject
    private BorrowingService borrowingService;

    @Override
    public UserFinanceVo findUserFinanceByUserId(Integer userId) {
        UserVo userVo = userService.getUserById(userId);
        UserFinanceVo financeVo = userFinanceDao.getUserFinance(userId);
        financeVo.setUserName(userVo.getName());
        financeVo.setRealName(userVo.getRealName());
        financeVo.setIdNo(userVo.getIdNo());
        financeVo.setMobile(userVo.getMobile());
        financeVo.setRegistDate(new Date());
        return financeVo;
    }

    @Override
    public PageResult<UserFinanceVo> findUserFinancePage(PageCriteria criteria) {
        PageResult<UserFinanceVo> userFinanceVos = userFinanceDao.findPage(criteria);
        for (UserFinanceVo financeVo : userFinanceVos.getRows()) {
            List<Borrowing> borrowingList = borrowingService.findByBorrowerId(financeVo.getUserId());
            if (borrowingList.isEmpty()) {
                financeVo.setIsBorrower(false);
            } else {
                financeVo.setIsBorrower(true);
            }
        }
        return userFinanceVos;
    }

    @Override
    public UserFinance findByUserId(Integer userId) {
        return userFinanceDao.getByUserId(userId);
    }

    @Override
    public Map<String, Object> getAssetsByUser(Integer userId) {
        Map<String, Object> map = new HashedMap();
        UserFinance userFinance = findByUserId(userId);
        BigDecimal withdrawing = BigDecimal.ZERO;
        List<WithdrawRecord> withdrawings = withdrawService.findWithdrawing(userId);
        for (WithdrawRecord record : withdrawings) {
            withdrawing = withdrawing.add(record.getAmount());
        }
        BigDecimal investFrozen = BigDecimal.ZERO;
        List<Investment> investments = investmentService.findInvestingByUserId(userId);
        for (Investment investment : investments) {
            investFrozen = investFrozen.add(investment.getAmount());
        }
        BigDecimal watingCapital = BigDecimal.ZERO;
        BigDecimal watingProfits = BigDecimal.ZERO;
        List<RepaymentPlan> waitingRepaymentPlans = repaymentPlanService.waitingProfit(userId);
        for (RepaymentPlan repaymentPlan : waitingRepaymentPlans) {
            watingCapital = watingCapital.add(repaymentPlan.getRepaymentRecord().getCapital());
            watingProfits = watingProfits.add(repaymentPlan.getRepaymentRecord().getInterest()).add(repaymentPlan.getOverdueInterest()).add(repaymentPlan.getSeriousOverdueInterest());
        }
        BigDecimal alreadyProfits = BigDecimal.ZERO;
        List<RepaymentPlan> alreadyEepaymentPlans = repaymentPlanService.alreadyProfit(userId);
        for (RepaymentPlan repaymentPlan : alreadyEepaymentPlans) {
            alreadyProfits = alreadyProfits.add(repaymentPlan.getRepaymentRecord().getInterest()).add(repaymentPlan.getOverdueInterest()).add(repaymentPlan.getSeriousOverdueInterest());
        }
        BigDecimal alreadyReferralFees = BigDecimal.ZERO;
        List<ReferralFee> referralFees = referralFeeService.alreadySettlement(userId);
        for (ReferralFee referralFee : referralFees) {
            alreadyReferralFees = alreadyReferralFees.add(referralFee.getReferralFee());
        }
        BigDecimal withdrawFee = BigDecimal.ZERO;
        List<WithdrawRecord> withdraweds = withdrawService.findByUser(userId, RecordStatus.SUCCESS);
        for (WithdrawRecord withdrawed : withdraweds) {
            withdrawFee = withdrawFee.add(withdrawed.getFee());
        }
        Map<String, Object> assets = new HashedMap();
        // 可用余额
        assets.put("available", userFinance.getAvailable());
        // 提现中的金额
        assets.put("withdrawing", withdrawing);
        // 投资冻结金额
        assets.put("investFrozen", investFrozen);
        // 待收本金
        assets.put("watingCapital", watingCapital);
        // 待收收益
        assets.put("watingProfits", watingProfits);
        // 已收收益
        assets.put("alreadyProfits", alreadyProfits);
        // 已收推荐费
        assets.put("alreadyReferralFees", alreadyReferralFees);
        // 提现手续费
        assets.put("withdrawFee", withdrawFee);
        assets.put("allCapitalSum", userFinance.getAvailable().add(withdrawing).add(investFrozen).add(watingCapital).add(watingProfits));
        assets.put("alreadyProfitsSum", alreadyProfits.add(alreadyReferralFees));
        map.put("assets", assets);
        return map;
    }


    @Override
    public List<UserFinanceVo> findAllUserFund(String mobile, String startCreateDate, String endCreateDate) {
        Map map = new HashMap();
        if(StringUtils.isNotBlank(mobile)){
            map.put("mobile",mobile);
        }
        if(StringUtils.isNotBlank(startCreateDate)){
            map.put("startCreateDate",startCreateDate);
        }
        if(StringUtils.isNotBlank(endCreateDate)){
            map.put("endCreateDate",endCreateDate);
        }
        return myDaoSupport.findList("com.klzan.p2p.mapper.CapitalMapper.userFund",map);
    }

    @Override
    public UserFinance getByUserId(Integer userId) {
        return userFinanceDao.getByUserId(userId);
    }
}
