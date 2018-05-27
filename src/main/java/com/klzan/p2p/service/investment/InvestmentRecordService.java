package com.klzan.p2p.service.investment;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.model.InvestmentRecord;
import com.klzan.p2p.vo.investment.InvestmentRecordSimpleVo;
import com.klzan.p2p.vo.investment.InvestmentRecordVo;
import com.klzan.p2p.vo.investment.InvestmentSourceVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 投资记录
 * @author zhu
 *
 */
public interface InvestmentRecordService extends IBaseService<InvestmentRecord> {
	
	/**
	 * 分页查询投资记录
	 * @param pageCriteria 分页信息,vo封装查询条件的值对象
	 * @return
	 */
	PageResult<InvestmentRecordVo> findInvestmentRecordList(PageCriteria pageCriteria, Date startDate, Date endDate);
	/**
	 * 新增投资记录
	 * @param vo 投资记录具体信息值对象
	 */
	void createInvestmentRecord(InvestmentRecordVo vo);
	/**
	 * 通过ID删除投资记录
	 * @param id 投资记录唯一标示
	 */
	void deleteInvestmentRecordById(Integer id);

	/**
	 * 列表
	 */
	List<InvestmentRecord> findList(Integer borrowingId);

	/**
	 * 列表查投资成功
	 */
	List<InvestmentRecord> findListSuccess(Integer borrowingId);
	/**
	 * 根据投资人ID查询投资列表
	 * @param userId
	 * @return
	 */
	List<InvestmentRecord> findListByUserId(Integer userId);
	/**
	 * 根据状态列表
	 * @param borrowingId
	 * @return
	 */
	List<InvestmentRecord> findList(Integer borrowingId, InvestmentState state);

	/**
	 * 根据状态列表
	 * @param borrowingId
	 * @param states
	 * @return
	 */
	List<InvestmentRecord> findList(Integer borrowingId, Boolean isTransfer, InvestmentState... states);

	/**
	 * 列表
	 */
	List<InvestmentRecord> findListByInvestment(Integer investmentId);

	/**
	 * 查询投资记录
	 * @param investmentId
	 * @param state
	 * @return
	 */
	List<InvestmentRecord> findListByInvestment(Integer investmentId, InvestmentState state);

	/**
	 * 查询投资记录
	 * @param projectId
	 * @param state
	 * @param criteria
	 * @return
	 */
    PageResult<InvestmentRecordSimpleVo> findPage(Integer projectId, InvestmentState state, PageCriteria criteria);

    PageResult<InvestmentRecordSimpleVo> findPage(Integer projectId, PageCriteria criteria, Boolean isTransfer, InvestmentState... state);

    PageResult<InvestmentRecordSimpleVo> findPage(PageCriteria criteria, Map params, InvestmentState... state);

	/**
	 * 投资记录
	 * @param orderNo
	 * @return
	 */
	InvestmentRecord findByOrderNo(String orderNo);

	/**
	 * 统计投资来源
	 * @return
	 */
	InvestmentSourceVo countSource();

	/**
	 * 投资统计
	 * @return
	 */
	List countInvest(String realName, String mobile, Date startDate, Date endDate);

	/**
	 * 根据投资获取投资记录
	 * @param investmentId
	 * @return
	 */
    InvestmentRecord getRecordByInvestment(Integer investmentId);
}
