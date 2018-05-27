package com.klzan.p2p.service.user.impl;

import com.klzan.core.util.SnUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.point.PointRecordDao;
import com.klzan.p2p.dao.user.UserPointDao;
import com.klzan.p2p.enums.CouponSource;
import com.klzan.p2p.enums.GoodsShippingStatus;
import com.klzan.p2p.enums.PointMethod;
import com.klzan.p2p.enums.PointType;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.coupon.UserCouponService;
import com.klzan.p2p.service.goods.GoodsOrderService;
import com.klzan.p2p.service.user.UserPointService;
import com.klzan.p2p.setting.PointSetting;
import com.klzan.p2p.setting.SettingUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.Date;
import java.util.Map;

/**
 * 用户积分
 * @author: chenxinglin
 */
@Service
@Transactional
public class UserPointServiceImpl extends BaseService<UserPoint> implements UserPointService {

    @Inject
    private UserPointDao userPointDao;

    @Inject
    private PointRecordDao pointRecordDao;

    @Inject
    private GoodsOrderService goodsOrderService;

    @Inject
    private UserCouponService userCouponService;

    @Inject
    private SettingUtils settingUtils;

    @Override
    public UserPoint findByUserId(Integer userId) {
        UserPoint userPoint = userPointDao.findByUserId(userId);
        if(userPoint == null){
            userPoint = new UserPoint();
            userPoint.setUserId(userId);
            userPoint = userPointDao.persist(userPoint);
            userPointDao.refresh(userPoint);
        }
        return userPoint;
    }

    @Override
    public void regist(Integer userId) {
        PointSetting pointSetting = settingUtils.getPointSetting();
        if(pointSetting != null && pointSetting.getSignInEnable()!=null && pointSetting.getRegistEnable()){
            if(pointSetting.getRegistPoints()!=null && pointSetting.getRegistPoints()>0){
                // 更新用户积分
                UserPoint userPoint = this.findByUserId(userId);
                userPoint.addPoint(pointSetting.getRegistPoints());
                userPoint.addCredits(pointSetting.getRegistPoints());
                userPointDao.merge(userPoint);

                // 积分记录
                PointRecord pointRecord = new PointRecord();
                pointRecord.setUserId(userPoint.getUserId());
                pointRecord.setOrderNo(SnUtils.getOrderNo());
                pointRecord.setMethod(PointMethod.regist);
                pointRecord.setType(PointType.credit);
                pointRecord.setPoint(pointSetting.getRegistPoints());
                pointRecord.setMemo("注册送积分");
                pointRecordDao.persist(pointRecord);
            }
        }
    }

    @Override
    public void referral(Integer referralId, Integer referrerId) {
        PointSetting pointSetting = settingUtils.getPointSetting();
        if(pointSetting != null && pointSetting.getSignInEnable()!=null && pointSetting.getReferralEnable()){
            if(pointSetting.getReferralPoints()!=null && pointSetting.getReferralPoints()>0) {
                // 更新被推荐人积分
                UserPoint userPoint = this.findByUserId(referralId);
                userPoint.addPoint(pointSetting.getReferralPoints());
                userPoint.addCredits(pointSetting.getReferralPoints());
                userPointDao.merge(userPoint);

                // 积分记录
                PointRecord pointRecord = new PointRecord();
                pointRecord.setUserId(userPoint.getUserId());
                pointRecord.setOrderNo(SnUtils.getOrderNo());
                pointRecord.setMethod(PointMethod.referral);
                pointRecord.setType(PointType.credit);
                pointRecord.setPoint(pointSetting.getReferralPoints());
                pointRecord.setMemo("被推荐人积分");
                pointRecordDao.persist(pointRecord);
            }
            if(pointSetting.getReferrerPoints()!=null && pointSetting.getReferrerPoints()>0) {
                // 更新推荐人积分
                UserPoint userPoint = this.findByUserId(referrerId);
                userPoint.addPoint(pointSetting.getReferrerPoints());
                userPoint.addCredits(pointSetting.getReferrerPoints());
                userPointDao.merge(userPoint);

                // 积分记录
                PointRecord pointRecord = new PointRecord();
                pointRecord.setUserId(userPoint.getUserId());
                pointRecord.setOrderNo(SnUtils.getOrderNo());
                pointRecord.setMethod(PointMethod.referrer);
                pointRecord.setType(PointType.credit);
                pointRecord.setPoint(pointSetting.getReferrerPoints());
                pointRecord.setMemo("推荐人积分");
                pointRecordDao.persist(pointRecord);
            }
        }
    }

