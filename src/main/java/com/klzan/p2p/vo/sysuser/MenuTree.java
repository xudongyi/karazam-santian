package com.klzan.p2p.vo.sysuser;

import com.klzan.core.util.StringUtils;

import java.io.Serializable;
import java.util.List;

public class MenuTree implements Serializable {
    private Integer id;
    private Integer pid;
    private Integer sort;
    private String text;
    private String url;
    private String iconCls;
    private String state;
    private List<MenuTree> children;

    public MenuTree(Integer id, Integer pid, Integer sort, String text, String url, String iconCls, List<MenuTree> children) {
        this.id = id;
        this.pid = pid;
        this.sort = sort;
        this.text = text;
        this.url = url;
        this.iconCls = StringUtils.isBlank(iconCls) ? "fa fa-file-archive-o" : iconCls;
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPid() {
        return pid;
    }

    public Integer getSort() {
        return sort;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public String getIconCls() {
        return iconCls;
    }

    public List<MenuTree> getChildren() {
        return children;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
