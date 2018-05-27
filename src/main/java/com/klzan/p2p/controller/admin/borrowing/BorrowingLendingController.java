/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.borrowing;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.Agreement;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.CpcnSettlement;
import com.klzan.p2p.service.agreement.AgreementService;
import com.klzan.p2p.service.borrowing.BorrowingContactsService;
import com.klzan.p2p.service.borrowing.BorrowingOpinionService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.material.MaterialService;
import com.klzan.p2p.service.repayment.CpcnSettlementService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.vo.borrowing.BorrowingVo;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * 借款出借
 */
@Controller("adminBorrowingLendingController")
@RequestMapping("admin/borrowing")
public class BorrowingLendingController extends BaseAdminController {

    private final static BorrowingType[] types = {BorrowingType.CREDIT, BorrowingType.GUARANTEE, BorrowingType.MORTGAGE};

    private final static RepaymentMethod[] repaymentMethods = {RepaymentMethod.EACH_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL, RepaymentMethod.EACH_PERIOD_AVG_CAPITAL_PLUS_INTEREST, RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST};
    private final static RepaymentMethod[] repaymentMethodsAsDay = {RepaymentMethod.FIXED_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL, RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST};
    private final static InterestMethod[] interestMethods = InterestMethod.values();
    private final static InterestMethod[] interestMethods_ZERO = {InterestMethod.T_PLUS_ZERO};
    private final static InterestMethod[] interestMethods_ONE = {InterestMethod.T_PLUS_ONE, InterestMethod.T_PLUS_ONE_B, InterestMethod.T_PLUS_ZERO, InterestMethod.T_PLUS_ZERO_B};

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private BorrowingOpinionService bOpinionService;

    @Autowired
    private BorrowingContactsService bContactsService;

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private InvestmentRecordService investmentRecordService;

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private RepaymentPlanService repaymentPlanService;

    @Inject
    private DataConvertService dataConvertService;
    @Resource
    private AgreementService agreementService;
    @Resource
    private CpcnSettlementService cpcnSettlementService;

    /**
     * 出借
     * @param model
     * @return
     */
    @RequiresPermissions("borrowing:lending")
    @RequestMapping(value = "/lending/{id}", method = RequestMethod.GET)
    public String lendingView(@PathVariable("id") Integer id, Model model) {

        //操作
        model.addAttribute("action", "lending");

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

        //借款 借款材料
        Borrowing borrowing = borrowingService.get(id);
        model.addAttribute("borrowing", borrowing);
        model.addAttribute("materials", materialService.findList(id));
        //投资 投资记录
        model.addAttribute("investments", JsonUtils.toJson(dataConvertService.convertInvestments(investmentService.findList(id))));
        model.addAttribute("investmentRecords", JsonUtils.toJson(dataConvertService.convertInvestmentRecords(investmentRecordService.findList(id, null, InvestmentState.PAID, InvestmentState.SUCCESS))));
        //还款 还款记录
        model.addAttribute("repayments", JsonUtils.toJson(dataConvertService.convertRepayments(repaymentService.findList(id))));
        model.addAttribute("repaymentPlans", JsonUtils.toJson(dataConvertService.convertRepaymentPlans(repaymentPlanService.findList(id))));
        //标的联系人
        model.addAttribute("bContacts", JsonUtils.toJson(bContactsService.findList(id)));
        //意见
        model.addAttribute("bOpinions", JsonUtils.toJson(bOpinionService.findList(id)));

        //页面数据
        model.addAttribute("types",types);
        model.addAttribute("periodUnits",PeriodUnit.values());//期限单位
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
        return template("borrowing/lending");
    }

    /**
     * 出借
     * @param vo 借款Vo
     * @param state 状态
     * @param opinion 意见
     */
    @RequiresPermissions("borrowing:lending")
    @RequestMapping(value = "lending", method = RequestMethod.POST)
    @ResponseBody
    public Result lendingData(BorrowingVo vo, BorrowingCheckState state, String opinion) {

        if(vo == null || vo.getId() == null){
            logger.error("借款出借失败：借款ID不存在");
            return Result.error("借款ID不存在");
        }

        //借款
        Borrowing borrowing = borrowingService.get(vo.getId());

        if(borrowing == null){
            logger.error("借款出借失败：借款不存在");
            return Result.error("借款不存在");
        }

        try {
            borrowingService.lendingPay(borrowing, state, opinion, vo.getNoticeBorrower(), vo.getNoticeInvestor());
            return Result.success();
        } catch (Exception e) {
            logger.error("借款出借失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 流标
     */
    @RequiresPermissions("borrowing:lending")
    @RequestMapping(value = "/failureBid/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Result failureBid(@PathVariable("id") Integer id/*, BorrowingVo vo, BorrowingCheckState state, String opinion*/) {

        if(id == null || id == null){
            return Result.error("借款ID不存在");
        }

        Borrowing borrowing = borrowingService.get(id);

        if(borrowing == null){
            return Result.error("借款不存在");
        }

        try {
            CpcnSettlement cpcnSettlement = cpcnSettlementService.findSettlement(PaymentOrderType.REFUND, borrowing.getId());
            if(cpcnSettlement != null && cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.success)){
                return Result.success("已结算成功");
            }

            if(cpcnSettlement == null || cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.failure) || cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.unsettled)){
                Result result = borrowingService.failureBidSettlement(borrowing, "手动流标");
                if(!result.isSuccess()){
                    return Result.success("退款处理中："+result.getMessage(), result.getData());
                }
            }

            Response response = null;
            cpcnSettlement = cpcnSettlementService.findSettlement(PaymentOrderType.REFUND, borrowing.getId());
            if(cpcnSettlement != null && cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.settling)){
                String sn = cpcnSettlement.getsOrderNo();
                PayModule payModule = PayPortal.project_settlement_batch_query.getModuleInstance();
                SnRequest snRequest = new SnRequest();
                snRequest.setSn(sn);
                payModule.setRequest(snRequest);
                response = payModule.invoking().getResponse();
                if(response.isError()){
                    System.out.println(JsonUtils.toJson(Result.error("退款结算查询失败，结算查询结果："+response.getMsg())));
                    return Result.success("退款结算失败", response.getMsg());
                }
                if(response.isProccessing()){
                    System.out.println(JsonUtils.toJson(Result.proccessing("还款结算处理中，结算查询结果："+response.getMsg())));
                    return Result.success("退款结算处理中", response.getMsg());
                }
                if(response.isSuccess()){
                    System.out.println(JsonUtils.toJson(Result.proccessing("还款结算处理中，结算查询结果："+response.getMsg())));
                    return Result.success("退款结算成功", response.getMsg());
                }
            }

            cpcnSettlement = cpcnSettlementService.findSettlement(PaymentOrderType.REFUND, borrowing.getId());
            if(cpcnSettlement != null && cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.success)){
                return Result.success("已结算成功");
            }

            return Result.success("未知结果");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }



}
