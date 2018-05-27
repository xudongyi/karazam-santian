package com.klzan.p2p.dao.user;

import com.klzan.core.persist.DaoSupport;
import com.klzan.p2p.model.CorporationLegal;
import com.klzan.p2p.vo.user.CorporationVo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 企业法人dao
 *
 * @author zhu
 */
@Repository
public class CorporationLegalDao extends DaoSupport<CorporationLegal> {

    public Integer createCorporationLegal(CorporationVo vo) {
        CorporationLegal corporationLegal = new CorporationLegal(
                vo.getCorporationMobile(),
                vo.getCorporationName(), vo.getCorporationIdCard());
        this.persist(corporationLegal);
        return corporationLegal.getId();
    }

    public CorporationLegal findCorporationLegalById(Integer id) {
        return this.get(id);
    }

    public void updateCorporationLegal(CorporationVo vo) {
        CorporationLegal corporationLegal = findCorporationLegalById(vo.getLegalPk());
        corporationLegal.update(vo.getCorporationMobile(),
                vo.getCorporationName(), vo.getCorporationIdCard());
        this.merge(corporationLegal);
    }

    public CorporationLegal findCorporationLegalByUserId(Integer userId) {
        String sql = "select * from karazam_corporation_legal where deleted=false and user_id=:userId";
        Map map = new HashMap();
        map.put("userId", userId);
        return this.findUniqueBySQL(sql, CorporationLegal.class, map);
    }

    public CorporationLegal findCorporationLegalByCardId(String cardId) {
        String sql = "select * from karazam_corporation_legal where deleted=false and corporation_id_card=:cardId";
        Map map = new HashMap();
        map.put("cardId", cardId);
        return this.findUniqueBySQL(sql, CorporationLegal.class, map);
    }
    public CorporationLegal findCorporationlegalBylegalId(Integer legalId){
        String sql= " select * from karazam_corporation_legal where deleted=false and id=:legalId";
        Map map=new HashMap();
        map.put("legalId",legalId);
        return  this.findUniqueBySQL(sql,CorporationLegal.class,map);
    }
}
