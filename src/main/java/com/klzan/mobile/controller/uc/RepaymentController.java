/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.mobile.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.common.RepaymentCom;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.enums.RepaymentOperator;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.capital.AccountantService;
import com.klzan.p2p.service.repayment.CpcnSettlementService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.plugin.pay.common.PayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

@Controller("mobileUcRepaymentController")
@RequestMapping("/mobile/uc/repayment")
public class RepaymentController {
    @Autowired
    private RepaymentService repaymentService;
    @Autowired
    private RepaymentPlanService repaymentPlanService;
    @Autowired
    private BorrowingService borrowingService;
    @Autowired
    private AccountantService accountantService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserFinanceService userFinanceService;
    @Autowired
    private RepaymentCom repaymentCom;
    @Autowired
    private PayUtils payUtils;

    /**
     * 还款计划列表
     */
    @RequestMapping(value = "/plan/{borrowingId}")
    @ResponseBody
    public Result listRepaymentPlan(@CurrentUser User currentUser, @PathVariable Integer borrowingId) {
        //页面数据
        List<Repayment> repayments = accountantService.calOverdue(repaymentService.findList(borrowingId));
        if (repayments != null && repayments.size() > 0) {
            Repayment repayment = repayments.get(0);
            if (!repayment.getBorrower().equals(currentUser.getId())) {
                return Result.error("拒绝访问");
            }
        }
        Integer period = 0;
        for (Repayment repayment : repayments) {
            if ((period > repayment.getPeriod() || period == 0) && repayment.getState().equals(RepaymentState.REPAYING)) {
                period = repayment.getPeriod();
            }
        }
        Map map = new HashMap();
        map.put("repayments", repayments);
        map.put("now", new Date());
        map.put("period", period);
        return Result.success(map);
    }

    /**
     * 还款
     *
     * @param repaymentId
     * @return
     */
    @RequestMapping(value = "/repayment/{repaymentId}", method = RequestMethod.POST)
    @ResponseBody
    public Result repayment(@CurrentUser User currentUser, @PathVariable Integer repaymentId) {

        //校验
        if (repaymentId == null) {
            return Result.error("还款ID不存在");
        }

        // 验证还款
        Repayment repayment = accountantService.calOverdue(repaymentService.get(repaymentId));
        if (repayment == null) {
            return Result.error("还款ID不存在");
        }
        if (!currentUser.getId().equals(repayment.getBorrower())) {
            return Result.error("还款ID不存在");
        }
        if (!repayment.getState().equals(RepaymentState.REPAYING)) {
            return Result.error("该期已还款");
        }

        // 验证上一期还款计划
        if (!repaymentService.repaidLastRepayment(repaymentId)) {
            return Result.error("上一期未还");
        }

        // 借款人验证
        User user = userService.get(repayment.getBorrower());
        if (user == null) {
            return Result.error("借款人不存在");
        }
        // 借款人余额校验
        UserFinance userFinance = userFinanceService.findByUserId(repayment.getBorrower());
        repayment.setAdvance(false);
        if (userFinance == null || repayment.getRepaymentAmount().compareTo(userFinance.getAvailable()) > 0) {
            return Result.error("账户余额不足");
        }

        CpcnPayAccountInfo accountInfo = payUtils.getCpcnPayAccountInfo(repayment.getBorrower());
        if (accountInfo == null || StringUtils.isBlank(accountInfo.getChargeAgreementNo())) {
            return Result.error("未签约自动还款");
        }

        try {
            return repaymentCom.repayCarry(repaymentService.repayment(repayment, RepaymentOperator.BORROWER));
        } catch (Exception e) {
            return Result.error("还款失败");
        }
    }

