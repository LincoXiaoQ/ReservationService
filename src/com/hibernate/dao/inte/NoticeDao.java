package com.hibernate.dao.inte;

import com.hibernate.entity.Notice;

import java.util.List;

/**
 * Created by Linco_S on 2017/8/2.
 */
public interface NoticeDao {
	void add(Notice notice);

	void add(String json);

	Notice getNotice(int nid);

	Notice getNotice();

	void delete(int nid);

	List<Notice> getAll();
}
