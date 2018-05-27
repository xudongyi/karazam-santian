package com.klzan.core.persist;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class DefaultDataSourceTransactonManager extends DataSourceTransactionManager implements InitializingBean {

	private static final long serialVersionUID = 6972408766640663006L;

	@Override
	public void afterPropertiesSet() {
	}
	
	public void initialize() {
		super.afterPropertiesSet();
	}
}
