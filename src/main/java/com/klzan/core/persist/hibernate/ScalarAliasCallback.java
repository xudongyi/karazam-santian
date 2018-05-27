package com.klzan.core.persist.hibernate;

import org.hibernate.query.NativeQuery;

public abstract class ScalarAliasCallback<T> {
	
	protected abstract Class<T> doAddScalar(NativeQuery query);
	
}
