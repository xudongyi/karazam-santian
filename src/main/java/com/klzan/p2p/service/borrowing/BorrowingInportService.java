package com.klzan.p2p.service.borrowing;

import com.klzan.core.Result;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.vo.borrowing.BorrowingImportVo;

import java.util.List;

/**
 * 项目批量导入
 */
public interface BorrowingInportService extends IBaseService<Borrowing> {

	/**
	 * 导入
	 */
	Boolean importOne(BorrowingImportVo vo);

	/**
	 * 批量导入
	 */
	Result importBatch(List<BorrowingImportVo> vos);

}
