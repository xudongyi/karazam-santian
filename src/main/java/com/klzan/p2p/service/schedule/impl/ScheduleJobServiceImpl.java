package com.klzan.p2p.service.schedule.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.enums.ScheduleStatus;
import com.klzan.p2p.mapper.ScheduleJobMapper;
import com.klzan.p2p.model.ScheduleJob;
import com.klzan.p2p.service.schedule.ScheduleJobService;
import com.klzan.p2p.util.ScheduleUtils;
import com.klzan.p2p.vo.schedule.ScheduleJobVo;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleJobServiceImpl extends BaseService<ScheduleJob> implements ScheduleJobService {
	@Autowired
    private Scheduler scheduler;
	@Autowired
	private ScheduleJobMapper scheduleJobMapper;
	
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<ScheduleJob> scheduleJobList = scheduleJobMapper.queryList(new HashMap<String, Object>());
		for(ScheduleJob scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
		}
	}
	
	@Override
	public ScheduleJob queryObject(Integer jobId) {
		return scheduleJobMapper.queryObject(jobId);
	}

	@Override
	public List<ScheduleJob> queryList(Map<String, Object> map) {
		return scheduleJobMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return scheduleJobMapper.queryTotal(map);
	}

	@Override
	public void addJob(ScheduleJob scheduleJob) {
		scheduleJob.setStatus(ScheduleStatus.NORMAL.getValue());
        this.persist(scheduleJob);
        
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }
	
	@Override
	public void updateJob(ScheduleJobVo scheduleJobVo) {
		ScheduleJob scheduleJob = queryObject(scheduleJobVo.getId());
		scheduleJob.setBeanName(scheduleJobVo.getBeanName());
		scheduleJob.setMethodName(scheduleJobVo.getMethodName());
		scheduleJob.setParams(scheduleJobVo.getParams());
		scheduleJob.setCronExpression(scheduleJobVo.getCronExpression());
		scheduleJob.setRemark(scheduleJobVo.getRemark());
		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        this.merge(scheduleJob);
    }

	@Override
    public void deleteBatch(Integer[] jobIds) {
    	for(Integer jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    	}
    	
    	//删除数据
    	scheduleJobMapper.deleteBatch(jobIds);
	}

	@Override
	public void delete(Integer jobId) {
		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
		//删除数据
		Integer[] jobIds = {jobId};
		scheduleJobMapper.deleteBatch(jobIds);
	}

	@Override
    public int updateBatch(Integer[] jobIds, int status){
    	Map<String, Object> map = new HashMap<>();
    	map.put("list", jobIds);
    	map.put("status", status);
    	return scheduleJobMapper.updateBatch(map);
    }
    
	@Override
    public void run(Integer[] jobIds) {
    	for(Integer jobId : jobIds){
    		ScheduleUtils.run(scheduler, queryObject(jobId));
    	}
    }

	@Override
    public void pause(Integer[] jobIds) {
        for(Integer jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}
        
    	updateBatch(jobIds, ScheduleStatus.PAUSE.getValue());
    }

	@Override
    public void resume(Integer[] jobIds) {
    	for(Integer jobId : jobIds){
    		ScheduleUtils.resumeJob(scheduler, jobId);
    	}

    	updateBatch(jobIds, ScheduleStatus.NORMAL.getValue());
    }

    @Override
    public PageResult<ScheduleJob> findJobPage(PageCriteria criteria) {
        return myDaoSupport.findPage("com.klzan.p2p.mapper.ScheduleJobMapper.queryList", new HashMap<>(), criteria);
    }

}
