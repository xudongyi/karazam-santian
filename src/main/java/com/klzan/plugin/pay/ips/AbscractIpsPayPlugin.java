package com.klzan.plugin.pay.ips;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.util.HttpUtils;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.Reflections;
import com.klzan.core.util.StringUtils;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by suhao on 2017/3/14.
 */
public abstract class AbscractIpsPayPlugin<T> {
    protected static Logger logger = LoggerFactory.getLogger(AbscractIpsPayPlugin.class);
    /**
     * 数据3DES加密
     *
     * @param request
     * @return
     */
    protected String doEncrypt3DES(Map request) {
        return DES.encrypt3DES(JsonUtils.toJson(request), IpsPayConfig.DES_KEY, IpsPayConfig.DES_VCTOR);
    }

    /**
     * 数据MD5签名
     *
     * @param desData
     * @return
     */
    protected String doSign(String desData) {
        return DigestUtils.md5Hex(new StringBuffer(getBusinessType().getOperationType())
                .append(IpsPayConfig.MERCHANT_ID)
                .append(desData)
                .append(IpsPayConfig.CERT_MD5).toString());
    }

    /**
     * 验证签名
     * @param result
     * @return
     */
    protected boolean doVerifySign(String result) {
        try {
            IpsPayPluginResponse response = JsonUtils.toObject(result, IpsPayPluginResponse.class);
            checkState(response);
            return StringUtils.equals(response.getSign(), DigestUtils.md5Hex(new StringBuffer(IpsPayConfig.MERCHANT_ID)
                    .append(response.getResultCode())
                    .append(response.getResultMsg())
                    .append(response.getResponse())
                    .append(IpsPayConfig.CERT_MD5).toString()));
        } catch (Exception e) {
            logger.error(e.getMessage());
//            throw new BusinessProcessException(e.getMessage());
        }
        return false;
    }

    /**
     * 获取解密数据
     * @param result
     * @return
     */
    protected IDetailResponse doGetResponseResult(String result) {
        IpsPayPluginResponse response = JsonUtils.toObject(result, IpsPayPluginResponse.class);
        logger.info("ips decode result:{}", JsonUtils.toJson(response));
        checkState(response);
        String res = response.getResponse();
        return JsonUtils.toObject(doDecrypt3DES(res), Reflections.getClassGenricType(getClass()));
    }

    /**
     * 后台请求
     * @param map
     * @return
     */
    protected String doPost(Map<String, Object> map) {
        return HttpUtils.doPost(IpsPayConfig.REQUEST_URL, map);
    }

    /**
     * 获取业务类型
     * @return
     */
    protected abstract BusinessType getBusinessType();

    /**
     * 组装请求参数
     * @param requestMap
     * @return
     */
    protected Map<String, Object> doGenerateParams(Map requestMap) {
        String desData = this.doEncrypt3DES(requestMap);
        Map params = new LinkedMap();
        params.put("operationType", getBusinessType().getOperationType());
        params.put("merchantID", IpsPayConfig.MERCHANT_ID);
        params.put("sign", this.doSign(desData));
        params.put("request", desData);

        logger.info("{} request:{}", getBusinessType().getOperationType(), JsonUtils.toJson(requestMap));
        logger.info("{} params:{}", getBusinessType().getOperationType(), JsonUtils.toJson(params));

        return params;
    }

    /**
     * 检查响应状态，如果不成功则抛出异常
     * @param response
     */
    private void checkState(IpsPayPluginResponse response) {
        if (!StringUtils.equals("000000", response.getResultCode())) {
            String resultMsg = response.getResultMsg();
            if (StringUtils.contains(resultMsg, "|")) {
                resultMsg = StringUtils.substringAfter(resultMsg, "|");
            }
            throw new BusinessProcessException(response.getResultCode(), resultMsg);
        }
    }

    /**
     * 3DES解密
     * @param response
     * @return
     */
    private String doDecrypt3DES(String response) {
        return DES.decrypt3DES(response, IpsPayConfig.DES_KEY, IpsPayConfig.DES_VCTOR);
    }

}
