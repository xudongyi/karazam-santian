package com.klzan.p2p.service.sms;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.enums.SmsType;
import com.klzan.p2p.model.SmsToken;
import javax.servlet.http.HttpServletRequest;

/**
 * Service - 令牌
 */
public interface SmsTokenService extends IBaseService<SmsToken> {

    /**
     * 生成令牌
     * 
     * @param type
     *            令牌方式
     * @param addr
     *            通讯地址
     * @return 生成成功的令牌
     */
    SmsToken build(SmsType type, String addr) throws Exception;

    /**
     * 验证令牌
     * 
     * @param type
     *            令牌方式
     * @param addr
     *            通讯地址
     * @param code
     *            令牌代码
     * @param isDelete
     *            是否删除
     * @return 验证是否通过
     */
    boolean verify(SmsType type, String addr, String code, boolean isDelete);

    /**
     * 验证令牌
     * 
     * @param type
     *            令牌方式
     * @param addr
     *            通讯地址
     * @param code
     *            令牌代码
     * @return 验证是否通过
     */
    boolean verify(SmsType type, String addr, String code);

    /**
     * 分页查询优惠券
     * @param criteria
     * @param request
     * @return
     */
    PageResult<SmsToken> findAllSmsTokenPage(PageCriteria criteria, HttpServletRequest request);

}