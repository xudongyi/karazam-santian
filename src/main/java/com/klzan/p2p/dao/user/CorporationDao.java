package com.klzan.p2p.dao.user;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.DaoSupport;
import com.klzan.core.persist.hibernate.ScalarAliasCallback;
import com.klzan.p2p.model.Corporation;
import com.klzan.p2p.vo.user.CorporationVo;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.LockMode;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CorporationDao extends DaoSupport<Corporation> {
	
	public void createCorporation(CorporationVo vo) {
		Corporation corporation = new Corporation();
		objectSet(corporation,vo);
		this.persist(corporation);
	}
	public void updateCorporation(CorporationVo vo,Integer id){
		Corporation corporation = findCorporationById(id);
		objectSet(corporation,vo);
		this.merge(corporation);
	}
	public Corporation findCorporationById(Integer id){
		return (Corporation)this.get(id, LockMode.NONE);
	}


	public List<Corporation> findGuaranteeCorp(){
		String sql = "select * from karazam_corporation where deleted=0 and corp_With_Guarantee=1";
		return this.findBySQL(sql,Corporation.class);
	}

	public Corporation findCorporationByUserId(Integer userId){
		StringBuilder hql = new StringBuilder("SELECT c From Corporation c,CorporationLegal cl, User u where c.deleted = 0 and cl.deleted = 0 and u.deleted = 0 and u.id = cl.userId and cl.id = c.legalId ");
		Map<String, Object> params = new HashedMap();
		if(userId != null){
			hql.append(" and u.id = :userId");
			params.put("userId", userId);
		}
		return this.findUnique(hql.toString(), params);
	}

	public PageResult<CorporationVo> findPageListPage(PageCriteria pageCriteria) {
        String sql = "select a.id as id,"
        		+"a.corp_Name as corpName,"
        		+"a.corp_Type as corpType,"
        		+"a.corp_Domain as corpDomain,"
        		+"a.corp_Scale as corpScale,"
        		+"a.corp_Certification as corpCertification,"
        		+"a.corp_With_Guarantee as corpWithGuarantee,"
        		+"a.corp_Intro as corpIntro,"
        		+"a.corp_Asset_Size as corpAssetSize,"
        		+"a.corp_Prev_Year_Operated_Revenue as corpPrevYearOperatedRevenue,"
        		+"a.corp_Registered_Capital as corpRegisteredCapital,"
        		+"a.corp_Locality as corpLocality,"
        		+"a.corp_Addr as corpAddr,"
        		+"a.corp_Zipcode as corpZipcode,"
        		+"a.corp_License_No as corpLicenseNo,"
        		+"a.corp_License_Issue_Date as corpLicenseIssueDate,"
        		+"a.corp_License_Expiry_Date as corpLicenseExpiryDate,"
        		+"a.corp_National_Tax_No as corpNationalTaxNo,"
        		+"a.corp_Land_Tax_No as corpLandTaxNo,"
//        		+"a.enterprise_Guarantee_Ability_State as enterpriseGuaranteeAbilityStateStr,"
        		+"a.enterprise_Borrowing_Ability as enterpriseBorrowingAbility,"
//        		+"a.enterprise_Borrowing_Ability_State as enterpriseBorrowingAbilityStateStr "
				+"b.corporation_Mobile as corporationMobile,"
				+"b.corporation_Name as corporationName,"
				+"b.corporation_Id_Card as corporationIdCard "
        		+ "from karazam_corporation a LEFT JOIN karazam_corporation_legal b on a.legal_id=b.id "
        		+ "where a.deleted=false ";
        return this.findPageBySQL(sql, pageCriteria, new ScalarAliasCallback<CorporationVo>() {
            @Override
            protected Class<CorporationVo> doAddScalar(NativeQuery query) {
                query.addScalar("id", StandardBasicTypes.INTEGER);
                query.addScalar("corpName", StandardBasicTypes.STRING);
			    query.addScalar("corpType", StandardBasicTypes.STRING);
			    query.addScalar("corpDomain", StandardBasicTypes.STRING);
			    query.addScalar("corpScale", StandardBasicTypes.STRING);
			    query.addScalar("corpCertification", StandardBasicTypes.STRING);
			    query.addScalar("corpWithGuarantee", StandardBasicTypes.BOOLEAN);
			    query.addScalar("corpIntro", StandardBasicTypes.STRING);
			    query.addScalar("corpAssetSize", StandardBasicTypes.STRING);
			    query.addScalar("corpPrevYearOperatedRevenue", StandardBasicTypes.STRING);
				query.addScalar("corpRegisteredCapital", StandardBasicTypes.STRING);
				query.addScalar("corpLocality", StandardBasicTypes.STRING);
				query.addScalar("corpAddr", StandardBasicTypes.STRING);
				query.addScalar("corpZipcode", StandardBasicTypes.STRING);
				query.addScalar("corpLicenseNo", StandardBasicTypes.STRING);
				query.addScalar("corpLicenseIssueDate", StandardBasicTypes.DATE);
				query.addScalar("corpLicenseExpiryDate", StandardBasicTypes.DATE);
				query.addScalar("corpNationalTaxNo", StandardBasicTypes.STRING);
				query.addScalar("corpLandTaxNo", StandardBasicTypes.STRING);
				query.addScalar("enterpriseBorrowingAbility", StandardBasicTypes.BOOLEAN);
				query.addScalar("corporationMobile", StandardBasicTypes.STRING);
				query.addScalar("corporationName", StandardBasicTypes.STRING);
				query.addScalar("corporationIdCard", StandardBasicTypes.STRING);
                return CorporationVo.class;
            }
        },pageCriteria.getParams());
    }
    private void objectSet(Corporation corporation,CorporationVo vo){
    	corporation.setLegalId(vo.getLegalId());
		corporation.setCorpName(vo.getCorpName());
		corporation.setCorpType(vo.getCorpType());
		corporation.setCorpDomain(vo.getCorpDomain());
		corporation.setCorpScale(vo.getCorpScale());
		corporation.setCorpCertification(vo.getCorpCertification());
		corporation.setCorpWithGuarantee(vo.getCorpWithGuarantee());
		corporation.setCorpIntro(vo.getCorpIntro());
		corporation.setCorpAssetSize(vo.getCorpAssetSize());
		corporation.setCorpPrevYearOperatedRevenue(vo.getCorpPrevYearOperatedRevenue());
		corporation.setCorpRegisteredCapital(vo.getCorpRegisteredCapital());
		corporation.setCorpLocality(vo.getCorpLocality());
		corporation.setCorpAddr(vo.getCorpAddr());
		corporation.setCorpZipcode(vo.getCorpZipcode());
		corporation.setCorpLicenseNo(vo.getCorpLicenseNo());
		corporation.setCorpLicenseIssueDate(vo.getCorpLicenseIssueDate());
		corporation.setCorpLicenseExpiryDate(vo.getCorpLicenseExpiryDate());
		corporation.setCorpNationalTaxNo(vo.getCorpNationalTaxNo());
		corporation.setCorpLandTaxNo(vo.getCorpLandTaxNo());
		corporation.setEnterpriseGuaranteeAbilityState(vo.getEnterpriseGuaranteeAbilityState());
		corporation.setEnterpriseBorrowingAbility(vo.getEnterpriseBorrowingAbility());
		corporation.setEnterpriseBorrowingAbilityState(vo.getEnterpriseBorrowingAbilityState());
	}
}
//				query.addScalar("enterpriseGuaranteeAbilityStateStr", StandardBasicTypes.STRING);
//						query.addScalar("enterpriseBorrowingAbilityStateStr", StandardBasicTypes.STRING);
