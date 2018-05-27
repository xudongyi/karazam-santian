/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseSortModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity - 银行
 */
@Entity
@Table(name = "karazam_bank")
public class Bank extends BaseSortModel {
    /**
     * 名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 代码
     */
    @Column(nullable = false)
    private String code;

    /**
     * LOGO
     */
    private String logo;

    /**
     * 描述
     */
    private String description;

    public Bank() {
    }

    public Bank(String name, String code, String logo, String description) {
        this.name = name;
        this.code = code;
        this.logo = logo;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getLogo() {
        return logo;
    }

    public String getDescription() {
        return description;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void update(String name, String code) {
        this.name = name;
        this.code = code;
    }
}