package com.klzan.p2p.service.investment;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.BorrowingProgress;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.model.Investment;
import com.klzan.p2p.model.InvestmentRecord;
import com.klzan.p2p.model.User;
import com.klzan.p2p.vo.investment.InvestVo;
import com.klzan.p2p.vo.investment.InvestmentVo;
import com.klzan.p2p.vo.investment.WaitingProfitInvestmentVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 投资
 * @author zhu
 *
 */
public interface InvestmentService extends IBaseService<Investment> {

	/**
	 *
	 * 投资列表
	 * @param pageCriteria 分页信息
	 * @return
	 */
	PageResult<Investment> findList(PageCriteria pageCriteria);
	
	/**
	 * 分页查询投资列表
	 * @param  pageCriteria
	 * @return
	 */
	PageResult<InvestmentVo> findInvestmentList(PageCriteria pageCriteria, Boolean isTransfer, Date startDate, Date endDate);

	/**
	 *
	 * mobile分页查询投资列表
	 */
	PageResult<InvestmentVo> findInvestmentListMobile(PageCriteria pageCriteria, Boolean isTransfer, Date startDate, Date endDate,BorrowingProgress ... progresses );
	/**
	 * 投资统计
	 * @return
	 */
	List countInvest(String realName, String mobile, Date startDate, Date endDate);

	/**
	 * 列表
	 */
	List<Investment> findList(Integer borrowingId);

	/**
	 * 根据状态查询列表
	 * @param borrowingId
	 * @param state
	 * @return
	 */
	List<Investment> findList(Integer borrowingId, InvestmentState state);

	/**
	 * 列表
	 */
	List<Investment> findListByTransfer(Integer transferId);

	/**
	 * 通过Id查询投资信息
	 * @param id 投资信息唯一标示
	 * @return 返回一个长度为1的List
	 */
	List<InvestmentVo> findInvestmentById(Integer id);

	BigDecimal countInvest();

	/**
	 * 根据用户ID查询分页
	 * @param criteria
	 * @param userId
	 * @return
	 */
	PageResult<InvestmentVo> findByUserId(Integer userId, PageCriteria criteria);

	/**
	 * 根据用户ID查询待收投资
	 * @param userId
	 * @param listSize
	 * @return
	 */
	List<WaitingProfitInvestmentVo> findWaitingProfitInvestByUserId(Integer userId, Integer listSize);

	/**
	 * 根据用户ID查询投资记录
	 * @param userId
	 * @return
	 */
	List<Investment> findListByUserId(Integer userId);

	/**
	 * 投资
	 *
	 * @param orderNo
	 *            项目投资Bean
	 * @throws
	 */
	void invest(String orderNo);

	/**
	 * 查询状态为投资中的投资
	 * @param userId
	 * @return
	 */
    List<Investment> findInvestingByUserId(Integer userId);

	/**
	 * 用户投资分页列表
	 * @param criteria
	 * @param currentUser
	 * @return
	 */
	PageResult<Investment> findPage(PageCriteria criteria, User currentUser, BorrowingProgress progress);

	/**
	 * 投资期限统计
	 * @param begin
	 * @param end
	 * @param uint
	 * @return
	 */
	Integer countInvestmentDeadline(Integer begin, Integer end, String uint);

	/**
	 * 投资金额统计
	 * @param begin
	 * @param end
	 * @return
	 */
	Integer countInvestmentAmount(BigDecimal begin, BigDecimal end);


	InvestmentRecord genInvestAndInvestmentRecord(InvestVo investVo);

//	/**
//	 * 投资借款
//	 *
//	 * @param borrowing
//	 *            借款
//	 * @param investor
//	 *            投资人
////	 * @param couponCode
////	 *            优惠码
//	 * @param amount
//	 *            金额
//	 * @param operationMethod
//	 *            操作方式
//	 * @param payMethod
//	 *            支付方式
//	 * @param sn
//	 *            流水号
//	 * @param operator
//	 *            操作员
//	 * @param ip
//	 *            IP
//	 * @throws Exception
//	 *             投资失败的异常
//	 */
//	void invest(Borrowing borrowing, User investor, BigDecimal amount,
//				OperationMethod operationMethod, PayMethod payMethod, String sn, String operator, String ip) throws Exception;
}
