package com.klzan.p2p.service.investment.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.investment.InvestmentRecordDao;
import com.klzan.p2p.enums.InvestmentState;
import com.klzan.p2p.model.InvestmentRecord;
import com.klzan.p2p.service.investment.InvestmentRecordService;
import com.klzan.p2p.vo.investment.InvestmentRecordSimpleVo;
import com.klzan.p2p.vo.investment.InvestmentRecordVo;
import com.klzan.p2p.vo.investment.InvestmentSourceVo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhu on 2016/11/22.
 */
@Service
public class InvestmentRecordServiceImpl extends BaseService<InvestmentRecord> implements InvestmentRecordService {

    @Resource
    private InvestmentRecordDao investmentRecordDao;
    @Override
    public PageResult<InvestmentRecordVo> findInvestmentRecordList(PageCriteria pageCriteria, Date startDate, Date endDate) {
        PageResult<InvestmentRecordVo> pageResult = investmentRecordDao.findPageListPage(pageCriteria, startDate, endDate);
        return pageResult;
    }

    @Override
    public void createInvestmentRecord(InvestmentRecordVo vo) {

    }

    @Override
    public void deleteInvestmentRecordById(Integer id) {

    }

    @Override
    public List<InvestmentRecord> findList(Integer borrowingId) {
        return investmentRecordDao.findList(borrowingId);
    }
    @Override
    public List<InvestmentRecord> findListSuccess(Integer borrowingId) {
        return investmentRecordDao.findListSuccess(borrowingId);
    }

    @Override
    public List<InvestmentRecord> findListByUserId(Integer userId) {
        return investmentRecordDao.findListByUserId(userId);
    }

    @Override
    public List<InvestmentRecord> findList(Integer borrowingId, InvestmentState state) {
        return investmentRecordDao.findList(borrowingId, state);
    }

    @Override
    public List<InvestmentRecord> findList(Integer borrowingId, Boolean isTransfer, InvestmentState... states) {
        return investmentRecordDao.findList(borrowingId, isTransfer, states);
    }

    @Override
    public List<InvestmentRecord> findListByInvestment(Integer investmentId) {
        return investmentRecordDao.findListByInvestment(investmentId);
    }

    @Override
    public List<InvestmentRecord> findListByInvestment(Integer investmentId, InvestmentState state) {
        return investmentRecordDao.findListByInvestment(investmentId, state);
    }

    @Override
    public PageResult<InvestmentRecordSimpleVo> findPage(Integer projectId, InvestmentState state, PageCriteria criteria) {
        Map<String, Object> map = new HashedMap();
        map.put("projectId", projectId);
        if (state!=null){
            map.put("state", state.name());
        }
        return myDaoSupport.findPage("com.klzan.p2p.mapper.InvestmentRecordMapper.findByProjectId", map, criteria);
    }

    @Override
    public PageResult<InvestmentRecordSimpleVo> findPage(Integer projectId, PageCriteria criteria, Boolean isTransfer, InvestmentState... state) {
        Map<String, Object> map = new HashedMap();
        map.put("projectId", projectId);
        List<String> states = new ArrayList();
        for (InvestmentState investmentState : state) {
            states.add(investmentState.name());
        }
        map.put("states", states);
        if (null != isTransfer) {
            map.put("isTransfer", isTransfer);
        }
        return myDaoSupport.findPage("com.klzan.p2p.mapper.InvestmentRecordMapper.findByProjectId", map, criteria);
    }

    @Override
    public PageResult<InvestmentRecordSimpleVo> findPage(PageCriteria criteria, Map params, InvestmentState... state) {
        Map<String, Object> map = new HashedMap();
        List<String> states = new ArrayList();
        for (InvestmentState investmentState : state) {
            states.add(investmentState.name());
        }
        map.put("states", states);
        map.putAll(params);
        return myDaoSupport.findPage("com.klzan.p2p.mapper.InvestmentRecordMapper.findDetailByProjectId", map, criteria);
    }

    @Override
    public InvestmentRecord findByOrderNo(String orderNo) {
        return investmentRecordDao.findByOrderNo(orderNo);
    }

    @Override
    public InvestmentSourceVo countSource(){
        return (InvestmentSourceVo)myDaoSupport.findUnique("com.klzan.p2p.mapper.InvestmentRecordMapper.findSource",null);
    }

    @Override
    public List countInvest(String realName, String mobile, Date startDate, Date endDate) {
        return investmentRecordDao.countInvest(realName, mobile, startDate, endDate);
    }

    @Override
    public InvestmentRecord getRecordByInvestment(Integer investmentId) {
        return investmentRecordDao.getRecordByInvestment(investmentId);
    }
}
