package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.PayException;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.user.CpcnAccService;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.notice.Notice4233Request;
import payment.api.notice.NoticeRequest;

/**
 * 开户
 */
@Service
public class BusinessOpenAccountService extends AbstractBusinessService{

    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private CpcnAccService cpcnAccService;

    @Override
    public Result before(PayModule module) {

        UserInfoRequest request = (UserInfoRequest) module.getRequest();

        if(!module.getExistOrder()){
            createOrder(module, request.getUserId(), PaymentOrderType.OPEN_ACCOUNT);
            CpcnPayAccountInfo info = cpcnAccService.findByUserId(request.getUserId());
            if(info == null){
                info = new CpcnPayAccountInfo();
                info.setUserId(request.getUserId());
                cpcnAccService.persist(info);
            }
        }

        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
        paymentOrderService.merge(paymentOrder);
        return result;
    }

    @Override
    public Result notice(String sn, NoticeRequest notice) {
        try {
            Notice4233Request txNotice = new Notice4233Request(notice.getDocument());

            // 更新订单
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
            paymentOrder.setResParams(JsonUtils.toJson(txNotice));
            CpcnPayAccountInfo info = cpcnAccService.findByUserId(paymentOrder.getUserId());
            if(info == null){
                throw new PayException("");
            }
            info.setAccountUsername(txNotice.getUserName());
            info.setAccountNumber(txNotice.getPaymentAccountNumber());
            info.setAccountState(txNotice.getStatus());
            cpcnAccService.merge(info);

            // 10=未注册 15=待认证 20=审核中 30=已注册，个人直接成功，企业实名认证信息通过 40=审核驳回
            if(txNotice.getStatus().equals("10")){
                paymentOrder.setStatus(PaymentOrderStatus.FAILURE);
                paymentOrderService.merge(paymentOrder);
                return Result.error("未注册");
            }else if(txNotice.getStatus().equals("15")){
                paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
                paymentOrderService.merge(paymentOrder);
                return Result.error("待认证");
            }else if(txNotice.getStatus().equals("20")){
                paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
                paymentOrderService.merge(paymentOrder);
                return Result.error("审核中");
            }else if(txNotice.getStatus().equals("30")){
                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderService.merge(paymentOrder);
                return Result.success("已注册");
            }else if(txNotice.getStatus().equals("40")){
                paymentOrder.setStatus(PaymentOrderStatus.FAILURE);
                paymentOrderService.merge(paymentOrder);
                return Result.error("审核驳回");
            }else if(txNotice.getStatus().equals("60")){
                paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
                paymentOrderService.merge(paymentOrder);
                return Result.error("正在开通电子账户");
            }else {
                return Result.error("参数错误");
            }
        } catch (Exception e) {
            throw new PayException("通知处理异常");
        }
    }

}
