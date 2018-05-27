/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.controller;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.FreemarkerUtils;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.SnUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.RSAService;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.dao.user.UserInfoDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.agreement.AgreementService;
import com.klzan.p2p.service.borrowing.BorrowingExtraService;
import com.klzan.p2p.service.borrowing.BorrowingOpinionService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.captcha.CaptchaService;
import com.klzan.p2p.service.coupon.CouponService;
import com.klzan.p2p.service.coupon.UserCouponService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.material.MaterialService;
import com.klzan.p2p.service.message.MessagePushService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.user.CorporationService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.coupon.CouponRule;
import com.klzan.p2p.vo.coupon.UserCouponVo;
import com.klzan.p2p.vo.investment.InvestVo;
import com.klzan.p2p.vo.investment.InvestmentRecordSimpleVo;
import com.klzan.plugin.pay.common.PageRequest;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.InvestmentRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import com.klzan.plugin.repayalgorithm.DateLength;
import com.klzan.plugin.repayalgorithm.RepayRecords;
import com.klzan.plugin.repayalgorithm.RepayRecordsStrategyHolder;
import freemarker.template.TemplateException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

import static com.klzan.plugin.repayalgorithm.DateUnit.DAY;
import static com.klzan.plugin.repayalgorithm.DateUnit.MONTH;

/**
 * Controller - 投资
 *
 * @author zhutao   2017/04/08
 */
@Controller("webInvestmentController")
@RequestMapping("/investment")
public class InvestmentController extends BaseController {

    /**
     * 索引重定向URL
     */
    private static final String INDEX_REDIRECT_URL = "redirect:/investment";

    /**
     * 模板路径
     */
    private static final String TEMPLATE_PATH = "/investment";

    /**
     * 期限
     */
    private static final PeriodScope[] periods = {
            PeriodScope.BETWEEN_1MONTH_AND_3MONTH,
            PeriodScope.BETWEEN_3MONTH_AND_6MONTH,
            PeriodScope.BETWEEN_3MONTH_AND_12MONTH,
            PeriodScope.BETWEEN_12MONTH_AND_24MONTH,
            PeriodScope.OVER_12MONTH};

    /**
     * 利率范围
     */
    private static final InterestRateScope[] interestRateScopes = {
            InterestRateScope.BETWEEN_0_AND_5,
            InterestRateScope.BETWEEN_5_AND_10,
            InterestRateScope.BETWEEN_10_AND_15,
            InterestRateScope.BETWEEN_15_AND_20,
            InterestRateScope.BETWEEN_20_AND_24};

    /**
     * 进度范围
     */
    private static final BorrowingProgress[] progressScopes = {BorrowingProgress.INVESTING, BorrowingProgress.REPAYING, BorrowingProgress.COMPLETED};

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
    private UserFinanceService userFinanceService;

    @Inject
    private CorporationService corporationService;

    @Inject
    private UserInfoDao userInfoDao;

    @Inject
    private CaptchaService captchaService;

    @Inject
    private RSAService rsaService;

    @Inject
    private PaymentOrderService paymentOrderService;

    @Inject
    private SettingUtils setting;

    @Inject
    private MaterialService materialService;

    @Inject
    private RepaymentService repaymentService;

    @Inject
    private RepaymentPlanService repaymentPlanService;

    @Inject
    private BorrowingOpinionService borrowingOpinionService;

    @Inject
    private MessagePushService messagePushService;

    @Inject
    private DataConvertService dataConvertService;

    @Resource
    private BusinessService businessService;

    @Resource
    private AgreementService agreementService;

