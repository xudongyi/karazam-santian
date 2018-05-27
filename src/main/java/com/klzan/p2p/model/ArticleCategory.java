/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseSortModel;
import com.klzan.p2p.model.elem.SEOElem;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

/**
 * Entity - 文章分类
 *
 * @author: chenxinglin  Date: 2016/10/19 Time: 17:15
 */
@Entity
@Table(name = "karazam_article_category")
public class ArticleCategory extends BaseSortModel {

    /**
     * 标题
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * 别名
     */
    @Pattern(regexp = "^\\w+$")
    @Column(nullable = false, length = 100)
    private String alias;

    /**
     * 模板
     */
    private String template;

    /**
     *是否内置
     */
    private Boolean builtin;

    /**
     * seo
     */
    @Embedded
    private SEOElem seo;

    /**
     * 路径
     */
    private String treePath;

    /**
     * 级别
     */
    private Integer grade;

    /**
     * 父级文章分类ID
     */
    private Integer parent; //ArticleCategory

    public ArticleCategory() {
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getTemplate() {
        return template;
    }

    public Boolean getBuiltin() {
        return builtin;
    }

    public SEOElem getSeo() {
        return seo;
    }

    public String getTreePath() {
        return treePath;
    }

    public Integer getGrade() {
        return grade;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public void setBuiltin(Boolean builtin) {
        this.builtin = builtin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public void setSeo(SEOElem seo) {
        this.seo = seo;
    }
}