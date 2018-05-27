package com.klzan.p2p.dao.coupon;

import com.klzan.core.persist.DaoSupport;
import com.klzan.core.util.DateUtils;
import com.klzan.p2p.enums.CouponSource;
import com.klzan.p2p.model.UserCoupon;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhu on 2016/12/7.
 */

@Repository
public class UserCouponDao extends DaoSupport<UserCoupon> {

    public List<UserCoupon> findUserCouponByCoupon(Integer couponId){
        String sql = "select * from karazam_user_coupon where coupon = ?0";
        return this.findBySQL(sql, UserCoupon.class, couponId);
    }

    public List<UserCoupon> findUserCoupon(Integer userId, CouponSource couponSource){
        StringBuffer hql = new StringBuffer("SELECT uc From UserCoupon uc,Coupon c where c.id=uc.coupon ");
        Map param = new HashMap();
        if (null != userId) {
            hql.append(" AND uc.user=:userId ");
            param.put("userId", userId);
        }
        if (null != couponSource) {
            hql.append("AND c.couponSource=:couponSource ");
            param.put("couponSource", couponSource);
        }
        hql.append(" ORDER BY uc.createDate desc ");
        return this.find(hql.toString(), param);
    }

}
