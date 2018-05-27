package com.klzan.p2p.util;

import com.klzan.core.util.SpringUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.model.ScheduleJob;
import com.klzan.p2p.model.ScheduleJobLog;
import com.klzan.p2p.service.schedule.ScheduleJobLogService;
import com.klzan.p2p.service.schedule.ScheduleJobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 定时任务
 */
public class ScheduleJobBean extends QuartzJobBean {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ExecutorService service = Executors.newSingleThreadExecutor(); 
	
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(ScheduleJob.JOB_PARAM_KEY);
        
        //获取spring bean
        ScheduleJobService scheduleJobService = SpringUtils.getBean(ScheduleJobService.class);
        ScheduleJobLogService scheduleJobLogService = SpringUtils.getBean(ScheduleJobLogService.class);

        //数据库保存执行记录
        ScheduleJobLog log = new ScheduleJobLog();
        log.setJobId(scheduleJob.getId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethodName(scheduleJob.getMethodName());
        log.setParams(scheduleJob.getParams());

        //任务开始时间
        long startTime = System.currentTimeMillis();
        
        try {
            //执行任务
        	logger.info("任务准备执行，任务ID：{}-{}-{}", scheduleJob.getId(), scheduleJob.getBeanName(), scheduleJob.getMethodName());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), 
            		scheduleJob.getMethodName(), new ScheduleJobParams(scheduleJob.getId(), scheduleJob.getParams(), scheduleJob.getJobKey(), scheduleJob.getPlanCount(), scheduleJob.getExcutedCount()));
            Future<?> future = service.submit(task);
            
			future.get();
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int)times);
			//任务状态    0：成功    1：失败
			log.setStatus(0);

			scheduleJob.addExcuteCount();
			scheduleJobService.merge(scheduleJob);

			logger.info("任务执行完毕，任务ID：{}-{}-{} 总共耗时：{}毫秒", scheduleJob.getId(), scheduleJob.getBeanName(), scheduleJob.getMethodName(), times);
			if (null != scheduleJob.getPlanCount() && scheduleJob.getPlanCount().equals(scheduleJob.getExcutedCount())) {
				logger.info("任务达到执行次数，任务ID：{}-{}-{}", scheduleJob.getId(), scheduleJob.getBeanName(), scheduleJob.getMethodName());
				scheduleJobService.delete(scheduleJob.getId());
			}
		} catch (Exception e) {
			logger.error("任务执行失败，任务ID：{}-{}-{}", scheduleJob.getId(), scheduleJob.getBeanName(), scheduleJob.getMethodName(), e);
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int)times);
			
			//任务状态    0：成功    1：失败
			log.setStatus(1);
			log.setError(StringUtils.substring(e.toString(), 0, 2000));
		}finally {
			scheduleJobLogService.addLog(log);
		}
    }
}
