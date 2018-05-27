package com.klzan.mobile.controller;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.FreemarkerUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.agreement.AgreementService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.capital.AccountantService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.material.MaterialService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.util.AccountantUtils;
import com.klzan.p2p.vo.business.Request;
import freemarker.template.TemplateException;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
@Controller("TransferListController")
@RequestMapping("/mobile/transfer/")
public class TransferController {

    @Autowired
    private SettingUtils setting;
    @Inject
    private MaterialService materialService;
    @Inject
    private TransferService transferService;
    @Inject
    private BorrowingService borrowingService;
    @Inject
    private RepaymentPlanService repaymentPlanService;
    @Inject
    private UserService userService;
    @Inject
    private RepaymentService repaymentService;
    @Inject
    private UserFinanceService userFinanceService;
    @Inject
    private BusinessService businessService;
    @Inject
    private InvestmentRecordService investmentRecordService;
    @Inject
    private AccountantService accountantService;
    @Inject
    private AgreementService agreementService;
    @Inject
    private DataConvertService dataConvertService;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseBody
    public Result investments(PageCriteria criteria) {
        Map map = new HashMap();
        List<String> states = new ArrayList<>();
        states.add(TransferLoanState.TRANSFERING.name());
        states.add(TransferLoanState.TRANSFERPART.name());
        states.add(TransferLoanState.TRANSFERED.name());
        map.put("states", states);
        PageResult<Transfer> transferPageResult = transferService.findPage(criteria, map);

        List<Object> listTransfer = new ArrayList<>();
        if (transferPageResult.getRows() != null && transferPageResult.getRows().size() != 0) {
            for (Transfer transfer : transferPageResult.getRows()) {
                Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
                borrowing = dataConvertService.convertBorrowing(borrowing);
                List<String> materialImgUrls = new ArrayList<>();
                List<Material> materials = materialService.findList(borrowing.getId());
                if (materials != null && materials.size() != 0) {
                    for (Material material : materials) {
                        materialImgUrls.add(setting.getDfsUrl() + material.getSource());
                    }
                }
//                transfer = transferSet(transfer, borrowing);
//                transfer.setResidualPeriod(transfer.getResidualPeriod());
                List<InvestmentRecord> investmentRecords = investmentRecordService.findList(borrowing.getId(), true, InvestmentState.PAID, InvestmentState.SUCCESS);
                Map<String, Object> project = new HashedMap();
                project.put("investedNumber", investmentRecords.size() > 0 ? investmentRecords.size() : 0);
                project.put("pictureNumber", materialImgUrls.size() > 0 ? materialImgUrls.size() : 0);
                project.put("repaymentMethodDes", borrowing.getRepaymentMethodDes());
                project.put("PeriodUnitDes", borrowing.getPeriodUnitDes());
                project.put("PeriodUnit", borrowing.getPeriodUnit());
                project.put("investmentMinimum", 100);
                project.put("investmentMaximum", transfer.getSurplusCapital());
                project.put("labels", borrowing.getLabels());

                Map detail = new HashMap();
                detail.put("detail", project);
                detail.put("transfer1", transfer);
                listTransfer.add(detail);
            }
        }
        Map mapCont = new HashMap();
        mapCont.put("pages", transferPageResult.getPages());
        mapCont.put("rows", listTransfer);

        return Result.success("转让列表", mapCont);
    }

