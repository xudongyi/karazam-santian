package com.klzan.p2p.model.sys;

import com.klzan.p2p.enums.ResourceType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "karazam_sys_resource")
public class SysResource extends BaseModel {
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
    @Enumerated(EnumType.STRING)
    private ResourceType type = ResourceType.MENU;
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
    private Integer parentId;
    /**
     * 父编号列表
     */
    private String parentIds;
    /**
     * 是否可用
     */
    private Boolean available = Boolean.FALSE;
    /**
     * 排序
     */
    @Min(0)
    private Integer sort = 0;
    /**
     * 描述
     */
    @Column(length = 200)
    private String description;

    public SysResource() {
    }

    public SysResource(String name, String type, String permission, String parentIds, String url, String icon, Integer parentId, Integer sort, String description) {
        this.name = name;
        this.type = ResourceType.valueOf(type);
        this.permission = permission;
        this.parentIds = parentIds;
        this.url = url;
        this.icon = icon;
        this.parentId = parentId;
        this.sort = sort;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public ResourceType getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getPermission() {
        return permission;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public Boolean getAvailable() {
        return available;
    }

    public Integer getSort() {
        return sort;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRootNode() {
        return parentId == 0;
    }

    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }

    public void update(String name, String type, String permission, String parentIds, String url, String icon, Integer parentId, Integer sort, String description) {
        this.name = name;
        this.type = ResourceType.valueOf(type);
        this.permission = permission;
        this.parentIds = parentIds;
        this.url = url;
        this.icon = icon;
        this.parentId = parentId;
        this.sort = sort;
        this.description = description;
    }
}
