package com.MVC.controller.Manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibernate.dao.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Linco_S on 2017/8/2.
 * 提供用户列表也得通讯实现
 */
@Controller
@ResponseBody
public class UserListResponder {
	ObjectMapper json=new ObjectMapper();
	@Autowired
	UserImpl ui;
	@RequestMapping("/ajax/admin/userList")
	public String adminList(){
		String response="403";
		try {
			response=json.writeValueAsString(ui.getAll());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return response;
	}

	//按序号位置删除,不稳定
	@RequestMapping("/ajax/admin/removeUser")
	public String removeUser(@RequestParam("sd") int index){
		int uid=ui.getAll().get(index).getUid();
		ui.delete(uid);
		return "OK!";
	}
	@RequestMapping("/ajax/admin/addUpdateUser")
	public String addUpdateUser(@RequestBody String json){
		ui.addOrUpdate(json);
		return "OK";
	}
}

