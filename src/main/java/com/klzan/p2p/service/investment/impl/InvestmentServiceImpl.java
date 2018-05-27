package com.klzan.p2p.service.investment.impl;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.lock.DistributeLock;
import com.klzan.core.lock.LockStack;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.SnUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.dao.capital.CapitalDao;
import com.klzan.p2p.dao.investment.InvestmentDao;
import com.klzan.p2p.dao.investment.InvestmentRecordDao;
import com.klzan.p2p.dao.user.UserDao;
import com.klzan.p2p.dao.user.UserFinanceDao;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.*;
import com.klzan.p2p.service.borrowing.BorrowingNoticeService;
import com.klzan.p2p.service.capital.PaymentOrderService;
import com.klzan.p2p.service.coupon.UserCouponService;
import com.klzan.p2p.service.investment.InvestmentService;
import com.klzan.p2p.service.order.OrderService;
import com.klzan.p2p.service.user.UserAutoInvestmentRankService;
import com.klzan.p2p.util.CommonUtils;
import com.klzan.p2p.vo.investment.InvestVo;
import com.klzan.p2p.vo.investment.InvestmentVo;
import com.klzan.p2p.vo.investment.WaitingProfitInvestmentVo;
import org.hibernate.LockMode;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("investmentService")
public class InvestmentServiceImpl extends BaseService<Investment> implements InvestmentService {

	@Inject
	private InvestmentDao investmentDao;
	@Inject
	private InvestmentRecordDao investmentRecordDao;
	@Inject
	private BorrowingDao borrowingDao;
	@Inject
	private UserDao userDao;
	@Inject
	private UserFinanceDao userFinanceDao;
	@Inject
	private CapitalDao capitalDao;
    @Inject
    private PaymentOrderService paymentOrderService;
	@Inject
	private OrderService orderService;
	@Inject
	private UserAutoInvestmentRankService userAutoInvestmentRankService;
	@Inject
	private UserCouponService userCouponService;
	@Inject
	private BorrowingNoticeService borrowingNoticeService;
	@Inject
	private DistributeLock distributeLock;

	@Override
	public PageResult<Investment> findList(PageCriteria pageCriteria){
		return investmentDao.findList(pageCriteria);
	}

	@Override
	public PageResult<InvestmentVo> findInvestmentList(PageCriteria pageCriteria, Boolean isTransfer, Date startDate, Date endDate) {
		PageResult<InvestmentVo> pageResult = investmentDao.findPageListPage(pageCriteria, isTransfer, startDate, endDate);
		return pageResult;
	}
	@Override
	public PageResult<InvestmentVo> findInvestmentListMobile(PageCriteria pageCriteria, Boolean isTransfer, Date startDate, Date endDate,BorrowingProgress ... progresses) {
		PageResult<InvestmentVo> pageResult = investmentDao.findPageListPageMobile(pageCriteria, isTransfer, startDate, endDate, progresses);
		return pageResult;
	}

	@Override
	public List countInvest(String realName, String mobile, Date startDate, Date endDate) {
		return investmentDao.countInvest(realName, mobile, startDate, endDate);
	}

	@Override
	public List<Investment> findList(Integer borrowingId) {
		return investmentDao.findList(borrowingId);
	}

    @Override
    public List<Investment> findList(Integer borrowingId, InvestmentState state) {
        return investmentDao.findList(borrowingId, state);
    }

    @Override
	public List<Investment> findListByTransfer(Integer transferId) {
		return investmentDao.findListByTransfer(transferId);
	}

	@Override
	public List<InvestmentVo> findInvestmentById(Integer id) {
		return null;
	}

	@Override
	public BigDecimal countInvest() {
		return investmentDao.countInvest();
	}


    @Override
    public PageResult<InvestmentVo> findByUserId(Integer userId, PageCriteria criteria) {
        return investmentDao.findByUserId(userId, criteria);
    }

    @Override
    public List<WaitingProfitInvestmentVo> findWaitingProfitInvestByUserId(Integer userId, Integer listSize) {
        return investmentDao.findWaitingProfitInvestByUserId(userId, listSize);
    }

    @Override
    public List<Investment> findListByUserId(Integer userId) {
		return investmentDao.findListByUserId(userId);
    }

