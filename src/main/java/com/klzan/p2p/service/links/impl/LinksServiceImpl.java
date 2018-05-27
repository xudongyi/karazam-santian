package com.klzan.p2p.service.links.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.links.LinksDao;
import com.klzan.p2p.enums.FriendLinkType;
import com.klzan.p2p.model.Links;
import com.klzan.p2p.service.links.LinksService;
import com.klzan.p2p.vo.links.LinksVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LinksServiceImpl extends BaseService<Links> implements LinksService {

    @Resource
    private LinksDao linksDao;

    @Override
    public List<LinksVo> findList(FriendLinkType type) {
        Map param = new HashMap();
        param.put("type", type.name());
        List<LinksVo> list = myDaoSupport.findList("com.klzan.p2p.mapper.LinksMapper.findList", param);
        return list;
    }
    @Override
    public PageResult<LinksVo> findPageByCategory(PageCriteria pageCriteria){
        return linksDao.findPageByCategory(pageCriteria);
    }

    @Override
    public Boolean isNameExist(String name) {
        Links links = linksDao.findLinkByName(name);
        if(links==null){
            return false;
        }
        return true;
    }
    @Override
    public void saveLink(Links links){
        linksDao.persist(links);
    }

}
