package com.telusko.demo3.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.telusko.demo3.model.Notification;

@Component
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	protected EntityManager entityManager;

	@Override
	public <T> T fetchDataById(String id, String entityName) {
		T genericData = null;
		Class<?> c = getEntityClass(entityManager, entityName);
		if(c == null) throw new EntityNotFoundException("Entity NOT available");
		genericData = (T) entityManager.find(c, id);
		if(genericData == null) throw new EntityNotFoundException("Record for Entity is not available");
		return genericData;
	}

	/**
	 * Method to get Entity class by Entity name
	 * 
	 * @param entityManager
	 * @param entityName
	 * @return
	 */
	public static Class<?> getEntityClass(EntityManager entityManager, String entityName) {
		for (EntityType<?> entity : entityManager.getMetamodel().getEntities()) {
			if (entityName.equals(entity.getName())) {
				return entity.getJavaType();
			}
		}
		return null;
	}

	@Override
	public <T> T fetchDataMap(String referenceId, String template) {
		T genericData = null;
		String entityName= null;
		List<String> variableList =null;
		Map<String, String> dataMap = new HashMap<>();
		//Get notification row
		Notification nrow=entityManager.find(Notification.class, template);
		
		if(nrow == null) throw new EntityNotFoundException("No Mapping available for template");
		if(nrow != null && nrow.getEntityName()!= null) {
			entityName = nrow.getEntityName();
			variableList = getvarListfrmDesc(nrow.getNotificatioDesc());
		}
		//Fetch Entity row with reference id
		genericData = fetchDataById(referenceId, entityName);
		
		// for each key in varlist, get value from t
		System.out.println(genericData);
		System.out.println(variableList);
		for(String s: variableList) {
			String val = getValFromField(s,genericData);
			dataMap.put(s, val);
		}
		
		System.out.println(variableList);
		return (T)dataMap;
	}

	/**
	 * @param <T>
	 * @param s
	 * @param t
	 * @return String
	 * Extract values from Generic type
	 */
	private <T> String getValFromField(String s, T t) {
		
		Field field = null;
		Object value = null;
		try {
			field = t.getClass().getDeclaredField(s);
		} catch (NoSuchFieldException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		field.setAccessible(true);

		try {
			
			value = field.get(t);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return value.toString();
	}

	/**
	 * @param str
	 * @return String
	 * Helper method to get all dynamic values for $ fields.
	 */
	private List<String> getvarListfrmDesc(String str) {
		List<String> dynamicVaribaleList = new ArrayList<>();
		Pattern pattern = Pattern.compile("\\$\\{(.*?)}");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
		    String dynamicVariable = matcher.group();
		    //add valid fields to list
		    if(dynamicVariable != null && dynamicVariable.length()>3) {
		    	dynamicVaribaleList.add(dynamicVariable.substring(2,dynamicVariable.length()-1));
		    }
		}
		return dynamicVaribaleList;
	}

}
