package com.klzan.core.persist.hibernate;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.page.ParamsFilter;
import com.klzan.core.util.HQLUtils;
import com.klzan.core.util.Reflections;
import com.klzan.core.util.SQLUtils;
import com.klzan.core.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.ParameterMetadata;
import org.hibernate.query.Query;
import org.hibernate.query.QueryParameter;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.inject.Inject;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by suhao on 2016/11/18.
 */
public abstract class HiBaseDao<T> extends HibernateDaoSupport implements HiBaseSupport<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected int batchSize = 10;
    protected Class<T> entityClass;

    public HiBaseDao() {
        this.entityClass = Reflections.getClassGenricType(getClass());
    }

    @Inject
    protected void init(HibernateTemplate hibernateTemplate) throws Exception {
        setHibernateTemplate(hibernateTemplate);
    }

    @Override
    protected HibernateTemplate createHibernateTemplate(SessionFactory sessionFactory) {
        return getHibernateTemplate();
    }

    @Override
    public T get(Serializable id) {
        return getHibernateTemplate().get(entityClass, id);
    }

    @Override
    public T get(Serializable id, LockMode lock) {
        T entity = getHibernateTemplate().get(entityClass, id, lock);
        // 如果实体不为null,立即刷新,否则锁不会生效
        if (entity != null) {
            this.flush();
        }
        return entity;
    }

    @Override
    public T load(Serializable id) {
        return getHibernateTemplate().load(entityClass, id);
    }

    @Override
    public T load(Serializable id, LockMode lock) {
        T entity = (T) getHibernateTemplate().load(entityClass, id, lock);
        // 如果实体不为null,立即刷新,否则锁不会生效
        if (entity != null) {
            this.flush();
        }
        return entity;
    }

    @Override
    public T persist(T entity) {
        getHibernateTemplate().persist(entity);
        return entity;
    }

    @Override
    public void batchPersist(List<T> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        int max = entities.size();
        for (int i = 0; i < max; i++) {
            getHibernateTemplate().persist(entities.get(i));
            if ((i != 0 && i % batchSize == 0) || i == max - 1) {
                getHibernateTemplate().flush();
            }
        }
    }

    @Override
    public T save(T entity) {
        getHibernateTemplate().save(entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        getHibernateTemplate().delete(entity);
    }

    @Override
    public void delete(T entity, LockMode lock) {
        getHibernateTemplate().delete(entity, lock);
        this.flush();
    }

    @Override
    public void batchDelete(List<T> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        int max = entities.size();
        for (int i = 0; i < max; i++) {
            getHibernateTemplate().refresh(entities.get(i));
            getHibernateTemplate().delete(entities.get(i));
            if ((i != 0 && i % batchSize == 0) || i == max - 1) {
                getHibernateTemplate().flush();
                getHibernateTemplate().clear();
            }
        }
    }

    @Override
    public Integer deleteById(Serializable id) {
        return executeUpdate("DELETE FROM " + entityClass.getSimpleName() + " WHERE id=?0", id);
    }

    @Override
    public Integer batchDeleteById(Object... ids) {
        String hql = "DELETE FROM " + entityClass.getSimpleName() + " o WHERE o.id IN (:ids)";
        return getHibernateTemplate().bulkUpdate(hql, ids);
    }

    @Override
    public Integer batchDeleteById(List<Serializable> ids) {
        String hql = "DELETE FROM " + entityClass.getSimpleName() + " o WHERE o.id in (:ids)";
        return getHibernateTemplate().bulkUpdate(hql, ids);
    }

    @Override
    public T update(T entity) {
        getHibernateTemplate().update(entity);
        return entity;
    }

    @Override
    public T update(T entity, LockMode lock) {
        getHibernateTemplate().update(entity, lock);
        this.flush();
        return entity;
    }

    @Override
    public T merge(T entity) {
        return getHibernateTemplate().merge(entity);
    }

    @Override
    public void batchMerge(List<T> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        int max = entities.size();
        for (int i = 0; i < max; i++) {
            getHibernateTemplate().merge(entities.get(i));
            if ((i != 0 && i % batchSize == 0) || i == max - 1) {
                getHibernateTemplate().flush();
                getHibernateTemplate().clear();
            }
        }
    }

    @Override
    public T refresh(T entity) {
        getHibernateTemplate().refresh(entity);
        return entity;
    }

    @Override
    public void flush() {
        getHibernateTemplate().flush();
    }

    @Override
    public void lock(T entity, LockMode lock) {
        getHibernateTemplate().lock(entity, lock);
    }

    @Override
    public List<T> find(String hql, List<ParamsFilter> filters) {
        String newHql = HQLUtils.buildListFiltersStmt(hql, filters);
        Map<String, Object> params = HQLUtils.buildFiltersMap(filters);
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session)
                    throws HibernateException {
                Query query = session.createQuery(newHql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                return query.list();
            }
        });
    }

    @Override
    public List<T> find(String hql, Object... params) {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session)
                    throws HibernateException {
                Query query = session.createQuery(hql);
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        Object paramValue = params[i];
                        if (paramValue instanceof Collection) {
                            query.setParameterList(String.valueOf(i), (Collection) paramValue);
                        } else {
                            query.setParameter(String.valueOf(i), paramValue);
                        }
                    }
                }
                return query.list();
            }
        });
    }

    @Override
    public List<T> find(String hql, Map<String, Object> params) {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session)
                    throws HibernateException {
                Query query = session.createQuery(hql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                return query.list();
            }
        });
    }

    @Override
    public <M> List<M> findBySQL(String sql, List<ParamsFilter> filters) {
        String newSql = SQLUtils.buildListFiltersStmt(sql, filters);
        Map<String, Object> params = SQLUtils.buildFiltersMap(filters);
        return getHibernateTemplate().execute(new HibernateCallback<List<M>>() {
            @Override
            public List<M> doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(newSql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                return query.list();
            }
        });
    }

    @Override
    public <M> List<M> findBySQL(String sql, Object... params) {
        return getHibernateTemplate().execute(new HibernateCallback<List<M>>() {
            @Override
            public List<M> doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                for (int i = 0; i < params.length; i++) {
                    Object paramValue = params[i];
                    if (paramValue instanceof Collection) {
                        query.setParameterList(String.valueOf(i), (Collection) paramValue);
                    } else {
                        query.setParameter(String.valueOf(i), paramValue);
                    }
                }
                return query.list();
            }
        });
    }

    @Override
    public <M> List<M> findBySQL(String sql, Map<String, Object> params) {
        return getHibernateTemplate().execute(new HibernateCallback<List<M>>() {
            @Override
            public List<M> doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                return query.list();
            }
        });
    }

    @Override
    public <M> List<M> findBySQL(final String sql, final Class<M> entityCls, final Object... params) {
        return getHibernateTemplate().execute(new HibernateCallback<List<M>>() {
            @Override
            public List<M> doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                for (int i = 0; i < params.length; i++) {
                    query.setParameter(i, params[i]);
                }
                query.addEntity(entityCls);
                return query.list();
            }
        });
    }

    @Override
    public <M> List<M> findBySQL(final String sql, final Class<M> entityCls, final Map<String, Object> params) {
        return findBySQL(sql, null, entityCls, params);
    }

    @Override
    public <M> List<M> findBySQL(String sql, ScalarAliasCallback<M> scalarCallback, List<ParamsFilter> filters) {
        String newSql = SQLUtils.buildListFiltersStmt(sql, filters);
        Map<String, Object> params = SQLUtils.buildFiltersMap(filters);
        return getHibernateTemplate().execute(new HibernateCallback<List<M>>() {
            @Override
            public List<M> doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(newSql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                Class<M> c = scalarCallback.doAddScalar(query);
                if (c.isPrimitive() || c.isAssignableFrom(String.class)) {
                    return query.list();
                } else {
                    return query.setResultTransformer(Transformers.aliasToBean(c)).list();
                }
            }
        });
    }

    @Override
    public <M> List<M> findBySQL(String sql, ScalarAliasCallback<M> scalarCallback, Object... params) {
        return getHibernateTemplate().execute(new HibernateCallback<List<M>>() {
            @Override
            public List<M> doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                for (int i = 0; i < params.length; i++) {
                    Object paramValue = params[i];
                    if (paramValue instanceof Collection) {
                        query.setParameterList(String.valueOf(i), (Collection) paramValue);
                    } else {
                        query.setParameter(String.valueOf(i), paramValue);
                    }
                }
                Class<M> c = scalarCallback.doAddScalar(query);
                if (c.isPrimitive() || c.isAssignableFrom(String.class)) {
                    return query.list();
                } else {
                    return query.setResultTransformer(Transformers.aliasToBean(c)).list();
                }
            }
        });
    }

    @Override
    public <M> List<M> findBySQL(String sql, ScalarAliasCallback<M> scalarCallback, Map<String, Object> params) {
        return getHibernateTemplate().execute(new HibernateCallback<List<M>>() {
            @Override
            public List<M> doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                Class<M> c = scalarCallback.doAddScalar(query);
                if (c.isPrimitive() || c.isAssignableFrom(String.class)) {
                    return query.list();
                } else {
                    return query.setResultTransformer(Transformers.aliasToBean(c)).list();
                }
            }
        });
    }

    @Override
    public Integer executeUpdateWithSQL(String sql, Map<String, Object> params) {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(
                    Session session) throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                return query.executeUpdate();
            }
        });
    }

    @Override
    public Integer executeUpdateWithSQL(String sql, Object... params) {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(
                    Session session) throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                for (int i = 0; i < params.length; i++) {
                    Object paramValue = params[i];
                    if (paramValue instanceof Collection) {
                        query.setParameterList(String.valueOf(i), (Collection) paramValue);
                    } else {
                        query.setParameter(String.valueOf(i), paramValue);
                    }
                }
                return query.executeUpdate();
            }
        });
    }

    @Override
    public Integer executeUpdate(String hql, Map<String, Object> params) {
        return getHibernateTemplate().bulkUpdate(hql, params);
    }

    @Override
    public Integer executeUpdate(String hql, Object... params) {
        return getHibernateTemplate().bulkUpdate(hql, params);
    }

    @Override
    public Integer countWithSQL(String sql, Map<String, Object> params) {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                return ((BigInteger) query.uniqueResult()).intValue();
            }
        });
    }

    @Override
    public Integer countWithSQL(String sql, Object... params) {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                for (int i = 0; i < params.length; i++) {
                    Object paramValue = params[i];
                    if (paramValue instanceof Collection) {
                        query.setParameterList(String.valueOf(i), (Collection) paramValue);
                    } else {
                        query.setParameter(String.valueOf(i), paramValue);
                    }
                }
                return ((BigInteger) query.uniqueResult()).intValue();
            }
        });
    }

    @Override
    public Integer count(String hql, Map<String, Object> params) {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session)
                    throws HibernateException {
                Query query = session.createQuery(hql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                return ((Long) query.uniqueResult()).intValue();
            }
        });
    }

    @Override
    public Integer count(String hql, Object... params) {
        return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session)
                    throws HibernateException {
                Query query = session.createQuery(hql);
                for (int i = 0; i < params.length; i++) {
                    Object paramValue = params[i];
                    if (paramValue instanceof Collection) {
                        query.setParameterList(String.valueOf(i), (Collection) paramValue);
                    } else {
                        query.setParameter(String.valueOf(i), paramValue);
                    }
                }
                return ((Long) query.uniqueResult()).intValue();
            }
        });
    }

    @Override
    public List<T> find(String hql, int firstResult, int maxResult, Object... params) {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session)
                    throws HibernateException {
                Query query = session.createQuery(hql);
                for (int i = 0; i < params.length; i++) {
                    Object paramValue = params[i];
                    if (paramValue instanceof Collection) {
                        query.setParameterList(String.valueOf(i), (Collection) paramValue);
                    } else {
                        query.setParameter(String.valueOf(i), paramValue);
                    }
                }
                query.setFirstResult(firstResult);
                query.setMaxResults(maxResult);
                return query.list();
            }
        });
    }

    @Override
    public List<T> find(String hql, int firstResult, int maxResult, Map<String, Object> params) {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session)
                    throws HibernateException {
                Query query = session.createQuery(hql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                query.setFirstResult(firstResult);
                query.setMaxResults(maxResult);
                return query.list();
            }
        });
    }

    @Override
    public <M> List<M> findBySQL(String sql, int firstResult, int maxResult, ScalarAliasCallback<M> scalarCallback, Object... params) {
        return getHibernateTemplate().execute(new HibernateCallback<List<M>>() {
            @Override
            public List<M> doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                for (int i = 0; i < params.length; i++) {
                    Object paramValue = params[i];
                    if (paramValue instanceof Collection) {
                        query.setParameterList(String.valueOf(i), (Collection) paramValue);
                    } else {
                        query.setParameter(String.valueOf(i), paramValue);
                    }
                }
                query.setFirstResult(firstResult);
                query.setMaxResults(maxResult);
                Class<M> c = scalarCallback.doAddScalar(query);
                if (!c.isPrimitive() && !c.isAssignableFrom(String.class)) {
                    query.setResultTransformer(Transformers.aliasToBean(c));
                }
                return query.list();
            }
        });
    }

    @Override
    public <M> List<M> findBySQL(String sql, int firstResult, int maxResult, ScalarAliasCallback<M> scalarCallback, Map<String, Object> params) {
        return getHibernateTemplate().execute(new HibernateCallback<List<M>>() {
            @Override
            public List<M> doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                query.setFirstResult(firstResult);
                query.setMaxResults(maxResult);
                Class<M> c = scalarCallback.doAddScalar(query);
                if (!c.isPrimitive() && !c.isAssignableFrom(String.class)) {
                    query.setResultTransformer(Transformers.aliasToBean(c));
                }
                return query.list();
            }
        });
    }

    @Override
    public PageResult<T> findPage(String hql, PageCriteria criteria, Object... params) {
        if (criteria == null) {
            throw new IllegalArgumentException("Paremeter criteria is not allowd to be null.");
        }
        List results = null;
        Integer totalCount = 0;
        if (criteria.enablePaging()) {
            results = find(SQLUtils.buildSortStmt(hql, criteria), criteria.getOffset(),
                    criteria.getMaxResults(), params);
//            totalCount = count(SQLUtils.buildCountStmt(hql), params);
            totalCount = find(hql, params).size();
            return new PageResult<T>(criteria.getPage(), criteria.getRows(), totalCount, results);
        } else {
            results = find(SQLUtils.buildSortStmt(hql, criteria), params);
            totalCount = results.size();
            return new PageResult<T>(1, totalCount, totalCount, results);
        }
    }

    @Override
    public PageResult<T> findPage(String hql, PageCriteria criteria, List<ParamsFilter> filters) {
        if (criteria == null) {
            throw new IllegalArgumentException("Paremeter criteria is not allowd to be null.");
        }
        List results = null;
        Integer totalCount = 0;
        String newHql = HQLUtils.buildPageFiltersStmt(hql, criteria, filters);
        Map<String, Object> params = HQLUtils.buildFiltersMap(filters);
        if (criteria.enablePaging()) {
            results = find(newHql, criteria.getOffset(),
                    criteria.getMaxResults(), params);
//            totalCount = count(SQLUtils.buildCountStmt(newHql), params);
            totalCount = find(newHql, params).size();
            return new PageResult<>(criteria.getPage(), criteria.getRows(), totalCount, results);
        } else {
            results = find(SQLUtils.buildSortStmt(newHql, criteria), params);
            totalCount = results.size();
            return new PageResult<>(1, totalCount, totalCount, results);
        }
    }

    @Override
    public PageResult<T> findPage(String hql, PageCriteria criteria, List<ParamsFilter> filters, Map<String, Object> params) {
        if (criteria == null) {
            throw new IllegalArgumentException("Paremeter criteria is not allowd to be null.");
        }
        List results = null;
        Integer totalCount = 0;
        String newHql = HQLUtils.buildPageFiltersStmt(hql, criteria, filters);
        params.putAll(HQLUtils.buildFiltersMap(filters));
        if (criteria.enablePaging()) {
            results = find(newHql, criteria.getOffset(),
                    criteria.getMaxResults(), params);
//            totalCount = count(SQLUtils.buildCountStmt(newHql), params);
            totalCount = find(newHql, params).size();
            return new PageResult<>(criteria.getPage(), criteria.getRows(), totalCount, results);
        } else {
            results = find(SQLUtils.buildSortStmt(newHql, criteria), params);
            totalCount = results.size();
            return new PageResult<>(1, totalCount, totalCount, results);
        }
    }

    @Override
    public PageResult<T> findPage(String hql, PageCriteria criteria, Map<String, Object> params) {
        if (criteria == null) {
            throw new IllegalArgumentException("Paremeter criteria is not allowd to be null.");
        }
        List results = null;
        Integer totalCount = 0;
        if (criteria.enablePaging()) {
            results = find(HQLUtils.buildSortStmt(hql, criteria), criteria.getOffset(),
                    criteria.getMaxResults(), params);
//            totalCount = count(SQLUtils.buildCountStmt(hql), params);
            totalCount = find(hql, params).size();
            return new PageResult<T>(criteria.getPage(), criteria.getRows(), totalCount, results);
        } else {
            results = find(SQLUtils.buildSortStmt(hql, criteria), params);
            totalCount = results.size();
            return new PageResult<T>(1, totalCount, totalCount, results);
        }
    }

    @Override
    public PageResult<T> findPage(String hql, String countSQL, PageCriteria criteria, Object... params) {
        if (criteria == null) {
            throw new IllegalArgumentException("Paremeter criteria is not allowd to be null.");
        }
        List results = null;
        Integer totalCount = 0;
        if (criteria.enablePaging()) {
            results = find(HQLUtils.buildSortStmt(hql, criteria), criteria.getOffset(),
                    criteria.getMaxResults(), params);
//            totalCount = count(SQLUtils.buildCountStmt(hql), params);
            totalCount = find(hql, params).size();
            return new PageResult<>(criteria.getPage(), criteria.getRows(), totalCount, results);
        } else {
            results = find(SQLUtils.buildSortStmt(hql, criteria), params);
            totalCount = results.size();
            return new PageResult<>(1, totalCount, totalCount, results);
        }
    }

    @Override
    public PageResult<T> findPage(String hql, String countSQL, PageCriteria criteria, Map<String, Object> params) {
        if (criteria == null) {
            throw new IllegalArgumentException("Paremeter criteria is not allowd to be null.");
        }
        List results = null;
        Integer totalCount = 0;
        if (criteria.enablePaging()) {
            results = find(HQLUtils.buildSortStmt(hql, criteria), criteria.getOffset(),
                    criteria.getMaxResults(), params);
//            totalCount = count(SQLUtils.buildCountStmt(hql), params);
            totalCount = find(hql, params).size();
            return new PageResult<>(criteria.getPage(), criteria.getRows(), totalCount, results);
        } else {
            results = find(SQLUtils.buildSortStmt(hql, criteria), params);
            totalCount = results.size();
            return new PageResult<>(1, totalCount, totalCount, results);
        }
    }

    @Override
    public <M> PageResult<M> findPageBySQL(String sql, PageCriteria criteria, ScalarAliasCallback<M> scalarCallback, Map<String, Object> params) {
        if (criteria == null) {
            throw new IllegalArgumentException("Paremeter criteria is not allowd to be null.");
        }
        List results = null;
        Integer totalCount = 0;
        if (criteria.enablePaging()) {
            results = findBySQL(SQLUtils.buildSortStmt(sql, criteria), criteria.getOffset(),
                    criteria.getMaxResults(), scalarCallback, params);
//            totalCount = countWithSQL(SQLUtils.buildCountStmt(sql), params);
            totalCount = findBySQL(sql, params).size();
            return new PageResult<>(criteria.getPage(), criteria.getRows(), totalCount, results);
        } else {
            results = findBySQL(SQLUtils.buildSortStmt(sql, criteria), scalarCallback, params);
            totalCount = results.size();
            return new PageResult<>(1, totalCount, totalCount, results);
        }
    }

    @Override
    public <M> PageResult<M> findPageBySQL(String sql, PageCriteria criteria, ScalarAliasCallback<M> scalarCallback, Object... params) {
        if (criteria == null) {
            throw new IllegalArgumentException("Paremeter criteria is not allowd to be null.");
        }
        List results = null;
        Integer totalCount = 0;
        String newSql = SQLUtils.buildSortStmt(sql, criteria);
        if (criteria.enablePaging()) {
            results = findBySQL(newSql, criteria.getOffset(),
                    criteria.getMaxResults(), scalarCallback, params);
//            totalCount = countWithSQL(SQLUtils.buildCountStmt(newSql), params);
            totalCount = findBySQL(newSql, params).size();
            return new PageResult<>(criteria.getPage(), criteria.getRows(), totalCount, results);
        } else {
            results = findBySQL(newSql, scalarCallback, params);
            totalCount = results.size();
            return new PageResult<>(1, totalCount, totalCount, results);
        }
    }

    @Override
    public <M> PageResult<M> findPageBySQL(String sql, PageCriteria criteria, ScalarAliasCallback<M> scalarCallback, List<ParamsFilter> filters) {
        if (criteria == null) {
            throw new IllegalArgumentException("Paremeter criteria is not allowd to be null.");
        }
        List results = null;
        Integer totalCount = 0;
        String newSql = SQLUtils.buildPageFiltersStmt(sql, criteria, filters);

        Map params = SQLUtils.buildFiltersMap(filters);
        if (criteria.enablePaging()) {
            results = findBySQL(newSql, criteria.getOffset(),
                    criteria.getMaxResults(), scalarCallback, params);
//            totalCount = countWithSQL(SQLUtils.buildCountStmt(newSql), params);
            totalCount = findBySQL(newSql, params).size();
            return new PageResult<>(criteria.getPage(), criteria.getRows(), totalCount, results);
        } else {
            results = findBySQL(newSql, scalarCallback, params);
            totalCount = results.size();
            return new PageResult<>(1, totalCount, totalCount, results);
        }
    }

    @Override
    public <M> PageResult<M> findPageBySQL(String sql, PageCriteria criteria, ScalarAliasCallback<M> scalarCallback, List<ParamsFilter> filters, Map<String, Object> params) {
        if (criteria == null) {
            throw new IllegalArgumentException("Paremeter criteria is not allowd to be null.");
        }
        List results = null;
        Integer totalCount = 0;
        String newSql = SQLUtils.buildPageFiltersStmt(sql, criteria, filters);
        params.putAll(SQLUtils.buildFiltersMap(filters));
        if (criteria.enablePaging()) {
            results = findBySQL(newSql, criteria.getOffset(),
                    criteria.getMaxResults(), scalarCallback, params);
//            totalCount = countWithSQL(SQLUtils.buildCountStmt(newSql), params);
            totalCount = findBySQL(newSql, params).size();
            return new PageResult<>(criteria.getPage(), criteria.getRows(), totalCount, results);
        } else {
            results = findBySQL(newSql, scalarCallback, params);
            totalCount = results.size();
            return new PageResult<>(1, totalCount, totalCount, results);
        }
    }

    @Override
    public <M> PageResult<M> findPageBySQL(String sql, String countSQL, PageCriteria criteria, ScalarAliasCallback<M> scalarCallback, Object... params) {
        if (criteria == null) {
            throw new IllegalArgumentException("Paremeter criteria is not allowd to be null.");
        }
        List results = null;
        Integer totalCount = 0;
        if (criteria.enablePaging()) {
            results = findBySQL(SQLUtils.buildSortStmt(sql, criteria), criteria.getOffset(),
                    criteria.getMaxResults(), scalarCallback, params);
            if (StringUtils.isBlank(countSQL)) {
                countSQL = SQLUtils.buildEnclosedCountStmt(sql);
            }
            totalCount = countWithSQL(countSQL, params);
            return new PageResult<>(criteria.getPage(), criteria.getRows(), totalCount, results);
        } else {
            results = findBySQL(SQLUtils.buildSortStmt(sql, criteria), scalarCallback, params);
            totalCount = results.size();
            return new PageResult<>(1, totalCount, totalCount, results);
        }
    }

    @Override
    public <M> PageResult<M> findPageBySQL(String sql, String countSQL, PageCriteria criteria, ScalarAliasCallback<M> scalarCallback, Map<String, Object> params) {
        if (criteria == null) {
            throw new IllegalArgumentException("Paremeter criteria is not allowd to be null.");
        }
        List results = null;
        Integer totalCount = 0;
        if (criteria.enablePaging()) {
            results = findBySQL(SQLUtils.buildSortStmt(sql, criteria), criteria.getOffset(),
                    criteria.getMaxResults(), scalarCallback, params);
            if (StringUtils.isBlank(countSQL)) {
                countSQL = SQLUtils.buildEnclosedCountStmt(sql);
            }
            totalCount = countWithSQL(countSQL, params);
            return new PageResult<>(criteria.getPage(), criteria.getRows(), totalCount, results);
        } else {
            results = findBySQL(SQLUtils.buildSortStmt(sql, criteria), scalarCallback, params);
            totalCount = results.size();
            return new PageResult<>(1, totalCount, totalCount, results);
        }
    }

    @Override
    public T findUnique(String hql, Object... params) {
//        List<T> results = find(hql, params);
//        if (CollectionUtils.isNotEmpty(results)) {
//            return results.get(0);
//        }
//        return null;
        return getHibernateTemplate().execute(new HibernateCallback<T>() {
            @Override
            public T doInHibernate(Session session)
                    throws HibernateException {
                Query query = session.createQuery(hql);
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        Object paramValue = params[i];
                        if (paramValue instanceof Collection) {
                            query.setParameterList(String.valueOf(i), (Collection) paramValue);
                        } else {
                            query.setParameter(String.valueOf(i), paramValue);
                        }
                    }
                }
                return (T) query.uniqueResult();
            }
        });
    }

    @Override
    public T findUnique(String hql, Map<String, Object> params) {
//        List<T> results = find(hql, params);
//        if (CollectionUtils.isNotEmpty(results)) {
//            return results.get(0);
//        }
//        return null;

        return getHibernateTemplate().execute(new HibernateCallback<T>() {
            @Override
            public T doInHibernate(Session session)
                    throws HibernateException {
                Query query = session.createQuery(hql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                return (T) query.uniqueResult();
            }
        });
    }

    @Override
    public <M> M findUniqueBySQL(String sql, Object... params) {
        return getHibernateTemplate().execute(new HibernateCallback<M>() {
            @Override
            public M doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                for (int i = 0; i < params.length; i++) {
                    Object paramValue = params[i];
                    if (paramValue instanceof Collection) {
                        query.setParameterList(String.valueOf(i), (Collection) paramValue);
                    } else {
                        query.setParameter(String.valueOf(i), paramValue);
                    }
                }
                return (M) query.uniqueResult();
            }
        });
    }

    @Override
    public <M> M findUniqueBySQL(String sql, Map<String, Object> params) {
        return getHibernateTemplate().execute(new HibernateCallback<M>() {
            @Override
            public M doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                return (M) query.uniqueResult();
            }
        });
    }

    @Override
    public <M> M findUniqueBySQL(String sql, ScalarAliasCallback<M> scalarCallback, Object... params) {
//        List<M> results = findBySQL(sql, scalarCallback, params);
//        if (CollectionUtils.isNotEmpty(results)) {
//            return results.get(0);
//        }
//        return null;
        return getHibernateTemplate().execute(new HibernateCallback<M>() {
            @Override
            public M doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                for (int i = 0; i < params.length; i++) {
                    Object paramValue = params[i];
                    if (paramValue instanceof Collection) {
                        query.setParameterList(String.valueOf(i), (Collection) paramValue);
                    } else {
                        query.setParameter(String.valueOf(i), paramValue);
                    }
                }
                Class<M> c = scalarCallback.doAddScalar(query);
                if (c.isPrimitive() || c.isAssignableFrom(String.class)) {
                    return (M) query.uniqueResult();
                } else {
                    return (M) query.setResultTransformer(Transformers.aliasToBean(c)).uniqueResult();
                }
            }
        });
    }

    @Override
    public <M> M findUniqueBySQL(String sql, ScalarAliasCallback<M> scalarCallback, Map<String, Object> params) {
//        List<M> results = findBySQL(sql, scalarCallback, params);
//        if (CollectionUtils.isNotEmpty(results)) {
//            return results.get(0);
//        }
//        return null;
        return getHibernateTemplate().execute(new HibernateCallback<M>() {
            @Override
            public M doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                ParameterMetadata parameterMetadata = query.getParameterMetadata();
                Set<QueryParameter<?>> queryParameters = parameterMetadata.collectAllParameters();
                for (QueryParameter<?> queryParameter : queryParameters) {
                    String parameterName = queryParameter.getName();
                    Object paramValue = params.get(parameterName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(parameterName, (Collection) paramValue);
                    } else {
                        query.setParameter(parameterName, paramValue);
                    }
                }
                Class<M> c = scalarCallback.doAddScalar(query);
                if (c.isPrimitive() || c.isAssignableFrom(String.class)) {
                    return (M) query.uniqueResult();
                } else {
                    return (M) query.setResultTransformer(Transformers.aliasToBean(c)).uniqueResult();
                }
            }
        });
    }

    @Override
    public <M> M findUniqueBySQL(final String sql, Class<M> entityCls, final Object... params) {
//        List<M> results = findBySQL(sql, entityCls, params);
//        if (CollectionUtils.isNotEmpty(results)) {
//            return results.get(0);
//        }
//        return null;
        return getHibernateTemplate().execute(new HibernateCallback<M>() {
            @Override
            public M doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                for (int i = 0; i < params.length; i++) {
                    query.setParameter(i, params[i]);
                }
                query.addEntity(entityCls);
                return (M) query.uniqueResult();
            }
        });
    }

    @Override
    public <M> M findUniqueBySQL(final String sql, Class<M> entityCls, final Map<String, Object> params) {
//        List<M> results = findBySQL(sql, entityCls, params);
//        if (CollectionUtils.isNotEmpty(results)) {
//            return results.get(0);
//        }
//        return null;
        return getHibernateTemplate().execute(new HibernateCallback<M>() {
            @Override
            public M doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                String[] namedParameters = query.getNamedParameters();
                for (String paraName : namedParameters) {
                    Object paramValue = params.get(paraName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(paraName, (Collection) paramValue);
                    } else {
                        query.setParameter(paraName, paramValue);
                    }
                }
                if (entityCls != null) {
                    query.addEntity(entityCls);
                }
                return (M) query.uniqueResult();
            }
        });
    }

    @Override
    public Integer logicDeleteById(Object id) {
        String hql = "UPDATE " + entityClass.getSimpleName() + " set deleted=true where id=:id";
        Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        return query.executeUpdate();
    }


    @Override
    public Integer batchLogicDeleteById(Object... ids) {
        String hql = "UPDATE " + entityClass.getSimpleName() + " set deleted=true where id in (:ids)";
        Query query = getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameterList("ids", ids);
        return query.executeUpdate();
    }

    private <T> List<T> findBySQL(final String sql, final ScalarAliasCallback<T> scalarCallback, final Class<T> entityCls, final Map<String, Object> params) {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
            @Override
            public List<T> doInHibernate(Session session)
                    throws HibernateException {
                NativeQuery query = session.createNativeQuery(sql);
                String[] namedParameters = query.getNamedParameters();
                for (String paraName : namedParameters) {
                    Object paramValue = params.get(paraName);
                    if (paramValue instanceof Collection) {
                        query.setParameterList(paraName, (Collection) paramValue);
                    } else {
                        query.setParameter(paraName, paramValue);
                    }
                }
                if (entityCls != null) {
                    query.addEntity(entityCls);
                } else if (scalarCallback == null) {
                    throw new IllegalArgumentException("Arguments are incorrect, entityCls OR scalarCallback must not be null.");
                } else {
                    Class<T> c = scalarCallback.doAddScalar(query);
                    if (!(c.isPrimitive() || c.isAssignableFrom(String.class))) {
                        query.setResultTransformer(Transformers.aliasToBean(c));
                    }
                }
                return query.list();
            }
        });
    }

}