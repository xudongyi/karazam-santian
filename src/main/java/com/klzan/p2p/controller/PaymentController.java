package com.klzan.p2p.controller;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.InvestmentRecord;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.business.BusinessProcessService;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.IResponse;
import com.klzan.plugin.pay.PayGeneratorContext;
import com.klzan.plugin.pay.ips.withdrawrefticket.vo.IpsPayWithdrawRefundTicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by suhao Date: 2017/3/22 Time: 13:48
 *
 * @version: 1.0
 */
@Controller
@RequestMapping("payment")
public class PaymentController extends BaseController {
    @Autowired
    private PayGeneratorContext payGeneratorContext;
    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private BusinessProcessService businessProcessService;
    @Autowired
    private InvestmentRecordService investmentRecordService;
    @Autowired
    private DistributeLock lock;

    @RequestMapping("notify/sync/{orderNo}")
    public String sync(@PathVariable String orderNo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            lock.lock(LockStack.ORDER_NO_LOCK, orderNo);
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
            Map<String, Object> requestParamMap = WebUtils.getRequestParamMap(request);
            logger.info(JsonUtils.toJson(requestParamMap));
            String result = JsonUtils.toJson(requestParamMap);
            IResponse response = paymentOrder.getMethod().getResponse();
            IResponse iResult = JsonUtils.toObject(result, response.getClass());
            String resultMsg = iResult.getMsg();
            try {
                if (null == paymentOrder) {
                    logger.warn("----------sync 订单不存在----------");
                    logger.warn(String.format("sync notify orderNo[%s] not exist.", orderNo));
                    return "redirect:/index";
                }
                if (paymentOrder.getStatus().equals(PaymentOrderStatus.SUCCESS)) {
                    logger.warn("----------sync 订单已处理----------");
                    return "redirect:/index";
                }
                BusinessType businessType = paymentOrder.getType().getBusinessType();
                businessProcessService.processOrder(orderNo, result);
                if (payGeneratorContext.verifySign(result, businessType)) {
                    IDetailResponse responseResult = payGeneratorContext.getResponseResult(result, businessType);
                    logger.info(JsonUtils.toJson(responseResult));
                    businessProcessService.process(orderNo, responseResult, businessType, paymentOrder.getType());
                    if (paymentOrder.getType().equals(PaymentOrderType.INVESTMENT)||paymentOrder.getType().equals(PaymentOrderType.COMB_FREEZE)) {
                        InvestmentRecord investmentRecord = investmentRecordService.findByOrderNo(orderNo);
                        logger.info("----------投资 处理完成--------");
                        return "redirect:/investment/" + investmentRecord.getBorrowing();
                    }
                    if (paymentOrder.getType().equals(PaymentOrderType.TRANSFER_FROZEN)) {
                        logger.info("----------转让 处理完成--------");
                        return "redirect:/investment?type=TRANSFER";
                    }
                    if (paymentOrder.getType().equals(PaymentOrderType.REPAYMENT_FROZEN) || paymentOrder.getType().equals(PaymentOrderType.REPAYMENT_EARLY_FROZEN)) {
                        logger.info("----------还款/提前还款 处理完成--------");
                        return "redirect:/uc/borrowing";
                    }
                    if (paymentOrder.getType().equals(PaymentOrderType.OPEN_ACCOUNT) || paymentOrder.getType().equals(PaymentOrderType.OPEN_ACCOUNT)) {
                        logger.info("----------开户 处理完成--------");
                        return "redirect:/uc/security";
                    }
                    if (paymentOrder.getType().equals(PaymentOrderType.RECHARGE)) {
                        logger.info("----------充值 处理完成--------");
                        return "redirect:/uc/order";
                    }
                    if (paymentOrder.getType().equals(PaymentOrderType.AUTO_INVESTMENT_SIGN)) {
                        logger.info("----------自动投标签约 处理完成--------");
                        return "redirect:/uc/autoInvestment";
                    }
                    if (paymentOrder.getType().equals(PaymentOrderType.REFERRAL_FEE_SETTLEMENT_FROZEN)) {
                        return "redirect:/admin";
                    }
                    return businessType.getRedirectPath();
                }
            } catch (BusinessProcessException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                redirectAttributes.addFlashAttribute("flashMessage", resultMsg);
                if (paymentOrder.getType() == PaymentOrderType.WITHDRAWAL) {
                    return "redirect:/uc/security";
                }
            }

            if (StringUtils.equals(iResult.getResultCode(), "000000")) {
                redirectAttributes.addFlashAttribute("flashMessage", "处理成功");
            } else {
                redirectAttributes.addFlashAttribute("flashMessage", resultMsg);
            }

            return "redirect:/index";
        } finally {
            lock.unLock(LockStack.ORDER_NO_LOCK, orderNo);
        }
    }

    @RequestMapping("notify/async/{orderNo}")
    @ResponseBody
    public String async(@PathVariable String orderNo, HttpServletRequest request) {
        try {
            lock.lock(LockStack.ORDER_NO_LOCK, orderNo);
            logger.info("----------async ----------" + orderNo);
            Map<String, Object> requestParamMap = WebUtils.getRequestParamMap(request);
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
            if (null == paymentOrder) {
                logger.warn("----------async 订单不存在----------");
                logger.warn(String.format("async notify orderNo[%s] not exist.", orderNo));
                return "error";
            }
            logger.info("----------async ----------" + orderNo + "---" + paymentOrder.getStatus());
            if (paymentOrder.getStatus().equals(PaymentOrderStatus.SUCCESS)) {
                logger.info("----------async 订单已处理----------");
                return "ipsCheckOk";
            }
            BusinessType businessType = paymentOrder.getType().getBusinessType();
            logger.info(JsonUtils.toJson(requestParamMap));
            String result = JsonUtils.toJson(requestParamMap);
            businessProcessService.processOrder(orderNo, result);
            if (payGeneratorContext.verifySign(result, businessType)) {
                IDetailResponse responseResult = payGeneratorContext.getResponseResult(result, businessType);
                logger.info(JsonUtils.toJson(responseResult));
                businessProcessService.process(orderNo, responseResult, businessType, paymentOrder.getType());
                return "ipsCheckOk";
            }
            return "error";
        } finally {
            lock.unLock(LockStack.ORDER_NO_LOCK, orderNo);
        }
    }

    @RequestMapping("notify/withdraw_refund")
    @ResponseBody
    public String withdrawRefund(HttpServletRequest request) {
        logger.info("----------withdraw_refund ----------");
        Map<String, Object> requestParamMap = WebUtils.getRequestParamMap(request);
        logger.info(JsonUtils.toJson(requestParamMap));
        BusinessType businessType = BusinessType.WITHDRAW_REFUND_TICKET;
        logger.info(JsonUtils.toJson(requestParamMap));
        String result = JsonUtils.toJson(requestParamMap);
        IpsPayWithdrawRefundTicketResponse response = JsonUtils.toObject(result, IpsPayWithdrawRefundTicketResponse.class);
        String orderNo = response.getMerBillNo();
        logger.info("----------withdraw_refund order {} ----------", orderNo);
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
        if (null == paymentOrder) {
            logger.warn("----------withdraw_refund 订单不存在----------");
            logger.warn(String.format("withdraw_refund notify orderNo[%s] not exist.", orderNo));
            return "";
        }
        businessProcessService.processOrder(orderNo, result);
        if (payGeneratorContext.verifySign(result, businessType)) {
            IDetailResponse responseResult = payGeneratorContext.getResponseResult(result, businessType);
            logger.info(JsonUtils.toJson(responseResult));
            businessProcessService.process(orderNo, responseResult, businessType, paymentOrder.getType());
            return "ipsCheckOk";
        }
        return "";
    }

}
