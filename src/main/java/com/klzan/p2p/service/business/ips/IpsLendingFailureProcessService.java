package com.klzan.p2p.service.business.ips;

import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.model.ReferralFee;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.user.ReferralFeeService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.unfreeze.vo.IpsPayUnfreezeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * 出借失败-解冻
 * Created by suhao Date: 2017/4/7 Time: 18:31
 *
 * @version: 1.0
 */
@Service
public class IpsLendingFailureProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ReferralFeeService referralFeeService;

    @Override
    public Boolean process(String orderNo, IDetailResponse response) {
        try {
            lock.lock(LockStack.USER_LOCK, "LENDING_CANCEL" + orderNo);
            checkOrder(orderNo);
            IpsPayUnfreezeResponse responseResult = (IpsPayUnfreezeResponse) response;
            logger.info(JsonUtils.toJson(responseResult));

            Boolean isSucc = false;
            PaymentOrderStatus status;
            // 成功
            if (StringUtils.equals(responseResult.getTrdStatus(), "1")) {
                isSucc = true;
                status = PaymentOrderStatus.SUCCESS;
            } else {
                //更新
                isSucc = false;
                status = PaymentOrderStatus.FAILURE;
            }
            processOrder(orderNo, responseResult.getIpsBillNo(), status);
            return isSucc;
        } finally {
            lock.unLock(LockStack.USER_LOCK, "LENDING_CANCEL" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.UNFREEZE && type == PaymentOrderType.LENDING_CANCEL;
    }

}
