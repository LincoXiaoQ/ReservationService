package com.hibernate.dao.inte;

import com.hibernate.entity.Admin;

import java.util.List;

/**
 * Created by Linco_S on 2017/8/2.
 */
public interface AdminDao {
	void addOrUpdate(Admin admin);

	void addOrUpdate(String json);

	Admin getAdmin(String name,String password);

	void delete(int aid);

	List<Admin> getAll();
}
