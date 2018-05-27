package com.klzan.p2p.enums;

/**
 * Created by Sue on 2017/5/29.
 */
public enum PostsContentModule implements IEnum {
    ARTICLE("文章"),

    PAGE("页面"),

    MENU("菜单");

    private String displayName;

    PostsContentModule(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

}
