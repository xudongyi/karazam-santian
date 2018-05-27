package com.klzan.p2p.dao.recharge;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.RechargeRecord;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suhao Date: 2016/12/8 Time: 11:46
 *
 * @version: 1.0
 */
@Repository
public class RechargeRecordDao extends DaoSupport<RechargeRecord> {

    /**
     * 充值记录
     * @param orderNo
     * @return
     */
    public RechargeRecord findByOrderNo(String orderNo) {
        StringBuilder hql = new StringBuilder("FROM RechargeRecord WHERE orderNo=?0 ");
        return findUnique(hql.toString(), orderNo);
    }

    public PageResult<RechargeRecord> findPageByUser(Integer userId, PageCriteria criteria, RecordStatus status) {
        criteria.getParams().add(ParamsFilter.addFilter("userId", ParamsFilter.MatchType.EQ, userId));
        criteria.setSort("id");
        criteria.setOrder("desc");
        StringBuilder hql = new StringBuilder("FROM RechargeRecord WHERE deleted=false ");
        Map map = new HashMap();
        if (null != status) {
            map.put("status", status);
            hql.append("AND status=:status");
        }
        return findPage(hql.toString(), criteria, criteria.getParams(), map);
    }

    public List<RechargeRecord> findByUser(Integer userId) {
        StringBuilder hql = new StringBuilder("FROM RechargeRecord WHERE deleted=false AND userId=?0 ");
        return find(hql.toString(), userId);
    }
}
