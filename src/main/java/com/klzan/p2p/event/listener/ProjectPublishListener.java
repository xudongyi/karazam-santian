package com.klzan.p2p.event.listener;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.event.ProjectPublishEvent;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.ScheduleJob;
import com.klzan.p2p.service.borrowing.BorrowingService;
import com.klzan.p2p.service.investment.AutoInvestmentService;
import com.klzan.p2p.service.schedule.ScheduleJobService;
import com.klzan.p2p.setting.AutoInvestmentSetting;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.util.CronExpressionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;

/**
 * Created by suhao Date: 2017/2/15 Time: 14:32
 *
 * @version: 1.0
 */
@Component
public class ProjectPublishListener implements ApplicationListener {
    private static final Logger logger = LoggerFactory.getLogger(ProjectPublishListener.class);
    @Inject
    private BorrowingService borrowingService;
    @Inject
    private AutoInvestmentService autoInvestmentService;
    @Inject
    private ScheduleJobService scheduleJobService;
    @Inject
    private SettingUtils setting;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ProjectPublishEvent) {
            Integer borrowingId = ((ProjectPublishEvent)event).getBorrowingId();
            logger.info("标的{}发布后续处理 {}", borrowingId, DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS));
            Borrowing borrowing = borrowingService.get(borrowingId);
            AutoInvestmentSetting autoInvestmentSetting = setting.getAutoInvestmentSetting();

            Boolean isSupportAutoInvest = borrowing.getAutoInvest();
            Boolean autoInvestment = autoInvestmentSetting.getAutoInvestment();

            if (borrowing.getProgress() != BorrowingProgress.PREVIEWING) {
                return;
            }

            boolean isTimingProject = false;
            Date investmentStartDate = borrowing.getInvestmentStartDate();
            if (investmentStartDate.after(new Date())) {
                isTimingProject = true;
            }

            if (!isTimingProject) {
                if (isSupportAutoInvest && autoInvestment) {
                    borrowing.setProgress(BorrowingProgress.AUTOINVESTING);
                } else {
                    borrowing.setProgress(BorrowingProgress.INVESTING);
                }
            } else {
                logger.info("标的{}借款进度更新定时任务 {}", borrowingId, DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS));
                ScheduleJob job = new ScheduleJob();
                job.setBeanName("borrowingPublishTask");
                job.setMethodName("updateProgress");
                job.setParams(borrowing.getId().toString());
                job.setPlanCount(1);
                job.setStatus(0);
                job.setRemark(borrowing.getId() + "借款进度更新定时任务");
                job.setCronExpression(CronExpressionUtils.getCron(investmentStartDate));
                scheduleJobService.addJob(job);
            }

            if (null != borrowing.getInvestmentEndDate()) {
                logger.info("标的{}借款定时任务 {}", borrowingId, DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS));
                ScheduleJob job = new ScheduleJob();
                job.setBeanName("borrowingExpireTask");
                job.setMethodName("expire");
                job.setParams(borrowing.getId().toString());
                job.setPlanCount(1);
                job.setStatus(0);
                job.setCronExpression(CronExpressionUtils.getCron(borrowing.getInvestmentEndDate()));
                job.setRemark(borrowing.getId() + "借款定时任务");
                scheduleJobService.addJob(job);
            }

            // 自动投标
            if (isSupportAutoInvest && autoInvestment) {
                if (isTimingProject) {
                    logger.info("标的{}借款自动投标定时任务 {}", borrowingId, DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS));
                    ScheduleJob job = new ScheduleJob();
                    job.setBeanName("autoInvestmentTask");
                    job.setMethodName("autoInvestment");
                    job.setParams(borrowing.getId().toString());
                    job.setPlanCount(1);
                    job.setStatus(0);
                    job.setRemark(borrowing.getId() + "借款自动投标定时任务");
                    job.setCronExpression(CronExpressionUtils.getCron(DateUtils.addSeconds(investmentStartDate, 5)));
                    scheduleJobService.addJob(job);
                } else {
                    logger.info("标的{}借款执行定时任务 {}", borrowingId, DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS));
                    autoInvestmentService.executeAutoInvest(borrowingId);
                }
            }
        }
    }
}
