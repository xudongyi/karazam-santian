package com.klzan.p2p.service.investment;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.Investment;
import com.klzan.p2p.vo.user.UserAutoInvestVo;

/**
 * Created by Sue on 2017/5/29.
 */
public interface AutoInvestmentService extends IBaseService<Investment> {

    void createOrUpdateSign(UserAutoInvestVo autoInvestVo);

    void executeAutoInvest(Integer borrowingId);

}
