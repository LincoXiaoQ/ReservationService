package com.MVC.controller.User;

import com.MVC.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibernate.dao.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Linco on 2017/8/8.
 * 更新表单的提交响应
 */
@Controller
@ResponseBody
public class UpdateDataResponder {
	ObjectMapper json=new ObjectMapper();
	@Autowired
	UserImpl ui;
	@RequestMapping("/ajax/user/update")
	public String updateMyDate(HttpServletRequest request, @RequestBody String jsonStr){
		User user= (User) request.getSession().getAttribute("user");
		try {
			if (json.readValue(jsonStr,User.class).getGid()==user.getGid()) {
				ui.addOrUpdate(jsonStr);
				return "OK";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "GG";
	}
}
