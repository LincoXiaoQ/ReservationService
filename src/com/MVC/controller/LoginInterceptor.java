package com.MVC.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Linco on 2017/8/16.
 * 拦截器也会拦截内部转发的请求
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
	//注意log用法
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
//		输出拦截到的内容
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());

		//没有配置,会使用NOPLogger,不输出
		log.info("requestUri:"+requestUri);
		log.info("contextPath:"+contextPath);
		log.info("url:"+url);

//		过滤和转发工作

		//访问管理界面或发送管理动作,但是未登录管理员
		if (url.contains("/admin/")&&request.getSession().getAttribute("admin")==null){
			log.info("Interceptor：跳转到login页面！");
//重定向错误的话会因为新地址无限拦截GG
			response.sendRedirect("/login.html");
			//不许继续
			return false;
		}
		if (url.contains("/user/")&&request.getSession().getAttribute("queueUser")==null){
			log.info("Interceptor：跳转到login页面！");
			response.sendRedirect("/login.html");
			//不许继续
			return false;
		}
		/*else{
			转移对象方便spring mvc的mapper自动载入(参数@RequestPram)  --先不用
			request.setAttribute("user",request.getSession().getAttribute("user"));
			request.setAttribute("admin",request.getSession().getAttribute("admin"));
		}*/
		return true;
	}

}
