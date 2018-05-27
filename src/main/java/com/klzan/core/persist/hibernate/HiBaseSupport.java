package com.klzan.core.persist.hibernate;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import org.hibernate.LockMode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by suhao on 2015/8/27.
 */
public interface HiBaseSupport<T> {
    /**
     * 根据主键查询一个实体
     * @param id
     * @return
     */
    T get(Serializable id);

    /**
     * 根据主键查询一个实体
     * @param id
     * @param lock
     * @return
     */
    T get(Serializable id, LockMode lock);

    /**
     * 根据主键加载一个实体对象
     * @param id
     * @return
     */
    T load(Serializable id);

    /**
     * 根据主键加载一个实体对象
     * @param id
     * @param lock
     * @return
     */
    T load(Serializable id, LockMode lock);

    /**
     * 持久化实体对象
     * @param entity
     */
    T persist(T entity);

    /**
     * 批量持久化实体对象
     * @param entities
     */
    void batchPersist(List<T> entities);

    /**
     * 保存一个实体
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * 删除一个实体
     * @param entity
     */
    void delete(T entity);

    /**
     * 删除一个实体
     * @param entity
     * @param lock
     */
    void delete(T entity, LockMode lock);

    /**
     * 批量删除实体
     * @param entities
     */
    void batchDelete(List<T> entities);

    /**
     * 根据ID删除一个实体
     * @param id
     * @return
     */
    Integer deleteById(Serializable id);

    /**
     * 根据ID批量删除实体
     * @param ids
     * @return
     */
    Integer batchDeleteById(Object... ids);

    /**
     * 根据ID批量删除实体
     * @param ids
     * @return
     */
    Integer batchDeleteById(List<Serializable> ids);

    /**
     * 修改一个实体
     * @param entity
     * @return
     */
    T update(T entity);

    /**
     * 修改一个实体
     * @param entity
     * @param lock
     */
    T update(T entity, LockMode lock);

    /**
     * 更新一个实体
     * @param entity
     * @return
     */
    T merge(T entity);

    /**
     * 批量更新实体
     * @param entities
     */
    void batchMerge(List<T> entities);

    /**
     * 刷新一个实体
     * @param entity
     * @return
     */
    T refresh(T entity);

    /**
     * 强制立即更新到数据库,否则需要事务提交后,才会提交到数据库
     */
    void flush();

    /**
     * 加锁指定的实体
     * @param entity 实体对象
     * @param lock 加锁
     */
    void lock(T entity, LockMode lock);

    /**
     * 列表查询
     * @param hql
     * @param filters
     * @return
     */
    List<T> find(String hql, List<ParamsFilter> filters);

    /**
     * 列表查询
     * @param hql
     * @param objects
     * @return
     */
    List<T> find(String hql, Object... objects);

    /**
     * 列表查询
     * @param hql
     * @param params
     * @return
     */
    List<T> find(String hql, Map<String, Object> params);

    /**
     * execute query with native SQL.
     * @param sql
     * @param filters
     * @param <M>
     * @return
     */
    <M> List<M> findBySQL(String sql, List<ParamsFilter> filters);

    /**
     * execute query with native SQL.
     * @param sql
     * @param params
     * @return
     */
    <M> List<M> findBySQL(String sql, Object... params);

    /**
     * execute query with native SQL.
     * @param sql
     * @param params
     * @return
     */
    <M> List<M> findBySQL(String sql, Map<String, Object> params);

    /**
     * execute query with native SQL.
     * @param sql
     * @param entityCls   domain class
     * @param params
     * @return
     */
    <M> List<M> findBySQL(final String sql, final Class<M> entityCls, final Object... params);

    /**
     * execute query with native SQL.
     * @param sql
     * @param entityCls   domain class
     * @param params
     * @return
     */
    <M> List<M> findBySQL(final String sql, final Class<M> entityCls, final Map<String, Object> params);

