package com.klzan.plugin.pay.common.business;

import com.klzan.core.Result;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.model.CpcnBankCard;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.user.CpcnBankCardService;
import com.klzan.plugin.pay.common.dto.Request;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.api.tx.paymentaccount.Tx4244Response;
import payment.api.vo.PaymentBankAccount;

import java.util.*;

/**
 * 绑卡查询
 * Created by suhao Date: 2017/11/22 Time: 14:50
 *
 * @version: 1.0
 */
@Service
public class BusinessBankcardBindQueryService extends AbstractBusinessService {

    @Autowired
    private CpcnBankCardService cpcnBankCardService;

    @Override
    public Result before(PayModule module) {
        createOrder(module, null, PaymentOrderType.BINDCARD_QUERY);
        return Result.success();
    }

    @Override
    public Result after(PayModule module, Result result) {
        PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(module.getSn());
        paymentOrder.setResParams(JsonUtils.toJson(module.getResponse().getTxResponse()));
        paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
        paymentOrderService.merge(paymentOrder);
        UserInfoRequest request = (UserInfoRequest) module.getRequest();
        Integer userId = request.getUserId();
        Tx4244Response txResponse = (Tx4244Response) module.getResponse().getTxResponse();
        try {
            Set<String> userBcs = new HashSet<>();
            Map<String, CpcnBankCard> userBankCardLists = new HashMap<>();
            Set<String> cpcnBcs = new HashSet<>();
            Map<String, PaymentBankAccount> cpcnBankCardLists = new HashMap<>();
            List<CpcnBankCard> userBankCards = cpcnBankCardService.findByUserId(userId);
            for (CpcnBankCard userBankCard : userBankCards) {
                userBcs.add(userBankCard.getBindingSystemNo());
                userBankCardLists.put(userBankCard.getBindingSystemNo(), userBankCard);
            }
            for (PaymentBankAccount bankAccount : txResponse.getPaymentBankAccountList()) {
                cpcnBcs.add(bankAccount.getBindingSystemNo());
                cpcnBankCardLists.put(bankAccount.getBindingSystemNo(), bankAccount);
            }
            for (String userBc : userBcs) {
                CpcnBankCard bankCard = userBankCardLists.get(userBc);
                if (!cpcnBcs.contains(userBc)) {
                    bankCard.setStatus("50");
                }
                cpcnBankCardService.merge(bankCard);
            }

            for (String cpcnBc : cpcnBcs) {
                PaymentBankAccount bankAccount = cpcnBankCardLists.get(cpcnBc);
                if (!userBcs.contains(cpcnBc)) {
                    CpcnBankCard bankCard = new CpcnBankCard();
                    bankCard.setUserId(userId);
                    bankCard.setOrderNo(paymentOrder.getOrderNo());
                    bankCard.setBankID(bankAccount.getBankID());
                    bankCard.setBankAccountNumber(bankAccount.getBankAccountNumber());
                    bankCard.setBindingSystemNo(bankAccount.getBindingSystemNo());
                    bankCard.setStatus(bankAccount.getStatus() + "");
                    cpcnBankCardService.persist(bankCard);
                } else {
                    CpcnBankCard bankCard = userBankCardLists.get(cpcnBc);
                    bankCard.setBankID(bankAccount.getBankID());
                    bankCard.setBankAccountNumber(bankAccount.getBankAccountNumber());
                    bankCard.setBindingSystemNo(bankAccount.getBindingSystemNo());
                    bankCard.setStatus(bankAccount.getStatus() + "");
                    cpcnBankCardService.merge(bankCard);
                }
            }

        } catch (Exception e) {
            LOGGER.error("用户[{}]绑卡查询异常[{}]", userId, JsonUtils.toJson(txResponse));
        }

        return result;
    }

}
