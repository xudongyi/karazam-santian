package com.klzan.core.persist.mybatis;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;
import com.klzan.core.persist.mybatis.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by suhao on 2015/10/29.
 */
@Repository
public class MyDaoSupportImpl<T> implements MyDaoSupport<T> {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Inject
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 取得当前Session.
     *
     * @return Session
     */
    public SqlSession getSqlSession() {
        return this.getDefaultSqlSession();
    }

    private SqlSession getDefaultSqlSession() {
        return this.sqlSessionFactory.openSession();
    }

    @Override
    public List<T> findList(String id, Object parameter) {
        return getSqlSession().selectList(id, parameter);
    }

    @Override
    public PageResult<T> findPage(String id, Object parameter, PageCriteria criteria) {
        Assert.isTrue(criteria.getMaxResults() != 0, "传入的结果返回数'maxRows'参数不允许为0.");
        Assert.hasText(id, "传入的SQL配置ID不能为空.");
        int tmpOffset = criteria.getOffset();
        int tmpMaxRows = criteria.getMaxResults();
        PageRowBounds rowBounds = new PageRowBounds(tmpOffset, tmpMaxRows);
        List<T> resultList = getSqlSession().selectList(id, parameter, rowBounds);
        return new PageResult<>(resultList, rowBounds.getTotal().intValue());
    }

    @Override
    public T findUnique(String id, Object parameter) {
        return getSqlSession().selectOne(id, parameter);
    }

    @Override
    public Integer save(String id, Object parameter) {
        return getSqlSession().insert(id, parameter);
    }

    @Override
    public Integer update(String id, Object parameter) {
        return getSqlSession().update(id, parameter);
    }

    @Override
    public Integer delete(String id, Object parameter) {
        return getSqlSession().delete(id, parameter);
    }

}
