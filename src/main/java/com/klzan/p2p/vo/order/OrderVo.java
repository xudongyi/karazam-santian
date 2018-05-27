package com.klzan.p2p.vo.order;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.OrderMethod;
import com.klzan.p2p.enums.OrderStatus;
import com.klzan.p2p.enums.OrderType;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by suhao Date: 2017/5/11 Time: 14:40
 *
 * @version: 1.0
 */
public class OrderVo extends BaseVo {
    /** 当前用户 */
    private Integer userId;
    /** 付款人 -1银行 -2第三方支付 -3平台 */
    private Integer payer;

    /** 付款人名称 */
    private String payerName;

    /** 收款人 */
    private Integer payee;

    /** 收款人名称 */
    private String payeeName;

    /** 状态（发起、审核中、平台处理中、环迅处理中、完成、失败、撤销） */
    private OrderStatus status;

    /** 业务类型（投资-借款出借、还款-回款、充值、提现、（推荐费、其他）） */
    private OrderType type;

    /** 支付方式（平台余额、第三方支付、银行卡） */
    private OrderMethod method;

    /** 业务编号  */
    private Integer business;

    /** 平台父订单号 */
    private String parentOrderNo;

    /** 平台订单号 */
    private String orderNo;

    /** 第三方订单号 */
    private String thirdOrderNo;

    private BigDecimal payerBalance;

    private BigDecimal payeeBalance;

    /** 实际支付金额 - 支付人服务费 - 支付人服务费(环迅) = 实际收款金额 + 收款人服务费 + 收款人服务费(环迅)  */
    /** 实际支付金额 */
    private BigDecimal amount = BigDecimal.ZERO;

    /** 实际收款金额 */
    private BigDecimal amountReceived = BigDecimal.ZERO;

    /** 支付人服务费 */
    private BigDecimal payerFee = BigDecimal.ZERO;

    /** 收款人服务费 */
    private BigDecimal payeeFee = BigDecimal.ZERO;

    /** 支付人服务费(环迅) */
    private BigDecimal payerThirdFee = BigDecimal.ZERO;

    /** 收款人服务费(环迅) */
    private BigDecimal payeeThirdFee = BigDecimal.ZERO;

    /** 积分 */
    private Integer points;

    /** 支付时间（发起） */
    private Date launchDate;

    /** 审核时间（审核） */
    private Date auditDate;

    /** 处理中时间（处理中） */
    private Date processingDate;

    /** 完成时间（成功、失败、撤销） */
    private Date finishDate;

    /** 备注  */
    private String memo;

    /** 操作员  */
    private String operator;

    /** IP  */
    private String ip;

    public OrderVo() {
    }

    public OrderVo(Integer userId, BigDecimal payerBalance, BigDecimal payeeBalance, Integer payer, String payerName, Integer payee, String payeeName, OrderStatus status, OrderType type, OrderMethod method, Integer business, String parentOrderNo, String orderNo, String thirdOrderNo, BigDecimal amount, BigDecimal amountReceived, BigDecimal payerFee, BigDecimal payeeFee, BigDecimal payerThirdFee, BigDecimal payeeThirdFee, Date operateDate, String memo, String operator, String ip) {
        this.userId = userId;
        this.payerBalance = payerBalance;
        this.payeeBalance = payeeBalance;
        this.payer = payer;
        this.payerName = payerName;
        this.payee = payee;
        this.payeeName = payeeName;
        this.status = status;
        this.type = type;
        this.method = method;
        this.business = business;
        this.parentOrderNo = parentOrderNo;
        this.orderNo = orderNo;
        this.thirdOrderNo = thirdOrderNo;
        this.amount = amount;
        this.amountReceived = amountReceived;
        this.payerFee = payerFee;
        this.payeeFee = payeeFee;
        this.payerThirdFee = payerThirdFee;
        this.payeeThirdFee = payeeThirdFee;

        switch (status) {
            case LAUNCH:
                this.launchDate = operateDate;
                break;
            case AUDITING:
                this.auditDate = operateDate;
                break;
            case THIRD_PROCESSING:
                this.processingDate = operateDate;
            case SUCCESS:
            case FAILURE:
                this.finishDate = operateDate;
                break;
            default:

        }

        this.memo = memo;
        this.operator = operator;
        this.ip = ip;
    }

    public OrderVo(BigDecimal payerBalance, BigDecimal payeeBalance, OrderStatus status, OrderType type, Integer business, String thirdOrderNo) {
        this.payerBalance = payerBalance;
        this.payeeBalance = payeeBalance;
        this.status = status;
        this.type = type;
        this.business = business;
        this.thirdOrderNo = thirdOrderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getPayer() {
        return payer;
    }

    public void setPayer(Integer payer) {
        this.payer = payer;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public Integer getPayee() {
        return payee;
    }

    public void setPayee(Integer payee) {
        this.payee = payee;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public OrderMethod getMethod() {
        return method;
    }

    public void setMethod(OrderMethod method) {
        this.method = method;
    }

    public Integer getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public String getParentOrderNo() {
        return parentOrderNo;
    }

    public void setParentOrderNo(String parentOrderNo) {
        this.parentOrderNo = parentOrderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    public BigDecimal getPayerBalance() {
        return payerBalance;
    }

    public void setPayerBalance(BigDecimal payerBalance) {
        this.payerBalance = payerBalance;
    }

    public BigDecimal getPayeeBalance() {
        return payeeBalance;
    }

    public void setPayeeBalance(BigDecimal payeeBalance) {
        this.payeeBalance = payeeBalance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(BigDecimal amountReceived) {
        this.amountReceived = amountReceived;
    }

    public BigDecimal getPayerFee() {
        return payerFee;
    }

    public void setPayerFee(BigDecimal payerFee) {
        this.payerFee = payerFee;
    }

    public BigDecimal getPayeeFee() {
        return payeeFee;
    }

    public void setPayeeFee(BigDecimal payeeFee) {
        this.payeeFee = payeeFee;
    }

    public BigDecimal getPayerThirdFee() {
        return payerThirdFee;
    }

    public void setPayerThirdFee(BigDecimal payerThirdFee) {
        this.payerThirdFee = payerThirdFee;
    }

    public BigDecimal getPayeeThirdFee() {
        return payeeThirdFee;
    }

    public void setPayeeThirdFee(BigDecimal payeeThirdFee) {
        this.payeeThirdFee = payeeThirdFee;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Date getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(Date processingDate) {
        this.processingDate = processingDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
}
