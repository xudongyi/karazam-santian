package com.klzan.p2p.common.service.impl;

import com.klzan.core.persist.hibernate.HiBaseDao;
import com.klzan.core.persist.mybatis.MyDaoSupport;
import com.klzan.core.persist.mybatis.MyMapper;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author suhao
 * @description: 抽象服务类, 提供公用服务注入
 * @version 1.0
 */
public abstract class BaseService<T> extends HiBaseDao<T> {
    protected Logger logger = LoggerFactory.getLogger(BaseService.class);

    @Inject
    protected MyDaoSupport myDaoSupport;

    @Inject
    protected MyMapper<T> mapper;

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    public T get(Integer id) {
        return super.get(id);
    }

    /**
     * 根据ID查询实体
     * @param id
     * @param lock
     * @return
     */
    public T get(Integer id, LockMode lock) {
        return super.get(id, lock);
    }

    /**
     * 全部数据
     * @return
     */
    public List<T> findAll() {
        return super.find("FROM " + entityClass.getSimpleName());
    }

    /**
     * 有效数据
     * @return
     */
    public List<T> findValidAll() {
        return super.find("FROM " + entityClass.getSimpleName() + " WHERE deleted = false ");
    }

    /**
     * 保存实体
     * @param entity
     * @return
     */
    @Transactional
    public T persist(T entity) {
        return super.persist(entity);
    }

    /**
     * 更新实体
     * @param entity
     * @return
     */
    public T update(T entity) {
        return super.update(entity);
    }

    /**
     * 更新实体
     * @param entity
     * @param lock
     * @return
     */
    public T update(T entity, LockMode lock) {
        return super.update(entity, lock);
    }

    /**
     * 更新实体
     * @param entity
     * @return
     */
    public T merge(T entity) {
        return super.merge(entity);
    }

    /**
     * 更新实体
     * @param hql
     * @param params
     * @return
     */
    public Integer update(String hql, Object... params) {
        return super.executeUpdate(hql, params);
    }

    /**
     * 刷新实体
     * @param entity
     * @return
     */
    public T refresh(T entity) {
        return super.refresh(entity);
    }

    /**
     * 根据ID逻辑删除
     * @param id
     */
    public void remove(Integer id) {
        super.logicDeleteById(id);
    }

    /**
     * 刷新缓存
     * @return
     */
    public void flush() {
        super.flush();
    }

}
