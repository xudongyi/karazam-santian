package com.klzan.p2p.vo.user;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.UserLogType;

import javax.persistence.Column;
import javax.persistence.Lob;

/**
 * Created by suhao on 2017/5/26.
 */
public class UserMetaVo extends BaseVo {
    /**
     * 关联用户
     */
    private Integer userId;

    /**
     * 信息类型
     */
    private String metaType;

    /**
     * 信息类型中文描述
     */
    private String metaTypeDes;

    /**
     * 信息-键
     */
    private String metaKey;

    /**
     * 信息-值
     */
    private String metaVal;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMetaType() {
        return metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public String getMetaTypeDes() {
        return metaTypeDes;
    }

    public void setMetaTypeDes(String metaTypeDes) {
        this.metaTypeDes = metaTypeDes;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getMetaVal() {
        return metaVal;
    }

    public void setMetaVal(String metaVal) {
        this.metaVal = metaVal;
    }
}
