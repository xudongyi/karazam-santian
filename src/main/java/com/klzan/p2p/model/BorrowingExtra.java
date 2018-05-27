package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * 借款项目附加信息
 */
@Entity
@Table(name = "karazam_borrowing_extra")
public class BorrowingExtra extends BaseModel {

    /**
     * 借款ID
     */
    @Column(nullable = false)
    private Integer borrowing;

    @Column(nullable = false, length = 200)
    private String extraKey;

    @Column(nullable = false, length = 200)
    private String extraKeyAlias;

    @Column(nullable = false, length = 200)
    private String extraKeyDes;

    @Column(nullable = false, length = 1000)
    private String extraValue;

    public BorrowingExtra() {
    }

    public BorrowingExtra(Integer borrowing, String extraKeyAlias, String extraKeyDes, String extraValue) {
        this.extraKey = UUID.randomUUID().toString().replace("-", "");
        this.borrowing = borrowing;
        this.extraKeyAlias = extraKeyAlias;
        this.extraKeyDes = extraKeyDes;
        this.extraValue = extraValue;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public String getExtraKey() {
        return extraKey;
    }

    public String getExtraKeyAlias() {
        return extraKeyAlias;
    }

    public String getExtraKeyDes() {
        return extraKeyDes;
    }

    public String getExtraValue() {
        return extraValue;
    }
}