/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model;

import com.klzan.p2p.enums.LinkType;
import com.klzan.p2p.enums.MaterialType;
import com.klzan.p2p.model.base.BaseSortModel;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Entity - 材料
 *
 * @author: chenxinglin  Date: 2016/11/18 Time: 15:35
 */
@Entity
@Table(name = "karazam_material")
public class Material extends BaseSortModel {

    /** 类型 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaterialType type;

    /** 关联类型 */
    @Enumerated(EnumType.STRING)
    private LinkType linkType;

    /** 标题 */
    @Length(max = 200)
    private String title;

    /** 原图片 */
    @Length(max = 200)
    private String source;

    /** 大图片 */
    @Length(max = 200)
    private String large;

    /** 中图片 */
    @Length(max = 200)
    private String medium;

    /** 缩略图 */
    @Length(max = 200)
    private String thumbnail;

    /** 借款 */
    private Integer borrowing;

    /**
     * 操作人
     */
    @Length(max = 200)
    private String operator;

    public Material(){}

    public Material(MaterialType type, String title, String source, Integer sort, Integer borrowing, String operator) {
        this.type = type;
        this.title = title;
        this.source = source;
        this.borrowing = borrowing;
        this.sort = sort;
        this.operator = operator;
    }

    public Material(MaterialType type, String title, String source, String large, String medium, String thumbnail, Integer borrowing) {
        this.type = type;
        this.title = title;
        this.source = source;
        this.large = large;
        this.medium = medium;
        this.thumbnail = thumbnail;
        this.borrowing = borrowing;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkType linkType) {
        this.linkType = linkType;
    }

    public MaterialType getType() {
        return type;
    }

    public void setType(MaterialType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Integer borrowing) {
        this.borrowing = borrowing;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}