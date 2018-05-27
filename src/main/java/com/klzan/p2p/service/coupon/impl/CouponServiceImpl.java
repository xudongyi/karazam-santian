package com.klzan.p2p.service.coupon.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.enums.CouponSource;
import com.klzan.p2p.model.Coupon;
import com.klzan.p2p.service.coupon.CouponService;
import com.klzan.p2p.vo.coupon.CouponVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券
 */
@Service
public class CouponServiceImpl extends BaseService<Coupon> implements CouponService {

    @Override
    public PageResult<CouponVo> findAllCouponPage(PageCriteria criteria,HttpServletRequest request) {
        Map map = new HashMap();
        map.put("type",request.getParameter("type"));
        return myDaoSupport.findPage("com.klzan.p2p.mapper.CouponMapper.findCoupons", map, criteria);
    }

    @Override
    public List findCouponBySource(CouponSource couponSource) {
        Map map = new HashMap();
        map.put("couponSource",couponSource.toString());
        return myDaoSupport.findList("com.klzan.p2p.mapper.CouponMapper.findCoupons", map);
    }

    @Override
    public Boolean canCreate(Coupon coupon){
        List<CouponVo> couponBySource = findCouponBySource(coupon.getCouponSource());
        for (CouponVo couponVo:couponBySource){
            if (couponVo.getAvailable() && couponVo.getInvalidDate().after(new Date())) {
                return false;
            }
        }
        return true;
    }
}
