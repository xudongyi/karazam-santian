package com.klzan.p2p.service.borrowing.impl;

import com.klzan.core.util.SnUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.LendingRecordDao;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.Investment;
import com.klzan.p2p.model.InvestmentRecord;
import com.klzan.p2p.model.LendingRecord;
import com.klzan.p2p.service.borrowing.LendingRecordService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 出借记录
 * Created by suhao Date: 2017/4/13 Time: 20:35
 *
 * @version: 1.0
 */
@Service
public class LendingRecordServiceImpl extends BaseService<LendingRecord> implements LendingRecordService {
    @Autowired
    private InvestmentRecordService investmentRecordService;
    @Autowired
    private LendingRecordDao lendingRecordDao;

    @Override
    public List<LendingRecord> findByBorrowing(Integer borrowingId) {
        return lendingRecordDao.findByBorrowing(borrowingId);
    }

    @Override
    public List<LendingRecord> addRecord(Investment investment, String batchOrderNo) {
        List<InvestmentRecord> records = investmentRecordService.findListByInvestment(investment.getId(), InvestmentState.PAID);
        List<LendingRecord> lendingRecords = new ArrayList<>();
        for (InvestmentRecord record : records) {
            LendingRecord lendingRecord = new LendingRecord(investment.getBorrowing(),
                    investment.getId(),
                    record.getId(),
                    record.getAmount(),
                    batchOrderNo,
                    record.getOrderNo(),
                    SnUtils.getOrderNo(),
                    CommonUtils.getLoginName(),
                    CommonUtils.getRemoteIp());
            lendingRecordDao.persist(lendingRecord);
            lendingRecords.add(lendingRecord);
        }
        return lendingRecords;
    }

    @Override
    public List<LendingRecord> findByStatus(RecordStatus status) {
        return lendingRecordDao.findByStatus(status);
    }

    @Override
    public LendingRecord findByOrderNo(String orderNo) {
        return lendingRecordDao.findByOrderNo(orderNo);
    }

    @Override
    public Set<RecordStatus> findLendingStatus(Integer borrowingId) {
        List<LendingRecord> lendingRecords = lendingRecordDao.findByBorrowing(borrowingId);
        Set<RecordStatus> statusSet = new HashSet<>();
        for (LendingRecord lendingRecord : lendingRecords) {
            statusSet.add(lendingRecord.getStatus());
        }
        return statusSet;
    }

    @Override
    public List<LendingRecord> findByBatchOrderNo(String batchOrderNo) {
        return lendingRecordDao.findByBatchOrderNo(batchOrderNo);
    }

    @Override
    public List<LendingRecord> findByBorrowingStatus(Integer borrowingId, RecordStatus status) {
        return lendingRecordDao.findByBorrowingStatus(borrowingId, status);
    }
}
