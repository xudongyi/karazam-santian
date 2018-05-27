package com.klzan.p2p.dao.capital;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.SpringUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.setting.BasicSetting;
import com.klzan.p2p.vo.capital.PaymentOrderVo;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 支付订单
 * @version: 1.0
 */
@Repository
public class PaymentOrderDao extends DaoSupport<PaymentOrder> {

    public PageResult<PaymentOrderVo> findPage(PageCriteria criteria, boolean isPlatform) {
        StringBuilder sql = new StringBuilder("select ");
        sql.append("p.id                id, ");
        sql.append("p.user_id           userId, ");
        sql.append("u.name              name, ");
        sql.append("u.mobile            mobile, ");
        sql.append("p.create_date       createDate, ");
        sql.append("p.status            statusStr, ");
        sql.append("p.type              typeStr, ");
        sql.append("p.method            methodStr, ");
        sql.append("p.order_no          orderNo, ");
        sql.append("p.parent_order_no   parentOrderNo, ");
        sql.append("p.ext_order_no      extOrderNo, ");
        sql.append("p.amount            amount, ");
        sql.append("p.memo              memo, ");
        sql.append("p.ext               ext ");
        sql.append("from karazam_payment_order  p ");
        sql.append("left join karazam_user u    on u.id=p.user_id ");
        sql.append("where   p.deleted   =   0 ");
        sql.append("and     p.type      in   :types ");
        List<String> types = new ArrayList<>();
        if (isPlatform) {
            types.add(PaymentOrderType.WITHDRAWAL.name());
        } else {
            types.add(PaymentOrderType.INVESTMENT.name());
            types.add(PaymentOrderType.RECHARGE.name());
        }
        Map<String, Object> params = new HashedMap();
        params.put("types", types);
        criteria.desc("p.create_date");
        return this.findPageBySQL(sql.toString(), criteria, new ScalarAliasCallback<PaymentOrderVo>() {
            @Override
            protected Class<PaymentOrderVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("userId", StandardBasicTypes.INTEGER);
                query.addScalar("name", StandardBasicTypes.STRING);
                query.addScalar("mobile", StandardBasicTypes.STRING);
                query.addScalar("statusStr", StandardBasicTypes.STRING);
                query.addScalar("typeStr", StandardBasicTypes.STRING);
                query.addScalar("methodStr", StandardBasicTypes.STRING);
                query.addScalar("orderNo", StandardBasicTypes.STRING);
                query.addScalar("parentOrderNo", StandardBasicTypes.STRING);
                query.addScalar("extOrderNo", StandardBasicTypes.STRING);
                query.addScalar("amount", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("memo", StandardBasicTypes.STRING);
                query.addScalar("ext", StandardBasicTypes.STRING);
                query.addScalar("createDate", StandardBasicTypes.TIMESTAMP);
                return PaymentOrderVo.class;
            }
        }, criteria.getParams(), params);
    }

    public PageResult<PaymentOrder> findPage(PageCriteria criteria, PaymentOrderType type, Integer userId) {
        StringBuffer hql = new StringBuffer("From PaymentOrder p WHERE p.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(type != null){
            hql.append(" AND p.type = :type ");
            params.put("type", type);
        }
        if(userId != null){
            hql.append(" AND p.userId = :userId ");
            params.put("userId", userId);
        }
        hql.append(" AND p.type <> 'quickPaymentGetSMS' ");
        criteria.setSortName("createDate");
        criteria.setOrder("desc");
        return this.findPage(hql.toString(), criteria, criteria.getParams(), params);
    }

    public PaymentOrder findByOrderNo(String orderNo){
        StringBuffer hql = new StringBuffer("From PaymentOrder p WHERE p.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if (StringUtils.isBlank(orderNo)) {
            throw new BusinessProcessException("订单号不能为空");
        }
        hql.append(" AND p.orderNo = :orderNo");
        params.put("orderNo", orderNo);
        return findUnique(hql.toString(), params);
    }

    public PaymentOrder find(PaymentOrderStatus status, PaymentOrderType type, Integer borrowingId, Integer userId) {
        StringBuffer hql = new StringBuffer("From PaymentOrder p WHERE p.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(status != null && type != null && borrowingId != null && userId != null){
            hql.append(" AND p.status = :status AND p.type = :type AND p.borrowing = :borrowingId AND p.userId = :userId AND p.expire > :occupyDate");
            params.put("status", status);
            params.put("type", type);
            params.put("borrowingId", borrowingId);
            params.put("userId", userId);
            params.put("occupyDate", new Date());
        }
        List<PaymentOrder> list = this.find(hql.toString(), params);
        if(list == null || list.size()==0){
            return null;
        }
        return list.get(0);
    }

    public PaymentOrder find(Integer userId, PaymentOrderStatus status, PaymentOrderType type) {
        StringBuffer hql = new StringBuffer("From PaymentOrder p WHERE p.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(status != null && type != null && userId != null){
            hql.append(" AND p.status = :status AND p.type = :type AND p.userId = :userId ");
            params.put("status", status);
            params.put("type", type);
            params.put("userId", userId);
        }
        return this.findUnique(hql.toString(), params);
    }

    public List<PaymentOrder> findByParentOrderNo(String orderNo){
        StringBuffer hql = new StringBuffer("From PaymentOrder p WHERE p.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(orderNo != null){
            hql.append(" AND p.parentOrderNo = :orderNo");
            params.put("orderNo", orderNo);
        }
        return this.find(hql.toString(), params);
    }

    public List<PaymentOrder> findListForTask(PaymentOrderStatus status){
        StringBuffer hql = new StringBuffer("From PaymentOrder p WHERE p.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(status != null){
            hql.append(" AND p.status = :status");
            params.put("status", status);
        }
        hql.append(" AND p.type <> :type1");
        params.put("type1", PaymentOrderType.WITHDRAWAL);
        hql.append(" AND p.type <> :type2");
        params.put("type2", PaymentOrderType.RECHARGE);
        return this.find(hql.toString(), params);
    }

    public List<PaymentOrder> findWithdrawListForTask(PaymentOrderStatus status){
        StringBuffer hql = new StringBuffer("From PaymentOrder p WHERE p.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        hql.append(" AND p.status = :status");
        params.put("status", status);
        hql.append(" AND p.type = :type");
        params.put("type", PaymentOrderType.WITHDRAWAL);
        return this.find(hql.toString(), params);
    }

    public List<PaymentOrder> findRechargeListForTask(PaymentOrderStatus status){
        StringBuffer hql = new StringBuffer("From PaymentOrder p WHERE p.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        hql.append(" AND p.status = :status");
        params.put("status", status);
        hql.append(" AND p.type = :type");
        params.put("type", PaymentOrderType.RECHARGE);
        return this.find(hql.toString(), params);
    }

    public List<PaymentOrder> findList(Integer userId, PaymentOrderStatus status, PaymentOrderType type) {
        StringBuffer hql = new StringBuffer("From PaymentOrder p WHERE p.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(status != null && type != null && userId != null){
            hql.append(" AND p.status = :status AND p.type = :type AND p.userId = :userId ");
            params.put("status", status);
            params.put("type", type);
            params.put("userId", userId);
        }
        return this.find(hql.toString(), params);
    }
}