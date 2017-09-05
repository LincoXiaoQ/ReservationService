package com.hibernate.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibernate.dao.inte.UserDao;
import com.hibernate.entity.User;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.io.IOException;
import java.util.List;

/**
 * Created by Linco_S on 2017/8/4.
 */
public class UserImpl extends HibernateDaoSupport implements UserDao {
	HibernateTemplate ht;

	@Override
	protected void initDao() throws Exception {
//		super.initDao();	父类方法是空的,无所谓
		ht=getHibernateTemplate();
		ht.setCheckWriteOperations(false);
	}

	@Override
	public void addOrUpdate(User user) {
		if (getUser(user.getUid())!=null)
			update(user);
		else
			add(user);
	}

	@Override
	public void addOrUpdate(String json) {
		ObjectMapper objectMapper=new ObjectMapper();
		try {
			User user=objectMapper.readValue(json,User.class);
			addOrUpdate(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User getUser(int uid) {
		List<User>users= (List<User>) ht.find("from User where uid =" + uid);
		if (!users.isEmpty())
			return users.get(0);
		return null;
	}


	@Override
	public User getUser(String name, String password) {
		List<User> items = (List<User>) getHibernateTemplate().find("from User where name = '" + name + "' and password = '" + password + "'");
		if (!items.isEmpty())
			return items.get(0);
		return null;
	}

	@Override
	public void delete(int uid) {
		User user=getUser(uid);
		if (user!=null) {
			ht.delete(user);
			ht.flush();
		}
	}

	@Override
	public List<User> getAll() {
		return null;
	}

	private void add(User user){
		ht.save(user);
		ht.flush();
	}
	//更新可手动更改部分
	private void update(User user){
		User u=getUser(user.getUid());
		user.setTTempAbove(u.getTTempAbove());
		user.setGid(u.getGid());
		ht.update(user);
		ht.flush();
	}
	public void updateTime(int uid,int time){
		//hql
		getHibernateTemplate().getSessionFactory().openSession().createQuery
				("update User set tTempAbove="+time+" where uid = "+uid).executeUpdate();

	}
}


//管理员对用户的update和用户自己的update在内容上有什么差别,如何解决?
//暂时不提供管理员界面端设置用户gid的功能