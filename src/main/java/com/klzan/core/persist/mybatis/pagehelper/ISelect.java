package com.klzan.core.persist.mybatis.pagehelper;

/**
 * 分页查询接口
 */
public interface ISelect {

    /**
     * 在接口中调用自己的查询方法，不要在该方法内写过多代码，只要一行查询方法最好
     */
    void doSelect();

}
