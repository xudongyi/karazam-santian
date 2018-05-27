/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing.impl;

import com.klzan.core.util.BeanUtils;
import com.klzan.core.util.PinyinUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingExtraDao;
import com.klzan.p2p.dao.borrowing.BorrowingOpinionDao;
import com.klzan.p2p.model.BorrowingExtra;
import com.klzan.p2p.service.borrowing.BorrowingExtraDetailService;
import com.klzan.p2p.service.borrowing.BorrowingExtraService;
import com.klzan.p2p.vo.borrowing.BorrowingExtraDetailVo;
import com.klzan.p2p.vo.borrowing.BorrowingExtraVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 借款项目附加信息
 */
@Service
public class BorrowingExtraServiceImpl extends BaseService<BorrowingExtra> implements BorrowingExtraService {

    @Autowired
    private BorrowingExtraDao borrowingExtraDao;

    @Autowired
    private BorrowingExtraDetailService borrowingExtraDetailService;

    @Override
    public void addExtra(Integer borrowingId, List<BorrowingExtraVo> extras) {
        try {
            for (BorrowingExtraVo extraVo : extras) {
                if (!extraVo.getDetails().isEmpty()) {
                    String extraKeyDes = extraVo.getExtraValue();
                    String extraValue = extraVo.getExtraValue();
                    BorrowingExtra extra = new BorrowingExtra(borrowingId, PinyinUtils.hanziToPinyin(extraKeyDes, "_"), extraKeyDes, extraValue);
                    borrowingExtraDao.persist(extra);

                    borrowingExtraDetailService.addExtraDetail(extra, extraVo.getDetails());
                }
            }
        } catch (Exception e) {
            logger.error("项目{}附加信息添加失败", borrowingId);
        }
    }

    @Override
    public void updateExtra(Integer borrowingId, List<BorrowingExtraVo> extras) {
        List<BorrowingExtra> list = borrowingExtraDao.findByBorrowing(borrowingId);
        for (BorrowingExtra extra : list) {
            borrowingExtraDao.logicDeleteById(extra.getId());
            borrowingExtraDetailService.deleteByExtra(extra.getId());
        }
        addExtra(borrowingId, extras);
    }

    @Override
    public List<BorrowingExtraVo> findByBorrowing(Integer borrowingId) {
        List<BorrowingExtra> list = borrowingExtraDao.findByBorrowing(borrowingId);
        List<BorrowingExtraVo> extraVos = new ArrayList<>();
        for (BorrowingExtra extra : list) {
            BorrowingExtraVo extraVo = new BorrowingExtraVo();
            try {
                BeanUtils.copyBean2Bean(extraVo, extra);
                List<BorrowingExtraDetailVo> detailVos = borrowingExtraDetailService.findByExtra(extra.getId());
                extraVo.setDetails(detailVos);
                extraVos.add(extraVo);
            } catch (Exception e) {
                continue;
            }
        }
        return extraVos;
    }
}
