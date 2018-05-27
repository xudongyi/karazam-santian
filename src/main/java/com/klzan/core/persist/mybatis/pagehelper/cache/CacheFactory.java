package com.klzan.core.persist.mybatis.pagehelper.cache;

import com.klzan.core.persist.mybatis.pagehelper.PageException;
import com.klzan.core.persist.mybatis.pagehelper.util.StringUtil;

import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * CacheFactory
 */
public abstract class CacheFactory {

    /**
     * 创建 SQL 缓存
     *
     * @param sqlCacheClass
     * @return
     */
    public static <K, V> Cache<K, V> createCache(String sqlCacheClass, String prefix, Properties properties) {
        if (StringUtil.isEmpty(sqlCacheClass)) {
            try {
                Class.forName("com.google.common.cache.Cache");
                return new GuavaCache<K, V>(properties, prefix);
            } catch (Throwable t) {
                return new SimpleCache<K, V>(properties, prefix);
            }
        } else {
            try {
                Class<? extends Cache> clazz = (Class<? extends Cache>) Class.forName(sqlCacheClass);
                try {
                    Constructor<? extends Cache> constructor = clazz.getConstructor(Properties.class, String.class);
                    return constructor.newInstance(properties, prefix);
                } catch (Exception e) {
                    return clazz.newInstance();
                }
            } catch (Throwable t) {
                throw new PageException("Created Sql Cache [" + sqlCacheClass + "] Error", t);
            }
        }
    }

}
