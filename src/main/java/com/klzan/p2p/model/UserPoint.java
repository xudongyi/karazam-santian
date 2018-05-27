package com.klzan.p2p.model;

import com.klzan.core.util.DateUtils;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户积分
 */
@Entity
@Table(name = "karazam_user_point")
public class UserPoint extends BaseModel {

	/** 用户 */
	@Column(nullable = false)
	private Integer userId;

	/** 积分 */
	@Column(nullable = false)
	private Integer point = 0;

	/** 冻结 */
	@Column(nullable = false)
	private Integer frozen = 0;

	/** 历史总收入 */
	@Column(nullable = false)
	private Integer credits = 0;

	/** 历史总支出 */
	@Column(nullable = false)
	private Integer debits = 0;

	/** 总签到次数 */
	@Column
	private Integer signInCount = 0;

	/** 连续签到次数 */
	@Column
	private Integer conSignInCount = 0;

	/** 最后一次签到时间 */
	@Column
	private Date lastSignIn;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Integer getFrozen() {
		return frozen;
	}

	public void setFrozen(Integer frozen) {
		this.frozen = frozen;
	}

	public Integer getCredits() {
		return credits;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public Integer getDebits() {
		return debits;
	}

	public void setDebits(Integer debits) {
		this.debits = debits;
	}

	public Integer getSignInCount() {
		return signInCount;
	}

	public void setSignInCount(Integer signInCount) {
		this.signInCount = signInCount;
	}

	public Integer getConSignInCount() {
		return conSignInCount;
	}

	public void setConSignInCount(Integer conSignInCount) {
		this.conSignInCount = conSignInCount;
	}

	public Date getLastSignIn() {
		return lastSignIn;
	}

	public void setLastSignIn(Date lastSignIn) {
		this.lastSignIn = lastSignIn;
	}

	/**
	 * 可用积分
	 */
	@Transient
	public Integer available() {
		return getPoint()-getFrozen();
	}

	@Transient
	public void addPoint(Integer count) {
		setPoint(getPoint() + count);
	}

	@Transient
	public void subtractPoint(Integer count) {
		setPoint(getPoint() - count);
	}

	@Transient
	public void addCredits(Integer count) {
		setCredits(getCredits() + count);
	}

	@Transient
	public void addDebits(Integer count) {
		setDebits(getDebits() + count);
	}

	/**
	 * 连续签到天数
	 */
	@Transient
	public Integer getConSignInCountCal() {
		//今天已签到 昨天已签到
		if(getLastSignIn()!=null &&
				(DateUtils.getZeroDate(getLastSignIn()).compareTo(DateUtils.getZeroDate(new Date()))==0
				|| DateUtils.getZeroDate(getLastSignIn()).compareTo(DateUtils.getZeroDate(DateUtils.addDays(new Date(), -1)))==0)
				){
			return getConSignInCount();
		}
		return 0;
	}

	/**
	 * 签到
	 */
	@Transient
	public void signIn() {
		if(getLastSignIn()!=null && DateUtils.getZeroDate(getLastSignIn()).compareTo(DateUtils.getZeroDate(DateUtils.addDays(new Date(), -1)))==0){
			setConSignInCount(getConSignInCount() + 1);
		}else {
			setConSignInCount(1);
		}
		setSignInCount(getSignInCount()+1);
		setLastSignIn(new Date());
	}

	/**
	 * 今日已签到
	 */
	@Transient
	public Boolean getTodaySignIn() {
		if(getLastSignIn()!=null && DateUtils.getZeroDate(getLastSignIn()).compareTo(DateUtils.getZeroDate(new Date()))==0){
			return true;
		}
		return false;
	}

}
