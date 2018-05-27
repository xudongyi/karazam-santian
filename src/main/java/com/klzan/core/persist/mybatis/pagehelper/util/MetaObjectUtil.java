package com.klzan.core.persist.mybatis.pagehelper.util;

import com.klzan.core.persist.mybatis.pagehelper.PageException;
import org.apache.ibatis.reflection.MetaObject;

import java.lang.reflect.Method;

public class MetaObjectUtil {
    public static Method method;

    static {
        try {
            Class<?> metaClass = Class.forName("org.apache.ibatis.reflection.SystemMetaObject");
            method = metaClass.getDeclaredMethod("forObject", Object.class);
        } catch (Exception e1) {
            try {
                Class<?> metaClass = Class.forName("org.apache.ibatis.reflection.MetaObject");
                method = metaClass.getDeclaredMethod("forObject", Object.class);
            } catch (Exception e2) {
                throw new PageException(e2);
            }
        }

    }

    public static MetaObject forObject(Object object) {
        try {
            return (MetaObject) method.invoke(null, object);
        } catch (Exception e) {
            throw new PageException(e);
        }
    }

}
