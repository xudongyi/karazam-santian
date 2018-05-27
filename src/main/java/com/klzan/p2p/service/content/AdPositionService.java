package com.klzan.p2p.service.content;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.AdPosition;
import com.klzan.p2p.vo.content.AdPositionVo;

/**
 * 友情链接
 */
public interface AdPositionService extends IBaseService<AdPosition> {
    /**
     * 分页查找Ad
     *
     * @param pageCriteria
     * @return
     */
    PageResult<AdPositionVo> findPageByCategory(PageCriteria pageCriteria);

    Boolean nameExists(String name);

    Boolean identExists(String ident);

}
