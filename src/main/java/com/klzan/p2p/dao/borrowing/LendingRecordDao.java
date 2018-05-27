/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.borrowing;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.LendingRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 出借记录
 */
@Repository
public class LendingRecordDao extends DaoSupport<LendingRecord> {

    public List<LendingRecord> findByBorrowing(Integer borrowingId) {
        String hql = "FROM LendingRecord WHERE deleted=false AND borrowing=?0 ";
        return this.find(hql, borrowingId);
    }

    public List<LendingRecord> findByStatus(RecordStatus status) {
        String hql = "FROM LendingRecord WHERE deleted=false AND status=?0 ";
        return this.find(hql, status);
    }

    public LendingRecord findByOrderNo(String orderNo) {
        String hql = "FROM LendingRecord WHERE deleted=false AND orderNo=?0 ";
        return this.findUnique(hql, orderNo);
    }

    public List<LendingRecord> findByBatchOrderNo(String batchOrderNo) {
        String hql = "FROM LendingRecord WHERE deleted=false AND batchOrderNo=?0 ";
        return this.find(hql, batchOrderNo);
    }

    public List<LendingRecord> findByBorrowingStatus(Integer borrowingId, RecordStatus status) {
        String hql = "FROM LendingRecord WHERE deleted=false AND borrowing=?0 AND status=?1 ";
        return this.find(hql, borrowingId, status);
    }
}