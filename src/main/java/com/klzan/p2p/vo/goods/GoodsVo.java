package com.klzan.p2p.vo.goods;

import com.klzan.p2p.common.vo.BaseSortVo;
import com.klzan.p2p.enums.GoodsType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import java.math.BigDecimal;

/**
 * 商品类别
 */
public class GoodsVo extends BaseSortVo {

    /** 名称 */
    private String name;

    /** 副标题 */
    private String subhead;

    /** 类型 */
    private GoodsType type;

    /** 原价格 */
    private BigDecimal originalPrice = BigDecimal.ZERO;

    /** 价格 */
    private BigDecimal price = BigDecimal.ZERO;

    /** 原积分 */
    private Integer originalPoint = 0;

    /** 积分 */
    private Integer point = 0;

    /** 展示图片 */
    private String image;

    /** 展示图片 */
    private String imageLarge;

    /** 展示图片 */
    private String imageDetail;

    /** 重量 */
    private BigDecimal weight = BigDecimal.ZERO;

    /** 库存 */
    private Integer stock = 0;

    /** 已分配库存 */
    private Integer allocatedStock = 0;

    /** 总销量 */
    private Integer sales = 0;

    /** 人气（关注） */
    private Integer follow = 0;

    /** 是否虚拟商品 */
    private Boolean dummy = Boolean.FALSE;

    /** 是否上架 */
    private Boolean putaway = Boolean.FALSE;

    /** 是否推荐 */
    private Boolean recommend = Boolean.FALSE;

    /** 是否热门 */
    private Boolean hot = Boolean.FALSE;

    /** 介绍 */
    private String introduction;

    /** 备注 */
    private String memo;

    /** 页面标题 */
    private String seoTitle = "";

    /** 页面关键词 */
    private String seoKeywords = "";

    /** 页面描述 */
    private String seoDescription = "";

    /** 商品属性 */
    private String goodsAttr;

    /** 商品分类 */
    private Integer goodsCategory;

    /** 历史数据 */
    private Boolean history;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getOriginalPoint() {
        return originalPoint;
    }

    public void setOriginalPoint(Integer originalPoint) {
        this.originalPoint = originalPoint;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(String imageLarge) {
        this.imageLarge = imageLarge;
    }

    public String getImageDetail() {
        return imageDetail;
    }

    public void setImageDetail(String imageDetail) {
        this.imageDetail = imageDetail;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getAllocatedStock() {
        return allocatedStock;
    }

    public void setAllocatedStock(Integer allocatedStock) {
        this.allocatedStock = allocatedStock;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getFollow() {
        return follow;
    }

    public void setFollow(Integer follow) {
        this.follow = follow;
    }

    public Boolean getDummy() {
        return dummy;
    }

    public void setDummy(Boolean dummy) {
        this.dummy = dummy;
    }

    public Boolean getPutaway() {
        return putaway;
    }

    public void setPutaway(Boolean putaway) {
        this.putaway = putaway;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Boolean getHot() {
        return hot;
    }

    public void setHot(Boolean hot) {
        this.hot = hot;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public String getGoodsAttr() {
        return goodsAttr;
    }

    public void setGoodsAttr(String goodsAttr) {
        this.goodsAttr = goodsAttr;
    }

    public Integer getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(Integer goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public Boolean getHistory() {
        return history;
    }

    public void setHistory(Boolean history) {
        this.history = history;
    }
}
