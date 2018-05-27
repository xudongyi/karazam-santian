package com.klzan.plugin.pay.common.module;

import com.klzan.core.Result;
import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.util.*;
import com.klzan.plugin.pay.common.PageRequest;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.PayUtils;
import com.klzan.plugin.pay.common.business.BusinessService;
import com.klzan.plugin.pay.common.dto.Request;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.cpcn.service.CpcnPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import payment.api.notice.NoticeRequest;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.TxBaseResponse;

/**
 * 支付组件
 *
 * @author: chenxinglin
 */
public abstract class PayModule {

    protected static final Logger LOGGER = LoggerFactory.getLogger(PayModule.class);

    //订单号
    protected String sn;

    public Boolean existOrder = Boolean.FALSE;

    //请求参数（业务）
    public Request request;

    //响应参数（业务）
    public Response response = new Response();

    //请求参数（接口跳转）
    private PageRequest pageRequest;

    protected TxBaseRequest txRequest;

    protected PayUtils payUtils = SpringUtils.getBean(PayUtils.class);

    public TxBaseRequest getTxRequest() {
        return txRequest;
    }

    public PageRequest getPageRequest() {
        return pageRequest;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    abstract protected TxBaseRequest getReqParam();

    abstract public PayPortal getPayPortal();

    /**
     * 接口调用
     * @return
     */
    public PayModule invoking() {
        Result result;
        BusinessService businessService = getPayPortal().getService();

        //业务参数，接口参数，接口响应
        TxBaseRequest txRequest = getReqParam();
        this.txRequest = txRequest;
        String txCode = (String) Reflections.getFieldValue(txRequest, "txCode");
        LOGGER.info("sn[{}] txcode[{}] request params:" + JsonUtils.toJson(txRequest), sn, txCode);

        //接口调用前业务
        if(businessService != null){
            result = businessService.before(this);
            this.dealBusResponse(result);
            if(!result.isSuccess()){
                return this;
            }
        }else {
            this.dealBusResponse(Result.success());
        }

        //接口调用
        try {
            if(getPayPortal().getPage()){
                result = CpcnPlugin.process(request.isMobile(), txRequest);
            }else {
                result = CpcnPlugin.process(txRequest);
            }
            this.dealResponse(result);
        } catch (Exception e) {
            throw new BusinessProcessException("异常");
        }

        //接口调用后业务
        if(businessService != null){
            result = businessService.after(this, result);
            this.dealBusResponse(result);
        }else {
            this.dealBusResponse(Result.success());
        }

        return this;
    }

    /**
     * 通知
     * @param sn 外部sn
     * @param notice 接口通知
     * @return
     */
    public Result notice(String sn, NoticeRequest notice) {
        LOGGER.info("sn[{}] txcode[{}] notice:" + JsonUtils.toJson(notice), sn, notice.getTxCode());
        return getPayPortal().getService().notice(sn, notice);
    }

    /**
     * 响应参数处理
     * @param result
     */
    public void dealResponse(Result result) {
//        Response response = new Response(result.isSuccess(), result.getMessage());
        response.setSuccess(result.isSuccess());
        response.setMsg(result.getMessage());
        if (result.isSuccess() && getPayPortal().getPage()){
            this.pageRequest = (PageRequest)result.getData();
        }
        if (!getPayPortal().getPage() && result.getData() instanceof TxBaseResponse){
            response.setTxResponse((TxBaseResponse)result.getData());
        }
//        this.response = response;
    }

    public void dealBusResponse(Result result) {
        this.response.setBuiSuccess(result.getCode());
        this.response.setMsg(result.getMessage());
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Boolean getExistOrder() {
        return existOrder;
    }

    public void setExistOrder(Boolean existOrder) {
        this.existOrder = existOrder;
    }

    /**
     * 订单号
     * @return
     */
    public String getSn() {
        if(StringUtils.isBlank(sn)){
            this.sn = SnUtils.getOrderNo();
        }
        return sn;
    }
}