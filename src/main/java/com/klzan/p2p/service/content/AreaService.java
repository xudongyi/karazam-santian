package com.klzan.p2p.service.content;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.Area;

import java.util.List;

/**
 * 地区
 *
 * @author chenxinglin Date: 2016-12-2 12:34:22
 */
public interface AreaService extends IBaseService<Area> {

    /**
     * 获取省份列表
     *
     * @return
     */
    List<Area> getAreaList();

    /**
     * 获取省份对应地区列表
     *
     * @param parentId
     */
    List<Area> findChildren(Integer parentId);

    /**
     * 查找顶级地区集合
     *
     * @return 顶级地区集合
     */
    List<Area> findRoots();


//    void deal();
}
