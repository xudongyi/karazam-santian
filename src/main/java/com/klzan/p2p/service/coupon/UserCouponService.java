package com.klzan.p2p.service.coupon;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.CouponSource;
import com.klzan.p2p.enums.CouponState;
import com.klzan.p2p.model.Coupon;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserCoupon;
import com.klzan.p2p.vo.coupon.UserCouponVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 优惠券-用户
 * @author
 *
 */
public interface UserCouponService extends IBaseService<UserCoupon> {

    /**
     * 分页查询优惠券
     * @param criteria
     * @param request
     * @return
     */
    PageResult<UserCouponVo> findAllCouponUserPage(PageCriteria criteria, HttpServletRequest request, String state, User user);

    /**
     * 查询用户优惠券
     * @param userId
     * @param couponState
     * @return
     */
    List findUserCoupon(Integer userId,CouponState couponState);

    /**
     * 查询用户优惠券
     * @param userId
     * @param couponSource
     * @return
     */
    List<UserCoupon> findUserCoupon(Integer userId,CouponSource couponSource);

    Map createUserCoupon(CouponSource couponSource, Integer userId);
}
