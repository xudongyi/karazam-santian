package com.klzan.p2p.service.business.ips;

import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.dao.investment.InvestmentDao;
import com.klzan.p2p.dao.investment.InvestmentRecordDao;
import com.klzan.p2p.enums.OrderStatus;
import com.klzan.p2p.enums.OrderType;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.InvestmentRecord;
import com.klzan.p2p.model.Order;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.frozen.vo.IpsPayFrozenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhutao Date: 2017/4/10
 *
 * @version: 1.0
 */
@Service
public class IpsInvestProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvestmentRecordDao investmentRecordDao;

    @Override
    public PaymentOrderStatus process(String orderNo, IDetailResponse response) {
        try {
            lock.lock(LockStack.INVESTMENT_LOCK, "INVEST" + orderNo);
            checkOrder(orderNo);
            IpsPayFrozenResponse investResponse = (IpsPayFrozenResponse) response;
            logger.info(JsonUtils.toJson(investResponse));
            try {
                investmentService.invest(orderNo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            PaymentOrderStatus status;
            OrderStatus orderStatus;

            if ("1".equals(investResponse.getTrdStatus())) {
                status = PaymentOrderStatus.SUCCESS;
                orderStatus = OrderStatus.FROZEN;
            } else {
                status = PaymentOrderStatus.FAILURE;
                orderStatus = OrderStatus.FAILURE;
            }
            InvestmentRecord investmentRecord = investmentRecordDao.findByOrderNo(orderNo);
            orderService.updateOrderStatus(OrderType.INVESTMENT, investmentRecord.getId(), orderStatus, orderNo);
            processOrder(orderNo, investResponse.getIpsBillNo(), status);
            return status;
        } finally {
            lock.unLock(LockStack.INVESTMENT_LOCK, "INVEST" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.FROZEN && type == PaymentOrderType.INVESTMENT;
    }

}
