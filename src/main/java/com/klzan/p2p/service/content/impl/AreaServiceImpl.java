/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.content.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.content.AreaDao;
import com.klzan.p2p.model.Area;
import com.klzan.p2p.service.content.AreaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * 地区
 *
 * @author: chenxinglin  Date: 2016/11/10
 */
@Service
public class AreaServiceImpl extends BaseService<Area> implements AreaService {

    @Inject
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaList() {
        return areaDao.getAreaList();
    }

    @Override
    public List<Area> findChildren(Integer parentId) {
        return areaDao.findChildren(parentId);
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable("area")
    public List<Area> findRoots() {
        return areaDao.findRoots();
    }

//    @Override
//    @Transactional
//    public void deal() {
//
//        List<AAProvince> provinces = provinceDao.getList();
//        for(AAProvince province : provinces){
//            Area area = new Area();
//            area.setName(province.getName());
//            area.setFullName(province.getName());
//            area.setTreePath(",");
//            area.setGrade(0);
//            area.setSort(province.getId());
////            area.setParent();
//            area.setCode(province.getCode());
//            areaDao.persist(area);
//        }
//
//        List<AAProvince> provinces = provinceDao.getList();
//        for(AAProvince province : provinces){
//            int i = 1;
//            Area provinceArea = areaDao.find(province.getCode());
//            List<AACity> citys = cityDao.findbyprovince(province.getCode());
//            for(AACity city : citys){
//                Area area = new Area();
//                area.setName(city.getName());
//                area.setFullName(province.getName()+city.getName());
//                area.setTreePath(","+provinceArea.getId()+",");
//                area.setGrade(1);
//                area.setSort(i);
//                area.setParent(provinceArea.getId());
//                area.setCode(city.getCode());
//                areaDao.persist(area);
//                i++;
//            }
//        }
//
//        List<AAProvince> provinces = provinceDao.getList();
//        for(AAProvince province : provinces){
//            Area provinceArea = areaDao.find(province.getCode());
//            List<AACity> citys = cityDao.findbyprovince(province.getCode());
//            for(AACity city : citys){
//                int i = 1;
//                Area cityArea = areaDao.find(city.getCode());
//                List<AArea> aAreas = aAreaDao.findbycitycode(city.getCode());
//                for(AArea aArea : aAreas){
//                    Area area = new Area();
//                    area.setName(aArea.getName());
//                    area.setFullName(province.getName()+city.getName()+aArea.getName());
//                    area.setTreePath(","+provinceArea.getId()+","+cityArea.getId()+",");
//                    area.setGrade(2);
//                    area.setSort(i);
//                    area.setParent(cityArea.getId());
//                    area.setCode(aArea.getCode());
//                    areaDao.persist(area);
//                    i++;
//                }
//
//            }
//        }
//
//    }

}
