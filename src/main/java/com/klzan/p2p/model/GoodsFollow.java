package com.klzan.p2p.model;

import com.klzan.core.util.SpringUtils;
import com.klzan.p2p.model.base.BaseModel;
import com.klzan.p2p.service.goods.GoodsService;
import com.klzan.p2p.service.goods.impl.GoodsServiceImpl;

import javax.persistence.*;

/**
 * 商品关注
 */
@Entity
@Table(name = "karazam_goods_follow")
public class GoodsFollow extends BaseModel {

    /** 用户 */
    @Column(nullable = false)
    private Integer userId;

	/** 商品 */
    @Column(nullable = false)
	private Integer goods;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGoods() {
		return goods;
	}

	public void setGoods(Integer goods) {
		this.goods = goods;
	}

	/** 商品  */
	@Transient
	public Goods getGoodsObj() {
		GoodsService goodsService = SpringUtils.getBean(GoodsServiceImpl.class);
		if(getGoods()!=null && goodsService!=null){
			return goodsService.get(getGoods());
		}
		return null;
	}
}
