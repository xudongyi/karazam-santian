package com.klzan.core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Utils - 日期/时间转换
 *
 * @author suhao
 * @version 1.0
 */
public final class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public final static String YYYY = "yyyy";
    public final static String MM = "MM";
    public final static String DD = "dd";
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String YYYY_MM = "yyyy-MM";
    public final static String HH_MM_SS = "HH:mm:ss";
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String DATE_PATTERN_DEFAULT_S = "yyyy-MM-dd HH:mm:ss.S";
    public static String DATE_PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_PATTERN_NUMBER = "yyyyMMddHHmmss";
    public static String DATE_PATTERN_NUMBER_S = "yyyyMMddHHmmssSSS";
    public static String DATE_PATTERN_yyyyMMddHHmm = "yyyy-MM-dd HH:mm";
    public static String DATE_PATTERN_yyyyMMddHH = "yyyy-MM-dd HH";
    public static String DATE_PATTERN_yyyyMMdd = "yyyy-MM-dd";
    public static String DATE_PATTERN_NUMBER_yyyyMMdd = "yyyyMMdd";
    public static String[] formatStr = {DATE_PATTERN_DEFAULT, DATE_PATTERN_yyyyMMddHHmm, DATE_PATTERN_yyyyMMddHH, DATE_PATTERN_yyyyMMdd};


    /**
     * 日期格式化－将<code>Date</code>类型的日期格式化为<code>String</code>型
     *
     * @param date
     *            待格式化的日期
     * @param pattern
     *            时间样式
     * @return 一个被格式化了的<code>String</code>日期
     */
    public static String format(Date date, String pattern) {
        if (date == null)
            return "";
        else
            return getFormatter(pattern).format(date);
    }

    /**
     * 默认把日期格式化成yyyy-mm-dd格式
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        if (date == null)
            return "";
        else
            return getFormatter(YYYY_MM_DD).format(date);
    }

    /**
     * 把字符串日期默认转换为yyyy-mm-dd格式的Data对象
     *
     * @param strDate
     * @return
     */
    public static Date format(String strDate) {
        Date d = null;
        if (strDate == "")
            return null;
        else
            try {
                d = getFormatter(YYYY_MM_DD).parse(strDate);
            } catch (ParseException pex) {
                return null;
            }
        return d;
    }

    /**
     * 把字符串日期转换为f指定格式的Data对象
     *
     * @param strDate
     *            ,f
     * @return
     */
    public static Date format(String strDate, String f) {
        Date d = null;
        if (strDate == "")
            return null;
        else
            try {
                d = getFormatter(f).parse(strDate);
            } catch (ParseException pex) {
                return null;
            }
        return d;
    }

    /**
     * 日期解析－将<code>String</code>类型的日期解析为<code>Date</code>型
     *
     * @param strDate
     *            待格式化的日期
     * @param pattern
     *            日期样式
     * @exception ParseException
     *                如果所给的字符串不能被解析成一个日期
     * @return 一个被格式化了的<code>Date</code>日期
     */
    public static Date parse(String strDate, String pattern) throws ParseException {
        try {
            return getFormatter(pattern).parse(strDate);
        } catch (ParseException pe) {
            throw new ParseException("Method parse in Class DateUtils err: parse strDate fail.", pe.getErrorOffset());
        }
    }

    /**
     * 获取当前日期
     *
     * @return 一个包含年月日的<code>Date</code>型日期
     */
    public static synchronized Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    /**
     * 获取当前日期
     *
     * @return 一个包含年月日的<code>String</code>型日期，但不包含时分秒。yyyy-mm-dd
     */
    public static String getCurrentDateStr() {
        return format(getCurrentDate(), YYYY_MM_DD);
    }

    /**
     * 获取当前时间
     *
     * @return 一个包含年月日时分秒的<code>String</code>型日期。hh:mm:ss
     */
    public static String getCurrentTimeStr() {
        return format(getCurrentDate(), HH_MM_SS);
    }

    /**
     * 获取当前完整时间,样式: yyyy－MM－dd hh:mm:ss
     *
     * @return 一个包含年月日时分秒的<code>String</code>型日期。yyyy-MM-dd hh:mm:ss
     */
    public static String getCurrentDateTimeStr() {
        return format(getCurrentDate(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前年分 样式：yyyy
     *
     * @return 当前年分
     */
    public static String getYearOfDate() {
        return format(getCurrentDate(), YYYY);
    }

    /**
     * 返回一个时间的年份整数
     *
     * @param d
     *            日期对象
     * @return 年份
     */
    public static int getYearOfDate(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月分 样式：MM
     *
     * @return 当前月分
     */
    public static String getMonthOfYear() {
        return format(getCurrentDate(), MM);
    }

    /**
     * 返回一个时间的月份整数
     *
     * @param d
     *            日期对象
     * @return 月份
     */
    public static int getMonthOfYear(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日期号 样式：dd
     *
     * @return 当前日期号
     */
    public static String getDayOfMonth() {
        return format(getCurrentDate(), DD);
    }

    /**
     * 返回一个时间的天份整数，是这个月的第几天
     *
     * @param d
     *            日期对象
     * @return 天份
     */
    public static int getDayOfMonth(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回一个时间的天份整数，是这个年份的第几天
     *
     * @param d
     *            日期对象
     * @return 天份
     */
    public static int getDayOfYear(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取指定日期的1号0点0分0秒
     *
     * @param d
     *            指定日期
     * @return 指定日期的0点0分0秒
     */
    public static Date getDateByFirstDayOfMonth(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 返回一个时间的天份整数，是这个周的第几天
     *
     * @param d
     *            日期对象
     * @return 天份
     */
    public static int getDayOfWeek(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 返回一个时间的周的整数，是这个月的第几周
     *
     * @param d
     *            日期对象
     * @return 周
     */
    public static int getWeekOfMonth(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 返回一个时间的周的整数，是这个年份的第几周
     *
     * @param d
     *            日期对象
     * @return 周
     */
    public static int getWeekOfYear(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 返回该时间所对应的在一天中的小时数的整数，如当前(Date now)是下午3点，返回为15
     *
     * @param d
     *            日期对象
     * @return 小时
     */
    public static int getHoursOfDay(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        return hours;
    }

    /**
     * 返回该时间所对应的在一天中的小时数的整数(采用12小时制)，如当前(Date now)是下午3点，返回为3
     *
     * @param d
     *            日期对象
     * @return 小时
     */
    public static int getHoursOfDay12(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int hours = calendar.get(Calendar.HOUR);
        return hours;
    }

    /**
     * 返回该时间所对应的分钟数中的整数，如now是15点14分，则返回14
     *
     * @param d
     *            日期对象
     * @return 分钟
     */
    public static int getMinutesOfHour(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int minutes = calendar.get(Calendar.MINUTE);

        return minutes;
    }

    /**
     * 返回该时间所对应的秒数中的整数，如now是15点14分34秒，则返回34
     *
     * @param d
     *            日期对象
     * @return 秒
     */
    public static int getSecondsOfMinute(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int seconds = calendar.get(Calendar.SECOND);

        return seconds;
    }

    /**
     * 返回该时间所对应的毫秒数中的整数，如now是15点14分34秒470毫秒，则返回470
     *
     * @param d
     *            日期对象
     * @return 毫秒
     */
    public static int getMillisecondsOfSecond(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int millisecond = calendar.get(Calendar.MILLISECOND);

        return millisecond;
    }

    /**
     * 返回当前时间相对于1970年1月1日开始计算的对应的毫秒数
     * @return
     */
    public static long getTime() {
        return getTime(getCurrentDate());
    }

    /**
     * 返回该时间相对于1970年1月1日开始计算的对应的毫秒数
     *
     * @param d
     *            日期对象
     * @return 毫秒数
     */
    public static long getTime(Date d) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        return d.getTime();
    }

    /**
     * 比较两个时间的先后顺序。 如果时间d1在d2之前，返回1，如果时间d1在d2之后，返回-1，如果二者相等，返回0
     *
     * @param d1
     *            日期对象
     * @param d2
     *            日期对象
     * @return 如果时间d1在d2之前，返回1，如果时间d1在d2之后，返回-1，如果二者相等，返回0
     */
    public static int compareTwoDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("参数d1或d2不能是null对象！");
        }

        long dI1 = d1.getTime();
        long dI2 = d2.getTime();

        if (dI1 > dI2) {
            return -1;
        } else if (dI1 < dI2) {
            return 1;
        } else {
            return 0;
        }

    }

    /**
     * 返回两个日期之间的毫秒数的差距
     *
     * @param d1
     *            日期对象
     * @param d2
     *            日期对象
     * @return 二者至1970年1.1后的毫秒数的差值
     */
    public static long getMillisecondsOfTwoDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("参数d1或d2不能是null对象！");
        }
        long dI1 = d1.getTime();
        long dI2 = d2.getTime();
        return (dI1 - dI2);
    }

    /**
     * 获得两个日期之间相差的秒数
     *
     * @param d1
     *            日期对象
     * @param d2
     *            日期对象
     * @return 两日期之间相差的秒数
     */
    public static double getSecondsOfTwoDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("参数d1或d2不能是null对象！");
        }
        long i = getMillisecondsOfTwoDate(d1, d2);

        return (double) i / 1000;
    }

    /**
     * 获得两个日期之间相差的分钟数
     *
     * @param d1
     *            日期对象
     * @param d2
     *            日期对象
     * @return 两日期之间相差的分钟数
     */
    public static double getMinutesOfTwoDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("参数d1或d2不能是null对象！");
        }
        long millions = getMillisecondsOfTwoDate(d1, d2);
        return (double) millions / 60 / 1000;
    }

    /**
     * 获得两个日期之间相差的小时数
     *
     * @param d1
     *            日期对象
     * @param d2
     *            日期对象
     * @return 两日期之间相差的小时数
     */
    public static double getHoursOfTwoDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("参数d1或d2不能是null对象！");
        }
        long millions = getMillisecondsOfTwoDate(d1, d2);
        return (double) millions / 60 / 60 / 1000;
    }

    /**
     * 获得两个日期之间相差的天数
     *
     * @param d1
     *            日期对象
     * @param d2
     *            日期对象
     * @return 两日期之间相差的天数
     */
    public static double getDaysOfTwoDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("参数d1或d2不能是null对象！");
        }
        long millions = getMillisecondsOfTwoDate(d1, d2);
        return (double) millions / 24 / 60 / 60 / 1000;
    }

    /**
     * 获得两个日期之间相差的月数
     *
     * @param d1
     *            日期对象
     * @param d2
     *            日期对象
     * @return 两日期之间相差的月数
     */
    public static int getMonthsOfTwoDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            throw new IllegalArgumentException("参数d1或d2不能是null对象！");
        }
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if(month1 < month2 || month1 == month2 && day1 < day2) yearInterval --;
        // 获取月数差值
        int monthInterval =  (month1 + 12) - month2  ;
        if(day1 < day2) monthInterval --;
        monthInterval %= 12;
        return Math.abs(yearInterval * 12 + monthInterval);
    }

    /**
     * 把给定的时间加上指定的时间值，可以为负
     *
     * @param d
     *            需要设定的日期对象
     * @param times
     *            时间值
     * @param type
     *            类型，Calendar.MILLISECOND，毫秒<BR>
     *            Calendar.SECOND，秒<BR>
     *            Calendar.MINUTE，分钟<BR>
     *            Calendar.HOUR，小时<BR>
     *            Calendar.DATE，日<BR>
     * @return 如果d为null，返回null
     */
    public static Date addTime(Date d, double times, int type) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        long qv = 1;
        switch (type) {
            case Calendar.MILLISECOND:
                qv = 1;
                break;
            case Calendar.SECOND:
                qv = 1000;
                break;
            case Calendar.MINUTE:
                qv = 1000 * 60;
                break;
            case Calendar.HOUR:
                qv = 1000 * 60 * 60;
                break;
            case Calendar.DATE:
                qv = 1000 * 60 * 60 * 24;
                break;
            default:
                throw new RuntimeException("时间类型不正确!type＝" + type);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        long milliseconds = (long) Math.round(Math.abs(times) * qv);
        if (times > 0) {
            for (; milliseconds > 0; milliseconds -= 2147483647) {
                if (milliseconds > 2147483647) {
                    calendar.add(Calendar.MILLISECOND, 2147483647);
                } else {
                    calendar.add(Calendar.MILLISECOND, (int) milliseconds);
                }
            }
        } else {
            for (; milliseconds > 0; milliseconds -= 2147483647) {
                if (milliseconds > 2147483647) {
                    calendar.add(Calendar.MILLISECOND, -2147483647);
                } else {
                    calendar.add(Calendar.MILLISECOND, -(int) milliseconds);
                }
            }
        }
        return calendar.getTime();
    }

    /**
     * 把给定的时间加上指定的年份，可以为负，返回新的被加上了年份的日期对象，不影响参数日期对象值
     *
     * @param d
     *            需要设定的日期对象
     * @param years
     *            年份
     * @return 新日期对象
     */
    public static Date addYears(Date d, int years) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    /**
     * 把给定的时间加上指定的月份，可以为负
     *
     * @param d
     *            需要设定的日期对象
     * @param months
     *            月份
     * @return 新日期对象
     */
    public static Date addMonths(Date d, int months) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * 把给定的时间加上指定的天数，可以为负
     *
     * @param d
     *            需要设定的日期对象
     * @param days
     *            天数
     * @return 新日期对象
     */
    public static Date addDays(Date d, int days) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.HOUR, days * 24);
        return calendar.getTime();
    }

    /**
     * 把给定的时间加上指定的小时，可以为负
     *
     * @param d
     *            需要设定的日期对象
     * @param hours
     *            小时
     * @return 新日期对象
     */
    public static Date addHours(Date d, int hours) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * 把给定的时间加上指定的分钟，可以为负
     *
     * @param d
     *            需要设定的日期对象
     * @param minutes
     *            分钟
     * @return 新日期对象
     */
    public static Date addMinutes(Date d, int minutes) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 把给定的时间加上指定的秒数，可以为负
     *
     * @param d
     *            需要设定的日期对象
     * @param seconds
     *            秒
     * @return 新日期对象
     */
    public static Date addSeconds(Date d, int seconds) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 把给定的时间加上指定的毫秒数，可以为负
     *
     * @param d
     *            需要设定的日期对象
     * @param milliseconds
     *            毫秒
     * @return 新日期对象
     */
    public static Date addMilliseconds(Date d, int milliseconds) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MILLISECOND, milliseconds);
        return calendar.getTime();
    }

    /**
     * 设置一个日期对象的年份是新的给定的年份
     *
     * @param d
     *            需要设定的日期对象
     * @param year
     *            新的年份
     * @return 新日期对象
     */
    public static Date setYearOfDate(Date d, int year) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 设置一个日期对象的月份是新的给定的月份
     *
     * @param d
     *            需要设定的日期对象
     * @param month
     *            新的月份
     * @return 新日期对象
     */
    public static Date setMonthOfDate(Date d, int month) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 设置一个日期对象的天是新的给定的天
     *
     * @param d
     *            需要设定的日期对象
     * @param day
     *            新的天
     * @return 新日期对象
     */
    public static Date setDayOfDate(Date d, int day) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 设置一个日期对象的小时是新的给定的小时
     *
     * @param d
     *            需要设定的日期对象
     * @param hour
     *            新的小时数
     * @return 新日期对象
     */
    public static Date setHourOfDate(Date d, int hour) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    /**
     * 设置一个日期对象的分钟是新的给定的分钟数
     *
     * @param d
     *            需要设定的日期对象
     * @param minute
     *            新的分钟数
     * @return 新日期对象
     */
    public static Date setMinuteOfDate(Date d, int minute) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 设置一个日期对象的秒数是新的给定的分钟数
     *
     * @param d
     *            需要设定的日期对象
     * @param second
     *            新的秒数
     * @return 新日期对象
     */
    public static Date setSecondOfDate(Date d, int second) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 设置一个日期对象的毫秒数是新的给定的分钟数
     *
     * @param d
     *            需要设定的日期对象
     * @param millisecond
     *            新的毫秒数
     * @return 新日期对象
     */
    public static Date setMillisecondOfDate(Date d, int millisecond) {
        if (d == null) {
            throw new IllegalArgumentException("参数d不能是null对象！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

    /**
     * 返回指定日期的月份的天数量
     *
     * @param d
     *            日期对象
     * @return 天数
     */
    public static int getDaysOfMonth(Date d) {
        int year = getYearOfDate(d);
        int month = getMonthOfYear(d);
        return getDaysOfMonth(year, month);
    }

    /**
     * 返回指定日期的月份的天数量
     *
     * @param year
     *            年
     * @param month
     *            月
     * @return 天数
     */
    public static int getDaysOfMonth(int year, int month) {
        int days = 0;
        if (month == 2) {
            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                days = 29;
            } else {
                days = 28;
            }
        }
        if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
            days = 30;
        }
        if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10)
                || (month == 12)) {
            days = 31;
        }
        return days;
    }

    /**
     * 获取当前日期的 0点0分0秒
     * @return
     */
    public static Date getZeroDate() {
        return getZeroDate(getCurrentDate());
    }

    /**
     * 获取指定日期的 0点0分0秒
     *
     * @param d
     *            指定日期
     * @return 指定日期的0点0分0秒
     */
    public static Date getZeroDate(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 按给定日期样式判断给定字符串是否为合法日期数据
     *
     * @param strDate
     *            要判断的日期
     * @param pattern
     *            日期样式
     * @return true 如果是，否则返回false
     */
    public static boolean isDate(String strDate, String pattern) {
        try {
            parse(strDate, pattern);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式年份（格式：yyyy）数据
     *
     * @param strDate
     *            要判断的日期
     * @return true 如果是，否则返回false
     */
    public static boolean isYYYY(String strDate) {
        try {
            parse(strDate, YYYY);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    public static boolean isYYYY_MM(String strDate) {
        try {
            parse(strDate, YYYY_MM);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式的年月日（格式：yyyy-MM-dd）数据
     *
     * @param strDate
     *            要判断的日期
     * @return true 如果是，否则返回false
     */
    public static boolean isYYYY_MM_DD(String strDate) {
        try {
            parse(strDate, YYYY_MM_DD);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式年月日时分秒（格式：yyyy-MM-dd HH:mm:ss）数据
     *
     * @param strDate
     *            要判断的日期
     * @return true 如果是，否则返回false
     */
    public static boolean isYYYY_MM_DD_HH_MM_SS(String strDate) {
        try {
            parse(strDate, YYYY_MM_DD_HH_MM_SS);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 判断给定字符串是否为特定格式时分秒（格式：HH:mm:ss）数据
     *
     * @param strDate
     *            要判断的日期
     * @return true 如果是，否则返回false
     */
    public static boolean isHH_MM_SS(String strDate) {
        try {
            parse(strDate, HH_MM_SS);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    /**
     * 获取一个简单的日期格式化对象
     *
     * @return 一个简单的日期格式化对象
     */
    private static SimpleDateFormat getFormatter(String parttern) {
        return new SimpleDateFormat(parttern);
    }

    /**
     * 获取给定日前的后intevalDay天的日期
     *
     * @param refenceDate
     *            给定日期（格式为：yyyy-MM-dd）
     * @param intevalDays
     *            间隔天数
     * @return 计算后的日期
     */
    public static String getNextDate(String refenceDate, int intevalDays) {
        try {
            return getNextDate(parse(refenceDate, YYYY_MM_DD), intevalDays);
        } catch (Exception ee) {
            return "";
        }
    }

    /**
     * 获取给定日前的后intevalDay天的日期
     *
     * @param refenceDate
     *            Date 给定日期
     * @param intevalDays
     *            int 间隔天数
     * @return String 计算后的日期
     */
    public static String getNextDate(Date refenceDate, int intevalDays) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(refenceDate);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + intevalDays);
            return format(calendar.getTime(), YYYY_MM_DD);
        } catch (Exception ee) {
            return "";
        }
    }

    /**
     * 获取两个日期之间的天娄
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getIntevalDays(String startDate, String endDate) {
        try {
            return getIntevalDays(parse(startDate, YYYY_MM_DD), parse(endDate, YYYY_MM_DD));
        } catch (Exception ee) {
            return 0l;
        }
    }

    /**
     * 获取两个日期之间的天娄
     * @param startDate
     * @param endDate
     * @return
     */
    public static long getIntevalDays(Date startDate, Date endDate) {
        try {
            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();

            startCalendar.setTime(startDate);
            endCalendar.setTime(endDate);
            long diff = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();

            return (diff / (1000 * 60 * 60 * 24));
        } catch (Exception ee) {
            return 0l;
        }
    }

    /**
     * 求当前日期和指定字符串日期的相差天数
     *
     * @param startDate
     * @return
     */
    public static long getTodayIntevalDays(String startDate) {
        try {
            // 当前时间
            Date currentDate = new Date();

            // 指定日期
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date theDate = myFormatter.parse(startDate);

            // 两个时间之间的天数
            long days = (currentDate.getTime() - theDate.getTime()) / (24 * 60 * 60 * 1000);

            return days;
        } catch (Exception ee) {
            return 0l;
        }
    }

    public static Date parseToDate(String dateTimeStr) {
        if (dateTimeStr == null)
            return null;
        Date d = null;
        int formatStrLength = formatStr.length;
        for (int i = 0; i < formatStrLength; i++) {
            d = parseToDate(dateTimeStr, formatStr[i]);
            if (d != null) {
                break;
            }
        }
        return d;
    }

    private static Date parseToDate(String dateTimeStr, String formatString) {
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        try {
            d = sdf.parse(dateTimeStr);
        } catch (ParseException pe) {

        }
        return d;
    }

    /**
     * 根据给出的Date格式化时间
     * @param datetime
     * @return
     */
    public static String formatDateTimeToString(Date datetime) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(datetime);
        String dateTime = calendar.get(Calendar.YEAR) + "" + (calendar.get(Calendar.MONTH) + 1 > 9 ? "" : "0") + (calendar.get(Calendar.MONTH) + 1) + ""
                + (calendar.get(Calendar.DATE) > 9 ? "" : "0") + calendar.get(Calendar.DATE) + "" + (calendar.get(Calendar.HOUR_OF_DAY) > 9 ? "" : "0")
                + calendar.get(Calendar.HOUR_OF_DAY) + "" + (calendar.get(Calendar.MINUTE) > 9 ? "" : "0") + calendar.get(Calendar.MINUTE) + ""
                + (calendar.get(Calendar.SECOND) > 9 ? "" : "0") + calendar.get(Calendar.SECOND);
        return dateTime;
    }

    /**
     * 根据给出的Date值字符串和格式串采用操作系统的默认所在的国家风格来格式化时间，并返回相应的字符串
     *
     * @param dateStr
     *            日期字符串
     * @param formatStr
     *            日期格式
     * @return 如果为null，返回""
     * @throws Exception
     *             可能抛出的异常
     */
    public static String formatDateTimeToString(String dateStr, String formatStr) throws Exception {
        String dStr = "";
        if (dateStr != null && dateStr.trim().length() > 0 && formatStr != null && formatStr.trim().length() > 0) {
            dStr = formatDateTimeToString(parseToDate(dateStr), formatStr);
        }
        return dStr;
    }

    /**
     * 根据给出的Date值和格式串采用操作系统的默认所在的国家风格来格式化时间，并返回相应的字符串
     *
     * @param date
     *            日期对象
     * @param formatStr
     *            日期格式
     * @return 如果为null，返回字符串""
     */
    public static String formatDateTimeToString(Date date, String formatStr) {
        String reStr = "";
        if (date == null || formatStr == null || formatStr.trim().length() < 1) {
            return reStr;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(formatStr);
        reStr = sdf.format(date);
        return reStr == null ? "" : reStr;
    }

    /**
     * 由年、月份，获得当前月的最后一天
     *
     * @param year
     *            month 月份 01 02 11 12
     * @return
     * @throws ParseException
     */
    public static String getLastDayOfMonth(String year, String month) throws ParseException {
        String LastDay = "";
        Calendar cal = Calendar.getInstance();
        Date date_;
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-14");
        cal.setTime(date);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        date_ = cal.getTime();
        LastDay = new SimpleDateFormat("yyyy-MM-dd").format(date_);
        return LastDay;
    }

    /**
     * 获取当前日期
     *
     * @return Date
     */
    public static Timestamp getTimestamp() {
        return getTimestamp(getCurrentDate());
    }

    /**
     * 获取指定日期
     * @param date
     * @return
     */
    public static Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 取得一个date对象对应的日期的0分0秒时刻的Date对象。
     */
    public static Date getMinDateOfHour(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 取得一个date对象对应的日期的0点0分0秒时刻的Date对象。
     */
    public static Date getMinDateOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getMinDateOfHour(date));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        return calendar.getTime();
    }

    /**
     * 取得一月中的最早一天。
     */
    public static Date getMinDateOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 取得一月中的最后一天
     */
    public static Date getMaxDateOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 取得一个date对象对应的日期的23点59分59秒时刻的Date对象。
     */
    public static Date getMaxDateOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar.getTime();
    }

    /**
     * 是否是闰年
     * @return
     */
    public static boolean isLeapYear(Date date){
        int year = getDayOfYear(date);
        return (year% 4 == 0 && year % 100 != 0) || (year% 400 == 0);
    }

    public static Calendar calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * debug
     */
    public static void main(String[] args) {
        try {
            System.out.println("当前日期：" + DateUtils.getCurrentDateStr());
            System.out.println("日期格式化：" + DateUtils.format(new Date(), DateUtils.DATE_PATTERN_NUMBER));
            System.out.println("短日期：" + DateUtils.format(new Date()));
            System.out.println("长日期：" + DateUtils.getCurrentDateTimeStr());
            System.out.println("日：" + DateUtils.getDayOfMonth());
            System.out.println("月：" + DateUtils.getMonthOfYear());
            System.out.println("年：" + DateUtils.getYearOfDate());
            System.out.println("月未最后一天：" + DateUtils.getLastDayOfMonth("2010", "08"));
            System.out.println("相差几天：" + DateUtils.getIntevalDays("2010-08-01", "2010-08-21"));

            System.out.println("当前日期后的几天：" + DateUtils.getNextDate("2010-08-01", -3));
            System.out.println("与今天相差几天：" + DateUtils.getTodayIntevalDays("2010-08-01"));
            System.out.println("与今天相差几天1：" + DateUtils.formatDateTimeToString(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}