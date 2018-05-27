package com.klzan.p2p.model;


import com.klzan.p2p.enums.DeviceType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;

/**
 * Created by suhao Date: 2017/1/18 Time: 10:09
 *APP信息
 * @version: 1.0
 */
@Entity
@Table(name = "karazam_mobile_app")
public class MobileApp extends BaseModel {
    /**
     * 应用类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceType appType = DeviceType.ANDROID;
    /**
     * APP版本号
     */
    @Column(nullable = false)
    private Integer versionNo;

    /**
     * 版本名称
     */
    @Column(nullable = false)
    private String versionName;

    /**
     * 更新日志
     */
    @Column(nullable = false)
    private String changeLog;

    /**
     * 包名
     */
    @Column(nullable = false)
    private String packageName;

    /**
     * 下载地址
     */
    @Column(nullable = false)
    private String appUrl;

    private Integer downCount = 0;

    public MobileApp() {
    }

    public MobileApp(Integer versionNo, String versionName, String changeLog, String packageName, String appUrl) {
        this.versionNo = versionNo;
        this.versionName = versionName;
        this.changeLog = changeLog;
        this.packageName = packageName;
        this.appUrl = appUrl;
    }

    public DeviceType getAppType() {
        return appType;
    }

    public void setAppType(DeviceType appType) {
        this.appType = appType;
    }

    public Integer getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Integer versionNo) {
        this.versionNo = versionNo;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Integer getDownCount() {
        return downCount;
    }

    public void setDownCount(Integer downCount) {
        this.downCount = downCount;
    }

    public void update(String versionName, String changeLog, String packageName, String appUrl) {
        this.versionName = versionName;
        this.changeLog = changeLog;
        this.packageName = packageName;
        this.appUrl = appUrl;
    }

    public void addDownloadCount() {
        this.downCount += 1;
    }
}
