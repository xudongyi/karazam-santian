package com.klzan.plugin.pay.common.module;

import com.klzan.core.exception.PayException;
import com.klzan.p2p.vo.user.UserVo;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.UserInfoRequest;
import com.klzan.plugin.pay.cpcn.ChinaClearingConfig;
import com.klzan.plugin.pay.cpcn.ChinaClearingConstant;
import payment.api.tx.TxBaseRequest;
import payment.api.tx.paymentaccount.Tx4231Request;
import payment.api.tx.paymentaccount.Tx4234Request;

/**
 * 开户 - 支付组件
 *
 * @author: chenxinglin
 */
public class PayOpenAccountModule extends PayModule{

    @Override
    public PayPortal getPayPortal() {
        return PayPortal.open_account;
    }

    @Override
    protected TxBaseRequest getReqParam() {
        UserInfoRequest req = (UserInfoRequest)getRequest();
        UserVo userVo = payUtils.getUserVo(req.getUserId());

        String phoneNumber = userVo.getMobile();
        String userName = userVo.getRealName();
        String identificationNumber = userVo.getIdNo();
        String userType = payUtils.getUserType(req.getUserId());
        String sn = getSn();

        if(getRequest().isMobile()){
            if(!ChinaClearingConstant.UserType.personal.equals(userType)){
                throw new PayException("用户类型错误");
            }
            Tx4234Request request = new Tx4234Request();
            request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
            request.setPhoneNumber(phoneNumber);
            request.setUserName(userName);
            request.setIdentificationNumber(identificationNumber);
            request.setUserType(userType);
            request.setPageURL(ChinaClearingConfig.PAGE_URL + sn);
            return request;
        }

        Tx4231Request request = new Tx4231Request();
        request.setInstitutionID(ChinaClearingConfig.INSTITUTION_ID);
        request.setPhoneNumber(phoneNumber);
        request.setUserName(userName);
        request.setIdentificationNumber(identificationNumber);
        request.setUserType(userType);
        request.setPageURL(ChinaClearingConfig.PAGE_URL + sn);
        return request;
    }

}