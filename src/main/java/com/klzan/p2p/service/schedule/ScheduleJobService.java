package com.klzan.p2p.service.schedule;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.ScheduleJob;
import com.klzan.p2p.vo.schedule.ScheduleJobVo;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 */
public interface ScheduleJobService extends IBaseService<ScheduleJob> {

	/**
	 * 根据ID，查询定时任务
	 */
	ScheduleJob queryObject(Integer jobId);
	
	/**
	 * 查询定时任务列表
	 */
	List<ScheduleJob> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存定时任务
	 */
	void addJob(ScheduleJob scheduleJob);
	
	/**
	 * 更新定时任务
	 */
	void updateJob(ScheduleJobVo scheduleJobVo);
	
	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(Integer[] jobIds);

	/**
	 * 删除定时任务
	 * @param jobId
	 */
	void delete(Integer jobId);

	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(Integer[] jobIds, int status);
	
	/**
	 * 立即执行
	 */
	void run(Integer[] jobIds);
	
	/**
	 * 暂停运行
	 */
	void pause(Integer[] jobIds);
	
	/**
	 * 恢复运行
	 */
	void resume(Integer[] jobIds);

	PageResult<ScheduleJob> findJobPage(PageCriteria criteria);
}
