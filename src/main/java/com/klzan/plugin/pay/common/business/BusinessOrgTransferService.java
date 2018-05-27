package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.user.CpcnAccService;
import com.klzan.plugin.pay.common.dto.OrgTransferRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 开户
 */
@Service
public class BusinessOrgTransferService extends AbstractBusinessService{

    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private CpcnAccService cpcnAccService;

    @Override
    public Result before(PayModule module) {

        OrgTransferRequest request = (OrgTransferRequest) module.getRequest();

        PaymentOrder paymentOrder = createOrder(module, request.getUserId(), PaymentOrderType.ORG_TRANSFER);
        paymentOrder.setAmount(request.getAmount());
        paymentOrderService.merge(paymentOrder);

        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setResParams(JsonUtils.toJson(result.getData()));
        if(!result.isSuccess()){
            paymentOrder.setStatus(PaymentOrderStatus.FAILURE);
        }else {
            paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
        }
        paymentOrderService.merge(paymentOrder);
        return result;
    }

}
