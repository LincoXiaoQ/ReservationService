package com.MVC.controller.Manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hibernate.dao.impl.NoticeImpl;
import com.support_Singleton.LastedNotice;
import com.support_Singleton.queueHelp.QueueListProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Linco_S on 2017/8/2.
 * 提供队列列表的通讯实现包括分页实现
 */
@Controller
public class QueueListResponder {
	ObjectMapper json=new ObjectMapper();
	@Autowired
	QueueListProvider qlp;
	@ResponseBody
	@RequestMapping("/ajax/admin/queueList")
	public String noticeList(@RequestBody String code){
		System.out.println("/ajax/admin/queueList: "+code);
		String response="403";
		StringBuilder sb=new StringBuilder(32);
		sb.append("{\"code\":\"103\",\"list\":");
		try {
			sb.append(json.writeValueAsString(qlp.getQueueJson()));
			sb.append(",\"pageNum\":1").append("}");
			response=sb.toString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("response:"+response);
		return response;
	}

}
