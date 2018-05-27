package com.klzan.p2p.model;

import com.klzan.p2p.enums.AdType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/22 0022.
 */
@Entity
@Table(name = "karazam_app_cover")
public class AppCover extends BaseModel {
    /**
     * 标题
     */
    @Column(nullable = false, length = 50)
    private String title;

    /**
     * 路径
     */
    @Column(length = 300)
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

    @Column(length = 100)
    private String url;

    /**
     * 备注
     */
    private String cont;
    /**
     *是否是欢迎页
     */
    private boolean isWelcomePage;

    public boolean isWelcomePage() {
        return isWelcomePage;
    }

    public void setWelcomePage(boolean welcomePage) {
        isWelcomePage = welcomePage;
    }

    public  AppCover(){

    }
    public AppCover(String title, String path, Date startDate, Date endDate, String url,String cont,boolean isWelcomePage) {
        this.title = title;
        this.path = path;
        this.startDate = startDate;
        this.endDate = endDate;
        this.url = url;
        this.cont=cont;
        this.isWelcomePage=isWelcomePage;
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
