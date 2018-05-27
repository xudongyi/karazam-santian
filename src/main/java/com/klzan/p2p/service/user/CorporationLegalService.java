package com.klzan.p2p.service.user;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.CorporationLegal;

/**
 * 企业信息
 *
 * @author zhu
 */
public interface CorporationLegalService extends IBaseService<CorporationLegal> {
    /**
     * 新增企业法人
     *
     * @param corporationLegal
     */
    CorporationLegal createCorporationLegal(CorporationLegal corporationLegal);

    /**
     * 根据id查询企业法人信息
     *
     * @param cardId
     */
    CorporationLegal findCorporationLegalByCardId(String cardId);

    /**
     * 根据userId查询企业法人信息
     * @param userId
     * @return
     */
    CorporationLegal findCorporationLegalByUserId(Integer userId);
    /**
     *  根据法人Id查找企业法人信息
     * */
    CorporationLegal findCorporationlegalBylegalId(Integer legalId);
}
