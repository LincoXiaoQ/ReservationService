package com.MVC.controller.Manager;

import com.MVC.model.Admin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibernate.dao.impl.AdminImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Linco_S on 2017/8/2.
 * 提供管理员列表也得通讯实现,包括人员管理的实现
 * 本页错误代码统一402
 */
@Controller
//返回直接字符串,不再查找视图
@ResponseBody
public class AdminListResponder {
	ObjectMapper json=new ObjectMapper();
	// TODO: 2017/8/25 传来的条目不含所有对象,如果不能完成转换??
	@Autowired
	AdminImpl ai;
	@RequestMapping("/ajax/admin/adminList")
	public String adminList(){
		String response="402";
		StringBuilder sb=new StringBuilder(32);
		sb.append("{\"code\":\"102\",\"list\":");
		try {
			response=sb+json.writeValueAsString(ai.getAll())+"}";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return response;
	}

	//按序号位置删除,不稳定
	@RequestMapping("/ajax/admin/removeAdmin")
	public String removeAdmin(@RequestParam("delete") int index){
		//待优化:防越界
		int aid=ai.getAll().get(index).getAid();
		ai.delete(aid);
		return "OK";
	}
	@RequestMapping("/ajax/admin/addAdmin")
	public String addUpdateAdmin(HttpServletRequest request){
		Admin admin=new Admin();
		admin.setName((String) request.getAttribute("name"));
		admin.setPassword((String) request.getAttribute("password"));
		admin.setSuperAdmin((Boolean) request.getAttribute("superAdmin"));
		String json= "402";
		try {
			json = this.json.writeValueAsString(admin);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		ai.addOrUpdate(json);
		return "OK";
	}
}
