package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by suhao Date: 2017/11/16 Time: 17:16
 *
 * @version: 1.0
 */
@Entity
@Table(name = "karazam_borrowing_field_remark")
public class BorrowingFieldRemark extends BaseModel {
    /**
     * 借款ID
     */
    @Column(nullable = false)
    private Integer borrowing;

    /**
     * 字段名称
     */
    @Column(nullable = false)
    private String fieldName;
    /**
     * 字段备注
     */
    @Column(nullable = false)
    private String fieldRemark;

    public BorrowingFieldRemark() {
    }

    public BorrowingFieldRemark(Integer borrowing, String fieldName, String fieldRemark) {
        this.borrowing = borrowing;
        this.fieldName = fieldName;
        this.fieldRemark = fieldRemark;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldRemark() {
        return fieldRemark;
    }

    public void update(Integer borrowing, String fieldName, String fieldRemark) {
        this.borrowing = borrowing;
        this.fieldName = fieldName;
        this.fieldRemark = fieldRemark;
    }
}
