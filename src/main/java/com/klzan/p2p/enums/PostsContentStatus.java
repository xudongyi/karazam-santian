package com.klzan.p2p.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sue on 2017/5/29.
 */
public enum PostsContentStatus implements IEnum {
    PUBLISH("发布"),

    DRAFT("草稿"),

    GARBAGE("回收");

    private String displayName;

    PostsContentStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public String getCode() {
        return this.name().toLowerCase();
    }

    public static Map<String, String> getTypes() {
        Map<String, String> types = new HashMap<>();
        for (PostsContentStatus taxonomyType : values()) {
            types.put(taxonomyType.name().toLowerCase(), taxonomyType.getDisplayName());
        }
        return types;
    }
}
