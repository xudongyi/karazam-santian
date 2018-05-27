/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.uc;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.FreemarkerUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.common.view.PdfView;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.InterestMethod;
import com.klzan.p2p.enums.PeriodUnit;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.agreement.AgreementService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.user.CorporationService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.investment.InvestmentVo;
import com.klzan.p2p.vo.user.UserVo;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 投资管理
 * Created by chenxinglin Date: 2016-12-12
 */
@Controller("ucInvestmentController")
@RequestMapping("/uc/investment")
public class InvestmentController extends BaseController {

    /** 错误视图 */
    protected static final String ERROR_VIEW = "/error";

    @Inject
    private UserService userService;
    @Inject
    private InvestmentService investmentService;

    @Inject
    private InvestmentRecordService investmentRecordService;

    @Inject
    private BorrowingService borrowingService;

    @Inject
    private RepaymentService repaymentService;

    @Inject
    private RepaymentPlanService repaymentPlanService;

    @Inject
    private DataConvertService dataConvertService;

    @Inject
    private CorporationService corporationService;
    @Inject
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Resource
    private AgreementService agreementService;

    @RequestMapping
    public String index(@CurrentUser User currentUser) {
        return "uc/investment/index";
    }

    @RequestMapping("data")
    @ResponseBody
    public PageResult<InvestmentVo> investments(@CurrentUser User currentUser,HttpServletRequest request, Date startDate,Date endDate, PageCriteria criteria) {
        criteria.getParams().add(ParamsFilter.addFilter("investor", ParamsFilter.MatchType.EQ, currentUser.getId()));
        return investmentService.findInvestmentList(criteria, false, startDate,endDate);
    }

    @RequestMapping(value = "/records/{investmentId}")
    public String records(@CurrentUser User currentUser, Model model,@PathVariable Integer investmentId) {
        model.addAttribute("investmentRecords", investmentRecordService.findListByInvestment(investmentId));
        return "uc/investment/records";
    }

    /**
     * 协议
     */
    @RequestMapping("/agreement/{investmentId}")
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

