package com.klzan.p2p.model.base;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity - 基类
 *
 * @author suhao
 * @version 1.0
 */
@MappedSuperclass
public abstract class BaseModel implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -3703154275817120085L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 创建日期 */
    @Column(length = 19, nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate = new Date();

    /** 修改日期 */
    @Column(length = 19)
    @UpdateTimestamp
    private Date modifyDate = new Date();

    @Version
    @Column(name="version", nullable = false)
    private Integer version;

    private Boolean deleted = Boolean.FALSE;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 生成HashCode
     *
     * @return HashCode
     */
    @Override
    @Transient
    public int hashCode() {
        return 17 + (isEmpty() ? 0 : getId().hashCode() * 31);
    }

    /**
     * 判断是否相等
     *
     * @param obj
     *            对象
     * @return 是否相等
     */
    @Override
    @Transient
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (isEmpty() || obj == null || !getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        BaseModel model = (BaseModel) obj;
        if (model.isEmpty()) {
            return false;
        }
        return getId().equals(model.getId());
    }

    /**
     * 判断是否为空
     *
     * @return 是否为空
     */
    @Transient
    public boolean isEmpty() {
        return getId() == null;
    }

    @PreUpdate
    public void prePersist() {
        this.modifyDate = new Date();
    }
}