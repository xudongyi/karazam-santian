package com.klzan.plugin.pay;

import com.klzan.core.exception.BusinessProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by suhao Date: 2017/3/14 Time: 18:01
 *
 * @version: 1.0
 */
@Service
public class PayGeneratorContext {
    private Logger logger = LoggerFactory.getLogger(PayGeneratorContext.class);

    @Resource
    private List<PayPagePlugin> payPagePlugins;

    @Resource
    private List<PayBgPlugin> payBgPlugins;

    @Resource
    private List<PayPlugin> payPlugins;

    public Object generateRequest(IRequest request){
        if (request.isPageRequest()) {
            return generatePageRequest(request);
        }
        return generateBgRequest(request);
    }

    public Boolean verifySign(String result, BusinessType type) {
        for (PayPlugin payPlugin : payPlugins) {
            if(payPlugin.verifySupport(type)){
                return payPlugin.verifySign(result);
            }
        }
        logger.error("没有可用平台", type);
        throw new BusinessProcessException("没有可用平台");
    }

    public IDetailResponse getResponseResult(String result, BusinessType type) {
        for (PayPlugin payPlugin : payPlugins) {
            if(payPlugin.verifySupport(type)){
                return payPlugin.getResponseResult(result);
            }
        }
        logger.error("没有可用平台", type);
        throw new BusinessProcessException("没有可用平台");
    }

    private Object generatePageRequest(IRequest request){
        for (PayPagePlugin payPlugin : payPagePlugins) {
            if(payPlugin.isSupport(request)){
                return payPlugin.generateParams(request);
            }
        }
        logger.error("没有可用平台", request.getBusinessType());
        throw new BusinessProcessException("没有可用平台");
    }

    private Object generateBgRequest(IRequest request){
        for (PayBgPlugin payPlugin : payBgPlugins) {
            if(payPlugin.isSupport(request)){
                return payPlugin.generateParamsAndPostRequest(request);
            }
        }
        logger.error("没有可用平台", request.getBusinessType());
        throw new BusinessProcessException("没有可用平台");
    }

}
