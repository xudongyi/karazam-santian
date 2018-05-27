package com.klzan.p2p.vo.sysuser;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.ResourceType;

import java.io.Serializable;

public class SysResourceVo implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = -3703154275817120085L;
    /**
     * ID
     */
    private Integer id;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 图标
     */
    private String icon;
    /**
     * 资源类型
     */
    private String type;
    private String typeDisplay;
    /**
     * 资源路径
     */
    private String url;
    /**
     * 权限字符串
     */
    private String permission;
    /**
     * 父编号
     */
    private Integer parentId = 0;
    /**
     * 父编号列表
     */
    private String parentIds;
    /**
     * 是否可用
     */
    private Boolean available;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String description;

    private Boolean hasChildren;

    public SysResourceVo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDisplay() {
        if (StringUtils.isNotBlank(type)) {
            typeDisplay = ResourceType.valueOf(type).getInfo();
        }
        return typeDisplay;
    }

    public void setTypeDisplay(String typeDisplay) {
        this.typeDisplay = typeDisplay;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}
