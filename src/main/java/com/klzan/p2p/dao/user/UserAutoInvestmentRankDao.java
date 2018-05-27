package com.klzan.p2p.dao.user;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.UserAutoInvestmentRank;
import org.springframework.stereotype.Repository;

/**
 * Created by suhao on 2017/2/14.
 */
@Repository
public class UserAutoInvestmentRankDao extends DaoSupport<UserAutoInvestmentRank> {
    public UserAutoInvestmentRank findByUserId(Integer userId) {
        StringBuffer hql = new StringBuffer("FROM UserAutoInvestmentRank WHERE deleted=0 AND userId=?0 ");
        return findUnique(hql.toString(), userId);
    }
}
