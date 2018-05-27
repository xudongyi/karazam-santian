package com.klzan.core;

/**
 * @class : CommonAttrs
 * @description: 公共参数
 */
public final class CommonAttrs {

    /** 日期格式配比 */
    public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd",
            "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };

    /**
     * 不可实例化
     */
    private CommonAttrs() {
    }

}