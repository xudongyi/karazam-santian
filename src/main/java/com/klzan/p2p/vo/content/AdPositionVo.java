package com.klzan.p2p.vo.content;

import com.klzan.p2p.common.vo.BaseVo;

/**
 * Created by zhu on 2016/12/18.
 */
public class AdPositionVo extends BaseVo {
    private String name;
    private String ident;
    private Boolean builtin;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public Boolean getBuiltin() {
        return builtin;
    }

    public void setBuiltin(Boolean builtin) {
        this.builtin = builtin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
