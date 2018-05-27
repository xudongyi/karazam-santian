/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.controller.admin.borrowing;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.controller.admin.BaseAdminController;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.Agreement;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.BorrowingOpinion;
import com.klzan.p2p.model.Corporation;
import com.klzan.p2p.service.agreement.AgreementService;
import com.klzan.p2p.service.borrowing.*;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.material.MaterialService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.user.CorporationLegalService;
import com.klzan.p2p.service.user.CorporationService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.borrowing.BorrowingVo;
import com.klzan.p2p.vo.user.UserVo;
import com.klzan.plugin.repayalgorithm.DateUnit;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 借款
 *
 * @author: chenxinglin
 */
@Controller
@RequestMapping("admin/borrowing")
public class BorrowingController extends BaseAdminController {

    private final static BorrowingType[] types = {BorrowingType.CREDIT, BorrowingType.GUARANTEE, BorrowingType.MORTGAGE};

    private final static BorrowingProgress[] progresses = {BorrowingProgress.INQUIRING, BorrowingProgress.APPROVAL,
            BorrowingProgress.INVESTING, BorrowingProgress.LENDING, BorrowingProgress.REPAYING, BorrowingProgress.COMPLETED};

    private final static RepaymentMethod[] repaymentMethods = {RepaymentMethod.EACH_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL, RepaymentMethod.EACH_PERIOD_AVG_CAPITAL_PLUS_INTEREST, RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST};
    private final static RepaymentMethod[] repaymentMethodsAsDay = {RepaymentMethod.FIXED_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL, RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST};
    private final static InterestMethod[] interestMethods = InterestMethod.values();
    private final static InterestMethod[] interestMethods_ZERO = {InterestMethod.T_PLUS_ZERO};
    private final static InterestMethod[] interestMethods_ONE = {InterestMethod.T_PLUS_ONE, InterestMethod.T_PLUS_ONE_B, InterestMethod.T_PLUS_ZERO, InterestMethod.T_PLUS_ZERO_B};

    @Inject
    private BorrowingService borrowingService;

    @Inject
    private UserService userService;

    @Inject
    private MaterialService materialService;

    @Inject
    private BorrowingOpinionService bOpinionService;

    @Inject
    private BorrowingContactsService bContactsService;

    @Inject
    private BorrowingExtraService borrowingExtraService;

    @Inject
    private InvestmentService investmentService;

    @Inject
    private InvestmentRecordService investmentRecordService;

    @Inject
    private RepaymentService repaymentService;

    @Inject
    private RepaymentPlanService repaymentPlanService;

    @Inject
    private SettingUtils setting;

    @Inject
    private CorporationService corporationService;

    @Inject
    private CorporationLegalService corporationLegalService;

    @Inject
    private DataConvertService dataConvertService;
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
     * 列表
     *
     * @param model
     * @return
     */
    @RequiresPermissions("borrowing:list")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(PageCriteria PageCriteria, ModelMap model) {
        //页面数据
        model.addAttribute("progresses", progresses);

        return template("borrowing/list");
    }

