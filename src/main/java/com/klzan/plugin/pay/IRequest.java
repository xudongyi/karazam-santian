package com.klzan.plugin.pay;

import java.io.Serializable;

/**
 * Created by suhao Date: 2017/3/14 Time: 16:44
 *
 * @version: 1.0
 */
public interface IRequest extends Serializable {
    /**
     * 业务类型
     * @return
     */
    BusinessType getBusinessType();

    /**
     * 是否页面跳转请求，否则为后台请求
     * @return
     */
    Boolean isPageRequest();
}
