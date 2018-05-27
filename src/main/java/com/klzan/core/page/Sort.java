package com.klzan.core.page;

/**
 * 排序
 */
public class Sort {

    /**
     * 数据库排序字段
     */
    private String sortName;

    /**
     * 排序顺序 asc desc
     */
    private String order;

    public Sort() {
    }

    public Sort(String sortName, String order) {
        this.sortName = sortName;
        this.order = order;
    }

    public String getSortName() {
        return sortName==null?"":sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getOrder() {
        return order==null?"":order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
