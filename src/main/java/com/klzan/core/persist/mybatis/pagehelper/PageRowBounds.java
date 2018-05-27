package com.klzan.core.persist.mybatis.pagehelper;

import org.apache.ibatis.session.RowBounds;

public class PageRowBounds extends RowBounds {
    private Long total;

    public PageRowBounds(int offset, int limit) {
        super(offset, limit);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