    /**
     * 列表json
     *
     * @param pageCriteria
     * @return request
     */
    @RequiresPermissions("borrowing:list")
    @RequestMapping(value = "list.json", method = RequestMethod.GET)
    @ResponseBody
    public Object listData(PageCriteria pageCriteria, HttpServletRequest request, BorrowingProgress progress) {
        PageResult<Borrowing> page = borrowingService.findBorrowingList(buildQueryCriteria(pageCriteria, request), progress, Boolean.FALSE);
        page.setRows(dataConvertService.convertBorrowings(page.getRows()));
        return page;
    }
    /**
     * 查看
     *
     * @param model
     * @return
     */
    @RequiresPermissions("borrowing:view")
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Integer id, Model model) {

        //操作
        model.addAttribute("action", "view");

        //借款 借款材料
        Borrowing borrowing = borrowingService.get(id);
        model.addAttribute("borrowing", borrowing);
        model.addAttribute("materials", materialService.findList(id));
        model.addAttribute("extras", borrowingExtraService.findByBorrowing(id));

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
        return template("borrowing/view");
    }

    /**
     * 借款意见集合(JSON)
     */
    @RequestMapping(value = "opinions/list.json", method = RequestMethod.GET)
    @ResponseBody
    public List<BorrowingOpinion> opinions(Integer borrowingId) {
        return bOpinionService.findList(borrowingId);
    }

    /**
     * 修改
     *
     * @param model
     * @return
     */
    @RequiresPermissions("borrowing:update")
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateView(@PathVariable("id") Integer id, Model model) {

        //操作
        model.addAttribute("action", "update");

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
        return template("borrowing/update");
    }

    /**
     * 修改
     *
     * @param borrowingVo
     * @param request
     * @return MaterialVo materialVo, @RequestParam(name = "materials[]") List<MaterialVo> materials,
     */
    @RequiresPermissions("borrowing:update")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Result updateData(BorrowingVo borrowingVo, String opinion, HttpServletRequest request) {
        try {
            borrowingService.update(borrowingVo, opinion);
            borrowingFieldRemarkService.createOrUpdateRemarks(borrowingVo.getId(), WebUtils.getRequestParamMap(request));
            return Result.success();
        } catch (Exception e) {
            logger.error("修改借款失败", e);
            return Result.error("修改借款失败");
        }
    }

    /**
     * 借款人集合(JSON)
     */
    @RequestMapping(value = "borrower/json", method = RequestMethod.GET)
    @ResponseBody
    public List<UserVo> borrowerAsJson() {
        return userService.findHasOpenAcctUsers();
    }

    /**
     * 借款人查询集合(JSON)
     */
    @RequestMapping(value = "borrower/search.json")
    @ResponseBody
    public List<UserVo> borrowerSearchAsJson(@RequestParam(name = "q", defaultValue = "") String nameOrMobile) {
        return userService.findHasOpenAcctUsersByNameOrMobile(nameOrMobile);
    }

    @RequestMapping(value = "remarks.json")
    @ResponseBody
    public Result borrowingRemark(@RequestParam(required = false) Integer borrowingId) {
        if (null == borrowingId) {
            return Result.success("", new HashMap<>());
        }
        Map<String, String> reamrks = borrowingFieldRemarkService.findReamrks(borrowingId);
        return Result.success("", reamrks);
    }

    /**
     * 担保公司集合(JSON)
     */
    @RequestMapping(value = "guaranteeCorp/json", method = RequestMethod.GET)
    @ResponseBody
    public List<Corporation> guaranteeCorpAsJson() {
        List<Corporation> corp = corporationService.findGuaranteeCorp();
        return corp;
    }

    @RequestMapping(value = "repaymentMethods")
    @ResponseBody
    public List getRepaymentMethods(DateUnit dateUnit) {
        RepaymentMethod[] currentRepaymentMethods;
        List list = new ArrayList();
        Map<String, Object> map;
        if (dateUnit == DateUnit.DAY) {
            currentRepaymentMethods = repaymentMethodsAsDay;
        } else  {
            currentRepaymentMethods = repaymentMethods;
        }
        for (RepaymentMethod repaymentMethod : currentRepaymentMethods) {
            map = new HashedMap();
            map.put("id", repaymentMethod.name());
            map.put("text", repaymentMethod.getDisplayName());
            list.add(map);
        }
        return list;
    }

    @RequestMapping(value = "interestMethods")
    @ResponseBody
    public List getInterestMethods(DateUnit dateUnit) {
        InterestMethod[] currentInterestMethods;
        List list = new ArrayList();
        Map<String, Object> map;
        if (dateUnit == DateUnit.DAY) {
            currentInterestMethods = interestMethods_ONE;
        } else  {
            currentInterestMethods = interestMethods_ZERO;
        }
        for (InterestMethod interestMethod : currentInterestMethods) {
            map = new HashedMap();
            map.put("id", interestMethod.name());
            map.put("text", interestMethod.getDisplayBgName());
            list.add(map);
        }
        return list;
    }

    /**
     * 失败列表
     * @param model
     * @return
     */
    @RequiresPermissions("borrowing:failureList")
    @RequestMapping(value = "/failureList", method = RequestMethod.GET)
    public String failureList(ModelMap model) {

        //操作
        model.addAttribute("action", "failureList");

        return "admin/borrowing/failureList";
    }

    /**
     * 失败列表json
     * @param pageCriteria
     * @return request
     */
    @RequiresPermissions("borrowing:failureList")
    @RequestMapping(value="/failureList.json",method = RequestMethod.GET)
    @ResponseBody
    public Object listFailData(PageCriteria pageCriteria, HttpServletRequest request, BorrowingProgress progress) {
        PageResult<Borrowing> page = borrowingService.findBorrowingList(buildQueryCriteria(pageCriteria,request), progress, Boolean.TRUE);
        return getPageResult(page);
    }

    /**
     * 调查失败重置
     * @return request
     */
    @RequiresPermissions("borrowing:failureList")
    @RequestMapping(value="/reInquiry/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Result reInquiry(@PathVariable("id") Integer id, HttpServletRequest request) {
        //借款
        Borrowing borrowing = borrowingService.get(id);
        if(borrowing.getState().equals(BorrowingState.FAILURE) && borrowing.getProgress().equals(BorrowingProgress.INQUIRING)){
            borrowing.setState(BorrowingState.WAIT);
            borrowingService.merge(borrowing);
            return Result.success();
        }else {
            return Result.error("参数错误");
        }
    }
}
