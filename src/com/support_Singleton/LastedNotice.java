package com.support_Singleton;

import com.MVC.Socket.UserSocketServer;
import com.MVC.model.Notice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibernate.dao.impl.NoticeImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by Linco on 2017/8/25.
 * 提供最新的一条通知
 */
public class LastedNotice {
	@Autowired
	NoticeImpl ni;
	UserSocketServer uss;
	Notice notice;

	public Notice getNotice() {
		if (notice==null){
			notice=(Notice) (Object)ni.getNotice();
		}
		return notice;
	}

	public void updateLasted(Notice notice){
		this.notice=notice;
		uss.pushNoticeAll(notice);
	}
	public void updateLasted(String json){
		ObjectMapper objectMapper=new ObjectMapper();
		try {
			Notice notice=objectMapper.readValue(json, Notice.class);
			updateLasted(notice);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setUss(UserSocketServer uss) {
		this.uss = uss;
	}
}

//本来是相互依赖,解决方法就是有一个改用set方法,手动调用而不是注入
//更面向对象的方法就是把set改造成接口