/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.BorrowingExtra;
import com.klzan.p2p.model.BorrowingExtraDetail;
import com.klzan.p2p.vo.borrowing.BorrowingExtraDetailVo;

import java.util.List;

/**
 * 借款项目附加详细信息
 */
public interface BorrowingExtraDetailService extends IBaseService<BorrowingExtraDetail> {

    void addExtraDetail(BorrowingExtra extra, List<BorrowingExtraDetailVo> details);

    void deleteByExtra(Integer extraId);

    List<BorrowingExtraDetailVo> findByExtra(Integer extraId);
}
