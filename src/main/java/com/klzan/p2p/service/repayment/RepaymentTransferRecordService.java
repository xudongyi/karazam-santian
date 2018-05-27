package com.klzan.p2p.service.repayment;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.model.RepaymentPlan;
import com.klzan.p2p.model.RepaymentTransferRecord;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by suhao on 2017/4/13.
 */
public interface RepaymentTransferRecordService extends IBaseService<RepaymentTransferRecord> {
    /**
     * 根据还款查询还款转账记录
     * @param repayment
     * @return
     */
    List<RepaymentTransferRecord> findByRepayment(Integer repayment);

    /**
     * 添加还款转账记录
     * @param repayment
     * @param batchOrderNo
     */
    List<RepaymentTransferRecord> addRecord(Repayment repayment, String batchOrderNo);

    /**
     * 查询提前还款转账记录（提前还款）
     * @param borrowing
     * @return
     */
    List<RepaymentTransferRecord> findByBorrowing(Integer borrowing);

    /**
     * 添加提前还款转账记录（提前还款）
     * @param repayments
     * @param batchOrderNo
     */
    List<RepaymentTransferRecord> addRecord(List<Repayment> repayments, String batchOrderNo);

    /**
     * 根据状态查询
     * @param status
     * @return
     */
    List<RepaymentTransferRecord> findByStatus(RecordStatus status);

    /**
     * 根据订单号查询出借记录
     * @param orderNo
     * @return
     */
    RepaymentTransferRecord findByOrderNo(String orderNo);

    /**
     * 查询还款转账记录状态
     * @param repayment
     * @return
     */
    Set<RecordStatus> findTransferStatus(Integer repayment);

    /**
     * 根据批次号查询
     * @param batchOrderNo
     * @return
     */
    List<RepaymentTransferRecord> findByBatchOrderNo(String batchOrderNo);

    /**
     * 根据还款和状态查询
     * @param repayment
     * @param status
     * @return
     */
    List<RepaymentTransferRecord> findByRepaymentStatus(Integer repayment, RecordStatus status);

    /**
     * 根据借款ID和状态查询（提前还款）
     * @param borrowing
     * @param status
     * @return
     */
    List<RepaymentTransferRecord> findByBorrowingStatus(Integer borrowing, RecordStatus status);

    RepaymentTransferRecord findByRepaymentOrderNo(String repaymentPlanOrderNo);

    RepaymentTransferRecord findByBorrowingOrderNo(Integer borrowing, Integer invstor);

    /**
     * 投资人集合
     * @param repayments
     * @return
     */
     Map<Integer, BigDecimal> getInvestorsByRepayments(List<Repayment> repayments);

}
