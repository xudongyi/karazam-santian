/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.BorrowingExtra;
import com.klzan.p2p.vo.borrowing.BorrowingExtraVo;

import java.util.List;

/**
 * 借款项目附加信息
 */
public interface BorrowingExtraService extends IBaseService<BorrowingExtra> {

    void addExtra(Integer borrowingId, List<BorrowingExtraVo> extras);

    void updateExtra(Integer borrowingId, List<BorrowingExtraVo> extras);

    List<BorrowingExtraVo> findByBorrowing(Integer borrowingId);
}
