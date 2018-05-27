package com.klzan.p2p.dao.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.core.util.StringUtils;
import com.klzan.p2p.model.Referral;
import com.klzan.p2p.model.ReferralFee;
import com.klzan.p2p.vo.user.ReferralFeeVo;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReferralFeeDao extends DaoSupport<ReferralFee> {

    @Inject
    private ReferralDao referralDao;
	
	public ReferralFee findReferralFeeById(Integer id){
		return this.get(id);
	}
	
	public PageResult<ReferralFeeVo> findPageListPage(PageCriteria pageCriteria, Integer id) {
        String sql = "select a.id as id,"
        		+ "a.referral_Id as referralId,"
        		+ "a.state as stateStr,"
        		+ "a.referral_Amt as referralAmt,"
        		+ "a.referral_Fee_Rate as referralFeeRate,"
        		+ "a.referral_Fee as referralFee,"
        		+ "a.plan_Payment_Date as planPaymentDate,"
        		+ "a.payment_Date as paymentDate,"
        		+ "a.memo as memo,"
        		+ "a.operator as operator,"
        		+ "a.ip as ip,"
        		+ "a.borrowing as borrowing,"
        		+ "a.investment as investment,"
                + "b.user_id as userId,"
                + "b.re_user_id as reUserId,"
                + "c.name as userNickName,"
                + "c.mobile as userMobile,"
                + "d.name as reUserNickName,"
                + "d.mobile as reUserMobile "
        		+ "from karazam_referral_Fee a left join karazam_referral b on a.referral_Id=b.id "
                + "left join karazam_user c on b.user_id=c.id "
                + "left join karazam_user d on b.re_user_id=d.id "
                + "where a.deleted=false ";
        Map map = new HashMap();
        if(id!=null){
            sql = sql + "AND b.user_id =:user_id";
            map = new HashMap();
            map.put("user_id",id);
        }
        if (StringUtils.isBlank(pageCriteria.getSort())) {
            pageCriteria.setSort("a.id");
            pageCriteria.setOrder("desc");
        }
        return this.findPageBySQL(sql, pageCriteria, new ScalarAliasCallback<ReferralFeeVo>() {
            @Override
            protected Class<ReferralFeeVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("referralId", StandardBasicTypes.INTEGER);
                query.addScalar("stateStr", StandardBasicTypes.STRING);
                query.addScalar("referralAmt", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("referralFeeRate", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("referralFee", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("planPaymentDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("paymentDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("memo", StandardBasicTypes.STRING);
                query.addScalar("operator", StandardBasicTypes.STRING);
                query.addScalar("ip", StandardBasicTypes.STRING);
                query.addScalar("borrowing", StandardBasicTypes.INTEGER);
                query.addScalar("investment", StandardBasicTypes.INTEGER);
                query.addScalar("userId", StandardBasicTypes.INTEGER);
                query.addScalar("reUserId", StandardBasicTypes.INTEGER);
                query.addScalar("userNickName", StandardBasicTypes.STRING);
                query.addScalar("userMobile", StandardBasicTypes.STRING);
                query.addScalar("reUserNickName", StandardBasicTypes.STRING);
                query.addScalar("reUserMobile", StandardBasicTypes.STRING);
                return ReferralFeeVo.class;
            }
        },pageCriteria.getParams(),map);
    }
    /**
     * 获取推荐关系及其推荐费用-------------------------------------------
     * */

    public PageResult<ReferralFeeVo> findPageReferralListPage(PageCriteria pageCriteria, Integer id) {
        String sql = "select a.id as id,"
                + "b.referral_Id as referralId,"
                + "b.state as stateStr,"
                + "a.create_date as createDate,"
                + "b.referral_Amt as referralAmt,"
                + "b.referral_Fee_Rate as referralFeeRate,"
                + "b.referral_Fee as referralFee,"
                + "b.plan_Payment_Date as planPaymentDate,"
                + "b.payment_Date as paymentDate,"
                + "b.memo as memo,"
                + "b.operator as operator,"
                + "b.ip as ip,"
                + "b.borrowing as borrowing,"
                + "b.investment as investment,"
                + "a.user_id as userId,"
                + "a.re_user_id as reUserId,"
                + "c.name as userNickName,"
                + "c.mobile as userMobile,"
                + "d.name as reUserNickName,"
                + "d.mobile as reUserMobile "
                + "from karazam_referral a left join karazam_referral_Fee b on b.referral_Id=a.id "
                + "left join karazam_user c on a.user_id=c.id "
                + "left join karazam_user d on a.re_user_id=d.id "
                + "where a.deleted=false ";
        Map map = new HashMap();
        if(id!=null){
            sql = sql + "AND a.user_id =:user_id";
            map = new HashMap();
            map.put("user_id",id);
        }
        if (StringUtils.isBlank(pageCriteria.getSort())) {
            pageCriteria.setSort("a.create_date");
            pageCriteria.setOrder("desc");
        }
        return this.findPageBySQL(sql, pageCriteria, new ScalarAliasCallback<ReferralFeeVo>() {
            @Override
            protected Class<ReferralFeeVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("referralId", StandardBasicTypes.INTEGER);
                query.addScalar("stateStr", StandardBasicTypes.STRING);
                query.addScalar("createDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("referralAmt", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("referralFeeRate", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("referralFee", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("planPaymentDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("paymentDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("memo", StandardBasicTypes.STRING);
                query.addScalar("operator", StandardBasicTypes.STRING);
                query.addScalar("ip", StandardBasicTypes.STRING);
                query.addScalar("borrowing", StandardBasicTypes.INTEGER);
                query.addScalar("investment", StandardBasicTypes.INTEGER);
                query.addScalar("userId", StandardBasicTypes.INTEGER);
                query.addScalar("reUserId", StandardBasicTypes.INTEGER);
                query.addScalar("userNickName", StandardBasicTypes.STRING);
                query.addScalar("userMobile", StandardBasicTypes.STRING);
                query.addScalar("reUserNickName", StandardBasicTypes.STRING);
                query.addScalar("reUserMobile", StandardBasicTypes.STRING);
                return ReferralFeeVo.class;
            }
        },pageCriteria.getParams(),map);
    }



    //-----------------------------------------------------------------
    /**
     * 获取指定推荐关系待结算的推荐费
     * @param list
     * @return
     */
    public BigDecimal getWillSettleReferralFee(List<Integer> list){
        if (list.isEmpty()) {
            return BigDecimal.ZERO;
        }
        String sql = "select ifnull(sum(referral_fee), 0) From referral_fee a where a.deleted=0 AND state='wait_pay' AND a.referral_id in (:referral_id) ";
        Map map = new HashMap();
        map.put("referral_id",list);
        BigDecimal referralFee = (BigDecimal) this.findUniqueBySQL(sql, map);
        return referralFee == null ? BigDecimal.ZERO : referralFee;
    }

    public List<ReferralFee> alreadySettlement(Integer userId) {
        List<Referral> referrals = referralDao.findByUserId(userId);
        if (referrals.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> ids = new ArrayList<>();
        for (Referral referral : referrals) {
            ids.add(referral.getId());
        }
        String hql = "FROM ReferralFee WHERE deleted=0 AND state='PAID' AND referralId in (:referralIds)";
        Map<String, Object> map = new HashedMap();
        map.put("referralIds", ids);
        return find(hql, map);
    }

    public ReferralFee findByOrderNo(String orderNo) {
        StringBuffer hql = new StringBuffer("From ReferralFee c WHERE c.deleted = 0 AND c.orderNo=?0");
        return findUnique(hql.toString(), orderNo);
    }

    public List<ReferralFee> findByBatchOrderNo(String batchOrderNo) {
        StringBuffer hql = new StringBuffer("From ReferralFee c WHERE c.deleted = 0 AND c.batchOrderNo=?0");
        return find(hql.toString(), batchOrderNo);
    }
}
