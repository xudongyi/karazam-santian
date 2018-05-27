package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 用户元信息
 * Created by suhao Date: 2017/5/26 Time: 11:40
 *
 * @version: 1.0
 */
@Entity
@Table(name = "karazam_user_meta")
public class UserMeta extends BaseModel {
    /**
     * 关联用户
     */
    @Column(nullable = false)
    private Integer userId;

    /**
     * 信息类型
     */
    @Column(nullable = false, length = 200)
    private String metaType;

    /**
     * 信息-键
     */
    @Column(nullable = false, length = 200)
    private String metaKey;

    /**
     * 信息-值
     */
    @Column(nullable = false)
    @Lob
    private String metaVal;

    public UserMeta(Integer userId, String metaType, String metaKey, String metaVal) {
        this.userId = userId;
        this.metaType = metaType;
        this.metaKey = metaKey;
        this.metaVal = metaVal;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getMetaType() {
        return metaType;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public String getMetaVal() {
        return metaVal;
    }
}
