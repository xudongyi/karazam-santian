/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.postloan;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.enums.CpcnRepaymentStatus;
import com.klzan.p2p.enums.CpcnSettlementStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnSettlement;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 还款
 * @author: chenxinglin
 */
@Repository
public class CpcnSettlementDao extends DaoSupport<CpcnSettlement> {

    public CpcnSettlement findByOrderNoRepayment(String rOrderNo) {
        StringBuilder hql = new StringBuilder("From CpcnSettlement c where c.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(rOrderNo != null){
            hql.append(" AND c.rOrderNo = :rOrderNo");
            params.put("rOrderNo", rOrderNo);
        }
        return this.findUnique(hql.toString(), params);
    }

    public CpcnSettlement findByOrderNoSettlement(String sOrderNo) {
        StringBuilder hql = new StringBuilder("From CpcnSettlement c where c.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(sOrderNo != null){
            hql.append(" AND c.sOrderNo LIKE '%"+sOrderNo+"%' ");
//            params.put("orderNoSettlement", orderNoSettlement);
        }
        return this.findUnique(hql.toString(), params);
    }

    public CpcnSettlement find(PaymentOrderType type, Integer borrowing, Integer repayment) {//AND s.sStatus <> 'failure'
        StringBuffer hql = new StringBuffer("From CpcnSettlement s WHERE s.deleted = 0 AND s.rStatus <> 'failure' ");
        Map<String, Object> params = new HashMap();
        if(type != null){
            hql.append(" AND s.type = :type ");
            params.put("type", type);
        }
        if(borrowing != null){
            hql.append(" AND s.borrowing = :borrowing ");
            params.put("borrowing", borrowing);
        }
        if(repayment != null){
            hql.append(" AND s.repayment = :repayment ");
            params.put("repayment", repayment);
        }
        return this.findUnique(hql.toString(), params);
    }

    // 退款、出借
    public CpcnSettlement findSettlement(PaymentOrderType type, Integer borrowing) {
        StringBuffer hql = new StringBuffer("From CpcnSettlement s WHERE s.deleted = 0 AND s.sStatus <> 'failure' ");
        Map<String, Object> params = new HashMap();
        if(type != null){
            hql.append(" AND s.type = :type ");
            params.put("type", type);
        }
        if(borrowing != null){
            hql.append(" AND s.borrowing = :borrowing ");
            params.put("borrowing", borrowing);
        }
        return this.findUnique(hql.toString(), params);
    }

    public List<CpcnSettlement> findUnSettlement() {//AND s.sStatus <> 'failure'
        StringBuffer hql = new StringBuffer("From CpcnSettlement s WHERE s.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        hql.append(" AND ( s.type = :type1 OR s.type = :type2 )");
        params.put("type1", PaymentOrderType.REPAYMENT);
        params.put("type2", PaymentOrderType.REPAYMENT_EARLY);
        hql.append(" AND s.rStatus = :rStatus ");
        params.put("rStatus", CpcnRepaymentStatus.success);
        hql.append(" AND s.sStatus <> :sStatus) ");
        params.put("sStatus", CpcnSettlementStatus.success);
        return this.find(hql.toString(), params);
    }

    public List<CpcnSettlement> findUnSettlementTransfer() {//AND s.sStatus <> 'failure'
        StringBuffer hql = new StringBuffer("From CpcnSettlement s WHERE s.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        hql.append(" AND s.type = :type");
        params.put("type", PaymentOrderType.TRANSFER);
        hql.append(" AND s.sStatus <> :sStatus) ");
        params.put("sStatus", CpcnSettlementStatus.success);
        return this.find(hql.toString(), params);
    }

}