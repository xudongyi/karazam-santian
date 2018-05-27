package com.klzan.p2p.task;

import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.p2p.util.ScheduleJobParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 还款通知定时任务
 */
@Component("repaymentNoticeTask")
public class RepaymentNoticeTask {

    private static final Logger logger = LoggerFactory.getLogger(RepaymentNoticeTask.class);

    @Autowired
    private RepaymentService repaymentService;

    /**
     * 还款通知任务
     */
    public void notice(ScheduleJobParams params) {
        logger.info("还款通知任务");
        repaymentService.repaymentNotice();
    }


}
