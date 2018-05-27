package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 定时器实体类
 */
@Entity
@Table(name = "karazam_schedule_job")
public class ScheduleJob extends BaseModel {

    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    /**
     * spring bean名称
     */
    @NotBlank(message="bean名称不能为空")
    private String beanName;

    /**
     * 方法名
     */
    @NotBlank(message="方法名称不能为空")
    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * 任务key
     */
    private String jobKey;

    /**
     * 任务计划执行次数
     */
    private Integer planCount;

    /**
     * 已执行次数
     */
    private Integer excutedCount = 0;

    /**
     * cron表达式
     */
    @NotBlank(message="cron表达式不能为空")
    private String cronExpression;

    /**
     * 任务状态
     */
    private Integer status;

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

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Transient
    public void addExcuteCount() {
        this.excutedCount = getExcutedCount() + 1;
    }
}
