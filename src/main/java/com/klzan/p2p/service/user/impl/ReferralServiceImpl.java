package com.klzan.p2p.service.user.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.user.ReferralDao;
import com.klzan.p2p.model.Referral;
import com.klzan.p2p.service.user.ReferralService;
import com.klzan.p2p.vo.user.ReferralVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReferralServiceImpl extends BaseService<Referral> implements ReferralService {

	@Resource
	private ReferralDao refferralDao;
	@Override
	public PageResult<ReferralVo> findReferral(PageCriteria pageCriteria) {
		PageResult<ReferralVo> pageResult = refferralDao.findPageListPage(pageCriteria);
		return pageResult;
	}

	@Override
	public void createReferral(Referral referral) {
		refferralDao.createReferral(referral);
	}

	@Override
	public List<Referral> findListById(int id) {
		return refferralDao.getListById(id);
	}

}