    /**
     * 提前还款
     *
     * @param borrowingId
     * @return
     */
    @RequestMapping(value = "/prepayment_view/{borrowingId}", method = RequestMethod.GET)
    @ResponseBody
    public Result prepayment_view(@PathVariable Integer borrowingId) {

        //校验
        if (borrowingId == null) {
            return Result.error("参数错误");
        }

        // 验证还款
        Borrowing borrowing = borrowingService.get(borrowingId);
        if (borrowing == null) {
            return Result.error("还款不存在");
        }
        if (!borrowing.getProgress().equals(BorrowingProgress.REPAYING)) {
            return Result.error("该项目已还款");
        }

        // 借款人验证
        User user = userService.get(borrowing.getBorrower());
        if (user == null) {
            return Result.error("借款人不存在");
        }

        //身份校验
        User currentUser = UserUtils.getCurrentUser();
        if (currentUser.getId() == null || !borrowing.getBorrower().equals(currentUser.getId())) {
            return Result.error("拒绝访问");
        }

        BigDecimal repaymentTotalAmount = BigDecimal.ZERO;   // 借款人还款金额
        List<Repayment> repayments = repaymentService.findList(borrowingId);
        List<Repayment> repayingRepayments = new ArrayList<>();
        for (Repayment repayment : repayments) {
            if (repayment.getState().equals(RepaymentState.REPAID)) {
                continue;
            }
            if (repayment.getIsOverdue()) {
                return Result.error("存在逾期未还,不能提前还款");
            }
            // TODO 还款
            repayment = accountantService.calAhead(repayment);
            // TODO 还款计划
            List<RepaymentPlan> repaymentPlans = repaymentPlanService.findListByRepayment(repayment.getId());
            repaymentPlans = accountantService.calAhead(repaymentPlans);
            // TODO 遍历还款计划
            for (RepaymentPlan repaymentPlan : repaymentPlans) {
                repaymentTotalAmount = repaymentTotalAmount.add(repaymentPlan.getCapital().add(repaymentPlan.getAheadInterest()));
            }
            repaymentTotalAmount = repaymentTotalAmount.add(repayment.getRepaymentFee());
            repayingRepayments.add(repayment);
        }
        Map map = new HashMap();
        map.put("preRepaymentAmount", repaymentTotalAmount);
        map.put("repayingRepayments", repayingRepayments);
        map.put("borrowing", borrowing);
        return Result.success(map);
    }

    /**
     * 提前还款
     *
     * @param borrowingId
     * @return
     */
    @RequestMapping(value = "/prepayment/{borrowingId}", method = RequestMethod.POST)
    @ResponseBody
    public Result prepayment_(@PathVariable Integer borrowingId) {

        //校验
        if (borrowingId == null) {
            return Result.error("参数错误");
        }

        // 验证还款
        Borrowing borrowing = borrowingService.get(borrowingId);
        if (borrowing == null) {
            return Result.error("还款不存在");
        }
        if (!borrowing.getProgress().equals(BorrowingProgress.REPAYING)) {
            return Result.error("该项目已还款");
        }

        // 借款人验证
        User user = userService.get(borrowing.getBorrower());
        if (user == null) {
            return Result.error("借款人不存在");
        }

        //身份校验
        User currentUser = UserUtils.getCurrentUser();
        if (currentUser.getId() == null || !borrowing.getBorrower().equals(currentUser.getId())) {
            return Result.error("拒绝访问");
        }

        BigDecimal repayAmount = BigDecimal.ZERO;
        List<Repayment> repayments = repaymentService.findList(borrowingId);
        for (Repayment repayment : repayments) {
            if (repayment.getState().equals(RepaymentState.REPAID)) {
                continue;
            }
            repayment.setAdvance(true);
            repayAmount = repayAmount.add(repayment.getRepaymentAmount());
            if (repayment.getIsOverdue()) {
                return Result.error("存在逾期未还,不能提前还款");
            }
        }

        // 借款人余额校验
        UserFinance userFinance = userFinanceService.findByUserId(borrowing.getBorrower());
        if (userFinance == null || repaymentService.aheadRepaymentAmount(borrowingId).compareTo(userFinance.getAvailable()) > 0) {
            return Result.error("账户余额不足");
        }

        CpcnPayAccountInfo accountInfo = payUtils.getCpcnPayAccountInfo(borrowing.getBorrower());
        if (accountInfo == null || StringUtils.isBlank(accountInfo.getChargeAgreementNo())) {
            return Result.error("未签约自动还款");
        }

        try {
            return repaymentCom.repayCarry(repaymentService.repaymentEarly(borrowing, RepaymentOperator.BORROWER));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("还款失败");
        }
    }

}
