package com.klzan.p2p.service.borrowing;

import com.klzan.p2p.model.Borrowing;

import java.util.List;
import java.util.Map;

/**
 * 借款通知
 *
 */
public interface BorrowingNoticeService{

    /**
     * 满标
     */
    public void full(Borrowing borrowing);

    /**
     * 出借/放款  还款人
     */
    public void lendingToBorrower(Borrowing borrowing, Map map);

    /**
     * 出借/放款  投资人
     */
    public void lendingToInvestors(Borrowing borrowing, List<Map> investors);

    /**
     * 还款
     */
    public void repayment(Integer borrowingId, Integer repaymentId, Integer count, List<Map> investors);

    /**
     * 提前还款
     */
    public void repaymentAdvance(Integer borrowingId, Integer count, List<Map> investors);

}
