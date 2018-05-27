package com.klzan.p2p.vo.notice;

import com.klzan.p2p.common.vo.BaseVo;

/**
 * Created by zhu on 2016/12/7.
 */
public class NoticeVo extends BaseVo {
    private String title;
    private String cont;
    private String publisher;
    private String ip;
    private int receivers;

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

}
