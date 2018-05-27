package com.klzan.p2p.service.posts.impl;

import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.enums.PostsTaxonomyType;
import com.klzan.p2p.model.PostsMapping;
import com.klzan.p2p.model.PostsTaxonomy;
import com.klzan.p2p.service.posts.PostsMappingService;
import com.klzan.p2p.service.posts.PostsTaxonomyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sue on 2017/5/29.
 */
@Service
public class PostsMappingServiceImpl extends BaseService<PostsMapping> implements PostsMappingService {

    @Autowired
    private PostsTaxonomyService postsTaxonomyService;

    @Override
    public List<PostsMapping> findByContent(PostsTaxonomyType type, Integer contentId) {
        Map map = new HashMap();
        map.put("taxonomyType", type.name().toLowerCase());
        map.put("contentId", contentId);
        return myDaoSupport.findList("com.klzan.p2p.mapper.PostsMappingMapper.findBycriteria", map);
    }

    @Override
    public void addOrMerge(PostsTaxonomyType type, Integer contentId, Integer... taxonomyIds) {
        List<PostsMapping> mappings = findByContent(type, contentId);
        if (!mappings.isEmpty()) {
            for (PostsMapping mapping : mappings) {
                this.delete(get(mapping.getId()));
                PostsTaxonomy taxonomy = postsTaxonomyService.get(mapping.getTaxonomyId());
                taxonomy.subtractContentCount();
                postsTaxonomyService.merge(taxonomy);
            }
        }
        if (null == taxonomyIds) {
            return;
        }
        for (Integer taxonomyId : taxonomyIds) {
            if (null == taxonomyId) {
                continue;
            }
            PostsMapping mapping = new PostsMapping(contentId, taxonomyId);
            this.persist(mapping);

            PostsTaxonomy taxonomy = postsTaxonomyService.get(taxonomyId);
            taxonomy.addContentCount();
            postsTaxonomyService.merge(taxonomy);
        }
    }
}
