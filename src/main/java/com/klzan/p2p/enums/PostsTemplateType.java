package com.klzan.p2p.enums;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.util.StringUtils;

/**
 * Created by Sue on 2017/5/29.
 */
public enum PostsTemplateType implements IEnum {
    CONTENT("内容", "/template/posts/content"),

    CATEGORY("分类", "/template/posts/taxonomy/category"),

    TAG("标签", "/template/posts/taxonomy/tag"),

    FEATURE("专题", "/template/posts/taxonomy/feature");

    private String displayName;
    private String path;

    PostsTemplateType(String displayName, String path) {
        this.displayName = displayName;
        this.path = path;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public String getPath() {
        return path;
    }

    public static PostsTemplateType getUse(String use) {
        for (PostsTemplateType templateType : values()) {
            if (StringUtils.equals(use.toUpperCase(), templateType.name())) {
                return templateType;
            }
        }
        throw new BusinessProcessException();
    }
}
