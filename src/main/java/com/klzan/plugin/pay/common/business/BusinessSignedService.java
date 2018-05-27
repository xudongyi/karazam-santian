package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.PayException;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.user.CpcnAccService;
import com.klzan.plugin.pay.common.dto.SignedRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.notice.Notice4278Request;
import payment.api.notice.NoticeRequest;

/**
 * 开户
 */
@Service
public class BusinessSignedService extends AbstractBusinessService{

    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private CpcnAccService cpcnAccService;

    @Override
    public Result before(PayModule module) {

        SignedRequest request = (SignedRequest) module.getRequest();

        createOrder(module, request.getUserId(), PaymentOrderType.SIGNED);

        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
        paymentOrderService.merge(paymentOrder);
        return result;
    }

    @Override
    public Result notice(String sn, NoticeRequest notice) {
        try {
            Notice4278Request txNotice = new Notice4278Request(notice.getDocument());
            System.out.println(JsonUtils.toJson(txNotice));

            // 更新订单
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
            paymentOrder.setResParams(JsonUtils.toJson(txNotice));
            CpcnPayAccountInfo info = cpcnAccService.findByUserId(paymentOrder.getUserId());
            if(info == null){
                throw new PayException("");
            }

            SignedRequest request = JsonUtils.toObject(paymentOrder.getBusReqParams(), SignedRequest.class);

            if(request.getAgreementType().equals("20") && txNotice.getAgreementType().equals("20")){
                info.setChargeAgreementNo(txNotice.getAgreementNo());
            }else if(request.getAgreementType().equals("60") && txNotice.getAgreementType().equals("60")){
                info.setInvestAgreementNo(txNotice.getAgreementNo());
            }else {
                return Result.error();
            }
            cpcnAccService.merge(info);
            return Result.success();
        } catch (Exception e) {
            throw new PayException("通知处理异常");
        }
    }

}
