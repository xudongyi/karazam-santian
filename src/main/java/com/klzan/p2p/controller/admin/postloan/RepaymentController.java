/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.postloan;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.RepaymentCom;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.enums.RepaymentOperator;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.capital.AccountantService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.plugin.pay.common.PayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 贷后 还款
 */
@Controller("adminRepaymentController")
@RequestMapping("admin/postloan/repayment")
public class RepaymentController extends BaseAdminController {

    private BorrowingProgress[] progresses = {BorrowingProgress.REPAYING, BorrowingProgress.COMPLETED};

    @Inject
    private UserService userService;

    @Inject
    private UserFinanceService userFinanceService;

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
    private RepaymentCom repaymentCom;
    @Inject
    private PayUtils payUtils;

    /**
     * 借款列表
     *
     * @param model
     * @return
     */
    @RequiresPermissions("postloan:repayment")
    @RequestMapping(method = RequestMethod.GET)
    public String list(ModelMap model) {
        //页面数据
        model.addAttribute("progresses", progresses);
        return template("repayment/list");
    }

    /**
     * 借款列表json
     *
     * @param criteria
     * @return request
     */
    @RequiresPermissions("postloan:repayment")
    @RequestMapping(value = "list.json", method = RequestMethod.GET)
    @ResponseBody
    public PageResult<Borrowing> listData(PageCriteria criteria, HttpServletRequest request, BorrowingProgress progress) {
        PageResult<Borrowing> page = borrowingService.findBorrowingList(buildQueryCriteria(criteria, request), progress, Boolean.FALSE);
//        for(Borrowing borrowing : page.getRows()){
//            Repayment repayment = repaymentService.getCurrentRepayment(borrowing.getId());
//            if(repayment != null){
//                borrowing.setModifyDate(repayment.getPayDate());
//                borrowing.setIp(repayment.getPeriod()+"/"+borrowing.getPeriod());
//            }else{
//                borrowing.setModifyDate(null);
//                borrowing.setIp("已完成");
//            }
//        }
        dataConvertService.convertBorrowings(page.getRows());
        if (progress.equals(BorrowingProgress.REPAYING)) {
            for (int i = 0; i < page.getRows().size(); i++) {
                for (int j = i + 1; j < page.getRows().size(); j++) {
                    try {
                        Borrowing bI = page.getRows().get(i);
                        Borrowing bJ = page.getRows().get(j);
                        if (bI.getNextPayDate().compareTo(bJ.getNextPayDate()) > 0) {
                            Borrowing temp = bI;
                            page.getRows().set(i, bJ);
                            page.getRows().set(j, temp);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return page;
    }

    /**
     * 还款列表
     *
     * @param model
     * @return
     */
    @RequiresPermissions("postloan:repayment")
    @RequestMapping(value = "repayment_list", method = RequestMethod.GET)
    public String listRepayment(Integer borrowingId, ModelMap model) {
        //页面数据
        model.addAttribute("progresses", progresses);
        model.addAttribute("borrowingId", borrowingId);

        return template("repayment/repayment_list");
    }

    /**
     * 还款列表json
     *
     * @param borrowingId
     * @return
     */
    @RequiresPermissions("postloan:repayment")
    @RequestMapping(value = "repayment_list.json", method = RequestMethod.GET)
    @ResponseBody
    public List<Repayment> listRepaymentData(Integer borrowingId) {
        List<Repayment> repayments = repaymentService.findList(borrowingId);
        accountantService.calOverdue(repayments); //逾期计算
        dataConvertService.convertRepayments(repayments); //数据转换
        return repayments;
    }

    /**
     * 还款计划列表
     *
     * @param model
     * @return
     */
    @RequiresPermissions("postloan:repayment")
    @RequestMapping(value = "repaymentPlan_list", method = RequestMethod.GET)
    public String listRepaymentPlan(Integer borrowingId, ModelMap model) {
        //页面数据
        model.addAttribute("progresses", progresses);
        model.addAttribute("borrowingId", borrowingId);

        return template("repayment/repaymentPlan_list");
    }

    /**
     * 还款计划列表json
     *
     * @param borrowingId
     * @return
     */
    @RequiresPermissions("postloan:repayment")
    @RequestMapping(value = "repaymentPlan_list.json", method = RequestMethod.GET)
    @ResponseBody
    public List<RepaymentPlan> listRepaymentPlanData(Integer borrowingId) {
        return dataConvertService.convertRepaymentPlans(repaymentPlanService.findList(borrowingId));
    }


    /**
     * 还款
     * @param repaymentId
     * @return
     */
    @RequestMapping(value="/repayment",method = RequestMethod.POST)
    @ResponseBody
    public Result repayment_(Integer repaymentId, Boolean isInstead, ModelMap model, RedirectAttributes redirectAttributes) {

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
        if (!BooleanUtils.isTrue(isInstead)){
            UserFinance userFinance = userFinanceService.findByUserId(repayment.getBorrower());
            repayment.setAdvance(false);
            if(userFinance == null || repayment.getRepaymentAmount().compareTo(userFinance.getAvailable())>0){
                return Result.error("账户余额不足");
            }
        }

        CpcnPayAccountInfo accountInfo = payUtils.getCpcnPayAccountInfo(repayment.getBorrower());
        if(accountInfo == null || StringUtils.isBlank(accountInfo.getChargeAgreementNo())){
            return Result.error("未签约自动还款");
        }

        try{
            RepaymentOperator ro = isInstead?RepaymentOperator.ADMIN_INSTEAD:RepaymentOperator.ADMIN_AGENT;
            Result result = repaymentCom.repayCarry(repaymentService.repayment(repayment, ro));
            return result;
        }catch (Exception e){
            return Result.error("还款失败");
        }
    }


    /**
     * 提前还款列表
     *
     * @param model
     * @return
     */
    @RequiresPermissions("postloan:repayment")
    @RequestMapping(value = "repayment_ahead_list", method = RequestMethod.GET)
    public String listRepaymentAhead(Integer borrowingId, ModelMap model) {

        //校验
        if (borrowingId == null) {
            logger.error("借款ID不存在");
            return "";
        }

        // 验证还款
        Borrowing borrowing = borrowingService.get(borrowingId);
        if (borrowing == null) {
            logger.error("借款不存在");
            return "";
        }
        BigDecimal aheadRepaymentAmount = BigDecimal.ZERO;
        try {
            aheadRepaymentAmount = repaymentService.preRepaymentAmount(borrowing);
        } catch (Exception e) {
            logger.error("还款金额计算错误");
        }
        model.addAttribute("aheadRepaymentAmount", aheadRepaymentAmount);
        //页面数据
        model.addAttribute("progresses", progresses);
        model.addAttribute("borrowingId", borrowingId);
        return template("repayment/repayment_ahead_list");
    }

    /**
     * 提前还款列表json
     *
     * @param borrowingId
     * @return
     */
    @RequiresPermissions("postloan:repayment")
    @RequestMapping(value = "repayment_ahead_list.json", method = RequestMethod.GET)
    @ResponseBody
    public List<Repayment> listRepaymentAheadData(Integer borrowingId) {
        List<Repayment> repayments = repaymentService.findList(borrowingId);
        accountantService.calAhead(repayments); //提前计算
        dataConvertService.convertRepayments(repayments); //数据转换
        return repayments;
    }

    /**
     * 提前还款
     *
     * @param borrowingId
     * @return
     */
    @RequiresPermissions("postloan:repayment")
    @RequestMapping(value = "aheadRepayment", method = RequestMethod.POST)
    @ResponseBody
    public Result aheadRepayment(Integer borrowingId, Boolean isInstead) {
        //校验
        if (borrowingId == null) {
            logger.error("借款ID不存在");
            return Result.error("借款ID不存在");
        }

        // 验证还款
        Borrowing borrowing = borrowingService.get(borrowingId);
        if (borrowing == null) {
            logger.error("借款不存在");
            return Result.error("借款不存在");
        }
        if (!borrowing.getProgress().equals(BorrowingProgress.REPAYING)) {
            logger.error("借款状态错误");
            return Result.error("借款状态错误");
        }

        BigDecimal repayAmount = BigDecimal.ZERO;
        List<Repayment> repayments = repaymentService.findList(borrowingId);
        for (Repayment repayment : repayments) {
            repayment.setAdvance(true);
            repayAmount = repayAmount.add(repayment.getRepaymentAmount());

            if (repayment.getIsOverdue()) {
                logger.error("存在逾期未还,不能提前还款");
                return Result.error("存在逾期未还,不能提前还款");
            }
        }

        // 借款人验证
        User user = userService.get(borrowing.getBorrower());
        if (user == null) {
            logger.error("借款人不存在");
            return Result.error("借款人不存在");
        }

        // 借款人余额校验
        if (!BooleanUtils.isTrue(isInstead)){
            UserFinance userFinance = userFinanceService.get(borrowing.getBorrower());
            if (userFinance == null || repayAmount.compareTo(userFinance.getAvailable()) > 0) {
                logger.error("借款人余额校验不足");
                return Result.error("借款人余额校验不足");
            }
        }

        try {
//            repaymentService.preRepayment(borrowing);
            RepaymentOperator ro = isInstead?RepaymentOperator.ADMIN_INSTEAD:RepaymentOperator.ADMIN_AGENT;
            Result result = repaymentCom.repayCarry(repaymentService.repaymentEarly(borrowing, ro));
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage() == null ? "还款失败" : e.getMessage(), e);
            return Result.error(e.getMessage() == null ? "还款失败" : e.getMessage());
        }

    }

    /**
     * 提前还款校验
     *
     * @param borrowingId
     * @return
     */
    @RequiresPermissions("postloan:repayment")
    @RequestMapping(value = "aheadRepaymentCheck", method = RequestMethod.POST)
    @ResponseBody
    public Result aheadRepaymentCheck(Integer borrowingId) {

        //校验
        if (borrowingId == null) {
            logger.error("借款ID不存在");
            return Result.error("借款ID不存在");
        }

        // 验证还款
        Borrowing borrowing = borrowingService.get(borrowingId);
        if (borrowing == null) {
            logger.error("借款不存在");
            return Result.error("借款不存在");
        }
        if (!borrowing.getProgress().equals(BorrowingProgress.REPAYING)) {
            logger.error("借款状态错误");
            return Result.error("借款状态错误");
        }

        BigDecimal repayAmount = BigDecimal.ZERO;
        List<Repayment> repayments = repaymentService.findList(borrowingId);
        for (Repayment repayment : repayments) {
            repayment.setAdvance(true);
            repayAmount = repayAmount.add(repayment.getRepaymentAmount());
            if (repayment.getIsOverdue()) {
                logger.error("存在逾期未还,不能提前还款");
                return Result.error("存在逾期未还,不能提前还款");
            }
        }

        // 借款人验证
        User user = userService.get(borrowing.getBorrower());
        if (user == null) {
            logger.error("借款人不存在");
            return Result.error("借款人不存在");
        }

//        // 借款人余额校验
//        UserFinance userFinance = huaShanUserService.getUserFinance(borrowing.getBorrower());
//        if(userFinance == null || repayAmount.compareTo(userFinance.getAvailable())>0){
//            logger.error("借款人余额校验不足");
//            return Result.error("借款人余额校验不足");
//        }
//
//        BigDecimal aheadRepaymentAmount = BigDecimal.ZERO;
//        try {
//            aheadRepaymentAmount = repaymentService.aheadRepaymentAmount(borrowing);
//        }catch (Exception e){
//            logger.error("还款金额计算错误");
//            return Result.error("还款金额计算错误");
//        }
//        Map map = new HashMap();
//        map.put("aheadRepaymentAmount", aheadRepaymentAmount);

        return Result.success("成功");

    }
//
//    /**
//     * 还款列表
//     *
//     * @param model
//     * @return
//     */
//    @RequiresPermissions("postloan:repayment")
//    @RequestMapping(value = "repayments_list", method = RequestMethod.GET)
//    public String listRepayments(Date payDate, Boolean overdue, ModelMap model) {
//        //页面数据
//        model.addAttribute("progresses", progresses);
//        model.addAttribute("payDate", DateUtils.format(payDate));
//        model.addAttribute("overdue", overdue == null ? false : overdue);
//
//        return template("repayment/repayments_list");
//    }
//
//    /**
//     * 还款列表json
//     *
//     * @return
//     */
//    @RequiresPermissions("postloan:repayment")
//    @RequestMapping(value = "repayments_list.json", method = RequestMethod.GET)
//    @ResponseBody
//    public PageResult<Repayment> listRepaymentsData(PageCriteria pageCriteria, Date payDate, Boolean overdue) {
//        PageResult<Repayment> page = repaymentService.findPage(pageCriteria, RepaymentState.REPAYING, payDate, overdue);
//        accountantService.calOverdue(page.getRows()); //逾期计算
//        dataConvertService.convertRepayments(page.getRows()); //数据转换
//        return page;
//    }
//

}
