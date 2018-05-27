package com.klzan.p2p.service.investment.impl;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.util.SnUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.Investment;
import com.klzan.p2p.model.UserAutoInvestmentRank;
import com.klzan.p2p.service.investment.AutoInvestmentService;
import com.klzan.p2p.service.user.UserAutoInvestmentRankService;
import com.klzan.p2p.service.user.UserMetaService;
import com.klzan.p2p.setting.AutoInvestmentSetting;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.investment.InvestVo;
import com.klzan.p2p.vo.user.UserAutoInvestVo;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.InvestmentAutoRequest;
import com.klzan.plugin.pay.common.module.PayModule;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sue on 2017/5/29.
 */
@Service
public class AutoInvestmentServiceImpl extends BaseService<Investment> implements AutoInvestmentService {

    @Inject
    private BorrowingDao borrowingDao;
    @Inject
    private UserAutoInvestmentRankService userAutoInvestmentRankService;
    @Inject
    private UserMetaService userMetaService;
    @Inject
    private SettingUtils setting;

    @Override
    public void createOrUpdateSign(UserAutoInvestVo autoInvestVo) {
        autoInvestVo.setAutoInvestSign(true);
//        autoInvestVo.setInvestmentAomunt(tmpInvest.getInvestmentAomunt());
        autoInvestVo.setStatus(true);

        userMetaService.addMetasByType(SignType.AUTO_INVESTMENT_SIGN, autoInvestVo);
        userAutoInvestmentRankService.addOrMergeRank(autoInvestVo);
    }

    @Override
    public void executeAutoInvest(Integer borrowingId) {
        Borrowing borrowing = borrowingDao.get(borrowingId);
        if (null == borrowing) {
            throw new BusinessProcessException("项目不存在");
        }
        AutoInvestmentSetting autoInvestmentSetting = setting.getAutoInvestmentSetting();
        BigDecimal autoInvestmentPercent = autoInvestmentSetting.getAutoInvestmentPercent();
        BigDecimal autoInvestmentMaxAmount = borrowing.getAmount().multiply(autoInvestmentPercent).divide(new BigDecimal(100), 0, BigDecimal.ROUND_DOWN);
        BigDecimal[] maxInvestResults = autoInvestmentMaxAmount.divideAndRemainder(BigDecimal.valueOf(100));
        if (maxInvestResults[1].intValue() != 0) {
            autoInvestmentMaxAmount = maxInvestResults[0].multiply(new BigDecimal(100));
        }
        List<UserAutoInvestmentRank> effectiveList = userAutoInvestmentRankService.findEffectiveList(borrowingId);
        Integer autoInvestCount = 1;
        Map<Integer, List<InvestVo>> autoInvestQueue = new LinkedHashMap<>();
        for (UserAutoInvestmentRank rank : effectiveList) {
            borrowing = borrowingDao.get(borrowingId);
            if (borrowing.getIsInvestFull()) {
                logger.warn("标的{}已投满，此次自动投标结束", borrowingId);
                break;
            }
            if (borrowing.getSurplusInvestmentAmount().compareTo(BigDecimal.ZERO) == 0) {
                logger.warn("标的{}已投满，此次自动投标结束", borrowingId);
                break;
            }
            if (!autoInvestQueue.containsKey(autoInvestCount)) {
                autoInvestQueue.put(autoInvestCount, new ArrayList<>());
            } else {
                List<InvestVo> investVos = autoInvestQueue.get(autoInvestCount);
                if (investVos.size() > 100) {
                    autoInvestCount++;
                    autoInvestQueue.put(autoInvestCount, new ArrayList<>());
                }
            }

            String sn = SnUtils.getOrderNo();
            // 完善业务参数
            InvestVo investVo = new InvestVo();
            investVo.setProjectId(borrowingId);
            investVo.setInvestor(rank.getUserId());
            investVo.setOperationMethod(OperationMethod.AUTO);
            investVo.setPaymentMethod(PaymentOrderMethod.CHINA_CLEAING_DEPOSIT_PAY);
            investVo.setDeviceType(DeviceType.SYSTEM);
            investVo.setSn(sn);

            BigDecimal autoInvestAmount = rank.getInvestMaxAmount();
            if (autoInvestAmount.compareTo(autoInvestmentMaxAmount) == 1) {
                autoInvestAmount = autoInvestmentMaxAmount;
            }
            if (autoInvestAmount.compareTo(borrowing.getSurplusInvestmentAmount()) == 1) {
                autoInvestAmount = borrowing.getSurplusInvestmentAmount();
            }
            if (autoInvestAmount.compareTo(borrowing.getInvestmentMinimum()) == -1) {
                continue;
            }
            investVo.setAmount(autoInvestAmount);

            autoInvestQueue.get(autoInvestCount).add(investVo);
        }

        for (Map.Entry<Integer, List<InvestVo>> entry : autoInvestQueue.entrySet()) {
            PayModule payModule = PayPortal.invest_auto.getModuleInstance();
            InvestmentAutoRequest req = new InvestmentAutoRequest(SnUtils.getOrderNo(), borrowingId, entry.getValue());
            payModule.setRequest(req);
            payModule.invoking().getResponse();
        }

        borrowing.setProgress(BorrowingProgress.INVESTING);
        borrowingDao.merge(borrowing);
    }
}
