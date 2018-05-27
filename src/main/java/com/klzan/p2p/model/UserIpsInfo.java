package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户-环迅账户信息
 */
@Entity
@Table(name = "karazam_user_ips_info")
public class UserIpsInfo extends BaseModel {

	private Boolean autoInvestSign;

	/**
	 * 关联账户ID
	 */
	private Integer userId;
	/**
	 * 账户开通状态 1 正常、 2 异常
	 */
	private String acctStatus;
	/**
	 * 身份证审核状态
	 * 0 未上传身份证（默认）
	 * 1 审核成功、
	 * 2 审核拒绝、
	 * 3 审核中（已经上传身份证，但是未审核）、
	 * 4 未推送审核(已上传,但未发往运管审核)
	 */
	private String userCardStatus;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 银行卡后四位
	 */
	private String bankCard;
	/**
	 * 代扣签约状态 1 未申请、 2 成功、 3 失败
	 */
	private String repaySignStatus;

	/**
	 * 账户余额
	 */
	private String curBal;
	/**
	 * 可用余额
	 */
	private String availBal;
	/**
	 * 冻结余额
	 */
	private String freezeBal;
	/**
	 * 还款专用余额 账号为用户IPS存管账户时返回
	 */
	private String repaymentBal;

	@CreationTimestamp
	private Date queryDate;

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public String getCurBal() {
		return curBal;
	}

	public void setCurBal(String curBal) {
		this.curBal = curBal;
	}

	public String getAvailBal() {
		return availBal;
	}

	public void setAvailBal(String availBal) {
		this.availBal = availBal;
	}

	public String getFreezeBal() {
		return freezeBal;
	}

	public void setFreezeBal(String freezeBal) {
		this.freezeBal = freezeBal;
	}

	public String getRepaymentBal() {
		return repaymentBal;
	}

	public void setRepaymentBal(String repaymentBal) {
		this.repaymentBal = repaymentBal;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/** default constructor */
	public UserIpsInfo() {
	}

	public Boolean getAutoInvestSign() {
		return autoInvestSign;
	}

	public void setAutoInvestSign(Boolean autoInvestSign) {
		this.autoInvestSign = autoInvestSign;
	}

	public String getAcctStatus() {
		return acctStatus;
	}

	public void setAcctStatus(String acctStatus) {
		this.acctStatus = acctStatus;
	}

	public String getUserCardStatus() {
		return userCardStatus;
	}

	public void setUserCardStatus(String userCardStatus) {
		this.userCardStatus = userCardStatus;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getRepaySignStatus() {
		return repaySignStatus;
	}

	public void setRepaySignStatus(String repaySignStatus) {
		this.repaySignStatus = repaySignStatus;
	}
}