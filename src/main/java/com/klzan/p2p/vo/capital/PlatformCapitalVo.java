package com.klzan.p2p.vo.capital;

import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.CapitalMethod;
import com.klzan.p2p.enums.CapitalType;

import java.math.BigDecimal;

/**
 * Created by zhu on 2017/2/9.
 */
public class PlatformCapitalVo extends BaseVo {
    /**
     * 类型
     */
    private CapitalType type;
    private String typeStr;
    /**
     * 方式
     *
     */
    private CapitalMethod method;
    private String methodStr;

    /**
     * 收入
     */
    private BigDecimal credit;

    /**
     * 支出
     */
    private BigDecimal debit;

    private Integer userFinanceId;
    private String userLoginName;
    /**
     * 备注
     */
    private String memo;

    /**
     * 操作人
     */
    private String operator;

    /**
     * IP
     */
    private String ip;

    public CapitalType getType() {
        return type;
    }

    public void setType(CapitalType type) {
        this.type = type;
    }

    public CapitalMethod getMethod() {
        return method;
    }

    public void setMethod(CapitalMethod method) {
        this.method = method;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public Integer getUserFinanceId() {
        return userFinanceId;
    }

    public void setUserFinanceId(Integer userFinanceId) {
        this.userFinanceId = userFinanceId;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTypeStr() {
        return this.type.getDisplayName();
    }

    public String getMethodStr() {
        return this.method.getDisplayName();
    }
}
