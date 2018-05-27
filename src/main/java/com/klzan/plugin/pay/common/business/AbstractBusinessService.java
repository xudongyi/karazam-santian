package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.PaymentOrderMethod;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import payment.api.notice.NoticeRequest;

/**
 * 抽象服务类
 * Created by suhao Date: 2017/11/23 Time: 15:45
 *
 * @version: 1.0
 */
public abstract class AbstractBusinessService implements BusinessService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BusinessService.class);

    @Autowired
    protected PaymentOrderService paymentOrderService;

    protected PaymentOrder createOrder(PayModule module, Integer userId, PaymentOrderType paymentOrderType) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUserId(userId);
        paymentOrder.setStatus(PaymentOrderStatus.NEW_CREATE);
        paymentOrder.setType(paymentOrderType);
        paymentOrder.setMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
        if (module.getRequest() instanceof SnRequest) {
            paymentOrder.setParentOrderNo(((SnRequest)module.getRequest()).getSn());
        }
        paymentOrder.setOrderNo(module.getSn());
        paymentOrder.setMemo(module.getPayPortal().getAlias());
        paymentOrder.setReqParams(JsonUtils.toJson(module.getTxRequest()));
        paymentOrder.setBusReqParams(JsonUtils.toJson(module.getRequest()));
        paymentOrder.setIp(CommonUtils.getRemoteIp());
        paymentOrder.setOperator(CommonUtils.getLoginName());
        paymentOrder.setIsMobile(module.getRequest().isMobile());
        paymentOrder = paymentOrderService.persist(paymentOrder);
        return paymentOrder;
    }

    @Override
    public Result notice(String sn, NoticeRequest notice) {
        return Result.error("默认notice");
    }
}
