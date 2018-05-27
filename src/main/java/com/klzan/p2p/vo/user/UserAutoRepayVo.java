package com.klzan.p2p.vo.user;

/**
 * Created by suhao Date: 2017/5/26 Time: 16:35
 *
 * @version: 1.0
 */
public class UserAutoRepayVo extends AbstractUserMeta {
    /**
     * 是否签约
     */
    private Boolean autoRepaySign;
    /**
     * 授权号
     */
    private String authNo;
    /**
     * 有效期
     */
    private Integer validity;
    /**
     * 签约是否成功
     */
    private Boolean status;

    public Boolean getAutoRepaySign() {
        return autoRepaySign;
    }

    public void setAutoRepaySign(Boolean autoRepaySign) {
        this.autoRepaySign = autoRepaySign;
    }

    public String getAuthNo() {
        return authNo;
    }

    public void setAuthNo(String authNo) {
        this.authNo = authNo;
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
