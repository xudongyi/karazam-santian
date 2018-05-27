/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.enums.BorrowingApplyProgress;
import com.klzan.p2p.model.BorrowingApply;
import com.klzan.p2p.service.borrowing.BorrowingApplyService;
import com.klzan.p2p.vo.borrowing.BorrowingApplyVo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借款
 * zhu
 */
@Service
public class BorrowingApplyServiceImpl extends BaseService<BorrowingApply> implements BorrowingApplyService {

    @Override
    public PageResult<BorrowingApplyVo> findBorrowingApplyByPage(PageCriteria criteria, BorrowingApplyVo vo) {
        Map map = new HashMap();
        map.put("mobile",vo.getMobile());
        map.put("userName",vo.getUserName());
        map.put("borrowingApplyProgressEnum",vo.getBorrowingApplyProgressEnum()==null?null:vo.getBorrowingApplyProgressEnum().toString());
        map.put("borrowingApplyTypeEnum",vo.getBorrowingApplyTypeEnum()==null?null:vo.getBorrowingApplyTypeEnum().toString());
        map.put("paramList",criteria.getParams());
        return myDaoSupport.findPage("com.klzan.p2p.mapper.BorrowingApplyMapper.pageList", map, criteria);
    }

    @Override
    public List<BorrowingApplyVo> findListByProgress(BorrowingApplyProgress progress){
        Map map = new HashMap();
        map.put("borrowingApplyProgressEnum",progress==null?null:progress.toString());
        return myDaoSupport.findList("com.klzan.p2p.mapper.BorrowingApplyMapper.pageList",map);
    }
}