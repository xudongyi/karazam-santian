/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.controller.uc;
import com.klzan.core.Result;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.model.RepaymentPlan;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的还款明细
 * Created by chenxinglin Date: 2016-12-12
 */
@Controller("MyRepaymentController")
@RequestMapping("/uc/myrepayment")
public class MyRepaymentController {

    @Inject
    private BorrowingService borrowingService;

    @Inject
    private RepaymentService repaymentService;

    @Inject
    private RepaymentPlanService repaymentPlanService;


    @RequestMapping(value = "/{borrowingId}")
    public String repayment(@CurrentUser User currentUser, Model model, @PathVariable Integer borrowingId, RedirectAttributes redirectAttributes) {
        List<Repayment> repayments = repaymentService.findList(borrowingId);
        if(repayments!=null && repayments.size()>0){
            Repayment repayment = repayments.get(0);
            if(!repayment.getBorrower().equals(currentUser.getId())){
                redirectAttributes.addFlashAttribute("flashMessage", "拒绝访问");
                return "redirect:/uc/borrowing";
            }

            Borrowing borrowing = borrowingService.get(repayment.getBorrowing());
            model.addAttribute("borrowing", borrowing);
        }
        model.addAttribute("repayments", repayments);
        return "uc/myrepayment/repayments";
    }

    @RequestMapping(value = "/plans/{investmentId}")
    @ResponseBody
    public Result plans(@CurrentUser User currentUser, Model model, @PathVariable Integer investmentId, RedirectAttributes redirectAttributes) {
        List<RepaymentPlan> repaymentPlans = repaymentPlanService.findListByInvestment(investmentId);
        Map map = new HashMap<>();
        if(repaymentPlans!=null && repaymentPlans.size()>0){
            RepaymentPlan repaymentPlan = repaymentPlans.get(0);
            if(!repaymentPlan.getInvestor().equals(currentUser.getId())){
                return Result.error("拒绝访问");
            }
            Borrowing borrowing = borrowingService.get(repaymentPlans.get(0).getBorrowing());
            map.put("borrowing", borrowing);
        }
        map.put("repaymentPlans", repaymentPlans);
        return Result.success("成功",map);
    }
}
