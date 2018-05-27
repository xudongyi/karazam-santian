package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.CpcnSettlementStatus;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnSettlement;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.repayment.CpcnSettlementService;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目批量结算
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessProjectSettlementBatchService extends AbstractBusinessService {

    @Autowired
    protected CpcnSettlementService cpcnSettlementService;

    @Override
    public Result before(PayModule module) {
//        ProjectSettlementBatchRequest request = (ProjectSettlementBatchRequest) module.getRequest();

        PaymentOrder paymentOrder = createOrder(module, null, PaymentOrderType.SETTLEMENT_BATCH);
        paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
        paymentOrderService.merge(paymentOrder);

        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setResParams(JsonUtils.toJson(result.getData()));

        CpcnSettlement cpcnSettlement = cpcnSettlementService.findByOrderNoSettlement(paymentOrder.getOrderNo());

        if(!result.isSuccess()){
            cpcnSettlement.setsStatus(CpcnSettlementStatus.failure);
            cpcnSettlementService.merge(cpcnSettlement);
            paymentOrder.setStatus(PaymentOrderStatus.FAILURE);
            paymentOrderService.merge(paymentOrder);
            return Result.error();
        }
        cpcnSettlement.setsStatus(CpcnSettlementStatus.settling);
        cpcnSettlementService.merge(cpcnSettlement);
        paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
        paymentOrderService.merge(paymentOrder);
        return Result.proccessing();
    }
}
