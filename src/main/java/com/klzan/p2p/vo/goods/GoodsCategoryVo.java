package com.klzan.p2p.vo.goods;

import com.klzan.p2p.common.vo.BaseSortVo;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.model.Goods;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品类别
 */
public class GoodsCategoryVo extends BaseSortVo {

    /** 名称 */
    private String name;

    /** 页面标题 */
    private String seoTitle;

    /** 页面关键词 */
    private String seoKeywords;

    /** 页面描述 */
    private String seoDescription;

    /** 上级 */
    private Integer parent;

    /** 层级 */
    private Integer grade;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
