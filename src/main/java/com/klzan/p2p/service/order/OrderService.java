package com.klzan.p2p.service.order;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.OrderStatus;
import com.klzan.p2p.enums.OrderType;
import com.klzan.p2p.model.Order;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.vo.order.OrderVo;

/**
 * Created by suhao on 2017/5/11.
 */
public interface OrderService extends IBaseService<Order> {
    /**
     * 添加或更新订单
     * @param orderVo
     * @return
     */
    Order addOrMergeOrder(OrderVo orderVo);

    /**
     * 查询
     * @param type 业务类型
     * @param businessId 业务ID
     * @return
     */
    Order getByBusinessId(OrderType type, Integer businessId);

    PageResult<Order> findPage(PageCriteria criteria, Integer userId, OrderType orderType);

    PageResult<Order> AppfindPage(PageCriteria criteria, Integer userId, OrderType orderType);
//    /**
//     * 新增订单
//     * @param type 业务类型
//     * @param businessId 业务ID
//     * @param status 订单状态
//     * @return
//     */
//    Order addOrder(OrderType type, Integer businessId, OrderStatus status);

    /**
     * 更新订单状态
     * @param type 业务类型
     * @param businessId 业务ID
     * @param status 订单状态
     * @param orderNo 业务订单号
     * @param orderNo 业务订单号
     * @param orderNo 业务订单号
     * @return
     */
    Order updateOrderStatus(OrderType type, Integer businessId, OrderStatus status, String orderNo);
    Order findOrder(OrderType type, Integer businessId, OrderStatus status, String orderNo);

//    /**
//     * 查询
//     * @param orderNo 订单编号
//     * @return
//     */
//    Order getByOrderNo(String orderNo);
}
