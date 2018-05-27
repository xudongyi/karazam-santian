package com.klzan.p2p.service.goods.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.goods.GoodsDao;
import com.klzan.p2p.dao.goods.GoodsFollowDao;
import com.klzan.p2p.enums.GoodsType;
import com.klzan.p2p.model.Goods;
import com.klzan.p2p.model.GoodsFollow;
import com.klzan.p2p.model.User;
import com.klzan.p2p.service.goods.GoodsFollowService;
import com.klzan.p2p.service.goods.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class GoodsServiceImpl extends BaseService<Goods> implements GoodsService {

	@Inject
	private GoodsDao goodsDao;

	@Inject
	private GoodsFollowService goodsFollowService;

	@Inject
	private GoodsFollowDao goodsFollowDao;

	@Override
	public PageResult<Goods> findPage(PageCriteria criteria){
		return goodsDao.findPage(criteria);
	}

	@Override
	public PageResult<Goods> findPage(PageCriteria criteria, Integer goodsCategory, GoodsType type, Integer point) {
		return goodsDao.findPage(criteria, goodsCategory, type, point);
	}

	@Override
	public List<Goods> findAllList(Integer goodsCategory) {
		return goodsDao.findAllList(goodsCategory);
	}

	@Override
	public List<Goods> findList(Integer goodsCategory) {
		return goodsDao.findList(goodsCategory);
	}

    @Override
    public List<Goods> findList(Integer goodsCategory, GoodsType type, Integer count, Boolean hot) {
        return goodsDao.findList(goodsCategory, type, count, hot);
    }

	@Override
	public Boolean follow(User currentUser, Goods goods) {
		GoodsFollow goodsFollow = goodsFollowService.find(currentUser.getId(), goods.getId());
		if(goodsFollow == null){
			goodsFollow = new GoodsFollow();
			goodsFollow.setUserId(currentUser.getId());
			goodsFollow.setGoods(goods.getId());
			goodsFollowService.persist(goodsFollow);

			goods.addFollow();
			goodsDao.merge(goods);
		}else {
//			goodsFollowService.remove(goodsFollow.getId());
			goodsFollowDao.delete(goodsFollow);
			goods.subtractFollow();
			goodsDao.merge(goods);
		}
		return true;
	}


}
