package com.klzan.p2p.service.posts.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.ObjectUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.PostsContentStatus;
import com.klzan.p2p.enums.PostsTaxonomyType;
import com.klzan.p2p.model.PostsContent;
import com.klzan.p2p.model.PostsTaxonomy;
import com.klzan.p2p.service.posts.PostsContentService;
import com.klzan.p2p.service.posts.PostsMappingService;
import com.klzan.p2p.service.posts.PostsTaxonomyService;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.p2p.vo.posts.PostsContentVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Sue on 2017/5/29.
 */
@Service
public class PostsContentServiceImpl extends BaseService<PostsContent> implements PostsContentService {

    @Autowired
    private PostsMappingService postsMappingService;

    @Autowired
    private PostsTaxonomyService postsTaxonomyService;

    static {
        // 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.sql.Date.class);
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.util.Date.class);
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlTimestampConverter(null), java.sql.Timestamp.class);
        // 注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
    }

    @Override
    public Boolean isExistSlug(String slug, Integer contentId) {
        PostsContentVo contentVo = new PostsContentVo();
        contentVo.setId(contentId);
        contentVo.setSlug(slug);
        List<PostsContent> contents = myDaoSupport.findList("com.klzan.p2p.mapper.PostsContentMapper.existSlug", contentVo);
        return null != contents && !contents.isEmpty() ? true : false;
    }

    @Override
    public void createContent(PostsContentVo contentVo) {
        PostsContent content = new PostsContent();
        Map<String, Object> fieldMap = ObjectUtils.getFieldMap(contentVo);
        fieldMap.remove("id");
        fieldMap.remove("createDate");
        fieldMap.remove("modifyDate");
        try {
            for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
                BeanUtils.copyProperty(content, entry.getKey(), entry.getValue());
            }
            content.setUserId(UserUtils.getCurrentSysUser().getId());
            content.setUserIp(CommonUtils.getRemoteIp());
            this.persist(content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer contentId = content.getId();
        // 分类
        Integer categoryId = contentVo.getCategoryId();
        Integer featureId = contentVo.getFeatureId();

        postsMappingService.addOrMerge(PostsTaxonomyType.CATEGORY, contentId, categoryId);
        postsMappingService.addOrMerge(PostsTaxonomyType.FEATURE, contentId, featureId);

        // 标签
        Integer[] tagIdArray = null;
        if (null != contentVo.getTagIds()) {
            tagIdArray = new Integer[contentVo.getTagIds().size()];
            int i = 0;
            for (Integer tagId : contentVo.getTagIds()) {
                tagIdArray[i] = tagId;
                i++;
            }
        }
        postsMappingService.addOrMerge(PostsTaxonomyType.TAG, contentId, tagIdArray);
    }

    @Override
    public void updateContent(PostsContentVo contentVo) {
        PostsContent content = get(contentVo.getId());
        Map<String, Object> fieldMap = ObjectUtils.getFieldMap(contentVo);
        fieldMap.remove("createDate");
        fieldMap.remove("modifyDate");
        try {
            for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
                BeanUtils.copyProperty(content, entry.getKey(), entry.getValue());
            }
            this.merge(content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer contentId = content.getId();
        // 分类
        Integer categoryId = contentVo.getCategoryId();
        Integer featureId = contentVo.getFeatureId();

        postsMappingService.addOrMerge(PostsTaxonomyType.CATEGORY, contentId, categoryId);
        postsMappingService.addOrMerge(PostsTaxonomyType.FEATURE, contentId, featureId);

        // 标签
        Integer[] tagIdArray = null;
        if (null != contentVo.getTagIds()) {
            tagIdArray = new Integer[contentVo.getTagIds().size()];
            int i = 0;
            for (Integer tagId : contentVo.getTagIds()) {
                tagIdArray[i] = tagId;
                i++;
            }
        }
        postsMappingService.addOrMerge(PostsTaxonomyType.TAG, contentId, tagIdArray);

    }

    @Override
    public PageResult<PostsContent> findPage(PageCriteria criteria) {
        return myDaoSupport.findPage("com.klzan.p2p.mapper.PostsContentMapper.findList", new PostsContentVo(), criteria);
    }

    @Override
    public PageResult<PostsContent> findPageByTaxonomy(PageCriteria criteria, PostsContentVo postsContentVo) {
        return myDaoSupport.findPage("com.klzan.p2p.mapper.PostsContentMapper.findList", postsContentVo, criteria);
    }

    @Override
    public PageResult<PostsContent> findPageUnderTaxonomy(PageCriteria criteria, PostsContentVo postsContentVo) {
        return myDaoSupport.findPage("com.klzan.p2p.mapper.PostsContentMapper.findList", postsContentVo, criteria);
    }

    @Override
    public PostsContent findBySlug(String slug) {
        PostsContentVo contentVo = new PostsContentVo();
        contentVo.setSlug(slug);
        contentVo.setExpire(new Date());
        contentVo.setTaxonomyType("category");
        PostsContent content = (PostsContent) myDaoSupport.findUnique("com.klzan.p2p.mapper.PostsContentMapper.findByCriteria", contentVo);
        return content;
    }

    @Override
    public List<PostsContent> findList(String taxonomyType, String taxonomySlug, Integer listSize) {
        Map<String, String> types = PostsTaxonomyType.getTypes();
        if (!types.containsKey(taxonomyType)) {
            return new ArrayList<>();
        }
        PostsTaxonomy taxonomy = postsTaxonomyService.find(taxonomyType, taxonomySlug);
        if (null == taxonomy) {
            return new ArrayList<>();
        }
        PostsContentVo postsContentVo = new PostsContentVo();
        postsContentVo.setTaxonomyId(taxonomy.getId());
        postsContentVo.setListSize(listSize);
        postsContentVo.setTaxonomyType(taxonomy.getType());
        postsContentVo.setTaxonomyDisplay(true);
        postsContentVo.setStatus(PostsContentStatus.PUBLISH.name());
        postsContentVo.setExpire(new Date());
        return myDaoSupport.findList("com.klzan.p2p.mapper.PostsContentMapper.findList", postsContentVo);
    }

    @Override
    public void updateViewCount(Integer contentId) {
        PostsContent content = get(contentId);
        if (null == content) {
            return;
        }
        Integer viewCount = content.getViewCount();
        if (null == viewCount) {
            viewCount = 0;
        }
        content.setViewCount(++viewCount);
        this.merge(content);
    }

    @Override
    public void updateStatus(Integer contentId, String status) {
        PostsContent content = get(contentId);
        if (null == content) {
            return;
        }
        content.setStatus(status);
        this.merge(content);
    }

    @Override
    public List<PostsContent> findList(Integer taxonomyId) {
        PostsContentVo postsContentVo = new PostsContentVo();
        postsContentVo.setTaxonomyId(taxonomyId);
        return myDaoSupport.findList("com.klzan.p2p.mapper.PostsContentMapper.findList", postsContentVo);
    }

}
