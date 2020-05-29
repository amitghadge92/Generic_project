package com.telusko.demo3.service;

public interface NotificationService {

	public <T> T fetchDataById(String id, String entityName);

	public <T>T fetchDataMap(String referenceId, String template);
	
}
