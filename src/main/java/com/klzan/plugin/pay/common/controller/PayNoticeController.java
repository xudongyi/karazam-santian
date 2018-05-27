package com.klzan.plugin.pay.common.controller;

import com.klzan.core.Result;
import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.core.util.Base64;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.core.util.XmlUtils;
import com.klzan.p2p.controller.IndexController;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.enums.UserType;
import com.klzan.p2p.model.CpcnBankCard;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.user.CpcnBankCardService;
import com.klzan.p2p.service.user.UserCommonService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import payment.api.notice.Notice4243Request;
import payment.api.notice.NoticeRequest;
import payment.api.notice.NoticeResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Controller - 支付
 *
 * @author ICLNetwork Team
 * @version 3.0
 */
@Controller
@RequestMapping("china-clearing")
public class PayNoticeController {

    protected static Logger logger = LoggerFactory.getLogger(IndexController.class);

    protected static final String ERROR_VIEW = "/error";

    @Autowired
    private UserService userService;
    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private PayUtils payUtils;
    @Autowired
    private UserCommonService userCommonService;
    @Autowired
    private CpcnBankCardService cpcnBankCardService;
    @Autowired
    private DistributeLock lock;

    /**
     * 回调
     */
    @RequestMapping("/notice/sync/{sn}")
    public String callback(@PathVariable String sn, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, ModelMap model) throws Exception {
        try {
            lock.lock(LockStack.ORDER_NO_LOCK, sn);
            if (sn == null) {
                logger.info("订单WEB同步回调失败:订单号不存在");
                return ERROR_VIEW;
            }
            logger.info(String.format("订单[%s]WEB同步回调", sn));
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
            NoticeRequest notice = null;
            PayPortal type = null;
            if (paymentOrder == null) {
                logger.info(String.format("订单[%s]类型[%s]WEB同步回调失败:订单不存在", sn, type));
                return ERROR_VIEW;
            }

            if (!paymentOrder.getIsMobile()) {
                notice = getNoticeRequest(request);
                type = getTxCodeType(notice);
                if (!paymentOrder.getStatus().equals(PaymentOrderStatus.PROCESSING)) {
                    logger.info(String.format("订单[%s]已处理", sn));
                } else {
                    // 参数
                    logger.info(String.format("[%s]WEB同步回调", type));
                    logger.info(JsonUtils.toJson(notice));

                    // 业务
                    Result result = type.getModuleInstance().notice(sn, notice);
                    if (!result.isSuccess()) {
                        logger.info(String.format("订单[%s]WEB同步失败:[%s]", sn, result.getMessage()));
                    }
                    logger.info(String.format("订单[%s]WEB同步回调成功", sn));
                }


                switch (type) {
                    case open_account: {
                        return "redirect:/uc/security";
                    }
                }
                logger.info(String.format("WEB同步回调[%s]", "redirect:/index"));
                return "redirect:/index";
            } else {
                if (!paymentOrder.getStatus().equals(PaymentOrderStatus.PROCESSING)) {
                    Map accountDetail = userCommonService.accountDetail(paymentOrder.getUserId());
                    logger.info(String.format("订单[%s]已处理", sn));
                    if (paymentOrder.getStatus().equals(PaymentOrderStatus.SUCCESS)) {
                        Result result = Result.success("该订单已处理", accountDetail);
                        model.addAttribute("result", result);
                        model.addAttribute("resultStr", JsonUtils.toJson(result));
                    } else {
                        model.addAttribute("result", Result.error("该订单已处理"));
                        model.addAttribute("resultStr", JsonUtils.toJson(Result.error("该订单已处理")));
                    }
                } else {
                    // 参数
                    notice = getNoticeRequest(request);
                    type = getTxCodeType(notice);
                    logger.info(String.format("[%s]APP同步回调", type));
                    logger.info(JsonUtils.toJson(notice));
                    // 业务
                    Result result = type.getModuleInstance().notice(sn, notice);
                    Map accountDetail = userCommonService.accountDetail(paymentOrder.getUserId());
                    if (result.isSuccess()) {
                        result = Result.success("成功", accountDetail);
                        model.addAttribute("result", result);
                        model.addAttribute("resultStr", JsonUtils.toJson(result));
                        logger.info(String.format("订单[%s]类型[%s]APP同步回调成功", sn, type));
                    } else {
                        model.addAttribute("result", result);
                        model.addAttribute("resultStr", JsonUtils.toJson(result));
                        logger.info(String.format("订单[%s]类型[%s]APP同步回调失败：[%s]", sn, type, result.getMessage()));
                    }
                }
                return "/payment/notice";
            }
        } finally {
            lock.unLock(LockStack.ORDER_NO_LOCK, sn);
        }
    }

