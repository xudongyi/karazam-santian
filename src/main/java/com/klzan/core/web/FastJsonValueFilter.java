package com.klzan.core.web;

import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * Created by suhao Date: 2017/7/21 Time: 17:40
 *
 * @version: 1.0
 */
public class FastJsonValueFilter implements ValueFilter {
    /**
     * fastjson 值过滤器
     */
    @Override
    public Object process(Object object, String name, Object value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}