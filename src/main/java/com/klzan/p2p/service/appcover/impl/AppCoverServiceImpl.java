package com.klzan.p2p.service.appcover.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.mobile.vo.MobileAppVo;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.appcover.AppCoverDao;
import com.klzan.p2p.dao.mobileapp.MobileAppDao;
import com.klzan.p2p.enums.DeviceType;
import com.klzan.p2p.model.AppCover;
import com.klzan.p2p.model.Bank;
import com.klzan.p2p.model.MobileApp;
import com.klzan.p2p.service.agreement.AgreementService;
import com.klzan.p2p.service.appcover.AppCoverService;
import com.klzan.p2p.service.bank.BankService;
import com.klzan.p2p.vo.AppCover.AppCoverVo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/22 0022.
 */
@Service
public class AppCoverServiceImpl extends BaseService<AppCover>  implements AppCoverService {

    @Resource
    private AppCoverDao appCoverDao;

    @Override
    public List<AppCover> findLatestAppWelcomeCover() {
        String hql = "FROM AppCover WHERE deleted=0 AND startDate<NOW() AND endDate>NOW() AND isWelcomePage <> '0' ORDER BY id DESC ";
        List<AppCover> AppCover = appCoverDao.find(hql);
        return AppCover.isEmpty() ?null:AppCover;
    }

    @Override
    public AppCover findLatestAppCover() {
        String hql = "FROM AppCover WHERE deleted=0 AND startDate<NOW() AND endDate>NOW() AND isWelcomePage <> '1' ORDER BY id DESC ";
        List<AppCover> AppCover = appCoverDao.find(hql);
        return AppCover.isEmpty() ? null : AppCover.get(0);
    }


    @Override
    public PageResult<AppCoverVo> findPageAppCover(PageCriteria pageCriteria) {
        StringBuffer hql = new StringBuffer("From AppCover o WHERE o.deleted = 0 ");
        return myDaoSupport.findPage("appcover.findPage", new HashMap<>(), pageCriteria);
    }

    @Override
    public void save(AppCoverVo appCoverVo) {
        AppCover appCover= new AppCover(appCoverVo.getTitle(),appCoverVo.getPath(),appCoverVo.getStartDate(),appCoverVo.getEndDate(),appCoverVo.getUrl(),appCoverVo.getCont(),appCoverVo.getIsWelcomePage());
        appCoverDao.persist(appCover);
    }

    @Override
    public void update(AppCoverVo appCoverVo) {
        AppCover appCover= new AppCover(appCoverVo.getTitle(),appCoverVo.getPath(),appCoverVo.getStartDate(),appCoverVo.getEndDate(),appCoverVo.getUrl(),appCoverVo.getCont(),appCoverVo.getIsWelcomePage());
        appCoverDao.merge(appCover);
    }

    @Override
    public AppCover findById(Integer id) {
        return appCoverDao.get(id);
    }
}
