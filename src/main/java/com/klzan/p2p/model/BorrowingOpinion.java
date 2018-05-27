package com.klzan.p2p.model;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.BorrowingOpinionType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;

/**
 * 借款意见
 */
@Entity
@Table(name = "karazam_borrowing_opinion")
public class BorrowingOpinion extends BaseModel {

    /**
     * 类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BorrowingOpinionType type;

    /**
     * 内容
     */
    private String cont;

    /**
     * 操作人
     */
    private String operator;

    /**
     * IP
     */
    private String ip;

    /**
     * 借款ID
     */
    private Integer borrowing;

    public BorrowingOpinion() {
    }

    public BorrowingOpinion(BorrowingOpinionType type, String cont, String operator, String ip, Integer borrowing) {
        this.type = type;
        this.cont = cont;
        this.operator = operator;
        this.ip = ip;
        this.borrowing = borrowing;
    }

    public BorrowingOpinionType getType() {
        return type;
    }

    public void setType(BorrowingOpinionType type) {
        this.type = type;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    @Transient
    public String getTypeDes() {
        return type.getDisplayName();
    }

    @Transient
    public String getCreateDateDes(){
        return DateUtils.formatDateTimeToString(getCreateDate(), DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

}