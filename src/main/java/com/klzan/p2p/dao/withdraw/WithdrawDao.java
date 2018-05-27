package com.klzan.p2p.dao.withdraw;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.WithdrawRecord;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suhao Date: 2016/12/15 Time: 14:47
 *
 * @version: 1.0
 */
@Repository
public class WithdrawDao extends DaoSupport<WithdrawRecord> {
    public List<WithdrawRecord> findWithdrawing(Integer userId) {
        String hql = "FROM WithdrawRecord WHERE userId=?0 AND (status='PROCESSING') ";
        return find(hql, userId);
    }
    public List<WithdrawRecord> findWithdrawingMobile(Integer userId) {
        String hql = "FROM WithdrawRecord WHERE userId=?0 AND (status='PROCESSING' OR status='NEW_CREATE' ) ";
        return find(hql, userId);
    }

    public List<WithdrawRecord> findByUser(Integer userId, RecordStatus status) {
        String hql = "FROM WithdrawRecord WHERE userId=?0 AND status=?1 ";
        return find(hql, userId, status);
    }

    public List<WithdrawRecord> findByUser(Integer userId) {
        StringBuffer hql = new StringBuffer("FROM WithdrawRecord w WHERE w.deleted = 0 AND userId=?0 ");
        return find(hql.toString(), userId);
    }

    public List<WithdrawRecord> findList(RecordStatus status) {
        StringBuffer hql = new StringBuffer("FROM WithdrawRecord w WHERE w.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if(status != null){
            hql.append(" AND w.status = :status");
            params.put("status" , status);
        }
        return this.find(hql.toString(), params);
    }

    /**
     * 提现记录
     * @param orderNo
     * @return
     */
    public WithdrawRecord findByOrderNo(String orderNo) {
        StringBuilder hql = new StringBuilder("FROM WithdrawRecord WHERE orderNo=?0 ");
        return findUnique(hql.toString(), orderNo);
    }

    public PageResult<WithdrawRecord> findPageByUser(Integer userId, PageCriteria criteria, RecordStatus status) {
        criteria.getParams().add(ParamsFilter.addFilter("userId", ParamsFilter.MatchType.EQ, userId));
        criteria.setSort("id");
        criteria.setOrder("desc");
        StringBuilder hql = new StringBuilder("FROM WithdrawRecord WHERE deleted=false ");
        Map map = new HashMap();
        if (null != status) {
            map.put("status", status);
            hql.append("AND status=:status");
        }
        return findPage(hql.toString(), criteria, criteria.getParams(), map);
    }

}
