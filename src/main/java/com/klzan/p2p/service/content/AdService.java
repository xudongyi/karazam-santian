package com.klzan.p2p.service.content;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.Ad;
import com.klzan.p2p.vo.content.AdVo;

import java.util.List;

/**
 * 友情链接
 */
public interface AdService extends IBaseService<Ad> {
    /**
     * 分页查找Ad
     *
     * @param pageCriteria
     * @return
     */
    PageResult<AdVo> findPageByCategory(PageCriteria pageCriteria);

    PageResult<AdVo> findPageByCategory(Integer id, PageCriteria pageCriteria);

    List<Ad> findAdByPosition(Integer positon);

    List<AdVo> findAdByIdent(String ident);
}
