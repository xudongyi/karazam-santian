package com.klzan.p2p.service.content.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.content.AdDao;
import com.klzan.p2p.model.Ad;
import com.klzan.p2p.service.content.AdService;
import com.klzan.p2p.vo.content.AdVo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AdServiceImpl extends BaseService<Ad> implements AdService {

    @Resource
    private AdDao adDao;

    @Override
    public PageResult<AdVo> findPageByCategory(PageCriteria pageCriteria) {
        return adDao.findPageByCategory(pageCriteria);
    }

    @Override
    public PageResult<AdVo> findPageByCategory(Integer position, PageCriteria pageCriteria) {
        return adDao.findPageByCategory(position, pageCriteria);
    }

    @Override
    public List<Ad> findAdByPosition(Integer positon) {

        return adDao.findAdByPosition(positon);
    }

    @Override
    public List<AdVo> findAdByIdent(String ident) {
        Map map = new HashedMap();
        map.put("ident", ident);
        map.put("nowtime", new Date());
        return myDaoSupport.findList("com.klzan.p2p.mapper.AdMapper.findByIdent", map);
    }

}
