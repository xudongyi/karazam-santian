package com.klzan.plugin.pay.common.dto;

import com.klzan.p2p.enums.DeviceType;
import com.klzan.p2p.enums.OperationMethod;
import com.klzan.p2p.enums.PaymentOrderMethod;

import java.math.BigDecimal;

/**
 * 投资
 * Created by suhao Date: 2017/11/22 Time: 14:51
 *
 * @version: 1.0
 */
public class InvestmentRequest extends Request {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 项目ID
     */
    private Integer projectId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 是否债权转让
     */
    private Boolean yesTransfer = Boolean.FALSE;
    /**
     * 转让ID
     */
    private Integer transfer;

    /**
     * 是否债权转让
     */
    private Integer parts;

    /** 操作方式 */
    private OperationMethod operationMethod;

    /** 支付方式 */
    private PaymentOrderMethod paymentMethod;

    /** 投标来源 */
    private DeviceType deviceType;

    public InvestmentRequest() {
    }

    public InvestmentRequest(Boolean isMobile, Integer userId, Integer projectId,
                             BigDecimal amount, Boolean yesTransfer, Integer transfer, Integer parts,
                             PaymentOrderMethod paymentMethod, DeviceType deviceType) {
        setMobile(isMobile);
        this.userId = userId;
        this.projectId = projectId;
        this.amount = amount;
        this.yesTransfer = yesTransfer;
        this.transfer = transfer;
        this.parts = parts;
        this.operationMethod = OperationMethod.MANUAL;
        this.paymentMethod = paymentMethod;
        this.deviceType = deviceType;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public OperationMethod getOperationMethod() {
        return operationMethod;
    }

    public PaymentOrderMethod getPaymentMethod() {
        return paymentMethod;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public Integer getParts() {
        return parts;
    }

    public Integer getTransfer() {
        return transfer;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getYesTransfer() {
        return yesTransfer;
    }

    public void setYesTransfer(Boolean yesTransfer) {
        this.yesTransfer = yesTransfer;
    }

    public void setTransfer(Integer transfer) {
        this.transfer = transfer;
    }

    public void setParts(Integer parts) {
        this.parts = parts;
    }

    public void setOperationMethod(OperationMethod operationMethod) {
        this.operationMethod = operationMethod;
    }

    public void setPaymentMethod(PaymentOrderMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
