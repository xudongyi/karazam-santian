package com.klzan.p2p.vo.links;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.FriendLinkType;

public class LinksVo extends BaseVo {
    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private FriendLinkType type;
    private String typeStr;

    /**
     * LOGO
     */
    private String logo;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 链接打开方式
     */
    private String target;

    /**
     * 链接描述
     */
    private String description;

    /**
     * 是否可见（Y/N）
     */
    private Boolean visible;

    /**
     * 排序
     */
    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FriendLinkType getType() {
        if (StringUtils.isNotBlank(typeStr)) {
            return FriendLinkType.valueOf(typeStr);
        }
        return type;
    }

    public void setType(FriendLinkType type) {
        this.type = type;
    }

    public String getTypeStr() {
        if (StringUtils.isNotBlank(typeStr)) {
            return FriendLinkType.valueOf(typeStr).getDisplayName();
        }
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
