package com.klzan.p2p.controller;

import com.klzan.core.Result;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.SnUtils;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.PayGeneratorContext;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.assureproject.vo.IpsPayAssureProjectRequest;
import com.klzan.plugin.pay.ips.assureproject.vo.IpsPayAssureProjectResponse;
import com.klzan.plugin.pay.ips.autosign.vo.IpsPayAutoSignRequest;
import com.klzan.plugin.pay.ips.combfreeze.vo.IpsPayCombFreezeRequest;
import com.klzan.plugin.pay.ips.combfreeze.vo.RedPacketRequest;
import com.klzan.plugin.pay.ips.comquery.vo.IpsPayCommonQueryRequest;
import com.klzan.plugin.pay.ips.comquery.vo.IpsPayCommonQueryResponse;
import com.klzan.plugin.pay.ips.frozen.vo.IpsPayFrozenRequest;
import com.klzan.plugin.pay.ips.openacc.vo.IpsPayOpenAccountRequest;
import com.klzan.plugin.pay.ips.querybank.vo.IpsPayQueryBankRequest;
import com.klzan.plugin.pay.ips.querybank.vo.IpsPayQueryBankResponse;
import com.klzan.plugin.pay.ips.recharge.vo.IpsPayRechargeRequest;
import com.klzan.plugin.pay.ips.regproject.vo.IpsPayRegProjectRequest;
import com.klzan.plugin.pay.ips.regproject.vo.IpsPayRegProjectResponse;
import com.klzan.plugin.pay.ips.transfer.vo.IpsPayTransferRequest;
import com.klzan.plugin.pay.ips.transfer.vo.IpsPayTransferResponse;
import com.klzan.plugin.pay.ips.transfer.vo.TransferAccDetailRequest;
import com.klzan.plugin.pay.ips.unfreeze.vo.IpsPayUnfreezeRequest;
import com.klzan.plugin.pay.ips.unfreeze.vo.IpsPayUnfreezeResponse;
import com.klzan.plugin.pay.ips.userlogin.vo.IpsPayUserLoginRequest;
import com.klzan.plugin.pay.ips.withdraw.vo.IpsPayWithdrawRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 测试
 * Created by suhao Date: 2017/3/1 Time: 16:43
 *
 * @version: 1.0
 */
@Controller
public class TestController {
    protected static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private PayGeneratorContext payGeneratorContext;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @RequestMapping("test_openacc")
    public String testOpenacc(Model model) {
        PaymentOrder paymentOrder = new PaymentOrder(false, 1, PaymentOrderType.OPEN_ACCOUNT, SnUtils.getOrderNo(), null, new BigDecimal(100), "", null);
        Object map = payGeneratorContext.generateRequest(new IpsPayOpenAccountRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                "1",
                "13258382819",
                "13258382819",
                "610115197707244566",
                "贺新华",
                "",
                "",
                ""));
        paymentOrderService.persist(paymentOrder);
        model.addAttribute("requestUrl", IpsPayConfig.REQUEST_URL);
        model.addAttribute("parameterMap", map);

