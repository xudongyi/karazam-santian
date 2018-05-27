package com.klzan.p2p.mapper;

import com.klzan.core.persist.mybatis.MyMapper;
import com.klzan.p2p.model.ScheduleJob;

import java.util.List;
import java.util.Map;

public interface ScheduleJobMapper extends MyMapper<ScheduleJob> {
    /**
     * 批量更新状态
     */
    int updateBatch(Map<String, Object> map);

    void save(ScheduleJob scheduleJob);

    void update(ScheduleJob scheduleJob);

    void deleteBatch(Integer[] jobIds);

    List<ScheduleJob> queryList(Map map);

    ScheduleJob queryObject(Integer jobId);

    int queryTotal(Map<String, Object> map);
}
