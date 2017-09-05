package com.MVC.controller;

import com.support.ThisServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Linco on 2017/8/4.
 */
@Controller
public class RunCodeDirect {
//	从bean引入值
/*	@Autowired
	private AdminImpl ai;
	@Autowired
	private UserImpl ui;*/
	@RequestMapping("/RunCode")
	public void runCode(HttpServletResponse response) {
		System.out.println("RunCode:" + (response == null ? "null" : "OK"));
//		开始执行
		/*Admin admin ;
		admin=ai.getAdmin(234);
		admin=new Admin();
		admin.setAid(232);
		ai.add(admin);
		ai.delete(232);
		ai.getAll();

		User user=new User();
		user.setUid(232);
		ui.add(user);
		ui.getUser(232);*/
		ThisServer ts=new ThisServer();
		System.out.println(ts.isState_ser());
	}
}
