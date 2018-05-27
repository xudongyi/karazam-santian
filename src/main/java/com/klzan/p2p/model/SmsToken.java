package com.klzan.p2p.model;

import com.klzan.p2p.enums.SmsType;
import com.klzan.p2p.model.base.BaseModel;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;

/**
 * 令牌
 */
@Entity
@Table(name = "karazam_sms_token")
public class SmsToken extends BaseModel {

    /**
     * 数量限制
     */
    public static final int COUNT = 10;

    /**
     * 类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SmsType type;

    /**
     * 通讯地址
     */
    @Column(nullable = false, unique = true)
    private String addr;

    /**
     * 代码
     */
    @NotBlank
    @Length(max = 200)
    @Column(nullable = false)
    private String code;

    /**
     * 到期时间
     */
    private Date expiry;

    /**
     * 重发时间
     */
    private Date retry;

    /**
     * 次数
     */
    @Column(nullable = false)
    private Integer count;

    public SmsType getType() {
        return type;
    }

    public void setType(SmsType type) {
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public Date getRetry() {
        return retry;
    }

    public void setRetry(Date retry) {
        this.retry = retry;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 判断是否过期
     *
     * @return 是否过期
     */
    @Transient
    public boolean getExpired() {
        return getExpiry() != null && getExpiry().before(new Date());
    }

    /**
     * 判断是否可重发
     *
     * @return 是否可重发
     */
    @Transient
    public boolean getRetried() {
        return getRetry() == null || getRetry().before(new Date());
    }

    /**
     * 验证重发
     *
     * @param type 方式
     * @return 重发是否允许
     */
    @Transient
    public boolean verifyRetry(SmsType type) {
        return getType() != type || getRetried();
    }

    /**
     * 验证
     *
     * @param type 方式
     * @param addr 通讯地址
     * @param code 代码
     * @return 验证是否通过
     */
    @Transient
    public boolean verify(SmsType type, String addr, String code) {
        return getType() == type && !getExpired() && StringUtils.equals(getCode(), code);
    }

}