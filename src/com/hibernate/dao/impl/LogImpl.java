package com.hibernate.dao.impl;

import com.hibernate.dao.inte.LogDao;
import com.hibernate.entity.Log;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by Linco_S on 2017/8/4.
 */
public class LogImpl extends HibernateDaoSupport implements LogDao{
	HibernateTemplate ht;

	@Override
	protected void initDao() throws Exception {
//		super.initDao();	父类方法是空的,无所谓
		ht=getHibernateTemplate();
		ht.setCheckWriteOperations(false);
	}

	@Override
	public void add(Log log) {
		ht.save(log);
		ht.flush();
	}

	@Override
	public void delete(int nid) {
		ht.deleteAll(getAll());
	}

	@Override
	public List<Log> getAll() {
		return null;
	}
}
