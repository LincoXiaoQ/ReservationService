package com.MVC.controller;

import com.MVC.model.Admin;
import com.MVC.model.User;
import com.hibernate.dao.impl.AdminImpl;
import com.hibernate.dao.impl.UserImpl;
import com.hibernate.outerInterface.Adapter;
import com.support_Singleton.Uid_UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Linco on 2017/7/12.
 */
@Controller
public class DoLogin {
	@Autowired
	private UserImpl ui;
	@Autowired
	private AdminImpl ai;
	@Autowired
	private Uid_UserMapper uum;
	@Autowired
	private Adapter adapter;
	@RequestMapping("/doLogin")
	public String login(HttpServletRequest request, ModelMap modelMap, @RequestParam("name") String name, @RequestParam("password") String password) {
		Object account=null;
		// TODO: 2017/8/26 加Cookie
		if ((account=adapter.getUser(ui.getUser(name, password)))!=null) {
			request.getSession().setAttribute("queueUser", uum.getQueueUser(account));    //反正attribute本来提取也是object
			thymeInit(modelMap,(User)account);
			return "/th/user/index";
		}

		if ((account=adapter.getAdmin(ai.getAdmin(name, password)))!=null) {
			request.getSession().setAttribute("admin", account);
			thymeInit(modelMap,(Admin)account);
			return "/th/admin/index";
		}
		//加载错误信息,返回登录页
		/*try {
			response.sendError(403);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		//不再请求任何页面
		return "/login.html";
	}

	//th页面的动态设置
	private void thymeInit(ModelMap modelMap,User user){

	}
	private void thymeInit(ModelMap modelMap,Admin admin){

	}
}



/*hibernate.user-->Object-->MVC.User*/