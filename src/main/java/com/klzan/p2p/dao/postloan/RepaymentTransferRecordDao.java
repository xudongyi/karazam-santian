/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.postloan;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.RepaymentTransferRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 还款转账记录
 */
@Repository
public class RepaymentTransferRecordDao extends DaoSupport<RepaymentTransferRecord> {

    public List<RepaymentTransferRecord> findByRepayment(Integer repayment) {
        String hql = "FROM RepaymentTransferRecord WHERE deleted=false AND repayment=?0 ";
        return this.find(hql, repayment);
    }

    public List<RepaymentTransferRecord> findByStatus(RecordStatus status) {
        String hql = "FROM RepaymentTransferRecord WHERE deleted=false AND status=?0 ";
        return this.find(hql, status);
    }

    public RepaymentTransferRecord findByOrderNo(String orderNo) {
        String hql = "FROM RepaymentTransferRecord WHERE deleted=false AND orderNo=?0 ";
        return this.findUnique(hql, orderNo);
    }

    public List<RepaymentTransferRecord> findByBatchOrderNo(String batchOrderNo) {
        String hql = "FROM RepaymentTransferRecord WHERE deleted=false AND batchOrderNo=?0 ";
        return this.find(hql, batchOrderNo);
    }

    public List<RepaymentTransferRecord> findByRepaymentStatus(Integer repayment, RecordStatus status) {
        String hql = "FROM RepaymentTransferRecord WHERE deleted=false AND repayment=?0 AND status=?1 ";
        return this.find(hql, repayment, status);
    }

    public List<RepaymentTransferRecord> findByBorrowingStatus(Integer borrowing, RecordStatus status) {
        String hql = "FROM RepaymentTransferRecord WHERE deleted=false AND ahead=true AND borrowing=?0 AND status=?1 ";
        return this.find(hql, borrowing, status);
    }

    public RepaymentTransferRecord findByRepaymentOrderNo(String repaymentPlanOrderNo) {
        String hql = "FROM RepaymentTransferRecord WHERE deleted=false AND planOrderNo=?0 ";
        return this.findUnique(hql, repaymentPlanOrderNo);
    }

    public RepaymentTransferRecord findByBorrowingOrderNo(Integer borrowing, Integer invstor) {
        String hql = "FROM RepaymentTransferRecord WHERE deleted=false AND ahead=true AND borrowing=?0 AND invstor=?1 ";
        return this.findUnique(hql, borrowing, invstor);
    }

    public List<RepaymentTransferRecord> findByBorrowing(Integer borrowing) {
        String hql = "FROM RepaymentTransferRecord WHERE deleted=false AND ahead=true AND borrowing=?0 ";
        return this.find(hql, borrowing);
    }
}