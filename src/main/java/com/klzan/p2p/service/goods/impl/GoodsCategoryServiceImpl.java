package com.klzan.p2p.service.goods.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.SnUtils;
import com.klzan.core.util.WebUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.dao.capital.CapitalDao;
import com.klzan.p2p.dao.goods.GoodsCategoryDao;
import com.klzan.p2p.dao.investment.InvestmentDao;
import com.klzan.p2p.dao.investment.InvestmentRecordDao;
import com.klzan.p2p.dao.user.UserDao;
import com.klzan.p2p.dao.user.UserFinanceDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.goods.GoodsCategoryService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.vo.investment.InvestVo;
import com.klzan.p2p.vo.investment.InvestmentVo;
import com.klzan.p2p.vo.investment.WaitingProfitInvestmentVo;
import org.apache.shiro.SecurityUtils;
import org.hibernate.LockMode;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品分类
 * @author chenxinglin
 */
@Service
public class GoodsCategoryServiceImpl extends BaseService<GoodsCategory> implements GoodsCategoryService {

	@Inject
	private GoodsCategoryDao goodsCategoryDao;

	@Override
	public PageResult<GoodsCategory> findPage(PageCriteria pageCriteria){
		return goodsCategoryDao.findPage(pageCriteria);
	}

}
