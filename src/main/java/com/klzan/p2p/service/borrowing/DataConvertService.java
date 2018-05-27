/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing;

import com.klzan.p2p.model.*;

import java.util.List;

/**
 * 数据转换
 * @author: chenxinglin  Date: 2017-2-10
 */
public interface DataConvertService {

    /**
     * 借款转换
     * @param borrowing
     * @return
     */
    Borrowing convertBorrowing(Borrowing borrowing);

    /**
     * 借款转换
     * @param borrowings
     * @return
     */
    List<Borrowing> convertBorrowings(List<Borrowing> borrowings);

    /**
     * 投资转换
     * @param investment
     * @return
     */
    Investment convertInvestment(Investment investment);

    /**
     * 投资转换
     * @param investments
     * @return
     */
    List<Investment> convertInvestments(List<Investment> investments);

    /**
     * 投资记录转换
     * @param investmentRecords
     * @return
     */
    List<InvestmentRecord> convertInvestmentRecords(List<InvestmentRecord> investmentRecords);

    /**
     * 还款转换
     * @param repayments
     * @return
     */
    List<Repayment> convertRepayments(List<Repayment> repayments);

    /**
     * 回款计划转换
     * @param repaymentPlans
     * @return
     */
    List<RepaymentPlan> convertRepaymentPlans(List<RepaymentPlan> repaymentPlans);

}
