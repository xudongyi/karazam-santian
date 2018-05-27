package com.klzan.core.util;

import org.junit.Test;

import java.util.Date;

/**
 * Created by suhao on 2017/2/27.
 */
public class DateUtilsTest {
    @Test
    public void format() throws Exception {
        DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_MM_SS);
    }

}