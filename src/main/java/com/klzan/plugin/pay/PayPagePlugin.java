package com.klzan.plugin.pay;

import java.util.Map;

/**
 * 环迅支付页面跳转
 * Created by suhao Date: 2017/3/14 Time: 15:37
 *
 * @version: 1.0
 */
public interface PayPagePlugin extends PayPlugin {
    /**
     * 生成请求参数
     * @return
     */
    Map<String, Object> generateParams(IRequest request);

}