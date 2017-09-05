package com.MVC.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Linco on 2017/7/11.
 */
public class User {
	private int uid;
	private String name;
	private int gid;
	private int tTempAbove;
	private String password;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getTTempAbove() {
		return tTempAbove;
	}

	public void setTTempAbove(int tAbove) {
		this.tTempAbove = tAbove;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
