package com.klzan.core.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by suhao Date: 2017/3/16 Time: 17:52
 *
 * @version: 1.0
 */
public class PropertiesUtils extends PropertyPlaceholderConfigurer {
    private static Map<String, String> ctxPropertiesMap;

    @Override
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
        ctxPropertiesMap = new HashMap<>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            String regex = "ENCRYPT\\((.*)\\)";
            Pattern mPattern = Pattern.compile(regex);
            Matcher mMatcher = mPattern.matcher(value);
            while (mMatcher.find()) {
                value = DigestUtils.decrypt(mMatcher.group(1));
                props.setProperty(keyStr, value);
            }
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    /**
     * Get a value based on key , if key does not exist , null is returned
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        try {
            return ctxPropertiesMap.get(key);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * 根据key获取值
     *
     * @param key
     * @return
     */
    public static int getInt(String key) {
        return Integer.parseInt(ctxPropertiesMap.get(key));
    }

    /**
     * 根据key获取值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(String key, int defaultValue) {
        String value = ctxPropertiesMap.get(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    /**
     * 根据key获取值
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = ctxPropertiesMap.get(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return new Boolean(value);
    }

}