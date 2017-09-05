package com.hibernate.entity;


import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Linco_S on 2017/7/24.
 */
@Entity
public class Log {
	@Id
	private int lid;
	private long time;
	private String content;
	private boolean inportant;

	public int getlid() {
		return lid;
	}

	public void setlid(int nid) {
		this.lid = lid;
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

	public boolean isInportant() {
		return inportant;
	}

	public void setInportant(boolean push) {
		this.inportant = inportant;
	}
}
