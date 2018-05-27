/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.SpringObjectFactory;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.common.RepaymentCom;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.*;
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
import com.klzan.p2p.vo.business.Request;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户中心借款人还款
 * @author: chenxinglin
 */
@Controller("ucRepaymentController")
@RequestMapping("uc/repayment")
public class RepaymentController{

    private BorrowingProgress[] progresses = {BorrowingProgress.REPAYING, BorrowingProgress.COMPLETED};

    @Inject
    private RepaymentService repaymentService;

    @Inject
    private RepaymentPlanService repaymentPlanService;

    @Inject
    private BorrowingService borrowingService;

    @Inject
    private AccountantService accountantService;

    @Inject
    private DataConvertService dataConvertService;

    @Inject
    private UserService userService;

    @Inject
    private UserFinanceService userFinanceService;

    @Inject
    private BusinessService businessService;
    @Inject
    private CpcnSettlementService cpcnSettlementService;
    @Inject
    private RepaymentCom repaymentCom;
    @Inject
    private PayUtils payUtils;

    /**
     * 还款计划列表
     * @param model
     * @return
     */
    @RequestMapping(value="/plan/{projectId}")
    public String listRepaymentPlan(@CurrentUser User currentUser, @PathVariable Integer projectId, ModelMap model,RedirectAttributes redirectAttributes) {
        //页面数据
        List<Repayment> repayments = accountantService.calOverdue(repaymentService.findList(projectId));
        if(repayments!=null && repayments.size()>0){
            Repayment repayment = repayments.get(0);
            if(!repayment.getBorrower().equals(currentUser.getId())){
                redirectAttributes.addFlashAttribute("flashMessage", "拒绝访问");
                return "redirect:/uc/borrowing";
            }
            Borrowing borrowing = borrowingService.get(repayment.getBorrowing());
            model.addAttribute("borrowing", borrowing);
        }
        Integer period = 0;
        for (Repayment repayment : repayments){
            if((period>repayment.getPeriod() || period==0) && repayment.getState().equals(RepaymentState.REPAYING)){
                period = repayment.getPeriod();
            }
        }
        model.addAttribute("repayments", repayments);
        model.addAttribute("now", new Date());
        model.addAttribute("", SpringObjectFactory.getActiveProfile());
        model.addAttribute("period", period);
        return "uc/repayment/repayment_plan";
    }

//    /**
//     * 还款
//     * @param repaymentId
//     * @return
//     */
//    @RequestMapping(value="/repayment/{repaymentId}",method = RequestMethod.GET)
//    public String repayment(@PathVariable Integer repaymentId,ModelMap model, RedirectAttributes redirectAttributes) {
//
//        //校验
//        if(repaymentId == null){
//            redirectAttributes.addFlashAttribute("flashMessage", "还款ID不存在");
//            return "redirect:/uc/borrowing";
//        }
//
//        // 验证还款
//        Repayment repayment = accountantService.calOverdue(repaymentService.get(repaymentId));
//        if(repayment == null){
//            redirectAttributes.addFlashAttribute("flashMessage", "还款不存在");
//            return "redirect:/uc/borrowing";
//        }
//        if(!repayment.getState().equals(RepaymentState.REPAYING)){
//            redirectAttributes.addFlashAttribute("flashMessage", "该期已还款");
//            return "redirect:/uc/borrowing";
//        }
//
//        // 验证上一期还款计划
//        if(!repaymentService.repaidLastRepayment(repaymentId)){
//            redirectAttributes.addFlashAttribute("flashMessage", "上一期未还");
//            return "redirect:/uc/borrowing";
//        }
//
//        // 借款人验证
//        User user = userService.get(repayment.getBorrower());
//        if(user == null){
//            redirectAttributes.addFlashAttribute("flashMessage", "借款人不存在");
//            return "redirect:/uc/borrowing";
//        }
//        // 借款人余额校验
//        UserFinance userFinance = userFinanceService.findByUserId(repayment.getBorrower());
//        repayment.setAdvance(false);
//        if(userFinance == null || repayment.getRepaymentAmount().compareTo(userFinance.getAvailable())>0){
//            redirectAttributes.addFlashAttribute("flashMessage", "账户余额不足");
//            return "redirect:/uc/borrowing";
//        }
//
//        try{
//            //调接口
//            Request backRequest = businessService.repaymentFrozen(userService.get(repayment.getBorrower()), repayment, PaymentOrderType.REPAYMENT_FROZEN);
//            model.addAttribute("requestUrl", backRequest.getRequestUrl());
//            model.addAttribute("parameterMap", backRequest.getParameterMap());
//            return "payment/submit";
//        }catch (Exception e){
//            redirectAttributes.addFlashAttribute("flashMessage", e.getMessage()==null?"还款失败":e.getMessage());
//            return "redirect:/uc/borrowing";
//        }
//    }

