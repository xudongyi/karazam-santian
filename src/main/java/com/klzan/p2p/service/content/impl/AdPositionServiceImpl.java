package com.klzan.p2p.service.content.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.content.AdPositionDao;
import com.klzan.p2p.model.AdPosition;
import com.klzan.p2p.service.content.AdPositionService;
import com.klzan.p2p.vo.content.AdPositionVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdPositionServiceImpl extends BaseService<AdPosition> implements AdPositionService {

    @Resource
    private AdPositionDao adPositionDao;

    @Override
    public PageResult<AdPositionVo> findPageByCategory(PageCriteria pageCriteria) {
        return adPositionDao.findPageByCategory(pageCriteria);
    }

    @Override
    public Boolean nameExists(String name) {
        List<AdPosition> list = adPositionDao.findByName(name);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean identExists(String ident) {
        List<AdPosition> list = adPositionDao.findByName(ident);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }

}
