package com.klzan.p2p.mapper;

import com.klzan.core.persist.mybatis.MyMapper;
import com.klzan.p2p.model.ScheduleJobLog;

import java.util.List;
import java.util.Map;

public interface ScheduleJobLogMapper extends MyMapper<ScheduleJobLog> {
    ScheduleJobLog queryObject(Integer jobId);

    List<ScheduleJobLog> queryList(Map map);

    int queryTotal(Map map);

    void save(ScheduleJobLog scheduleJobLog);
}
