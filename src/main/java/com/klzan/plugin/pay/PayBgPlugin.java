package com.klzan.plugin.pay;

/**
 * 环迅支付后台请求
 * Created by suhao Date: 2017/3/14 Time: 15:37
 *
 * @version: 1.0
 */
public interface PayBgPlugin extends PayPlugin {
    /**
     * 生成请求参数并发送请求
     * @return
     */
    String generateParamsAndPostRequest(IRequest request);

}
