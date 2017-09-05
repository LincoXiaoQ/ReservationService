package com.MVC.model;



/**
 * Created by Linco_S on 2017/8/2.
 */
public class Admin {
	private int aid;
	private String name;
	private String email;
	private long lastLogin;
//	timeLogin是登录次数而非时间
	private int timeLogin;
	private String password;
	private boolean superAdmin;

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(long lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getTimeLogin() {
		return timeLogin;
	}

	public void setTimeLogin(int timeLogin) {
		this.timeLogin = timeLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}
}
