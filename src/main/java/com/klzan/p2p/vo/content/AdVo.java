package com.klzan.p2p.vo.content;

import com.klzan.core.util.StringUtils;
import com.klzan.p2p.common.vo.BaseVo;
import com.klzan.p2p.enums.AdType;

import java.util.Date;

/**
 * Created by zhu on 2016/12/28.
 */
public class AdVo extends BaseVo {

    private AdType type;
    private String typeStr;
    private String title;
    private String path;
    private String cont;
    private Date startDate;
    private Date endDate;
    private String url;
    private Integer position;
    private String positionName;
    private Integer sort;

    public AdType getType() {

        if (null==this.type && StringUtils.isNotBlank(typeStr)) {
            return AdType.valueOf(typeStr);
        }
        return type;
    }

    public void setType(AdType type) {
        this.type = type;
    }

    public String getTypeStr() {
        if (StringUtils.isNotBlank(typeStr)) {
            return AdType.valueOf(typeStr).getDisplayName();
        }
        return typeStr;
    }
    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
