package com.klzan.core.util;

/**
 * Created by Sue on 2017/6/10.
 */
public final class SnowflakeUtils {
    private static final Snowflake SNOWFLAKE = new Snowflake(1, 1);
    public static long getNextNo() {
        return SNOWFLAKE.nextId();
    }

}
