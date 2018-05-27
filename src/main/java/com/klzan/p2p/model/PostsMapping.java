package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 内容和分类的多对多映射关系
 * Created by Sue on 2017/5/28.
 */
@Entity
@Table(name = "karazam_posts_mapping")
public class PostsMapping extends BaseModel {
    /**
     * 内容ID
     */
    private Integer contentId;
    /**
     * 分类ID
     */
    private Integer taxonomyId;

    public PostsMapping() {
    }

    public PostsMapping(Integer contentId, Integer taxonomyId) {
        this.contentId = contentId;
        this.taxonomyId = taxonomyId;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getTaxonomyId() {
        return taxonomyId;
    }

    public void setTaxonomyId(Integer taxonomyId) {
        this.taxonomyId = taxonomyId;
    }

    public void update(Integer contentId, Integer taxonomyId) {
        this.contentId = contentId;
        this.taxonomyId = taxonomyId;
    }
}
