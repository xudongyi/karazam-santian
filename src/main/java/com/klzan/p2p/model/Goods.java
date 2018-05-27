package com.klzan.p2p.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klzan.core.util.SpringUtils;
import com.klzan.p2p.common.util.UserUtils;
import com.klzan.p2p.enums.GoodsType;
import com.klzan.p2p.model.base.BaseSortModel;
import com.klzan.p2p.service.goods.GoodsFollowService;
import com.klzan.p2p.service.goods.impl.GoodsFollowServiceImpl;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 商品
 */
@Entity
@Table(name = "karazam_goods")
public class Goods extends BaseSortModel {

	/** 名称 */
    @Column(nullable = false)
	private String name;

	/** 副标题 */
	@Column
	private String subhead;

	/** 类型 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private GoodsType type = GoodsType.benefit;

    /** 原价格 */
    @Column(precision = 16, scale = 2)
    private BigDecimal originalPrice = BigDecimal.ZERO;

	/** 价格 */
    @Column(precision = 16, scale = 2)
	private BigDecimal price = BigDecimal.ZERO;

    /** 原积分 */
    @Column
    private Integer originalPoint = 0;

	/** 积分 */
    @Column
	private Integer point = 0;

	/** 展示图片 */
    @Column
	private String image;

	/** 展示图片 */
	@Column
	private String imageLarge;

	/** 展示图片 */
	@Column
	private String imageDetail;

	/** 重量 */
	@Column(precision = 16, scale = 2)
	private BigDecimal weight = BigDecimal.ZERO;

	/** 库存 */
    @Column
	private Integer stock = 0;

	/** 已分配库存 */
    @Column
	private Integer allocatedStock = 0;

	/** 总销量 */
	@Column
	private Integer sales = 0;

	/** 人气（关注） */
	@Column
	private Integer follow = 0;

	/** 是否虚拟商品 */
	@Column(nullable = false)
	private Boolean dummy = Boolean.FALSE;

	/** 是否上架 */
	@Column
	private Boolean putaway = Boolean.FALSE;

	/** 是否推荐 */
	@Column(nullable = false)
	private Boolean recommend = Boolean.FALSE;

	/** 是否热门 */
	@Column(nullable = false)
	private Boolean hot = Boolean.FALSE;

	/** 介绍 */
	@Lob
	private String introduction;

	/** 备注 */
	@Lob
	private String memo;

	/** 页面标题 */
	@Column
	private String seoTitle = "";

	/** 页面关键词 */
	@Column
	private String seoKeywords = "";

	/** 页面描述 */
	@Column
	private String seoDescription = "";

	/** 商品属性 */
	@Column(length = 1000)
	private String goodsAttr;

	/** 商品分类 */
	@Column
	private Integer goodsCategory;

	/** 历史数据 */
	@Column
	private Boolean history;

//    /** 积分的范围 */
//    private IntegralEnum integral;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public Boolean getPutaway() {
		return putaway;
	}

	public void setPutaway(Boolean putaway) {
		this.putaway = putaway;
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

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getOriginalPoint() {
        return originalPoint;
    }

    public void setOriginalPoint(Integer originalPoint) {
        this.originalPoint = originalPoint;
    }

	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}

	public GoodsType getType() {
		return type;
	}

	public void setType(GoodsType type) {
		this.type = type;
	}

	public Boolean getDummy() {
		return dummy;
	}

	public void setDummy(Boolean dummy) {
		this.dummy = dummy;
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

	public String getSubhead() {
		return subhead;
	}

	public void setSubhead(String subhead) {
		this.subhead = subhead;
	}

	@Transient
	public void addStock(Integer count) {
		setStock(getStock()+count);
	}

	@Transient
	public void subtractStock(Integer count) {
		setStock(getStock() - count);
		setAllocatedStock(getAllocatedStock() + count);
	}

	@Transient
	public void addFollow() {
		setFollow(getFollow()==null?1:(getFollow()+1));
	}

	@Transient
	public void subtractFollow() {
		setFollow(getFollow()==null||getFollow()==0?0:(getFollow()-1));
	}

	@Transient
	@JsonProperty("currentFollow")
	public Boolean getCurrentFollow() {
		GoodsFollowService goodsFollowService = SpringUtils.getBean(GoodsFollowServiceImpl.class);
		User currentUser = UserUtils.getCurrentUser();
		if(goodsFollowService!=null && currentUser!=null){
			GoodsFollow goodsFollow = goodsFollowService.find(currentUser.getId(), getId());
			if(goodsFollow!=null){
				return true;
			}
		}
		return false;
	}



	//	@Override
//	public String toString() {
//		return "GoodsEntity [name=" + name + ", price=" + price + ", point="
//				+ point + ", image=" + image + ", weight=" + weight
//				+ ", stock=" + stock + ", allocatedStock=" + allocatedStock
//				+ ", putaway=" + putaway + ", introduction="
//				+ introduction + ", memo=" + memo + ", seoTitle=" + seoTitle
//				+ ", seoKeywords=" + seoKeywords + ", seoDescription="
//				+ seoDescription + ", goodsAttr=" + goodsAttr + ", goodsCategory="
//				+ goodsAttr + "]";
//	}

//	@PreRemove
//	public void preRemove() {
//		for (GoodsOrderEntity goodsOrder : getOrders()) {
//			goodsOrder.setGoods(null);
//		}
//	}

}