        return "payment/submit";
    }

    @RequestMapping("test_login")
    public String testLogin(Model model) {
        PaymentOrder paymentOrder = new PaymentOrder(false, 1, PaymentOrderType.USER_LOGIN, SnUtils.getOrderNo(), null, new BigDecimal(100), "", null);
        Object map = payGeneratorContext.generateRequest(new IpsPayUserLoginRequest("im11@shs.im"));
        paymentOrderService.persist(paymentOrder);
        model.addAttribute("requestUrl", IpsPayConfig.LOGIN_URL);
        model.addAttribute("parameterMap", map);
        return "payment/submit";
    }

    @RequestMapping("test_bank_query")
    @ResponseBody
    public Result testBankQuery(Model model) {
        String result = (String) payGeneratorContext.generateRequest(new IpsPayQueryBankRequest());
        logger.info("result:{}", result);
        if (payGeneratorContext.verifySign(result, BusinessType.QUERY_BANK)) {
            IpsPayQueryBankResponse responseResult = (IpsPayQueryBankResponse) payGeneratorContext.getResponseResult(result, BusinessType.QUERY_BANK);
            logger.info(JsonUtils.toJson(responseResult));
            return Result.success("查询银行成功", responseResult);
        }
        return Result.error();
    }

    @RequestMapping("test_recharge")
    public String testRecharge(Model model) {
        PaymentOrder paymentOrder = new PaymentOrder(false, 1, PaymentOrderType.RECHARGE, SnUtils.getOrderNo(), null, new BigDecimal(10000), "", null);
        Object map = payGeneratorContext.generateRequest(new IpsPayRechargeRequest(
                paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                "1",
                "1",
                "1103",
                "1",
                "100000123990",
                paymentOrder.getAmountString(),
                "1",
                "0.02",
                "2",
                "2"));
        paymentOrderService.persist(paymentOrder);
        model.addAttribute("requestUrl", IpsPayConfig.REQUEST_URL);
        model.addAttribute("parameterMap", map);

        return "payment/submit";
    }

    @RequestMapping("test_withdraw")
    public String testWithdraw(Model model) {
        PaymentOrder paymentOrder = new PaymentOrder(false, 1, PaymentOrderType.WITHDRAWAL, SnUtils.getOrderNo(), null, new BigDecimal(100), "", null);
        Object map = payGeneratorContext.generateRequest(new IpsPayWithdrawRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                "1",
                paymentOrder.getAmountString(),
                "0",
                "1",
                "2",
                "100000123990"));
        paymentOrderService.persist(paymentOrder);
        model.addAttribute("requestUrl", IpsPayConfig.REQUEST_URL);
        model.addAttribute("parameterMap", map);
        return "payment/submit";
    }

    @RequestMapping("test_reg_project")
    public String testRegProject(Model model) {
        PaymentOrder paymentOrder = new PaymentOrder(false, 1, PaymentOrderType.REGIST_PROJECT, SnUtils.getOrderNo(), null, new BigDecimal(100), "", null);
        String result = (String) payGeneratorContext.generateRequest(new IpsPayRegProjectRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                "3",
                "test",
                "1",
                paymentOrder.getAmountString(),
                "1",
                "12",
                "",
                "",
                "365",
                "买车",
                "1",
                "445103198512286995",
                "庞可文",
                "100000122836",
                "0"));
        logger.info("result:{}", result);
        paymentOrderService.persist(paymentOrder);
        if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
            IpsPayRegProjectResponse responseResult = (IpsPayRegProjectResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
            logger.info(JsonUtils.toJson(responseResult));
        }
        return "index";
    }

    @RequestMapping("test_assure_project")
    @ResponseBody
    public Result testAssureProject(Model model) {
        PaymentOrder paymentOrder = new PaymentOrder(false, 1, PaymentOrderType.ASSURE_PROJECT, SnUtils.getOrderNo(), null, new BigDecimal(100), "", null);
        String result = (String) payGeneratorContext.generateRequest(new IpsPayAssureProjectRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                "1",
                "500",
                "5",
                "1",
                "100000123990"));
        logger.info("result:{}", result);
        paymentOrderService.persist(paymentOrder);
        if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
            IpsPayAssureProjectResponse responseResult = (IpsPayAssureProjectResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
            logger.info(JsonUtils.toJson(responseResult));
            return Result.success("追加成功", responseResult);
        }
        return Result.error();
    }

    @RequestMapping("test_frozen")
    public String testFrozen(Model model) {
        PaymentOrder paymentOrder = new PaymentOrder(false, 1, PaymentOrderType.INVESTMENT, SnUtils.getOrderNo(), null, new BigDecimal(100), "", null);
        Object map = payGeneratorContext.generateRequest(new IpsPayFrozenRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                "1",
                "1",
                "1",
                "10000",
                "1",
                paymentOrder.getAmountString(),
                "0",
                "1",
                "100000123990",
                ""));
        logger.info("result:{}", map);
        paymentOrderService.persist(paymentOrder);
        model.addAttribute("requestUrl", IpsPayConfig.REQUEST_URL);
        model.addAttribute("parameterMap", map);
        return "payment/submit";
    }

    @RequestMapping("test_unfrozen")
    @ResponseBody
    public Result testUnFrozen(Model model) {
        PaymentOrder paymentOrder = new PaymentOrder(false, 1, PaymentOrderType.LENDING_CANCEL, SnUtils.getOrderNo(), null, new BigDecimal(100), "", null);
        String result = (String) payGeneratorContext.generateRequest(new IpsPayUnfreezeRequest(
                paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                "1",
                "PDJ17032200000014937",
                "1",
                "0",
                "100000123990",
                "100.00"
        ));
        logger.info("result:{}", result);
        paymentOrderService.persist(paymentOrder);
        if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
            IpsPayUnfreezeResponse responseResult = (IpsPayUnfreezeResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
            logger.info(JsonUtils.toJson(responseResult));
            return Result.success("解冻成功", responseResult);
        }
        return Result.error();
    }

    @RequestMapping("test_transfer")
    @ResponseBody
    public Result testTransfer(Model model) {
        PaymentOrder paymentOrder = new PaymentOrder(false, 1, PaymentOrderType.LENDING, SnUtils.getOrderNo(), null, new BigDecimal(100), "", null);
        List<TransferAccDetailRequest> transferAccDetails = new ArrayList<>();
        TransferAccDetailRequest transferAccDetail = new TransferAccDetailRequest(SnUtils.getOrderNo(),
                "PDJ17032200000014974",
                "100000123990",
                "0.00",
                "100000122836",
                "0.00",
                "100.00");
        transferAccDetails.add(transferAccDetail);
        String result = (String) payGeneratorContext.generateRequest(new IpsPayTransferRequest(
                paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                "1",
                "1",
                "2",
                "2",
                transferAccDetails
        ));
        logger.info("result:{}", result);
        paymentOrderService.persist(paymentOrder);
        if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
            IpsPayTransferResponse responseResult = (IpsPayTransferResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
            logger.info(JsonUtils.toJson(responseResult));
            return Result.success("出借成功", responseResult);
        }
        return Result.error();
    }

    @RequestMapping("test_comb_freeze")
    public String testCombFreeze(Model model) {
        PaymentOrder paymentOrder = new PaymentOrder(true, 1, PaymentOrderType.COMB_FREEZE, SnUtils.getOrderNo(), null, new BigDecimal(100), "", null);
        PaymentOrder childPaymentOrder = new PaymentOrder(false, 1, PaymentOrderType.COMB_FREEZE, SnUtils.getOrderNo(), null, new BigDecimal(100), "", null);
        childPaymentOrder.setParentOrderNo(paymentOrder.getOrderNo());
        Object map = payGeneratorContext.generateRequest(new IpsPayCombFreezeRequest(SnUtils.getOrderNo(),
                DateUtils.format(new Date(), DateUtils.YYYY_MM_DD),
                "1",
                "1",
                "",
                "",
                new RedPacketRequest(SnUtils.getOrderNo(), "9", childPaymentOrder.getAmountString(), "0", "1", "100000123990", "100000122836"),
                new RedPacketRequest(SnUtils.getOrderNo(), "1", childPaymentOrder.getAmountString(), "0", "1", "100000123990", "100000122836")));
        logger.info("result:{}", map);
        paymentOrderService.persist(paymentOrder);
        paymentOrderService.persist(childPaymentOrder);
        model.addAttribute("requestUrl", IpsPayConfig.REQUEST_URL);
        model.addAttribute("parameterMap", map);
        return "payment/submit";
    }

    @RequestMapping("test_auto_sign")
    public String testAutoSign(Model model) {
        PaymentOrder paymentOrder = new PaymentOrder(false, 1, PaymentOrderType.AUTO_INVESTMENT_SIGN, SnUtils.getOrderNo(), null, new BigDecimal(100), "", null);
        Object map = payGeneratorContext.generateRequest(new IpsPayAutoSignRequest(paymentOrder.getOrderNo(),
                DateUtils.format(paymentOrder.getCreateDate(), DateUtils.YYYY_MM_DD),
                "1",
                "100000123990",
                "0",
                "365",
                "30",
                "730",
                "0",
                "0",
                "1%",
                "10.25%"));
        logger.info("result:{}", map);
        paymentOrderService.persist(paymentOrder);
        model.addAttribute("requestUrl", IpsPayConfig.REQUEST_URL);
        model.addAttribute("parameterMap", map);
        return "payment/submit";
    }

    @RequestMapping("test_common_query")
    @ResponseBody
    public Result testCommonQuery(Model model) {
        PaymentOrder paymentOrder = new PaymentOrder(false, 1, PaymentOrderType.USER_INFO_QUERY, SnUtils.getOrderNo(), null, new BigDecimal(100), "", null);
        String result = (String) payGeneratorContext.generateRequest(new IpsPayCommonQueryRequest(
                "01",
                "100000123990"
               ));
        logger.info("result:{}", result);
        paymentOrderService.persist(paymentOrder);
        if (payGeneratorContext.verifySign(result, paymentOrder.getType().getBusinessType())) {
            IpsPayCommonQueryResponse responseResult = (IpsPayCommonQueryResponse) payGeneratorContext.getResponseResult(result, paymentOrder.getType().getBusinessType());
            logger.info(JsonUtils.toJson(responseResult));
            return Result.success("查询成功", responseResult);
        }
        return Result.error();
    }

}
