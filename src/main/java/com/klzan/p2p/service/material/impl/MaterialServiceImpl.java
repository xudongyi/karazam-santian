/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.material.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.common.MaterialDao;
import com.klzan.p2p.model.Material;
import com.klzan.p2p.service.material.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: chenxinglin  Date: 2016/11/3 Time: 11:52
 */
@Service
public class MaterialServiceImpl extends BaseService<Material> implements MaterialService {

    @Autowired
    private MaterialDao materialDao;

    @Override
    public List<Material> findList(Integer borrowingID) {
        return materialDao.findList(borrowingID);
    }

    @Override
    public void deleteList(Integer borrowingID) {
        materialDao.deleteList(borrowingID);
    }

}
