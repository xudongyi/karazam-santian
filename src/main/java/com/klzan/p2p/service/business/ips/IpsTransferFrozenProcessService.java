package com.klzan.p2p.service.business.ips;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.SnUtils;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.Investment;
import com.klzan.p2p.model.InvestmentRecord;
import com.klzan.p2p.model.Transfer;
import com.klzan.p2p.service.business.BusinessService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.frozen.vo.IpsPayFrozenResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 *
 * 债权转让-冻结
 * Created by suhao Date: 2017/4/7 Time: 18:31
 *
 * @version: 1.0
 */
@Service
public class IpsTransferFrozenProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private InvestmentService investmentService;

    @Autowired
    private InvestmentRecordService investmentRecordService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private BusinessService businessService;

    @Override
    public PaymentOrderStatus process(String orderNo, IDetailResponse response) {
        try {
            logger.info("-------------债权转让冻结,订单号{}----------------------", orderNo);
            lock.lock(LockStack.INVESTMENT_LOCK, "TRANSFER_FROZEN" + orderNo);
            checkOrder(orderNo);
            IpsPayFrozenResponse investResponse = (IpsPayFrozenResponse) response;
            logger.info(JsonUtils.toJson(investResponse));

            PaymentOrderStatus status;
            InvestmentRecord investmentRecord = investmentRecordService.findByOrderNo(orderNo);
            if ("1".equals(investResponse.getTrdStatus())) {
                //新增投资
                Investment pInvestment = new Investment();
                pInvestment.setState(InvestmentState.INVESTING);
                pInvestment.setAmount(investmentRecord.getAmount());
                pInvestment.setPreferentialAmount(BigDecimal.ZERO);
                pInvestment.setBorrowing(investmentRecord.getBorrowing());
                pInvestment.setInvestor(investmentRecord.getInvestor());
                pInvestment.setOrderNo(SnUtils.getOrderNo());
                pInvestment.setTransfer(investmentRecord.getTransferId());
                investmentService.persist(pInvestment);

                status = PaymentOrderStatus.SUCCESS;
                investmentRecord.setInvestment(pInvestment.getId());
                investmentRecord.setState(InvestmentState.PAID);
                investmentRecordService.merge(investmentRecord);

                try {
                    Transfer transfer = transferService.get(investmentRecord.getTransferId());
                    if(!transfer.isCanTransfer()){
                        throw new BusinessProcessException("不可转让");
                    }
                    businessService.transferDo(transfer, pInvestment, investmentRecord, investResponse.getIpsBillNo());
                } catch (Exception e) {
                    logger.error("订单号[{}]债权购买异常:{}", orderNo, ExceptionUtils.getStackTrace(e));
                } finally {

                }

            } else {
                logger.error("订单号[{}]债权购买失败", orderNo);
                investmentRecord.setState(InvestmentState.FAILURE);
                investmentRecordService.merge(investmentRecord);
                status = PaymentOrderStatus.FAILURE;
            }
            processOrder(orderNo, investResponse.getIpsBillNo(), status);
            return status;
        } finally {
            lock.unLock(LockStack.INVESTMENT_LOCK, "TRANSFER_FROZEN" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.FROZEN && type == PaymentOrderType.TRANSFER_FROZEN;
    }

}
