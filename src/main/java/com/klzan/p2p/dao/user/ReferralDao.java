package com.klzan.p2p.dao.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.p2p.model.Referral;
import com.klzan.p2p.vo.user.ReferralVo;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReferralDao extends DaoSupport<Referral> {

    public PageResult<ReferralVo> findPageListPage(PageCriteria pageCriteria) {
        String sql = "select a.id as id,"
                + "a.user_Id as userId,"
                + "a.re_User_Id as reUserId,"
                + "a.referral_Fee_Rate as referralFeeRate,"
                + "a.available as available,"
                + "b.name as userNickName,"
                + "b.mobile as userMobile, "
                + "c.name as reUserNickName,"
                + "a.create_date as createDate,"
                + "c.mobile as reUserMobile "
                + "from karazam_referral a left join karazam_user b on a.user_Id = b.id left join karazam_user c on a.re_user_id=c.id "
                + "where a.deleted=false";
        return this.findPageBySQL(sql, pageCriteria, new ScalarAliasCallback<ReferralVo>() {
            @Override
            protected Class<ReferralVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("userId", StandardBasicTypes.INTEGER);
                query.addScalar("reUserId", StandardBasicTypes.INTEGER);
                query.addScalar("referralFeeRate", StandardBasicTypes.BIG_DECIMAL);
                query.addScalar("available", StandardBasicTypes.BOOLEAN);
                query.addScalar("userNickName", StandardBasicTypes.STRING);
                query.addScalar("userMobile", StandardBasicTypes.STRING);
                query.addScalar("reUserNickName", StandardBasicTypes.STRING);
                query.addScalar("createDate", StandardBasicTypes.TIMESTAMP);
                query.addScalar("reUserMobile", StandardBasicTypes.STRING);
                return ReferralVo.class;
            }
        },pageCriteria.getParams());
    }

    public void createReferral(Referral referral){
        this.persist(referral);
    }

    /**
     * 获取指定推荐人的所有推荐关系
     * @param id
     * @return
     */
    public List<Referral> getListById(int id){
        String sql = "select * from karazam_referral where deleted=0 and user_id = ?0";
        return this.findBySQL(sql, Referral.class, id);
    }

    public List<Referral> findByUserId(Integer userId) {
        String hql = "FROM Referral WHERE deleted=0 AND userId=?0 ";
        return find(hql, userId);
    }

    public List<Referral> findByReUserId(Integer reUserId) {
        String hql = "FROM Referral WHERE deleted=0 AND reUserId=?0 ";
        return find(hql, reUserId);
    }

}
