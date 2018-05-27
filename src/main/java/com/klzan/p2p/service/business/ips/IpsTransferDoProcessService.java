package com.klzan.p2p.service.business.ips;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.LendingRecordService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.transfer.vo.IpsPayTransferResponse;
import com.klzan.plugin.pay.ips.transfer.vo.TransferAccDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * 债权转让-转账
 * Created by suhao Date: 2017/4/7 Time: 18:31
 *
 * @version: 1.0
 */
@Service
public class IpsTransferDoProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private UserService userService;

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private InvestmentRecordService investmentRecordService;

    @Autowired
    private LendingRecordService lendingRecordService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TransferService transferService;

    @Override
    public Boolean process(String orderNo, IDetailResponse response) {
        try {
            logger.info("-------------债权转让转账,订单号{}----------------------", orderNo);
            lock.lock(LockStack.USER_LOCK, "TRANSFER_DO" + orderNo);
            checkOrder(orderNo);
            IpsPayTransferResponse responseResult = (IpsPayTransferResponse) response;
            logger.info(JsonUtils.toJson(responseResult));

            String batchOrderNo = orderNo;
            List<LendingRecord> lendingRecords = lendingRecordService.findByBatchOrderNo(batchOrderNo);
            if (!lendingRecords.isEmpty() && lendingRecords.size() == 1) {
                LendingRecord lendingRecord = lendingRecords.get(0);
                InvestmentRecord investmentRecord = investmentRecordService.findByOrderNo(lendingRecord.getInvestOrderNo());
                Investment investment = investmentService.get(investmentRecord.getInvestment());
                List<TransferAccDetailResponse> transferAccDetail1 = responseResult.getTransferAccDetail();
                for (TransferAccDetailResponse detailResponse : transferAccDetail1) {
                    Transfer transfer = transferService.get(investment.getTransfer());
                    transfer = transferService.refresh(transfer);
                    if(!transfer.isCanTransfer()){
                        throw new BusinessProcessException("不可转让");
                    }
                    User user = userService.get(investment.getInvestor());
                    PaymentOrderStatus status;
                    // 成功
                    if (StringUtils.equals(detailResponse.getTrdStatus(), "1")) {
                        transferService.transferIn(transfer, investment, investmentRecord, user, investment.getAmount().divide(new BigDecimal(100), 0, BigDecimal.ROUND_DOWN).intValue());
                        status = PaymentOrderStatus.SUCCESS;
                    } else {
                        //更新投资
                        investment.setState(InvestmentState.FAILURE);
                        investmentService.merge(investment);
                        investmentRecord.setState(InvestmentState.FAILURE);
                        investmentRecordService.merge(investmentRecord);

                        orderService.updateOrderStatus(OrderType.TRANSFER_OUT, investmentRecord.getId(), OrderStatus.FAILURE, investmentRecord.getOrderNo());
                        orderService.updateOrderStatus(OrderType.TRANSFER_IN, investmentRecord.getId(), OrderStatus.FAILURE, investmentRecord.getOrderNo());
                        status = PaymentOrderStatus.FAILURE;
                    }
                    processOrder(orderNo, detailResponse.getIpsBillNo(), status);
                }
            }

            return true;
        } finally {
            lock.unLock(LockStack.USER_LOCK, "TRANSFER_DO" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.TRANSFER && type == PaymentOrderType.TRANSFER;
    }

}
