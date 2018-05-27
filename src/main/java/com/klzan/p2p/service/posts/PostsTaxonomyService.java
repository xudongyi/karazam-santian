package com.klzan.p2p.service.posts;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.PostsTaxonomyType;
import com.klzan.p2p.model.PostsTaxonomy;
import com.klzan.p2p.vo.posts.PostsTaxonomyVo;

import java.util.List;

/**
 * Created by Sue on 2017/5/29.
 */
public interface PostsTaxonomyService extends IBaseService<PostsTaxonomy> {
    void createTaxonomy(PostsTaxonomyVo taxonomy);

    PageResult<PostsTaxonomy> findPage(PageCriteria criteria, PostsTaxonomyVo taxonomy);

    List<PostsTaxonomy> findListByParent(Integer parentId);

    List<PostsTaxonomy> findListByType(PostsTaxonomyVo taxonomy);

    Boolean isExistSlug(String type, String slug, Integer taxonomyId);

    void updateTaxonomy(PostsTaxonomyVo taxonomyVo);

    void updateTaxonomySetting(PostsTaxonomyVo taxonomyVo);

    PostsTaxonomy find(String type, String slug);

    List<PostsTaxonomy> findChildren(String type, String slug);

    PostsTaxonomy findParent(String type, String slug);

    List<PostsTaxonomy> findBrothers(String type, String slug);

    List<PostsTaxonomy> findContentTaxonomy(PostsTaxonomyType taxonomyType, Integer contentId);

    List<PostsTaxonomy> findChildren(Integer taxonomyId);

}
