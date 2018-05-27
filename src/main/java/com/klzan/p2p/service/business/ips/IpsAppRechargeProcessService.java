package com.klzan.p2p.service.business.ips;

import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.service.recharge.RechargeService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.appRecharge.vo.IpsPayAppRechargeResponse;
import com.klzan.plugin.pay.ips.recharge.vo.IpsPayRechargeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by suhao Date: 2017/4/7 Time: 18:31
 *
 * @version: 1.0
 */
@Service
public class IpsAppRechargeProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private RechargeService rechargeService;

    @Override
    public String process(String orderNo, IDetailResponse response) {
        try {
            lock.lock(LockStack.USER_LOCK, "RECHARGE" + orderNo);
            checkOrder(orderNo);
            IpsPayAppRechargeResponse rechargeResponse = (IpsPayAppRechargeResponse) response;
            logger.info(JsonUtils.toJson(rechargeResponse));
            PaymentOrderStatus status;
            String pStatus = rechargeResponse.getTrdStatus();
            // 成功
            if (StringUtils.equals(pStatus, "1")) {
                status = PaymentOrderStatus.SUCCESS;
                rechargeService.appRechargeSuccess(orderNo, rechargeResponse);
            }
            // 处理中
            else if (StringUtils.equals(pStatus, "2")) {
                status = PaymentOrderStatus.CANCEL;
            }
            // 失败
            else {
                status = PaymentOrderStatus.FAILURE;
            }

            processOrder(orderNo, rechargeResponse.getIpsBillNo(), status);
            return rechargeResponse.getIpsBillNo();
        } finally {
            lock.unLock(LockStack.USER_LOCK, "RECHARGE" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.MOBILE_RECHARGE && type == PaymentOrderType.MOBILE_RECHARGE;
    }

}
