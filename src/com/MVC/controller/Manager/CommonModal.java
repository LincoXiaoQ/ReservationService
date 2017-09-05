package com.MVC.controller.Manager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Linco_S on 2017/8/2.
 * 提供后台共同元素的页面
 */
@Controller
public class CommonModal {
	@RequestMapping("/head-common")
	public String getHeadCommon(){

		return "th/head-common";
	}
	@RequestMapping("/left-common")
	public String getLeftCommon(){

		return "th/left-common";
	}
	@RequestMapping("/modelFade-common")
	public String getModelCommon(Model model){
//		model.addAttribute()
		return "th/modelFade-common";
	}
}