    @Resource
    private UserCouponService userCouponService;
    @Resource
    private CouponService couponService;
    /**
     * 列表
     */
    @RequestMapping
    public String list(
            @RequestParam(value = "progre", required = false) BorrowingProgress progre,
            @RequestParam(value = "period", required = false) PeriodScope period,
            @RequestParam(value = "rate", required = false) InterestRateScope rate,
            @RequestParam(value = "type", required = false) String type,
            ModelMap model) {
        if (StringUtils.isNotBlank(type)) {
            model.addAttribute("type", type);
        }
        model.addAttribute("periods", periods);
        model.addAttribute("period", period);
        model.addAttribute("rates", interestRateScopes);
        model.addAttribute("rate", rate);
        model.addAttribute("progress", progressScopes);
        model.addAttribute("progre", progre);
        return TEMPLATE_PATH + "/list";
    }

    @RequestMapping("data")
    @ResponseBody
    public PageResult<Borrowing> data(BorrowingProgress progre, PeriodScope scope, InterestRateScope rate, PageCriteria criteria, HttpServletRequest request) {
//        PageCriteria pageCriteria = buildQueryCriteria(criteria, request);
//        String type = request.getParameter("type");
//        BorrowingType borrowingType = null;
//        try {
//            borrowingType = BorrowingType.valueOf(type);
//        } catch (IllegalArgumentException e) {
//            logger.error("借款类型转换错误");
//        }
        PageResult<Borrowing> page = borrowingService.findList(criteria, progre, scope, rate, null, false);
        return page;
    }

    @RequestMapping("records/{projectId}")
    @ResponseBody
    public PageResult<InvestmentRecordSimpleVo> investmentRecords(@PathVariable Integer projectId, Boolean isTransfer, PageCriteria criteria) {
        return investmentRecordService.findPage(projectId, criteria, isTransfer, InvestmentState.PAID, InvestmentState.SUCCESS);
    }