	@Override
	public synchronized void invest(String orderNo) {
		try {
			distributeLock.lock(LockStack.INVESTMENT_LOCK, orderNo);
			//更新投资记录状态
			InvestmentRecord investmentRecord = investmentRecordDao.findByOrderNo(orderNo);
			if (investmentRecord.getState() == InvestmentState.PAID) {
                logger.error("订单号[{}]已付款", orderNo);
                return;
            }
//			Order order = orderService.getByBusinessId(OrderType.INVESTMENT, investmentRecord.getId());
			Order order = orderService.updateOrderStatus(OrderType.INVESTMENT, investmentRecord.getId(), OrderStatus.FROZEN, orderNo);

			// TODO 参数
			Integer investorId = investmentRecord.getInvestor();
			BigDecimal amount = investmentRecord.getAmount();  //金额
			BigDecimal preferentialAmount = BigDecimal.ZERO; //优惠码金额

			Borrowing borrowing = borrowingDao.get(investmentRecord.getBorrowing().intValue());
//		borrowing.subtractOccupyAmount(amount);
//		borrowing.subtractOccupyCount();

			Investment pInvestment = investmentDao.find(borrowing.getId(), investorId, false, InvestmentState.INVESTING);
			// TODO 投资
			if (pInvestment != null) {// TODO 继续投资
				investmentDao.lock(pInvestment, LockMode.PESSIMISTIC_WRITE);
			}else {// TODO 初始投资
				pInvestment = new Investment();
				pInvestment.setPreferentialAmount(preferentialAmount);
				pInvestment.setBorrowing(borrowing.getId());
				pInvestment.setInvestor(investorId);
				pInvestment.setOrderNo(SnUtils.getOrderNo());
				investmentDao.persist(pInvestment);
			}

			pInvestment.addAmount(amount);
			pInvestment.addPreferentialAmount(preferentialAmount);
			pInvestment.setState(InvestmentState.INVESTING);
			investmentRecord.setState(InvestmentState.PAID);
			investmentRecord.setInvestment(pInvestment.getId());
			// 如果是自动投标，更新投资序号
			if (investmentRecord.getOperationMethod() == OperationMethod.AUTO) {
				UserAutoInvestmentRank rank = userAutoInvestmentRankService.findByUserId(investmentRecord.getInvestor());
				if (amount.compareTo(rank.getResidualAmount()) != 0) {
					rank.updatePartInvest(amount);
				} else {
					rank.updateFullInvest();
				}
				userAutoInvestmentRankService.merge(rank);
			}
			investmentDao.merge(pInvestment);
			investmentRecordDao.merge(investmentRecord);

			// TODO 项目校验
			if (!(borrowing != null && borrowing.getType() != BorrowingType.TRANSFER
                    && borrowing.getCanInvest()
                    && borrowing.getState() == BorrowingState.WAIT)) {
                throw new BusinessProcessException("项目校验失败");
            }

			// TODO 投资人、
			User investor = userDao.get(investorId);

			// TODO 参数
			if (investmentRecord.getPreferentialAmount()!=null){
                preferentialAmount = investmentRecord.getPreferentialAmount();
            }
			// TODO 验证借款
			if (!borrowing.verifyInvest()) {
                throw new BusinessProcessException("项目投资校验失败");
            }
//		if (!borrowing.verifyInvest(amount)) {
//			throw new RuntimeException();
//		}

			// TODO 投资人资金冻结
			userFinanceDao.flush();
			UserFinance investorFinance = userFinanceDao.getByUserId(investor.getId());
			if (amount.compareTo(BigDecimal.ZERO) > 0) {
                investorFinance.addFrozen(amount);
                investorFinance.addBalance(preferentialAmount, RechargeBusinessType.GENERAL);
                userFinanceDao.update(investorFinance);
            }

			// TODO 投资人投资资金记录
			if (amount.compareTo(BigDecimal.ZERO) > 0) {
    //			Order order = orderService.addOrMergeOrder(new OrderVo());
                Capital capital = new Capital(investmentRecord.getInvestor(),
                        CapitalMethod.INVESTMENT,
                        CapitalType.FROZEN,
                        amount,
                        investorFinance,
                        investmentRecord.getOrderNo(),
                        CommonUtils.getLoginName(),
                        CommonUtils.getRemoteIp(),
                        "投资人投资冻结",
                        order.getId());
                capitalDao.persist(capital);
            }

			// TODO 更新借款
			borrowing.addInvestedAmount(amount);
			if (borrowing.getIsInvestFull()) {
                borrowing.setProgress(BorrowingProgress.LENDING);
                borrowing.setState(BorrowingState.WAIT);
                borrowing.setInvestmentFinishDate(new Date());
                // 满标通知
                borrowingNoticeService.full(borrowing);
            }
			borrowingDao.merge(borrowing);
			userFinanceDao.flush();
		} finally {
			distributeLock.unLock(LockStack.INVESTMENT_LOCK, orderNo);
		}
	}

