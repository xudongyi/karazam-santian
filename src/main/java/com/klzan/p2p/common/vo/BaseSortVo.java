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
public class BaseSortVo extends BaseVo{

    public Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
