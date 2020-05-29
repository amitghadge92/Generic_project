package com.telusko.demo3.controller;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.demo3.model.Company;
import com.telusko.demo3.model.Notification;
import com.telusko.demo3.repo.CompanyRepo;
import com.telusko.demo3.service.NotificationService;

@RestController
@RequestMapping("/api")
public class DataController {

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	CompanyRepo companyrepo;
	
	@Autowired
	EntityManager em;

	@GetMapping("/getnotification")
	public String getNotificationData() {
		return "Hello";
	}

	@GetMapping("/getnotification/{id}")
	public Notification getNotification(@PathVariable("id") String id) {

		Notification nrow = em.find(Notification.class, id);
		return nrow;

	}

	@GetMapping("/getnotification/{id}/data/{entity}")
	public <T, K> T getData(@PathVariable("id") String id, @PathVariable("entity") String entity) {
		T l = null;
		l = notificationService.fetchDataById(id, entity);
		return l;
	}
	
	@GetMapping("/getnotification/{refId}/map/{templateId}")
	public <T> T getDataBindResult(@PathVariable("refId") String id, @PathVariable("templateId") String template) {
		T l = null;
		l = notificationService.fetchDataMap(id, template);
		return l;
	}
}
