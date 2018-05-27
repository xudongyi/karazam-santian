/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.dao.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.p2p.model.UserFinance;
import com.klzan.p2p.vo.capital.UserFinanceVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

/**
 * Created by suhao Date: 2016/11/15 Time: 18:13
 *
 * @version: 1.0
 */
@Repository
public class UserFinanceDao extends DaoSupport<UserFinance> {

    public UserFinanceVo getUserFinance(Integer userId) {
        StringBuilder sql = getBasicSql();
        sql.append("where uf.user_id=?0 ");
        return this.findUniqueBySQL(sql.toString(), getScalarCallback(), userId);
    }

    public UserFinance getByUserId(Integer userId) {
        StringBuffer hql = new StringBuffer("From UserFinance b WHERE deleted = 0  AND b.userId = ?0 ");
        return findUnique(hql.toString(), userId);
    }

    public PageResult<UserFinanceVo> findPage(PageCriteria criteria) {
        StringBuilder sql = getBasicSql();
        return this.findPageBySQL(sql.toString(), criteria, getScalarCallback(),criteria.getParams());
    }
    public PageResult<UserFinanceVo> findUserFinancePage(PageCriteria criteria) {
        StringBuilder sql = getBasicSql();
        return this.findPageBySQL(sql.toString(), criteria, getScalarCallback(),criteria.getParams());
    }
    private StringBuilder getBasicSql() {
        StringBuilder sql = new StringBuilder("select ");
        sql.append("uf.id id, ");
        sql.append("uf.user_id userId, ");
        sql.append("ifnull(uf.borrowing_amts,0) borrowingAmts, ");
        sql.append("ifnull(uf.investment_amts,0) investmentAmts, ");
        sql.append("ifnull(uf.credit,0) credit, ");
        sql.append("ifnull(uf.debit,0) debit, ");
        sql.append("ifnull(uf.experience,0) experience, ");
        sql.append("ifnull(uf.recharge_amts,0) rechargeAmts, ");
        sql.append("ifnull(uf.withdrawal_amts,0) withdrawalAmts, ");
        sql.append("ifnull(uf.balance,0) balance, ");
        sql.append("ifnull(uf.frozen,0) frozen,");
        sql.append("u.login_name userName,");
        sql.append("u.mobile mobile,");
        sql.append("u.create_date createDate,");
        sql.append("ui.real_name realName,");
        sql.append("ui.id_no idNo,");
        sql.append("u.type typeStr,");
        sql.append("ips.cur_bal as curBal,");
        sql.append("ips.avail_bal as availBal,");
        sql.append("ips.freeze_bal as freezeBal,");
        sql.append("ips.repayment_bal as repaymentBal,");
        sql.append("ips.query_date as queryDate ");
        sql.append("from karazam_user_finance uf ");
        sql.append("left join karazam_user u on uf.user_id=u.id ");
        sql.append("left join karazam_user_info ui on uf.user_id=ui.user_id ");
        sql.append("left join karazam_user_ips_info ips on uf.user_id=ips.user_id");
        return sql;
    }

    private ScalarAliasCallback<UserFinanceVo> getScalarCallback() {
        return new ScalarAliasCallback<UserFinanceVo>() {
            @Override
            protected Class<UserFinanceVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("userId", StandardBasicTypes.INTEGER);
                query.addScalar("borrowingAmts", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("investmentAmts", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("credit", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("debit", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("experience", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("rechargeAmts", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("withdrawalAmts", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("balance", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("frozen", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("userName", StandardBasicTypes.STRING);
                query.addScalar("mobile", StandardBasicTypes.STRING);
                query.addScalar("createDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("realName", StandardBasicTypes.STRING);
                query.addScalar("idNo", StandardBasicTypes.STRING);
                query.addScalar("typeStr", StandardBasicTypes.STRING);
                query.addScalar("curBal", StandardBasicTypes.STRING);
                query.addScalar("availBal", StandardBasicTypes.STRING);
                query.addScalar("freezeBal", StandardBasicTypes.STRING);
                query.addScalar("repaymentBal", StandardBasicTypes.STRING);
                query.addScalar("queryDate", StandardBasicTypes.TIMESTAMP);
                return UserFinanceVo.class;
            }
        };
    }

}
