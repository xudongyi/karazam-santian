package com.klzan.plugin.pay.cpcn.service;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.Reflections;
import com.klzan.plugin.pay.common.PageRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import payment.api.system.PaymentEnvironment;
import payment.api.system.PaymentUserEnvironment;
import payment.api.system.TxMessenger;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.TxBaseResponse;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Plugin - 中金托管
 */
public class CpcnPlugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(CpcnPlugin.class);

    /**
     * 请求参数处理
     *
     * @param isMobile
     * @param txRequest
     * @return
     */
    public static Result process(Boolean isMobile, TxBaseRequest txRequest) throws Exception {
        // 执行报文处理
        txRequest.process();
        // 返回接口请求参数
        Map<String, String> req = new HashMap();
        req.put("message", txRequest.getRequestMessage());
        req.put("signature", txRequest.getRequestSignature());
        String reqUrl = PaymentUserEnvironment.paymentuserpayURL;
        if (isMobile) {
            reqUrl = PaymentUserEnvironment.mobilepaymentuserpayURL;
        }
        String txCode = (String) Reflections.getFieldValue(txRequest, "txCode");
        LOGGER.info("\ncpcn [{}] req url:[{}]\ncpcn [{}] req params:{}", txCode, reqUrl, txCode, JsonUtils.toJson(req));
        return Result.success("", new PageRequest(reqUrl, req));
    }

    /**
     * 请求接口数据
     *
     * @param txRequest
     * @return
     */
    public static Result process(TxBaseRequest txRequest) throws Exception {
        // 执行报文处理
        txRequest.process();
        // 请求接口 获取结果
        String message = txRequest.getRequestMessage();
        String signature = txRequest.getRequestSignature();
        Map<String, String> req = new HashMap();
        req.put("message", message);
        req.put("signature", signature);
        String txCode = (String) Reflections.getFieldValue(txRequest, "txCode");
        LOGGER.info("\ncpcn [{}] query req params:{}", txCode, JsonUtils.toJson(req));

        TxMessenger txMessenger = new TxMessenger();
        String[] respMsg = null;
        try {
            String requestUrl = PaymentUserEnvironment.paymentusertxURL;
            if (txCode.equals("4540") || txCode.equals("4542")) {
                requestUrl = PaymentEnvironment.txURL;
            }
            respMsg = txMessenger.send(message, signature, requestUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getTxResponse(respMsg, txRequest);
    }

    /**
     * 获取接口响应
     *
     * @param respMsg 返回参数（加密的）
     * @param txRequest
     * @return
     */
    private static Result getTxResponse(String[] respMsg, TxBaseRequest txRequest) throws Exception {
        Class txRequestClazz = txRequest.getClass();
        String clazzSimpleName = txRequestClazz.getSimpleName();
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(clazzSimpleName);
        String txcode = m.replaceAll("").trim();
        Class<?> txResponseClazz = Class.forName(txRequest.getClass().getPackage().getName().concat(".Tx").concat(txcode).concat("Response"));
        Constructor constructor = txResponseClazz.getConstructor(String.class, String.class);
        TxBaseResponse response = null;
        try {
            LOGGER.info("\ncpcn [{}] response:{}", txcode, JsonUtils.toJson(respMsg));
            response = (TxBaseResponse) constructor.newInstance(respMsg[0], respMsg[1]);
        } catch (InstantiationException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            return Result.error("接口请求异常");
        } catch (IllegalAccessException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            return Result.error("接口请求异常");
        } catch (IllegalArgumentException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            return Result.error("接口请求异常");
        } catch (InvocationTargetException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            return Result.error("接口请求异常");
        }
        if (response == null) {
            return Result.error("无返回");
        }
        LOGGER.info(JsonUtils.toJson(response));
        if (response != null && "2000".equals(response.getCode())) {
            return Result.success(response.getMessage(), response);
        } else {
            return Result.error(response.getMessage(), response);
        }
    }

}