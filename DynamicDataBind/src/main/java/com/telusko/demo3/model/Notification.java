package com.telusko.demo3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Notification {

	@Id
	@Column(name = "template", columnDefinition = "VARCHAR(64)")
	private String template;
	private String application;
	private String notificationDesc;
	private String entityName;

	public Notification(String template, String application, String notificationDesc, String entityName) {
		this.template = template;
		this.application = application;
		this.notificationDesc = notificationDesc;
		this.entityName = entityName;
	}

	public Notification() {

	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getNotificatioDesc() {
		return notificationDesc;
	}

	public void setNotificatioDesc(String notificatioDesc) {
		this.notificationDesc = notificatioDesc;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Override
	public String toString() {
		return "Notification [template=" + template + ", application=" + application + ", notificationDesc="
				+ notificationDesc + ", entityName=" + entityName + "]";
	}

	
}
