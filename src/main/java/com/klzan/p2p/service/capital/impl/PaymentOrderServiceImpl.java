/*
 * Copyright (c) 2017 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.capital.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.capital.PaymentOrderDao;
import com.klzan.p2p.enums.PaymentOrderMethod;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.vo.capital.PaymentOrderVo;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentOrderServiceImpl extends BaseService<PaymentOrder> implements PaymentOrderService {

    @Inject
    private PaymentOrderDao paymentDao;

    @Override
    public PageResult<PaymentOrderVo> findPage(PageCriteria criteria, boolean isPlatform) {
        return paymentDao.findPage(criteria, isPlatform);
    }
    @Override
    public List<PaymentOrderVo> findAllOrder(boolean isBgOrder, String mobile, String orderNo, String type, String status, String method, String startCreateDate, String endCreateDate) {
        Map map = new HashMap();
        if (StringUtils.isNotBlank(mobile)){
            map.put("mobile",mobile);
        }
        if (StringUtils.isNotBlank(orderNo)){
            map.put("orderNo",orderNo);
        }
        if (StringUtils.isNotBlank(type)){
            map.put("type",type);
        }
        if (StringUtils.isNotBlank(status)){
            map.put("status",status);
        }
        if (StringUtils.isNotBlank(method)){
            map.put("method",method);
        }
        if (StringUtils.isNotBlank(startCreateDate)){
            map.put("startCreateDate",startCreateDate);
        }
        if (StringUtils.isNotBlank(endCreateDate)){
            map.put("endCreateDate",endCreateDate);
        }
        if (isBgOrder) {
            return myDaoSupport.findList("com.klzan.p2p.mapper.PaymentOrderMapper.bgOrder",map);
        }else {
            return myDaoSupport.findList("com.klzan.p2p.mapper.PaymentOrderMapper.order",map);
        }
    }

    @Override
    public PaymentOrder find(Integer userId, PaymentOrderStatus status, PaymentOrderType type) {
        return paymentDao.find(userId, status, type);
    }

    @Override
    public List<PaymentOrder> findList(Integer userId, PaymentOrderStatus status, PaymentOrderType type) {
        return paymentDao.findList(userId, status, type);
    }

    @Override
    public PageResult<PaymentOrder> findPage(PageCriteria criteria, PaymentOrderType type, Integer userId){
        return paymentDao.findPage(criteria, type, userId);
    }

    @Override
    public PaymentOrder generateOrder(Boolean hasChildren, Integer userId, PaymentOrderType type, PaymentOrderMethod method, String orderNo, String parentOrderNo, BigDecimal amount, Date expire, String memo, String ext) {
        PaymentOrder order = new PaymentOrder(hasChildren, userId, type, orderNo, parentOrderNo, amount, memo, ext);
        paymentDao.persist(order);
        return order;
    }

    @Override
    public PaymentOrder findByOrderNo(String orderNo) {
        return paymentDao.findByOrderNo(orderNo);
    }

    @Override
    public List<PaymentOrder> findByParentOrderNo(String orderNo) {
        return paymentDao.findByParentOrderNo(orderNo);
    }

}
