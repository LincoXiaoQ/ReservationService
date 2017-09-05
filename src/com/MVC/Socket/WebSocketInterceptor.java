package com.MVC.Socket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Linco on 2017/8/10.
 *	握手拦截+对象转储
 */
public class WebSocketInterceptor extends HttpSessionHandshakeInterceptor {
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//		setCopyHttpSessionId(true);
//		setCopyAllAttributes(true);
		if (request instanceof ServletServerHttpRequest) {
			HttpSession httpSession = ((ServletServerHttpRequest) request).getServletRequest().getSession();

			if (httpSession != null && httpSession.getAttribute("admin") != null) {
				attributes.put("admin",httpSession.getAttribute("admin"));
				return super.beforeHandshake(request, response, wsHandler, attributes);
			}
			if (httpSession != null && httpSession.getAttribute("queueUser") != null) {
				attributes.put("queueUser",httpSession.getAttribute("queueUser"));
				return super.beforeHandshake(request, response, wsHandler, attributes);
			}
		}
		return false;
	}
}

//找了好久,网上的方法安全性或效率不好,看过程,拦截器中有更多的外部参数传入,考虑用
//看到ServerHttpRequest request的request对象,但是这个对象只有4个无关方法,我看到它是一个接口,那么,它的实现对象会不会有更多信息呢
//在tomcat环境下运行,调试查看对象类型,在TomCat环境下传入的是一个SSHR对象,跟踪查看源码,看到它实现了一个getHttpServletRequest方法