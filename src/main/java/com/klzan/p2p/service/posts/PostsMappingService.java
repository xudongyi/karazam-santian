package com.klzan.p2p.service.posts;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.PostsTaxonomyType;
import com.klzan.p2p.model.PostsMapping;

import java.util.List;

/**
 * Created by Sue on 2017/5/29.
 */
public interface PostsMappingService extends IBaseService<PostsMapping> {

    List<PostsMapping> findByContent(PostsTaxonomyType type, Integer contentId);

    void addOrMerge(PostsTaxonomyType type, Integer contentId, Integer... taxonomyId);
}
