package com.klzan.core.persist.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;

/**
 * Created by suhao on 2016/9/20.
 */
public class DefaultHibernateTemplate extends HibernateTemplate {

    public DefaultHibernateTemplate(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
