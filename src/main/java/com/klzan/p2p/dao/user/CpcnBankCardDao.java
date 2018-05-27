package com.klzan.p2p.dao.user;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.enums.BankCardStatus;
import com.klzan.p2p.model.CpcnBankCard;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CpcnBankCardDao extends DaoSupport<CpcnBankCard> {

    public CpcnBankCard find(Integer userId, String orderNo, String bindingSystemNo) {
        StringBuffer hql = new StringBuffer("FROM CpcnBankCard WHERE deleted=0 ");
        Map<String, Object> params = new HashedMap();
        if(userId != null){
            hql.append(" and userId = :userId");
            params.put("userId", userId);
        }
        if(orderNo != null){
            hql.append(" and orderNo = :orderNo");
            params.put("orderNo", orderNo);
        }
        if(bindingSystemNo != null){
            hql.append(" and bindingSystemNo = :bindingSystemNo");
            params.put("bindingSystemNo", bindingSystemNo);
        }
        return this.findUnique(hql.toString(), params);
    }

    public List<CpcnBankCard> findByUserId(Integer userId) {
        String hql = "FROM CpcnBankCard WHERE deleted=0 AND userId=?0 ";
        return this.find(hql, userId);
    }

    public List<CpcnBankCard> findByUserId(Integer userId, BankCardStatus... status) {
        Map params = new HashMap();
        params.put("userId", userId);
        List<String> states = new ArrayList<>();
        for (BankCardStatus cardStatus : status) {
            states.add(cardStatus.getStatus());
        }
        params.put("status", states);
        StringBuffer hql = new StringBuffer("FROM CpcnBankCard WHERE deleted=0 AND userId=:userId AND status IN (:status) ");

        return this.find(hql.toString(), params);
    }

}
