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
		T t = null;
		Class<?> c = getEntityClass(entityManager, entityName);
		if(c == null) throw new EntityNotFoundException("Entity NOT available");
		t = (T) entityManager.find(c, id);
		if(t == null) throw new EntityNotFoundException("Record for Entity is not available");
		return t;
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
		T t = null;
		String entityName= null;
		List<String> varlist =null;
		Map<String, String> dataMap = new HashMap<>();
		//Get notification row
		Notification nrow=entityManager.find(Notification.class, template);
		
		if(nrow == null) throw new EntityNotFoundException("No Mapping available for template");
		if(nrow != null && nrow.getEntityName()!= null) {
			entityName = nrow.getEntityName();
			varlist = getvarListfrmDesc(nrow.getNotificatioDesc());
		}
		//Fetch Entity row with reference id
		t = fetchDataById(referenceId, entityName);
		
		// for each key in varlist, get value from t
		System.out.println(t);
		System.out.println(varlist);
		for(String s: varlist) {
			String val = getValFromField(s,t);
			dataMap.put(s, val);
		}
		
		System.out.println(varlist);
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
		//String str = "Approve_DEL SDM ${addressLine1} was approved and ${addressLine2} ready for use by ${pinCode}.";
		List<String> varlist = new ArrayList<>();
		Pattern pattern = Pattern.compile("\\$\\{(.*?)}");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
		    String s =matcher.group();
		    //add valid fields to list
		    if(s != null && s.length()>3) {
		    	varlist.add(s.substring(2,s.length()-1));
		    }
		}
		return varlist;
	}

}
