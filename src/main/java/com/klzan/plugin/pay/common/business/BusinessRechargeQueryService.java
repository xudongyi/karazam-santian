package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.recharge.RechargeService;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.tx.paymentaccount.Tx4252Response;

/**
 * 充值查询
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessRechargeQueryService extends AbstractBusinessService {

    @Autowired
    private RechargeService rechargeService;

    @Override
    public Result before(PayModule module) {
        createOrder(module, null, PaymentOrderType.RECHARGE_QUERY);
        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setResParams(JsonUtils.toJson(module.getResponse().getTxResponse()));
        paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
        paymentOrderService.merge(paymentOrder);

        Tx4252Response response = (Tx4252Response)module.response.getTxResponse();

        // 10=未支付 20=已支付
        if (response.getStatus() == 10) {

        } else if (response.getStatus() == 20) {
            rechargeService.rechargeSuccess(paymentOrder.getParentOrderNo());
        }

        return result;
    }

}
