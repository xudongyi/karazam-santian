package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.CpcnSettlementStatus;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnSettlement;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.repayment.CpcnSettlementService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.tx.p2p.Tx3262Response;
import payment.api.vo.RongziProjectSettlementBatItem;

import java.util.List;

/**
 * 项目批量结算查询
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessProjectSettlementBatchQueryService extends AbstractBusinessService {
    @Autowired
    protected CpcnSettlementService cpcnSettlementService;
    @Autowired
    protected BorrowingService borrowingService;
    @Autowired
    protected RepaymentService repaymentService;
    @Autowired
    protected TransferService transferService;

    @Override
    public Result before(PayModule module) {
        SnRequest request = (SnRequest)module.getRequest();
        PaymentOrder paymentOrder = createOrder(module, null, PaymentOrderType.SETTLEMENT_BATCH_QUERY);
        paymentOrder.setParentOrderNo(request.getSn());
        paymentOrderService.merge(paymentOrder);

        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {

        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());  // 结算查询订单
        paymentOrder.setResParams(JsonUtils.toJson(result.getData()));
        paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
        paymentOrderService.merge(paymentOrder);

        synchronized (BusinnessLOCK.SETTLEMENT_BATCH_LOCK){
            Tx3262Response response = (Tx3262Response)result.getData();

            String settlementBatchNo = paymentOrder.getParentOrderNo();

            String parentOrderNo = settlementBatchNo;
            CpcnSettlement cpcnSettlement = null;

            PaymentOrder settlementOrder = paymentOrderService.findByOrderNo(settlementBatchNo);  // 结算订单
            if(!settlementOrder.getStatus().equals(PaymentOrderStatus.PROCESSING)){
                return Result.error("该结算已处理", result.getData());
            }
//            paymentOrderService.lock(settlementOrder, LockMode.PESSIMISTIC_WRITE);

//            //判断补单
//            if(!settlementOrder.getReplenish()){
            cpcnSettlement = cpcnSettlementService.findByOrderNoSettlement(settlementBatchNo);
                if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT) || cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT_EARLY)){
                    parentOrderNo = cpcnSettlement.getrOrderNo();
                }
//            }

            List<PaymentOrder> orders = paymentOrderService.findByParentOrderNo(parentOrderNo);  // 结算子订单（放款/放款服务费/回款/回款服务费/提前回款/提前回款服务费）

            int success = 0;
            int failure = 0;
            int processing = 0;
            if(response.getItemList()!=null && response.getItemList().size()>0){
                for(RongziProjectSettlementBatItem item : response.getItemList()){
                    int status = item.getStatus();
                    PaymentOrder orderItem = null;
                    for(PaymentOrder order : orders){
                        if(!order.getStatus().equals(PaymentOrderStatus.PROCESSING)){
                            continue;
                        }
                        if(order.getOrderNo().equals(item.getSettlementNo())){
                            orderItem = order;break;
                        }
                    }
                    if (orderItem == null){
                        continue;
                    }
                    // 10=New20=已受理30=正在结算40=已经执行50=结算失败
                    if(status == 10 || status == 20 || status == 30){
                        orderItem.setStatus(PaymentOrderStatus.PROCESSING);
                        paymentOrderService.merge(orderItem);

                        processing++;
                    }else if(status == 40){
                        orderItem.setStatus(PaymentOrderStatus.SUCCESS);
                        paymentOrderService.merge(orderItem);

//                        if(!settlementOrder.getReplenish()){
                            if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT)){
                                repaymentService.repaySettlementSucceed(cpcnSettlement, orderItem, false);
                            }else if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT_EARLY)){
                                repaymentService.aheadRepaySettlementSucceed(cpcnSettlement, orderItem);
                            }else if(cpcnSettlement.getType().equals(PaymentOrderType.TRANSFER)){
                                transferService.transferInSettlementSucceed(orderItem);
                            }else if(cpcnSettlement.getType().equals(PaymentOrderType.REFUND)){ //流标
                                borrowingService.failureBidSettlementSucceed(orderItem);
                            }
//                        }else {
//                            PaymentOrder originalOrder = paymentOrderService.findByOrderNo(orderItem.getOriginalOrderNo());
//                            originalOrder.setReplenishStatus(ReplenishStatus.SUCCESS);
//                            paymentOrderService.merge(originalOrder);
//                        }
                        success++;
                    }else if(status == 50){
                        orderItem.setStatus(PaymentOrderStatus.FAILURE);
                        paymentOrderService.merge(orderItem);
//                        logger.info("失败的结算订单[%s]", orderItem.getOrderNo());
//                        if(settlementOrder.getReplenish()){
//                            PaymentOrder originalOrder = paymentOrderService.findByOrderNo(orderItem.getOriginalOrderNo());
//                            originalOrder.setReplenishStatus(ReplenishStatus.FAILURE);
//                            paymentOrderService.merge(originalOrder);
//                        }
                        failure++;
                    }else {
                        throw new RuntimeException("接口信息异常："+JsonUtils.toJson(result));
                    }
                }
                if(success>0 && failure==0 && processing==0){
                    settlementOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    paymentOrderService.merge(settlementOrder);

//                    if(!settlementOrder.getReplenish()) {
                    cpcnSettlement.setsStatus(CpcnSettlementStatus.success);
                    cpcnSettlementService.merge(cpcnSettlement);

                    if (cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT)) {
                            repaymentService.repaySettlementSucceed(cpcnSettlement, null, true);
                    } else if (cpcnSettlement.getType().equals(PaymentOrderType.LENDING)) {
                        try {
                            borrowingService.lendingSucceed(cpcnSettlement);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException("接口信息异常："+JsonUtils.toJson(result));
                        }
                    } else if (cpcnSettlement.getType().equals(PaymentOrderType.REFUND)) {
                        try {
                            borrowingService.failureBidSettlementAllSucceed(cpcnSettlement);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException("接口信息异常："+JsonUtils.toJson(result));
                        }
                    }
//                    }
                    return Result.success("本次处理中订单全部成功", result.getData());
                }
                if(success==0 && failure>0 && processing==0){
                    settlementOrder.setStatus(PaymentOrderStatus.FAILURE);
                    paymentOrderService.merge(settlementOrder);
                    cpcnSettlement.setsStatus(CpcnSettlementStatus.failure);
                    cpcnSettlementService.merge(cpcnSettlement);
                    return Result.error("本次处理中订单全部失败", result.getData());
                }
                if(processing>0){
                    String msm = String.format("成功[%s]失败[%s]处理中[%s]", success, failure, processing);
                    return Result.proccessing(msm, result.getData());
                }
                return Result.error("系统异常", result.getData());
            }else {
                throw new RuntimeException("接口信息异常："+JsonUtils.toJson(result));
            }
        }
    }

}