    /**
     * execute query with native SQL.
     * @param sql
     * @param scalarCallback
     * @param filters
     * @param <M>
     * @return
     */
    <M> List<M> findBySQL(String sql, ScalarAliasCallback<M> scalarCallback, List<ParamsFilter> filters);

    /**
     * execute query with native SQL.
     *
     * @param sql            native sql
     * @param scalarCallback callback for add scalar alias
     * @param params         query params
     * @return
     */
    <M> List<M> findBySQL(String sql, ScalarAliasCallback<M> scalarCallback, Object... params);

    /**
     * execute query with native SQL.
     *
     * @param sql            native sql
     * @param scalarCallback callback for add scalar alias
     * @param params         query params
     * @return
     */
    <M> List<M> findBySQL(String sql, ScalarAliasCallback<M> scalarCallback, Map<String, Object> params);

    /**
     * update with native named SQL.
     *
     * @param sql
     * @param params
     * @return
     */
    Integer executeUpdateWithSQL(String sql, Map<String, Object> params);

    /**
     * update with native named SQL.
     *
     * @param sql
     * @param params
     * @return
     */
    Integer executeUpdateWithSQL(String sql, Object... params);

    /**
     * update with native named SQL.
     *
     * @param hql
     * @param params
     * @return
     */
    Integer executeUpdate(String hql, Map<String, Object> params);

    /**
     * update with hql.
     *
     * @param hql
     * @param params
     * @return
     */
    Integer executeUpdate(String hql, Object... params);

    /**
     * find for count with native sql
     *
     * @param sql
     * @param params
     * @return
     */
    Integer countWithSQL(String sql, Object... params);

    /**
     * find for count with named HQL
     *
     * @param hql
     * @param params
     * @return
     */
    Integer count(String hql, Map<String, Object> params);

    /**
     * find for count with HQL
     *
     * @param hql
     * @param params
     * @return
     */
    Integer count(String hql, Object... params);

    /**
     * find for count with native named sql
     *
     * @param sql
     * @param params
     * @return
     */
    Integer countWithSQL(String sql, Map<String, Object> params);

    /**
     * do paging find by HQL
     *
     * @param hql
     * @param firstResult
     * @param maxResult
     * @param params
     * @return
     */
    List<T> find(String hql, int firstResult, int maxResult, Object... params);

    /**
     * do paging find by HQL
     *
     * @param hql
     * @param firstResult
     * @param maxResult
     * @param params
     * @return
     */
    List<T> find(String hql, int firstResult, int maxResult, Map<String, Object> params);

    /**
     * do paging find by native SQL
     *
     * @param sql
     * @param firstResult
     * @param maxResult
     * @param params
     * @return
     */
    <M> List<M> findBySQL(String sql, int firstResult, int maxResult, ScalarAliasCallback<M> scalarCallback, Object... params);

    /**
     * do paging find by native SQL
     *
     * @param sql
     * @param firstResult
     * @param maxResult
     * @param params
     * @return
     */
    <M> List<M> findBySQL(String sql, int firstResult, int maxResult, ScalarAliasCallback<M> scalarCallback, Map<String, Object> params);

    /**
     * find with paging result
     *
     * @param hql
     * @param criteria
     * @param params
     * @return
     */
    PageResult<T> findPage(String hql, PageCriteria criteria, Object... params);

    /**
     * find with paging result
     *
     * @param hql
     * @param criteria
     * @param filters
     * @return
     */
    PageResult<T> findPage(String hql, PageCriteria criteria, List<ParamsFilter> filters);

    /**
     * find with paging result
     * @param hql
     * @param criteria
     * @param filters
     * @param params
     * @return
     */
    PageResult<T> findPage(String hql, PageCriteria criteria, List<ParamsFilter> filters, Map<String, Object> params);

    /**
     * find with paging result
     *
     * @param hql
     * @param criteria
     * @param params
     * @return
     */
    PageResult<T> findPage(String hql, PageCriteria criteria, Map<String, Object> params);

    /**
     * find with paging result
     *
     * @param hql
     * @param criteria
     * @param params
     * @return
     */
    PageResult<T> findPage(String hql, String countSQL, PageCriteria criteria, Object... params);

