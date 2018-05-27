package com.klzan.p2p.service.coupon.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.coupon.UserCouponDao;
import com.klzan.p2p.enums.CouponSource;
import com.klzan.p2p.enums.CouponState;
import com.klzan.p2p.enums.PeriodUnit;
import com.klzan.p2p.model.User;
import com.klzan.p2p.model.UserCoupon;
import com.klzan.p2p.service.coupon.CouponService;
import com.klzan.p2p.service.coupon.UserCouponService;
import com.klzan.p2p.setting.CouponSetting;
import com.klzan.p2p.setting.SettingUtils;
import com.klzan.p2p.vo.coupon.CouponRule;
import com.klzan.p2p.vo.coupon.CouponVo;
import com.klzan.p2p.vo.coupon.UserCouponVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 优惠券-用户
 */
@Service
public class UserCouponServiceImpl extends BaseService<UserCoupon> implements UserCouponService {

    @Resource
    private CouponService couponService;
    @Resource
    private SettingUtils settingUtils;
    @Resource
    private UserCouponDao userCouponDao;

    @Override
    public PageResult<UserCouponVo> findAllCouponUserPage(PageCriteria criteria, HttpServletRequest request, String state, User user) {
        Map map = new HashMap();
        map.put("mobile",request.getParameter("mobile"));
        map.put("realName",request.getParameter("realName"));
        map.put("state",state);
        map.put("userId",user==null?null:user.getId());
        return myDaoSupport.findPage("com.klzan.p2p.mapper.UserCouponMapper.findUserCoupons", map, criteria);
    }

    @Override
    public List findUserCoupon(Integer userId,CouponState couponState) {
        Map map = new HashMap();
        map.put("userId",userId);
        map.put("couponState",couponState.toString());
        return myDaoSupport.findList("com.klzan.p2p.mapper.UserCouponMapper.findUserCoupons", map);
    }

    @Override
    public List<UserCoupon> findUserCoupon(Integer userId, CouponSource couponSource) {
        return userCouponDao.findUserCoupon(userId, couponSource);
    }

    @Override
    public Map createUserCoupon(CouponSource couponSource, Integer userId) {

        Map<String, Object> map = new HashMap();

        CouponSetting couponSetting = settingUtils.getCouponSetting();
        if (null != couponSetting && couponSetting.getCouponEnable()) {

            List<CouponVo> couponBySource = couponService.findCouponBySource(couponSource);
            if (couponBySource == null || couponBySource.size() == 0){
                return map;
            }
            CouponVo couponVo = null;
            for (CouponVo vo : couponBySource) {
                if (vo.getAvailable() && vo.getInvalidDate().after(new Date())) {
                    couponVo = vo;
                    break;
                }
            }
            if (couponVo == null) {
                map.put("false", "新增优惠券失败，未找到可用的优惠券种类");
                return map;
            }
            List<UserCoupon> userCouponByCoupons = userCouponDao.findUserCouponByCoupon(couponVo.getId());
            if(new Integer(couponVo.getCouponNumber()).intValue() <= userCouponByCoupons.size()){
                map.put("false", "新增优惠券失败，该类优惠券已达到限制数量");
                return map;
            } else {
                UserCoupon userCoupon = new UserCoupon();
                userCoupon.setCoupon(couponVo.getId());
                userCoupon.setUser(userId);
                userCoupon.setCouponState(CouponState.UNUSED);
                if (couponVo.getIsRandomAmount()) {
                    Random random = new Random();
                    int couponAmount = random.nextInt(couponVo.getRandomAmountMax()) % (couponVo.getRandomAmountMax() - couponVo.getRandomAmountMix() + 1)
                            + couponVo.getRandomAmountMix();
                    userCoupon.setAmount(new BigDecimal(couponAmount));
                }else {
                    userCoupon.setAmount(couponVo.getAmount());
                }
                userCoupon.setTitle("("+userCoupon.getAmount().toString()+"元)"+couponSource.getDisplayName());
                CouponRule couponRule = JsonUtils.toObject(couponVo.getRule(), CouponRule.class);
                if (couponVo.getAvailableByCategory()){
                    userCoupon.setInvalidDate(couponVo.getInvalidDate());
                }else {
                    if (couponRule.getPeriodUnit() == PeriodUnit.DAY){
                        userCoupon.setInvalidDate(DateUtils.addDays(new Date(),couponRule.getTerm()));
                    }else {
                        userCoupon.setInvalidDate(DateUtils.addMonths(new Date(),couponRule.getTerm()));
                    }
                }
                userCoupon.setRule(couponVo.getRule());
                userCouponDao.merge(userCoupon);
                map.put("true", "新增优惠券成功");
                map.put("amount", userCoupon.getAmount());
            }
        }
        return map;
    }


}
