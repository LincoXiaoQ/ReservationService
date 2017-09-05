package com.MVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Linco on 2017/7/12.
 */
@Controller
public class TestTh {
	@RequestMapping("/thymeleaf")
	public String login() {
		return "/th/admin/index";
	}
}
