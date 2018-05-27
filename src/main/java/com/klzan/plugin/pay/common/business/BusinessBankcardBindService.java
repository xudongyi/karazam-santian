package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.PayException;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnBankCard;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.user.CpcnAccService;
import com.klzan.p2p.service.user.CpcnBankCardService;
import com.klzan.plugin.pay.common.dto.BankcardBindRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.notice.Notice4243Request;
import payment.api.notice.NoticeRequest;

/**
 * 开户
 */
@Service
public class BusinessBankcardBindService extends AbstractBusinessService{

    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private CpcnAccService cpcnAccService;
    @Autowired
    private CpcnBankCardService cpcnBankCardService;

    @Override
    public Result before(PayModule module) {

        BankcardBindRequest request = (BankcardBindRequest) module.getRequest();

        createOrder(module, request.getUserId(), PaymentOrderType.BINDCARD_BIND);

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
            Notice4243Request txNotice = new Notice4243Request(notice.getDocument());
            System.out.println(JsonUtils.toJson(txNotice));

            // 更新订单
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
            paymentOrder.setResParams(JsonUtils.toJson(txNotice));
//            CpcnPayAccountInfo info = cpcnAccService.findByUserId(paymentOrder.getUserId());
//            if(info == null){
//                throw new PayException("");
//            }

            BankcardBindRequest request = JsonUtils.toObject(paymentOrder.getBusReqParams(), BankcardBindRequest.class);
            String bindingSystemNo = request.getBindingSystemNo();
            CpcnBankCard cpcnBankCard = null;
            if (StringUtils.isBlank(bindingSystemNo)) {
                cpcnBankCard = cpcnBankCardService.find(paymentOrder.getUserId(), paymentOrder.getOrderNo(), null);
            } else {
                cpcnBankCard = cpcnBankCardService.find(paymentOrder.getUserId(), null, bindingSystemNo);
            }
            if(cpcnBankCard == null){
//                info.setBindCardCount(info.getBindCardCount()+1);
//                cpcnAccService.merge(info);
                cpcnBankCard = new CpcnBankCard();
                cpcnBankCard.setUserId(paymentOrder.getUserId());
                cpcnBankCard.setOrderNo(paymentOrder.getOrderNo());
                cpcnBankCard.setBankID(txNotice.getBankID());
                cpcnBankCard.setBankAccountNumber(txNotice.getBankAccountNumber());
                cpcnBankCard.setBindingSystemNo(txNotice.getBindingSystemNo());
            }
            cpcnBankCard.setStatus(txNotice.getStatus());
            cpcnBankCardService.merge(cpcnBankCard);

            // 绑定状态:10=已绑定 30=待验证 40=绑定失败
            if(txNotice.getStatus().equals("10")){
                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrderService.merge(paymentOrder);
                return Result.success("已绑定");
            }else if(txNotice.getStatus().equals("30")){
                paymentOrder.setStatus(PaymentOrderStatus.PROCESSING);
                paymentOrderService.merge(paymentOrder);
                return Result.error("待验证");
            }else if(txNotice.getStatus().equals("40")){
                paymentOrder.setStatus(PaymentOrderStatus.FAILURE);
                paymentOrderService.merge(paymentOrder);
                return Result.error("绑定失败");
            }else {
                return Result.error("参数错误");
            }

        } catch (Exception e) {
            throw new PayException("通知处理异常");
        }
    }

}
