package com.klzan.p2p.util;

import com.klzan.core.exception.BusinessProcessException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时表达式工具
 * Created by suhao Date: 2017/5/23 Time: 19:37
 *
 * @version: 1.0
 */
public class CronExpressionUtils {
    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    /**
     * 根据日期获取表达式
     * @param date 时间
     * @return cron类型的日期
     */
    public static String getCron(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * 根据表达式获取日期
     * @param cron Quartz cron的类型的日期
     * @return Date日期
     */
    public static Date getDate(final String cron) {
        if (cron == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        Date date;
        try {
            date = sdf.parse(cron);
        } catch (ParseException e) {
            throw new BusinessProcessException("表达式转换异常");
        }
        return date;
    }

    public static void main(String[] args) {
        System.out.println(getCron(new Date()));
    }
}
