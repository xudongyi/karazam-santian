package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.PayException;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.enums.RechargeBusinessType;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.model.RechargeRecord;
import com.klzan.p2p.service.recharge.RechargeService;
import com.klzan.p2p.service.user.CpcnAccService;
import com.klzan.p2p.vo.recharge.RechargeVo;
import com.klzan.plugin.pay.common.dto.RechargeRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.notice.Notice4253Request;
import payment.api.notice.NoticeRequest;

import java.math.BigDecimal;

/**
 * 充值
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessRechargeService extends AbstractBusinessService {

    @Autowired
    private RechargeService rechargeService;

    @Autowired
    private CpcnAccService cpcnAccService;

    @Override
    public Result before(PayModule module) {
        RechargeRequest request = (RechargeRequest) module.getRequest();
        createOrder(module, request.getUserId(), PaymentOrderType.RECHARGE);
        RechargeVo rechargeVo = new RechargeVo();
        rechargeVo.setRechargeBusType(RechargeBusinessType.GENERAL);
        rechargeVo.setOrderNo(module.getSn());
        rechargeVo.setAmount(request.getAmount());
        rechargeVo.setUserId(request.getUserId());
        rechargeService.addRecord(rechargeVo);
        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        String sn = module.getSn();
        RechargeRecord rechargeRecord = rechargeService.findByOrderNo(sn);
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
        paymentOrder.setAmount(rechargeRecord.getAmount());
        paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
        paymentOrderService.merge(paymentOrder);
        return result;
    }

    @Override
    public Result notice(String sn, NoticeRequest notice) {
        try {
            Notice4253Request txNotice = new Notice4253Request(notice.getDocument());
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
            if (null == paymentOrder) {
                LOGGER.error("充值通知处理异常[{}],支付订单不存在", sn);
                throw new PayException("充值通知处理异常");
            }

            BigDecimal amount = new BigDecimal(txNotice.getAmount());
            String paymentAccountNumber = txNotice.getPaymentAccountNumber();
            String paymentNo = txNotice.getPaymentNo();

            CpcnPayAccountInfo payAcc = cpcnAccService.findByPayAcc(paymentAccountNumber);
            if (null == payAcc) {
                LOGGER.error("充值通知处理异常[{}],账户信息不存在", sn);
                throw new PayException("充值通知处理异常");
            }

            RechargeRecord rechargeRecord = rechargeService.findByOrderNo(sn);
            if (null == rechargeRecord) {
                LOGGER.error("充值通知处理异常[{}],充值记录不存在", sn);
                throw new PayException("充值通知处理异常");
            }
            if (!payAcc.getUserId().equals(rechargeRecord.getUserId())) {
                LOGGER.error("充值通知处理异常[{}],账户信息不一致", sn);
                throw new PayException("充值通知处理异常");
            }
            BigDecimal recordAmount = rechargeRecord.getAmount().multiply(BigDecimal.TEN).multiply(BigDecimal.TEN);
            if (recordAmount.compareTo(amount) != 0 ) {
                LOGGER.error("充值通知处理异常[{}],充值记录金额与通知金额不一致", sn);
                throw new PayException("充值通知处理异常");
            }

            paymentOrder.setResParams(JsonUtils.toJson(txNotice));
            paymentOrderService.merge(paymentOrder);
            rechargeService.rechargeSuccess(paymentNo);

            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PayException("充值通知处理异常");
        }
    }
}
