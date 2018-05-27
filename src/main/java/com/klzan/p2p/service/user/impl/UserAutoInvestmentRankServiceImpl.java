package com.klzan.p2p.service.user.impl;

import com.klzan.core.exception.BusinessProcessException;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.util.DateUtils;
import com.klzan.core.util.JsonUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingDao;
import com.klzan.p2p.dao.user.UserAutoInvestmentRankDao;
import com.klzan.p2p.mapper.UserAutoInvestmentRankMapper;
import com.klzan.p2p.model.Borrowing;
import com.klzan.p2p.model.UserAutoInvestmentRank;
import com.klzan.p2p.service.user.UserAutoInvestmentRankService;
import com.klzan.p2p.vo.user.UserAutoInvestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by suhao Date: 2017/5/26 Time: 12:54
 *
 * @version: 1.0
 */
@Service
public class UserAutoInvestmentRankServiceImpl extends BaseService<UserAutoInvestmentRank> implements UserAutoInvestmentRankService {

    @Autowired
    private UserAutoInvestmentRankDao userAutoInvestmentRankDao;

    @Autowired
    private BorrowingDao borrowingDao;

    @Autowired
    private UserAutoInvestmentRankMapper userAutoInvestmentRankMapper;

    @Override
    public void addOrMergeRank(UserAutoInvestVo autoInvestVo) {
        Integer userId = autoInvestVo.getUserId();
        UserAutoInvestmentRank rank = userAutoInvestmentRankDao.findByUserId(userId);
        if (null == rank) {
            rank = new UserAutoInvestmentRank(
                    userId,
                    autoInvestVo.getValidity(),
                    autoInvestVo.getInvestMinAmount(),
                    autoInvestVo.getInvestMaxAmount(),
                    autoInvestVo.getProjectMinCyc(),
                    autoInvestVo.getProjectMaxCyc(),
                    autoInvestVo.getInterestRateMinRate(),
                    autoInvestVo.getInterestRateMaxRate()
            );
            this.persist(rank);
        } else {
            rank.updateReSign(
                    autoInvestVo.getValidity(),
                    autoInvestVo.getInvestMinAmount(),
                    autoInvestVo.getInvestMaxAmount(),
                    autoInvestVo.getProjectMinCyc(),
                    autoInvestVo.getProjectMaxCyc(),
                    autoInvestVo.getInterestRateMinRate(),
                    autoInvestVo.getInterestRateMaxRate()
            );
            this.merge(rank);
        }
    }

    @Override
    public List<UserAutoInvestmentRank> findEffectiveList(Integer borrowingId) {
        Borrowing borrowing = borrowingDao.get(borrowingId);
        if (null == borrowing) {
            throw new BusinessProcessException("项目不存在");
        }
        Map params = new HashMap();
        params.put("interestRate", borrowing.getRealInterestRate());
//        params.put("projectAmount", borrowing.getAmount());
        Integer projectCyc = 0;
        switch (borrowing.getPeriodUnit()) {
            case MONTH: {
                projectCyc = borrowing.getPeriod() * 30;
                break;
            }
            case DAY: {
                projectCyc = borrowing.getPeriod();
                break;
            }

            default: {
                throw new BusinessProcessException("项目期限单位错误");
            }
        }
        if (null == projectCyc || projectCyc <= 0) {
            throw new BusinessProcessException("项目期限单位错误");
        }
        params.put("projectCyc", projectCyc);
        params.put("openStatus", true);
        params.put("now", DateUtils.format(new Date(), DateUtils.DATE_PATTERN_yyyyMMddHHmm));
        logger.info("标的{}自动投标查询条件：{}", borrowingId, JsonUtils.toJson(params));
        return userAutoInvestmentRankMapper.findEffectiveList(params);
    }

    @Override
    public UserAutoInvestmentRank findByUserId(Integer userId) {
        return userAutoInvestmentRankDao.findByUserId(userId);
    }

    @Override
    public void updateOpenStatus(Integer userId, boolean openStatus) {
        UserAutoInvestmentRank rank = findByUserId(userId);
        rank.updateOpenStatus(openStatus);
        this.merge(rank);
    }

    @Override
    public Integer findHasSign() {
        Map params = new HashMap();
        params.put("openStatus", true);
        List<UserAutoInvestmentRank> effectiveList = userAutoInvestmentRankMapper.findEffectiveList(params);
        return effectiveList.size();
    }

    @Override
    public Integer findEffectiveSign() {
        Map params = new HashMap();
        params.put("openStatus", true);
        params.put("now", DateUtils.format(new Date(), DateUtils.DATE_PATTERN_yyyyMMddHHmm));
        List<UserAutoInvestmentRank> effectiveList = userAutoInvestmentRankMapper.findEffectiveList(params);
        return effectiveList.size();
    }

    @Override
    public Integer getUserRank(Integer userId) {
        Map params = new HashMap();
        params.put("openStatus", true);
        params.put("now", DateUtils.format(new Date(), DateUtils.DATE_PATTERN_yyyyMMddHHmm));
        List<UserAutoInvestmentRank> effectiveList = userAutoInvestmentRankMapper.findEffectiveList(params);
        UserAutoInvestmentRank userAutoInvestmentRank = findByUserId(userId);
        if (null == userAutoInvestmentRank) {
            return null;
        }
        if (!userAutoInvestmentRank.getOpenStatus()) {
            return null;
        }
        if (userAutoInvestmentRank.getExpire().before(new Date())) {
            return null;
        }
        return effectiveList.indexOf(userAutoInvestmentRank) + 1;
    }

    @Override
    public PageResult<UserAutoInvestmentRank> findPage(PageCriteria criteria, Map map) {
        return myDaoSupport.findPage("com.klzan.p2p.mapper.UserAutoInvestmentRankMapper.findPage", map, criteria);
    }
}
