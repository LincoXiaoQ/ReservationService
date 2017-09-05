package com.hibernate.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibernate.dao.inte.AdminDao;
import com.hibernate.entity.Admin;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * Created by Linco_S on 2017/8/4.
 */
public class AdminImpl extends HibernateDaoSupport implements AdminDao {
	@Override
	//-1就是Add标志
	public void addOrUpdate(Admin admin) {
		if (admin.getAid()!=-1||getAdmin(admin.getAid())!=null)
			update(admin);
		else
			add(admin);
	}

/*	@Override
	public void addOrUpdate(int aid, String name, long lastLogin, int timeLogin, String password, boolean superAdmin) {
		Admin admin=new Admin();
		admin.setAid(aid);
		admin.setName(name);
		admin.setLastLogin(lastLogin);
		admin.setTimeLogin(timeLogin);
		admin.setPassword(password);
		admin.setSuperAdmin(superAdmin);
	}*/

	@Override
	public void addOrUpdate(String json) {
		ObjectMapper objectMapper=new ObjectMapper();
		try {
			Admin admin=objectMapper.readValue(json,Admin.class);
			addOrUpdate(admin);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void add(Admin admin) {
		HibernateTemplate ht=getHibernateTemplate();
		if (ht.isCheckWriteOperations())
			ht.setCheckWriteOperations(false);
		ht.save(admin);
		ht.flush();
	}

	//更新手动修改的信息
	private void update(Admin admin) {
		HibernateTemplate ht=getHibernateTemplate();
		if (ht.isCheckWriteOperations())
			ht.setCheckWriteOperations(false);
		Admin toUpdate=getAdmin(admin.getAid());
		if (admin.getName()!=null&&!admin.getName().equals(""))
			toUpdate.setName(admin.getName());
		if (admin.getEmail()!=null&&!admin.getEmail().equals(""))
			toUpdate.setEmail(admin.getEmail());
		if (admin.getPassword()!=null&&!admin.getPassword().equals(""))
			toUpdate.setPassword(admin.getPassword());
		ht.update(toUpdate);
		ht.flush();
	}

	//特定项更新
	public void recodeLogin(int aid){
		//+1
		//system.current
		//可以通过HQL语句实现动态更新
		getHibernateTemplate().getSessionFactory().openSession().createQuery
		("update Admin set loginTime=loginTime+1,lastLogin="+System.currentTimeMillis()+"where aid="+aid).executeUpdate();
	}

	private Admin getAdmin(int aid) {
		List<Admin> items = (List<Admin>) getHibernateTemplate().find("from Admin where aid =" + aid);
		if (!items.isEmpty())
			return items.get(0);
		return null;
	}

	@Override
	public Admin getAdmin(String name, String password) {
//		这里find语句的字符串要用单引号包裹,且与sql的select不同,不能用双引号
		List<Admin> items = (List<Admin>) getHibernateTemplate().find("from Admin where name = '" + name + "' and password = '" + password+"'");
		if (!items.isEmpty())
			return items.get(0);
		return null;
	}

	@Override
	public void delete(int aid) {
		Admin toDelete = getAdmin(aid);
		if (toDelete != null) {
			HibernateTemplate ht=getHibernateTemplate();
			if (ht.isCheckWriteOperations())
				ht.setCheckWriteOperations(false);
			ht.delete(toDelete);
			ht.flush();
		}
	}

	@Override
	public List<Admin> getAll() {
		return (List<Admin>) getHibernateTemplate().find("from Admin");
	}
}
