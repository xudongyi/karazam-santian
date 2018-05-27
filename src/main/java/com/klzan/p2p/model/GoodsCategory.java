package com.klzan.p2p.model;

import com.klzan.core.page.Sort;
import com.klzan.p2p.model.base.BaseModel;
import com.klzan.p2p.model.base.BaseSortModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品类别
 */
@Entity
@Table(name = "karazam_goods_category")
public class GoodsCategory extends BaseSortModel {

	/** 名称 */
	@Column(nullable = false)
	private String name;

	/** 页面标题 */
	@Column
	private String seoTitle;

	/** 页面关键词 */
	@Column
	private String seoKeywords;

	/** 页面描述 */
	@Column
	private String seoDescription;

	/** 上级 */
	@Column
	private Integer parent;

	/** 层级 */
	@Column
	private Integer grade;

//	/** 拥有商品 */
//	private List<Goods> goods = new ArrayList<Goods>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

//	@OneToMany(mappedBy = "goodsCategory", fetch = FetchType.LAZY)
//	public List<Goods> getGoods() {
//		return goods;
//	}
//
//	public void setGoods(List<Goods> goods) {
//		this.goods = goods;
//	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
}
