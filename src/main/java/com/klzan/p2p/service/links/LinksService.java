package com.klzan.p2p.service.links;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.FriendLinkType;
import com.klzan.p2p.model.Links;
import com.klzan.p2p.vo.links.LinksVo;

import java.util.List;

/**
 * 友情链接
 */
public interface LinksService extends IBaseService<Links> {
    List<LinksVo> findList(FriendLinkType type);

    /**
     * 分页查找links
     * @param pageCriteria
     * @return
     */
    PageResult<LinksVo> findPageByCategory(PageCriteria pageCriteria);

    /**
     * 判断名称是否存在
     * @param name
     * @return
     */
    Boolean isNameExist(String name);

    /**
     * 保存
     * @param links
     */
    void saveLink(Links links);
}
