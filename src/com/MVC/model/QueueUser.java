package com.MVC.model;

/**
 * Created by Linco_S on 2017/7/22.
 */
public class QueueUser {
	private int uid;
	private String userName;
	private int timeTemp;

	public QueueUser(int uid, String userName,int timeTemp) {
		this.uid = uid;
		this.userName = userName;
		this.timeTemp = timeTemp;
	}

	public int getUid() {
		return uid;
	}

	public String getUserName() {
		return userName;
	}

	public int getTimeTemp() {
		return timeTemp;
	}
}
