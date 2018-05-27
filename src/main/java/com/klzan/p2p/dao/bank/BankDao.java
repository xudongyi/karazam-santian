package com.klzan.p2p.dao.bank;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.Bank;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by suhao Date: 2017/4/26 Time: 13:20
 *
 * @version: 1.0
 */
@Repository
public class BankDao extends DaoSupport<Bank> {
    public List<Bank> findList() {
        return find("FROM Bank WHERE deleted=false ");
    }

    public Bank findByCodeAndBizType(String bankCode, Integer bizType) {
        StringBuffer hql = new StringBuffer("FROM Bank WHERE deleted=false AND code = ?0 AND bizType=?1 ");
        return findUnique(hql.toString(), bankCode, bizType);
    }

    public Bank findByCode(String bankCode) {
        StringBuffer hql = new StringBuffer("FROM Bank WHERE deleted=false AND code = ?0 ");
        return findUnique(hql.toString(), bankCode);
    }
}
