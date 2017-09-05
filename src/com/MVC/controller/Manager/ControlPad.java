package com.MVC.controller.Manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Linco_S on 2017/8/2.
 * 提供控制面板的页面
 */
@Controller
public class ControlPad {
//	@RequestMapping("/admin/index")
	@RequestMapping("/admin/index0")
	public String index(){

		return "/th/admin/index";
	}
}
