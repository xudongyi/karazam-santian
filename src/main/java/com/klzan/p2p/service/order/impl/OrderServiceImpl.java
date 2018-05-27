package com.klzan.p2p.service.order.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.order.OrderDao;
import com.klzan.p2p.enums.OrderStatus;
import com.klzan.p2p.enums.OrderType;
import com.klzan.p2p.mapper.OrderMapper;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.vo.order.OrderVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 订单
 * Created by suhao Date: 2017/5/11 Time: 14:35
 *
 * @version: 1.0
 */
@Service
@Transactional
public class OrderServiceImpl extends BaseService<Order> implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private InvestmentRecordService investmentRecordService;

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private RepaymentPlanService repaymentPlanService;

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private UserFinanceService userFinanceService;

    @Autowired
    private TransferService transferService;

    @Override
    public Order addOrMergeOrder(OrderVo orderVo) {
        boolean create = false;
        Order order = getByBusinessId(orderVo.getType(), orderVo.getBusiness());
        if (null == order) {
            create = true;
            order = new Order();
            order.setUserId(orderVo.getUserId());
            order.setPayer(orderVo.getPayer());
            order.setPayerName(orderVo.getPayerName());
            order.setPayee(orderVo.getPayee());
            order.setPayeeName(orderVo.getPayeeName());
            order.setMethod(orderVo.getMethod());
            order.setOrderNo(orderVo.getOrderNo());
            order.setAmount(orderVo.getAmount());
            order.setAmountReceived(orderVo.getAmountReceived());
            order.setPayeeFee(orderVo.getPayeeFee());
            order.setPayeeThirdFee(orderVo.getPayeeThirdFee());
            order.setPayerFee(orderVo.getPayerFee());
            order.setPayerThirdFee(orderVo.getPayerThirdFee());
            order.setPoints(0);
            order.setLaunchDate(orderVo.getLaunchDate());
            order.setAuditDate(orderVo.getAuditDate());
            order.setFinishDate(orderVo.getFinishDate());
            order.setOperator(orderVo.getOperator());
            order.setIp(WebUtils.getRemoteIp(WebUtils.getHttpRequest()));
            order.setMemo(orderVo.getMemo());
        }
        order.setPayerBalance(orderVo.getPayerBalance());
        order.setPayeeBalance(orderVo.getPayeeBalance());
        order.setStatus(orderVo.getStatus());
        order.setType(orderVo.getType());
        order.setBusiness(orderVo.getBusiness());
        order.setThirdOrderNo(orderVo.getThirdOrderNo());
        if (create) {
            orderDao.persist(order);
        } else {
            orderDao.merge(order);
        }

        return order;
    }

    @Override
    public Order getByBusinessId(OrderType type, Integer businessId) {
        return orderDao.getByBusinessId(type, businessId);
    }

    @Override
    public PageResult<Order> findPage(PageCriteria criteria, Integer userId, OrderType orderType) {
        return orderDao.findByPage(criteria, userId, orderType);
    }
    @Override
    public PageResult<Order> AppfindPage(PageCriteria criteria, Integer userId, OrderType orderType){
        return orderDao.appFindByPage(criteria, userId, orderType);
    }
    @Override
    public Order updateOrderStatus(OrderType type, Integer businessId, OrderStatus status, String orderNo) {
        if(type==null || businessId == null || status == null){
            throw new RuntimeException("平台订单不存在");
        }
        Order order = orderDao.getByBusinessId(type, businessId);
        if(order == null){
            throw new RuntimeException("平台订单不存在");
        }

        //第三方订单号
        String paymentOrderNo = order.getOrderNo();
        if(!StringUtils.isBlank(orderNo)){
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
            paymentOrderNo = paymentOrder!=null&&paymentOrder.getExtOrderNo()!=null?paymentOrder.getExtOrderNo():"";
        }
        //数据补录
        switch (status){
            case LAUNCH :break;
            case FROZEN :break;
            case AUDITING :break;
            case PROCESSING :break;
            case THIRD_PROCESSING :break;
            case SUCCESS :{
                order.setFinishDate(new Date());
                order.setThirdOrderNo(paymentOrderNo);
                break;
            }
            case FAILURE :{
                order.setFinishDate(new Date());
                order.setThirdOrderNo(paymentOrderNo);
                break;
            }
            case RESCIND :{
                order.setFinishDate(new Date());
                order.setThirdOrderNo(paymentOrderNo);
                break;
            }
            default:break;
        }
        //余额更新
        switch (type){
            case BORROWING :{
                break;
            }
            case INVESTMENT :{
                InvestmentRecord investmentRecord = investmentRecordService.get(businessId);
                Borrowing borrowing = borrowingService.get(investmentRecord.getBorrowing());
                UserFinance borrowerFinance = userFinanceService.getByUserId(borrowing.getBorrower());
                UserFinance investorFinance = userFinanceService.getByUserId(investmentRecord.getInvestor());
//                userFinanceService.flush();
                order.setPayerBalance(investorFinance.getAvailable());
                order.setPayeeBalance(borrowerFinance.getAvailable());
                break;
            }
            case REPAYMENT :{
                Repayment repayment = repaymentService.get(businessId);
                UserFinance borrowerFinance = userFinanceService.getByUserId(repayment.getBorrower());
//                userFinanceService.flush();
                order.setPayerBalance(borrowerFinance.getAvailable());
                break;
            }
            case RECOVERY :{
                break;
            }
            case REPAYMENT_EARLY :{
                Borrowing borrowing = borrowingService.get(businessId);
                UserFinance borrowerFinance = userFinanceService.getByUserId(borrowing.getBorrower());
                order.setPayerBalance(borrowerFinance.getBalance());
                break;
            }
            case TRANSFER_IN: {
                InvestmentRecord investmentRecord = investmentRecordService.get(businessId);
                Transfer transfer = transferService.get(investmentRecord.getTransferId());
                UserFinance transferFinance = userFinanceService.getByUserId(transfer.getTransfer());
                UserFinance investorFinance = userFinanceService.getByUserId(investmentRecord.getInvestor());
                order.setPayerBalance(investorFinance.getAvailable());
                order.setPayeeBalance(transferFinance.getAvailable());
                break;
            }
            case TRANSFER_OUT: {
                InvestmentRecord investmentRecord = investmentRecordService.get(businessId);
                Transfer transfer = transferService.get(investmentRecord.getTransferId());
                UserFinance transferFinance = userFinanceService.getByUserId(transfer.getTransfer());
                UserFinance investorFinance = userFinanceService.getByUserId(investmentRecord.getInvestor());
                order.setPayerBalance(investorFinance.getAvailable());
                order.setPayeeBalance(transferFinance.getAvailable());
                break;
            }
            default:break;
        }
        //状态更新
        order.setStatus(status);
        order = orderDao.merge(order);
        System.out.println("--------订单已更新----------："+order.getId()+"-"+order.getOrderNo()+"-"+status);
        return order;
    }

    @Override
    public Order findOrder(OrderType type, Integer businessId, OrderStatus status, String orderNo) {
        return orderDao.findOrder(type, businessId, status, orderNo);
    }

//    @Override
//    public Order getByOrderNo(String orderNo) {
//        return orderDao.getByOrderNo(orderNo);
//    }

}
