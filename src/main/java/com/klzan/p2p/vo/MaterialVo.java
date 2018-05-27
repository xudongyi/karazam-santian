/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.vo;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.MaterialType;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public class MaterialVo extends BaseVo {

    /** 类型 */
    private MaterialType type;

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

    public Integer sort;

    /** 材料 */
    private MultipartFile file;

    private String operator;

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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
