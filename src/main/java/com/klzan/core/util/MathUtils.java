package com.klzan.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

public strictfp class MathUtils {

    private MathUtils() {
    }

    /**
     * 默认运算精度
     */
    private static final int DEF_SCALE = 10;

    /**
     * ROUNDING_MODE
     */
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;

    /**
     * 提供数据类型转换为BigDecimal
     *
     * @param object 原始数据
     * @return BigDecimal
     */
    public static BigDecimal toBigDecimal(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
        BigDecimal result;
        try {
            result = new BigDecimal(String.valueOf(object).replaceAll(",", ""));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please give me a numeral.Not " + object);
        }
        return result;
    }

    /**
     * 提供(相对)精确的加法运算。
     *
     * @param num1 被加数
     * @param num2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(Object num1, Object num2) {
        BigDecimal result = toBigDecimal(num1).add(toBigDecimal(num2));
        return result.setScale(DEF_SCALE, ROUNDING_MODE);
    }

    /**
     * 提供(相对)精确的加法运算。
     *
     * @param num1 被加数
     * @param num2 加数
     * @param scale 精度
     * @return 两个参数的和
     */
    public static BigDecimal add(Object num1, Object num2, Integer scale) {
        if (scale == null) {
            scale = DEF_SCALE;
        }
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal result = toBigDecimal(num1).add(toBigDecimal(num2));
        return result.setScale(scale, ROUNDING_MODE);
    }

    /**
     * 提供(相对)精确的减法运算。
     *
     * @param num1 被减数
     * @param num2 减数
     * @return 两个参数的差
     */
    public static BigDecimal subtract(Object num1, Object num2) {
        BigDecimal result = toBigDecimal(num1).subtract(toBigDecimal(num2));
        return result.setScale(DEF_SCALE, ROUNDING_MODE);
    }

    /**
     * 提供(相对)精确的减法运算。
     *
     * @param num1 被减数
     * @param num2 减数
     * @param scale 精度
     * @return 两个参数的差
     */
    public static BigDecimal subtract(Object num1, Object num2, Integer scale) {
        if (scale == null) {
            scale = DEF_SCALE;
        }
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal result = toBigDecimal(num1).subtract(toBigDecimal(num2));
        return result.setScale(scale, ROUNDING_MODE);
    }

    /**
     * 提供(相对)精确的乘法运算。
     *
     * @param num1 被乘数
     * @param num2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal multiply(Object num1, Object num2) {
        BigDecimal result = toBigDecimal(num1).multiply(toBigDecimal(num2));
        return result.setScale(DEF_SCALE, ROUNDING_MODE);
    }

    /**
     * 提供(相对)精确的乘法运算。
     *
     * @param num1 被乘数
     * @param num2 乘数
     * @param scale 精度
     * @return 两个参数的积
     */
    public static BigDecimal multiply(Object num1, Object num2, Integer scale) {
        if (scale == null) {
            scale = DEF_SCALE;
        }
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal result = toBigDecimal(num1).multiply(toBigDecimal(num2));
        return result.setScale(scale, ROUNDING_MODE);
    }

    /**
     * 提供(相对)精确的除法运算，当发生除不尽的情况时，精度为10位，以后的数字四舍五入。
     *
     * @param num1 被除数
     * @param num2 除数
     * @return 两个参数的商
     */
    public static BigDecimal divide(Object num1, Object num2) {
        return divide(num1, num2, DEF_SCALE);
    }

    /**
     * 提供(相对)精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param num1 被除数
     * @param num2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static BigDecimal divide(Object num1, Object num2, Integer scale) {
        if (scale == null) {
            scale = DEF_SCALE;
        }
        num2 = num2 == null || Math.abs(new Double(num2.toString())) == 0 ? 1 : num2;
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal result = toBigDecimal(num1).divide(toBigDecimal(num2), scale, ROUNDING_MODE);
        return result;
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param num 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(Object num, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal result = toBigDecimal(num).divide(toBigDecimal("1"), scale, ROUNDING_MODE);
        return result;
    }

    /**
     * 获取start到end区间的随机数,不包含start+end
     *
     * @param start
     * @param end
     * @return
     */
    public static BigDecimal getRandom(int start, int end) {
        return new BigDecimal(start + Math.random() * end);
    }

    /**
     * 格式化
     *
     * @param obj
     * @param pattern
     * @return
     */
    public static String format(Object obj, String pattern) {
        if (obj == null) {
            return null;
        }
        if (pattern == null || "".equals(pattern)) {
            pattern = "#";
        }
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(toBigDecimal(obj));
    }

    /**
     * 格式化为2位小数
     *
     * @param obj
     * @param min
     * @param max
     * @return
     */
    public static String format(Object obj, Integer min, Integer max) {
        if (obj == null) {
            return null;
        }
        if (min == null) {
            min = 0;
        }
        if (max == null) {
            max = 0;
        }
        DecimalFormat df=(DecimalFormat) NumberFormat.getInstance();
        df.setMaximumFractionDigits(max);
        df.setMinimumFractionDigits(min);
        return df.format(obj);
    }

    /** 是否数字 */
    public static boolean isNumber(Object object) {
        Pattern pattern = Pattern.compile("\\d+(.\\d+)?$");
        return pattern.matcher(object.toString()).matches();
    }

    public static void main(String[] args) {
        System.out.println(add(1.01, 2.025, 2));
        System.out.println(divide(10, 3));
    }
}