    /**
     * 查看详情
     */
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Result view(@PathVariable Integer id) {

        // 验证转让
        Transfer transfer = transferService.get(id);
        if (transfer == null) {
            return Result.error("转让不存在");
        }

        if (transfer.getState() == TransferLoanState.CANCEL) {
            return Result.error("转让已撤销");
        }

        // 验证项目
        Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
        borrowing = dataConvertService.convertBorrowing(borrowing);
        if (borrowing == null) {
            return Result.error("投资项目不存在");
        }
        Map<String, Object> project = new HashedMap();
        List<String> materialImgUrls = new ArrayList<>();
        List<Material> materials = materialService.findList(borrowing.getId());
        if (materials != null && materials.size() != 0) {
            for (Material material : materials) {
                materialImgUrls.add(setting.getDfsUrl() + material.getSource());
            }
        }
        transferSet(transfer, borrowing);
        List<InvestmentRecord> investmentRecords = investmentRecordService.findList(borrowing.getId(), true, InvestmentState.PAID, InvestmentState.SUCCESS);
        project.put("investedNumber", investmentRecords.size() > 0 ? investmentRecords.size() : 0);
        project.put("pictureNumber", materialImgUrls.size() > 0 ? materialImgUrls.size() : 0);
        project.put("transfer", transfer);
        project.put("repaymentMethodDes", borrowing.getRepaymentMethodDes());
        project.put("PeriodUnitDes", borrowing.getPeriodUnitDes());
        project.put("investmentMinimum", 100);
        project.put("investmentMaximum", transfer.getSurplusCapital());
        project.put("labels", borrowing.getLabels());
        project.put("touziqixian", borrowing.getPeriod());

        return Result.success("转让列表", project);
    }

    @RequestMapping(value = "{id}/invest")
    @ResponseBody
    public Result shortcut(@PathVariable Integer id, Integer amount, HttpServletRequest request) {

        // 验证转让
        String token = request.getHeader("sid");
        User currentUser = null;
        if (!org.apache.commons.lang3.StringUtils.isBlank(token)) {
            currentUser = userService.getCurrentUserOfApp(null, token);
        }
        if (currentUser == null) {
            return Result.error("请先登录");
        }
        Transfer transfer = transferService.get(id);
        if (transfer == null) {
            return Result.error("转让不存在");
        }
        if (transfer.getIsFull()) {
            return Result.error("已满额");
        }

        // 验证项目
        Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
        if (borrowing == null) {
            return Result.error("投资项目不存在");
        }

        if (currentUser == null) {
            return Result.error("请先登录");
        }
        currentUser = userService.get(currentUser.getId());

        if (transfer.getTransfer().intValue() == currentUser.getId().intValue()) {
            return Result.error("不能购买自己的债权");
        }

        if (borrowing.getBorrower().intValue() == currentUser.getId().intValue()) {
            return Result.error("受让人不能为借款人");
        }

        //逾期判断
        List<Repayment> repayments = repaymentService.findList(borrowing.getId());
        for (Repayment repayment : repayments) {
            if (repayment.getState().equals(RepaymentState.REPAID)) {
                continue;
            }
            if (repayment.getIsOverdue()) {
                return Result.error("借款逾期，购买失败");
            }
        }

        // 会员资金
        UserFinance investorFinance = userFinanceService.getByUserId(currentUser.getId());
        BigDecimal transferCapital = new BigDecimal(amount * 100);
        // 投资人余额校验
        if (transferCapital.compareTo(investorFinance.getAvailable()) > 0) {
            return Result.error("账户余额不足");
        }

        // 投资人余额校验
        if (transfer.getState().equals(TransferLoanState.CANCEL)) {
            return Result.error("转让已撤销");
        }

        try {
            Request backRequest = businessService.transferFrozen(request, transfer, amount, currentUser, borrowing, PaymentOrderType.TRANSFER_FROZEN);
            return Result.success("购买成功", backRequest.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() == null) {
                return Result.error("购买失败");
            }
            return Result.error("系统错误");
        }
    }

