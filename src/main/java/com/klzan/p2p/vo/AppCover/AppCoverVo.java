package com.klzan.p2p.vo.AppCover;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Column;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class AppCoverVo extends BaseVo {
    /**
     * 标题
     */

    private String title;

    /**
     * 路径
     */

    private String path;

    /**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 链接
     */
    private String url;
    /**
     * 备注
     */
    private String cont;

    /**
     *是否是欢迎页
     */
    private Boolean isWelcomePage;

    public Boolean getIsWelcomePage() {
        return isWelcomePage;
    }

    public void setIsWelcomePage(Boolean welcomePage) {
        isWelcomePage = welcomePage;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
