package com.klzan.p2p.service.borrowing;

import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.BorrowingFieldRemark;

import java.util.Map;

/**
 * 借款字段备注
 * @author zhu
 *
 */
public interface BorrowingFieldRemarkService extends IBaseService<BorrowingFieldRemark> {

    /**
     * 创建/修改备注
     * @param borrowingId
     * @param remarks
     */
    void createOrUpdateRemarks(Integer borrowingId, Map<String, ?> remarks);

    /**
     * 查询备注
     * @param borrowingId
     * @return
     */
    Map<String, String> findReamrks(Integer borrowingId);

}
