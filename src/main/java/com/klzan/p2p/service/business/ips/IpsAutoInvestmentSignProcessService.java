package com.klzan.p2p.service.business.ips;

import com.klzan.core.lock.LockStack;
import com.klzan.core.util.JsonUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.PaymentOrderStatus;
import com.klzan.p2p.enums.PaymentOrderType;
import com.klzan.p2p.enums.SignType;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.service.user.UserAutoInvestmentRankService;
import com.klzan.p2p.service.user.UserMetaService;
import com.klzan.p2p.vo.user.UserAutoInvestVo;
import com.klzan.plugin.pay.BusinessType;
import com.klzan.plugin.pay.IDetailResponse;
import com.klzan.plugin.pay.ips.autosign.vo.IpsPayAutoSignResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by suhao Date: 2017/4/7 Time: 18:31
 *
 * @version: 1.0
 */
@Service
public class IpsAutoInvestmentSignProcessService extends AbscractIpsProcessSerivce {

    @Autowired
    private UserMetaService userMetaService;

    @Autowired
    private UserAutoInvestmentRankService userAutoInvestmentRankService;

    @Override
    public String process(String orderNo, IDetailResponse response) {
        try {
            lock.lock(LockStack.USER_LOCK, "AUTO_INVEST_SIGN" + orderNo);
            checkOrder(orderNo);
            IpsPayAutoSignResponse autoSignResponse = (IpsPayAutoSignResponse) response;
            logger.info(JsonUtils.toJson(autoSignResponse));
            PaymentOrder paymentOrder = paymentOrderService.findByOrderNo(orderNo);
            Integer userId = paymentOrder.getUserId();
            UserAutoInvestVo tmpInvest = JsonUtils.toObject(paymentOrder.getExt(), UserAutoInvestVo.class);

            // 处理
            // 1-成功 0-失败
            if (StringUtils.equals(autoSignResponse.getStatus(), "1")) {
                UserAutoInvestVo autoInvestVo = new UserAutoInvestVo();
                autoInvestVo.setUserId(userId);
                autoInvestVo.setAutoInvestSign(true);
                autoInvestVo.setMerBillNo(autoSignResponse.getMerBillNo());
                autoInvestVo.setIpsBillNo(autoSignResponse.getIpsBillNo());
                autoInvestVo.setAuthNo(autoSignResponse.getIpsAuthNo());
                autoInvestVo.setValidity(Integer.parseInt(autoSignResponse.getValidity()));
                autoInvestVo.setProjectMinCyc(Integer.parseInt(autoSignResponse.getCycMinVal()));
                autoInvestVo.setProjectMaxCyc(Integer.parseInt(autoSignResponse.getCycMaxVal()));
                autoInvestVo.setInvestMinAmount(new BigDecimal(autoSignResponse.getBidMin()));
                autoInvestVo.setInvestMaxAmount(new BigDecimal(autoSignResponse.getBidMax()));
                autoInvestVo.setInterestRateMinRate(new BigDecimal(StringUtils.substring(autoSignResponse.getRateMin(), 0, autoSignResponse.getRateMin().indexOf("%"))));
                autoInvestVo.setInterestRateMaxRate(new BigDecimal(StringUtils.substring(autoSignResponse.getRateMax(), 0, autoSignResponse.getRateMax().indexOf("%"))));
                autoInvestVo.setInvestmentAomunt(tmpInvest.getInvestmentAomunt());
                autoInvestVo.setStatus(true);

                userMetaService.addMetasByType(SignType.AUTO_INVESTMENT_SIGN, autoInvestVo);
                userAutoInvestmentRankService.addOrMergeRank(autoInvestVo);
            }
            processOrder(orderNo, autoSignResponse.getIpsBillNo(), PaymentOrderStatus.SUCCESS);
            return autoSignResponse.getIpsAcctNo();
        } finally {
            lock.unLock(LockStack.USER_LOCK, "AUTO_INVEST_SIGN" + orderNo);
        }
    }

    @Override
    public Boolean support(BusinessType businessType, PaymentOrderType type) {
        return businessType == BusinessType.AUTO_SIGN && type == PaymentOrderType.AUTO_INVESTMENT_SIGN;
    }

}
