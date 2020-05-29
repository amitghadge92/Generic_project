package com.telusko.demo3.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaDao implements Dao{


	protected Class<?> entityClass;

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Override
	public <E, K> E findById(K id) {
	
		return (E) entityManager.find(entityClass, id);
	}

}
