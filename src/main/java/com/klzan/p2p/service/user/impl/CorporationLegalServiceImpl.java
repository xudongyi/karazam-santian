package com.klzan.p2p.service.user.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.user.CorporationLegalDao;
import com.klzan.p2p.model.CorporationLegal;
import com.klzan.p2p.service.user.CorporationLegalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CorporationLegalServiceImpl extends BaseService<CorporationLegal> implements CorporationLegalService {

    @Resource
    private CorporationLegalDao corporationLegalDao;

    @Override
    public CorporationLegal createCorporationLegal(CorporationLegal corporationLegal) {
        return corporationLegalDao.persist(corporationLegal);
    }

    @Override
    public CorporationLegal findCorporationLegalByCardId(String cardId) {
        return corporationLegalDao.findCorporationLegalByCardId(cardId);
    }

    @Override
    public CorporationLegal findCorporationLegalByUserId(Integer userId) {
        return corporationLegalDao.findCorporationLegalByUserId(userId);
    }
    @Override
   public CorporationLegal findCorporationlegalBylegalId(Integer legalId){
        return corporationLegalDao.findCorporationlegalBylegalId(legalId);
    }

}
