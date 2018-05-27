package com.klzan.p2p.service.appcover;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.DeviceType;
import com.klzan.p2p.model.AppCover;
import com.klzan.p2p.model.MobileApp;
import com.klzan.p2p.vo.AppCover.AppCoverVo;

import java.util.List;

/**
 * Created by Administrator on 2017/6/22 0022.
 */
public interface AppCoverService extends IBaseService<AppCover> {

    PageResult<AppCoverVo> findPageAppCover(PageCriteria pageCriteria);

    void save(AppCoverVo appCoverVo);

    void update(AppCoverVo appCoverVo);

    AppCover findById(Integer id);

    AppCover findLatestAppCover();
    List<AppCover> findLatestAppWelcomeCover();

}
