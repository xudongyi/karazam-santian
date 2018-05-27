package com.klzan.p2p.service.goods;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.GoodsType;
import com.klzan.p2p.model.Goods;
import com.klzan.p2p.model.User;

import java.util.List;

/**
 * 商品
 * @author chenxinglin
 *
 */
public interface GoodsService extends IBaseService<Goods> {

    /**
     * 分页列表
     * @param criteria 分页信息
     * @return
     */
    PageResult<Goods> findPage(PageCriteria criteria);

    /**
     * 分页列表
     * @param criteria 分页信息
     * @return
     */
    PageResult<Goods> findPage(PageCriteria criteria, Integer goodsCategory, GoodsType type, Integer point);

    /**
     * 商品
     * @param goodsCategory 商品分类
     * @return
     */
    List<Goods> findAllList(Integer goodsCategory);

    /**
     * 商品
     * @param goodsCategory 商品分类
     * @return
     */
    List<Goods> findList(Integer goodsCategory);

    /**
     * 商品
     * @param goodsCategory 商品分类
     * @return
     */
    List<Goods> findList(Integer goodsCategory, GoodsType type, Integer count, Boolean hot);

    /**
     * 关注商品
     * @param currentUser 用户
     * @param goods 商品
     * @return
     */
    Boolean follow(User currentUser, Goods goods);


}
