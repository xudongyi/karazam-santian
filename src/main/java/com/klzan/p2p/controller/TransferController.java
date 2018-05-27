/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.controller;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.FreemarkerUtils;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.common.service.RSAService;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.agreement.AgreementService;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.borrowing.DataConvertService;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.capital.AccountantService;
import com.klzan.p2p.service.captcha.CaptchaService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.material.MaterialService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.util.AccountantUtils;
import com.klzan.p2p.vo.business.Request;
import com.klzan.plugin.pay.common.PageRequest;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.InvestmentRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import freemarker.template.TemplateException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

/**
 * Controller - 债权转让
 */
@Controller
@RequestMapping("transfer")
public class TransferController extends BaseController {

	/** 索引重定向URL */
	private static final String INDEX_REDIRECT_URL = "redirect:/transfer";

	/** 模板路径 */
	private static final String TEMPLATE_PATH = "/transfer";

	@Inject
	private TransferService transferService;

	@Inject
	private BorrowingService borrowingService;

	@Inject
	private RepaymentService repaymentService;

	@Inject
	private RepaymentPlanService repaymentPlanService;

	@Inject
	private InvestmentService investmentService;

	@Inject
	private InvestmentRecordService investmentRecordService;

	@Inject
	private UserService userService;

	@Inject
	private UserFinanceService userFinanceService;

	@Inject
	private CaptchaService captchaService;

	@Inject
	private RSAService rsaService;

	@Inject
	private MaterialService materialService;

	@Inject
	private DataConvertService dataConvertService;

	@Inject
	private AccountantService accountantService;

	@Inject
	private BusinessService businessService;

	@Resource
	private AgreementService agreementService;
	/**
	 * 列表
	 * @return
	 */
	@RequestMapping
	public String index(ModelMap model) {
		return "transfer/list";
	}

	/**
	 * 列表数据
	 * @param criteria
	 * @return
	 */
	@RequestMapping("data")
	@ResponseBody
	public PageResult<Transfer> investments(PageCriteria criteria) {
		Map map = new HashMap();
		List<String> states = new ArrayList<>();
		states.add(TransferLoanState.TRANSFERING.name());
		states.add(TransferLoanState.TRANSFERPART.name());
		states.add(TransferLoanState.TRANSFERED.name());
		map.put("states", states);
		return transferService.findPage(criteria, map);
	}

