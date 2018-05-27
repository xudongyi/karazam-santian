package com.klzan.p2p.service.business.ips;

import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.enums.SignType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.user.UserMetaService;
import com.klzan.p2p.vo.user.UserAutoInvestVo;
import com.klzan.p2p.vo.user.UserAutoRepayVo;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.autosign.vo.IpsPayAutoSignResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by suhao Date: 2017/4/7 Time: 18:31
 *
 * @version: 1.0
 */
@Service
public class IpsAutoRepaymentSignProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private UserMetaService userMetaService;

    @Override
    public String process(String orderNo, IDetailResponse response) {
        try {
            lock.lock(LockStack.USER_LOCK, "AUTO_REPAY_SIGN" + orderNo);
            checkOrder(orderNo);
            IpsPayAutoSignResponse autoSignResponse = (IpsPayAutoSignResponse) response;
            logger.info(JsonUtils.toJson(autoSignResponse));
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
            Integer userId = paymentOrder.getUserId();

            // 处理
            // 1-成功 0-失败
            if (StringUtils.equals(autoSignResponse.getStatus(), "1")) {
                UserAutoRepayVo autoRepayVo = new UserAutoRepayVo();
                autoRepayVo.setUserId(userId);
                autoRepayVo.setAutoRepaySign(true);
                autoRepayVo.setAuthNo(autoSignResponse.getIpsAuthNo());
                autoRepayVo.setValidity(Integer.parseInt(autoSignResponse.getValidity()));
                autoRepayVo.setStatus(true);

                userMetaService.addMetasByType(SignType.AUTO_REPAYMENT_SIGN, autoRepayVo);
            }
            processOrder(orderNo, autoSignResponse.getIpsBillNo(), PaymentOrderStatus.SUCCESS);
            return autoSignResponse.getIpsAcctNo();
        } finally {
            lock.unLock(LockStack.USER_LOCK, "AUTO_REPAY_SIGN" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.AUTO_SIGN && type == PaymentOrderType.AUTO_REPAYMENT_SIGN;
    }

}
