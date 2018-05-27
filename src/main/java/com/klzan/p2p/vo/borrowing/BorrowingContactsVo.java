/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.vo.borrowing;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.BorrowingContactsType;

public class BorrowingContactsVo extends BaseVo {

    /**
     * 类型
     */
    private BorrowingContactsType type;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 座机号码
     */
    private String telephone;

    /**
     * 备注
     */
    private String memo;

    /**
     * 借款ID
     */
    private Integer borrowing;

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
