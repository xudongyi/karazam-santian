package com.klzan.p2p.service.point.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.point.PointRecordDao;
import com.klzan.p2p.enums.PointMethod;
import com.klzan.p2p.enums.PointType;
import com.klzan.p2p.model.PointRecord;
import com.klzan.p2p.service.point.PointRecordService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * 积分记录
 * @author: chenxinglin
 */
@Service
public class PointRecordServiceImpl extends BaseService<PointRecord> implements PointRecordService {

    @Inject
    private PointRecordDao pointRecordDao;

    @Override
    public PageResult<PointRecord> findPage(PageCriteria pageCriteria, Integer userId, PointType type, Integer month) {
        return pointRecordDao.findPage(pageCriteria, userId, type, month);
    }

    @Override
    public List<PointRecord> findListByUserId(Integer userId) {
        return pointRecordDao.findListByUserId(userId);
    }

    @Override
    public List<PointRecord> findList(Integer userId, PointMethod method) {
        return pointRecordDao.findList(userId, method);
    }

    @Override
    public List<PointRecord> findList(Integer userId, PointMethod method, Date startDate, Date endDate) {
        return pointRecordDao.findList(userId, method, startDate, endDate);
    }

//    @Override
//    public List<PointRecord> findListByGoods(Integer goodsId) {
//        return pointRecordDao.findListByGoods(goodsId);
//    }

}
