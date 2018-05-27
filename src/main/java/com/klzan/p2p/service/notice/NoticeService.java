package com.klzan.p2p.service.notice;
import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.model.Notice;
import com.klzan.p2p.vo.notice.NoticeVo;

/**
 * Created by zhu on 2016/12/7.
 */
public interface NoticeService {

    /**
     * 分页获取通知
     * @param id
     * @param pageCriteria
     * @return
     */
    PageResult<NoticeVo> findNoticePageByUserId(Integer id, PageCriteria pageCriteria);

    /**
     * 根据id获取单个通知详细信息
     * @param id
     * @return
     */
    Notice getNoticeDetail(Integer id);
}
