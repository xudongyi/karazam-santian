package com.klzan.p2p.task;

import com.klzan.p2p.enums.TransferLoanState;
import com.klzan.p2p.enums.TransferState;
import com.klzan.p2p.model.Transfer;
import com.klzan.p2p.service.transfer.TransferService;
import com.klzan.p2p.util.ScheduleJobParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 债权转让撤销定时任务
 */
@Component("transferExpireTask")
public class TransferExpireTask {

    private static final Logger logger = LoggerFactory.getLogger(TransferExpireTask.class);

    @Autowired
    private TransferService transferService;

    public void expire(ScheduleJobParams params) {
        String transferId = params.getParams();
        Transfer transfer = transferService.get(Integer.parseInt(transferId));
        if (transfer.getState() == TransferLoanState.CANCEL) {
            logger.warn("{}债权已经是撤销状态", transferId);
            return;
        }
        transferService.transferCancel(transfer);
    }

}
