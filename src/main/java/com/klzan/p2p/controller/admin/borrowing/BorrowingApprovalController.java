/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.borrowing;

import com.klzan.core.Result;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.Agreement;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.service.agreement.AgreementService;
import com.klzan.p2p.service.borrowing.BorrowingExtraService;
import com.klzan.p2p.service.borrowing.BorrowingFieldRemarkService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.material.MaterialService;
import com.klzan.p2p.vo.borrowing.BorrowingVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 借款审批
 *
 * @author: chenxinglin
 */
@Controller
@RequestMapping("admin/borrowing")
public class BorrowingApprovalController extends BaseAdminController {

    private final static BorrowingType[] types = {BorrowingType.CREDIT, BorrowingType.GUARANTEE, BorrowingType.MORTGAGE};

    private final static RepaymentMethod[] repaymentMethods = {RepaymentMethod.EACH_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL, RepaymentMethod.EACH_PERIOD_AVG_CAPITAL_PLUS_INTEREST, RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST};
    private final static RepaymentMethod[] repaymentMethodsAsDay = {RepaymentMethod.FIXED_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL, RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST};
    private final static InterestMethod[] interestMethods = InterestMethod.values();
    private final static InterestMethod[] interestMethods_ZERO = {InterestMethod.T_PLUS_ZERO};
    private final static InterestMethod[] interestMethods_ONE = {InterestMethod.T_PLUS_ONE, InterestMethod.T_PLUS_ONE_B, InterestMethod.T_PLUS_ZERO, InterestMethod.T_PLUS_ZERO_B};

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private BorrowingExtraService borrowingExtraService;

    @Autowired
    private MaterialService materialService;
    @Resource
    private AgreementService agreementService;
    @Autowired
    private BorrowingFieldRemarkService borrowingFieldRemarkService;

    @Override
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, null);
    }

    /**
     * 审批
     *
     * @param model
     * @return
     */
    @RequiresPermissions("borrowing:approval")
    @RequestMapping(value = "approval/{id}", method = RequestMethod.GET)
    public String approvalView(@PathVariable("id") Integer id, Model model) {

        //操作
        model.addAttribute("action", "approval");

        //协议
        List<Agreement> agreements = agreementService.findAll();
        List<Agreement> investmentAgreements = new ArrayList<>();
        List<Agreement> transferAgreements = new ArrayList<>();
        List<Agreement> borrowingTransferAgreements = new ArrayList<>();
        for (Agreement agreement:agreements){
            if (agreement.getType()==AgreementType.INVESTMENT){
                investmentAgreements.add(agreement);
            }
            if(agreement.getType()==AgreementType.TRANSFER){
                transferAgreements.add(agreement);
            }
            if (agreement.getType()==AgreementType.INVESTMENT_TRANSFER){
                borrowingTransferAgreements.add(agreement);
            }
        }
        model.addAttribute("agreements",investmentAgreements);
        model.addAttribute("transferAgreements",transferAgreements);
        model.addAttribute("borrowingTransferAgreements",borrowingTransferAgreements);

        //借款
        Borrowing borrowing = borrowingService.get(id);
        model.addAttribute("borrowing", borrowing);
        model.addAttribute("materials", materialService.findList(id));
        model.addAttribute("extras", borrowingExtraService.findByBorrowing(id));

        //页面数据
        model.addAttribute("types", types);
        model.addAttribute("periodUnits", PeriodUnit.values());//期限单位
        model.addAttribute("guaranteeMethods", GuaranteeMethod.values());
        model.addAttribute("creditRatings", CreditRating.values());//信用评级
        model.addAttribute("investmentMethods", InvestmentMethod.values());//投资方式
        if (borrowing.getPeriodUnit() == PeriodUnit.DAY) {
            model.addAttribute("interestMethods", interestMethods_ONE);//计息方式
            model.addAttribute("repaymentMethods", repaymentMethodsAsDay);
        } else {
            model.addAttribute("interestMethods", interestMethods_ZERO);//计息方式
            model.addAttribute("repaymentMethods", repaymentMethods);
        }
        model.addAttribute("lendingTimes", LendingTime.values());
        model.addAttribute("repaymentFeeMethods", RepaymentFeeMethod.values());
        model.addAttribute("materialTypes", MaterialType.values());
        return template("borrowing/approval");
    }

    /**
     * 审批
     *
     * @param vo      借款Vo
     * @param state   状态
     * @param opinion 意见
     * @return
     */
    @RequiresPermissions("borrowing:approval")
    @RequestMapping(value = "approval", method = RequestMethod.POST)
    @ResponseBody
    public Result approvalData(BorrowingVo vo, BorrowingCheckState state, String opinion, HttpServletRequest request) {

        if (vo == null || vo.getId() == null) {
            logger.error("借款审批失败：借款ID不存在");
            return Result.error("借款ID不存在");
        }

        //借款
        Borrowing borrowing = borrowingService.get(vo.getId());

        if (borrowing == null) {
            logger.error("借款审批失败：借款不存在");
            return Result.error("借款不存在");
        }

        try {
            borrowingService.approval(vo, borrowing, state, opinion);
            borrowingFieldRemarkService.createOrUpdateRemarks(vo.getId(), WebUtils.getRequestParamMap(request));
            return Result.success();
        } catch (Exception e) {
            logger.error("借款审批失败", e);
            return Result.error(e.getMessage());
        }
    }

}
