package com.klzan.p2p.service.coupon;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.CouponSource;
import com.klzan.p2p.model.Coupon;
import com.klzan.p2p.vo.coupon.CouponVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 优惠券
 * @author
 *
 */
public interface CouponService extends IBaseService<Coupon> {

    /**
     * 分页查询优惠券
     * @param criteria
     * @param request
     * @return
     */
    PageResult<CouponVo> findAllCouponPage(PageCriteria criteria,HttpServletRequest request);

    List findCouponBySource(CouponSource couponSource);

    Boolean canCreate(Coupon coupon);
}
