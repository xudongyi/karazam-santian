/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing;

import com.klzan.p2p.model.BorrowingOpinion;

import java.util.List;

/**
 * 借款意见
 * @author chenxinglin
 *
 */
public interface BorrowingOpinionService {

	/**
	 * 创建
	 * @param opinion 借款具体信息值对象
	 */
	BorrowingOpinion create(BorrowingOpinion opinion);

	/**
	 * 列表
	 * @param borrowingID 借款ID
	 * @return
	 */
	List<BorrowingOpinion> findList(Integer borrowingID);


}
