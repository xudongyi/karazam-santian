package com.klzan.mobile.controller.uc;

import com.klzan.core.util.DateUtils;
import com.klzan.core.util.FreemarkerUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.enums.InterestMethod;
import com.klzan.p2p.enums.PeriodUnit;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.agreement.AgreementService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.service.user.CorporationService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.transfer.TransferVo;
import com.klzan.p2p.vo.user.UserVo;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/6/22 0022.
 */
@Controller("mobileUCAgreementController")
@RequestMapping("/mobile/uc")
public class AboutAgreementController {

    @Inject
    private TransferService transferService;
    @Inject
    private BorrowingService borrowingService;
    @Inject
    private InvestmentService investmentService;
    @Inject
    private UserService userService;
    @Inject
    private RepaymentPlanService repaymentPlanService;
    @Inject
    private AgreementService agreementService;
    @Inject
    private CorporationService corporationService;
    @Inject
    private RepaymentService repaymentService;
    @Inject
    private DataConvertService dataConvertService;



    @RequestMapping(value = "/transferin/agreementDetail/{id}/{investId}", method = RequestMethod.GET)
    public String transferinagreement(@CurrentUser User currentUser, @PathVariable Integer id, @PathVariable Integer investId, ModelMap model, RedirectAttributes redirectAttributes) {
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "未登录");
            return "redirect:/login";
        }
        Transfer transfer = transferService.get(id);
        Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
        borrowing = dataConvertService.convertBorrowing(borrowing);
        Investment investment = investmentService.get(investId);
        // 验证项目
        UserVo transferUser = userService.getUserById(transfer.getTransfer());  //转让人
        UserVo borrowingUser = userService.getUserById(borrowing.getBorrower());//借款人
        UserVo investUser = userService.getUserById(currentUser.getId());       //投资人

        BigDecimal repayingInterest = BigDecimal.ZERO;  //未收利息（剩余利息）
        BigDecimal totalCurrentClaimTotalPrice = BigDecimal.ZERO;  //债权价格（剩余本金）
        List<RepaymentPlan> repaymentPlans = repaymentPlanService.findList(borrowing.getId(), investment.getInvestor(), investment.getId());//currentUser.getId());
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            repayingInterest = repayingInterest.add(repaymentPlan.getInterest());
            totalCurrentClaimTotalPrice = totalCurrentClaimTotalPrice.add(repaymentPlan.getCapital());
        }
        //--------- 计息开始时间----------
        Date beginDate = transferSetjixitime(transfer, borrowing, investment.getId());
        //实际计息时间
        Integer residualPeriod = 0;
        if (borrowing.getFinalPayDate() != null) {
            residualPeriod = new Double(DateUtils.getDaysOfTwoDate(DateUtils.getMaxDateOfDay(borrowing.getFinalPayDate()), DateUtils.getMinDateOfDay(beginDate))).intValue();
        }

        if (borrowing.getPeriodUnit() == PeriodUnit.MONTH) {
            residualPeriod = Integer.parseInt(transfer.getSurplusPeriod().substring(0, transfer.getSurplusPeriod().indexOf("/")));
        }
        //-----------------
        String agreementContent = null;
        Map map = new HashMap();

        Agreement agreement = agreementService.get(borrowing.getInvestTransferAgreementId());
        agreementContent = agreement.getContent();
        //借款人
        map.put("surplusPeriod", residualPeriod);
        map.put("periodUnit", borrowing.getPeriodUnitDes());
        map.put("borrowingRealName", borrowingUser.getRealName());
        map.put("borrowingIdNo", borrowingUser.getIdNo());
        map.put("borrowingType", borrowingUser.getType());
        map.put("borrowingCorpName", borrowingUser.getCorpName());
        map.put("borrowingCorpLicenseNo", borrowingUser.getCorpLicenseNo());
        //转让人
        map.put("transferRealName", transferUser.getRealName());
        map.put("transferCorpName", transferUser.getCorpName());
        //受让人
        map.put("investRealName", investUser.getRealName());
        map.put("investIdNo", investUser.getIdNo());
        map.put("investType", investUser.getType());
        map.put("investCorpName", investUser.getCorpName());
        map.put("investCorpLicenseNo", investUser.getCorpLicenseNo());

        //剩余利息,剩余本金
        map.put("repayingInterest", repayingInterest);
        map.put("totalCurrentClaimTotalPrice", totalCurrentClaimTotalPrice);
        map.put("interestRate", borrowing.getRealInterestRate());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //合同编号
        String dataStr = sdf.format(investment.getCreateDate());
        map.put("contractNo", "STYH-" + dataStr + "-" + borrowing.getId());
        //剩余期限ＳＴＹＨ
        //签约日期
        Date date = investment.getCreateDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String nowDate = simpleDateFormat.format(date);
        map.put("year", nowDate.substring(0, 4));
        map.put("mouth", nowDate.substring(4, 6));
        map.put("day", nowDate.substring(6, 8));

        try {
            model.addAttribute("content", FreemarkerUtils.process(agreementContent, map));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return "/agreement/agreement";
    }

    //投资协议
    @RequestMapping("/investment/agreement/{investmentId}")
    public String agreement(@CurrentUser User currentUser, @PathVariable Integer investmentId, ModelMap model, RedirectAttributes redirectAttributes) {

        // 验证投资
        Investment investment = investmentService.get(investmentId);
        if (investment == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "投资不存在");
            return "redirect:/uc/investment";
        }
        // 验证用户
        if(!investment.getInvestor().equals(currentUser.getId())){
            redirectAttributes.addFlashAttribute("flashMessage", "拒绝访问");
            return "redirect:/uc/investment";
        }
        // 验证项目
        Borrowing borrowing = borrowingService.get(investment.getBorrowing());
        if (borrowing == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "借款不存在");
            return "redirect:/uc/investment";
        }
        User borrower = userService.get(borrowing.getBorrower());
        UserVo borrowUserInfo = userService.getUserById(borrower.getId());

        model.addAttribute("bUserInfo", borrowUserInfo);
        model.addAttribute("borrower", borrower);
        model.addAttribute("currentUser",userService.getUserById(currentUser.getId()));
        if (currentUser.getType()!=UserType.GENERAL){
            Corporation investCorporation = corporationService.findCorporationByUserId(currentUser.getId());
            model.addAttribute("investCorporation",investCorporation);
        }

        List<Repayment> repayments= repaymentService.findList(investment.getBorrowing());
        if(repayments!=null && repayments.size()>0){
            Repayment rm = repayments.get(0);
            for(Repayment repayment : repayments){
                if(rm.getPeriod() < repayment.getPeriod()){
                    rm = repayment;
                }
            }
            model.addAttribute("repaymentEndDate", rm.getPayDate());   /*最后还款日期 */
        }
        BigDecimal totalRepaymentMoney = BigDecimal.ZERO;
        List<Repayment> dataConverRepayments =dataConvertService.convertRepayments(repayments);
        for (Repayment repayment : dataConverRepayments) {
            totalRepaymentMoney = totalRepaymentMoney.add(repayment.getCapitalInterest());
        }
        Agreement agreement = agreementService.get(borrowing.getAgreementId());
        UserVo currentUserInfo = userService.getUserById(currentUser.getId());
        try {
            Map map = new HashMap<>();
            map.put("totalRepaymentMoney", totalRepaymentMoney);   /*总共还款金额*/
            map.put("repayments", repayments);   /*还款列表 */
            map.put("seriousOverdueStartPeriod",borrowing.getSeriousOverdueStartPeriod());
            map.put("seriousOverdueInterestRate",borrowing.getSeriousOverdueInterestRate());
            map.put("overdueInterestRate",borrowing.getOverdueInterestRate());
            //出借人姓名
            map.put("idNo",currentUserInfo.getIdNo());//2018/1/4
            map.put("corpLicenseNo",currentUserInfo.getCorpLicenseNo());
            map.put("currentUser",currentUserInfo);
            map.put("corpName",currentUserInfo.getCorpName());
            map.put("investmentType",currentUserInfo.getType());
            map.put("borrowing",borrowing);
            map.put("borrowUserType",borrowUserInfo.getType());
            map.put("borrowCorpLicenseNo",borrowUserInfo.getCorpLicenseNo());
            map.put("borrowCorpName",borrowUserInfo.getCorpName());
            map.put("realName",currentUserInfo.getRealName());
            List<Investment> investments = dataConvertService.convertInvestments(investmentService.findList(investment.getBorrowing()));
            List<Investment> converInvestments = new ArrayList<>();
            for (Investment investment1:investments){
                if (investment1.getTransfer()==null){
                    converInvestments.add(investment1);
                }
            }
            map.put("investments",converInvestments);
            //合同编号  PTD-${((investment.createDate)?string("yyyyMMdd"))!"XXXXXXXX"}-${(borrowing.id)!"XXXX"}
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dataStr = sdf.format(borrowing.getLendingDate());
            map.put("contractNo","STYH-"+dataStr+"-"+borrowing.getId());
            //借款人姓名,拍拖贷用户名，身份证号
            map.put("borrowRealName",borrowUserInfo.getRealName());
            map.put("borrowLoginName",borrowUserInfo.getLoginName());
            map.put("borrowIdNo",borrowUserInfo.getIdNo());
            //合同年 月 日
            map.put("contractYear",dataStr.substring(0,4));
            map.put("contractMonth",dataStr.substring(4,6));
            map.put("contractDay",dataStr.substring(6,8));
            map.put("repaymentMethod",borrowing.getRepaymentMethodDes());
            //借款金额
            map.put("amount",borrowing.getAmount());
            //期限
            map.put("period",borrowing.getPeriod());
            //期限单位
            map.put("periodUnitDes",borrowing.getPeriodUnitDes());
            //实际利率
            map.put("realInterestRate",borrowing.getRealInterestRate());
            //借款用途
            map.put("purpose",borrowing.getPurpose());
            //合同生效日期
            Date contractBeginDate = borrowing.getInterestBeginDate();
            map.put("contractBeginDateYear",sdf.format(contractBeginDate).substring(0,4));
            map.put("contractBeginDateMonth",sdf.format(contractBeginDate).substring(4,6));
            map.put("contractBeginDateDay",sdf.format(contractBeginDate).substring(6,8));
            //合同到期日期
            Date contractEndDate = null;
            if(borrowing.getPeriodUnit()== PeriodUnit.DAY){
                contractEndDate = DateUtils.addDays(contractBeginDate,borrowing.getPeriod());
            }else if (borrowing.getPeriodUnit()==PeriodUnit.MONTH){
                contractEndDate = DateUtils.addMonths(contractBeginDate,borrowing.getPeriod());
            }
            map.put("contractEndDateYear",sdf.format(contractEndDate).substring(0,4));
            map.put("contractEndDateMonth",sdf.format(contractEndDate).substring(4,6));
            Borrowing borrowing1 = dataConvertService.convertBorrowing(borrowing);
            map.put("contractEndDateDay",sdf.format(borrowing1.getFinalPayDate()).substring(6,8));
            //还款计划列表
            map.put("isBorrowing",false);
            List<RepaymentPlan> repaymentPlans = repaymentPlanService.findList(investment.getBorrowing(), investment.getInvestor(), investmentId);
            model.addAttribute("repaymentPlans", dataConvertService.convertRepaymentPlans(repaymentPlans));   /*还款计划列表 */
            BigDecimal repayAmount = BigDecimal.ZERO; /*本息*/
            for(RepaymentPlan repaymentPlan:repaymentPlans){
                repayAmount = repayAmount.add(repaymentPlan.getCapitalInterest());
            }
            List<RepaymentPlan> listRepaymentPlan  = dataConvertService.convertRepaymentPlans(repaymentPlans);
            if (borrowing.getInterestMethod() == InterestMethod.T_PLUS_ZERO_B){
                map.put("mouthRepaymentDay",sdf.format(contractBeginDate).substring(6,8));
            }else if(borrowing.getInterestMethod() == InterestMethod.T_PLUS_ONE_B){
                map.put("mouthRepaymentDay",sdf.format(DateUtils.addDays(contractBeginDate,1)).substring(6,8));
            }else if (borrowing.getInterestMethod() == InterestMethod.T_PLUS_ZERO){
                map.put("mouthRepaymentDay",sdf.format(listRepaymentPlan.get(listRepaymentPlan.size()-1).getRepaymentRecord().getPayDate()).substring(6,8));
            }else {
                if (!listRepaymentPlan.isEmpty()){
                    map.put("mouthRepaymentDay",sdf.format(listRepaymentPlan.get(listRepaymentPlan.size()-1).getRepaymentRecord().getPayDate()).substring(6,8));
                }

            }
            map.put("repaymentPlans",listRepaymentPlan);
            map.put("periods",borrowing.getRepayPeriod());
            map.put("repaymentPlanSize",repaymentPlans.size()+1);
            //借款服务费
            map.put("borrowServiceFee",borrowing.getAmount().multiply(borrowing.getFeeRate()).divide(new BigDecimal(100)));
            //还款服务费
            map.put("repayServiceFee",borrowing.getRepaymentFeeRate().divide(new BigDecimal(borrowing.getRepayPeriod()),2,BigDecimal.ROUND_HALF_UP));
            model.addAttribute("content",
                    FreemarkerUtils.process(
                            agreement!=null?agreement.getContent():agreementService.findAll().get(0).getContent(),map
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return "/agreement/agreement";
    }


    public Date transferSetjixitime(Transfer transfer, Borrowing borrowing, Integer investmentId) {

        List<Repayment> repayments = repaymentService.findList(borrowing.getId());
        Integer surplusperiod = Integer.parseInt(transfer.getSurplusPeriod().substring(0, transfer.getSurplusPeriod().indexOf("/")));
        Integer totalperiod = Integer.parseInt(transfer.getSurplusPeriod().substring(transfer.getSurplusPeriod().indexOf("/") + 1, transfer.getSurplusPeriod().length()));
        Integer period = totalperiod - surplusperiod + 1;
        Date begin = investmentService.get(investmentId).getCreateDate();
        for (Repayment repayment : repayments) {
            if (repayment.getPeriod() == period - 1 && repayment.getState() == RepaymentState.REPAID && transfer.getCreateDate().getTime() > repayment.getPaidDate().getTime()) {
                begin = repayment.getPayDate();
            }

        }

        return begin;
    }
}