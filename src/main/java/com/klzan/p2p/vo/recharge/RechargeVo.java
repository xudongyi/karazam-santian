package com.klzan.p2p.vo.recharge;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.PaymentOrderMethod;
import com.klzan.p2p.enums.RechargeBusinessType;
import com.klzan.p2p.enums.RecordStatus;

import java.math.BigDecimal;

/**
 * Created by suhao Date: 2016/12/7 Time: 17:52
 *
 * @version: 1.0
 */
public class RechargeVo extends BaseVo {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 实际支付金额=amount-优惠金额
     */
    private BigDecimal actualAmount;


    /**
     * 服务费
     */
    private BigDecimal fee;

    /**
     * 银行ID
     */
    private Integer bankId;
    private String bankCode;

    /**
     * 优惠券ID
     */
    private Integer coupon;

    /**
     * 记录状态
     */
    private RecordStatus status;
    private String statusStr;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 支付方式
     */
    private PaymentOrderMethod paymentMethod;
    private String paymentMethodStr;
    /**
     * 短信验证码
     */
    private String smsCode;

    /**
     * 充值类型
     */
    private RechargeBusinessType rechargeBusType;

    private String mobileType;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Integer getCoupon() {
        return coupon;
    }

    public void setCoupon(Integer coupon) {
        this.coupon = coupon;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public PaymentOrderMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentOrderMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodStr() {
        return paymentMethodStr;
    }

    public void setPaymentMethodStr(String paymentMethodStr) {
        this.paymentMethodStr = paymentMethodStr;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public RechargeBusinessType getRechargeBusType() {
        return rechargeBusType;
    }

    public void setRechargeBusType(RechargeBusinessType rechargeBusType) {
        this.rechargeBusType = rechargeBusType;
    }

    public String getMobileType() {
        return mobileType;
    }

    public void setMobileType(String mobileType) {
        this.mobileType = mobileType;
    }
}
