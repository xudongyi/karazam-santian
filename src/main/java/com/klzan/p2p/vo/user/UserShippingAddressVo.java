/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.vo.user;

import com.klzan.p2p.common.vo.BaseSortVo;


public class UserShippingAddressVo extends BaseSortVo{

    /** 用户 */
    private Integer userId;

    /** 收货人名称 */
    private String consignee;

    /** 地区 */
    private Integer area;

    /** 地址 */
    private String address;

    /** 邮编 */
    private String zipCode;

    /** 手机 */
    private String mobile;

    /** 电话 */
    private String telephone;

    /** 备注 */
    private String memo;

    /** 是否默认 */
    private Boolean preferred = Boolean.FALSE;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public Boolean getPreferred() {
        return preferred;
    }

    public void setPreferred(Boolean preferred) {
        this.preferred = preferred;
    }
}
