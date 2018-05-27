package com.klzan.p2p.service.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.p2p.common.service.IBaseService;
import com.klzan.p2p.model.Corporation;
import com.klzan.p2p.vo.user.CorporationVo;

import java.util.List;

/**
 * 企业信息
 *
 * @author zhu
 */
public interface CorporationService extends IBaseService<Corporation> {
    /**
     * 查询企业列表
     *
     * @param pageCriteria 分页信息
     * @return 企业列表
     */
    PageResult<CorporationVo> findCorporation(PageCriteria pageCriteria);

    /**
     * 查询企业详细信息
     *
     * @param id 企业信息唯一标示
     * @return 包含一条企业信息的
     */
    Corporation findCorporationById(Integer id);

    /**
     * 新增企业信息
     *
     * @param vo 企业信息值对象
     */
    void createCorporation(CorporationVo vo);

    /**
     * 修改企业信息
     *
     * @param vo 企业信息值对象
     */
    void updateCorporation(CorporationVo vo, Integer id);

    /**
     * 删除企业信息
     *
     * @param id 企业信息唯一标示
     */
    void deleteCorporationById(Integer id);

    /**
     * 查询 具有担保资质的企业
     *
     * @return
     */
    List<Corporation> findGuaranteeCorp();

    Corporation findCorporationByUserId(Integer userId);
}
