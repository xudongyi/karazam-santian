/*
 * Copyright 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.setting;

import java.io.Serializable;

/**
 * Setting - 内容设置
 */
public class PostsSetting implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 982827572983485140L;

    /** 内容url前缀 */
    private String contentUrlPrefix;

    /** 类别url前缀 */
    private String taxonomyUrlPrefix;

    public String getContentUrlPrefix() {
        return contentUrlPrefix;
    }

    public void setContentUrlPrefix(String contentUrlPrefix) {
        this.contentUrlPrefix = contentUrlPrefix;
    }

    public String getTaxonomyUrlPrefix() {
        return taxonomyUrlPrefix;
    }

    public void setTaxonomyUrlPrefix(String taxonomyUrlPrefix) {
        this.taxonomyUrlPrefix = taxonomyUrlPrefix;
    }
}