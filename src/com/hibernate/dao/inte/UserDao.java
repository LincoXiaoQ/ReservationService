package com.hibernate.dao.inte;

import com.hibernate.entity.User;

import java.util.List;

/**
 * Created by Linco_S on 2017/8/2.
 */
public interface UserDao {
	void addOrUpdate(User user);

	void addOrUpdate(String json);

	User getUser(int uid);

	User getUser(String name, String password);

	void delete(int uid);

	List<User> getAll();
}
