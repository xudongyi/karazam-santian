package com.klzan.p2p.service.transfer;

import com.klzan.core.Result;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.TransferLoanState;
import com.klzan.p2p.model.*;
import com.klzan.p2p.vo.transfer.TransferVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 转让
 *
 * @author
 */
public interface TransferService extends IBaseService<Transfer> {

    /**
     * 列表 (转让中)
     *
     * @param borrowingId
     * @return
     */
    List<Transfer> findList(Integer borrowingId);

    /**
     * 列表 (转让中)
     *
     * @param borrowingId
     * @return
     */
    List<Transfer> findList(Integer borrowingId, Integer userId);

    /**
     * 列表（首页）
     *
     * @param pageCriteria 分页信息
     * @return
     */
    PageResult<Transfer> findPage(PageCriteria pageCriteria);

    PageResult<Transfer> findPage(PageCriteria pageCriteria, Map params);

    /**
     * 列表（首页）
     *
     * @param pageCriteria 分页信息
     * @param state
     * @return
     */
    PageResult<Transfer> findPage(PageCriteria pageCriteria, TransferLoanState state, Integer userId);

    /**
     * 列表（账务中心）
     *
     * @param pageCriteria
     * @param userId
     * @return
     */
    PageResult<TransferVo> findPageVo(PageCriteria pageCriteria, Integer userId);

    /**
     * 已承接债权列表
     *
     * @param pageCriteria
     * @param userId
     * @return
     */
    PageResult<TransferVo> findBuyInPageVo(PageCriteria pageCriteria, Integer userId);

    /**
     * 转出
     *
     * @param borrowing
     * @param currentUser
     */
    void transferOut(Borrowing borrowing, User currentUser);

    /**
     * 转出撤销
     *
     * @param transfer
     */
    void transferCancel(Transfer transfer);

    /**
     * 转入
     *
     * @param transfer
     * @param investment
     * @param investmentRecord
     * @param currentUser
     * @param parts
     */
    String transferIn(Transfer transfer, Investment investment, InvestmentRecord investmentRecord, User currentUser, Integer parts);

    /**
     *
     */
    Result transferInSucceed(String sn, Transfer transfer, User currentUser, Integer parts);

    /**
     *
     */
    void transferInSettlementSucceed(PaymentOrder paymentOrder);

    /**
     * 按条件分页查询
     *
     * @param criteria
     * @return
     */
    PageResult findPageByMybatis(PageCriteria criteria, HttpServletRequest request);

    /**
     * 根据转让人ID 查询债转的投资记录
     *
     * @param userId
     * @return
     */
    List<TransferVo> findTransferInvestment(Integer userId, Integer borrowingId);

    List<TransferVo> countAmount(Integer investId);
}
