package com.klzan.p2p.task;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.enums.BorrowingState;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.InvestmentRecord;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.util.ScheduleJobParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 借款流标定时任务
 */
@Component("borrowingExpireTask")
public class BorrowingExpireTask {

    private static final Logger logger = LoggerFactory.getLogger(BorrowingExpireTask.class);

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private InvestmentRecordService investmentRecordService;

    public void expire(ScheduleJobParams params) {
        String borrowingId = params.getParams();
        logger.info("借款{}流标定时任务-更新标的状态", borrowingId);
        Borrowing borrowing = borrowingService.get(Integer.parseInt(borrowingId));
        if (DateUtils.compareTwoDate(new Date(), borrowing.getInvestmentEndDate()) == 1) {
            return;
        }
        if (borrowing.getIsInvestFull() || borrowing.getProgress() == BorrowingProgress.LENDING) {
            return;
        }
        borrowing.setProgress(BorrowingProgress.EXPIRE);
        borrowing.setState(BorrowingState.EXPIRY);
        borrowingService.update(borrowing);
        List<InvestmentRecord> records = investmentRecordService.findList(Integer.parseInt(borrowingId), InvestmentState.PAID);
        if (records.isEmpty()) {
            return;
        }

    }


}
