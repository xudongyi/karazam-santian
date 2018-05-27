/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.borrowing;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.Agreement;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.BorrowingApply;
import com.klzan.p2p.model.sys.SysUser;
import com.klzan.p2p.service.agreement.AgreementService;
import com.klzan.p2p.service.borrowing.BorrowingApplyService;
import com.klzan.p2p.service.borrowing.BorrowingFieldRemarkService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.material.MaterialService;
import com.klzan.p2p.vo.borrowing.BorrowingApplyVo;
import com.klzan.p2p.vo.borrowing.BorrowingVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 借款申请
 *
 * @author: chenxinglin
 */
@Controller
@RequestMapping("admin/borrowing")
public class BorrowingApplyController extends BaseAdminController {

    private final static BorrowingType[] types = {BorrowingType.CREDIT, BorrowingType.GUARANTEE, BorrowingType.MORTGAGE};

    private final static RepaymentMethod[] repaymentMethods = {RepaymentMethod.EACH_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL, RepaymentMethod.EACH_PERIOD_AVG_CAPITAL_PLUS_INTEREST, RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST};
    private final static InterestMethod[] interestMethods = InterestMethod.values();

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private MaterialService materialService;

    @Resource
    private BorrowingApplyService borrowingApplyService;
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
     * 申请
     *
     * @param model
     * @return
     */
    @RequiresPermissions("borrowing:apply")
    @RequestMapping(value = "apply", method = RequestMethod.GET)
    public String createView(Model model, Boolean copy, Integer id) {

        //操作
        model.addAttribute("action", "apply");

        //复制 借款数据
        if (copy != null && copy) {
            model.addAttribute("borrowing", borrowingService.get(id));
//            model.addAttribute("materials", materialService.findList(id));
        }

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
        //页面数据
        model.addAttribute("types", types);
        model.addAttribute("periodUnits", PeriodUnit.values());//期限单位
        model.addAttribute("guaranteeMethods", GuaranteeMethod.values());
        model.addAttribute("creditRatings", CreditRating.values());//信用评级
        model.addAttribute("investmentMethods", InvestmentMethod.values());//投资方式
        model.addAttribute("interestMethods", interestMethods);//计息方式
        model.addAttribute("repaymentMethods", repaymentMethods);
        model.addAttribute("lendingTimes", LendingTime.values());
        model.addAttribute("repaymentFeeMethods", RepaymentFeeMethod.values());
        model.addAttribute("materialTypes", MaterialType.values());
        return template("borrowing/apply");
    }

    /**
     * 申请
     *
     * @param borrowingVo
     * @return
     */
    @RequiresPermissions("borrowing:apply")
    @RequestMapping(value = "apply", method = RequestMethod.POST)
    @ResponseBody
    public Result createData(BorrowingVo borrowingVo, String prepareState, String opinion, HttpServletRequest request) {
        try {
            Borrowing borrowing = borrowingService.apply(borrowingVo, prepareState, opinion);
            Map<String, ?> requestParamMap = WebUtils.getRequestParamMap(request);
            borrowingFieldRemarkService.createOrUpdateRemarks(borrowing.getId(), requestParamMap);
            return Result.success();
        } catch (Exception e) {
            logger.error("新增借款失败", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 来至PC前端的借款申请列表
     * @param modelMap
     * @return
     */
    @RequiresPermissions("borrowing:apply:fromwebsite")
    @RequestMapping("borrowingApplyFromWebSite")
    public String borrowingApplyFromWebSite(ModelMap modelMap){
        modelMap.addAttribute("borrowTypes",BorrowingApplyType.values());
        modelMap.addAttribute("borrowingApplyProgresses",BorrowingApplyProgress.values());
        return "admin/borrowing_apply/list";
    }

    @RequiresPermissions("borrowing:apply:fromwebsite")
    @RequestMapping("borrowingApplyFromWebSite.json")
    @ResponseBody
    public Object borrowingApplyFromWebSiteData(PageCriteria criteria,BorrowingApplyVo vo){

        PageResult<BorrowingApplyVo> borrowingApplyByPage = borrowingApplyService.findBorrowingApplyByPage(criteria, vo);

        return borrowingApplyByPage;
    }

    /**
     * 用户反馈
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/suggestion/{id}")
    public String suggestion(@PathVariable("id") Integer id,String type, Model model){
        model.addAttribute("id",id);
        model.addAttribute("type",type);
        return "admin/borrowing_apply/suggestion";
    }

    @RequestMapping(value="/doSet",method = RequestMethod.POST)
    @ResponseBody
    public Result doSet(String id, String type, String suggestion, Model model){
        BorrowingApply borrowingApply = borrowingApplyService.get(new Integer(id));
        if (borrowingApply==null||borrowingApply.getDeleted()){
            return Result.error("未找到该借款申请");
        }
        if ("connected".equals(type)){
            borrowingApply.setBorrowingApplyProgress(BorrowingApplyProgress.CONTACTED);
        }else if ("reject".equals(type)){
            borrowingApply.setBorrowingApplyProgress(BorrowingApplyProgress.REJECTED);
        }else {
            borrowingApply.setBorrowingApplyProgress(BorrowingApplyProgress.COMPLETED);
        }
        SysUser currentSysUser = UserUtils.getCurrentSysUser();
        borrowingApply.setRemark("手机号为: "+currentSysUser.getMobile()+"的管理员将该借款设置为: "+borrowingApply.getBorrowingApplyProgress().getDisplayName());
        borrowingApply.setSuggestion(suggestion);
        borrowingApplyService.merge(borrowingApply);
        return Result.success();
    }
}
