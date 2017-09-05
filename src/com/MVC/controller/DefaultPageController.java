package com.MVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Linco on 2017/7/11.
 * 映射静态资源的访问,优先级低于其他controller
 */
@Controller
public class DefaultPageController {
	//	mvc的路径表示: "/"只会适配根路径 *匹配多一级,**全
	//多个用数组形式
	@RequestMapping({"/**/*.css","/**/*.js","/**/*.png","/**/*.jpg","/**/*.html","/admin/fonts/**"})
	public String getHtml(HttpServletRequest request) {
		String uri = request.getRequestURI();
		System.out.println("Mapped: " + uri);
		return "/static" + request.getRequestURI();
	}

	//	对根路径访问没有被其他条件处理时, spring 会以/index适配
	@RequestMapping("/")
	public String index() {
		return "/static/index.html";
	}
}