	/**
	 * 查看详情
	 *
	 * **/
	@RequestMapping(value = "{id}")
	public String view(@PathVariable Integer id, ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes ) {

		// 验证转让
		Transfer transfer = transferService.get(id);
		if (transfer == null) {
			redirectAttributes.addFlashAttribute("flashMessage", "转让不存在");
			return INDEX_REDIRECT_URL;
		}

		// 验证项目
		Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
		if (borrowing == null) {
			redirectAttributes.addFlashAttribute("flashMessage", "投资项目不存在");
			return INDEX_REDIRECT_URL;
		}
		if (transfer.getState() == TransferLoanState.CANCEL) {
			redirectAttributes.addFlashAttribute("flashMessage", "转让已撤销");
			return INDEX_REDIRECT_URL;
		}
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

		model.addAttribute("transfer", transfer);
		model.addAttribute("borrowing", borrowing);

		// 生成密钥
		RSAPublicKey publicKey = rsaService.generateKey(request);
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		model.addAttribute("modules", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
		model.addAttribute("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));

		User currentUser = UserUtils.getCurrentUser();
		if(currentUser !=null ){
			currentUser = userService.get(currentUser.getId());
			model.addAttribute("currentUser", currentUser);
			model.addAttribute("currentUserFinance", userFinanceService.findByUserId(currentUser.getId()));
		}
		model.addAttribute("expireDate", transfer.getExpireDate());
		//详情
		model.addAttribute("transferUser", userService.get(transfer.getTransfer()));
		model.addAttribute("borrower", userService.get(borrowing.getBorrower()));
		model.addAttribute("materials", materialService.findList(borrowing.getId()));
		List<InvestmentRecord> investmentRecords = investmentRecordService.findList(borrowing.getId(), true, InvestmentState.PAID, InvestmentState.SUCCESS);
		model.addAttribute("investmentRecords", dataConvertService.convertInvestmentRecords(investmentRecords));
		model.addAttribute("repayments", repaymentService.findList(borrowing.getId()));
		model.addAttribute("transferInvestments", dataConvertService.convertInvestments(investmentService.findListByTransfer(transfer.getId()))); //转让投资记录
		return "/transfer/detail";
	}


	/**
	 * 转让投资
	 */
	@RequestMapping(value = "{id}/invest")
	public String shortcut(@PathVariable Integer id, Integer parts, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {

		// 验证转让
		Transfer transfer = transferService.get(id);
		if (transfer == null) {
			redirectAttributes.addFlashAttribute("flashMessage", "转让不存在");
			return INDEX_REDIRECT_URL + "/{id}" ;
		}
		if (transfer.getIsFull()) {
			redirectAttributes.addFlashAttribute("flashMessage", "已满额");
			return INDEX_REDIRECT_URL + "/{id}" ;
		}

		// 验证项目
		Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
		if (borrowing == null) {
			redirectAttributes.addFlashAttribute("flashMessage", "投资项目不存在");
			return INDEX_REDIRECT_URL + "/{id}" ;
		}

		User currentUser = UserUtils.getCurrentUser();
		if (currentUser == null) {
			redirectAttributes.addFlashAttribute("flashMessage", "请先登录");
			return INDEX_REDIRECT_URL + "/{id}" ;
		}
		currentUser = userService.get(currentUser.getId());

		if (transfer.getTransfer().intValue() == currentUser.getId().intValue()) {
			redirectAttributes.addFlashAttribute("flashMessage", "不能购买自己的债权");
			return INDEX_REDIRECT_URL + "/{id}" ;
		}

		if (borrowing.getBorrower().intValue() == currentUser.getId().intValue()) {
			redirectAttributes.addFlashAttribute("flashMessage", "受让人不能为借款人");
			return INDEX_REDIRECT_URL + "/{id}" ;
		}

		//逾期判断
		List<Repayment> repayments = repaymentService.findList(borrowing.getId());
		for(Repayment repayment: repayments){
			if(repayment.getState().equals(RepaymentState.REPAID)){
				continue;
			}
			if(repayment.getIsOverdue()){
				redirectAttributes.addFlashAttribute("flashMessage", "借款逾期，购买失败");
				return INDEX_REDIRECT_URL + "/{id}" ;
			}
		}

		// 会员资金
		UserFinance investorFinance = userFinanceService.getByUserId(currentUser.getId());
		BigDecimal transferCapital = new BigDecimal(parts * 100);
		BigDecimal inTransferFee = AccountantUtils.calFee(transferCapital, borrowing.getInTransferFeeRate()); // 转入服务费
		transferCapital = transferCapital.add(inTransferFee);
		// 投资人余额校验
//		if (transferCapital.compareTo(investorFinance.getAvailable()) > 0) {
//			redirectAttributes.addFlashAttribute("flashMessage", "账户余额不足");
//			return INDEX_REDIRECT_URL + "/{id}";
//		}

		// 投资人余额校验
		if (transfer.getState().equals(TransferLoanState.CANCEL)) {
			redirectAttributes.addFlashAttribute("flashMessage", "转让已撤销");
			return INDEX_REDIRECT_URL + "/{id}";
		}

		try{
////			transferService.transferIn(transfer, currentUser, parts);
//			Request backRequest = businessService.transferFrozen(request, transfer, parts, currentUser, borrowing, PaymentOrderType.TRANSFER_FROZEN);
//			model.addAttribute("requestUrl", backRequest.getRequestUrl());
//			model.addAttribute("parameterMap", backRequest.getParameterMap());
//			return "payment/submit";

			PayModule payModule = PayPortal.invest.getModuleInstance();
			InvestmentRequest req = new InvestmentRequest(false, UserUtils.getCurrentUser().getId(), transfer.getBorrowing(),
					transferCapital, true, transfer.getId(), parts, PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY, DeviceType.PC);
			payModule.setRequest(req);
			PageRequest param = payModule.invoking().getPageRequest();
			model.addAttribute("requestUrl", param.getRequestUrl());
			model.addAttribute("parameterMap", param.getParameterMap());
			return "payment/submit";
		}catch (Exception e){
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("flashMessage", e.getMessage()==null?"购买失败":e.getMessage());
			return INDEX_REDIRECT_URL + "/{id}";
		}

//		redirectAttributes.addFlashAttribute("flashMessage", "购买成功");
//		return INDEX_REDIRECT_URL + "/{id}";
	}

	/**
	 * 计算债权
	 *
	 **/
	@RequestMapping(value = "cal", method = RequestMethod.POST)
	@ResponseBody
	public Result cal(@RequestParam Integer id, @RequestParam  Integer parts) {

		if(id == null || parts == null){
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

		User currentUser = UserUtils.getCurrentUser();
		if (currentUser == null) {
			return Result.error("请先登录");
		}
		currentUser = userService.get(currentUser.getId());

		//逾期判断
		List<Repayment> repayments = repaymentService.findList(borrowing.getId());
		for(Repayment repayment: repayments){
			if(repayment.getState().equals(RepaymentState.REPAID)){
				continue;
			}
			if(repayment.getIsOverdue()){
				return Result.error("借款逾期，购买失败");
			}
		}

		BigDecimal transferWorth = BigDecimal.ZERO;
		BigDecimal transferCapital = new BigDecimal(parts * 100);
		BigDecimal transferFee = BigDecimal.ZERO;
		List<RepaymentPlan> repaymentPlans = repaymentPlanService.findListCanTransfer(transfer.getBorrowing(), transfer.getTransfer());
		for(RepaymentPlan repaymentPlan : repaymentPlans) {
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

		return Result.success(JsonUtils.toJson(messages));
	}

	/**
	 * 协议
	 */
	@RequestMapping("agreement/{id}")
	public String agreement(@PathVariable Integer id,ModelMap model,RedirectAttributes redirectAttributes) {
		// 验证项目
		Transfer transfer = transferService.get(id);
		if (transfer == null) {
			redirectAttributes.addFlashAttribute("flashMessage", "转让不存在");
			return "redirect:/uc/transfer";
		}
		Borrowing borrowing = borrowingService.get(transfer.getBorrowing());
		if (borrowing == null) {
			redirectAttributes.addFlashAttribute("flashMessage", "借款不存在");
			return "redirect:/uc/transfer";
		}
		//获取受让人转让协议
		Agreement agreement = agreementService.get(borrowing.getInvestTransferAgreementId());
		String agreementContent = agreement.getContent();

		try {
			model.addAttribute("content", FreemarkerUtils.process(agreementContent,new HashMap<>() ));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return "/agreement/agreement";
	}

}