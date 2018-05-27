package com.klzan.core.persist.hibernate;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

public class DefaultHibernateTXManager extends HibernateTransactionManager implements InitializingBean {

	private static final long serialVersionUID = 6972408766640663006L;

	@Override
	public void afterPropertiesSet() {
	}
	
	public void initialize() {
		super.afterPropertiesSet();
	}
}
