package com.klzan.p2p.service.schedule.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.mapper.ScheduleJobLogMapper;
import com.klzan.p2p.model.ScheduleJobLog;
import com.klzan.p2p.service.schedule.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleJobLogServiceImpl extends BaseService<ScheduleJobLog> implements ScheduleJobLogService {
	@Autowired
	private ScheduleJobLogMapper scheduleJobLogMapper;
	
	@Override
	public ScheduleJobLog queryObject(Integer jobId) {
		return scheduleJobLogMapper.queryObject(jobId);
	}

	@Override
	public List<ScheduleJobLog> queryList(Map<String, Object> map) {
		return scheduleJobLogMapper.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return scheduleJobLogMapper.queryTotal(map);
	}

	@Override
	public void addLog(ScheduleJobLog log) {
		this.persist(log);
	}

    @Override
    public PageResult<ScheduleJobLog> findJobLogPage(PageCriteria criteria) {
		return myDaoSupport.findPage("com.klzan.p2p.mapper.ScheduleJobLogMapper.queryList", new HashMap<>(), criteria);
    }

}
