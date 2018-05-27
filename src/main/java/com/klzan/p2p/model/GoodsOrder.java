package com.klzan.p2p.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klzan.core.util.SpringUtils;
import com.klzan.p2p.enums.GoodsOrderStatus;
import com.klzan.p2p.enums.GoodsShippingStatus;
import com.klzan.p2p.model.base.BaseModel;
import com.klzan.p2p.service.content.AreaService;
import com.klzan.p2p.service.content.impl.AreaServiceImpl;
import com.klzan.p2p.service.goods.GoodsService;
import com.klzan.p2p.service.goods.impl.GoodsServiceImpl;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.service.user.impl.UserServiceImpl;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品订单
 */
@Entity
@Table(name = "karazam_goods_order")
public class GoodsOrder extends BaseModel {

    /** 用户 */
    @Column(nullable = false)
    private Integer userId;

    /** 订单编号 */
    @Column(nullable = false)
    private String orderNo;

	/** 订单状态 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private GoodsOrderStatus status;

	/** 操作员 */
    @Column
	private String operator;

	/** 商品 */
    @Column
	private Integer goods;

	/** 数量 */
    @Column
	private Integer quantity;

    /** 成交金额 + 优惠金额 = 原商品价格    成交积分 + 优惠积分 = 原商品积分  */
	/** 成交金额 */
    @Column
	private BigDecimal amount = BigDecimal.ZERO;

    /** 优惠金额 */
    @Column
    private BigDecimal discountAmount = BigDecimal.ZERO;

    /** 成交积分 */
    @Column
    private Integer point;

    /** 优惠积分 */
    @Column
    private Integer discountPoint;

    /** 确认过期时间 */
    @Column
    private Date confirmExpireDate;

    /** 确认时间 */
    @Column
    private Date confirmDate;

    /** 发货时间 */
    @Column
    private Date sendDate;

    /** 收货时间 */
    @Column
    private Date receiveDate;

    /** 收货过期时间 */
    @Column
    private Date receiveExpireDate;

    /** 订单备注 */
    @Column
    private String memo;

//    /** 优惠券 */
//    private Integer coupon;
//
//    /** 返现 */
//    private BigDecimal returningCash;



    ////////// 收货信息 //////////////

    /** 收货人名称 */
    @Column
    private String adrConsignee;

    /** 地区 */
    @Column
    private Integer adrArea;

    /** 地址 */
    @Column
    private String adrAddress;

    /** 邮编 */
    @Column
    private String adrZipCode;

    /** 手机 */
    @Column
    private String adrMobile;

    /** 电话 */
    @Column
    private String adrTelephone;

    /** 备注 */
    @Column
    private String adrMemo;


    ////////// 物流信息 //////////////

    /** 物流状态 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private GoodsShippingStatus logisticsStatus;

    /** 物流公司 */
    @Column
    private String logisticsCorp;

    /** 物流公司网址 */
    @Column
    private String logisticsCorpUrl;

    /** 物流公司代码 */
    @Column
    private String logisticsCorpCode;

    /** 运单号 */
    @Column
    private String logisticsNo;

    /** 物流费用 */
    @Column
    private BigDecimal logisticsFee = BigDecimal.ZERO;



    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public GoodsOrderStatus getStatus() {
        return status;
    }

    public void setStatus(GoodsOrderStatus status) {
        this.status = status;
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

    /** 订单状态  */
    @Transient
    public String getStatusDes() {
        return getStatus().getDisplayName();
    }

//    /** 物流状态  */
//    @Transient
//    public String getLogisticsStatusDes() {
//        return getLogisticsStatus().getDisplayName();
//    }

    /** 用户名  */
    @Transient
    @JsonProperty("username")
    public String getUsername() {
        UserService userService = SpringUtils.getBean(UserServiceImpl.class);
        if(userService!=null){
            User user = userService.get(getUserId());
            if(user!=null){
                return user.getLoginName();
            }
        }
        return "";
    }

    /** 物流状态  */
    @Transient
    public String getGoodsName() {
        GoodsService goodsService = SpringUtils.getBean(GoodsServiceImpl.class);
        if(getGoods()!=null && goodsService!=null){
            Goods goods = goodsService.get(getGoods());
            return goods.getName()==null?"":goods.getName();
        }
        return "";
    }

    /** 商品  */
    @Transient
    public Goods getGoodsObj() {
        GoodsService goodsService = SpringUtils.getBean(GoodsServiceImpl.class);
        if(getGoods()!=null && goodsService!=null){
            return goodsService.get(getGoods());
        }
        return null;
    }

    /** 地区  */
    @Transient
    public Area getAreaObj() {
        AreaService areaService = SpringUtils.getBean(AreaServiceImpl.class);
        if(areaService!=null){
            Area area = areaService.get(getAdrArea());
            if(area!=null){
                return area;
            }
        }
        return null;
    }
}
