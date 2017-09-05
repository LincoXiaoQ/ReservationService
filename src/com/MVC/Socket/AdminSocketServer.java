package com.MVC.Socket;

import com.MVC.model.Admin;
import com.MVC.model.QueueUser;
import com.support.ThisServer;
import com.support_Singleton.RunningServer;
import com.support_Singleton.queueHelp.OnQueueChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Linco on 2017/8/8.
 * Admin登录后浏览器发起Socket连接,将所属session传给ThisServer,服务器接收到服务任务,或者出现连接问题时通知管理端界面
 */
public class AdminSocketServer implements WebSocketHandler{
	Set<WebSocketSession>socketSessions;
	@Autowired
	RunningServer runningServer;
	public AdminSocketServer() {
		socketSessions=new HashSet<>(4);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		socketSessions.add(webSocketSession);
		Admin admin=(Admin) webSocketSession.getAttributes().get("admin");
		ThisServer thisServer=runningServer.getServer(admin.getAid());
		thisServer.setWss(webSocketSession);
	}

	@Override
	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
		Admin admin=(Admin) webSocketSession.getAttributes().get("admin");
		if (admin==null)
			webSocketSession.close(CloseStatus.SESSION_NOT_RELIABLE);
		// TODO: 2017/8/22  处理Message onMessage(queueUser,webSocketMessage.toString());
	}

	@Override
	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

	}

	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
		if (socketSessions.contains(webSocketSession))
			socketSessions.remove(webSocketSession);
		Admin admin=(Admin) webSocketSession.getAttributes().get("admin");
		ThisServer thisServer=runningServer.getServer(admin.getAid());
		thisServer.setWss(null);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/*
		//获取cookite中的sid
		private String getSid(String str){
			System.out.println(str);
			String reg="[&\\?]{0,1}sid=([^&]+)";
			try{
				Matcher mat= Pattern.compile(reg).matcher(str);
				if(mat.find())	//从当前起点执行匹配动作,多次调用找到多个匹配,要先调用才能获取组
					return mat.group(1);
			}catch (IllegalStateException ise){
				System.out.println("error: ise");
			}
			return null;
		}
	*/
}
