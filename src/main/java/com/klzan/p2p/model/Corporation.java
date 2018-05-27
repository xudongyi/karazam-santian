package com.klzan.p2p.model;

import com.klzan.p2p.enums.EnterpriseBorrowingAbility;
import com.klzan.p2p.enums.EnterpriseGuaranteeAbility;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

/**
 * 公司
 */
@Entity
@Table(name = "karazam_corporation")
public class Corporation extends BaseModel {

    /**
     * 法人id
     */
    private Integer legalId;
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
    @Column(nullable = false)
    private Boolean corpWithGuarantee = false;

    /**
     * 公司简介
     */
    private String corpIntro;

    /**
     * 公司资产规模
     */
    @Column(length = 50)
    private String corpAssetSize;

    /**
     * 公司上年度经营额
     */
    @Column(length = 50)
    private String corpPrevYearOperatedRevenue;

    /**
     * 公司注册资金
     */
    @Column(length = 50)
    private String corpRegisteredCapital;

    /**
     * 公司所在地
     */
    private String  corpLocality;

    /**
     * 公司地址
     */
    @Column(length = 200)
    private String corpAddr;

    /**
     * 公司邮编
     */
    @Column(length = 20)
    private String corpZipcode;

    /**
     * 公司执照编号
     */
    @Column(length = 20)
    private String corpLicenseNo;

    /**
     * 公司执照签发日期
     */
    @Temporal(value= TemporalType.DATE)
    private Date corpLicenseIssueDate;

    /**
     * 公司执照到期日期
     */
    @Temporal(value= TemporalType.DATE)
    private Date corpLicenseExpiryDate;

    /**
     * 公司国税登记证编号
     */
    @Column(length = 20)
    private String corpNationalTaxNo;

    /**
     * 公司地税登记证编号
     */
    @Column(length = 20)
    private String corpLandTaxNo;
    /**
     * 企业担保资质状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EnterpriseGuaranteeAbility enterpriseGuaranteeAbilityState = EnterpriseGuaranteeAbility.NOT_APPLY;

    /**
     * 企业放贷资质
     */
    private Boolean enterpriseBorrowingAbility;

    /**
     * 企业放贷资质状态
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EnterpriseBorrowingAbility enterpriseBorrowingAbilityState = EnterpriseBorrowingAbility.NOT_APPLY;

    /**
     * 章
     */
    private String signet;

    public Corporation(){}

    public Corporation(Integer legalId,String corpName, String corpLogo, String corpType,
    		String corpDomain, String corpScale, String corpCertification, 
    		Boolean corpWithGuarantee, String corpIntro, String corpAssetSize,
    		String corpPrevYearOperatedRevenue, String corpRegisteredCapital,
    		String corpLocality, String corpAddr, String corpZipcode,
    		String corpLicenseNo, Date corpLicenseIssueDate, 
    		Date corpLicenseExpiryDate, String corpNationalTaxNo,String corpLandTaxNo,
    		EnterpriseGuaranteeAbility enterpriseGuaranteeAbilityState,
    		Boolean enterpriseBorrowingAbility,EnterpriseBorrowingAbility enterpriseBorrowingAbilityState,
    		String signet) {
    	this.legalId = legalId;
        this.corpName = corpName;
        this.corpLogo = corpLogo;
        this.corpType = corpType;
        this.corpDomain = corpDomain;
        this.corpScale = corpScale;
        this.corpCertification = corpCertification;
        this.corpWithGuarantee = corpWithGuarantee;
        this.corpIntro = corpIntro;
        this.corpAssetSize = corpAssetSize;
        this.corpPrevYearOperatedRevenue = corpPrevYearOperatedRevenue;
        this.corpRegisteredCapital = corpRegisteredCapital;
        this.corpLocality = corpLocality;
        this.corpAddr = corpAddr;
        this.corpZipcode = corpZipcode;
        this.corpLicenseNo = corpLicenseNo;
        this.corpLicenseIssueDate = corpLicenseIssueDate;
        this.corpLicenseExpiryDate = corpLicenseExpiryDate;
        this.corpNationalTaxNo = corpNationalTaxNo;
        this.corpLandTaxNo = corpLandTaxNo;
        this.enterpriseGuaranteeAbilityState = enterpriseGuaranteeAbilityState;
        this.enterpriseBorrowingAbility = enterpriseBorrowingAbility;
        this.enterpriseBorrowingAbilityState = enterpriseBorrowingAbilityState;
        this.signet = signet;
    }

    public void update(String corpName, String corpLogo, String corpType, 
    		String corpDomain, String corpScale, String corpCertification, 
    		Boolean corpWithGuarantee, String corpIntro, String corpAssetSize, 
    		String corpPrevYearOperatedRevenue, String corpRegisteredCapital, 
    		String corpLocality, String corpAddr, String corpZipcode,
    		String corpLicenseNo, Date corpLicenseIssueDate, Date corpLicenseExpiryDate,
    		String corpNationalTaxNo, String corpLandTaxNo,EnterpriseGuaranteeAbility enterpriseGuaranteeAbilityState,
    		Boolean enterpriseBorrowingAbility,EnterpriseBorrowingAbility enterpriseBorrowingAbilityState,
    		String signet) {
        this.corpName = corpName;
        this.corpLogo = corpLogo;
        this.corpType = corpType;
        this.corpDomain = corpDomain;
        this.corpScale = corpScale;
        this.corpCertification = corpCertification;
        this.corpWithGuarantee = corpWithGuarantee;
        this.corpIntro = corpIntro;
        this.corpAssetSize = corpAssetSize;
        this.corpPrevYearOperatedRevenue = corpPrevYearOperatedRevenue;
        this.corpRegisteredCapital = corpRegisteredCapital;
        this.corpLocality = corpLocality;
        this.corpAddr = corpAddr;
        this.corpZipcode = corpZipcode;
        this.corpLicenseNo = corpLicenseNo;
        this.corpLicenseIssueDate = corpLicenseIssueDate;
        this.corpLicenseExpiryDate = corpLicenseExpiryDate;
        this.corpNationalTaxNo = corpNationalTaxNo;
        this.corpLandTaxNo = corpLandTaxNo;
        this.enterpriseGuaranteeAbilityState = enterpriseGuaranteeAbilityState;
        this.enterpriseBorrowingAbility = enterpriseBorrowingAbility;
        this.enterpriseBorrowingAbilityState = enterpriseBorrowingAbilityState;
        this.signet = signet;
    }
    public String getCorpName() {
        return corpName;
    }