    @Override
    public Object[] signIn(Integer userId) {

        Object[] obj = new Object[]{0, 0, 0};

        UserPoint userPoint = this.findByUserId(userId);
        userPoint.signIn();
        obj[2]=userPoint.getConSignInCount();
        //TODO 签到送红包
        if(userPoint.getTodaySignIn() && userPoint.getConSignInCountCal()!=0 && userPoint.getConSignInCountCal()%7==0){
            Map map = userCouponService.createUserCoupon(CouponSource.SIGN_IN, userId);
            obj[1] = map.get("amount");
        }

        //TODO 签到送积分
        PointSetting pointSetting = settingUtils.getPointSetting();
        if(pointSetting != null && pointSetting.getSignInEnable()!=null && pointSetting.getSignInEnable()){
            if(pointSetting.getSignInPoints()!=null && pointSetting.getSignInPoints()>0) {
                // 更新用户积分
                userPoint.addPoint(pointSetting.getSignInPoints());
                userPoint.addCredits(pointSetting.getSignInPoints());
                userPointDao.merge(userPoint);
                obj[0] = pointSetting.getSignInPoints();

                // 积分记录
                PointRecord pointRecord = new PointRecord();
                pointRecord.setUserId(userPoint.getUserId());
                pointRecord.setOrderNo(SnUtils.getOrderNo());
                pointRecord.setMethod(PointMethod.sign_in);
                pointRecord.setType(PointType.credit);
                pointRecord.setPoint(pointSetting.getSignInPoints());
                pointRecord.setMemo("签到送积分");
                pointRecordDao.persist(pointRecord);
            }
        }
        return obj;
    }

    @Override
    public void invest(Investment investment) {
        PointSetting pointSetting = settingUtils.getPointSetting();
        if(pointSetting != null && pointSetting.getRepayEnable()!=null && pointSetting.getInvestEnable()){
            // 更新用户积分
            UserPoint userPoint = this.findByUserId(investment.getInvestor());
            userPoint.addPoint(investment.getAmount().intValue());
            userPoint.addCredits(investment.getAmount().intValue());
            userPointDao.merge(userPoint);

            // 积分记录
            PointRecord pointRecord = new PointRecord();
            pointRecord.setUserId(userPoint.getUserId());
            pointRecord.setOrderNo(investment.getOrderNo());
            pointRecord.setMethod(PointMethod.invest);
            pointRecord.setType(PointType.credit);
            pointRecord.setPoint(investment.getAmount().intValue());
            pointRecord.setMemo("投资送积分");
            pointRecordDao.persist(pointRecord);
        }
    }

    @Override
    public void repayment(RepaymentPlan repaymentPlan) {
        PointSetting pointSetting = settingUtils.getPointSetting();
        if(pointSetting != null && pointSetting.getRepayEnable()!=null && pointSetting.getRepayEnable() && repaymentPlan.getRepaymentRecord().getCapital().intValue()>0){
            // 更新用户积分
            UserPoint userPoint = this.findByUserId(repaymentPlan.getInvestor());
            userPoint.addPoint(repaymentPlan.getRepaymentRecord().getCapital().intValue());
            userPoint.addCredits(repaymentPlan.getRepaymentRecord().getCapital().intValue());
            userPointDao.merge(userPoint);

            // 积分记录
            PointRecord pointRecord = new PointRecord();
            pointRecord.setUserId(userPoint.getUserId());
            pointRecord.setOrderNo(repaymentPlan.getOrderNo());
            pointRecord.setMethod(PointMethod.repayment);
            pointRecord.setType(PointType.credit);
            pointRecord.setPoint(repaymentPlan.getRepaymentRecord().getCapital().intValue());
            pointRecord.setMemo("回款送积分");
            pointRecordDao.persist(pointRecord);
        }
    }

    @Override
    public void exchange(Integer userId, Integer count) {
        if(count>0){
            // 更新用户积分
            UserPoint userPoint = this.findByUserId(userId);
            userPoint.subtractPoint(count);
            userPoint.addDebits(count);
            userPointDao.merge(userPoint);

            // 积分记录
            PointRecord pointRecord = new PointRecord();
            pointRecord.setUserId(userPoint.getUserId());
            pointRecord.setOrderNo(SnUtils.getOrderNo());
            pointRecord.setMethod(PointMethod.exchange);
            pointRecord.setType(PointType.debit);
            pointRecord.setPoint(count);
            pointRecord.setMemo("商品兑换");
            pointRecordDao.persist(pointRecord);
        }
    }

    @Override
    public void exchangeCancel(GoodsOrder goodsOrder) {
//        GoodsOrder goodsOrder = goodsOrderService.get(orderId);
//        if(goodsOrder == null || !goodsOrder.getLogisticsStatus().equals(GoodsShippingStatus.unshipped)){
//            throw new RuntimeException("参数错误");
//        }

        // 更新用户积分
        UserPoint userPoint = this.findByUserId(goodsOrder.getUserId());
        userPoint.addPoint(goodsOrder.getPoint());
        userPoint.addCredits(goodsOrder.getPoint());
        userPointDao.merge(userPoint);

        // 积分记录
        PointRecord pointRecord = new PointRecord();
        pointRecord.setUserId(userPoint.getUserId());
        pointRecord.setOrderNo(SnUtils.getOrderNo());
        pointRecord.setMethod(PointMethod.exchange_cancel);
        pointRecord.setType(PointType.credit);
        pointRecord.setPoint(goodsOrder.getPoint());
        pointRecord.setMemo("商品订单撤销");
        pointRecordDao.persist(pointRecord);
    }

}
