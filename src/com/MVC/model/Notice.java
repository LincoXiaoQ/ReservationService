package com.MVC.model;

/**
 * Created by Linco_S on 2017/7/24.
 */
public class Notice {
	private int nid;
	private String titile;
	private String content;

	public Notice(String titile, String content) {
		this.titile = titile;
		this.content = content;
	}

	public int getNid() {
		return nid;
	}

	public String getTitile() {
		return titile;
	}

	public String getContent() {
		return content;
	}
}
