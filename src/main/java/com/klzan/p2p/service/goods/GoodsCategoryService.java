package com.klzan.p2p.service.goods;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.model.*;
import com.klzan.p2p.vo.investment.InvestVo;
import com.klzan.p2p.vo.investment.InvestmentVo;
import com.klzan.p2p.vo.investment.WaitingProfitInvestmentVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品分类
 * @author chenxinglin
 */
public interface GoodsCategoryService extends IBaseService<GoodsCategory> {

	/**
	 * 分页列表
	 * @param pageCriteria 分页信息
	 * @return
	 */
	PageResult<GoodsCategory> findPage(PageCriteria pageCriteria);


}
