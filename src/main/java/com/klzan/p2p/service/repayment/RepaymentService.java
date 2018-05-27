/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.repayment;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.RepaymentOperator;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.CpcnSettlement;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.model.Repayment;
import com.klzan.plugin.pay.common.dto.Response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 还款
 * @author: chenxinglin  Date: 2016/11/1 Time: 17:07
 */
public interface RepaymentService extends IBaseService<Repayment> {

    /**
     * 还款分页列表
     * @param pageCriteria 分页信息，
     * @param state
     * @return
     */
    PageResult<Repayment> findPage(PageCriteria pageCriteria, RepaymentState state, Date payDate, Boolean overdue);

    /**
     * 还款列表
     * @param state
     * @return
     */
    List<Repayment> findList(RepaymentState state, Date payDate, Boolean overdue);

    /**
     * 列表
     * @param borrowingId 借款ID
     * @return
     */
    List<Repayment> findList(Integer borrowingId);

    /**
     * 列表
     * @param startDate
     * @param startDate
     * @return
     */
    List<Repayment> findList(Date startDate, Date endDate);

    /**
     * 还款
     * @param repayment 还款
     */
    void repayment(Repayment repayment,String ipsBillNo) ;

    /**
     * 提前还款
     * @param borrowing 还款
     */
    void preRepayment(Borrowing borrowing,String ipsBillNo) ;

    /**
     * 提前还款金额
     * @param borrowing 还款
     */
    BigDecimal preRepaymentAmount(Borrowing borrowing);

    /**
     * 还款查询
     * @param orderNo 业务号
     * @return
     */
    Repayment findByOrderNo(String orderNo);

//    /**
//     * 还款查询
//     * @param borrowingId 借款ID
//     * @return
//     */
//    Repayment findEnd(Integer borrowingId);

    /**
     * 上一期未还
     * @param repaymentId
     * @return
     */
    Boolean repaidLastRepayment(Integer repaymentId);

    /**
     * 当期还款
     * @param borrowingId
     * @return
     */
    Repayment getCurrentRepayment(Integer borrowingId);

    /**
     * 累计待还
     * @return
     */
    BigDecimal countWaitPay();

    List<Repayment> findByUser(Integer borrower);

    /**
     * 还款通知
     */
    void repaymentNotice();

    /**
     *
     */
    CpcnSettlement repayment(Repayment repayment, RepaymentOperator operator);
    /**
     *
     */
    CpcnSettlement repaymentEarly(Borrowing borrowing, RepaymentOperator operator);

//    /**
//     * 继续还款
//     */
//    Result repayCarry(CpcnSettlement cpcnSettlement);

    /**
     * 还款金额
     */
    BigDecimal repaymentAmount(Integer repayment);

    /**
     * 还款 (撤销转让、产生还款进度、产生还款订单、产生回款订单、产生服务费订单、更新还款金额、更新还款计划金额)
     */
    Response repay(CpcnSettlement cpcnSettlement) ;

    /**
     * 还款成功    (更新借款人资金、更新还款状态、更新还款计划状态、更新借款)
     */
    void repaySucceed(CpcnSettlement cpcnSettlement, PaymentOrder paymentOrder) ;

    /**
     * 还款结算
     */
    Response repaySettlement(CpcnSettlement cpcnSettlement) ;

    /**
     * 还款结算成功
     */
    void repaySettlementSucceed(CpcnSettlement cpcnSettlement, PaymentOrder paymentOrder, Boolean allSuccess) ;

    /**
     * 提前还款金额
     * @param borrowing 还款
     */
    BigDecimal aheadRepaymentAmount(Integer borrowing);

    /**
     * 提前还款
     */
    Response aheadRepay(CpcnSettlement cpcnSettlement) ;

    /**
     * 提前还款成功
     */
    void aheadRepaySucceed(CpcnSettlement cpcnSettlement, PaymentOrder paymentOrder) ;

    /**
     *
     */
    Response aheadRepaySettlement(CpcnSettlement cpcnSettlement) ;

    /**
     *
     */
    void aheadRepaySettlementSucceed(CpcnSettlement CpcnSettlementState, PaymentOrder paymentOrder) ;
}
