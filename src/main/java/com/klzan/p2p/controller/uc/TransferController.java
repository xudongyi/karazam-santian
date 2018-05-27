package com.klzan.p2p.controller.uc;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.FreemarkerUtils;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.bind.annotation.CurrentUser;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.common.view.PdfView;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.agreement.AgreementService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.capital.AccountantService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.service.user.CorporationService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.setting.TransferSetting;
import com.klzan.p2p.util.AccountantUtils;
import com.klzan.p2p.vo.transfer.TransferVo;
import com.klzan.p2p.vo.user.UserVo;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 债权转让
 * Created by suhao Date: 2017/5/14 Time: 14:21
 *
 * @version: 1.0
 */
@Controller("ucTransferController")
@RequestMapping("uc/transfer")
public class TransferController extends BaseController {
    @Inject
    private TransferService transferService;

    @Inject
    private BorrowingService borrowingService;

    @Inject
    private RepaymentService repaymentService;

    @Inject
    private RepaymentPlanService repaymentPlanService;

    @Inject
    private AccountantService accountantService;

    @Inject
    private InvestmentService investmentService;

    @Inject
    private UserService userService;

    @Inject
    private CorporationService corporationService;

    @Inject
    private DataConvertService dataConvertService;

    @Resource
    private AgreementService agreementService;

    @Resource
    private SettingUtils settingUtils;

    @RequestMapping(value = {"", "index"})
    public String index(@CurrentUser User currentUser, RedirectAttributes redirectAttributes) {
        TransferSetting transferSetting = settingUtils.getTransferSetting();
        if (null != transferSetting) {
            if (!transferSetting.getTransferEnable()) {
                redirectAttributes.addFlashAttribute("flashMessage", "债权转让功能不可用");
                return "redirect:/uc";
            }
        }
        return "uc/transfer/index";
    }

    /**
     * 我的转让
     *
     * @param currentUser
     * @return
     */
    @RequestMapping("list")
    public String list(@CurrentUser User currentUser, RedirectAttributes redirectAttributes) {
        TransferSetting transferSetting = settingUtils.getTransferSetting();
        if (null != transferSetting) {
            if (!transferSetting.getTransferEnable()) {
                redirectAttributes.addFlashAttribute("flashMessage", "债权转让功能不可用");
                return "redirect:/uc";
            }
        }
        return "uc/transfer/list";
    }

    @RequestMapping("list/data")
    @ResponseBody
    public PageResult<Transfer> listdata(@CurrentUser User currentUser, TransferLoanState state, PageCriteria criteria, HttpServletRequest request) {
        return transferService.findPage(criteria, state, currentUser.getId());
    }

    @RequestMapping("data")
    @ResponseBody
    public PageResult<TransferVo> data(@CurrentUser User currentUser, @RequestParam(defaultValue = "false") Boolean searchBuyIn, PageCriteria criteria) {
        if (searchBuyIn) {
            return transferService.findBuyInPageVo(criteria, currentUser.getId());
        }
        return transferService.findPageVo(criteria, currentUser.getId());
    }

