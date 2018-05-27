/*
 * Copyright (c) 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */

package com.klzan.p2p.common.service;

import org.hibernate.LockMode;

import java.util.List;

/**
 * 提供公用接口
 */
public interface IBaseService<T> {

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    T get(Integer id);

    /**
     * 根据ID查询实体
     * @param id
     * @param lock
     * @return
     */
    T get(Integer id, LockMode lock);

    /**
     * 全部数据
     * @return
     */
    List<T> findAll();

    /**
     * 有效数据
     * @return
     */
    List<T> findValidAll();

    /**
     * 保存实体
     * @param entity
     * @return
     */
    T persist(T entity);

    /**
     * 更新实体
     * @param entity
     * @return
     */
    T update(T entity);

    /**
     * 更新实体
     * @param entity
     * @param lock
     * @return
     */
    T update(T entity, LockMode lock);

    /**
     * 更新实体
     * @param entity
     * @return
     */
    T merge(T entity);

    /**
     * 更新实体
     * @param hql
     * @param params
     * @return
     */
    Integer update(String hql, Object... params);

    /**
     * 刷新实体
     * @param entity
     * @return
     */
    T refresh(T entity);

    /**
     * 根据ID逻辑删除
     * @param id
     */
    void remove(Integer id);

    /**
     * 刷新缓存
     * @return
     */
    void flush();

}
