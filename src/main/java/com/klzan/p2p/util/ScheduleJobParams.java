package com.klzan.p2p.util;

import java.io.Serializable;

/**
 * 任务参数
 * Created by suhao Date: 2017/5/24 Time: 11:10
 *
 * @version: 1.0
 */
public class ScheduleJobParams implements Serializable {
    private Integer jobId;
    private String params;
    private String jobKey;
    private Integer planCount;
    private Integer excutedCount;

    public ScheduleJobParams(Integer jobId, String params, String jobKey, Integer planCount, Integer excutedCount) {
        this.jobId = jobId;
        this.params = params;
        this.jobKey = jobKey;
        this.planCount = planCount;
        this.excutedCount = excutedCount;
    }

    public Integer getJobId() {
        return jobId;
    }

    public String getParams() {
        return params;
    }

    public String getJobKey() {
        return jobKey;
    }

    public Integer getPlanCount() {
        return planCount;
    }

    public Integer getExcutedCount() {
        return excutedCount;
    }
}
