package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.PayException;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.tx.p2p.Tx3256Response;
import payment.api.vo.P2PLoanerItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * 项目支付查询-自动
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessInvestmentAutoQueryService extends AbstractBusinessService {
    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private PayUtils payUtils;

    @Override
    public Result before(PayModule module) {
        createOrder(module, null, PaymentOrderType.AUTO_INVESTMENT_QUERY);
        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setResParams(JsonUtils.toJson(module.getResponse().getTxResponse()));
        paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
        paymentOrderService.merge(paymentOrder);

        Tx3256Response response = (Tx3256Response)module.response.getTxResponse();
        String batchNo = response.getBatchNo();
        String projectNo = response.getProjectNo();
        List<P2PLoanerItem> loaners = response.getLoaners();
        Borrowing borrowing = borrowingService.findByProjectNo(projectNo);

        for (P2PLoanerItem loaner : loaners) {
            String sn = loaner.getPaymentNo();
            // 10=未支付 20=已支付
            if (loaner.getStatus() == 10) {

            } else if (loaner.getStatus() == 20) {
                PaymentOrder order = paymentOrderService.findByOrderNo(sn);
                CpcnPayAccountInfo accountInfo = payUtils.getCpcnPayAccountInfo(order.getUserId());
                BigDecimal amount = PayUtils.convertToYuan(loaner.getAmount());
                String investorAccountNumber = loaner.getLoanerPaymentAccountNumber();
                BigDecimal orderAmount = order.getAmount();
                if (amount.compareTo(orderAmount) != 0) {
                    LOGGER.error("项目支付查询处理异常[{}],投资金额与订单金额不一致", sn);
                    throw new PayException("项目支付查询处理异常");
                }
                if (!StringUtils.equals(accountInfo.getAccountNumber(), investorAccountNumber)) {
                    LOGGER.error("项目支付查询处理异常[{}],投资账号与订单账号不一致", sn);
                    throw new PayException("项目支付查询处理异常");
                }
                investmentService.invest(sn);
            }
        }

        return result;
    }

}