    /**
     * 查看
     */
    @RequestMapping(value = "/{projectId}")
    public String view(@PathVariable Integer projectId, ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

//		UserAgentUtils.getUserAgent(request);

        User currentUser = UserUtils.getCurrentUser();
        if (currentUser != null && currentUser.getId() != null) {
            currentUser = userService.get(currentUser.getId());
        }

        // 验证项目
        Borrowing pProject = borrowingService.get(projectId);
        if (pProject == null) {
            redirectAttributes.addFlashAttribute("result", Result.error("投资项目不存在"));
            return INDEX_REDIRECT_URL;
        }

        // 验证项目状态
        if (pProject.getProgress().equals(BorrowingProgress.INQUIRING) || pProject.getProgress().equals(BorrowingProgress.APPROVAL)) {
            redirectAttributes.addFlashAttribute("flashMessage", "拒绝访问");
            return INDEX_REDIRECT_URL;
        }

        Integer occupyCount = pProject.getOccupyCount();
        BigDecimal occupyAmount = pProject.getOccupyAmount();
        model.addAttribute("shortcutOccupyCount", occupyCount);
        model.addAttribute("occupyAmount", occupyAmount);

        // 生成密钥
        RSAPublicKey publicKey = rsaService.generateKey(request);
        model.addAttribute("project", pProject);
        if(!StringUtils.isBlank(pProject.getLabels())){
            model.addAttribute("labels", pProject.getLabels().split("，|,"));
        }
        User borrower = userService.get(pProject.getBorrower());
        model.addAttribute("borrower", borrower);
        model.addAttribute("borrowerInfo", userService.getUserById(pProject.getBorrower()));
        if (currentUser != null) {
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("currentUserFinance", userFinanceService.findByUserId(currentUser.getId()));
            List<UserCouponVo> userCoupon = userCouponService.findUserCoupon(currentUser.getId(),CouponState.UNUSED);
            List<UserCouponVo> avilebleUserCoupon = new ArrayList<>();
            //检查优惠券是否过期
            for (UserCouponVo vo : userCoupon){
//                Coupon coupon = couponService.get(vo.getCoupon());
//                if (!coupon.getAvailableByCategory()){
                if (vo.getUserInvalidDate().before(new Date())){
                    UserCoupon userCoupon1 = userCouponService.get(vo.getId());
                    userCoupon1.setCouponState(CouponState.OVERDUE);
                    userCouponService.merge(userCoupon1);
                }else {
                    avilebleUserCoupon.add(vo);
                }
//                }else {
//                    if (coupon.getInvalidDate().before(new Date())){
//                        UserCoupon userCoupon1 = userCouponService.get(vo.getId());
//                        userCoupon1.setCouponState(CouponState.OVERDUE);
//                        userCoupon1.setInvalidDate(coupon.getInvalidDate());
//                        userCouponService.merge(userCoupon1);
//                    }else {
//                        avilebleUserCoupon.add(vo);
//                    }
//                }

            }
            model.addAttribute("userCoupons",JsonUtils.toJson(avilebleUserCoupon));
            model.addAttribute("userCouponsSizen",avilebleUserCoupon.size());
        }

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
        List<Repayment> allRepayments = repaymentService.findByUser(pProject.getBorrower());
        Integer repaidPeriodCount = 0;
        Integer repayingPeriodCount = 0;
        Integer overduePeriodCount = 0;
        for (Repayment repayment : allRepayments) {
            if (repayment.getIsOverdue()) {
                overduePeriodCount++;
            }
            if (repayment.getState() == RepaymentState.REPAID) {
                repaidPeriodCount++;
            }
            if (repayment.getState() == RepaymentState.REPAYING) {
                repayingPeriodCount++;
            }
        }
        model.addAttribute("repaidPeriodCount", repaidPeriodCount);
        model.addAttribute("repayingPeriodCount", repayingPeriodCount);
        model.addAttribute("overduePeriodCount", overduePeriodCount);

		/*还款中*/
        if (pProject.getProgress().equals(BorrowingProgress.REPAYING)) {
            Integer period = 0; //待还期次
            Repayment nextRepayment = null;
            for (Repayment rep : repayments) {
                if (rep.getState().equals(RepaymentState.REPAYING) && (period == 0 || period.compareTo(rep.getPeriod()) > 0)) {
                    period = rep.getPeriod();
                    nextRepayment = rep;
                }
            }
            model.addAttribute("nextRepayment", nextRepayment);
        }
        /*已完成*/
        if (pProject.getProgress().equals(BorrowingProgress.COMPLETED)) {
            BigDecimal repaymentAmount = BigDecimal.ZERO;
            for (Repayment rep : repayments) {
                repaymentAmount = repaymentAmount.add(rep.getRepaymentAmount());
            }
            model.addAttribute("repaymentAmount", repaymentAmount);
        }

        // 项目上线日志
        List<BorrowingOpinionType> ignoreOpinionTypes = new ArrayList();

        for (BorrowingOpinion pOpinion : borrowingOpinionService.findList(pProject.getId())) {
            // 申请意见（最后一条）
            if (!ignoreOpinionTypes.contains(BorrowingOpinionType.APPLY)
                    && pOpinion.getType() == BorrowingOpinionType.APPLY) {
                ignoreOpinionTypes.add(BorrowingOpinionType.APPLY);
                model.addAttribute("applyOpinion", pOpinion);
            }
            // 调查意见（最后一条）
            if (!ignoreOpinionTypes.contains(BorrowingOpinionType.INQUIRY)
                    && pOpinion.getType() == BorrowingOpinionType.INQUIRY) {
                ignoreOpinionTypes.add(BorrowingOpinionType.INQUIRY);
                model.addAttribute("inquiryOpinion", pOpinion);
            }
            // 审批意见（最后一条）
            if (!ignoreOpinionTypes.contains(BorrowingOpinionType.APPROVAL)
                    && pOpinion.getType() == BorrowingOpinionType.APPROVAL) {
                ignoreOpinionTypes.add(BorrowingOpinionType.APPROVAL);
                model.addAttribute("confirmOpinion", pOpinion);
            }
            // 放款意见（最后一条）
            if (!ignoreOpinionTypes.contains(BorrowingOpinionType.LEND)
                    && pOpinion.getType() == BorrowingOpinionType.LEND) {
                ignoreOpinionTypes.add(BorrowingOpinionType.LEND);
                model.addAttribute("lendOpinion", pOpinion);
            }
            // 包含申请、调查、审批、放款意见时
            if (ignoreOpinionTypes.containsAll(Arrays.asList(
                    BorrowingOpinionType.APPLY,
                    BorrowingOpinionType.INQUIRY,
                    BorrowingOpinionType.APPROVAL,
                    BorrowingOpinionType.LEND))) {
                break;
            }
        }
        return TEMPLATE_PATH + "/detail";
    }

