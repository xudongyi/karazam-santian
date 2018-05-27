package com.klzan.p2p.vo.posts;

import java.io.Serializable;

/**
 * Created by Sue on 2017/6/4.
 */
public class PostsTemplateVo implements Serializable {
    private String use;
    private String name;
    private String des;

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
