package com.klzan.p2p.service.posts.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.ObjectUtils;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.enums.PostsTaxonomyType;
import com.klzan.p2p.model.PostsTaxonomy;
import com.klzan.p2p.service.posts.PostsTaxonomyService;
import com.klzan.p2p.vo.posts.PostsTaxonomyVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sue on 2017/5/29.
 */
@Service
public class PostsTaxonomyServiceImpl extends BaseService<PostsTaxonomy> implements PostsTaxonomyService {

    @Override
    public void createTaxonomy(PostsTaxonomyVo taxonomyVo) {
        PostsTaxonomy taxonomy = new PostsTaxonomy();
        Map<String, Object> fieldMap = ObjectUtils.getFieldMap(taxonomyVo);
        fieldMap.remove("id");
        fieldMap.remove("createDate");
        fieldMap.remove("modifyDate");
        try {
            for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
                BeanUtils.copyProperty(taxonomy, entry.getKey(), entry.getValue());
            }
            if (null != taxonomyVo.getParentId()) {
                PostsTaxonomy parentTaxonomy = this.get(taxonomyVo.getParentId());
                if (null != parentTaxonomy) {
                    StringBuffer addParentIds = new StringBuffer();
                    if (StringUtils.isNoneBlank(parentTaxonomy.getParentIds())) {
                        addParentIds.append(parentTaxonomy.getParentIds()).append("/");
                    }
                    addParentIds.append(parentTaxonomy.getId());
                    taxonomy.setParentIds(addParentIds.toString());
                }
            }
            this.persist(taxonomy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageResult<PostsTaxonomy> findPage(PageCriteria criteria, PostsTaxonomyVo taxonomy) {
        return myDaoSupport.findPage("com.klzan.p2p.mapper.PostsTaxonomyMapper.findList", taxonomy, criteria);
    }

    @Override
    public List<PostsTaxonomy> findListByParent(Integer parentId) {
        PostsTaxonomyVo taxonomy = new PostsTaxonomyVo();
        taxonomy.setParentId(parentId);
        return myDaoSupport.findList("com.klzan.p2p.mapper.PostsTaxonomyMapper.findChildren", taxonomy);
    }

    @Override
    public List<PostsTaxonomy> findListByType(PostsTaxonomyVo taxonomy) {
        return myDaoSupport.findList("com.klzan.p2p.mapper.PostsTaxonomyMapper.findByCriteria", taxonomy);
    }

    @Override
    public Boolean isExistSlug(String type, String slug, Integer taxonomyId) {
        PostsTaxonomyVo taxonomy = new PostsTaxonomyVo();
        taxonomy.setId(taxonomyId);
        taxonomy.setSlug(slug);
        taxonomy.setType(type.toLowerCase());
        List<PostsTaxonomy> taxonomies = myDaoSupport.findList("com.klzan.p2p.mapper.PostsTaxonomyMapper.existSlug", taxonomy);
        return null != taxonomies && !taxonomies.isEmpty() ? true : false;
    }

    @Override
    public void updateTaxonomy(PostsTaxonomyVo taxonomyVo) {
        PostsTaxonomy taxonomy = get(taxonomyVo.getId());
        Map<String, Object> fieldMap = ObjectUtils.getFieldMap(taxonomyVo);
        fieldMap.remove("id");
        fieldMap.remove("createDate");
        fieldMap.remove("modifyDate");
        try {
            for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
                BeanUtils.copyProperty(taxonomy, entry.getKey(), entry.getValue());
            }
            if (null != taxonomyVo.getParentId()) {
                PostsTaxonomy parentTaxonomy = this.get(taxonomyVo.getParentId());
                if (null != parentTaxonomy) {
                    StringBuffer addParentIds = new StringBuffer();
                    if (StringUtils.isNoneBlank(parentTaxonomy.getParentIds())) {
                        addParentIds.append(parentTaxonomy.getParentIds()).append("/");
                    }
                    addParentIds.append(parentTaxonomy.getId());
                    taxonomy.setParentIds(addParentIds.toString());
                }
            }
            this.persist(taxonomy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTaxonomySetting(PostsTaxonomyVo taxonomyVo) {
        PostsTaxonomy taxonomy = get(taxonomyVo.getId());
        taxonomy.setMetaKeywords(taxonomyVo.getMetaKeywords());
        taxonomy.setMetaDescription(taxonomyVo.getMetaDescription());
        taxonomy.setDisplay(taxonomyVo.getDisplay());
        this.merge(taxonomy);
    }

    @Override
    public PostsTaxonomy find(String type, String slug) {
        PostsTaxonomyVo taxonomy = new PostsTaxonomyVo();
        taxonomy.setSlug(slug);
        taxonomy.setType(type.toLowerCase());
        return (PostsTaxonomy) myDaoSupport.findUnique("com.klzan.p2p.mapper.PostsTaxonomyMapper.findByCriteria", taxonomy);
    }

    @Override
    public List<PostsTaxonomy> findChildren(String type, String slug) {
        PostsTaxonomyVo taxonomy = new PostsTaxonomyVo();
        taxonomy.setDisplay(true);
        taxonomy.setSlug(slug);
        taxonomy.setType(type.toLowerCase());
        return myDaoSupport.findList("com.klzan.p2p.mapper.PostsTaxonomyMapper.findChildren", taxonomy);
    }

    @Override
    public PostsTaxonomy findParent(String type, String slug) {
        PostsTaxonomy self = find(type, slug);
        return get(self.getParentId());
    }

    @Override
    public List<PostsTaxonomy> findBrothers(String type, String slug) {
        PostsTaxonomy self = find(type, slug);
        PostsTaxonomy parent = get(self.getParentId());
        PostsTaxonomyVo taxonomy = new PostsTaxonomyVo();
        taxonomy.setDisplay(true);
        taxonomy.setSlug(parent.getSlug());
        taxonomy.setType(parent.getType().toLowerCase());
        return myDaoSupport.findList("com.klzan.p2p.mapper.PostsTaxonomyMapper.findChildren", taxonomy);
    }

    @Override
    public List<PostsTaxonomy> findContentTaxonomy(PostsTaxonomyType taxonomyType, Integer contentId) {
        Map map = new HashMap();
        map.put("contentId", contentId);
        map.put("taxonomyType", taxonomyType.name().toLowerCase());
        return myDaoSupport.findList("com.klzan.p2p.mapper.PostsTaxonomyMapper.findContentTaxonomy", map);
    }

    @Override
    public List<PostsTaxonomy> findChildren(Integer taxonomyId) {
        PostsTaxonomyVo taxonomy = new PostsTaxonomyVo();
        taxonomy.setParentId(taxonomyId);
        return myDaoSupport.findList("com.klzan.p2p.mapper.PostsTaxonomyMapper.findChildren", taxonomy);
    }
}
