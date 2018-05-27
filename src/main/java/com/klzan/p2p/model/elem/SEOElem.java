/*
 * Copyright $2016-2020 www.klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.model.elem;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Elem - SEO
 *
 * @author: chenxinglin  Date: 2016/10/19 Time: 17:15
 * @version: 1.0
 */
@Embeddable
public class SEOElem implements Serializable {

    /**
     * 页面标题
     */
    private String pageTitle;
    /**
     * 页面关键词
     */
    private String keywords;
    /**
     * 页面描述
     */
    private String description;

    public SEOElem() {
    }

    public SEOElem(String pageTitle, String keywords, String description) {
        this.pageTitle = pageTitle;
        this.keywords = keywords;
        this.description = description;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getDescription() {
        return description;
    }
}