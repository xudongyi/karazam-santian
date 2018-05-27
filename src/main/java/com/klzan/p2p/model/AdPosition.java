
/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity - 广告位
 *
 * @author: chenxinglin  Date: 2016/10/19 Time: 17:15
 */
@Entity
@Table(name = "karazam_ad_position")
public class AdPosition extends BaseModel {
    /**
     * 名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 标识
     */
    @Column(nullable = false)
    private String ident;

    /**
     * 是否内置
     */
    @Column(nullable = false)
    private Boolean builtin = false;

    /**
     * 描述
     */
    @Column(length = 200)
    private String description;

    public AdPosition() {
    }

    public AdPosition(String name, String ident, Boolean builtin, String description) {
        this.name = name;
        this.ident = ident;
        this.builtin = builtin;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getIdent() {
        return ident;
    }

    public Boolean getBuiltin() {
        return builtin;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public void setBuiltin(Boolean builtin) {
        this.builtin = builtin;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}