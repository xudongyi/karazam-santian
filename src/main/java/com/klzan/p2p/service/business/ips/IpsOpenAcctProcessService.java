package com.klzan.p2p.service.business.ips;

import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.enums.UserMetaType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.user.UserMetaService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.user.UserOpenIpsAcctVo;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.IpsPayConfig;
import com.klzan.plugin.pay.ips.openacc.vo.IpsPayOpenAccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by suhao Date: 2017/4/7 Time: 18:31
 *
 * @version: 1.0
 */
@Service
public class IpsOpenAcctProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMetaService userMetaService;

    @Override
    public String process(String orderNo, IDetailResponse response) {
        try {
            lock.lock(LockStack.USER_LOCK, "OPEN_ACCOUNT" + orderNo);
            checkOrder(orderNo);
            IpsPayOpenAccountResponse openAccountResponse = (IpsPayOpenAccountResponse) response;
            logger.info(JsonUtils.toJson(openAccountResponse));
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
            Integer userId = paymentOrder.getUserId();
            PaymentOrderStatus orderStatus = PaymentOrderStatus.SUCCESS;
            if (StringUtils.equals(openAccountResponse.getStatus(), "1")) {
                User user = userService.get(userId);
                user.setPayAccountNo(openAccountResponse.getIpsAcctNo());
                userService.merge(user);

                UserOpenIpsAcctVo openIpsAcctVo = new UserOpenIpsAcctVo();
                openIpsAcctVo.setUserId(userId);
                openIpsAcctVo.setIpsAcctNo(openAccountResponse.getIpsAcctNo());
                openIpsAcctVo.setIpsBillNo(openAccountResponse.getIpsBillNo());
                openIpsAcctVo.setMerBillNo(openAccountResponse.getMerBillNo());
                openIpsAcctVo.setIpsDoTime(openAccountResponse.getIpsDoTime());
                openIpsAcctVo.setStatus(openAccountResponse.getStatus());
                openIpsAcctVo.setIpsLoginName(new StringBuilder(IpsPayConfig.USER_NAME_PREFIX).append(user.getType().getTag()).append(user.getMobile()).toString());
                userMetaService.addMetasByType(UserMetaType.IPS_OPEN_ACCT, openIpsAcctVo);
            } else if (StringUtils.equals(openAccountResponse.getStatus(), "0")) {
                orderStatus = PaymentOrderStatus.FAILURE;
            } else if (StringUtils.equals(openAccountResponse.getStatus(), "2")) {
                orderStatus = PaymentOrderStatus.WAITING_PROCESS;
            }
            processOrder(orderNo, openAccountResponse.getIpsBillNo(), orderStatus);
            return openAccountResponse.getIpsAcctNo();
        } finally {
            lock.unLock(LockStack.USER_LOCK, "OPEN_ACCOUNT" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.OPEN_ACCOUNT && type == PaymentOrderType.OPEN_ACCOUNT;
    }

}
