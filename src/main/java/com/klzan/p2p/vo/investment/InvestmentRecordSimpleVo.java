package com.klzan.p2p.vo.investment;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.OperationMethod;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by suhao Date: 2016/12/27 Time: 19:13
 *
 * @version: 1.0
 */
public class InvestmentRecordSimpleVo extends BaseVo {
    /**
     * 投资人
     */
    private String investor;

    /**
     * 购买时间
     */
    private Date buyTime;

    /**
     * 购买金额
     */
    private BigDecimal amount;

    private OperationMethod operationMethod;

    private String operationMethodStr;

    public String getInvestor() {
        return investor;
    }

    public void setInvestor(String investor) {
        this.investor = investor;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OperationMethod getOperationMethod() {
        if (StringUtils.isNotBlank(operationMethodStr)) {
            return OperationMethod.valueOf(operationMethodStr);
        }
        return operationMethod;
    }

    public void setOperationMethod(OperationMethod operationMethod) {
        this.operationMethod = operationMethod;
    }

    public String getOperationMethodStr() {
        if (StringUtils.isNotBlank(operationMethodStr)) {
            return OperationMethod.valueOf(operationMethodStr).getDisplayName();
        }
        return operationMethodStr;
    }

    public void setOperationMethodStr(String operationMethodStr) {
        this.operationMethodStr = operationMethodStr;
    }
}
