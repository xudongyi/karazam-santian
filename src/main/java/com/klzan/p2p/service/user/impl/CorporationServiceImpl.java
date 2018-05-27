package com.klzan.p2p.service.user.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.user.CorporationDao;
import com.klzan.p2p.dao.user.CorporationLegalDao;
import com.klzan.p2p.model.Corporation;
import com.klzan.p2p.model.CorporationLegal;
import com.klzan.p2p.service.user.CorporationService;
import com.klzan.p2p.vo.user.CorporationVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("corporationService")
public class CorporationServiceImpl extends BaseService<Corporation> implements CorporationService {

    @Resource
    private CorporationDao corporationDao;
    @Resource
    private CorporationLegalDao corporationLegalDao;

    @Override
    public PageResult<CorporationVo> findCorporation(PageCriteria pageCriteria) {
        return corporationDao.findPageListPage(pageCriteria);
    }

    @Override
    public Corporation findCorporationById(Integer id) {
        return corporationDao.findCorporationById(id);
    }

    /**
     * 新增企业信息的
     */
    @Override
    public void createCorporation(CorporationVo vo) {
        CorporationLegal corporationLegal = corporationLegalDao.findCorporationLegalByUserId(vo.getUserId());
        vo.setLegalId(corporationLegal.getId());
        corporationDao.createCorporation(vo);

    }

    @Override
    public void updateCorporation(CorporationVo vo, Integer id) {
        CorporationLegal corporationLegal = corporationLegalDao.findCorporationLegalByUserId(vo.getUserId());
        if (corporationLegal != null) {
            vo.setLegalId(corporationLegal.getId());
        }
        corporationDao.updateCorporation(vo, id);
    }

    @Override
    public void deleteCorporationById(Integer id) {
        corporationDao.logicDeleteById(id);
    }

    @Override
    public List<Corporation> findGuaranteeCorp() {
        return corporationDao.findGuaranteeCorp();
    }

    public Corporation findCorporationByUserId(Integer userId) {
        return corporationDao.findCorporationByUserId(userId);
    }

}
