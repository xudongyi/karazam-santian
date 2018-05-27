package com.klzan.p2p.service.goods;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.GoodsFollow;

/**
 * 商品关注
 * @author chenxinglin
 *
 */
public interface GoodsFollowService extends IBaseService<GoodsFollow> {

    /**
     * 关注记录
     */
    PageResult<GoodsFollow> findPage(PageCriteria pageCriteria, Integer userId);

    /**
     * 关注记录
     */
    GoodsFollow find(Integer userId, Integer goods);
}
