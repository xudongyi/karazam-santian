package com.klzan.core.persist;

import com.klzan.core.persist.hibernate.HiBaseDao;
import com.klzan.core.persist.mybatis.MyDaoSupport;
import com.klzan.core.util.Reflections;

import javax.inject.Inject;
import java.util.List;

public abstract class DaoSupport<T> extends HiBaseDao<T> {
    @Inject
    protected MyDaoSupport myDaoSupport;

    protected Class<T> entityClass;

    public DaoSupport() {
        this.entityClass = Reflections.getClassGenricType(getClass());
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

//    public T create(T entity) {
//        super.persist(entity);
//        return entity;
//    }
//
//    public T update(T entity) {
//        super.merge(entity);
//        return entity;
//    }
//
//    public Integer update(String hql, Object... params) {
//        return super.executeUpdate(hql, params);
//    }
//
//    public void remove(Integer id) {
//        super.logicDeleteById(id);
//    }

}
