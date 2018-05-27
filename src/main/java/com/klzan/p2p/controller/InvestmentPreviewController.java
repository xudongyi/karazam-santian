/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.controller;

import com.klzan.core.util.BeanUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.RSAService;
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.InvestmentRecord;
import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.borrowing.BorrowingExtraService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.material.MaterialService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.borrowing.BorrowingVo;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Controller - 投资项目预览
 */
@Controller
@RequestMapping("investment/preview")
public class InvestmentPreviewController {
    @Inject
    private BorrowingService borrowingService;

    @Inject
    private BorrowingExtraService borrowingExtraService;

    @Inject
    private InvestmentService investmentService;

    @Inject
    private InvestmentRecordService investmentRecordService;

    @Inject
    private UserService userService;

    @Inject
    private RSAService rsaService;

    @Inject
    private MaterialService materialService;

    @Inject
    private RepaymentService repaymentService;

    @Inject
    private RepaymentPlanService repaymentPlanService;

    @Inject
    private DataConvertService dataConvertService;

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
     * 预览
     */
    @RequestMapping()
    public String preview(BorrowingVo pProject, ModelMap model, HttpServletRequest request) throws Exception {
        if (null != pProject.getId()) {
            Borrowing borrowing = borrowingService.get(pProject.getId());
            if (borrowing.getProgress() == BorrowingProgress.LENDING) {
                BeanUtils.copyBean2Bean(pProject, borrowing);
            }
        }
        // 生成密钥
        RSAPublicKey publicKey = rsaService.generateKey(request);
        model.addAttribute("project", pProject);
        if(!StringUtils.isBlank(pProject.getLabels())){
            model.addAttribute("labels", pProject.getLabels().split("，|,"));
        }
        User borrower = userService.get(pProject.getBorrower());
        model.addAttribute("borrower", borrower);
        model.addAttribute("borrowerInfo", userService.getUserById(pProject.getBorrower()));

        //密码
        model.addAttribute("modules", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        model.addAttribute("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));

        //项目材料、投标记录
        model.addAttribute("materials", materialService.findList(pProject.getId()));
        model.addAttribute("extras", borrowingExtraService.findByBorrowing(pProject.getId()));
        model.addAttribute("investments", dataConvertService.convertInvestments(investmentService.findList(pProject.getId(), InvestmentState.INVESTING)));
        List<InvestmentRecord> investmentRecords = investmentRecordService.findList(pProject.getId(), false, InvestmentState.PAID, InvestmentState.SUCCESS);
        model.addAttribute("investmentRecords", dataConvertService.convertInvestmentRecords(investmentRecords));

        //还款   还款计划
        List<Repayment> repayments = repaymentService.findList(pProject.getId());
        model.addAttribute("repayments", repayments);
        model.addAttribute("repaymentPlans", repaymentPlanService.findList(pProject.getId()));
        Integer repaidPeriodCount = 0;
        Integer repayingPeriodCount = 0;
        Integer overduePeriodCount = 0;
        model.addAttribute("repaidPeriodCount", repaidPeriodCount);
        model.addAttribute("repayingPeriodCount", repayingPeriodCount);
        model.addAttribute("overduePeriodCount", overduePeriodCount);

        return "investment-preview/detail";
    }
}





