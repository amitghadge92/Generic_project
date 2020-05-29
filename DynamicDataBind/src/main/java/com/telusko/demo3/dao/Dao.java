package com.telusko.demo3.dao;

public interface Dao {

	public <E,K> E findById(K id);
}
