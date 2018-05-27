/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.capital;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.PaymentOrderMethod;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.vo.capital.PaymentOrderVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by suhao on 2016/11/21.
 */
public interface PaymentOrderService extends IBaseService<PaymentOrder> {
    /**
     * 查找订单分页
     * @param criteria
     * @param isPlatform
     * @return
     */
    PageResult<PaymentOrderVo> findPage(PageCriteria criteria, boolean isPlatform);

    /**
     * 用户订单分页
     * @param criteria
     * @param type
     * @param userId
     * @return
     */
    PageResult<PaymentOrder> findPage(PageCriteria criteria, PaymentOrderType type, Integer userId);

    /**
     * 生成订单
     * @param hasChildren
     * @param userId
     * @param type
     * @param method
     * @param orderNo
     * @param parentOrderNo
     * @param amount
     * @param expire
     * @param memo
     * @param ext
     * @return
     */
    PaymentOrder generateOrder(Boolean hasChildren, Integer userId, PaymentOrderType type, PaymentOrderMethod method, String orderNo, String parentOrderNo, BigDecimal amount, Date expire, String memo, String ext);

    /**
     * 订单
     * @param orderNo 订单号
     * @return
     */
    PaymentOrder findByOrderNo(String orderNo);

    /**
     * 查询订单列表
     * @param isBgOrder
     * @param mobile
     * @param orderNo
     * @param type
     * @param status
     * @param method
     * @param startCreateDate
     * @param endCreateDate
     * @return
     */
    List<PaymentOrderVo> findAllOrder(boolean isBgOrder, String mobile, String orderNo, String type, String status, String method, String startCreateDate, String endCreateDate);

    PaymentOrder find(Integer userId, PaymentOrderStatus status, PaymentOrderType type);

    List<PaymentOrder> findList(Integer userId, PaymentOrderStatus status, PaymentOrderType type);

    List<PaymentOrder> findByParentOrderNo(String orderNo);

}
