package com.klzan.p2p.vo.user;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.EnterpriseBorrowingAbility;
import com.klzan.p2p.enums.EnterpriseGuaranteeAbility;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CorporationVo extends BaseVo {
	
	/**
	 * id
	 */
	private Integer corpPk;

	/**
     * 公司名称
     */
    private String corpName;

    /**
     * 公司LOGO
     */
    private String corpLogo;

    /**
     * 公司类别
     */
    private String corpType;

    /**
     * 公司行业
     */
    private String corpDomain;

    /**
     * 公司规模
     */
    private String corpScale;

    /**
     * 公司认证
     */
    private String corpCertification;

    /**
     * 公司有无担保资质
     */
    private Boolean corpWithGuarantee;

    /**
     * 公司简介
     */
    private String corpIntro;

    /**
     * 公司资产规模
     */
    private String corpAssetSize;
    /**
     * 公司上年度经营额
     */
    private String corpPrevYearOperatedRevenue;
    /**
     * 公司注册资金
     */
    private String corpRegisteredCapital;
    /**
     * 公司所在地
     */
    private String corpLocality;
    /**
     * 公司地址
     */
    private String corpAddr;
    /**
     * 公司邮编
     */
    private String corpZipcode;
    /**
     * 公司执照编号
     */
    private String corpLicenseNo;
    /**
     * 公司执照签发日期
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date corpLicenseIssueDate;
    /**
     * 公司执照到期日期
     */
    private Date corpLicenseExpiryDate;
    /**
     * 公司国税登记证编号
     */
    private String corpNationalTaxNo;
    /**
     * 公司地税登记证编号
     */
    private String corpLandTaxNo;
    /**
     * 关联公司
     */
    private Integer corporateId;

    /**
     * 企业担保资质状态
     */
    private EnterpriseGuaranteeAbility enterpriseGuaranteeAbilityState;
	//private String enterpriseGuaranteeAbilityStateStr;
    /**
     * 企业放贷资质
     */
    private Boolean enterpriseBorrowingAbility;
    /**
     * 企业放贷资质状态
     */
    private EnterpriseBorrowingAbility enterpriseBorrowingAbilityState;
	//private String enterpriseBorrowingAbilityStateStr;
    
    private String signet;
/**---------------------------公司法人--------------------------------------------**/
	/**
	 * 法人表CorporationLegal主键id
	 */
	private Integer legalPk;
	/**
	 * CorporationLegal表中user_id
	 */
	private Integer userId;
	/**
	 * Corporation表中legal_id
	 */
	private Integer legalId;
	/**
	 * 法人手机号
	 */
	private String corporationMobile;

	/**
	 * 法人姓名
	 */
	private String corporationName;

	/**
	 * 法人身份证
	 */
	private String corporationIdCard;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getLegalId() {
		return legalId;
	}

	public void setLegalId(Integer legalId) {
		this.legalId = legalId;
	}

	public String getCorpAddr() {
		return corpAddr;
	}
	public String getCorpAssetSize() {
		return corpAssetSize;
	}
	public String getCorpCertification() {
		return corpCertification;
	}
	public String getCorpDomain() {
		return corpDomain;
	}
	public String getCorpIntro() {
		return corpIntro;
	}
	public String getCorpLandTaxNo() {
		return corpLandTaxNo;
	}
	public Date getCorpLicenseExpiryDate() {
		return corpLicenseExpiryDate;
	}
	public Date getCorpLicenseIssueDate() {
		return corpLicenseIssueDate;
	}
	public String getCorpLicenseNo() {
		return corpLicenseNo;
	}
	public String getCorpLocality() {
		return corpLocality;
	}
	public String getCorpLogo() {
		return corpLogo;
	}
	public String getCorpName() {
		return corpName;
	}
	public String getCorpNationalTaxNo() {
		return corpNationalTaxNo;
	}
	public Integer getCorporateId() {
		return corporateId;
	}
	public String getCorporationIdCard() {
		return corporationIdCard;
	}
	public String getCorporationMobile() {
		return corporationMobile;
	}
	public String getCorporationName() {
		return corporationName;
	}
	public String getCorpPrevYearOperatedRevenue() {
		return corpPrevYearOperatedRevenue;
	}
	public String getCorpRegisteredCapital() {
		return corpRegisteredCapital;
	}    
	public String getCorpScale() {
		return corpScale;
	}
	public String getCorpType() {
		return corpType;
	}
	public Boolean getCorpWithGuarantee() {
		return corpWithGuarantee;
	}
	public String getCorpZipcode() {
		return corpZipcode;
	}
	public Boolean getEnterpriseBorrowingAbility() {
		return enterpriseBorrowingAbility;
	}
	public EnterpriseBorrowingAbility getEnterpriseBorrowingAbilityState() {
		//return EnterpriseBorrowingAbility.valueOf(enterpriseBorrowingAbilityStateStr);
		return enterpriseBorrowingAbilityState;
	}
	public EnterpriseGuaranteeAbility getEnterpriseGuaranteeAbilityState() {
		//return EnterpriseGuaranteeAbility.valueOf(enterpriseGuaranteeAbilityStateStr);
		return enterpriseGuaranteeAbilityState;
	}
	public String getSignet() {
		return signet;
	}
	public void setCorpAddr(String corpAddr) {
		this.corpAddr = corpAddr;
	}
	public void setCorpAssetSize(String corpAssetSize) {
		this.corpAssetSize = corpAssetSize;
	}
	public void setCorpCertification(String corpCertification) {
		this.corpCertification = corpCertification;
	}
	public void setCorpDomain(String corpDomain) {
		this.corpDomain = corpDomain;
	}
	public void setCorpIntro(String corpIntro) {
		this.corpIntro = corpIntro;
	}
	public void setCorpLandTaxNo(String corpLandTaxNo) {
		this.corpLandTaxNo = corpLandTaxNo;
	}
	public void setCorpLicenseExpiryDate(Date corpLicenseExpiryDate) {
		this.corpLicenseExpiryDate = corpLicenseExpiryDate;
	}
	public void setCorpLicenseIssueDate(Date corpLicenseIssueDate) {
		this.corpLicenseIssueDate = corpLicenseIssueDate;
	}
	public void setCorpLicenseNo(String corpLicenseNo) {
		this.corpLicenseNo = corpLicenseNo;
	}
	public void setCorpLocality(String corpLocality) {
		this.corpLocality = corpLocality;
	}
	public void setCorpLogo(String corpLogo) {
		this.corpLogo = corpLogo;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public void setCorpNationalTaxNo(String corpNationalTaxNo) {
		this.corpNationalTaxNo = corpNationalTaxNo;
	}
	public void setCorporateId(Integer corporateId) {
		this.corporateId = corporateId;
	}
	public void setCorporationIdCard(String corporationIdCard) {
		this.corporationIdCard = corporationIdCard;
	}
	public void setCorporationMobile(String corporationMobile) {
		this.corporationMobile = corporationMobile;
	}
	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}
	public void setCorpPrevYearOperatedRevenue(String corpPrevYearOperatedRevenue) {
		this.corpPrevYearOperatedRevenue = corpPrevYearOperatedRevenue;
	}
	public void setCorpRegisteredCapital(String corpRegisteredCapital) {
		this.corpRegisteredCapital = corpRegisteredCapital;
	}
	public void setCorpScale(String corpScale) {
		this.corpScale = corpScale;
	}
	public void setCorpType(String corpType) {
		this.corpType = corpType;
	}
	public void setCorpWithGuarantee(Boolean corpWithGuarantee) {
		this.corpWithGuarantee = corpWithGuarantee;
	}
	public void setCorpZipcode(String corpZipcode) {
		this.corpZipcode = corpZipcode;
	}
	public void setEnterpriseBorrowingAbility(Boolean enterpriseBorrowingAbility) {
		this.enterpriseBorrowingAbility = enterpriseBorrowingAbility;
	}
	public void setEnterpriseBorrowingAbilityState(EnterpriseBorrowingAbility enterpriseBorrowingAbilityState) {
		this.enterpriseBorrowingAbilityState = enterpriseBorrowingAbilityState;
	}
	public void setEnterpriseGuaranteeAbilityState(EnterpriseGuaranteeAbility enterpriseGuaranteeAbilityState) {
		this.enterpriseGuaranteeAbilityState = enterpriseGuaranteeAbilityState;
	}
	public void setSignet(String signet) {
		this.signet = signet;
	}
	public Integer getCorpPk() {
		return corpPk;
	}
	public void setCorpPk(Integer corpPk) {
		this.corpPk = corpPk;
	}
	public Integer getLegalPk() {
		return legalPk;
	}
	public void setLegalPk(Integer legalPk) {
		this.legalPk = legalPk;
	}

//	public String getEnterpriseGuaranteeAbilityStateStr() {
//		return EnterpriseGuaranteeAbility.valueOf(enterpriseGuaranteeAbilityStateStr).getDisplayName();
//	}
//
//	public void setEnterpriseGuaranteeAbilityStateStr(String enterpriseGuaranteeAbilityStateStr) {
//		this.enterpriseGuaranteeAbilityStateStr = enterpriseGuaranteeAbilityStateStr;
//	}
//
//	public String getEnterpriseBorrowingAbilityStateStr() {
//		return EnterpriseBorrowingAbility.valueOf(enterpriseBorrowingAbilityStateStr).getDisplayName();
//	}
//
//	public void setEnterpriseBorrowingAbilityStateStr(String enterpriseBorrowingAbilityStateStr) {
//		this.enterpriseBorrowingAbilityStateStr = enterpriseBorrowingAbilityStateStr;
//	}
}
