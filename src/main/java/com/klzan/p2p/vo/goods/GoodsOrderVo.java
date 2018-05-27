package com.klzan.p2p.vo.goods;

import com.klzan.p2p.common.vo.BaseSortVo;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.GoodsOrderStatus;
import com.klzan.p2p.enums.GoodsShippingStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品类别
 */
public class GoodsOrderVo extends BaseVo {

    /** 用户 */
    private Integer userId;

    /** 订单编号 */
    private String orderNo;

    /** 订单状态 */
    private GoodsOrderStatus status;

    /** 操作员 */
    private String operator;

    /** 商品 */
    private Integer goods;

    /** 数量 */
    private Integer quantity;

    /** 成交金额 */
    private BigDecimal amount = BigDecimal.ZERO;

    /** 优惠金额 */
    private BigDecimal discountAmount = BigDecimal.ZERO;

    /** 成交积分 */
    private Integer point;

    /** 优惠积分 */
    private Integer discountPoint;

    /** 确认过期时间 */
    private Date confirmExpireDate;

    /** 确认时间 */
    private Date confirmDate;

    /** 发货时间 */
    private Date sendDate;

    /** 收货时间 */
    private Date receiveDate;

    /** 收货过期时间 */
    private Date receiveExpireDate;

    /** 订单备注 */
    private String memo;

//    /** 优惠券 */
//    private Integer coupon;
//
//    /** 返现 */
//    private BigDecimal returningCash;



    ////////// 收货信息 //////////////

    /** 收货人名称 */
    private String adrConsignee;

    /** 地区 */
    private Integer adrArea;

    /** 地址 */
    private String adrAddress;

    /** 邮编 */
    private String adrZipCode;

    /** 手机 */
    private String adrMobile;

    /** 电话 */
    private String adrTelephone;

    /** 备注 */
    private String adrMemo;


    ////////// 物流信息 //////////////

    /** 物流状态 */
    private GoodsShippingStatus logisticsStatus;

    /** 物流公司 */
    private String logisticsCorp;

    /** 物流公司网址 */
    private String logisticsCorpUrl;

    /** 物流公司代码 */
    private String logisticsCorpCode;

    /** 运单号 */
    private String logisticsNo;

    /** 物流费用 */
    private BigDecimal logisticsFee = BigDecimal.ZERO;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public GoodsOrderStatus getStatus() {
        return status;
    }

    public void setStatus(GoodsOrderStatus status) {
        this.status = status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getGoods() {
        return goods;
    }

    public void setGoods(Integer goods) {
        this.goods = goods;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getDiscountPoint() {
        return discountPoint;
    }

    public void setDiscountPoint(Integer discountPoint) {
        this.discountPoint = discountPoint;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAdrConsignee() {
        return adrConsignee;
    }

    public void setAdrConsignee(String adrConsignee) {
        this.adrConsignee = adrConsignee;
    }

    public Integer getAdrArea() {
        return adrArea;
    }

    public void setAdrArea(Integer adrArea) {
        this.adrArea = adrArea;
    }

    public String getAdrAddress() {
        return adrAddress;
    }

    public void setAdrAddress(String adrAddress) {
        this.adrAddress = adrAddress;
    }

    public String getAdrZipCode() {
        return adrZipCode;
    }

    public void setAdrZipCode(String adrZipCode) {
        this.adrZipCode = adrZipCode;
    }

    public String getAdrMobile() {
        return adrMobile;
    }

    public void setAdrMobile(String adrMobile) {
        this.adrMobile = adrMobile;
    }

    public String getAdrTelephone() {
        return adrTelephone;
    }

    public void setAdrTelephone(String adrTelephone) {
        this.adrTelephone = adrTelephone;
    }

    public String getAdrMemo() {
        return adrMemo;
    }

    public void setAdrMemo(String adrMemo) {
        this.adrMemo = adrMemo;
    }

    public GoodsShippingStatus getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(GoodsShippingStatus logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }

    public String getLogisticsCorp() {
        return logisticsCorp;
    }

    public void setLogisticsCorp(String logisticsCorp) {
        this.logisticsCorp = logisticsCorp;
    }

    public String getLogisticsCorpUrl() {
        return logisticsCorpUrl;
    }

    public void setLogisticsCorpUrl(String logisticsCorpUrl) {
        this.logisticsCorpUrl = logisticsCorpUrl;
    }

    public String getLogisticsCorpCode() {
        return logisticsCorpCode;
    }

    public void setLogisticsCorpCode(String logisticsCorpCode) {
        this.logisticsCorpCode = logisticsCorpCode;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public BigDecimal getLogisticsFee() {
        return logisticsFee;
    }

    public void setLogisticsFee(BigDecimal logisticsFee) {
        this.logisticsFee = logisticsFee;
    }

    public Date getConfirmExpireDate() {
        return confirmExpireDate;
    }

    public void setConfirmExpireDate(Date confirmExpireDate) {
        this.confirmExpireDate = confirmExpireDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Date getReceiveExpireDate() {
        return receiveExpireDate;
    }

    public void setReceiveExpireDate(Date receiveExpireDate) {
        this.receiveExpireDate = receiveExpireDate;
    }
}