    /**
     * 可转让列表弹窗
     */
    @RequestMapping(value = "transfer.json", method = RequestMethod.POST)
    @ResponseBody
    public Result getTransfer(@CurrentUser User currentUser, @RequestParam Integer id) {

        if (currentUser == null || id == null) {
            return Result.error("参数错误");
        }
        Borrowing borrowing = borrowingService.get(id);
        if (borrowing == null) {
            return Result.error("参数错误");
        }

        TransferSetting transferSetting = settingUtils.getTransferSetting();
        if (null != transferSetting) {
            if (!transferSetting.getTransferEnable()) {
                return Result.error("债权转让功能不可用");
            }
        }

        Date nextRepaymentDate = null;
        int surplusPeriod = 0;
        BigDecimal totalOldCapital = BigDecimal.ZERO;  //原始本金
        BigDecimal repaidInterest = BigDecimal.ZERO;  //已收利息(计划)
        BigDecimal repayingInterest = BigDecimal.ZERO;  //未收利息（剩余利息）
        BigDecimal totalCurrentClaimTotalPrice = BigDecimal.ZERO;  //债权价格（剩余本金）
        BigDecimal totalCurrentClaimTotalValue = BigDecimal.ZERO;  //债权价值
        Boolean canTransfer = true;
        List<RepaymentPlan> repaymentPlans = repaymentPlanService.findListCanTransfer(id, currentUser.getId());//currentUser.getId());
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            totalOldCapital = totalOldCapital.add(repaymentPlan.getCapital());
            if (repaymentPlan.getState().equals(RepaymentState.REPAID)) {
                repaidInterest = repaidInterest.add(repaymentPlan.getInterest());
                continue;
            }
            if (nextRepaymentDate == null || nextRepaymentDate.after(repaymentPlan.getRepaymentRecordPayDate())) {
                nextRepaymentDate = repaymentPlan.getRepaymentRecordPayDate();
            }
            surplusPeriod++;
            repayingInterest = repayingInterest.add(repaymentPlan.getInterest());
            totalCurrentClaimTotalValue = totalCurrentClaimTotalValue.add(accountantService.calCurrentSurplusValue(repaymentPlan, null).getTotalValue());
            totalCurrentClaimTotalPrice = totalCurrentClaimTotalPrice.add(repaymentPlan.getCapital());
            if (repaymentPlan.getIsOverdue()) {
                canTransfer = false;
            }
        }
        if (!borrowing.getProgress().equals(BorrowingProgress.REPAYING)) {
            canTransfer = false;
        }
        if (!canTransfer) {
            return Result.error("已逾期，不能转让");
        }

        Map<String, Object> messages = new HashMap<String, Object>();
//        //回收ID
//        messages.put("id", recovery.getId());
        //转出服务费
        messages.put("fee", AccountantUtils.calFee(totalCurrentClaimTotalPrice, borrowing.getOutTransferFeeRate()));  //转出服务费 = 债权价格（剩余本金） * 转出方服务费率
        //借款ID
        messages.put("borrowingId", borrowing.getId());
        //原始投资额
        messages.put("yuanAmount", totalOldCapital);
        //已收利息
        messages.put("revoveryedInterest", repaidInterest);
        //债权价值
        messages.put("totalValue", totalCurrentClaimTotalValue);
        //债权价格
        messages.put("totalPrice", totalCurrentClaimTotalPrice);
        //待收本金
        messages.put("daishoubenjin", totalCurrentClaimTotalPrice);
        //待收利息
        messages.put("daishoulixi", repayingInterest);
        messages.put("interest", borrowing.getRealInterestRate());  //借款利率
        messages.put("lowPeriod", surplusPeriod + "/" + borrowing.getRepayPeriod()); //剩余期数
        messages.put("nextRepaymentDate", nextRepaymentDate);
        messages.put("repaymentMethodDes", borrowing.getRepaymentMethodDes());

//        //预计下次还款收入金额
//        messages.put("yujixiaci", recovery.getLastAmount());

