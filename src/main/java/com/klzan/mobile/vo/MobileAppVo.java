package com.klzan.mobile.vo;


import com.klzan.p2p.common.vo.BaseVo;

/**
 * Created by suhao Date: 2017/1/18 Time: 11:28
 *
 * @version: 1.0
 */
public class MobileAppVo extends BaseVo {
    /**
     * 应用类型
     */
    private String appType;
    /**
     * 版本号
     */
    private Integer versionNo;

    /**
     * 版本名称
     */
    private String versionName;

    /**
     * 更新日志
     */
    private String changeLog;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 下载地址
     */
    private String appUrl;

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
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
}
