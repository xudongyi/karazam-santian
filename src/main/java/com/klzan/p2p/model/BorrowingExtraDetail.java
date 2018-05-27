package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * 借款项目附加详情信息
 */
@Entity
@Table(name = "karazam_borrowing_extra_detail")
public class BorrowingExtraDetail extends BaseModel {
    @Column(nullable = false)
    private Integer extraId;
    /**
     * 借款ID
     */
    @Column(nullable = false)
    private Integer borrowing;

    @Column(nullable = false, length = 200)
    private String extraKey;

    @Column(nullable = false, length = 200)
    private String extraField;

    @Column(nullable = false, length = 200)
    private String extraFieldAlias;

    @Column(nullable = false, length = 200)
    private String extraFieldDes;

    @Column(nullable = false, length = 1000)
    private String extraFieldValue;

    public BorrowingExtraDetail() {
    }

    public BorrowingExtraDetail(Integer extraId, String extraKey, Integer borrowing, String extraFieldAlias, String extraFieldDes, String extraFieldValue) {
        this.extraId = extraId;
        this.extraKey = extraKey;
        this.extraField = UUID.randomUUID().toString().replace("-", "");;
        this.borrowing = borrowing;
        this.extraFieldAlias = extraFieldAlias;
        this.extraFieldDes = extraFieldDes;
        this.extraFieldValue = extraFieldValue;
    }

    public Integer getExtraId() {
        return extraId;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public String getExtraKey() {
        return extraKey;
    }

    public String getExtraField() {
        return extraField;
    }

    public String getExtraFieldAlias() {
        return extraFieldAlias;
    }

    public String getExtraFieldDes() {
        return extraFieldDes;
    }

    public String getExtraFieldValue() {
        return extraFieldValue;
    }
}