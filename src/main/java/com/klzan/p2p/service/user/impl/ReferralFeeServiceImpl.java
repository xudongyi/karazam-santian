package com.klzan.p2p.service.user.impl;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.exception.SystemException;
import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.SnUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.user.ReferralDao;
import com.klzan.p2p.dao.user.ReferralFeeDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.capital.CapitalService;
import com.klzan.p2p.service.capital.PlatformCapitalService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.user.ReferralFeeService;
import com.klzan.p2p.service.user.UserFinanceService;
import com.klzan.p2p.service.user.UserService;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.p2p.vo.order.OrderVo;
import com.klzan.p2p.vo.user.ReferralFeeVo;
import com.klzan.p2p.vo.user.UserVo;
import com.klzan.plugin.pay.common.PayPortal;
import com.klzan.plugin.pay.common.dto.OrgTransferRequest;
import com.klzan.plugin.pay.common.dto.Response;
import com.klzan.plugin.pay.common.module.PayModule;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ReferralFeeServiceImpl extends BaseService<ReferralFee> implements ReferralFeeService {

    @Autowired
    private ReferralFeeDao referralFeeDao;
    @Autowired
    private ReferralDao referralDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserFinanceService userFinanceService;
    @Autowired
    private CapitalService capitalService;
    @Autowired
    private PlatformCapitalService platformCapitalService;
    @Autowired
    private OrderService orderService;
    @Autowired
    protected DistributeLock lock;

    @Override
    public PageResult<ReferralFeeVo> findReferralFeeList(PageCriteria pageCriteria, Integer id) {
        return referralFeeDao.findPageListPage(pageCriteria, id);
    }
    @Override
    public PageResult<ReferralFeeVo> findReferralFeeAndReferralList(PageCriteria pageCriteria, Integer id) {
        return referralFeeDao.findPageReferralListPage(pageCriteria,id);
    }

    @Override
    public void applyAuditing(Integer[] ids) {
        // TODO Auto-generated method stub
        ReferralFee referralFee = null;
        for (Integer id : ids) {
            referralFee = referralFeeDao.get(id);
            referralFee.setState(ReferralFeeState.APPLYING);
            referralFeeDao.merge(referralFee);
        }
    }

    @Override
    public void offlinepaid(Integer[] ids) {
        // TODO Auto-generated method stub
        ReferralFee referralFee = null;
        for (Integer id : ids) {
            referralFee = referralFeeDao.get(id);
            referralFee.setState(ReferralFeeState.OFFLINE_PAID);
            Subject subject = SecurityUtils.getSubject();
            String operator = "";
            if (subject != null) {
                operator = subject.getPrincipal().toString();
            }
            referralFee.setMemo("用户:'" + operator + "'设置为线下已结算");
            referralFee.setPaymentDate(new Date());
            referralFeeDao.merge(referralFee);
        }
    }

    @Override
    public ReferralFee findByOrderNo(String orderNo) {
        return referralFeeDao.findByOrderNo(orderNo);
    }

    @Override
    public List<ReferralFee> findByBatchOrderNo(String batchOrderNo) {
        return referralFeeDao.findByBatchOrderNo(batchOrderNo);
    }

    @Override
    public void auditing(Integer referralFeeId, String suggestion) {
        ReferralFee referralFee = referralFeeDao.get(referralFeeId);
        Referral referral = referralDao.get(referralFee.getReferralId());
        Integer userId = referral.getUserId();
        UserVo user = userService.getUserById(userId);
        if (!user.hasPayAccount()){
            throw new SystemException("推荐人未开通托管账户");
        }
        // 生成订单号
        String orderNo = SnUtils.getOrderNo();

        PayModule payModule = PayPortal.org_transfer.getModuleInstance();
        OrgTransferRequest req = new OrgTransferRequest();
        req.setUserId(userId);
        req.setAmount(referralFee.getReferralFee());
        req.setRemark("推荐费结算");
        payModule.setSn(orderNo);
        payModule.setRequest(req);
        Response response = payModule.invoking().getResponse();
        if (response.isSuccess()) {
            //状态设置为 结算中
            referralFee.setState(ReferralFeeState.PAYING);
            //更新订单号
            referralFee.setOrderNo(orderNo);
            //备注
            referralFee.setMemo(suggestion);
            referralFeeDao.merge(referralFee);

            UserVo userVo = userService.getUserById(userId);
            UserFinance userFinance = userFinanceService.findByUserId(userId);

            orderService.addOrMergeOrder(new OrderVo(
                    userId,
                    BigDecimal.ZERO,
                    userFinance.getAvailable(),
                    -3,
                    "平台",
                    userId,
                    userVo.getRealName(),
                    OrderStatus.LAUNCH,
                    OrderType.REFERRAL,
                    OrderMethod.CPCN,
                    referralFee.getId(),
                    null,
                    referralFee.getOrderNo(),
                    null,
                    referralFee.getReferralFee(),
                    referralFee.getReferralFee(),
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    referralFee.getPaymentDate(),
                    "推荐费结算",
                    CommonUtils.getLoginName(),
                    CommonUtils.getRemoteIp()
            ));
        } else {
            throw new BusinessProcessException("推荐费结算异常：" + response.getMsg());
        }

    }

    @Override
    public void transfer(Integer referralFeeId) {
        ReferralFee referralFee = referralFeeDao.get(referralFeeId);
        try {
            lock.lock(LockStack.USER_LOCK, "REFERRAL_FEE_TRANSFER" + referralFee.getOrderNo());
            if (referralFee.getState() == ReferralFeeState.PAID) {
                logger.warn("推荐费[{}]已结算");
                return;
            }
            Referral referral = referralDao.get(referralFee.getReferralId());
            Integer userId = referral.getUserId();
            //状态设置为 已结算
            referralFee.setState(ReferralFeeState.PAID);
            //实际结算时间
            referralFee.setPaymentDate(new Date());
            referralFeeDao.merge(referralFee);
            UserFinance userFinance = userFinanceService.findByUserId(userId);
            userFinance.addBalance(referralFee.getReferralFee(), RechargeBusinessType.GENERAL);
            userFinanceService.update(userFinance);
//        userFinanceService.flush();
            Order order = orderService.getByBusinessId(OrderType.REFERRAL, referralFee.getId());
            //1.用户计一笔收入
            Capital capital = new Capital(userFinance.getUserId(),
                    CapitalMethod.REFERRAL_FEE,
                    CapitalType.CREDIT,
                    referralFee.getReferralFee(),
                    userFinance,
                    referralFee.getOrderNo(),
                    CommonUtils.getLoginName(),
                    CommonUtils.getRemoteIp(),
                    "推荐费收入",
                    order.getId());
            capitalService.persist(capital);
            //2.平台计一笔推荐费支出
            PlatformCapital platformCapital = new PlatformCapital(CapitalType.DEBIT, CapitalMethod.REFERRAL_FEE, new BigDecimal(0), referralFee.getReferralFee(),
                    capital.getId(), "推荐费支出", CommonUtils.getLoginName(), CommonUtils.getRemoteIp(), referralFee.getOrderNo());
            platformCapitalService.persist(platformCapital);
        } finally {
            lock.unLock(LockStack.USER_LOCK, "REFERRAL_FEE_TRANSFER" + referralFee.getOrderNo());
        }
    }

    @Override
    public BigDecimal getWillSettleReferralFee(List<Integer> list) {
        return referralFeeDao.getWillSettleReferralFee(list);
    }

    @Override
    public List<ReferralFee> alreadySettlement(Integer userId) {
        return referralFeeDao.alreadySettlement(userId);
    }

}
