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
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.enums.InterestMethod;
import com.klzan.p2p.enums.PeriodUnit;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.agreement.AgreementService;
import com.klzan.p2p.service.borrowing.BorrowingApplyService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.user.CorporationService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.borrowing.BorrowingApplyVo;
import com.klzan.p2p.vo.user.UserVo;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
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
 * 借款管理
 * Created by chenxinglin Date: 2016-12-12
 */
@Controller("ucBorrowingController")
@RequestMapping("/uc/borrowing")
public class BorrowingController extends BaseController{

    /** 错误视图 */
    protected static final String ERROR_VIEW = "/error";

    @Inject
    private UserService userService;

    @Inject
    private CorporationService corporationService;

    @Inject
    private BorrowingService borrowingService;

    @Inject
    private InvestmentService investmentService;
    @Resource
    private InvestmentRecordService investmentRecordService;

    @Inject
    private RepaymentService repaymentService;

    @Inject
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Inject
    private DataConvertService dataConvertService;

    @Resource
    private BorrowingApplyService borrowingApplyService;
    @Resource
    private AgreementService agreementService;

    @RequestMapping
    public String index() {
        return "uc/borrowing/index";
    }

    @RequestMapping("data/{state}")
    @ResponseBody
    public Object data(@PathVariable String state, @CurrentUser User currentUser, PageCriteria criteria, HttpServletRequest request) {

//        if("APPLY".equals(state)){
//            BorrowingApplyVo vo = new BorrowingApplyVo();
//            vo.setMobile(currentUser.getMobile());
//            PageResult<BorrowingApplyVo> borrowingApplyByPage = borrowingApplyService.findBorrowingApplyByPage(buildQueryCriteria(criteria,request), vo);
//            return borrowingApplyByPage;
//        }else {
            return borrowingService.findList(buildQueryCriteria(criteria,request),currentUser.getId(),state);
//        }

    }

