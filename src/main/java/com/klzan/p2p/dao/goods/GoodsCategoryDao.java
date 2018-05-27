/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.goods;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.enums.BorrowingState;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.model.GoodsCategory;
import com.klzan.p2p.model.Investment;
import com.klzan.p2p.model.User;
import com.klzan.p2p.vo.investment.InvestmentVo;
import com.klzan.p2p.vo.investment.WaitingProfitInvestmentVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品分类
 * @author: chenxinglin
 */
@Repository
public class GoodsCategoryDao extends DaoSupport<GoodsCategory> {

    /**
     * 分页列表
     * @param pageCriteria
     * @return
     */
    public PageResult<GoodsCategory> findPage(PageCriteria pageCriteria) {
        StringBuffer hql = new StringBuffer("From GoodsCategory gc WHERE gc.deleted = 0");
        return this.findPage(hql.toString(), pageCriteria, pageCriteria.getParams());
    }

}