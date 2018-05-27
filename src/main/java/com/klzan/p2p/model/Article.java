
/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseSortModel;
import com.klzan.p2p.model.elem.SEOElem;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * Entity - 文章
 *
 * @author: chenxinglin  Date: 2016/10/19 Time: 17:15
 */
@Entity
@Table(name = "karazam_article")
public class Article extends BaseSortModel {

    /**
     * 别名
     */
    @Pattern(regexp = "^\\w+$")
    @Column(nullable = false, length = 100)
    private String alias;

    /**
     * 标题
     */
    @Column(nullable = false, length = 100)
    private String title;

    /**
     * LOGO
     */
    @Column(length = 100)
    private String logo;

    /**
     * 封面
     */
    @Column(length = 100)
    private String cover;

    /**
     * 作者
     */
    @Column(length = 20)
    private String author;

    /**
     * 内容
     */
    @Lob
    private String cont;

    /**
     * 是否发布
     */
    @Column(nullable = false)
    private Boolean published;

    /**
     * 是否置顶
     */
    @Column(nullable = false)
    private Boolean top;

    /**
     * 点击数
     */
    private Long hits;

    /**
     * SEO
     */
    @Embedded
    private SEOElem seo;
    /**
     * 分类
     */
    @Column(nullable = false)
    private Integer category; //ArticleCategory
    /**
     * 页码
     */
    private Integer pageNumber;
    /**
     * 摘要
     */
    private  String remark;
    /**
     * 点击次数
     */
    private  long clickCount=0;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getClickCount() {
        return clickCount;
    }

    public void setClickCount(long clickCount) {
        this.clickCount = clickCount;
    }

    public Article() {
    }

    public Article(String alias, String title, String logo, String cover, String author, String cont, Boolean published, Boolean top, Long hits, SEOElem seo, Integer category, Integer pageNumber) {
        this.alias = alias;
        this.title = title;
        this.logo = logo;
        this.cover = cover;
        this.author = author;
        this.cont = cont;
        this.published = published;
        this.top = top;
        this.hits = hits;
        this.seo = seo;
        this.category = category;
        this.pageNumber = pageNumber;
    }

    public String getAlias() {
        return alias;
    }

    public String getTitle() {
        return title;
    }

    public String getLogo() {
        return logo;
    }

    public String getCover() {
        return cover;
    }

    public String getAuthor() {
        return author;
    }

    public String getCont() {
        return cont;
    }

    public Boolean getPublished() {
        return published;
    }

    public Boolean getTop() {
        return top;
    }

    public Long getHits() {
        return hits;
    }

    public SEOElem getSeo() {
        return seo;
    }

    public void setSeo(SEOElem seo) {
        this.seo = seo;
    }

    public Integer getCategory() {
        return category;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }
}