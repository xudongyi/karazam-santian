package com.klzan.p2p.task;

import com.klzan.core.Result;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.SnUtils;
import com.klzan.core.util.SpringUtils;
import com.klzan.p2p.common.RepaymentCom;
import com.klzan.p2p.controller.uc.RepaymentController;
import com.klzan.p2p.dao.capital.PaymentOrderDao;
import com.klzan.p2p.dao.postloan.CpcnSettlementDao;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnSettlement;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.repayment.CpcnSettlementService;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.dto.ProjectSettlementBatchRequest;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.common.module.PayModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 富民订单
 * @version: 1.0
 */
@Component("orderTask")
public class OrderTask {
    private static final Logger logger = LoggerFactory.getLogger(OrderTask.class);

    @Autowired
    private PaymentOrderDao paymentOrderDao;
    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private PayUtils payUtils;
    @Autowired
    private CpcnSettlementService cpcnSettlementService;
    @Autowired
    private CpcnSettlementDao cpcnSettlementDao;
    @Autowired
    private RepaymentCom repaymentCom;
//    @Autowired
//    private PayQueryService payQueryService;

    /**
     *
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void repayment() {

        logger.info(String.format("还款定时任务处理开始："));
        List<CpcnSettlement> settlements = cpcnSettlementService.findUnSettlement();
        if(settlements == null || settlements.size()==0){
            logger.info(String.format("还款定时任务处理结束：无处理中还款"));
            return;
        }
        logger.info(String.format("还款定时任务:待处理订单：[%s]条", settlements.size()));
        for(int i=0; i<settlements.size(); i++){
            try {
                CpcnSettlement cpcnSettlement = settlements.get(i);
                logger.info(String.format("还款定时任务:第[%s]条,剩余[%s]条,还款[%s]", i+1, settlements.size()-(i+1), cpcnSettlement.getId()));
                Result result = repaymentCom.repayCarry(cpcnSettlement);
                logger.info(String.format("还款定时任务:还款[%s]处理结果:[%s]", cpcnSettlement.getId(), JsonUtils.toJson(result)));
            }catch(Exception e){
                logger.error(e.getMessage());
//                e.printStackTrace();
            }
        }

    }

//    @Scheduled(cron = "0 0/2 * * * ?")
//    public void transfe() {
//        logger.info(String.format("还款定时任务处理开始："));
//        List<CpcnSettlement> settlements = cpcnSettlementDao.findUnSettlementTransfer();
//        if(settlements == null || settlements.size()==0){
//            logger.info(String.format("还款定时任务处理结束：无处理中还款"));
//            return;
//        }
//        logger.info(String.format("还款定时任务:待处理订单：[%s]条", settlements.size()));
//        for(int i=0; i<settlements.size(); i++){
//            try {
//                CpcnSettlement cpcnSettlement = settlements.get(i);
//                logger.info(String.format("还款定时任务:第[%s]条,剩余[%s]条,还款[%s]", i+1, settlements.size()-(i+1), cpcnSettlement.getId()));
//
//                List<PaymentOrder> orders = paymentOrderService.findByParentOrderNo(cpcnSettlement.getsOrderNo());  // 结算子订单（放款/放款服务费/回款/回款服务费/提前回款/提前回款服务费）
//                for(PaymentOrder order : orders){
////                    if(order.getStatus().equals(PaymentOrderStatus.NEW_CREATE)){
//                        order.setOrderNo(SnUtils.getOrderNo());
//                        order.setStatus(PaymentOrderStatus.PROCESSING);
//                    paymentOrderService.merge(order);
////                    }
//                }
//                PayModule payModule = PayPortal.project_settlement_batch.getModuleInstance();
//                ProjectSettlementBatchRequest request = new ProjectSettlementBatchRequest();
//                request.setSettlement(cpcnSettlement);
//                request.setOrders(orders);
//                payModule.setRequest(request);
//                payModule.setSn(cpcnSettlement.getsOrderNo());
//                Response response = payModule.invoking().getResponse();
//                Result result = null;
//                result = Result.error("结算异常："+response.getMsg());
//                if(response.isError()){
//                    result = Result.error("结算失败："+response.getMsg());
//                }
//                if(response.isProccessing()){
//                    result = Result.proccessing("结算中："+response.getMsg());
//                }
//                logger.info(String.format("还款定时任务:还款[%s]处理结果:[%s]", cpcnSettlement.getId(), JsonUtils.toJson(result)));
//            }catch(Exception e){
//                logger.error(e.getMessage());
//                e.printStackTrace();
//            }
//        }
//
//    }

    /**
     * 订单任务
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void deal() {

        logger.info(String.format("订单定时任务处理开始："));
        List<PaymentOrder> orders = paymentOrderDao.findListForTask(PaymentOrderStatus.PROCESSING);
        if(orders == null || orders.size()==0){
            logger.info(String.format("订单定时任务处理结束：无处理中订单"));
            return;
        }
        logger.info(String.format("订单定时任务:待处理订单：[%s]条", orders.size()));
        for(int i=0; i<orders.size(); i++){
            try {
                PaymentOrder order = orders.get(i);
                if (order.getType() == PaymentOrderType.BINDCARD_BIND || order.getType() == PaymentOrderType.BINDCARD_UNBIND) {
                    logger.info(String.format("订单定时任务:第[%s]条为[%s],订单号[%s]", i + 1, orders.size() - (i + 1), order.getType(), order.getOrderNo()));
                    continue;
                }
                logger.info(String.format("订单定时任务:第[%s]条,剩余[%s]条,订单[%s]", i+1, orders.size()-(i+1), order.getOrderNo()));
                Response result = payUtils.query(order.getOrderNo());
                logger.info(String.format("订单定时任务:订单[%s]处理结果:[%s]", order.getOrderNo(), JsonUtils.toJson(result)));
            }catch(Exception e){
                logger.error(e.getMessage());
//                e.printStackTrace();
            }
        }

    }

    /**
     * 提现订单任务
     */
    @Scheduled(cron = "0 0 0/3 * * ?")
    public void dealWithdraw() {

        logger.info(String.format("提现定时任务处理开始："));
        List<PaymentOrder> orders = paymentOrderDao.findWithdrawListForTask(PaymentOrderStatus.PROCESSING);
        if(orders == null || orders.size()==0){
            logger.info(String.format("提现定时任务处理结束：无处理中订单"));
            return;
        }
        logger.info(String.format("提现定时任务:待处理订单：[%s]条", orders.size()));
        for(int i=0; i<orders.size(); i++){
            try {
                PaymentOrder order = orders.get(i);
//                if(!order.getType().name().startsWith("cpcn")){
//                    continue;
//                }
//                Date date = DateUtils.addDays(order.getCreateDate(), 1);
//                if(date.compareTo(new Date())<0){  //订单一天以内不处理（T+1到账）
//                    continue;
//                }
                logger.info(String.format("提现定时任务:第[%s]条,剩余[%s]条,订单[%s]", i+1, orders.size()-(i+1), order.getOrderNo()));
                Response result = payUtils.query(order.getOrderNo());
                logger.info(String.format("提现定时任务:订单[%s]处理结果:[%s]", order.getOrderNo(), JsonUtils.toJson(result)));
            }catch(Exception e){
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /**
     * 充值订单任务
     */
    @Scheduled(cron = "0 0 0/2 * * ?")
    public void dealRecharge() {

        logger.info(String.format("充值定时任务处理开始："));
        List<PaymentOrder> orders = paymentOrderDao.findRechargeListForTask(PaymentOrderStatus.PROCESSING);
        if(orders == null || orders.size()==0){
            logger.info(String.format("充值定时任务处理结束：无处理中订单"));
            return;
        }
        logger.info(String.format("充值定时任务:待处理订单：[%s]条", orders.size()));
        for(int i=0; i<orders.size(); i++){
            try {
                PaymentOrder order = orders.get(i);
                Date date = DateUtils.addDays(order.getCreateDate(), 2);
                if(date.compareTo(new Date())>0){   //订单超过两天不处理（2天后一般失败，等待通知或手动处理）
                    continue;
                }
                logger.info(String.format("充值定时任务:第[%s]条,剩余[%s]条,订单[%s]", i+1, orders.size()-(i+1), order.getOrderNo()));
                Response result = payUtils.query(order.getOrderNo());
                logger.info(String.format("充值定时任务:订单[%s]处理结果:[%s]", order.getOrderNo(), JsonUtils.toJson(result)));
            }catch(Exception e){
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }

    }
}
