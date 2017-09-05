package com.hibernate.dao.inte;

import com.hibernate.entity.Log;

import java.util.List;

/**
 * Created by Linco_S on 2017/8/2.
 */
public interface LogDao {
	void add(Log log);

	void delete(int lid);

	List<Log> getAll();
}
