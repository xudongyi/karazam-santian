package com.klzan.p2p.vo.content;

import com.klzan.p2p.common.vo.BaseVo;

/**
 * Created by zhu on 2016/12/18.
 */
public class ArticleVo extends BaseVo {
    private String remark;
    private String alias;
    private String cover;
    private String logo;
    private String title;
    private String author;
    private String cont;
    private Boolean published;
    private Boolean top;
    private Long hits;
    private String description;
    private String keywords;
    private String pageTitle;
    private Integer category;
    private String categoryName;
    private Integer pageNumber;
    private Integer sort;
    private Integer firstCategory;
    private Integer secondCategory;
    private Integer thirdCategory;
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

    public Integer getThirdCategory() {
        return thirdCategory;
    }

    public void setThirdCategory(Integer thirdCategory) {
        this.thirdCategory = thirdCategory;
    }

    public Integer getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(Integer firstCategory) {
        this.firstCategory = firstCategory;
    }

    public Integer getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(Integer secondCategory) {
        this.secondCategory = secondCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Long getHits() {
        return hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }
}
