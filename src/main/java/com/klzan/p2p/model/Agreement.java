package com.klzan.p2p.model;

import com.klzan.p2p.enums.AgreementType;
import com.klzan.p2p.model.base.BaseModel;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * 协议
 */
@Entity
@Table(name = "karazam_agreement")
public class Agreement extends BaseModel {

    /**
     * 协议名称
     */
    @Column(nullable = false)
    private String name;
    /**
     * 协议类型
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgreementType type;
    /**
     * 协议版本号
     */
    @Column(nullable = false)
    private String agreementVersion;
    /**
     * 协议内容
     */
    @Column(nullable = false)
    @Lob
    private String content;
    /**
     * 协议描述
     */
    private String description;

    public AgreementType getType() {
        return type;
    }

    public void setType(AgreementType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgreementVersion() {
        return agreementVersion;
    }

    public void setAgreementVersion(String agreementVersion) {
        this.agreementVersion = agreementVersion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}