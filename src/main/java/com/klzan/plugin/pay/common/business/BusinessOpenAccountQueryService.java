package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.PayException;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.user.CpcnAccService;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.tx.paymentaccount.Tx4232Response;

/**
 * 开户
 */
@Service
public class BusinessOpenAccountQueryService extends AbstractBusinessService {

    @Autowired
    private CpcnAccService cpcnAccService;

    @Override
    public Result before(PayModule module) {
        PaymentOrder paymentOrder = createOrder(module, null, PaymentOrderType.OPEN_ACCOUNT_QUERY);
        UserInfoRequest request = (UserInfoRequest) module.getRequest();
        PaymentOrder order = paymentOrderService.find(request.getUserId(), PaymentOrderStatus.PROCESSING, PaymentOrderType.OPEN_ACCOUNT);
        paymentOrder.setParentOrderNo(order.getOrderNo());
        paymentOrderService.merge(paymentOrder);
        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        try {
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
            paymentOrder.setResParams(JsonUtils.toJson(module.getResponse().getTxResponse()));
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderService.merge(paymentOrder);

            // 更新订单
            PaymentOrder order = paymentOrderService.findByOrderNo(paymentOrder.getParentOrderNo());
            CpcnPayAccountInfo info = cpcnAccService.findByUserId(order.getUserId());
            if(info == null){
                throw new PayException("用户支付账户不存在");
            }
            Tx4232Response response = (Tx4232Response)module.response.getTxResponse();
            if (!result.isSuccess()) {
                LOGGER.error("开户查询失败：{}", result.getMessage());
                return result;
            }
            info.setAccountUsername(response.getUserName());
            info.setAccountNumber(response.getPaymentAccountNumber());
            info.setAccountState(response.getStatus());
            cpcnAccService.merge(info);

            // 10=未注册 15=待认证 20=审核中 30=已注册，个人直接成功，企业实名认证信息通过 40=审核驳回
            if(response.getStatus().equals("10")){
                order.setStatus(PaymentOrderStatus.FAILURE);
                paymentOrderService.merge(order);
                return Result.error("未注册");
            }else if(response.getStatus().equals("15")){
                order.setStatus(PaymentOrderStatus.PROCESSING);
                paymentOrderService.merge(order);
                return Result.error("待认证");
            }else if(response.getStatus().equals("20")){
                order.setStatus(PaymentOrderStatus.PROCESSING);
                paymentOrderService.merge(order);
                return Result.error("审核中");
            }else if(response.getStatus().equals("30")){
                order.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderService.merge(order);
                return Result.success("已注册");
            }else if(response.getStatus().equals("40")){
                order.setStatus(PaymentOrderStatus.FAILURE);
                paymentOrderService.merge(order);
                return Result.error("审核驳回");
            }else if(response.getStatus().equals("60")){
                order.setStatus(PaymentOrderStatus.PROCESSING);
                paymentOrderService.merge(order);
                return Result.error("正在开通电子账户");
            }else {
                return Result.error("参数错误");
            }

        } catch (PayException e) {
            throw new PayException("通知处理异常");
        }
    }

}
