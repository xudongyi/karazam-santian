/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model;

import com.klzan.p2p.enums.AdType;
import com.klzan.p2p.model.base.BaseSortModel;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity - 广告
 *
 * @author: chenxinglin  Date: 2016/10/19 Time: 17:15
 */
@Entity
@Table(name = "karazam_ad")
public class Ad extends BaseSortModel {

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AdType type = AdType.IMAGE;

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
     * 内容
     */
    private String cont;

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
     * 广告位
     */
    @Column(nullable = false)
    private Integer position;

    public Ad() {
    }

    public Ad(AdType type, String title, String path, String cont, Date startDate, Date endDate, String url, Integer position) {
        this.type = type;
        this.title = title;
        this.path = path;
        this.cont = cont;
        this.startDate = startDate;
        this.endDate = endDate;
        this.url = url;
        this.position = position;
    }

    public AdType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public String getCont() {
        return cont;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getUrl() {
        return url;
    }

    public Integer getPosition() {
        return position;
    }

    public void setType(AdType type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}