package com.MVC.controller.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Linco on 2017/8/8.
 * 根据当前用户包装用户首页信息
 */
@Controller
public class PageIndex {
	@RequestMapping("QueueIndex")
	public String queueIndex(){
		return "th/user/index";
	}
}
