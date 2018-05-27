/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.model.elem;

import org.apache.commons.lang3.builder.CompareToBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Elem - 图片
 */
@Embeddable
@MappedSuperclass
public class ImageElem implements Serializable, Comparable<ImageElem> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2813009143487356400L;

    /**
     * 标题
     */
    protected String title;

    /**
     * 原图片
     */
    protected String source;

    /**
     * 大图片
     */
    protected String large;

    /**
     * 中图片
     */
    protected String medium;

    /**
     * 缩略图
     */
    protected String thumbnail;

    /**
     * 排序
     */
    @Column(name = "orders")
    protected Integer order;

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getLarge() {
        return large;
    }

    public String getMedium() {
        return medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Integer getOrder() {
        return order;
    }

    /**
     * 实现compareTo方法
     *
     * @param image 图片
     * @return 比较结果
     */
    @Override
    public int compareTo(ImageElem image) {
        return new CompareToBuilder().append(getOrder(), image.getOrder()).toComparison();
    }

}