    /**
     * 还款
     * @param repaymentId
     * @return
     */
    @RequestMapping(value="/repayment/{repaymentId}",method = RequestMethod.POST)
    @ResponseBody
    public Result repayment_(@PathVariable Integer repaymentId, ModelMap model, RedirectAttributes redirectAttributes) {

        //校验
        if(repaymentId == null){
            return Result.error("还款ID不存在");
        }

        // 验证还款
        Repayment repayment = accountantService.calOverdue(repaymentService.get(repaymentId));
        if(repayment == null){
            return Result.error("还款ID不存在");
        }
        if(!repayment.getState().equals(RepaymentState.REPAYING)){
            return Result.error("该期已还款");
        }

        // 验证上一期还款计划
        if(!repaymentService.repaidLastRepayment(repaymentId)){
            return Result.error("上一期未还");
        }

        // 借款人验证
        User user = userService.get(repayment.getBorrower());
        if(user == null){
            return Result.error("借款人不存在");
        }
        // 借款人余额校验
        UserFinance userFinance = userFinanceService.findByUserId(repayment.getBorrower());
        repayment.setAdvance(false);
        if(userFinance == null || repayment.getRepaymentAmount().compareTo(userFinance.getAvailable())>0){
            return Result.error("账户余额不足");
        }

        CpcnPayAccountInfo accountInfo = payUtils.getCpcnPayAccountInfo(repayment.getBorrower());
        if(accountInfo == null || StringUtils.isBlank(accountInfo.getChargeAgreementNo())){
            return Result.error("未签约自动还款");
        }

        try{
            Result result = repaymentCom.repayCarry(repaymentService.repayment(repayment, RepaymentOperator.BORROWER));
            return result;
        }catch (Exception e){
            return Result.error("还款失败");
        }
    }

    /**
     * 提前还款
     * @param borrowingId
     * @return
     */
    @RequestMapping(value="/prepayment_view/{borrowingId}",method = RequestMethod.GET)
    public String prepayment_view(@PathVariable Integer borrowingId, ModelMap model, RedirectAttributes redirectAttributes) {

        //校验
        if(borrowingId == null){
            redirectAttributes.addFlashAttribute("flashMessage", "参数错误");
            return "redirect:/uc/borrowing";
        }

        // 验证还款
        Borrowing borrowing = borrowingService.get(borrowingId);
        if(borrowing == null){
            redirectAttributes.addFlashAttribute("flashMessage", "还款不存在");
            return "redirect:/uc/borrowing";
        }
        if(!borrowing.getProgress().equals(BorrowingProgress.REPAYING)){
            redirectAttributes.addFlashAttribute("flashMessage", "该项目已还款");
            return "redirect:/uc/borrowing";
        }

        // 借款人验证
        User user = userService.get(borrowing.getBorrower());
        if (user == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "借款人不存在");
            return "redirect:/uc/borrowing";
        }

        //身份校验
        User currentUser = UserUtils.getCurrentUser();
        if(currentUser.getId()==null || !borrowing.getBorrower().equals(currentUser.getId())){
            redirectAttributes.addFlashAttribute("flashMessage", "拒绝访问");
            return "redirect:/uc/borrowing";
        }

