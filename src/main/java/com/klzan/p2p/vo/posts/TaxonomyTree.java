package com.klzan.p2p.vo.posts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaxonomyTree implements Serializable {
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容描述
     */
    private String text;
    /**
     * slug
     */
    private String slug;
    /**
     * 该分类的内容数量
     */
    private Integer contentCount;
    /**
     * 父级分类的ID
     */
    private Integer parentId;
    private String state;

    private String iconCls = "fa fa-folder-open-o";

    private List<TaxonomyTree> children = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getContentCount() {
        return contentCount;
    }

    public void setContentCount(Integer contentCount) {
        this.contentCount = contentCount;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public List<TaxonomyTree> getChildren() {
        return children;
    }

    public void setChildren(List<TaxonomyTree> children) {
        this.children = children;
    }
}
