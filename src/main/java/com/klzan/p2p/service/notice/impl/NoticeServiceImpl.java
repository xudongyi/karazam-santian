package com.klzan.p2p.service.notice.impl;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.dao.notice.NoticeDao;
import com.klzan.p2p.model.Notice;
import com.klzan.p2p.service.notice.NoticeService;
import com.klzan.p2p.vo.notice.NoticeVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhu on 2016/12/7.
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeDao noticeDao;

    @Override
    public PageResult<NoticeVo> findNoticePageByUserId(Integer id, PageCriteria pageCriteria) {
        return noticeDao.findPageListPage(id,pageCriteria);
    }

    @Override
    public Notice getNoticeDetail(Integer id) {
        return noticeDao.getNoticeDetail(id);
    }
}