    /**
     * find with paging result
     *
     * @param hql
     * @param criteria
     * @param params
     * @return
     */
    PageResult<T> findPage(String hql, String countSQL, PageCriteria criteria, Map<String, Object> params);

    /**
     * find page result by SQL
     * @param sql
     * @param criteria
     * @param params
     * @return
     */
    <M> PageResult<M> findPageBySQL(String sql, PageCriteria criteria, ScalarAliasCallback<M> scalarCallback, Map<String, Object> params);

    /**
     * find page result by SQL
     * @param sql
     * @param criteria
     * @param params
     * @return
     */
    <M> PageResult<M> findPageBySQL(String sql, PageCriteria criteria, ScalarAliasCallback<M> scalarCallback, Object... params);

    /**
     * find page result by SQL
     * @param sql
     * @param criteria
     * @param scalarCallback
     * @param filters
     * @return
     */
    <M> PageResult<M> findPageBySQL(String sql, PageCriteria criteria, ScalarAliasCallback<M> scalarCallback, List<ParamsFilter> filters);

    /**
     * find page result by SQL
     * @param sql
     * @param criteria
     * @param scalarCallback
     * @param filters
     * @param params
     * @param <M>
     * @return
     */
    <M> PageResult<M> findPageBySQL(String sql, PageCriteria criteria, ScalarAliasCallback<M> scalarCallback, List<ParamsFilter> filters, Map<String, Object> params);

    /**
     * find page result by SQL
     * @param sql
     * @param countSQL
     * @param criteria
     * @param scalarCallback
     * @param params
     * @return
     */
    <M> PageResult<M> findPageBySQL(String sql, String countSQL, PageCriteria criteria, ScalarAliasCallback<M> scalarCallback, Object... params);

    /**
     * find page result by SQL
     * @param namedSql
     * @param countSQL
     * @param criteria
     * @param scalarCallback
     * @param params
     * @return
     */
    <M> PageResult<M> findPageBySQL(String namedSql, String countSQL, PageCriteria criteria, ScalarAliasCallback<M> scalarCallback, Map<String, Object> params);

    /**
     * find single result by HQL
     *
     * @param hql
     * @param params
     * @return
     */
    T findUnique(String hql, Object... params);

    /**
     * find single result by HQL
     *
     * @param hql
     * @param params
     * @return
     */
    T findUnique(String hql, Map<String, Object> params);

    /**
     * find single result by native SQL
     * @param sql
     * @param params
     * @return
     */
    <M> M findUniqueBySQL(String sql, Object... params);

    /**
     * find single result by native SQL
     * @param sql
     * @param params
     * @return
     */
    <M> M findUniqueBySQL(String sql, Map<String, Object> params);

    /**
     * find single result by native SQL
     *
     * @param sql
     * @param scalarCallback
     * @param params
     * @return
     */
    <M> M findUniqueBySQL(String sql, ScalarAliasCallback<M> scalarCallback, Object... params);

    /**
     * find single result by native SQL
     *
     * @param sql
     * @param scalarCallback
     * @param params
     * @return
     */
    <M> M findUniqueBySQL(String sql, ScalarAliasCallback<M> scalarCallback, Map<String, Object> params);


    /**
     * find single result by native SQL
     * @param sql
     * @param entityCls domain class returned
     * @param params
     * @return
     */
    <M> M findUniqueBySQL(final String sql, Class<M> entityCls, final Object... params);

    /**
     * find single result by native SQL
     * @param sql
     * @param entityCls domain class returned
     * @param params
     * @return
     */
    <M> M findUniqueBySQL(final String sql, Class<M> entityCls, final Map<String, Object> params);

    /**
     * archive delete the object
     *
     * @param id
     * @return
     */
    Integer logicDeleteById(Object id);

    /**
     * archive delete the object
     *
     * @param ids
     * @return
     */
    Integer batchLogicDeleteById(Object... ids);
}