        return Result.success(JsonUtils.toJson(messages));
    }

    /**
     * 转让
     */
    @RequestMapping(value = "trans", method = RequestMethod.POST)
    public String trans(@CurrentUser User currentUser, @RequestParam Integer borrowingId, RedirectAttributes redirectAttributes) {

        if (currentUser == null || borrowingId == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "参数错误");
            return "redirect:/uc/transfer/index";
        }
        Borrowing borrowing = borrowingService.get(borrowingId);
        if (borrowing == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "参数错误");
            return "redirect:/uc/transfer/index";
        }

        TransferSetting transferSetting = settingUtils.getTransferSetting();
        if (null != transferSetting) {
            if (!transferSetting.getTransferEnable()) {
                redirectAttributes.addFlashAttribute("flashMessage", "债权转让功能不可用");
                return "redirect:/uc";
            }
        }

        Integer forzenDays = 1;
        Integer purchaseDays = 1;
        if(transferSetting !=null ){
            forzenDays = transferSetting.getForzenDays();
            purchaseDays = transferSetting.getPurchaseDays();
        }

        if(new Date().compareTo(DateUtils.addDays(borrowing.getLendingDate(), forzenDays)) < 0){
            redirectAttributes.addFlashAttribute("flashMessage", "持有债权24小时后才能转让");
            return "redirect:/uc/transfer/index";
        }

        // 验证转让
        List<Transfer> transfers = transferService.findList(borrowingId, currentUser.getId());
        if (transfers != null && transfers.size() > 0) {
            redirectAttributes.addFlashAttribute("flashMessage", "已在转让中");
            return "redirect:/uc/transfer/index";
        }
        for (Transfer transfer : transfers) {
            if(new Date().compareTo(DateUtils.addDays(transfer.getCreateDate(), purchaseDays)) < 0){
                redirectAttributes.addFlashAttribute("flashMessage", "24小时内只能进行一次转让");
                return "redirect:/uc/transfer/index";
            }
        }

        //还款类型判断
        if (!borrowing.getRepaymentMethod().equals(RepaymentMethod.EACH_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL)
                && !borrowing.getRepaymentMethod().equals(RepaymentMethod.FIXED_PERIOD_INTEREST_AND_LAST_PERIOD_PLUS_CAPITAL)
                && !borrowing.getRepaymentMethod().equals(RepaymentMethod.LAST_PERIOD_CAPITAL_PLUS_INTEREST)) {
            redirectAttributes.addFlashAttribute("flashMessage", "‘每月付息、到期还本’或者‘到期还本付息’的借款才能转让");
            return "redirect:/uc/transfer/index";
        }

        //逾期判断
        List<Repayment> repayments = repaymentService.findList(borrowingId);
        for (Repayment repayment : repayments) {
            if (repayment.getState().equals(RepaymentState.REPAID)) {
                continue;
            }
            if (repayment.getIsOverdue()) {
                redirectAttributes.addFlashAttribute("flashMessage", "逾期不能转让");
                return "uc/transfer/index";
            }
        }

        try {
            transferService.transferOut(borrowing, currentUser);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("flashMessage", e.getMessage() == null ? "转出失败" : e.getMessage());
            return "redirect:/uc/transfer/index";
        }
        redirectAttributes.addFlashAttribute("flashMessage", "转出成功");
        return "redirect:/uc/transfer/index";
    }

    /**
     * 转让撤销
     */
    @RequestMapping(value = "/transferCancel", method = RequestMethod.POST)
    @ResponseBody
    public Result transferCancel(@CurrentUser User currentUser, @RequestParam Integer id) {

        if (currentUser == null || id == null) {
            return Result.error("参数错误");
        }

        // 验证转让
        List<Transfer> transfers = transferService.findList(id, currentUser.getId());
        if (transfers == null || transfers.size() == 0) {
            return Result.error("转让不存在");
        }
        Transfer transfer = transfers.get(0);

        // 验证项目
        Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
        if (borrowing == null) {
            return Result.error("投资项目不存在");
        }

        try {
            transferService.transferCancel(transfer);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success("撤销失败");
        }

        return Result.success("撤销成功");
    }

    /**
     *
     * 协议
     */
    @RequestMapping("agreement/{borrowingId}")
    public String agreement(@CurrentUser User currentUser,@PathVariable Integer borrowingId,ModelMap model,RedirectAttributes redirectAttributes) {
        // 验证项目
        Borrowing borrowing = borrowingService.get(borrowingId);
        if (borrowing == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "借款不存在");
            return "redirect:/uc/transfer";
        }

        //获取转让协议
        Agreement agreement = agreementService.get(borrowing.getTransferAgreementId());
        String agreementContent = agreement.getContent();

        try {
            model.addAttribute("content", FreemarkerUtils.process(agreementContent,new HashMap<>()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return "/agreement/agreement";
    }

    /**
     * 受让人协议
     */
    @RequestMapping(value = "/transferin/agreement/{id}/{investId}", method = RequestMethod.GET)
    public String transferinagreement(@CurrentUser User currentUser,String type, @PathVariable Integer id,@PathVariable Integer  investId,ModelMap model, RedirectAttributes redirectAttributes) {

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
        List<RepaymentPlan> repaymentPlans = repaymentPlanService.findList(borrowing.getId(), investment.getInvestor(),investment.getId());//currentUser.getId());
        String planRepaymentStr="";
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            repayingInterest = repayingInterest.add(repaymentPlan.getInterest());
            totalCurrentClaimTotalPrice = totalCurrentClaimTotalPrice.add(repaymentPlan.getCapital());
            planRepaymentStr+="【"+DateUtils.format(repaymentPlan.getRepaymentRecordPayDate())+"第"+repaymentPlan.getRepaymentRecordPeriod()+"期】;";
        }

        String agreementContent = null;
        Map map = new HashMap();
        if ("IN".equals(type)){
            Agreement agreement = agreementService.get(borrowing.getInvestTransferAgreementId());
            agreementContent = agreement.getContent();
            //借款人
            map.put("borrowingRealName",borrowingUser.getRealName());
            map.put("borrowingIdNo",borrowingUser.getIdNo());
            map.put("borrowingType",borrowingUser.getType());
            map.put("borrowingCorpName",borrowingUser.getCorpName());
            map.put("borrowingCorpLicenseNo",borrowingUser.getCorpLicenseNo());
            map.put("repaymentMethod",borrowing.getRepaymentMethodDes());
            map.put("contractName",borrowing.getTitle());
            map.put("period",borrowing.getPeriod());
            map.put("planRepaymentStr",planRepaymentStr);
            map.put("borrowingAmount",borrowing.getAmount());
            map.put("outFee",transfer.getOutFee());
            //转让人
            map.put("transferRealName",transferUser.getRealName());
            map.put("transferCorpName",transferUser.getCorpName());
            //受让人
            map.put("investRealName",investUser.getRealName());
            map.put("investIdNo",investUser.getIdNo());
            map.put("investType",investUser.getType());
            map.put("investCorpName",investUser.getCorpName());
            map.put("investCorpLicenseNo",investUser.getCorpLicenseNo());

            UserVo userTransfer= userService.getUserById(transfer.getTransfer());
            if (userTransfer.getType().equals(UserType.GENERAL)){
                map.put("userIdNoTransfer",userTransfer.getIdNo());
                map.put("userNameTransfer",userTransfer.getRealName());
            }else {
                map.put("userIdNoTransfer",userTransfer.getCorpLicenseNo());
                map.put("userNameTransfer",userTransfer.getCorpName());
            }
            map.put("userTransferType",userTransfer.getType());
            map.put("transferId",userTransfer.getId());
            map.put("transferPrice",investment.getAmount());
            //剩余利息,剩余本金
            map.put("repayingInterest",repayingInterest);
            map.put("totalCurrentClaimTotalPrice",totalCurrentClaimTotalPrice);
            map.put("interestRate",borrowing.getRealInterestRate());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            //合同编号
            String dataStr = sdf.format(investment.getCreateDate());
            map.put("contractNo","STYH-"+dataStr+"-"+borrowing.getId());


            //--------- 设置剩余期限及单位----------
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

            map.put("surplusPeriod",residualPeriod);
            map.put("periodUnit",borrowing.getPeriodUnitDes());
            //签约日期
            Date date = investment.getCreateDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String nowDate = simpleDateFormat.format(date);
            map.put("year",nowDate.substring(0,4));
            map.put("mouth",nowDate.substring(4,6));
            map.put("day",nowDate.substring(6,8));
        }
        try {
            model.addAttribute("content",FreemarkerUtils.process(agreementContent,map ));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return "/agreement/agreement";
    }
    /**
     * 转让人转让协议列表
     */
    @RequestMapping(value = "/transferin/agreement/list/{id}", method = RequestMethod.GET)
    public String findTransferdAgreementList(@CurrentUser User currentUser,@PathVariable Integer id,ModelMap modelMap) {

        List<TransferVo> transferInvestments = transferService.findTransferInvestment(currentUser.getId(),id);
        modelMap.put("transferInvestments",transferInvestments);
        return "/uc/transfer/transfered_agreement_list";
    }
    /**
     * 查看转让人转让协议
     */
    @RequestMapping(value = "/transferin/agreement/detail/{id}", method = RequestMethod.GET)
    public String findAgreementDetail(@CurrentUser User currentUser,@PathVariable Integer id,RedirectAttributes redirectAttributes,ModelMap modelMap) {

        Investment investment = investmentService.get(id);

        Transfer transfer = transferService.get(investment.getTransfer());
        Borrowing borrowing = borrowingService.get(investment.getBorrowing());
        borrowing= dataConvertService.convertBorrowing(borrowing);
        BigDecimal totalCurrentClaimTotalPrice = BigDecimal.ZERO;
        BigDecimal repayingInterest = BigDecimal.ZERO;
        List<RepaymentPlan> list = repaymentPlanService.findList(borrowing.getId(), investment.getInvestor(), investment.getId());
        for (RepaymentPlan repaymentPlan:list){
            totalCurrentClaimTotalPrice = totalCurrentClaimTotalPrice.add(repaymentPlan.getCapital());
            repayingInterest = repayingInterest.add(repaymentPlan.getInterest());
        }
        Integer transferAtreementId = borrowing.getTransferAgreementId();
        UserVo borrowingUser = userService.getUserById(borrowing.getBorrower());
        UserVo tansferUser = userService.getUserById(currentUser.getId());
        UserVo investUser = userService.getUserById(investment.getInvestor());
        Agreement agreement = agreementService.get(transferAtreementId);
        String content = agreement.getContent();
        Map map = new HashMap();


        map.put("contractName",borrowing.getTitle());
        map.put("currentUser",currentUser);
        map.put("borrowingAmount",borrowing.getAmount());
        map.put("repaymentMethod",borrowing.getRepaymentMethodDes());
        map.put("period",borrowing.getPeriod());
        map.put("transferPrice",investment.getAmount());
        map.put("outFee",transfer.getOutFee());
        UserVo userTransfer= userService.getUserById(transfer.getTransfer());
        if (userTransfer.getType().equals(UserType.GENERAL)){
            map.put("userIdNoTransfer",userTransfer.getIdNo());
            map.put("userNameTransfer",userTransfer.getRealName());
        }else {
            map.put("userIdNoTransfer",userTransfer.getCorpLicenseNo());
            map.put("userNameTransfer",userTransfer.getCorpName());
        }
        map.put("userTransferType",userTransfer.getType());
        map.put("transferId",userTransfer.getId());
        map.put("investId",investUser.getId());
        map.put("transferId",userTransfer.getId());
        map.put("borrowingIdNo",borrowingUser.getIdNo());
        map.put("borrowingRealName",borrowingUser.getRealName());
        map.put("borrowingType",borrowingUser.getType());
        map.put("borrowingCorpLicenseNo",borrowingUser.getCorpLicenseNo());
        map.put("borrowingCorpName",borrowingUser.getCorpName());
        map.put("transferRealName",tansferUser.getRealName());
        map.put("transferType",tansferUser.getType());
        map.put("transferCorpName",tansferUser.getCorpName());
        map.put("transferCorpLicenseNo",tansferUser.getCorpLicenseNo());
        map.put("investType",investUser.getType());
        map.put("investRealName",investUser.getRealName());
        map.put("investIdNo",investUser.getIdNo());
        map.put("investCorpName",investUser.getCorpName());
        map.put("investCorpLicenseNo",investUser.getCorpLicenseNo());
        map.put("totalCurrentClaimTotalPrice",totalCurrentClaimTotalPrice);
        map.put("repayingInterest",repayingInterest);
        map.put("interestRate",borrowing.getRealInterestRate());
        if (borrowing.getPeriodUnit()==PeriodUnit.MONTH) {
            map.put("surplusPeriod", transfer.getSurplusPeriod().substring(0, transfer.getSurplusPeriod().indexOf("/")));
        }else{
            Integer residualPeriod=0;
            if (borrowing.getRepaymentFinishDate()!=null){
                //当还款完成时
                residualPeriod=new Double(DateUtils.getDaysOfTwoDate(borrowing.getRepaymentFinishDate(),investment.getCreateDate())).intValue();
            }else{
                residualPeriod=new Double(DateUtils.getDaysOfTwoDate(borrowing.getFinalPayDate(),investment.getCreateDate())).intValue();
            }
            map.put("surplusPeriod",residualPeriod);
        }
        map.put("periodUnit",borrowing.getPeriodUnit().getDisplayName());
        //合同编号
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dataStr = sdf.format(investment.getCreateDate());
        map.put("contractNo","STYH-"+dataStr+"-"+borrowing.getId());

        //签约日期
        Date date = investment.getCreateDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String nowDate = simpleDateFormat.format(date);
        map.put("year",nowDate.substring(0,4));
        map.put("mouth",nowDate.substring(4,6));
        map.put("day",nowDate.substring(6,8));
        try {
            modelMap.addAttribute("content",FreemarkerUtils.process(content,map ));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return "/agreement/agreement";
    }
    /**
     * 转让协议下载协议
     */
    @RequestMapping("/transferin/agreement/{id}/download")
    public ModelAndView transferinagreementDownload(@CurrentUser User currentUser, @PathVariable Integer id, RedirectAttributes redirectAttributes) throws IOException, TemplateException {

        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "未登录");
            return new ModelAndView(ERROR_VIEW);
        }

        // 验证转让
        Transfer transfer = transferService.get(id);
        if (transfer == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "转让不存在");
            return new ModelAndView(ERROR_VIEW);
        }
        // 验证项目
        Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
        if (borrowing == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "借款不存在");
            return new ModelAndView(ERROR_VIEW);
        }
        if (!transfer.getTransfer().equals(currentUser.getId())) {
            redirectAttributes.addFlashAttribute("flashMessage", "拒绝访问");
            return new ModelAndView(ERROR_VIEW);
        }

        // 生成HTML协议
        String agreementPath = "/agreement/borrowing/transfering_agreement.ftl";
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("borrowing", borrowing); //借款
        model.put("transfer", transfer); //转让
        model.put("currentUser", currentUser); //当前用户

        User borrower = userService.get(transfer.getTransfer());
        model.put("borrower", borrower); //转让人
        model.put("borrowerInfo", userService.getUserById(transfer.getTransfer())); //转让人信息
        if (borrower != null && borrower.getType() == UserType.ENTERPRISE) {
            model.put("borrowerCorporation", corporationService.findCorporationByUserId(transfer.getTransfer())); //转让人（企业）
        }

        model.put("investments", dataConvertService.convertInvestments(investmentService.findListByTransfer(transfer.getId())));     /*投资列表*/

        List<Repayment> repayments = repaymentService.findList(transfer.getBorrowing());
        model.put("repayments", repayments);   /*还款列表 */

        String agreementHtml = FreemarkerUtils.process(agreementPath, model);
        // HTML协议转换为PDF协议视图
        String filename = new SimpleDateFormat("yyyyMMdd").format(borrowing.getLendingDate()) + "-" + borrowing.getId() + ".pdf";
        PdfView agreementPdf = new PdfView(filename, agreementHtml);
        // 下载
        return new ModelAndView(agreementPdf);
    }

    /**
     * 受让协议
     */
    @RequestMapping(value = "/transferout/agreement/{id}", method = RequestMethod.GET)
    public String transferoutagreement(@CurrentUser User currentUser, @PathVariable Integer id, ModelMap model, RedirectAttributes redirectAttributes) {

        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "未登录");
            return "redirect:/uc/transfer";
        }

        // 验证投资
        Investment investment = investmentService.get(id);
        if (investment == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "投资不存在");
            return "redirect:/uc/transfer";
        }

        // 验证转让
        if (investment.getTransfer() == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "转让不存在");
            return "redirect:/uc/transfer";
        }
        Transfer transfer = transferService.get(investment.getTransfer());
        if (transfer == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "转让不存在");
            return "redirect:/uc/transfer";
        }
        // 验证项目
        Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
        if (borrowing == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "借款不存在");
            return "redirect:/uc/transfer";
        }
        if (!investment.getInvestor().equals(currentUser.getId())) {
            redirectAttributes.addFlashAttribute("flashMessage", "拒绝访问");
            return "redirect:/uc/transfer";
        }
        model.addAttribute("transferPrice",investment.getAmount());
        model.addAttribute("borrowing", borrowing); //借款
        model.addAttribute("transfer", transfer); //转让
        model.addAttribute("currentUser", currentUser); //当前用户

        User borrower = userService.get(transfer.getTransfer());
        model.addAttribute("borrower", borrower); //转让人
        model.addAttribute("borrowerInfo", userService.getUserById(transfer.getTransfer())); //转让人信息
        if (borrower != null && borrower.getType() == UserType.ENTERPRISE) {
            model.addAttribute("borrowerCorporation", corporationService.findCorporationByUserId(transfer.getTransfer())); //转让人（企业）
        }
        UserVo userTransfer= userService.getUserById(transfer.getTransfer());
        if (userTransfer.getType().equals(UserType.GENERAL)){
            model.put("userIdNoTransfer",userTransfer.getIdNo());
            model.put("userNameTransfer",userTransfer.getRealName());
        }else {
            model.put("userIdNoTransfer",userTransfer.getCorpLicenseNo());
            model.put("userNameTransfer",userTransfer.getCorpName());
        }
        model.put("userTransferType",userTransfer.getType());
        model.put("transferId",userTransfer.getId());
        User invstor = userService.get(currentUser.getId());
        model.addAttribute("invstor", invstor); //转让人
        model.addAttribute("invstorInfo", userService.getUserById(currentUser.getId())); //转让人信息
        if (invstor != null && invstor.getType() == UserType.ENTERPRISE) {
            model.addAttribute("invstorCorporation", corporationService.findCorporationByUserId(currentUser.getId())); //转让人（企业）
        }
        Agreement agreement = agreementService.get(borrowing.getAgreementId());
