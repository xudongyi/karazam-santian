package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.exception.PayException;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnBankCard;
import com.klzan.p2p.model.CpcnPayAccountInfo;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.user.CpcnAccService;
import com.klzan.p2p.service.user.CpcnBankCardService;
import com.klzan.plugin.pay.common.dto.BankcardBindRequest;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.notice.Notice4233Request;
import payment.api.notice.Notice4247Request;
import payment.api.notice.NoticeRequest;

/**
 * 开户
 */
@Service
public class BusinessBankcardUnbindService extends AbstractBusinessService{

    @Autowired
    private PaymentOrderService paymentOrderService;
    @Autowired
    private CpcnAccService cpcnAccService;
    @Autowired
    private CpcnBankCardService cpcnBankCardService;

    @Override
    public Result before(PayModule module) {

        BankcardBindRequest request = (BankcardBindRequest) module.getRequest();

        createOrder(module, request.getUserId(), PaymentOrderType.BINDCARD_UNBIND);

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
            Notice4247Request txNotice = new Notice4247Request(notice.getDocument());
            System.out.println(JsonUtils.toJson(txNotice));
//            private String txCode;
//            private String institutionID;
//            private String bankID;
//            private String bankAccountNumber;
//            private String paymentAccountNumber;
//            private String bindingSystemNo;

            // 更新订单
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(sn);
            paymentOrder.setResParams(JsonUtils.toJson(txNotice));

            BankcardBindRequest request = JsonUtils.toObject(paymentOrder.getBusReqParams(), BankcardBindRequest.class);
            CpcnBankCard cpcnBankCard = cpcnBankCardService.find(paymentOrder.getUserId(), null, request.getBindingSystemNo());
            if(cpcnBankCard == null){
                throw new PayException("");
//                cpcnBankCard = new CpcnBankCard();
//                cpcnBankCard.setUserId(paymentOrder.getUserId());
//                cpcnBankCard.setOrderNo(paymentOrder.getOrderNo());
//                cpcnBankCard.setBankID(txNotice.getBankID());
//                cpcnBankCard.setBankAccountNumber(txNotice.getBankAccountNumber());
//                cpcnBankCard.setBindingSystemNo(txNotice.getBindingSystemNo());
            }
            if(!request.getBindingSystemNo().equals(txNotice.getBindingSystemNo())){
                throw new PayException("");
            }
            // 绑定状态:10=已绑定 30=待验证 40=绑定失败
            cpcnBankCard.setStatus("50");
            cpcnBankCardService.merge(cpcnBankCard);
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderService.merge(paymentOrder);
            return Result.success("已解绑");

        } catch (Exception e) {
            throw new PayException("通知处理异常");
        }
    }

}
