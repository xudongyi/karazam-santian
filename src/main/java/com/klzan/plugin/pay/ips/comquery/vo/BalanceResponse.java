package com.klzan.plugin.pay.ips.comquery.vo;

import java.io.Serializable;

/**
 * Created by suhao Date: 2017/3/16 Time: 14:27
 *
 * @version: 1.0
 */
public class BalanceResponse implements Serializable {
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
     * 风险准备金余额 账号为平台IPS存管账户时返回
     */
    private String marginBal;
    /**
     * 还款专用余额 账号为用户IPS存管账户时返回
     */
    private String repaymentBal;

    public String getCurBal() {
        return curBal;
    }

    public String getAvailBal() {
        return availBal;
    }

    public String getFreezeBal() {
        return freezeBal;
    }

    public String getMarginBal() {
        return marginBal;
    }

    public String getRepaymentBal() {
        return repaymentBal;
    }
}
