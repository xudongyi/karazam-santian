package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.PayException;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.user.CpcnAccService;
import com.klzan.plugin.pay.common.dto.TerminationRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 开户
 */
@Service
public class BusinessTerminationService extends AbstractBusinessService{

    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private CpcnAccService cpcnAccService;

    @Override
    public Result before(PayModule module) {

        TerminationRequest request = (TerminationRequest) module.getRequest();

        createOrder(module, request.getUserId(), PaymentOrderType.SIGNED);

        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setResParams(JsonUtils.toJson(result.getData()));
        if(!result.isSuccess()){
            paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
        }else {
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);

            CpcnPayAccountInfo info = cpcnAccService.findByUserId(paymentOrder.getUserId());
            if(info == null){
                throw new PayException("");
            }
            TerminationRequest request = JsonUtils.toObject(paymentOrder.getBusReqParams(), TerminationRequest.class);
            if(StringUtils.isNotBlank(info.getChargeAgreementNo()) && info.getChargeAgreementNo().equals(request.getAgreementNo())){
                info.setChargeAgreementNo("");
            }else if(StringUtils.isNotBlank(info.getInvestAgreementNo()) && info.getInvestAgreementNo().equals(request.getAgreementNo())){
                info.setInvestAgreementNo("");
            }else {
                throw new PayException("");
            }
            cpcnAccService.merge(info);
        }
        paymentOrderService.merge(paymentOrder);
        return result;
    }

}