    /**
     * 协议
     */
    @RequestMapping("/agreement/{borrowingId}")
    public String agreement(@CurrentUser User currentUser, @PathVariable Integer borrowingId, ModelMap model, RedirectAttributes redirectAttributes) {

        // 验证项目

            Borrowing borrowing = borrowingService.get(borrowingId);
            if (borrowing == null) {
                redirectAttributes.addFlashAttribute("flashMessage", "借款不存在");
                return "redirect:/uc/investment";
            }
            UserVo borrowUserInfo = userService.getUserById(currentUser.getId());

            Agreement agreement = agreementService.get(borrowing.getAgreementId());
            try {
            Map map = new HashMap<>();
            map.put("borrowing",borrowing);
            map.put("currentUser",currentUser);
            map.put("overdueInterestRate",borrowing.getOverdueInterestRate());
            map.put("seriousOverdueStartPeriod",borrowing.getSeriousOverdueStartPeriod());
            map.put("seriousOverdueInterestRate",borrowing.getSeriousOverdueInterestRate());
            //合同编号  PTD-${((investment.createDate)?string("yyyyMMdd"))!"XXXXXXXX"}-${(borrowing.id)!"XXXX"}
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date contractBeginDate = borrowing.getInterestBeginDate();
            String dataStr = sdf.format(contractBeginDate);
            map.put("contractNo","STYH-"+dataStr+"-"+borrowing.getId());
            //甲方:出借人 TODO
            List<Investment> investments = investmentService.findList(borrowingId);
            List<Investment> converInvestments = new ArrayList<>();

            for (Investment investment:investments){
                if (investment.getTransfer()==null){
                    converInvestments.add(dataConvertService.convertInvestment(investment));

                }
            }
            map.put("investments",converInvestments);
            //乙方:借款人
            map.put("borrowRealName",borrowUserInfo.getRealName());
            map.put("borrowLoginName",borrowUserInfo.getLoginName());
            map.put("borrowIdNo",borrowUserInfo.getIdNo());

            //营业执照
//            map.put("corpLicenseNo",borrowUserInfo.getCorpLicenseNo());
//            map.put("corpName",borrowUserInfo.getCorpName());
//            map.put("workPhone",borrowUserInfo.getWorkPhone());
//            map.put("userType",borrowUserInfo.getType());

            map.put("borrowUserType",borrowUserInfo.getType());
            map.put("borrowCorpLicenseNo",borrowUserInfo.getCorpLicenseNo());
            map.put("borrowCorpName",borrowUserInfo.getCorpName());
            //合同签订日期。出借日期
            //合同签订日期 年 月 日
            map.put("contractYear",sdf.format(contractBeginDate).substring(0,4));
            map.put("contractMonth",sdf.format(contractBeginDate).substring(4,6));
            map.put("contractDay",sdf.format(contractBeginDate).substring(6,8));
            //合同生效日期
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
            //借款金额
            map.put("amount",borrowing.getAmount());
            map.put("realInterestRate",borrowing.getRealInterestRate());
            map.put("period",borrowing.getPeriod());
            map.put("periodUnitDes",borrowing.getPeriodUnitDes());
            //还款方式
            map.put("repaymentMethod",borrowing.getRepaymentMethodDes());
            //还款计划
            map.put("isBorrowing",true);
            List<Repayment> repayments = repaymentService.findList(borrowingId);
            if (borrowing.getInterestMethod() == InterestMethod.T_PLUS_ZERO_B){
                map.put("mouthRepaymentDay",sdf.format(contractBeginDate).substring(6,8));
            }else if(borrowing.getInterestMethod() == InterestMethod.T_PLUS_ONE_B){
                map.put("mouthRepaymentDay",sdf.format(DateUtils.addDays(contractBeginDate,1)).substring(6,8));
            }else if (borrowing.getInterestMethod() == InterestMethod.T_PLUS_ZERO){
                map.put("mouthRepaymentDay",sdf.format(repayments.get(repayments.size()-1).getPayDate()).substring(6,8));
            }else {
                map.put("mouthRepaymentDay",sdf.format(repayments.get(repayments.size()-1).getPayDate()).substring(6,8));
            }
            BigDecimal totalRepaymentMoney = BigDecimal.ZERO;
            List<Repayment> dataConverRepayments =dataConvertService.convertRepayments(repayments);
            for (Repayment repayment : dataConverRepayments) {
                totalRepaymentMoney = totalRepaymentMoney.add(repayment.getCapitalInterest());
            }
            map.put("totalRepaymentMoney", totalRepaymentMoney);   /*总共还款金额*/
            map.put("repayments", dataConverRepayments);   /*还款列表 */
            if(repayments!=null && repayments.size()>0){
                Repayment rm = repayments.get(0);
                for(Repayment repayment : repayments){
                    if(rm.getPeriod() < repayment.getPeriod()){
                        rm = repayment;
                    }
                    totalRepaymentMoney.add(repayment.getRepaymentAmount());
                }

                model.put("repaymentEndDate", rm.getPayDate());   /*最后还款日期 */
            }
            map.put("periods",borrowing.getRepayPeriod());
            map.put("repaymentsSize",repayments.size()+1);
            //借款服务费
            map.put("borrowServiceFee",borrowing.getAmount().multiply(borrowing.getFeeRate()).divide(new BigDecimal(100),2));
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
    @RequestMapping("/agreement/{borrowingId}/download")
    public ModelAndView agreementDownload(@CurrentUser User currentUser, @PathVariable Integer borrowingId, ModelMap model, RedirectAttributes redirectAttributes) {
        // 验证项目
        Borrowing borrowing = borrowingService.get(borrowingId);

        UserVo borrowUserInfo = userService.getUserById(currentUser.getId());

        Agreement agreement = agreementService.get(borrowing.getAgreementId());
        Map map = new HashMap<>();
        map.put("currentUser",currentUser);
        map.put("borrowing",borrowing);
        map.put("overdueInterestRate",borrowing.getOverdueInterestRate());
        map.put("seriousOverdueStartPeriod",borrowing.getSeriousOverdueStartPeriod());
        map.put("seriousOverdueInterestRate",borrowing.getSeriousOverdueInterestRate());

        //甲方:出借人 TODO
        List<Investment> investments = investmentService.findList(borrowingId);
        List<Investment> converInvestments = new ArrayList<>();
        for (Investment investment:investments){
            if (investment.getTransfer()==null){
                converInvestments.add(dataConvertService.convertInvestment(investment));
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date contractBeginDate = borrowing.getInterestBeginDate();
        String dataStr = sdf.format(contractBeginDate);
        map.put("contractNo","STYH-"+dataStr+"-"+borrowing.getId());

        map.put("investments",converInvestments);
        //乙方:借款人
        map.put("borrowRealName",borrowUserInfo.getRealName());
        map.put("borrowLoginName",borrowUserInfo.getLoginName());
        map.put("borrowIdNo",borrowUserInfo.getIdNo());

        map.put("borrowUserType",borrowUserInfo.getType());
        map.put("borrowCorpLicenseNo",borrowUserInfo.getCorpLicenseNo());
        map.put("borrowCorpName",borrowUserInfo.getCorpName());

        //合同签订日期 年 月 日
        map.put("contractYear",sdf.format(contractBeginDate).substring(0,4));
        map.put("contractMonth",sdf.format(contractBeginDate).substring(4,6));
        map.put("contractDay",sdf.format(contractBeginDate).substring(6,8));
        //合同生效日期
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
        //借款金额
        map.put("amount",borrowing.getAmount());
        map.put("realInterestRate",borrowing.getRealInterestRate());
        map.put("period",borrowing.getPeriod());
        map.put("periodUnitDes",borrowing.getPeriodUnitDes());
        //还款方式
        map.put("repaymentMethod",borrowing.getRepaymentMethodDes());
        //还款计划
        map.put("isBorrowing",true);
        List<Repayment> repayments = repaymentService.findList(borrowingId);
        if (borrowing.getInterestMethod() == InterestMethod.T_PLUS_ZERO_B){
            map.put("mouthRepaymentDay",sdf.format(contractBeginDate).substring(6,8));
        }else if(borrowing.getInterestMethod() == InterestMethod.T_PLUS_ONE_B){
            map.put("mouthRepaymentDay",sdf.format(DateUtils.addDays(contractBeginDate,1)).substring(6,8));
        }else if (borrowing.getInterestMethod() == InterestMethod.T_PLUS_ZERO){
            map.put("mouthRepaymentDay",sdf.format(repayments.get(repayments.size()-1).getPayDate()).substring(6,8));
        }else {
            map.put("mouthRepaymentDay",sdf.format(repayments.get(repayments.size()-1).getPayDate()).substring(6,8));
        }
        map.put("repayments", dataConvertService.convertRepayments(repayments));   /*还款列表 */
        if(repayments!=null && repayments.size()>0){
            Repayment rm = repayments.get(0);
            for(Repayment repayment : repayments){
                if(rm.getPeriod() < repayment.getPeriod()){
                    rm = repayment;
                }
            }
            model.put("repaymentEndDate", rm.getPayDate());   /*最后还款日期 */
        }
        BigDecimal totalRepaymentMoney = BigDecimal.ZERO;
        List<Repayment> dataConverRepayments =dataConvertService.convertRepayments(repayments);
        for (Repayment repayment : dataConverRepayments) {
            totalRepaymentMoney = totalRepaymentMoney.add(repayment.getCapitalInterest());
        }
        map.put("totalRepaymentMoney", totalRepaymentMoney);   /*总共还款金额*/
        map.put("periods",borrowing.getRepayPeriod());
        map.put("repaymentsSize",repayments.size()+1);
        //借款服务费
        map.put("borrowServiceFee",borrowing.getAmount().multiply(borrowing.getFeeRate()).divide(new BigDecimal(100),2));
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
        // 生成HTML协议
        String agreementPath = "/agreement/agreement.ftl";
        String agreementHtml = parse(agreementPath, newMap);

        // HTML协议转换为PDF协议视图
        String filename = null;
        filename = new SimpleDateFormat("yyyyMMdd").format(borrowing.getLendingDate()) + "-" + borrowing.getId() + ".pdf";
        PdfView agreementPdf = new PdfView(filename, agreementHtml);
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
