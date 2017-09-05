package com.MVC.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Set;

/**
 * Created by Linco on 2017/8/8.
 * 注册用户端和管理端的WS服务
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketRegister extends WebMvcConfigurerAdapter implements WebSocketConfigurer{
	private HttpSessionHandshakeInterceptor hshi;
	//只调用一次
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		webSocketHandlerRegistry.addHandler(getUss(),"/Socket/user").addInterceptors(hshi);
		webSocketHandlerRegistry.addHandler(getAss(),"/Socket/admin").addInterceptors(hshi);
	}
	@Bean
	protected UserSocketServer getUss(){
		return new UserSocketServer();
	}
	@Bean
	protected AdminSocketServer getAss(){
		return new AdminSocketServer();
	}
	public void setHshi(HttpSessionHandshakeInterceptor hshi) {
		this.hshi = hshi;
	}
}
