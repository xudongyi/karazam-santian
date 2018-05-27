package com.klzan.p2p.service.mobileapp.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.mobile.vo.MobileAppVo;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.mobileapp.MobileAppDao;
import com.klzan.p2p.enums.DeviceType;
import com.klzan.p2p.model.MobileApp;
import com.klzan.p2p.service.mobileapp.MobileAppService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by suhao Date: 2017/1/18 Time: 11:31
 *Material
 * @version: 1.0
 */
@Service("MobileApp")
public class MobileAppServiceImpl extends BaseService<MobileApp> implements MobileAppService {

    @Resource
    private MobileAppDao mobileAppDao;

    @Override
    public PageResult<MobileAppVo> findByPage(PageCriteria criteria,DeviceType appType) {
        StringBuffer hql = new StringBuffer("From MobileApp o WHERE o.deleted = 0 ");
        Map param = new HashMap();

        Map map = new HashedMap();
        map.put("appType", appType.name());
        return myDaoSupport.findPage("mobileapp.findPage", map, criteria);
    }

    @Override

    public void save(MobileAppVo appVo) {
        MobileApp latestApp;
        if (appVo.getAppType().equals(DeviceType.IOS.name())){
             latestApp = findLatestApp(DeviceType.IOS);
        }else{
             latestApp = findLatestApp(DeviceType.ANDROID);
        }
        if (null == latestApp) {
            appVo.setVersionNo(1);
        } else {
            appVo.setVersionNo(latestApp.getVersionNo() + 1);
        }
        MobileApp app = new MobileApp(appVo.getVersionNo(), appVo.getVersionName(), appVo.getChangeLog(), appVo.getPackageName(), appVo.getAppUrl());
        if (appVo.getAppType().equals(DeviceType.IOS.name())){
            app.setAppType(DeviceType.IOS);
        }
        else{
            app.setAppType(DeviceType.ANDROID);
        }
        mobileAppDao.persist(app);
    }

    @Override
    public void update(MobileAppVo appVo) {
        MobileApp mobileApp = mobileAppDao.get(appVo.getId());
        mobileApp.update(appVo.getVersionName(), appVo.getChangeLog(), appVo.getPackageName(), appVo.getAppUrl());
        mobileAppDao.merge(mobileApp);
    }

    @Override
    public MobileApp findById(Integer id) {
        return mobileAppDao.get(id);
    }

    @Override
    public MobileApp findLatestApp(DeviceType type) {
        String hql = "FROM MobileApp WHERE deleted=0 AND appType=?0 ORDER BY id DESC ";
        List<MobileApp> mobileApps = mobileAppDao.find(hql, type);
        return mobileApps.isEmpty() ? null : mobileApps.get(0);
    }

    @Override
    public void addDownCount(DeviceType appType) {
        MobileApp latestApp = findLatestApp(appType);
        latestApp.addDownloadCount();
        mobileAppDao.merge(latestApp);
    }
}
