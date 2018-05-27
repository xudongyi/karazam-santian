/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.material;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.Material;

import java.util.List;

/**
 * 材料
 *
 * @author: chenxinglin  Date: 2016/11/1 Time: 17:07
 */
public interface MaterialService extends IBaseService<Material> {


    /**
     * 列表
     *
     * @param borrowingID 借款ID
     * @return
     */
    List<Material> findList(Integer borrowingID);

    /**
     * 列表
     *
     * @param borrowingID 借款ID
     * @return
     */
    void deleteList(Integer borrowingID);

}
