/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.transfer;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.core.util.DateUtils;
import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.dao.postloan.RepaymentPlanDao;
import com.klzan.p2p.enums.PeriodUnit;
import com.klzan.p2p.enums.RepaymentState;
import com.klzan.p2p.enums.TransferLoanState;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.RepaymentPlan;
import com.klzan.p2p.model.Transfer;
import com.klzan.p2p.vo.transfer.TransferVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 转让
 */
@Repository
public class TransferDao extends DaoSupport<Transfer> {

    @Inject
    private BorrowingDao borrowingDao;

    @Inject
    private RepaymentPlanDao repaymentPlanDao;

    /**
     * 转让列表
     *
     * @param borrowingId
     * @return
     */
    public List<Transfer> findList(Integer borrowingId) {
        StringBuffer hql = new StringBuffer("From Transfer t WHERE t.deleted = 0 and t.state <> 'transfered' and t.state <> 'cancel' ");
        Map<String, Object> params = new HashMap();
        if (borrowingId != null) {
            hql.append(" AND t.borrowing = :borrowing ");
            params.put("borrowing", borrowingId);
        }
        return this.find(hql.toString(), params);
    }

    /**
     * 转让列表
     *
     * @param borrowingId
     * @return
     */
    public List<Transfer> findList(Integer borrowingId, Integer userId) {
        StringBuffer hql = new StringBuffer("From Transfer t WHERE t.deleted = 0 and t.state <> 'transfered' and t.state <> 'cancel' ");
        Map<String, Object> params = new HashMap();
        if (borrowingId != null) {
            hql.append(" AND t.borrowing = :borrowing ");
            params.put("borrowing", borrowingId);
        }
        if (borrowingId != null) {
            hql.append(" AND t.transfer = :userId ");
            params.put("userId", userId);
        }
        return this.find(hql.toString(), params);
    }

    /**
     * 转让列表
     *
     * @param pageCriteria
     * @return
     */
    public PageResult<Transfer> findPage(PageCriteria pageCriteria) {
        StringBuffer hql = new StringBuffer("From Transfer t WHERE t.deleted = 0 and t.state <> 'CANCEL' order by t.createDate desc");
        PageResult<Transfer> page = this.findPage(hql.toString(), pageCriteria, pageCriteria.getParams());
        for (Transfer transfer : page.getRows()) {
            Date begin = transfer.getCreateDate();
            Date nextRepaymentDate = null;
            Date lastRepaymentDate = null;
            Borrowing borrowing = borrowingDao.get(transfer.getBorrowing());
            List<RepaymentPlan> repaymentPlans = repaymentPlanDao.findList(transfer.getBorrowing(), transfer.getId());
            Integer residualPeriod = 0;
            for (RepaymentPlan repaymentPlan : repaymentPlans) {
                if (nextRepaymentDate == null || nextRepaymentDate.after(repaymentPlan.getRepaymentRecordPayDate())) {
                    nextRepaymentDate = repaymentPlan.getRepaymentRecordPayDate();
                }
                lastRepaymentDate = repaymentPlan.getRepaymentRecordPayDate();
                if (repaymentPlan.getState() == RepaymentState.REPAYING) {
                    residualPeriod++;
                }
                if (repaymentPlan.getPaidDate() != null
                        && DateUtils.compareTwoDate(repaymentPlan.getRepaymentRecordPayDate(), repaymentPlan.getPaidDate()) == 1) {
                    begin = DateUtils.addDays(repaymentPlan.getRepaymentRecordPayDate(), 1);
                }
            }
            if (borrowing.getPeriodUnit() == PeriodUnit.DAY) {
                residualPeriod = new Double(DateUtils.getDaysOfTwoDate(lastRepaymentDate, begin)).intValue();
            }
            transfer.setResidualPeriod(residualPeriod);
            transfer.setResidualUnit(borrowing.getPeriodUnitDes());
            transfer.setNextRepaymentDate(nextRepaymentDate);
            transfer.setRepaymentMethod(borrowing.getRepaymentMethod());
        }
        return page;
    }

    /**
     * 转让列表
     *
     * @param pageCriteria
     * @return
     */
    public PageResult<Transfer> findPage(PageCriteria pageCriteria, TransferLoanState state, Integer userId) {
        StringBuffer hql = new StringBuffer("From Transfer t WHERE t.deleted = 0 ");
        Map<String, Object> params = new HashMap();
        if (userId != null) {
            hql.append(" AND t.transfer = :userId ");
            params.put("userId", userId);
        }
        if (state != null) {
            if (state == TransferLoanState.TRANSFERING) {
                hql.append(" AND (t.state = 'TRANSFERING' OR t.state = 'TRANSFERPART') ");
            } else {
                hql.append(" AND t.state = :state ");
                params.put("state", state);
            }
        }

        return this.findPage(hql.toString(), pageCriteria, pageCriteria.getParams(), params);
    }

    /**
     * 分页查询债权
     *
     * @param pageCriteria
     * @return
     */
    public PageResult<TransferVo> findPage(PageCriteria pageCriteria, Integer userId, Boolean isRepaying) {
        String sql = "select DISTINCT "
                + " b.id as borrowingId,"
                + " b.title as title, "
                + " r.transfer as transfer "
                + " FROM karazam_borrowing b "
                + " left join karazam_repayment_plan r on r.borrowing = b.id "
                + " left join karazam_user_info u on r.investor = u.user_id "
                + " WHERE b.deleted = false ";
        if (userId != null) {
            sql += " AND r.investor = " + userId.intValue();
        }
        if (isRepaying != null && isRepaying) {
            sql += " AND b.progress = 'REPAYING' ";
        }
        sql += " AND r.transfer_state <> 'TRANSFER_OUT' AND r.transfer_state <> 'TRANSFER_IN' AND r.transfer_state <> 'TRANSFERING' ";
        pageCriteria.setSortName("b.id");
        pageCriteria.setOrder("desc");
        return this.findPageBySQL(sql, pageCriteria, new ScalarAliasCallback<TransferVo>() {
            @Override
            protected Class<TransferVo> doAddScalar(NativeQuery query) {
                query.addScalar("borrowingId", StandardBasicTypes.INTEGER);
                query.addScalar("title", StandardBasicTypes.STRING);
                query.addScalar("transfer", StandardBasicTypes.INTEGER);
                return TransferVo.class;
            }
        }, pageCriteria.getParams());
    }

}