package com.klzan.p2p.service.borrowing;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.*;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.CpcnSettlement;
import com.klzan.p2p.model.PaymentOrder;
import com.klzan.p2p.vo.borrowing.BorrowingSimpleVo;
import com.klzan.p2p.vo.borrowing.BorrowingVo;

import java.math.BigInteger;
import java.util.List;

/**
 * 借款
 * @author zhu
 *
 */
public interface BorrowingService extends IBaseService<Borrowing> {

	/**
	 * 列表
	 */
	List<Borrowing> findAll();

	/**
	 * 借款列表
	 * @param pageCriteria 分页信息，
	 * @param progress 进度
	 * @return
	 */
	PageResult<Borrowing> findBorrowingList(PageCriteria pageCriteria, BorrowingProgress progress, Boolean isFailure);

	/**
	 * 借款列表（前台）
	 * @param pageCriteria 分页信息，
	 * @param progress 进度
	 * @param borrowingType
	 * @return
	 */
	PageResult<Borrowing> findList(PageCriteria pageCriteria, BorrowingProgress progress, PeriodScope scope, InterestRateScope rate, BorrowingType borrowingType, Boolean isRecommend);

	/**
	 * 借款列表（前台）
	 * @param pageCriteria 分页信息
	 * @return
	 */
	PageResult<Borrowing> findList(PageCriteria pageCriteria,int borrower,String state);

	/**
	 * 根据Id查询一个完整的借款详情
	 * @param id 借款信息唯一标示
	 * @return BorrowingVo 返回一条具体的借款信息
	 */
	List<BorrowingVo> findSingleBorrowById(Integer id);

	/**
	 * 申请
	 * @param vo
	 */
	Borrowing apply(BorrowingVo vo, String prepareState, String opinion) throws Exception;

	/**
	 * 修改
	 * @param vo
	 */
	void update(BorrowingVo vo, String opinion);

	/**
	 * 调查
	 * @param borrowing 借款
	 * @param state 状态
	 * @param opinion 意见
	 */
	void inquiry(BorrowingVo vo, Borrowing borrowing, BorrowingCheckState state, String opinion);

	/**
	 * 审批
	 * @param borrowing 借款
	 * @param state 状态
	 * @param opinion 意见
	 */
	void approval(BorrowingVo vo, Borrowing borrowing, BorrowingCheckState state, String opinion);

	/**
	 * 出借
	 * @param borrowing 借款
	 * @param state 状态
	 * @param opinion 意见
	 */
	void lending(Borrowing borrowing, BorrowingCheckState state, String opinion, Boolean noticeBorrower, Boolean noticeInvestor) throws Exception;

	/**
	 * 出借
	 * @param borrowing 借款
	 * @param state 状态
	 * @param opinion 意见
	 */
	Result lendingPay(Borrowing borrowing, BorrowingCheckState state, String opinion, Boolean noticeBorrower, Boolean noticeInvestor) throws Exception;

	/**
	 * 出借成功
	 */
	void lendingSucceed(CpcnSettlement cpcnSettlement) throws Exception;

	/**
	 * 流标
	 */
	Result failureBidSettlement(Borrowing borrowing, String opinion) ;

	/**
	 * 流标结算成功
	 */
	void failureBidSettlementSucceed(PaymentOrder order) ;

	/**
	 * 流标结算成功
	 */
	void failureBidSettlementAllSucceed(CpcnSettlement cpcnSettlement) ;

	/**
	 * 根据类型查询列表
	 * @param type
	 * @return
	 */
	Integer findList(BorrowingType type);

	/**
	 * 查询推荐项目
	 * @return
	 */
    List<BorrowingSimpleVo> findHotProjects();

	/**
	 * 根据类型查询分页
	 * @param criteria
	 * @return
	 */
	PageResult<BorrowingSimpleVo> findPage(PageCriteria criteria);

	List<Borrowing> findByBorrowerId(Integer userId);

	BigInteger countTargetByType(String type);

	Borrowing findByProjectNo(String projectNo);
}
