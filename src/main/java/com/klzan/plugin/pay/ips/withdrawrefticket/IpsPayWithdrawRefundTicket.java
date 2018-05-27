package com.klzan.plugin.pay.ips.withdrawrefticket;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IRequest;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.PayPagePlugin;
import com.klzan.plugin.pay.ips.AbscractIpsPayPlugin;
import com.klzan.plugin.pay.ips.withdrawrefticket.vo.IpsPayWithdrawRefundTicketResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 提现退票通知
 * Created by suhao Date: 2017/3/16 Time: 14:58
 *
 * @version: 1.0
 */
@Service
public class IpsPayWithdrawRefundTicket extends AbscractIpsPayPlugin<IpsPayWithdrawRefundTicketResponse> implements PayPagePlugin {
    @Override
    public Boolean isSupport(IRequest request) {
        return request.isPageRequest() && request.getBusinessType() == getBusinessType();
    }

    @Override
    public Boolean verifySign(String result) {
        return this.doVerifySign(result);
    }

    @Override
    public Boolean verifySupport(BusinessType type) {
        return type == getBusinessType();
    }

    @Override
    public IDetailResponse getResponseResult(String result) {
        return doGetResponseResult(result);
    }

    @Override
    public Map<String, Object> generateParams(IRequest request) {
        throw new BusinessProcessException(request.getBusinessType() + " is not support.");
    }

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.WITHDRAW_REFUND_TICKET;
    }
}
