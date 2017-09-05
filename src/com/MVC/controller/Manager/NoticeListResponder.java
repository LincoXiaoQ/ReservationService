package com.MVC.controller.Manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibernate.dao.impl.AdminImpl;
import com.hibernate.dao.impl.NoticeImpl;
import com.sun.istack.internal.Nullable;
import com.support_Singleton.LastedNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Linco_S on 2017/8/2.
 * 提供公告列表也得通讯实现,也包括AddNotice的实现
 */
@Controller
public class NoticeListResponder {
	ObjectMapper json=new ObjectMapper();
	@Autowired
	NoticeImpl ni;
	@Autowired
	LastedNotice ln;
	@ResponseBody
	@RequestMapping("/ajax/admin/noticeList")
	public String noticeList(@RequestBody String code){
		System.out.println("/ajax/admin/noticeList: "+code);
		String response="403";
		StringBuilder sb=new StringBuilder(32);
		sb.append("{\"code\":\"103\",\"list\":");
		try {
			response=sb+json.writeValueAsString(ni.getAll())+"}";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("response:"+response);
		return response;
	}

	//按序号位置删除,不稳定
	@ResponseBody
	@RequestMapping("/ajax/admin/removeNotice")
	public String removeNotice(@RequestParam("delete") int index){
		int nid=ni.getAll().get(index).getNid();
		ni.delete(nid);
		return "OK";
	}
	@RequestMapping("/ajax/admin/addNotice")
//	public String addNotice(@RequestBody String json){
	public String addNotice(@RequestParam("title") String title,@RequestParam("content")String content){
		String json="{\"title\":\""+title+"\",\"content\":\""+content+"\"}";
		ni.add(json);
		ln.updateLasted(json);
		System.out.println("NoticeListResponder:"+json);
		return "redirect:/admin/notice.html";
	}
	@RequestMapping("/admin/add-notice")
	public String editToAddNotice(@RequestParam("ni")String index){
		return "th/admin/add-notice";
	}
}
