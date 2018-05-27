/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * VO基类
 * Created by suhao Date: 2016/11/3 Time: 10:55
 *
 * @version: 1.0
 */
public abstract class BaseVo implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = -3703154275817120085L;
    /**
     * ID
     */
    protected Integer id;

    /**
     * 创建日期
     */
    protected Date createDate;

    /**
     * 修改日期
     */
    protected Date modifyDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
