package com.klzan.core.persist.mybatis;

import com.klzan.core.page.PageCriteria;
import com.klzan.core.page.PageResult;

import java.util.List;

/**
 * Created by suhao on 2015/10/29.
 */
public interface MyDaoSupport<T> {
    /**
     * 通过SQL Mapping工具从映射文件中根据给定的查询名称ID进行查询.并返回映射后的对象结果集
     * @param id
     * @param parameter
     * @return
     */
    List<T> findList(String id, final Object parameter);

    /**
     * 通过SQL Mapping工具从映射文件中根据给定的查询名称ID进行查询.并返回映射后的分页结果对象
     * @param id
     * @param parameter
     * @param criteria
     * @return
     */
    PageResult<T> findPage(String id, final Object parameter, PageCriteria criteria);

    /**
     * 通过SQL Mapping工具从映射文件中根据给定的查询名称ID进行查询.并返回映射后的对象
     * @param id
     * @param parameter
     * @return
     */
    T findUnique(String id, final Object parameter);

    /**
     * 通过SQL Mapping执行保存操作
     * @param id
     * @param parameter
     * @return
     */
    Integer save(String id, Object parameter);

    /**
     * 通过SQL Mapping执行更新操作. 通过SQL Mapping工具从映射文件中执行给定ID对应的UPDATE SQL语句.
     * @param id
     * @param parameter
     * @return
     */
    Integer update(String id, final Object parameter);

    /**
     * 通过SQL Mapping工具从映射文件中执行给定ID对应的DELETE SQL语句.
     * @param id
     * @param parameter
     * @return
     */
    Integer delete(String id, final Object parameter);
}
