/*
 * Copyright 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.model;

import com.klzan.p2p.model.base.BaseModel;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity - 通知
 * 
 * @author Karazam Team
 * @version 1.0
 */
@Entity
@Table(name = "karazam_notice")
public class Notice extends BaseModel {

    /** 标题 */
    @NotBlank
    @Length(max = 200)
    @Column(nullable = false)
    private String title;

    /** 内容 */
    @NotBlank
    @Length(max = 500)
    @Column(nullable = false, updatable = false, length = 500)
    private String cont;

    /** 发布人 */
    @Column(nullable = false, updatable = false)
    private String publisher;

    /** IP */
    @Column(updatable = false)
    private String ip;

    /** 收件人 */
    private int receivers;

    /** 收件人 */
    @Transient
    private List<String> receiverList = new ArrayList<String>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getReceivers() {
        return receivers;
    }

    public void setReceivers(int receivers) {
        this.receivers = receivers;
    }

    public List<String> getReceiverList() {
        return receiverList;
    }

    public void setReceiverList(List<String> receiverList) {
        this.receiverList = receiverList;
    }

}