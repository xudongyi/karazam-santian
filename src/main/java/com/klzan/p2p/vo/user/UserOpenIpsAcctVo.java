package com.klzan.p2p.vo.user;

/**
 * Created by suhao Date: 2017/5/26 Time: 16:35
 *
 * @version: 1.0
 */
public class UserOpenIpsAcctVo extends AbstractUserMeta {
    /**
     * 商户订单号
     */
    private String merBillNo;
    /**
     * IPS订单号
     */
    private String ipsBillNo;
    /**
     * IPS处理时间
     */
    private String ipsDoTime;
    /**
     * IPS虚拟账号
     */
    private String ipsAcctNo;
    /**
     * 注册状态 0-失败 1-成功 2-待审核
     */
    private String status;
    /**
     * IPS登录名
     */
    private String ipsLoginName;

    public String getMerBillNo() {
        return merBillNo;
    }

    public void setMerBillNo(String merBillNo) {
        this.merBillNo = merBillNo;
    }

    public String getIpsBillNo() {
        return ipsBillNo;
    }

    public void setIpsBillNo(String ipsBillNo) {
        this.ipsBillNo = ipsBillNo;
    }

    public String getIpsDoTime() {
        return ipsDoTime;
    }

    public void setIpsDoTime(String ipsDoTime) {
        this.ipsDoTime = ipsDoTime;
    }

    public String getIpsAcctNo() {
        return ipsAcctNo;
    }

    public void setIpsAcctNo(String ipsAcctNo) {
        this.ipsAcctNo = ipsAcctNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIpsLoginName() {
        return ipsLoginName;
    }

    public void setIpsLoginName(String ipsLoginName) {
        this.ipsLoginName = ipsLoginName;
    }
}
