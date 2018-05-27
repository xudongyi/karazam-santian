/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model;

import com.klzan.p2p.enums.CpcnRepaymentStatus;
import com.klzan.p2p.enums.CpcnSettlementStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.enums.RepaymentOperator;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;

/**
 * 结算
 * @author: chenxinglin
 */
@Entity
@Table(name = "karazam_cpcn_settlement")
public class CpcnSettlement extends BaseModel {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentOrderType type;

    @Column
    private Integer borrowing;

    @Column
    private Integer repayment;

    @Column
    private Integer transfer;

    @Column
    private String rOrderNo;

    @Column
    private String sOrderNo;

    @Enumerated(EnumType.STRING)
    @Column
    private CpcnRepaymentStatus rStatus = CpcnRepaymentStatus.unpaid;

    @Enumerated(EnumType.STRING)
    @Column
    private CpcnSettlementStatus sStatus = CpcnSettlementStatus.unsettled;

    @Column
    private Boolean noticeBorrower;

    @Column
    private Boolean noticeInvestor;

    /**
     * 还款操作人
     */
    @Column
    @Enumerated(EnumType.STRING)
    private RepaymentOperator operator = RepaymentOperator.BORROWER;


    public PaymentOrderType getType() {
        return type;
    }

    public void setType(PaymentOrderType type) {
        this.type = type;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    public Integer getRepayment() {
        return repayment;
    }

    public void setRepayment(Integer repayment) {
        this.repayment = repayment;
    }

    public Integer getTransfer() {
        return transfer;
    }

    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
    }

    public String getrOrderNo() {
        return rOrderNo;
    }

    public void setrOrderNo(String rOrderNo) {
        this.rOrderNo = rOrderNo;
    }

    public String getsOrderNo() {
        return sOrderNo;
    }

    public void setsOrderNo(String sOrderNo) {
        this.sOrderNo = sOrderNo;
    }

    public CpcnRepaymentStatus getrStatus() {
        return rStatus;
    }

    public void setrStatus(CpcnRepaymentStatus rStatus) {
        this.rStatus = rStatus;
    }

    public CpcnSettlementStatus getsStatus() {
        return sStatus;
    }

    public void setsStatus(CpcnSettlementStatus sStatus) {
        this.sStatus = sStatus;
    }

    public Boolean getNoticeBorrower() {
        return noticeBorrower;
    }

    public void setNoticeBorrower(Boolean noticeBorrower) {
        this.noticeBorrower = noticeBorrower;
    }

    public Boolean getNoticeInvestor() {
        return noticeInvestor;
    }

    public void setNoticeInvestor(Boolean noticeInvestor) {
        this.noticeInvestor = noticeInvestor;
    }

    public RepaymentOperator getOperator() {
        return operator;
    }

    public void setOperator(RepaymentOperator operator) {
        this.operator = operator;
    }
}