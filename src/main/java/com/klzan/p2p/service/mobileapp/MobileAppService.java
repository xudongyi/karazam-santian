package com.klzan.p2p.service.mobileapp;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.mobile.vo.MobileAppVo;
import com.klzan.p2p.enums.DeviceType;
import com.klzan.p2p.model.MobileApp;

/**
 * Created by suhao on 2017/1/18.
 */
public interface MobileAppService {

    PageResult<MobileAppVo> findByPage(PageCriteria criteria, DeviceType appType);

    void save(MobileAppVo appVo);

    void update(MobileAppVo appVo);

    MobileApp findById(Integer id);

    MobileApp findLatestApp(DeviceType type);

    void addDownCount(DeviceType appType);
}
