package com.klzan.p2p.service.user.impl;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.user.CpcnAccDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.capital.CapitalService;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.message.MessagePushService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.user.CpcnAccService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.vo.order.OrderVo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;

/**
 * Created by suhao Date: 2017/11/23 Time: 17:22
 *
 * @version: 1.0
 */
@Service
public class CpcnAccServiceImpl extends BaseService<CpcnPayAccountInfo> implements CpcnAccService {
    @Autowired
    private CpcnAccDao cpcnAccDao;
    @Inject
    private DistributeLock distributeLock;
    @Inject
    private PaymentOrderService paymentOrderService;
    @Inject
    private UserFinanceService userFinanceService;
    @Inject
    private UserService userService;
    @Inject
    private OrderService orderService;
    @Inject
    private CapitalService capitalService;
    @Inject
    private MessagePushService messagePushService;

    @Override
    public CpcnPayAccountInfo findByPayAcc(String payAccount) {
        return cpcnAccDao.findByPayAcc(payAccount);
    }

    @Override
    public CpcnPayAccountInfo findByUserId(Integer userId) {
        return cpcnAccDao.findByUserId(userId);
    }

    @Override
    public void orgTransferSuccess(String orderNo) {
        try {
            distributeLock.lock(LockStack.USER_LOCK, orderNo);
            logger.info("begin to process orgTransfer order {}", orderNo);
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
            if (null == paymentOrder) {
                throw new BusinessProcessException(String.format("充值订单[%s]不存在", orderNo));
            }
            if (paymentOrder.getStatus() == PaymentOrderStatus.SUCCESS) {
                throw new BusinessProcessException(String.format("充值订单[%s]已处理", orderNo));
            }

            UserFinance userFinance = userFinanceService.findByUserId(paymentOrder.getUserId());
            // 更新用户余额
            BigDecimal amount = paymentOrder.getAmount();
            userFinance.addBalance(amount, RechargeBusinessType.GENERAL);
            userFinanceService.update(userFinance);

            // 生成资金记录
            User user = userService.get(paymentOrder.getUserId());
            Capital capital = new Capital(userFinance.getUserId(),
                    CapitalMethod.ORG_TRANSFER,
                    CapitalType.CREDIT,
                    amount,
                    userFinance,
                    orderNo,
                    user.getLoginName(),
                    WebUtils.getRemoteIp(WebUtils.getHttpRequest()),
                    "机构支付账户单笔转账",
                    null);

            userFinanceService.flush();
            Order order = orderService.addOrMergeOrder(new OrderVo(userFinance.getAvailable(),
                    userFinance.getAvailable(),
                    OrderStatus.SUCCESS,
                    OrderType.RECHARGE,
                    null,
                    paymentOrder.getExtOrderNo()
            ));
            order.setAmountReceived(amount);
            orderService.merge(order);
            capital.setOrderId(order.getId());
            capitalService.persist(capital);
//            messagePushService.rechargeSuccPush(paymentOrder.getUserId(), paymentOrder.getAmount());
        } catch (Exception e) {
            logger.error("orgTransfer fail order {}, {}", orderNo, ExceptionUtils.getMessage(e));
        } finally {
            distributeLock.unLock(LockStack.USER_LOCK, orderNo);
        }
    }
}
