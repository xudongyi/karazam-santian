package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.stereotype.Service;

/**
 * 余额查询
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessAccountBalanceQueryService extends AbstractBusinessService {

    @Override
    public Result before(PayModule module) {
        createOrder(module, null, PaymentOrderType.BALANCE_QUERY);
        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setResParams(JsonUtils.toJson(module.getResponse().getTxResponse()));
        paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
        paymentOrderService.merge(paymentOrder);
        return result;
    }

}
