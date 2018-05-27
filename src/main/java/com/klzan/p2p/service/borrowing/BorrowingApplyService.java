package com.klzan.p2p.service.borrowing;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.BorrowingApplyProgress;
import com.klzan.p2p.model.BorrowingApply;
import com.klzan.p2p.vo.borrowing.BorrowingApplyVo;

import java.util.List;
import java.util.Map;

/**
 * 借款
 * @author zhu
 *
 */
public interface BorrowingApplyService extends IBaseService<BorrowingApply> {

    /**
     * 根据 条件分页查询申请列表
     * @param criteria
     * @param vo
     * @return
     */
    public PageResult<BorrowingApplyVo> findBorrowingApplyByPage(PageCriteria criteria, BorrowingApplyVo vo);

    /**
     * 根据申请进度查询申请列表
     * @param progress
     * @return
     */
    public List<BorrowingApplyVo> findListByProgress(BorrowingApplyProgress progress);
}
