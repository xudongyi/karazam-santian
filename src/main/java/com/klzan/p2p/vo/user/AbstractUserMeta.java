package com.klzan.p2p.vo.user;

import com.klzan.p2p.common.vo.BaseVo;

import java.io.Serializable;

/**
 * Created by suhao on 2017/5/27.
 */
public abstract class AbstractUserMeta extends BaseVo implements Serializable {
    /**
     * 用户ID
     */
    protected Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