    /**
     * 投资
     */
    @RequestMapping(value = "/{projectId}/invest", method = RequestMethod.POST)
    public synchronized String invest(@PathVariable Integer projectId,
                                      InvestVo investVo, HttpServletRequest request, RedirectAttributes redirectAttributes, ModelMap model) {
        // 会员验证
        User currentUser = UserUtils.getCurrentUser();
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "请先登陆");
            return INDEX_REDIRECT_URL + "/{projectId}";
        }
        // 会员资金
        UserFinance investorFinance = userFinanceService.findByUserId(currentUser.getId());
        currentUser = userService.get(currentUser.getId());

        // 验证项目
        Borrowing pProject = borrowingService.get(projectId);
        if (pProject == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "项目不存在");
            return INDEX_REDIRECT_URL;
        }
        if (!pProject.verifyInvest()) {
            redirectAttributes.addFlashAttribute("flashMessage", "项目不可投");
            return INDEX_REDIRECT_URL + "/{projectId}";
        }
        // 验证是否为自己的项目
        if (currentUser.getId().equals(pProject.getBorrower())) {
            redirectAttributes.addFlashAttribute("flashMessage", "不可投资自己的项目");
            return INDEX_REDIRECT_URL + "/{projectId}";
        }
        // 验证是否为此项目担保人
        if (currentUser.equals(pProject.getGuaranteeCorp())) {
            redirectAttributes.addFlashAttribute("flashMessage", "不可投资自己担保的项目");
            return INDEX_REDIRECT_URL + "/{projectId}";
        }

        /** 投资验证 */
        BigDecimal investmentMinimum = pProject.getInvestmentMinimum();  // 最小可投
        BigDecimal surplusInvestmentAmount = pProject.getSurplusInvestmentAmount();  // 剩余可投
        BigDecimal investmentMaximum = pProject.getInvestmentMaximum(); // 最高可投
        investVo.setDeviceType(DeviceType.PC);
        switch (pProject.getInvestmentMethod()) {
            case MULTIPLE_OF_MINIMUM: { /** 最低投资倍数 */
                if (investVo.getAmount() == null) {
                    redirectAttributes.addFlashAttribute("flashMessage", "请输入投资金额");
                    return INDEX_REDIRECT_URL + "/{projectId}";
                }
                if ((investVo.getAmount().subtract(investmentMinimum)).intValue() % investmentMinimum.intValue() != 0) {
                    redirectAttributes.addFlashAttribute("flashMessage", "加价幅度需为" + investmentMinimum.intValue() + "的倍数");
                    return INDEX_REDIRECT_URL + "/{projectId}";
                }
                // 验证可投余额
                if (surplusInvestmentAmount.compareTo(investmentMinimum) == -1) {
                    if (!(investVo.getAmount().compareTo(surplusInvestmentAmount) == 0)) {
                        redirectAttributes.addFlashAttribute("flashMessage", "请输入正确的投资金额");
                        return INDEX_REDIRECT_URL + "/{projectId}";
                    }
                } else {
                    if (investVo.getAmount().compareTo(investmentMinimum) == -1) {
                        redirectAttributes.addFlashAttribute("flashMessage", "请输入正确的投资金额");
                        return INDEX_REDIRECT_URL + "/{projectId}";
                    }
                }
                if (investmentMaximum != null && investmentMaximum.compareTo(investVo.getAmount()) < 0) {
                    redirectAttributes.addFlashAttribute("flashMessage", "最大投资金额（￥" + investmentMaximum + "）");
                    return INDEX_REDIRECT_URL + "/{projectId}";
                }
                if (!pProject.verifyInvest(investVo.getAmount())) {
                    redirectAttributes.addFlashAttribute("flashMessage", "投资金额有误");
                    return INDEX_REDIRECT_URL + "/{projectId}";
                }
                break;
            }
            default: {
                redirectAttributes.addFlashAttribute("flashMessage", "系统错误");
                return INDEX_REDIRECT_URL + "/{projectId}";
            }
        }
        //验证优惠券
        UserCoupon userCoupon = null;
        if (pProject.getSurportCoupon() && investVo.getCoupon()!=null){
            //1.获取用户优惠券
            userCoupon = userCouponService.get(new Integer(investVo.getCoupon()));
            //2.获取用户优惠券规则并校验
            String rule = userCoupon.getRule();
            CouponRule couponRule = JsonUtils.toObject(rule, CouponRule.class);
            //①过期
            if (new Date().after(userCoupon.getInvalidDate())){
                userCoupon.setCouponState(CouponState.OVERDUE);
                userCouponService.merge(userCoupon);
                redirectAttributes.addFlashAttribute("flashMessage", "优惠券已过期");
                return INDEX_REDIRECT_URL + "/{projectId}";
            }
            //②最小投资额
            if (investVo.getAmount().compareTo(couponRule.getBeginAmount())<0){
                redirectAttributes.addFlashAttribute("flashMessage", "该优惠券至少需要投资"+couponRule.getBeginAmount()+"元方可使用");
                return INDEX_REDIRECT_URL + "/{projectId}";
            }
            //③标的类型
//            if (pProject.getPeriodUnit() != couponRule.getPeriodUnit()){
//                redirectAttributes.addFlashAttribute("flashMessage", "该优惠券不能在期限单位为:"+pProject.getPeriodUnitDes()+"的标使用");
//                return INDEX_REDIRECT_URL + "/{projectId}";
//            }
            //④起投期限
//            if (pProject.getPeriod().intValue() <couponRule.getBeginPeriod().intValue()){
//                redirectAttributes.addFlashAttribute("flashMessage", "该优惠券只能在期限大于"+couponRule.getBeginPeriod()+pProject.getPeriodUnitDes()+"的标使用");
//                return INDEX_REDIRECT_URL + "/{projectId}";
//            }
        }
        try {
//            // 投资人余额校验
//            if (investVo.getAmount().compareTo(investorFinance.getBalance().subtract(investorFinance.getFrozen())) > 0) {
//                redirectAttributes.addFlashAttribute("flashMessage", "账户余额不足");
//                return INDEX_REDIRECT_URL + "/{projectId}";
//            }
            String sn = SnUtils.getOrderNo();
            // 完善业务参数
            investVo.setInvestor(currentUser.getId());
            investVo.setOperationMethod(OperationMethod.MANUAL);
            investVo.setPaymentMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
            investVo.setSn(sn);
            if (pProject.getSurportCoupon() && investVo.getCoupon()!=null){
                redirectAttributes.addFlashAttribute("flashMessage", "暂不支持优惠券");
                return INDEX_REDIRECT_URL + "/{projectId}";
            }else {
                PayModule payModule = PayPortal.invest.getModuleInstance();
                InvestmentRequest req = new InvestmentRequest(false, UserUtils.getCurrentUser().getId(), investVo.getProjectId(),
                        investVo.getAmount(), false, 0, 0, investVo.getPaymentMethod(), DeviceType.PC);
                payModule.setRequest(req);
                PageRequest param = payModule.invoking().getPageRequest();
                model.addAttribute("requestUrl", param.getRequestUrl());
                model.addAttribute("parameterMap", param.getParameterMap());
                return "payment/submit";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("flashMessage", "投资失败");
            e.printStackTrace();
        }
        return INDEX_REDIRECT_URL + "/{projectId}";
    }

    /**
     * 协议
     */
    @RequestMapping("/agreement/{projectId}")
    public String agreement(@PathVariable Integer projectId,ModelMap modelMap) {
        // 验证项目
        Borrowing pProject = borrowingService.get(projectId);
        if (pProject!=null){
            Agreement agreement = agreementService.get(pProject.getAgreementId());
            Map map=new HashMap();
            try {
                modelMap.addAttribute("content",
                        FreemarkerUtils.process(
                                agreement!=null?agreement.getContent():agreementService.findAll().get(0).getContent(),map
                        )
                );
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }else {
            modelMap.addAttribute("content",agreementService.findAll().get(0).getContent());
        }
        return "/agreement/agreement";
    }

    /**
     * 验证支付密码
     */
    @RequestMapping(value = "/verify_payPassword", method = RequestMethod.GET)
    public @ResponseBody
    Boolean verify_payPassword(String password) {
        User currentUser = UserUtils.getCurrentUser();
        if (!currentUser.verifyPayPassword(password)) {
            return false;
        }
        return true;
    }

    /**
     * 验证验证码
     */
    @RequestMapping(value = "/verify_captcha", method = RequestMethod.GET)
    public @ResponseBody
    Boolean verify_captcha(String captcha, HttpServletRequest request) {
        if (!captchaService.verify(com.klzan.p2p.enums.CaptchaType.INVESTMENT, captcha, request.getSession())) {
            return false;
        }
        return true;
    }

    /**
     * 理财计算器
     */

    @RequestMapping("calculator")
    public String calculator(ModelMap model) {
        model.addAttribute("periodUnits", PeriodUnit.values());
        RepaymentMethod[] interestMethodAsDay = new RepaymentMethod[]{RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST};
        RepaymentMethod[] interestMethodAsMonth = new RepaymentMethod[]{RepaymentMethod.EACH_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL, RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST, RepaymentMethod.EACH_PERIOD_AVG_CAPITAL_PLUS_INTEREST};
        model.addAttribute("interestMethodAsDay", interestMethodAsDay);
        model.addAttribute("interestMethodAsMonth", interestMethodAsMonth);
        model.addAttribute("repaymentMethods", interestMethodAsMonth);
        model.addAttribute("recoveryMethods", interestMethodAsMonth);
        return TEMPLATE_PATH + "/calculator";
    }

    @RequestMapping("calculator/result")
    @ResponseBody
    public Map<String, Object> calculator(BigDecimal amount, BigDecimal rate, RepaymentMethod repaymentMethod, Integer borrowingPeriod, PeriodUnit periodUnit) {
        Map<String, Object> map = new HashMap<String, Object>();
        BigDecimal sum_invests = new BigDecimal(0);
        DateLength dateLength = new DateLength(borrowingPeriod, MONTH, InterestMethod.T_PLUS_ZERO);
        if (periodUnit.name().equals("MONTH")) {
            dateLength = new DateLength(borrowingPeriod, MONTH, InterestMethod.T_PLUS_ZERO);
        } else if (periodUnit.name().equals("DAY")) {
            dateLength = new DateLength(borrowingPeriod, DAY, InterestMethod.T_PLUS_ONE);
        }
        RepayRecords repayRecords = RepayRecordsStrategyHolder.instanse().generateRepayRecords(repaymentMethod, amount, rate, dateLength);
        List<RepaymentRecord> repaymentPlans = repayRecords.getRepaymentPlans();
        for (RepaymentRecord record : repaymentPlans) {
            sum_invests = record.getInterest().add(sum_invests);
        }
        map.put("sum_invests_capital", sum_invests.add(amount));
        map.put("sum_invests", sum_invests);
        map.put("records", repaymentPlans);
        return map;
    }
}





