/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.service.borrowing.impl;

import com.klzan.core.util.BeanUtils;
import com.klzan.core.util.PinyinUtils;
import com.klzan.p2p.common.service.impl.BaseService;
import com.klzan.p2p.dao.borrowing.BorrowingExtraDetailDao;
import com.klzan.p2p.model.BorrowingExtra;
import com.klzan.p2p.model.BorrowingExtraDetail;
import com.klzan.p2p.service.borrowing.BorrowingExtraDetailService;
import com.klzan.p2p.vo.borrowing.BorrowingExtraDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 借款项目附加详细信息
 */
@Service
public class BorrowingExtraDetailServiceImpl extends BaseService<BorrowingExtraDetail> implements BorrowingExtraDetailService {

    @Autowired
    private BorrowingExtraDetailDao borrowingExtraDetailDao;

    @Override
    public void addExtraDetail(BorrowingExtra extra, List<BorrowingExtraDetailVo> details) {
        for (BorrowingExtraDetailVo detail : details) {
            String extraFieldDes = detail.getExtraFieldDes();
            String extraFieldValue = detail.getExtraFieldValue();
            String extraFieldAlias = PinyinUtils.hanziToPinyin(extraFieldDes, "_");
            BorrowingExtraDetail pDetail = new BorrowingExtraDetail(extra.getId(), extra.getExtraKey(), extra.getBorrowing(), extraFieldAlias, extraFieldDes, extraFieldValue);
            borrowingExtraDetailDao.persist(pDetail);
        }
    }

    @Override
    public void deleteByExtra(Integer extraId) {
        List<BorrowingExtraDetail> list = borrowingExtraDetailDao.findByExtra(extraId);
        for (BorrowingExtraDetail detail : list) {
            borrowingExtraDetailDao.logicDeleteById(detail.getId());
        }
    }

    @Override
    public List<BorrowingExtraDetailVo> findByExtra(Integer extraId) {
        List<BorrowingExtraDetail> list = borrowingExtraDetailDao.findByExtra(extraId);
        List<BorrowingExtraDetailVo> extraDetailVos = new ArrayList<>();
        for (BorrowingExtraDetail extraDetail : list) {
            BorrowingExtraDetailVo extraDetailVo = new BorrowingExtraDetailVo();
            try {
                BeanUtils.copyBean2Bean(extraDetailVo, extraDetail);
                extraDetailVos.add(extraDetailVo);
            } catch (Exception e) {
                continue;
            }
        }
        return extraDetailVos;
    }
}
