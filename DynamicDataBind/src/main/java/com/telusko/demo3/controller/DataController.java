package com.telusko.demo3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.demo3.repo.CompanyRepo;
import com.telusko.demo3.service.NotificationService;

@RestController
@RequestMapping("/api")
public class DataController {

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	CompanyRepo companyrepo;

	
	/**
	 * Test URL to Check health of API
	 */
	@GetMapping("/getnotification")
	public String getNotificationData() {
		return "Hello";
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