	@Override
	public InvestmentRecord genInvestAndInvestmentRecord(InvestVo vo){
		// 标的
		Borrowing borrowing = borrowingDao.get(vo.getProjectId().intValue());
		// 投资人
		User investor = userDao.get(vo.getInvestor());
		BigDecimal preferentialAmount = BigDecimal.ZERO; //优惠码金额
		if (vo.getPreferentialAmount()!=null){
			preferentialAmount = vo.getPreferentialAmount();
		}
		// TODO 生成投资记录
		InvestmentRecord investmentRecord = new InvestmentRecord();
		investmentRecord.setOperationMethod(vo.getOperationMethod());
		investmentRecord.setMethod(vo.getPaymentMethod());
		investmentRecord.setAmount(vo.getAmount());
		investmentRecord.setPreferentialAmount(preferentialAmount);
		if (vo.getCoupon()!=null){
			investmentRecord.setCouponCode(vo.getCoupon());
		}
		investmentRecord.setBorrowing(borrowing.getId());
		investmentRecord.setInvestor(investor.getId());
		investmentRecord.setOrderNo(vo.getSn());
		investmentRecord.setOperator(CommonUtils.getLoginName());
		investmentRecord.setIp(CommonUtils.getRemoteIp());
		investmentRecord.setDeviceType(vo.getDeviceType());
		investmentRecord.setState(InvestmentState.INVESTING);
//		if (hasCoupon){
//			InvestmentRecord investmentRecord2 = new InvestmentRecord();
//			investmentRecord2.setOperationMethod(vo.getOperationMethod());
//			investmentRecord2.setMethod(vo.getPaymentMethod());
//			investmentRecord2.setAmount(preferentialAmount);
//			investmentRecord2.setPreferentialAmount(BigDecimal.ZERO);
//			if (vo.getCoupon()!=null){
//				investmentRecord2.setCouponCode(vo.getCoupon());
//			}
//			investmentRecord2.setBorrowing(borrowing.getId());
//			investmentRecord2.setInvestor(investor.getId());
//			UserCoupon userCoupon = userCouponService.get(vo.getCoupon());
//			investmentRecord2.setOrderNo(userCoupon.getOrderNo());
//			investmentRecord2.setOperator(CommonUtils.getLoginName());
//			investmentRecord2.setIp(CommonUtils.getRemoteIp());
//			investmentRecord2.setDeviceType(vo.getDeviceType());
//			investmentRecord2.setState(InvestmentState.INVESTING);
//			investmentRecordDao.persist(investmentRecord2);
//		}
		return investmentRecordDao.persist(investmentRecord);
	}

    @Override
	public List<Investment> findInvestingByUserId(Integer userId) {
		return investmentDao.findInvestingByUserId(userId);
	}

	@Override
	public PageResult<Investment> findPage(PageCriteria criteria, User currentUser, BorrowingProgress progress) {
		PageResult<Investment> page = investmentDao.findPage(criteria, currentUser, progress);
		return page;
	}

	@Override
	public Integer countInvestmentDeadline(Integer begin,Integer end,String uint){
		return investmentDao.countInvestmentDeadline(begin,end,uint);
	}
	@Override
	public Integer countInvestmentAmount(BigDecimal begin,BigDecimal end){
		return investmentDao.countInvestmentAmount(begin,end);
	}
}