    /**
     * 计算债权
     **/
    @RequestMapping(value = "cal", method = RequestMethod.POST)
    @ResponseBody
    public Result cal(Integer id, Integer parts) {

        if (id == null || parts == null) {
            return Result.error("参数错误");
        }

        // 验证转让
        Transfer transfer = transferService.get(id);
        if (transfer == null) {
            return Result.error("转让不存在");
        }

        if (transfer.getIsFull()) {
            return Result.error("已满额");
        }

        // 验证项目
        Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
        if (borrowing == null) {
            return Result.error("投资项目不存在");
        }
        //逾期判断
        List<Repayment> repayments = repaymentService.findList(borrowing.getId());
        for (Repayment repayment : repayments) {
            if (repayment.getState().equals(RepaymentState.REPAID)) {
                continue;
            }
            if (repayment.getIsOverdue()) {
                return Result.error("借款逾期，购买失败");
            }
        }

        BigDecimal transferWorth = BigDecimal.ZERO;
        BigDecimal transferCapital = new BigDecimal(parts * 100);
        BigDecimal transferFee = BigDecimal.ZERO;
        List<RepaymentPlan> repaymentPlans = repaymentPlanService.findListCanTransfer(transfer.getBorrowing(), transfer.getTransfer());
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            if (repaymentPlan.getState().equals(RepaymentState.REPAID)) {
                continue;
            }
            repaymentPlan = accountantService.calCurrentSurplusValue(repaymentPlan, parts, transfer.getSurplusParts(), transfer.getMaxParts());
            transferWorth = transferWorth.add(repaymentPlan.getCapitalValue().add(repaymentPlan.getInterestValue()));
        }

        transferFee = transferFee.add(AccountantUtils.calFee(transferCapital, borrowing.getInTransferFeeRate()));

        Map<String, Object> messages = new HashMap<String, Object>();
        //债权价值
        messages.put("transferWorth", transferWorth);
        //债权价格
        messages.put("transferCapital", transferCapital);
        //服务费
        messages.put("transferFee", transferFee);
        //支付金额
        messages.put("transferAmount", transferCapital.add(transferFee));

        return Result.success("债权计算", messages);
    }

    @RequestMapping("agreement/{id}")
    public String agreement(@PathVariable Integer id, ModelMap model) {
        // 验证项目
        Transfer transfer = transferService.get(id);
        if (transfer == null) {
            return "转让不存在";
        }
        Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
        if (borrowing == null) {
            return "借款不存在";
        }
        //获取受让人转让协议
        Agreement agreement = agreementService.get(borrowing.getInvestTransferAgreementId());
        String agreementContent = agreement.getContent();

        try {
            model.addAttribute("content", FreemarkerUtils.process(agreementContent, new HashMap<>()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return "/agreement/agreement";
    }

    private void transferSet(Transfer transfer, Borrowing borrowing) {
        Date begin = transfer.getCreateDate();
        Integer residualPeriod = 0;
        Date nextRepaymentDate = null;
        Date lastRepaymentDate = null;
        List<RepaymentPlan> repaymentPlans = repaymentPlanService.findList(transfer.getBorrowing(), transfer.getId());
        for (int i = 0; i<repaymentPlans.size();i++) {
            RepaymentPlan repaymentPlan = repaymentPlans.get(i);
            lastRepaymentDate = repaymentPlan.getRepaymentRecordPayDate();
            if (null == nextRepaymentDate) {
                nextRepaymentDate = lastRepaymentDate;
            }
            if (repaymentPlan.getState() == RepaymentState.REPAYING) {
                residualPeriod++;
            }
            if (repaymentPlan.getPaidDate() != null
                    && DateUtils.compareTwoDate(repaymentPlan.getRepaymentRecordPayDate(), repaymentPlan.getPaidDate()) == 1) {
                begin = repaymentPlan.getRepaymentRecordPayDate();
            }
        }
        if (borrowing.getPeriodUnit() == PeriodUnit.DAY) {
            if (begin.getTime()<borrowing.getInterestBeginDate().getTime()){
                begin=borrowing.getInterestBeginDate();
            }
            residualPeriod = new Double(DateUtils.getDaysOfTwoDate(lastRepaymentDate, DateUtils.getMinDateOfDay(begin))).intValue();
        }
        transfer.setResidualPeriod(residualPeriod);
        transfer.setResidualUnit(borrowing.getPeriodUnitDes());
        transfer.setNextRepaymentDate(nextRepaymentDate);
    }
}
