package com.klzan.p2p.vo.posts;

import com.klzan.p2p.common.vo.BaseVo;

/**
 * 元数据表
 * 用来对其他内容字段扩充
 * Created by Sue on 2017/5/28.
 */
public class PostsMetaVo extends BaseVo {
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

    public String getMetaValue() {
        return metaValue;
    }

    public String getObjectType() {
        return objectType;
    }

    public Integer getObjectId() {
        return objectId;
    }
}
