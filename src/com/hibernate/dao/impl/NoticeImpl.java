package com.hibernate.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibernate.dao.inte.NoticeDao;
import com.hibernate.entity.Notice;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.io.IOException;
import java.util.List;

/**
 * Created by Linco_S on 2017/8/4.
 */
public class NoticeImpl extends HibernateDaoSupport implements NoticeDao{
	HibernateTemplate ht;

	@Override
	protected void initDao() throws Exception {
//		super.initDao();	父类方法是空的,无所谓
		ht=getHibernateTemplate();
		ht.setCheckWriteOperations(false);
	}

	@Override
	public void add(Notice notice) {
		ht.save(notice);
		ht.flush();
	}

	@Override
	public void add(String json) {
		ObjectMapper objectMapper=new ObjectMapper();
		try {
			Notice notice=objectMapper.readValue(json,Notice.class);
			add(notice);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Notice getNotice(int nid) {
		List<Notice>notices= (List<Notice>) ht.find("from Notice where nid =" + nid);
		if (!notices.isEmpty())
			return notices.get(0);
		return null;
	}

	@Override
	public Notice getNotice() {
		return getAll().get(0);
	}

	@Override
	public void delete(int nid) {
		Notice notice=getNotice(nid);
		if (notice!=null) {
			ht.delete(notice);
			ht.flush();
		}
	}

	@Override
	public List<Notice> getAll() {
		List<Notice>notices=(List<Notice>)ht.find("from Notice");
		return notices;
	}
}
