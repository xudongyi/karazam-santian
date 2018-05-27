/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.controller.admin.transfer;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.common.service.RSAService;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.controller.BaseController;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.enums.TransferLoanState;
import com.klzan.p2p.model.*;
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
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

/**
 * Controller - 债权转让
 */
@Controller("adminTransfer")
@RequestMapping("admin/transfer")
public class TransferController extends BaseController {

	@Inject
	private TransferService transferService;

	@RequestMapping("list")
	public String list(ModelMap modelMap){
		return "admin/transfer/list";
	}
	@RequestMapping(value="/list.json",method = RequestMethod.GET)
	@ResponseBody
	public Object listQuery(PageCriteria pageCriteria, HttpServletRequest request){

		return transferService.findPageByMybatis(pageCriteria,request);
	}

}