        BigDecimal repaymentTotalAmount = BigDecimal.ZERO;   // 借款人还款金额
        List<Repayment> repayments = repaymentService.findList(borrowingId);
        List<Repayment> repayingRepayments = new ArrayList<>();
        for (Repayment repayment : repayments) {
            if(repayment.getState().equals(RepaymentState.REPAID)){
                continue;
            }
            if (repayment.getIsOverdue()) {
                redirectAttributes.addFlashAttribute("flashMessage", "存在逾期未还,不能提前还款");
                return "redirect:/uc/borrowing";
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
        model.addAttribute("preRepaymentAmount", repaymentTotalAmount);
        model.addAttribute("repayingRepayments", repayingRepayments);
        model.addAttribute("borrowing", borrowing);
        return "uc/repayment/prepayment";
    }

//    /**
//     * 提前还款
//     * @param borrowingId
//     * @return
//     */
//    @RequestMapping(value="/prepayment/{borrowingId}",method = RequestMethod.GET)
//    public String prepayment(@PathVariable Integer borrowingId, ModelMap model, RedirectAttributes redirectAttributes) {
//
//        //校验
//        if(borrowingId == null){
//            redirectAttributes.addFlashAttribute("flashMessage", "参数错误");
//            return "redirect:/uc/borrowing";
//        }
//
//        // 验证还款
//        Borrowing borrowing = borrowingService.get(borrowingId);
//        if(borrowing == null){
//            redirectAttributes.addFlashAttribute("flashMessage", "还款不存在");
//            return "redirect:/uc/borrowing";
//        }
//        if(!borrowing.getProgress().equals(BorrowingProgress.REPAYING)){
//            redirectAttributes.addFlashAttribute("flashMessage", "该项目已还款");
//            return "redirect:/uc/borrowing";
//        }
//
//        // 借款人验证
//        User user = userService.get(borrowing.getBorrower());
//        if (user == null) {
//            redirectAttributes.addFlashAttribute("flashMessage", "借款人不存在");
//            return "redirect:/uc/borrowing";
//        }
//
//        //身份校验
//        User currentUser = UserUtils.getCurrentUser();
//        if(currentUser.getId()==null || !borrowing.getBorrower().equals(currentUser.getId())){
//            redirectAttributes.addFlashAttribute("flashMessage", "拒绝访问");
//            return "redirect:/uc/borrowing";
//        }
//
//        BigDecimal repayAmount = BigDecimal.ZERO;
//        List<Repayment> repayments = repaymentService.findList(borrowingId);
//        for (Repayment repayment : repayments) {
//            if(repayment.getState().equals(RepaymentState.REPAID)){
//                continue;
//            }
//            repayment.setAdvance(true);
//            repayAmount = repayAmount.add(repayment.getRepaymentAmount());
//            if (repayment.getIsOverdue()) {
//                redirectAttributes.addFlashAttribute("flashMessage", "存在逾期未还,不能提前还款");
//                return "redirect:/uc/borrowing";
//            }
//        }
//        try{
//            //调接口
//            Request backRequest = businessService.preRepaymentFrozen(currentUser, borrowing, PaymentOrderType.REPAYMENT_EARLY_FROZEN);
//            model.addAttribute("requestUrl", backRequest.getRequestUrl());
//            model.addAttribute("parameterMap", backRequest.getParameterMap());
//            return "payment/submit";
//        }catch (Exception e){
//            e.printStackTrace();
//            redirectAttributes.addFlashAttribute("flashMessage", "还款失败");
//            return "redirect:/uc/borrowing";
//        }
//    }

    /**
     * 提前还款
     * @param borrowingId
     * @return
     */
    @RequestMapping(value="/prepayment/{borrowingId}",method = RequestMethod.POST)
    @ResponseBody
    public Result prepayment_(@PathVariable Integer borrowingId, ModelMap model, RedirectAttributes redirectAttributes) {

        //校验
        if(borrowingId == null){
            return Result.error("参数错误");
        }

        // 验证还款
        Borrowing borrowing = borrowingService.get(borrowingId);
        if(borrowing == null){
            return Result.error("还款不存在");
        }
        if(!borrowing.getProgress().equals(BorrowingProgress.REPAYING)){
            return Result.error("该项目已还款");
        }

        // 借款人验证
        User user = userService.get(borrowing.getBorrower());
        if (user == null) {
            return Result.error("借款人不存在");
        }

        //身份校验
        User currentUser = UserUtils.getCurrentUser();
        if(currentUser.getId()==null || !borrowing.getBorrower().equals(currentUser.getId())){
            return Result.error("拒绝访问");
        }

        BigDecimal repayAmount = BigDecimal.ZERO;
        List<Repayment> repayments = repaymentService.findList(borrowingId);
        for (Repayment repayment : repayments) {
            if(repayment.getState().equals(RepaymentState.REPAID)){
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
        if(userFinance == null || repaymentService.aheadRepaymentAmount(borrowingId).compareTo(userFinance.getAvailable())>0){
            return Result.error("账户余额不足");
        }

        CpcnPayAccountInfo accountInfo = payUtils.getCpcnPayAccountInfo(borrowing.getBorrower());
        if(accountInfo == null || StringUtils.isBlank(accountInfo.getChargeAgreementNo())){
            return Result.error("未签约自动还款");
        }

        try{
            Result result = repaymentCom.repayCarry(repaymentService.repaymentEarly(borrowing, RepaymentOperator.BORROWER));
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("还款失败");
        }
    }


//    public synchronized Result repayCarry(CpcnSettlement cpcnSettlement) {
//
//        if(cpcnSettlement == null || cpcnSettlement.getId() == null){
//            throw new RuntimeException();
//        }
//
//        cpcnSettlementService.flush();
//        cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());
//
//        Response response = null;
//
//        if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT) || cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT_EARLY)){
//
//            if(cpcnSettlement.getrStatus().equals(CpcnRepaymentStatus.unpaid)){
//                if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT)){
//                    response = repaymentService.repay(cpcnSettlement);
//                }else {
//                    response = repaymentService.aheadRepay(cpcnSettlement);
//                }
//                if(response.isError()){
//                    return Result.error("还款失败："+response.getMsg());
//                }
//            }
//
//            cpcnSettlementService.flush();
//            cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());
//            if(cpcnSettlement.getrStatus().equals(CpcnRepaymentStatus.paying)){
//                String sn = cpcnSettlement.getrOrderNo();
//                PayModule payModule = PayPortal.repayment_query.getModuleInstance();
//                SnRequest snRequest = new SnRequest();
//                snRequest.setSn(sn);
//                payModule.setRequest(snRequest);
//                response = payModule.invoking().getResponse();
//                if(response.isError()){
//                    return Result.error("还款处理中，还款查询结果："+response.getMsg());
//                }
//                if(response.isProccessing()){
//                    return Result.proccessing("还款处理中，还款查询结果："+response.getMsg());
//                }
//            }
//
//            cpcnSettlementService.flush();
//            cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());
//            if(!cpcnSettlement.getrStatus().equals(CpcnRepaymentStatus.success)){
//                return Result.error();
//            }
//
//            if(cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.unsettled) || cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.failure)){
//                if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT)){
//                    response = repaymentService.repaySettlement(cpcnSettlement);
//                }else {
//                    response = repaymentService.aheadRepaySettlement(cpcnSettlement);
//                }
//                if(response.isError()){
////                    return Result.error("还款结算失败："+response.getMsg());
//                    System.out.println(JsonUtils.toJson(Result.error("还款结算失败："+response.getMsg())));
//                    return Result.success("还款成功");
//                }
//                if(response.isSuccess()){
////                    return Result.error("还款结算异常："+response.getMsg());
//                    System.out.println(JsonUtils.toJson(Result.error("还款结算异常："+response.getMsg())));
//                    return Result.success("还款成功");
//                }
//            }
//
//            cpcnSettlementService.flush();
//            cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());
//            if(cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.settling)){
//                String sn = cpcnSettlement.getsOrderNo();
//                PayModule payModule = PayPortal.project_settlement_batch_query.getModuleInstance();
//                SnRequest snRequest = new SnRequest();
//                snRequest.setSn(sn);
//                payModule.setRequest(snRequest);
//                response = payModule.invoking().getResponse();
//                if(response.isError()){
////                    return Result.error("还款结算处理中，结算查询结果："+response.getMsg());
//                    System.out.println(JsonUtils.toJson(Result.error("还款结算处理中，结算查询结果："+response.getMsg())));
//                    return Result.success("还款成功");
//                }
//                if(response.isProccessing()){
////                    return Result.proccessing("还款结算处理中，结算查询结果："+response.getMsg());
//                    System.out.println(JsonUtils.toJson(Result.proccessing("还款结算处理中，结算查询结果："+response.getMsg())));
//                    return Result.success("还款成功");
//                }
//            }
//
//            cpcnSettlementService.flush();
//            cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());
//            if(cpcnSettlement.getrStatus().equals(CpcnRepaymentStatus.success) && cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.success)){
//                return Result.success("还款成功");
//            }
//        }
//
//        return Result.error();
//
//
//    }

}
