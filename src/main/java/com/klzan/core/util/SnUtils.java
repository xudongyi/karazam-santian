package com.klzan.core.util;

import com.klzan.core.SpringObjectFactory;
import com.klzan.core.exception.SystemException;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

/**
 * 编号工具类
 */
public class SnUtils {

    private static final Integer TYPE_SIZE = 2;

    private static final Integer DATE_SIZE = 17;

    /**
     * 订单前缀
     */
    public static final String DEV_PREFIX = "1";
    public static final String TEST_PREFIX = "2";
    public static final String PROD_PREFIX = "3";

    /**
     * 获取订单前缀
     *
     * @return
     */
    public static String getOrderPrefix() {
        try {
            String prefix = SpringObjectFactory.getActiveProfile().name();
            if (StringUtils.isBlank(prefix)) {
                throw new SystemException("订单获取前缀出错，获取环境参数异常");
            }
            if (SpringObjectFactory.Profile.DEV.name().equals(prefix)) {
                return DEV_PREFIX;
            } else if (SpringObjectFactory.Profile.TEST.name().equals(prefix)) {
                return TEST_PREFIX;
            } else if (SpringObjectFactory.Profile.PROD.name().equals(prefix)) {
                return PROD_PREFIX;
            }
            throw new SystemException("订单获取前缀出错，获取环境参数异常");
        } catch (SystemException e) {
            return DEV_PREFIX;
        } catch (NullPointerException e) {
            return DEV_PREFIX;
        }
    }

    /**
     * 生成订单号，(20141004+000+00000001)
     *
     * @param typeId  订单类型的序号（从0开始,3位）
     * @param orderId 订单表的编号       （剩余位数）
     * @param length  订单长度
     * @return
     */
    public static String getOrderFormId(int typeId, int orderId, int length) {
        String dateString = DateUtils.format(new Date(), DateUtils.DATE_PATTERN_NUMBER_yyyyMMdd);
        String typeString = numberToStringBySize(typeId, TYPE_SIZE);
        String orderString = numberToStringBySize(orderId, length - DATE_SIZE - TYPE_SIZE - getOrderPrefix().length());

        return getOrderPrefix() + dateString + typeString + orderString;
    }

    /**
     * 将数字转换成指定位数的字符串
     *
     * @param num
     * @param size
     * @return
     */
    private static String numberToStringBySize(Integer num, int size) {
        String numString = num.toString();
        if (numString.length() > size) {
            throw new SystemException("参数异常");
        }
        if (numString.length() == size) {
            return numString;
        }
        int randomLength = 0;
        int diff = size - numString.length();
        if (diff > 1) {
            randomLength = diff - 1;
            diff = diff - randomLength;
        }
        numString = RandomStringUtils.randomNumeric(randomLength) + numString;
        for (int i = 0; i < diff; i++) {
            numString = "0" + numString;
        }
        return numString;
    }

    private static String numberToStringBySize(int size) {
        return RandomStringUtils.randomNumeric(size);
    }

    public static String getOrderNo(Integer sequeceNo, int typeId, int length) {
        String dateString = DateUtils.format(new Date(), DateUtils.DATE_PATTERN_NUMBER_S);
        String typeString = numberToStringBySize(typeId, TYPE_SIZE);
        String orderString = numberToStringBySize(sequeceNo, length - DATE_SIZE - TYPE_SIZE - getOrderPrefix().length());
        StringBuffer orderNo = new StringBuffer(getOrderPrefix()).append(typeString).append(dateString).append(orderString);
        if (orderNo.length() > length) {
            throw new SystemException("生成订单号超长,生成订单号长度为：" + orderNo.length());
        }
        return orderNo.toString();
    }

    public static String getOrderNo() {
        int typeId = 51;
        int length = 30;
        String dateString = DateUtils.format(new Date(), DateUtils.DATE_PATTERN_NUMBER_S);
        String typeString = numberToStringBySize(typeId, TYPE_SIZE);
        String orderString = RandomStringUtils.randomNumeric(length - DATE_SIZE - TYPE_SIZE - getOrderPrefix().length());
        StringBuffer orderNo = new StringBuffer(getOrderPrefix()).append(typeString).append(dateString).append(orderString);
        if (orderNo.length() > length) {
            throw new SystemException("生成订单号超长,生成订单号长度为：" + orderNo.length());
        }
        return orderNo.toString();
    }

    public static String getUserNo(Integer userId, int typeId, int length) {
        String typeString = numberToStringBySize(typeId, TYPE_SIZE);
        String userNoString = numberToStringBySize(userId, length - TYPE_SIZE - getOrderPrefix().length());
        StringBuffer orderNo = new StringBuffer(getOrderPrefix()).append(typeString).append(userNoString);
        if (orderNo.length() > length) {
            throw new SystemException("生成用户编号超长");
        }
        return orderNo.toString();
    }

    public static String getOrgNo(Integer orgId, int typeId, int length) {
        String typeString = numberToStringBySize(typeId, TYPE_SIZE);
        String userNoString = numberToStringBySize(orgId, length - TYPE_SIZE - getOrderPrefix().length());
        StringBuffer orderNo = new StringBuffer(getOrderPrefix()).append(typeString).append(userNoString);
        if (orderNo.length() > length) {
            throw new SystemException("生成机构编号超长");
        }
        return orderNo.toString();
    }

}