package com.hibernate.outerInterface;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Linco on 2017/9/3.
 */
public class Adapter {
	@Autowired
	private ObjectMapper objectMapper;
	public com.MVC.model.Admin getAdmin(com.hibernate.entity.Admin admin){
		try {
			return objectMapper.readValue(objectMapper.writeValueAsBytes(admin),com.MVC.model.Admin.class);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public com.hibernate.entity.Admin getAdmin(com.MVC.model.Admin admin){
		try {
			return objectMapper.readValue(objectMapper.writeValueAsBytes(admin),com.hibernate.entity.Admin.class);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public com.MVC.model.User getUser(com.hibernate.entity.User user){
		try {
			return objectMapper.readValue(objectMapper.writeValueAsBytes(user),com.MVC.model.User.class);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public com.hibernate.entity.User getUser(com.MVC.model.User user){
		try {
			return objectMapper.readValue(objectMapper.writeValueAsBytes(user),com.hibernate.entity.User.class);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
