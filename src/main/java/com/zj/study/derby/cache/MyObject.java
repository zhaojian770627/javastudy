package com.zj.study.derby.cache;

import org.apache.derby.iapi.error.StandardException;
import org.apache.derby.iapi.services.cache.Cacheable;

public class MyObject implements Cacheable {

	@Override
	public Cacheable setIdentity(Object key) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cacheable createIdentity(Object key, Object createParameter) throws StandardException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearIdentity() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getIdentity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clean(boolean forRemove) throws StandardException {
		// TODO Auto-generated method stub

	}

}
