package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 元数据表
 * 用来对其他内容字段扩充
 * Created by Sue on 2017/5/28.
 */
@Entity
@Table(name = "karazam_posts_meta")
public class PostsMeta extends BaseModel {
    /**
     * 元数据key
     */
    private String metaKey;
    /**
     * 元数据value
     */
    private String metaValue;
    /**
     * 元数据的对象类型
     */
    private String objectType;
    /**
     * 元数据的对象ID
     */
    private Integer objectId;

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }
}
