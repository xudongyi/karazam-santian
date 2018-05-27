package com.klzan.p2p.vo.goods;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.GoodsOrderStatus;
import com.klzan.p2p.enums.GoodsShippingStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品类别
 */
public class GoodsExchangeVo extends BaseVo {

    /** 商品 */
    private Integer goodsId;

    /** 数量 */
    private Integer quantity;

    /** 积分 */
    private Integer point;

    /** 收货地址 */
    private Integer address;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }
}
