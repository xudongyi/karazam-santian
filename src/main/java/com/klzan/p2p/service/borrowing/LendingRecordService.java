package com.klzan.p2p.service.borrowing;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.Investment;
import com.klzan.p2p.model.LendingRecord;

import java.util.List;
import java.util.Set;

/**
 * Created by suhao on 2017/4/13.
 */
public interface LendingRecordService extends IBaseService<LendingRecord> {
    /**
     * 根据借款查询出借记录
     * @param borrowingId
     * @return
     */
    List<LendingRecord> findByBorrowing(Integer borrowingId);

    /**
     * 添加出借记录
     * @param investment
     * @param batchOrderNo
     */
    List<LendingRecord> addRecord(Investment investment, String batchOrderNo);

    /**
     * 根据状态查询
     * @param status
     * @return
     */
    List<LendingRecord> findByStatus(RecordStatus status);

    /**
     * 根据订单号查询出借记录
     * @param orderNo
     * @return
     */
    LendingRecord findByOrderNo(String orderNo);

    /**
     * 查询借款出借记录状态
     * @param borrowingId
     * @return
     */
    Set<RecordStatus> findLendingStatus(Integer borrowingId);

    /**
     * 根据批次号查询
     * @param batchOrderNo
     * @return
     */
    List<LendingRecord> findByBatchOrderNo(String batchOrderNo);

    /**
     * 根据借款和状态查询
     * @param borrowingId
     * @param status
     * @return
     */
    List<LendingRecord> findByBorrowingStatus(Integer borrowingId, RecordStatus status);
}
