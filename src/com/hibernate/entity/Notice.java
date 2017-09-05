package com.hibernate.entity;



import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Linco_S on 2017/7/24.
 */
@Entity
public class Notice {
	@Id
	private int nid;
	private long time;
	private String title;
	private String content;

//	private boolean push;

	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
/*
	public boolean isPush() {
		return push;
	}

	public void setPush(boolean push) {
		this.push = push;
	}
*/
}