    /**
     * 下载协议
     */
    @RequestMapping("/agreement/{investmentId}/download")
    public ModelAndView agreementDownload(@CurrentUser User currentUser, @PathVariable Integer investmentId, ModelMap model, RedirectAttributes redirectAttributes) {

        // 验证投资
        Investment investment = investmentService.get(investmentId);
        Map map = new HashMap<>();
        if (investment == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "投资不存在");
        }
        // 验证用户
        if(!investment.getInvestor().equals(currentUser.getId())){
            redirectAttributes.addFlashAttribute("flashMessage", "拒绝访问");
        }
        // 验证项目
        Borrowing borrowing = borrowingService.get(investment.getBorrowing());
        if (borrowing == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "借款不存在");
        }
        User borrower = userService.get(borrowing.getBorrower());
        UserVo borrowUserInfo = userService.getUserById(borrower.getId());
        map.put("bUserInfo", borrowUserInfo);
        map.put("borrower", borrower);
        map.put("currentUser",userService.getUserById(currentUser.getId()));
        map.put("borrowCorpLicenseNo",borrowUserInfo.getCorpLicenseNo());
        map.put("borrowCorpName",borrowUserInfo.getCorpName());
        map.put("seriousOverdueStartPeriod",borrowing.getSeriousOverdueStartPeriod());
        map.put("seriousOverdueInterestRate",borrowing.getSeriousOverdueInterestRate());
        map.put("overdueInterestRate",borrowing.getOverdueInterestRate());
        if (currentUser.getType()!=UserType.GENERAL){
            Corporation investCorporation = corporationService.findCorporationByUserId(currentUser.getId());
            map.put("investCorporation",investCorporation);
        }

        List<Repayment> repayments= repaymentService.findList(investment.getBorrowing());
        map.put("repayments", repayments);   /*还款列表 */
        if(repayments!=null && repayments.size()>0){
            Repayment rm = repayments.get(0);
            for(Repayment repayment : repayments){
                if(rm.getPeriod() < repayment.getPeriod()){
                    rm = repayment;
                }
            }
            map.put("repaymentEndDate", rm.getPayDate());   /*最后还款日期 */
        }
        BigDecimal totalRepaymentMoney = BigDecimal.ZERO;
        List<Repayment> dataConverRepayments =dataConvertService.convertRepayments(repayments);
        for (Repayment repayment : dataConverRepayments) {
            totalRepaymentMoney = totalRepaymentMoney.add(repayment.getCapitalInterest());
        }
        Agreement agreement = agreementService.get(borrowing.getAgreementId());
        UserVo currentUserInfo = userService.getUserById(currentUser.getId());

        map.put("totalRepaymentMoney",totalRepaymentMoney);
        map.put("currentUser",currentUserInfo);
        map.put("investmentType",currentUserInfo.getType());

        map.put("borrowing",borrowing);
        //出借人姓名
        map.put("realName",currentUserInfo.getRealName());
        List<Investment> investments = investmentService.findList(investment.getBorrowing());
        model.addAttribute("investments", dataConvertService.convertInvestments(investments));    /*所有投资*/
        List<Investment> converInvestments = new ArrayList<>();
        for (Investment investment1:investments){
            if (investment1.getTransfer()==null){
                if (investment1.getInvestor().intValue()==currentUser.getId().intValue()){
                    investment1.setInvestorLoginName(currentUser.getLoginName());
                }
                converInvestments.add(dataConvertService.convertInvestment(investment1));
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
        map.put("borrowUserType",borrowUserInfo.getType());
        map.put("borrowCorpLicenseNo",borrowUserInfo.getCorpLicenseNo());
        map.put("borrowCorpName",borrowUserInfo.getCorpName());
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
            map.put("mouthRepaymentDay",sdf.format(listRepaymentPlan.get(listRepaymentPlan.size()-1).getRepaymentRecord().getPayDate()).substring(6,8));
        }

        map.put("repaymentPlans",listRepaymentPlan);
        map.put("periods",borrowing.getRepayPeriod());
        map.put("repaymentPlanSize",repaymentPlans.size()+1);
        //借款服务费
        map.put("borrowServiceFee",borrowing.getAmount().multiply(borrowing.getFeeRate()).divide(new BigDecimal(100)));
        //还款服务费
        map.put("repayServiceFee",borrowing.getRepaymentFeeRate().divide(new BigDecimal(borrowing.getRepayPeriod()),2,BigDecimal.ROUND_HALF_UP));
        Map newMap = new HashMap();
        try {
            newMap.put("content",
                    FreemarkerUtils.process(
                            agreement!=null?agreement.getContent():agreementService.findAll().get(0).getContent(),map
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        String agreementHtml = parse("/agreement/agreement.ftl", newMap);
        // HTML协议转换为PDF协议视图
        String filename = null;
        filename = new SimpleDateFormat("yyyyMMdd").format(investment.getCreateDate()) + "-" + borrowing.getId() + ".pdf";
        try {
            agreementHtml = FreemarkerUtils.process(agreementHtml,map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        PdfView agreementPdf = new PdfView(filename,agreementHtml );
        // 下载
        return new ModelAndView(agreementPdf);
    }
    public String parse(String templatePath, Map<String, Object> model) {
        Assert.hasText(templatePath);
        StringWriter stringWriter = null;
        BufferedWriter writer = null;

        try {
            Template template = this.freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
            stringWriter = new StringWriter();
            writer = new BufferedWriter(stringWriter);
            template.process(model, writer);
            writer.flush();
            String var6 = stringWriter.toString();
            return var6;
        } catch (Exception var10) {
            var10.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
            IOUtils.closeQuietly(stringWriter);
        }
        return null;
    }

}
