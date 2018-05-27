/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model;

import com.klzan.p2p.enums.BorrowingContactsType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;

/**
 * Entity - 借款联系人
 *
 * @author: chenxinglin  Date: 2016/10/19 Time: 17:15
 */
@Entity
@Table(name = "karazam_borrowing_contacts")
public class BorrowingContacts extends BaseModel {

    /**
     * 类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BorrowingContactsType type;

    /**
     * 姓名
     */
    @Column(length = 50)
    private String name;

    /**
     * 手机号码
     */
    @Column(length = 11)
    private String mobile;

    /**
     * 座机号码
     */
    @Column(length = 20)
    private String telephone;

    /**
     * 备注
     */
    @Column(length = 100)
    private String memo;

    /**
     * 借款ID
     */
    @Column(nullable = false)
    private Integer borrowing;

    public BorrowingContacts() {
    }

    public BorrowingContacts(BorrowingContactsType type, String name, String mobile, String telephone, String memo, Integer borrowing) {
        this.type = type;
        this.name = name;
        this.mobile = mobile;
        this.telephone = telephone;
        this.memo = memo;
        this.borrowing = borrowing;
    }

    @Transient
    public String getTypeDes() {
        return type.getDisplayName();
    }

    public BorrowingContactsType getType() {
        return type;
    }

    public void setType(BorrowingContactsType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }
}