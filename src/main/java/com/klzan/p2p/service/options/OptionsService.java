/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.options;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.OptionsType;
import com.klzan.p2p.model.Options;
import com.klzan.p2p.vo.options.OptionsVo;

import java.util.List;

/**
 * 配置接口
 * Created by suhao on 2016/11/11.
 */
public interface OptionsService extends IBaseService<Options> {
    /**
     * 根据ID查找
     *
     * @param optionsId
     * @return
     */
    OptionsVo findById(Integer optionsId);
    /**
     * 查询分页
     * @param criteria
     * @return
     */
    PageResult<OptionsVo> findPageByType(PageCriteria criteria, OptionsType type);

    /**
     * 查询某类型的参数
     * @param type
     * @return
     */
    List<OptionsVo> findOptionsByType(OptionsType type);

    /**
     * 更新
     * @param optionsVos
     */
    void updateOp(List<OptionsVo> optionsVos);
}
