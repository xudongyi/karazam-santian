package com.klzan.p2p.service.goods.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.goods.GoodsFollowDao;
import com.klzan.p2p.model.GoodsFollow;
import com.klzan.p2p.service.goods.GoodsFollowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
@Transactional
public class GoodsFollowServiceImpl extends BaseService<GoodsFollow> implements GoodsFollowService {

	@Inject
	private GoodsFollowDao goodsFollowDao;

	@Override
	public PageResult<GoodsFollow> findPage(PageCriteria pageCriteria, Integer userId) {
		return goodsFollowDao.findPage(pageCriteria, userId);
	}

	@Override
	public GoodsFollow find(Integer userId, Integer goods) {
		return goodsFollowDao.find(userId, goods);
	}
}
