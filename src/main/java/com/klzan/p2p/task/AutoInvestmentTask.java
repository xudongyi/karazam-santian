package com.klzan.p2p.task;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.service.investment.AutoInvestmentService;
import com.klzan.p2p.util.ScheduleJobParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动投标定时任务
 */
@Component("autoInvestmentTask")
public class AutoInvestmentTask {

    private static final Logger logger = LoggerFactory.getLogger(AutoInvestmentTask.class);

    @Autowired
    private AutoInvestmentService autoInvestmentService;

    public void autoInvestment(ScheduleJobParams params) {
        Integer borrowingId = Integer.parseInt(params.getParams());
        logger.warn("标的{}自动投标开始{}", borrowingId, DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS));
        autoInvestmentService.executeAutoInvest(borrowingId);
        logger.warn("标的{}自动投标结束{}", borrowingId, DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS));
    }

}
