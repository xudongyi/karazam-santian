package com.klzan.p2p.task;

import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.setting.AutoInvestmentSetting;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.util.ScheduleJobParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 借款状态更新定时任务
 */
@Component("borrowingPublishTask")
public class BorrowingPublishTask {

    private static final Logger logger = LoggerFactory.getLogger(BorrowingPublishTask.class);

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private SettingUtils setting;

    public void updateProgress(ScheduleJobParams params) {
        String borrowingId = params.getParams();
        Borrowing borrowing = borrowingService.get(Integer.parseInt(borrowingId));
        if (borrowing.getProgress() == BorrowingProgress.PREVIEWING) {
            AutoInvestmentSetting autoInvestmentSetting = setting.getAutoInvestmentSetting();
            Boolean isSupportAutoInvest = borrowing.getAutoInvest();
            Boolean autoInvestment = autoInvestmentSetting.getAutoInvestment();
            if (isSupportAutoInvest && autoInvestment) {
                borrowing.setProgress(BorrowingProgress.AUTOINVESTING);
            } else {
                borrowing.setProgress(BorrowingProgress.INVESTING);
            }
            borrowingService.merge(borrowing);
        }
    }


}
