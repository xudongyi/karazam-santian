package com.klzan.p2p.service.repayment.impl;

import com.klzan.core.util.SnUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.postloan.RepaymentTransferRecordDao;
import com.klzan.p2p.enums.RecordStatus;
import com.klzan.p2p.model.Repayment;
import com.klzan.p2p.model.RepaymentPlan;
import com.klzan.p2p.model.RepaymentTransferRecord;
import com.klzan.p2p.service.capital.AccountantService;
import com.klzan.p2p.service.repayment.RepaymentPlanService;
import com.klzan.p2p.service.repayment.RepaymentTransferRecordService;
import com.klzan.p2p.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

/**
 * 还款转账记录
 * Created by suhao Date: 2017/4/13 Time: 20:35
 *
 * @version: 1.0
 */
@Service
public class RepaymentTransferRecordServiceImpl extends BaseService<RepaymentTransferRecord> implements RepaymentTransferRecordService {
    @Autowired
    private RepaymentPlanService repaymentPlanService;
    @Autowired
    private RepaymentTransferRecordDao transferRecordDao;
    @Autowired
    private AccountantService accountantService;

    @Override
    public List<RepaymentTransferRecord> findByRepayment(Integer repayment) {
        return transferRecordDao.findByRepayment(repayment);
    }

    @Override
    public List<RepaymentTransferRecord> addRecord(Repayment repayment, String batchOrderNo) {
        List<RepaymentPlan> repaymentPlans = repaymentPlanService.findListByRepayment(repayment.getId());
        List<RepaymentTransferRecord> transferRecords = new ArrayList<>();
        for (RepaymentPlan repaymentPlan : repaymentPlans) {
            if(repaymentPlan.getRecoveryAmount().compareTo(BigDecimal.ZERO)>0){
                RepaymentTransferRecord transferRecord = new RepaymentTransferRecord(repayment.getBorrowing(),
                        repayment.getId(),
                        repaymentPlan.getId(),
                        null,
                        repaymentPlan.getCapitalInterest(),
                        BigDecimal.ZERO,
                        batchOrderNo,
                        repaymentPlan.getOrderNo(),
                        SnUtils.getOrderNo(),
                        CommonUtils.getLoginName(),
                        CommonUtils.getRemoteIp(),
                        false);
                transferRecordDao.persist(transferRecord);
                transferRecords.add(transferRecord);
            }
        }
        return transferRecords;
    }

    @Override
    public List<RepaymentTransferRecord> findByBorrowing(Integer borrowing) {
        return transferRecordDao.findByBorrowing(borrowing);
    }

    @Override
    public List<RepaymentTransferRecord> addRecord(List<Repayment> repayments, String batchOrderNo) {

        // TODO 投资人集合
        Map<Integer, BigDecimal> investorMap = this.getInvestorsByRepayments(repayments);

//        List<RepaymentPlan> repaymentPlans = repaymentPlanService.findListByRepayment(repayment.getId());
        List<RepaymentTransferRecord> transferRecords = new ArrayList<>();
        for (Integer investor : investorMap.keySet()) {
            RepaymentTransferRecord transferRecord = new RepaymentTransferRecord(repayments.get(0).getBorrowing(),
                    null,
                    null,
                    investor,
                    investorMap.get(investor),
                    BigDecimal.ZERO,
                    batchOrderNo,
                    SnUtils.getOrderNo(),
                    SnUtils.getOrderNo(),
                    CommonUtils.getLoginName(),
                    CommonUtils.getRemoteIp(),
                    true);
            transferRecordDao.persist(transferRecord);
            transferRecords.add(transferRecord);
        }
        this.flush();
        return transferRecords;
    }

    @Override
    public List<RepaymentTransferRecord> findByStatus(RecordStatus status) {
        return transferRecordDao.findByStatus(status);
    }

    @Override
    public RepaymentTransferRecord findByOrderNo(String orderNo) {
        return transferRecordDao.findByOrderNo(orderNo);
    }

    @Override
    public Set<RecordStatus> findTransferStatus(Integer repayment) {
        List<RepaymentTransferRecord> transferRecords = transferRecordDao.findByRepayment(repayment);
        Set<RecordStatus> statusSet = new HashSet<>();
        for (RepaymentTransferRecord transferRecord : transferRecords) {
            statusSet.add(transferRecord.getStatus());
        }
        return statusSet;
    }

    @Override
    public List<RepaymentTransferRecord> findByBatchOrderNo(String batchOrderNo) {
        return transferRecordDao.findByBatchOrderNo(batchOrderNo);
    }

    @Override
    public List<RepaymentTransferRecord> findByRepaymentStatus(Integer repayment, RecordStatus status) {
        return transferRecordDao.findByRepaymentStatus(repayment, status);
    }

    @Override
    public List<RepaymentTransferRecord> findByBorrowingStatus(Integer borrowing, RecordStatus status) {
        return transferRecordDao.findByBorrowingStatus(borrowing, status);
    }

    @Override
    public RepaymentTransferRecord findByRepaymentOrderNo(String repaymentPlanOrderNo) {
        return transferRecordDao.findByRepaymentOrderNo(repaymentPlanOrderNo);
    }

    @Override
    public RepaymentTransferRecord findByBorrowingOrderNo(Integer borrowing, Integer invstor) {
        return transferRecordDao.findByBorrowingOrderNo(borrowing, invstor);
    }

    @Override
    public Map<Integer, BigDecimal> getInvestorsByRepayments(List<Repayment> repayments){
        Map<Integer, BigDecimal> investorMap = new HashMap<Integer, BigDecimal>();
        for(Repayment repayment : repayments){
            List<RepaymentPlan> repaymentPlans = accountantService.calAhead(repaymentPlanService.findListByRepayment(repayment.getId()));
            for(RepaymentPlan repaymentPlan : repaymentPlans){
                if(repaymentPlan.getRecoveryAmount().compareTo(BigDecimal.ZERO)>0) {
                    if (investorMap.containsKey(repaymentPlan.getInvestor())) {
                        investorMap.put(repaymentPlan.getInvestor(), investorMap.get(repaymentPlan.getInvestor()).add(repaymentPlan.getRecoveryAmount()));
                    } else {
                        investorMap.put(repaymentPlan.getInvestor(), repaymentPlan.getRecoveryAmount());
                    }
                }
            }
        }
        return investorMap;
    }
}
