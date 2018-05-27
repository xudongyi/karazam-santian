package com.klzan.p2p.dao.user;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by suhao Date: 2017/11/23 Time: 17:23
 *
 * @version: 1.0
 */
@Repository
public class CpcnAccDao extends DaoSupport<CpcnPayAccountInfo> {

    public CpcnPayAccountInfo findByPayAcc(String payAccount) {
        String hql = "FROM CpcnPayAccountInfo WHERE deleted=0 AND accountNumber=?0 ";
        return this.findUnique(hql, payAccount);
    }

    public CpcnPayAccountInfo findByUserId(Integer userId) {
        String hql = "FROM CpcnPayAccountInfo WHERE deleted=0 AND userId=?0 ";
        return this.findUnique(hql, userId);
    }
}