    public String getCorpLogo() {
        return corpLogo;
    }

    public String getCorpType() {
        return corpType;
    }

    public String getCorpDomain() {
        return corpDomain;
    }

    public String getCorpScale() {
        return corpScale;
    }

    public String getCorpCertification() {
        return corpCertification;
    }

    public Boolean getCorpWithGuarantee() {
        return corpWithGuarantee;
    }

    public String getCorpIntro() {
        return corpIntro;
    }

    public String getCorpAssetSize() {
        return corpAssetSize;
    }

    public String getCorpPrevYearOperatedRevenue() {
        return corpPrevYearOperatedRevenue;
    }

    public String getCorpRegisteredCapital() {
        return corpRegisteredCapital;
    }

    public String getCorpLocality() {
        return corpLocality;
    }

    public String getCorpAddr() {
        return corpAddr;
    }

    public String getCorpZipcode() {
        return corpZipcode;
    }

    public String getCorpLicenseNo() {
        return corpLicenseNo;
    }

    public Date getCorpLicenseIssueDate() {
        return corpLicenseIssueDate;
    }

    public Date getCorpLicenseExpiryDate() {
        return corpLicenseExpiryDate;
    }

    public String getCorpNationalTaxNo() {
        return corpNationalTaxNo;
    }

    public String getCorpLandTaxNo() {
        return corpLandTaxNo;
    }

	public Integer getLegalId() {
		return legalId;
	}

	public EnterpriseGuaranteeAbility getEnterpriseGuaranteeAbilityState() {
		return enterpriseGuaranteeAbilityState;
	}

	public Boolean getEnterpriseBorrowingAbility() {
		return enterpriseBorrowingAbility;
	}

	public EnterpriseBorrowingAbility getEnterpriseBorrowingAbilityState() {
		return enterpriseBorrowingAbilityState;
	}

	public String getSignet() {
		return signet;
	}

    public void setLegalId(Integer legalId) {
        this.legalId = legalId;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public void setCorpLogo(String corpLogo) {
        this.corpLogo = corpLogo;
    }

    public void setCorpType(String corpType) {
        this.corpType = corpType;
    }

    public void setCorpDomain(String corpDomain) {
        this.corpDomain = corpDomain;
    }

    public void setCorpScale(String corpScale) {
        this.corpScale = corpScale;
    }

    public void setCorpCertification(String corpCertification) {
        this.corpCertification = corpCertification;
    }

    public void setCorpWithGuarantee(Boolean corpWithGuarantee) {
        this.corpWithGuarantee = corpWithGuarantee;
    }

    public void setCorpIntro(String corpIntro) {
        this.corpIntro = corpIntro;
    }

    public void setCorpAssetSize(String corpAssetSize) {
        this.corpAssetSize = corpAssetSize;
    }

    public void setCorpPrevYearOperatedRevenue(String corpPrevYearOperatedRevenue) {
        this.corpPrevYearOperatedRevenue = corpPrevYearOperatedRevenue;
    }

    public void setCorpRegisteredCapital(String corpRegisteredCapital) {
        this.corpRegisteredCapital = corpRegisteredCapital;
    }

    public void setCorpLocality(String corpLocality) {
        this.corpLocality = corpLocality;
    }

    public void setCorpAddr(String corpAddr) {
        this.corpAddr = corpAddr;
    }

    public void setCorpZipcode(String corpZipcode) {
        this.corpZipcode = corpZipcode;
    }

    public void setCorpLicenseNo(String corpLicenseNo) {
        this.corpLicenseNo = corpLicenseNo;
    }

    public void setCorpLicenseIssueDate(Date corpLicenseIssueDate) {
        this.corpLicenseIssueDate = corpLicenseIssueDate;
    }

    public void setCorpLicenseExpiryDate(Date corpLicenseExpiryDate) {
        this.corpLicenseExpiryDate = corpLicenseExpiryDate;
    }

    public void setCorpNationalTaxNo(String corpNationalTaxNo) {
        this.corpNationalTaxNo = corpNationalTaxNo;
    }

    public void setCorpLandTaxNo(String corpLandTaxNo) {
        this.corpLandTaxNo = corpLandTaxNo;
    }

    public void setEnterpriseGuaranteeAbilityState(EnterpriseGuaranteeAbility enterpriseGuaranteeAbilityState) {
        this.enterpriseGuaranteeAbilityState = enterpriseGuaranteeAbilityState;
    }

    public void setEnterpriseBorrowingAbility(Boolean enterpriseBorrowingAbility) {
        this.enterpriseBorrowingAbility = enterpriseBorrowingAbility;
    }

    public void setEnterpriseBorrowingAbilityState(EnterpriseBorrowingAbility enterpriseBorrowingAbilityState) {
        this.enterpriseBorrowingAbilityState = enterpriseBorrowingAbilityState;
    }

    public void setSignet(String signet) {
        this.signet = signet;
    }
}
