package com.klzan.p2p.model;

import com.klzan.p2p.enums.RechargeBusinessType;
import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 用户财务信息
 */
@Entity
@Table(name = "karazam_user_finance")
public class UserFinance extends BaseModel {
    /**
     * 关联用户id
     */
    private Integer userId;

    /**
     * 借款总金额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal borrowingAmts = BigDecimal.ZERO;
    /**
     * 投资总金额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal investmentAmts = BigDecimal.ZERO;
    /**
     * 充值总金额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal rechargeAmts = BigDecimal.ZERO;
    /**
     * 提现总金额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal withdrawalAmts = BigDecimal.ZERO;
    /**
     * 总余额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;
    /**
     * 普通充值余额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal generalBalance = BigDecimal.ZERO;
    /**
     * 还款充值余额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal repaymentBalance = BigDecimal.ZERO;
    /**
     * 待收金额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal credit = BigDecimal.ZERO;
    /**
     * 待还金额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal debit = BigDecimal.ZERO;
    /**
     * 冻结金额
     */
    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal frozen = BigDecimal.ZERO;
    /**
     * 体验金
     */
    private BigDecimal experience = BigDecimal.ZERO;

    public BigDecimal getBorrowingAmts() {
        return borrowingAmts;
    }

    public BigDecimal getInvestmentAmts() {
        return investmentAmts;
    }

    public BigDecimal getRechargeAmts() {
        return rechargeAmts;
    }

    public BigDecimal getWithdrawalAmts() {
        return withdrawalAmts;
    }

    public BigDecimal getExperience() {
        return experience;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public BigDecimal getFrozen() {
        return frozen;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setBorrowingAmts(BigDecimal borrowingAmts) {
        this.borrowingAmts = borrowingAmts;
    }

    public void setInvestmentAmts(BigDecimal investmentAmts) {
        this.investmentAmts = investmentAmts;
    }

    public void setRechargeAmts(BigDecimal rechargeAmts) {
        this.rechargeAmts = rechargeAmts;
    }

    public void setWithdrawalAmts(BigDecimal withdrawalAmts) {
        this.withdrawalAmts = withdrawalAmts;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public void setFrozen(BigDecimal frozen) {
        this.frozen = frozen;
    }

    public void setExperience(BigDecimal experience) {
        this.experience = experience;
    }

    public BigDecimal getGeneralBalance() {
        return generalBalance;
    }

    public void setGeneralBalance(BigDecimal generalBalance) {
        this.generalBalance = generalBalance;
    }

    public BigDecimal getRepaymentBalance() {
        return repaymentBalance;
    }

    public void setRepaymentBalance(BigDecimal repaymentBalance) {
        this.repaymentBalance = repaymentBalance;
    }

    /**
     * 添加余额
     *
     * @param amount 金额
     * @param type
     */
    @Transient
    public void addBalance(BigDecimal amount, RechargeBusinessType type) {
        setBalance(getBalance().add(amount));
        if (type == RechargeBusinessType.GENERAL) {
            this.generalBalance = getGeneralBalance().add(amount);
        } else if (type == RechargeBusinessType.REAPYMENT) {
            this.repaymentBalance = getRepaymentBalance().add(amount);
        }
    }
//    @Transient
//    public void addBalance(BigDecimal amount) {
//        this.generalBalance = getBalance().add(amount);
//    }

//    @Transient
//    public void subtractBalance(BigDecimal amount) {
//        setBalance(getBalance().subtract(amount));
//    }

    @Transient
    public void subtractBalance(BigDecimal amount, Boolean isRepay) {
//        if (isRepay) {
//            if (getRepaymentBalance().compareTo(amount) >= 0) {
//                setRepaymentBalance(getRepaymentBalance().subtract(amount));
//            } else  {
//                setRepaymentBalance(BigDecimal.ZERO);
//                amount = amount.subtract(getRepaymentBalance());
//                setGeneralBalance(getGeneralBalance().subtract(amount));
//            }
//        } else {
//            setGeneralBalance(getGeneralBalance().subtract(amount));
//        }
//        setBalance(getBalance().subtract(amount));
        subtractBalance(amount);
    }

    @Transient
    public void subtractBalance(BigDecimal amount) {
        setGeneralBalance(getGeneralBalance().subtract(amount));
        setBalance(getBalance().subtract(amount));
    }

    /**
     * 添加待收金额
     *
     * @param amount 金额
     */
    @Transient
    public void addCredit(BigDecimal amount) {
        setCredit(getCredit().add(amount));
    }

    /**
     * 减去待收金额
     *
     * @param amount 金额
     */
    @Transient
    public void subtractCredit(BigDecimal amount) {
        setCredit(getCredit().subtract(amount));
    }

    /**
     * 添加待还金额
     *
     * @param amount 金额
     */
    @Transient
    public void addDebit(BigDecimal amount) {
        setDebit(getDebit().add(amount));
    }

    /**
     * 减去待还金额
     *
     * @param amount 金额
     */
    @Transient
    public void subtractDebit(BigDecimal amount) {
        setDebit(getDebit().subtract(amount));
    }

    /**
     * 添加借款总额
     *
     * @param amount 金额
     */
    @Transient
    public void addBorrowingAmts(BigDecimal amount) {
        setBorrowingAmts(getBorrowingAmts().add(amount));
    }

    /**
     * 添加投资总额
     *
     * @param amount 金额
     */
    @Transient
    public void addInvestmentAmts(BigDecimal amount) {
        setInvestmentAmts(getInvestmentAmts().add(amount));
    }

    /**
     * 添加充值总额
     *
     * @param amount 金额
     */
    @Transient
    public void addRechargeAmts(BigDecimal amount) {
        setRechargeAmts(getRechargeAmts().add(amount));
    }

    /**
     * 添加提现总额
     *
     * @param amount 金额
     */
    @Transient
    public void addWithdrawalAmts(BigDecimal amount) {
        setWithdrawalAmts(getWithdrawalAmts().add(amount));
    }

    /**
     * 添加余额
     *
     * @param amount 金额
     */
    @Transient
    public void addFrozen(BigDecimal amount) {
        setFrozen(getFrozen().add(amount));
    }

    public void subtractFrozen(BigDecimal amount) {
        setFrozen(getFrozen().subtract(amount));
    }

    public BigDecimal getEquity() {
        return getCredit().subtract(getDebit()).add(getBalance());
    }

    @Transient
    public BigDecimal getAvailable() {
        return getBalance().compareTo(BigDecimal.ZERO) > 0 ? getBalance().subtract(getRepaymentBalance()).subtract(getFrozen()) : BigDecimal.ZERO;
    }
}
