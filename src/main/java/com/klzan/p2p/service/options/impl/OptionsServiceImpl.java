/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.options.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.options.OptionsDao;
import com.klzan.p2p.enums.OptionsType;
import com.klzan.p2p.model.Options;
import com.klzan.p2p.service.options.OptionsService;
import com.klzan.p2p.vo.options.OptionsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by suhao Date: 2016/11/11 Time: 9:58
 *
 * @version: 1.0
 */
@Service("optionsService")
public class OptionsServiceImpl extends BaseService<Options> implements OptionsService {
    @Autowired
    private OptionsDao optionsDao;
    @Override
    public OptionsVo findById(Integer optionsId) {
        return optionsDao.findById(optionsId);
    }

    @Override
    public PageResult<OptionsVo> findPageByType(PageCriteria criteria, OptionsType type) {
        return optionsDao.findPageByType(criteria, type);
    }

    @Override
    public List<OptionsVo> findOptionsByType(OptionsType type) {
        return optionsDao.findListByType(type);
    }

    @Override
    public void updateOp(List<OptionsVo> optionsVos) {
        for (OptionsVo optionsVo : optionsVos) {
            Options options = optionsDao.get(optionsVo.getId());
            options.update(optionsVo.getKeyValue());
            optionsDao.update(options);
        }
    }
}
