package com.klzan.p2p.vo.schedule;

import com.klzan.p2p.common.vo.BaseVo;

/**
 * Created by suhao Date: 2017/4/18 Time: 16:14
 *
 * @version: 1.0
 */
public class ScheduleJobVo extends BaseVo {
    /**
     * spring bean名称
     */
    private String beanName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * 任务key
     */
    private String key;

    /**
     * 任务计划执行次数
     */
    private Integer planCount;

    /**
     * 已执行次数
     */
    private Integer excutedCount;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 备注
     */
    private String remark;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPlanCount() {
        return planCount;
    }

    public void setPlanCount(Integer planCount) {
        this.planCount = planCount;
    }

    public Integer getExcutedCount() {
        return excutedCount;
    }

    public void setExcutedCount(Integer excutedCount) {
        this.excutedCount = excutedCount;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
