/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.goods;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.GoodsOrderStatus;
import com.klzan.p2p.enums.GoodsType;
import com.klzan.p2p.model.Goods;
import com.klzan.p2p.model.GoodsOrder;
import com.klzan.p2p.model.User;
import com.klzan.p2p.vo.goods.GoodsExchangeVo;
import com.klzan.p2p.vo.goods.GoodsOrderVo;

import java.util.List;


/**
 * 商品订单
 * @author: chenxinglin
 */
public interface GoodsOrderService extends IBaseService<GoodsOrder> {

    /**
     * 分页列表
     * @param criteria 分页信息
     * @return
     */
    PageResult<GoodsOrder> findPage(PageCriteria criteria);

    /**
     * 分页列表
     * @param criteria 分页信息
     * @return
     */
    PageResult<GoodsOrder> findPage(PageCriteria criteria, Integer userId, GoodsType type, GoodsOrderStatus status, Integer month);

    /**
     * 商品订单
     * @param userId 用户ID
     * @return
     */
    List<GoodsOrder> findListByUserId(Integer userId);

    /**
     * 商品订单
     * @param goodsId 用户ID
     * @return
     */
    List<GoodsOrder> findListByGoods(Integer goodsId);

    /**
     * 提交订单
     * @param vo
     * @return
     */
    GoodsOrder submit(User currentUser, GoodsExchangeVo vo);

//    /**
//     * 确认订单
//     * 补录积分信息
//     * @param goodsOrderId
//     * @return
//     */
//    GoodsOrder confirm(Integer goodsOrderId);

    /**
     * 撤销订单
     * 发货前可以撤销
     * @param goodsOrder
     * @return
     */
    GoodsOrder cancel(GoodsOrder goodsOrder);

    /**
     * 发货（后台）
     * 补录物流信息
     * @param goodsOrder
     * @return
     */
    GoodsOrder sendOut(GoodsOrder goodsOrder, String logisticsCorp, String logisticsCorpUrl, String logisticsNo);

    /**
     * 确认收货 或 收货超时 == 完成订单
     * 更新状态
     * @param goodsOrder
     * @return
     */
    GoodsOrder receive(GoodsOrder goodsOrder);

    /**
     * 修改订单（后台）
     * @param vo
     * @return
     */
    GoodsOrder update(GoodsOrderVo vo);




}
