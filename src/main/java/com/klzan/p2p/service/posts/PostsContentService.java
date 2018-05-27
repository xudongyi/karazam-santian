package com.klzan.p2p.service.posts;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.PostsContent;
import com.klzan.p2p.vo.posts.PostsContentVo;

import java.util.List;

/**
 * Created by Sue on 2017/5/29.
 */
public interface PostsContentService extends IBaseService<PostsContent> {
    Boolean isExistSlug(String slug, Integer contentId);

    void createContent(PostsContentVo contentVo);

    void updateContent(PostsContentVo contentVo);

    PageResult<PostsContent> findPage(PageCriteria criteria);

    PageResult<PostsContent> findPageByTaxonomy(PageCriteria criteria, PostsContentVo postsContentVo);

    PageResult<PostsContent> findPageUnderTaxonomy(PageCriteria criteria, PostsContentVo postsContentVo);

    PostsContent findBySlug(String slug);

    List<PostsContent> findList(String taxonomyType, String taxonomySlug, Integer listSize);

    void updateViewCount(Integer contentId);

    void updateStatus(Integer contentId, String status);

    List<PostsContent> findList(Integer taxonomyId);

}
