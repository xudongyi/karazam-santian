package com.klzan.core.persist.mybatis.pagehelper.cache;

import com.google.common.cache.CacheBuilder;
import com.klzan.core.util.StringUtils;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Simple Guava Cache
 */
public class GuavaCache<K, V> implements Cache<K, V> {

    private final com.google.common.cache.Cache<K, V> CACHE;

    public GuavaCache(Properties properties, String prefix) {
        CacheBuilder cacheBuilder = CacheBuilder.newBuilder();
        String maximumSize = properties.getProperty(prefix + ".maximumSize");
        if (StringUtils.isNotEmpty(maximumSize)) {
            cacheBuilder.maximumSize(Long.parseLong(maximumSize));
        } else {
            cacheBuilder.maximumSize(1000);
        }
        String expireAfterAccess = properties.getProperty(prefix + ".expireAfterAccess");
        if (StringUtils.isNotEmpty(expireAfterAccess)) {
            cacheBuilder.expireAfterAccess(Long.parseLong(expireAfterAccess), TimeUnit.MILLISECONDS);
        }
        String expireAfterWrite = properties.getProperty(prefix + ".expireAfterWrite");
        if (StringUtils.isNotEmpty(expireAfterWrite)) {
            cacheBuilder.expireAfterWrite(Long.parseLong(expireAfterWrite), TimeUnit.MILLISECONDS);
        }
        String initialCapacity = properties.getProperty(prefix + ".initialCapacity");
        if (StringUtils.isNotEmpty(initialCapacity)) {
            cacheBuilder.initialCapacity(Integer.parseInt(initialCapacity));
        }
        CACHE = cacheBuilder.build();
    }

    @Override
    public V get(K key) {
        return CACHE.getIfPresent(key);
    }

    @Override
    public void put(K key, V value) {
        CACHE.put(key, value);
    }
}
