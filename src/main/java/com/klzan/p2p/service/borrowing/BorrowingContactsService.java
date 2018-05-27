/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.BorrowingContactsType;
import com.klzan.p2p.model.BorrowingContacts;
import com.klzan.p2p.vo.borrowing.BorrowingContactsVo;

import java.util.Date;
import java.util.List;

/**
 * 借款联系人
 * @author zhu
 *
 */
public interface BorrowingContactsService extends IBaseService<BorrowingContacts>{

	/**
	 * 列表分页查询
	 * @param pageCriteria 分页信息
	 * @return PageResult<BorrowingContacts>
	 */
	PageResult<BorrowingContacts> findList(PageCriteria pageCriteria, BorrowingContactsType filter_type, String filter_LIKES_name,
										   String filter_LIKES_mobile, String filter_LIKES_telephone, Date filter_createDateStart, Date filter_createDateEnd);

	/**
	 * 申请
	 * @param vo
	 */
	void create(BorrowingContactsVo vo);

	/**
	 * 修改
	 * @param vo
	 */
	void update(BorrowingContactsVo vo);

	/**
	 * 列表
	 * @param borrowingID 借款ID
	 * @return
	 */
	List<BorrowingContacts> findList(Integer borrowingID);

}
