/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.common;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.CpcnRepaymentStatus;
import com.klzan.p2p.enums.CpcnSettlementStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnSettlement;
import com.klzan.p2p.service.repayment.CpcnSettlementService;
import com.klzan.p2p.service.repayment.RepaymentService;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.common.dto.SnRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * 还款
 * @author: chenxinglin
 */
@Component
public class RepaymentCom {

    @Inject
    private RepaymentService repaymentService;
    @Inject
    private CpcnSettlementService cpcnSettlementService;


    public synchronized Result repayCarry(CpcnSettlement cpcnSettlement) {

        if(cpcnSettlement == null || cpcnSettlement.getId() == null){
            throw new RuntimeException();
        }

        cpcnSettlementService.flush();
        cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());

        Response response = null;

        if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT) || cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT_EARLY)){

            if(cpcnSettlement.getrStatus().equals(CpcnRepaymentStatus.unpaid)){
                if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT)){
                    response = repaymentService.repay(cpcnSettlement);
                }else {
                    response = repaymentService.aheadRepay(cpcnSettlement);
                }
                if(response.isError()){
                    return Result.error("还款失败："+response.getMsg());
                }
            }

            cpcnSettlementService.flush();
            cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());
            if(cpcnSettlement.getrStatus().equals(CpcnRepaymentStatus.paying)){
                String sn = cpcnSettlement.getrOrderNo();
                PayModule payModule = PayPortal.repayment_query.getModuleInstance();
                SnRequest snRequest = new SnRequest();
                snRequest.setSn(sn);
                payModule.setRequest(snRequest);
                response = payModule.invoking().getResponse();
                if(response.isError()){
                    return Result.error("还款处理中，还款查询结果："+response.getMsg());
                }
                if(response.isProccessing()){
                    return Result.proccessing("还款处理中，还款查询结果："+response.getMsg());
                }
            }

            cpcnSettlementService.flush();
            cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());
            if(!cpcnSettlement.getrStatus().equals(CpcnRepaymentStatus.success)){
                return Result.error();
            }

            if(cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.unsettled) || cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.failure)){
                if(cpcnSettlement.getType().equals(PaymentOrderType.REPAYMENT)){
                    response = repaymentService.repaySettlement(cpcnSettlement);
                }else {
                    response = repaymentService.aheadRepaySettlement(cpcnSettlement);
                }
                if(response.isError()){
//                    return Result.error("还款结算失败："+response.getMsg());
                    System.out.println(JsonUtils.toJson(Result.error("还款结算失败："+response.getMsg())));
                    return Result.success("还款成功");
                }
                if(response.isSuccess()){
//                    return Result.error("还款结算异常："+response.getMsg());
                    System.out.println(JsonUtils.toJson(Result.error("还款结算异常："+response.getMsg())));
                    return Result.success("还款成功");
                }
            }

            cpcnSettlementService.flush();
            cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());
            if(cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.settling)){
                String sn = cpcnSettlement.getsOrderNo();
                PayModule payModule = PayPortal.project_settlement_batch_query.getModuleInstance();
                SnRequest snRequest = new SnRequest();
                snRequest.setSn(sn);
                payModule.setRequest(snRequest);
                response = payModule.invoking().getResponse();
                if(response.isError()){
//                    return Result.error("还款结算处理中，结算查询结果："+response.getMsg());
                    System.out.println(JsonUtils.toJson(Result.error("还款结算处理中，结算查询结果："+response.getMsg())));
                    return Result.success("还款成功");
                }
                if(response.isProccessing()){
//                    return Result.proccessing("还款结算处理中，结算查询结果："+response.getMsg());
                    System.out.println(JsonUtils.toJson(Result.proccessing("还款结算处理中，结算查询结果："+response.getMsg())));
                    return Result.success("还款成功");
                }
            }

            cpcnSettlementService.flush();
            cpcnSettlement = cpcnSettlementService.get(cpcnSettlement.getId());
            if(cpcnSettlement.getrStatus().equals(CpcnRepaymentStatus.success) && cpcnSettlement.getsStatus().equals(CpcnSettlementStatus.success)){
                return Result.success("还款成功");
            }
        }

        return Result.error();


    }

}
