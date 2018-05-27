package com.klzan.p2p.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sue on 2017/5/29.
 */
public enum PostsTaxonomyType implements IEnum {
    CATEGORY("分类"),

    TAG("标签"),

    FEATURE("专题");

    private String displayName;

    PostsTaxonomyType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public static Map<String, String> getTypes() {
        Map<String, String> types = new HashMap<>();
        for (PostsTaxonomyType taxonomyType : values()) {
            types.put(taxonomyType.name().toLowerCase(), taxonomyType.getDisplayName());
        }
        return types;
    }
}
