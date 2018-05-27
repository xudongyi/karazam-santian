package com.klzan.p2p.service.schedule;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.model.ScheduleJobLog;

import java.util.List;
import java.util.Map;

/**
 * 定时任务日志
 */
public interface ScheduleJobLogService {

	/**
	 * 根据ID，查询定时任务日志
	 */
	ScheduleJobLog queryObject(Integer jobId);
	
	/**
	 * 查询定时任务日志列表
	 */
	List<ScheduleJobLog> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存定时任务日志
	 */
	void addLog(ScheduleJobLog log);

	/**
	 * 查询分页
	 * @param criteria
	 * @return
	 */
	PageResult<ScheduleJobLog> findJobLogPage(PageCriteria criteria);
}
