package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 分类表
 * 标签、专题、类别等都属于taxonomy
 * Created by Sue on 2017/5/28.
 */
@Entity
@Table(name = "karazam_posts_taxonomy")
public class PostsTaxonomy extends BaseModel {
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
     * 类型
     */
    private String type;
    /**
     * 图标
     */
    private String icon;
    /**
     * 对于的内容模型
     */
    private String contentModule;
    /**
     * 该分类的内容数量
     */
    private Integer contentCount = 0;
    /**
     * 排序编码
     */
    private Integer orderNumber;
    /**
     * 父级分类的ID
     */
    private Integer parentId;
    /**
     * 父级分类的ID全路径
     */
    private String parentIds;
    /**
     * 关联的对象ID
     */
    private Integer objectId;
    /**
     * 标识
     */
    private String flag;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 经度
     */
    private String lng;
    /**
     * SEO关键字
     */
    private String metaKeywords;
    /**
     * SEO描述内容
     */
    private String metaDescription;
    /**
     * 模板
     */
    private String template;
    /**
     * 是否显示
     */
    private Boolean display;

    @Transient
    private String state;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getContentModule() {
        return contentModule;
    }

    public void setContentModule(String contentModule) {
        this.contentModule = contentModule;
    }

    public Integer getContentCount() {
        return contentCount;
    }

    public void setContentCount(Integer contentCount) {
        this.contentCount = contentCount;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
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

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public void addContentCount() {
        if (null == this.contentCount) {
            this.contentCount = 0;
        }
        this.contentCount = this.contentCount + 1;
    }

    public void subtractContentCount() {
        if (null == this.contentCount) {
            this.contentCount = 0;
        }
        this.contentCount = this.contentCount - 1;
        if (this.contentCount.intValue() <= 0) {
            this.contentCount = 0;
        }
    }
}
