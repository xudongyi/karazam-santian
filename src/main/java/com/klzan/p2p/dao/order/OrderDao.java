package com.klzan.p2p.dao.order;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.enums.OrderStatus;
import com.klzan.p2p.enums.OrderType;
import com.klzan.p2p.model.Order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suhao Date: 2017/5/11 Time: 14:41
 *
 * @version: 1.0
 */
@Repository
public class OrderDao extends DaoSupport<Order> {

    /**
     * 根据业务类型和业务ID查询订单
     * @return
     */
    public Order getByBusinessId(OrderType type, Integer businessId) {
        if(type==null || businessId == null){
            return null;
        }
        StringBuffer hql = new StringBuffer("From Order o WHERE o.deleted = 0 AND o.type <> 'FAILURE' AND o.type <> 'RESCIND' ");
        Map<String, Object> params = new HashMap();
        hql.append(" AND o.type = :type");
        params.put("type" , type);
        hql.append(" AND o.business = :businessId");
        params.put("businessId" , businessId);
        hql.append(" ORDER BY o.id desc ");
        List<Order> list = this.find(hql.toString(), params);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
//        return this.findUnique(hql.toString(), params);
    }

    public PageResult<Order> findByPage(PageCriteria criteria, Integer userId, OrderType orderType) {
        StringBuffer hql = new StringBuffer("From Order o WHERE o.deleted = 0 ");
        Map param = new HashMap();
        if (null != userId) {
            hql.append("AND userId=:userId ");
            param.put("userId", userId);
        }
        if (null != orderType) {
            hql.append("AND type=:orderType ");
            param.put("orderType", orderType);
        }
        criteria.setSort("createDate");
        criteria.setOrder("desc");
        return this.findPage(hql.toString(), criteria, criteria.getParams(), param);
    }
    public PageResult<Order> appFindByPage(PageCriteria criteria, Integer userId, OrderType orderType) {
        StringBuffer hql = new StringBuffer("From Order o WHERE o.deleted = 0  AND o.status <> 'LAUNCH'");
        Map param = new HashMap();
        if (null != userId) {
            hql.append(" AND userId=:userId ");
            param.put("userId", userId);
        }
        if (orderType!=null) {
            switch (orderType.name()) {
                case "INVESTMENT":
                    hql.append(" AND (type='INVESTMENT' OR type='TRANSFER_IN' OR type='TRANSFER_OUT' OR type='RECOVERY' OR type='RECOVERY_EARLY' OR type='REPAYMENT' OR type = 'REPAYMENT_EARLY')");
                    break;
                default:
                    hql.append(" AND type=:orderType ");
                    param.put("orderType", orderType);
                    break;
            }
        }
        criteria.setSort("createDate");
        criteria.setOrder("desc");
        return this.findPage(hql.toString(), criteria, criteria.getParams(), param);
    }

    /**
     *
     * @return
     */
    public Order findOrder(OrderType type, Integer businessId, OrderStatus status, String orderNo) {
        StringBuffer hql = new StringBuffer("From Order o WHERE o.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if (null != type) {
            hql.append(" AND o.type = :type");
            params.put("type" , type);
        }
        if (null != businessId) {
            hql.append(" AND o.businessId = :businessId");
            params.put("businessId" , businessId);
        }
        if (null != status) {
            hql.append(" AND o.status = :status");
            params.put("status" , status);
        }
        if (null != orderNo) {
            hql.append(" AND o.orderNo = :orderNo");
            params.put("orderNo" , orderNo);
        }
        return this.findUnique(hql.toString(), params);
    }

//    /**
//     * 根据订单编号查询订单
//     * @return
//     */
//    public Order getByOrderNo(String orderNo) {
//        if(orderNo==null){
//            return null;
//        }
//        StringBuffer hql = new StringBuffer("From Order o WHERE o.deleted = 0 ");
//        Map<String, Object> params = new HashMap();
//        hql.append(" AND o.orderNo = :orderNo");
//        params.put("orderNo" , orderNo);
//        return this.findUnique(hql.toString(), params);
//    }
}