//        List<Investment> investments = dataConvertService.convertInvestments(investmentService.findListByTransfer(transfer.getId()))
//        model.addAttribute("investments", investments);     /*投资列表*/
        model.addAttribute("investment", dataConvertService.convertInvestment(investment));     /*投资*/

        List<Repayment> repayments = repaymentService.findList(transfer.getBorrowing());
        model.addAttribute("repayments", repayments);   /*还款列表 */

        return "/agreement/agreement";
    }

    /**
     * 受让协议下载
     */
    @RequestMapping("/transferout/agreement/{investmentId}/download")
    public ModelAndView agreementDownload(@CurrentUser User currentUser, @PathVariable Integer investmentId, RedirectAttributes redirectAttributes) throws IOException, TemplateException {

        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "未登录");
            return new ModelAndView(ERROR_VIEW);
        }

        // 验证投资
        Investment investment = investmentService.get(investmentId);
        if (investment == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "投资不存在");
            return new ModelAndView(ERROR_VIEW);
        }

        // 验证转让
        if (investment.getTransfer() == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "转让不存在");
            return new ModelAndView(ERROR_VIEW);
        }
        Transfer transfer = transferService.get(investment.getTransfer());
        if (transfer == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "转让不存在");
            return new ModelAndView(ERROR_VIEW);
        }
        // 验证项目
        Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
        if (borrowing == null) {
            redirectAttributes.addFlashAttribute("flashMessage", "借款不存在");
            return new ModelAndView(ERROR_VIEW);
        }
        if (!investment.getInvestor().equals(currentUser.getId())) {
            redirectAttributes.addFlashAttribute("flashMessage", "拒绝访问");
            return new ModelAndView(ERROR_VIEW);
        }

        // 生成HTML协议
        String agreementPath = "/agreement/invest/transfering_agreement.ftl";
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("borrowing", borrowing); //借款
        model.put("transfer", transfer); //转让
        model.put("currentUser", currentUser); //当前用户

        User borrower = userService.get(transfer.getTransfer());
        model.put("borrower", borrower); //转让人
        model.put("borrowerInfo", userService.getUserById(transfer.getTransfer())); //转让人信息
        if (borrower != null && borrower.getType() == UserType.ENTERPRISE) {
            model.put("borrowerCorporation", corporationService.findCorporationByUserId(transfer.getTransfer())); //转让人（企业）
        }
        UserVo userTransfer= userService.getUserById(transfer.getTransfer());
        if (userTransfer.getType().equals(UserType.GENERAL)){
            model.put("userIdNoTransfer",userTransfer.getIdNo());
            model.put("userNameTransfer",userTransfer.getRealName());
        }else {
            model.put("userIdNoTransfer",userTransfer.getCorpLicenseNo());
            model.put("userNameTransfer",userTransfer.getCorpName());
        }
        model.put("userTransferType",userTransfer.getType());
        model.put("transferId",userTransfer.getId());
        User invstor = userService.get(currentUser.getId());
        model.put("invstor", invstor); //转让人
        model.put("invstorInfo", userService.getUserById(currentUser.getId())); //转让人信息
        if (invstor != null && invstor.getType() == UserType.ENTERPRISE) {
            model.put("invstorCorporation", corporationService.findCorporationByUserId(currentUser.getId())); //转让人（企业）
        }

        model.put("investment", dataConvertService.convertInvestment(investment));     /*投资*/
        model.put("transferPrice",investment.getAmount());

        List<Repayment> repayments = repaymentService.findList(transfer.getBorrowing());
        model.put("repayments", repayments);   /*还款列表 */

        String agreementHtml = FreemarkerUtils.process(agreementPath, model);
        // HTML协议转换为PDF协议视图
        String filename = new SimpleDateFormat("yyyyMMdd").format(borrowing.getLendingDate()) + "-" + borrowing.getId() + ".pdf";
        PdfView agreementPdf = new PdfView(filename, agreementHtml);
        // 下载
        return new ModelAndView(agreementPdf);
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
