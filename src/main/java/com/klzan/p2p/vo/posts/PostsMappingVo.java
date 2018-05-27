package com.klzan.p2p.vo.posts;

import com.klzan.p2p.common.vo.BaseVo;

/**
 * 内容和分类的多对多映射关系
 * Created by Sue on 2017/5/28.
 */
public class PostsMappingVo extends BaseVo {
    /**
     * 内容ID
     */
    private Integer contentId;
    /**
     * 分类ID
     */
    private Integer taxonomyId;

    public Integer getContentId() {
        return contentId;
    }

    public Integer getTaxonomyId() {
        return taxonomyId;
    }
}