    /**
     * 异步通知
     */
    @RequestMapping("/notice/async")
    public void notify(HttpServletRequest request, HttpServletResponse response,
                       ModelMap model, RedirectAttributes redirectAttributes) throws Exception {

        logger.info("订单异步通知");

        // 参数
        NoticeRequest notice = getNoticeRequest(request);
        PayPortal type = getTxCodeType(notice);
        String sn = getSN(notice);
        try {
            lock.lock(LockStack.ORDER_NO_LOCK, sn);
            logger.info(String.format("订单[%s]类型[%s]异步通知", sn, type));

            if (sn == null) {
                logger.info("订单异步通知失败:订单号不存在");
                return;
            }
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
            if (paymentOrder == null) {
                logger.info(String.format("订单[%s]类型[%s]异步通知失败:订单不存在", sn, type));
                return;
            }
            if (!paymentOrder.getStatus().equals(PaymentOrderStatus.PROCESSING)) {
                logger.info(String.format("订单[%s]已处理", sn));
                return;
            }

            // 业务
            Result result = type.getModuleInstance().notice(sn, notice);
            if (!result.isSuccess()) {
                logger.info(String.format("订单[%s]类型[%s]异步通知失败：[%s]", sn, type, result.getMessage()));
                return;
            }

            logger.info(String.format("订单[%s]类型[%s]异步通知成功", sn, type));

            // 异步回调后，返回中金消息
            try {
                PrintWriter out = response.getWriter();
                String xmlString = new NoticeResponse().getMessage();
                String base64String = new String(Base64.encode(xmlString.getBytes("UTF-8")));
                out.print(base64String);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info(String.format("订单[%s]类型[%s]异步通知成功,返回中金消息成功", sn, type));
        } finally {
            lock.unLock(LockStack.ORDER_NO_LOCK, sn);
        }
    }

    /**
     * 获取响应消息
     *
     * @param request
     * @return
     */
    private NoticeRequest getNoticeRequest(HttpServletRequest request) {
        String message = request.getParameter("message");
        String signature = request.getParameter("signature");
        System.out.println(message);
        System.out.println(signature);
        NoticeRequest noticeRequest = null;
        try {
            noticeRequest = new NoticeRequest(message, signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noticeRequest;
    }

    /**
     * 获取通知数据
     */
    public PayPortal getTxCodeType(NoticeRequest notice) throws Exception {
        String code = notice.getTxCode();
        PayPortal type = null;
        if (code.equals("4233")) {
            type = PayPortal.open_account;
        } else if (code.equals("4253")) {
            type = PayPortal.recharge;
        } else if (code.equals("4243")) {
            type = PayPortal.bankcard_bind;
        } else if (code.equals("4247")) {
            type = PayPortal.bankcard_unbind;
        } else if (code.equals("3218")) {
            type = PayPortal.invest;
        } else if (code.equals("4278")) {
            type = PayPortal.signed;
        } else if (code.equals("4257")) {
            type = PayPortal.withdraw;
        } else {
//            logger.info("------获取通知数据异常------");
//            logger.info(JsonUtils.toJson(notice));
            throw new RuntimeException("获取通知数据异常");
        }
        return type;
    }

    /**
     * 获取通知数据
     */
    public String getSN(NoticeRequest notice) throws Exception {
        String code = notice.getTxCode();
        Map<String, String> request = XmlUtils.xml2Map(notice.getPlainText());
        logger.info(JsonUtils.toJson(request));
        if (code.equals("4233")) {
            String phoneNumber = request.get("PhoneNumber");
            String userType = request.get("UserType");
            UserType uType = null;
            if ("11".equals(userType)) {
                uType = UserType.GENERAL;
            } else {
                uType = UserType.ENTERPRISE;
            }
            User user = userService.getUserByMobile(phoneNumber, uType);
            if (user != null) {
                PaymentOrder paymentOrder = paymentOrderService.find(user.getId(), PaymentOrderStatus.PROCESSING, PaymentOrderType.OPEN_ACCOUNT);
                if (user != null) {
                    return paymentOrder.getOrderNo();
                }
            }
            throw new RuntimeException("获取开户通知数据异常");
        } else if (code.equals("3218")) { // 支付成功
            String paymentNo = request.get("PaymentNo");
            if (StringUtils.isBlank(paymentNo)) {
                throw new RuntimeException("订单号为空");
            }
            return paymentNo;
        } else if (code.equals("4243")) {  //银行账户绑定
//            Notice4243Request txNotice = new Notice4243Request(notice.getDocument());
//            CpcnPayAccountInfo payAccountInfo = payUtils.getCpcnPayAccountInfo(txNotice.getPaymentAccountNumber());
//            String bindingSystemNo = txNotice.getBindingSystemNo();
//            Integer userId = payAccountInfo.getUserId();
//            PaymentOrder paymentOrder = paymentOrderService.find(userId, PaymentOrderStatus.PROCESSING, PaymentOrderType.BINDCARD_BIND);
//            CpcnBankCard cpcnBankCard = cpcnBankCardService.find(userId, null, bindingSystemNo);
//            if(cpcnBankCard == null){
//                cpcnBankCard = new CpcnBankCard();
//                cpcnBankCard.setUserId(paymentOrder.getUserId());
//                cpcnBankCard.setOrderNo(paymentOrder.getOrderNo());
//                cpcnBankCard.setBankID(txNotice.getBankID());
//                cpcnBankCard.setBankAccountNumber(txNotice.getBankAccountNumber());
//                cpcnBankCard.setBindingSystemNo(txNotice.getBindingSystemNo());
//                cpcnBankCard.setStatus(txNotice.getStatus() + "");
//                cpcnBankCardService.persist(cpcnBankCard);
//            }
//            return paymentOrder.getOrderNo();
            return null;
        } else if (code.equals("4247")) {  //银行账户解绑
//            String paymentNo = request.get("BindingSystemNo");
//            if (StringUtils.isBlank(paymentNo)) {
//                throw new RuntimeException("订单号为空");
//            }
//            return paymentNo;
            return null;
        } else if (code.equals("4253")) {  //充值
            String paymentNo = request.get("PaymentNo");
            if (StringUtils.isBlank(paymentNo)) {
                throw new RuntimeException("订单号为空");
            }
            return paymentNo;
        } else if (code.equals("4257")) {//提现
            String paymentNo = request.get("TxSN");
            if (StringUtils.isBlank(paymentNo)) {
                throw new RuntimeException("订单号为空");
            }
            return paymentNo;
        } else if (code.equals("4278")) {//支付账户签约成功通知
            String paymentNo = request.get("AgreementNo");
            if (StringUtils.isBlank(paymentNo)) {
                throw new RuntimeException("订单号为空");
            }
            return paymentNo;
        } else {
            logger.info("------获取通知数据异常------");
            logger.info(JsonUtils.toJson(notice));
            throw new RuntimeException("获取通知数据异常");
        }
    }

}