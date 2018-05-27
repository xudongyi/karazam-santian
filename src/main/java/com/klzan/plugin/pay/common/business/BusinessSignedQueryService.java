package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.PayException;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.user.CpcnAccService;
import com.klzan.plugin.pay.common.dto.SignedRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.tx.paymentaccount.Tx4276Response;

/**
 * 签约查询
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessSignedQueryService extends AbstractBusinessService {

    @Autowired
    private CpcnAccService cpcnAccService;

    @Override
    public Result before(PayModule module) {
        PaymentOrder paymentOrder = createOrder(module, null, PaymentOrderType.SIGNED_QUERY);
        SignedRequest request = (SignedRequest) module.getRequest();
        PaymentOrderType type = request.getOrderType();
        if (type != PaymentOrderType.SIGNED && type != PaymentOrderType.TERMINATION){
            throw new PayException("签约查询订单类型不正确");
        }
        paymentOrder.setParentOrderNo(request.getQueryOrderNo());
        paymentOrderService.merge(paymentOrder);
        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setResParams(JsonUtils.toJson(module.getResponse().getTxResponse()));
        paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
        paymentOrderService.merge(paymentOrder);

        PaymentOrder order = paymentOrderService.findByOrderNo(paymentOrder.getParentOrderNo());
        CpcnPayAccountInfo info = cpcnAccService.findByUserId(order.getUserId());
        if(info == null){
            throw new PayException("");
        }

        Tx4276Response response = (Tx4276Response)module.getResponse().getTxResponse();
        if(response.getStatus() == 10){
            if(response.getAgreementType() == 20){
                info.setChargeAgreementNo(response.getAgreementNo());
            }else if(response.getAgreementType() == 60){
                info.setInvestAgreementNo(response.getAgreementNo());
            }else {
                return Result.error();
            }
        }else if(response.getStatus() == 20){
            if(response.getAgreementType() == 20){
                info.setChargeAgreementNo("");
            }else if(response.getAgreementType() == 60){
                info.setInvestAgreementNo("");
            }else {
                return Result.error();
            }
        }else {
            return Result.error();
        }
        cpcnAccService.merge(info);
        order.setStatus(PaymentOrderStatus.SUCCESS);
        paymentOrderService.merge(order);

        return Result.success();
    }

}
