package com.klzan.plugin.pay;

import java.io.Serializable;

/**
 * Created by suhao on 2017/5/12.
 */
public interface IResponse extends Serializable {

    /**
     * 响应消息
     *
     * @return
     */
    String getMsg();

    /**
     *
     * @return
     */
    String getResultCode();

}
