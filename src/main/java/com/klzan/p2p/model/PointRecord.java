package com.klzan.p2p.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klzan.core.util.SpringUtils;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.PointMethod;
import com.klzan.p2p.enums.PointType;
import com.klzan.p2p.model.base.BaseModel;
import com.klzan.p2p.service.goods.GoodsFollowService;
import com.klzan.p2p.service.goods.impl.GoodsFollowServiceImpl;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.service.user.impl.UserServiceImpl;

import javax.persistence.*;

/**
 * 积分记录
 */
@Entity
@Table(name = "karazam_point_record")
public class PointRecord extends BaseModel {

    /** 用户 */
    @Column(nullable = false)
    private Integer userId;

    /** 平台订单 */
    @Column(nullable = false)
    private String orderNo;

    /** 方式 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private PointMethod method;

    /** 类型 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private PointType type;

    /** 积分 */
    @Column(nullable = false)
    private Integer point = 0;

    /** 借款 */
    @Column
    private Integer borrowing;

    /** 备注 */
    @Column
    private String memo;

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

    public PointMethod getMethod() {
        return method;
    }

    public void setMethod(PointMethod method) {
        this.method = method;
    }

    public PointType getType() {
        return type;
    }

    public void setType(PointType type) {
        this.type = type;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Transient
    public String getMethodDes() {
        return getMethod().getDisplayName();
    }

    @Transient
    public String getTypeDes() {
        return getType().getDisplayName();
    }

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

    /** 持久化前处理 */
    @PrePersist
    public void prePersist() {
    }

}