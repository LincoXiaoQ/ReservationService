package com.MVC.Socket;

import com.MVC.model.Notice;
import com.MVC.model.QueueUser;
import com.MVC.model.WaitQueue;
import com.support.ThisServer;
import com.support_Singleton.LastedNotice;
import com.support_Singleton.RunningServer;
import com.support_Singleton.queueHelp.OnQueueChangeListener;
import com.support_Singleton.queueHelp.QueueListProvider;
import com.support_Singleton.queueHelp.QueueOperaEntrance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Linco on 2017/8/8.
 */
public class UserSocketServer implements WebSocketHandler,OnQueueChangeListener {
	Set<WebSocketSession>socketSessions;
	@Autowired
	QueueOperaEntrance qoe;
	@Autowired
	QueueListProvider qlp;
	@Autowired
	RunningServer rs;
	@Autowired
	LastedNotice ln;
	@Autowired
	WaitQueue wq;

	public UserSocketServer() {
		socketSessions=new HashSet<>(32);
	}
//	自动调用初始化??
	@InitBinder
	public void init(){
		qoe.addOnQueueChangeListener(this);
		ln.setUss(this);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		socketSessions.add(webSocketSession);
		Set<WebSocketSession>temp=new HashSet<>();
		temp.add(webSocketSession);
		pushNotice(temp,ln.getNotice());
		pushQueueState(webSocketSession);
		System.out.println(webSocketSession.getRemoteAddress()+" 已连接");
	}  	

	@Override
	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
		QueueUser queueUser=(QueueUser)webSocketSession.getAttributes().get("queueUser");
		String msg= webSocketMessage.getPayload().toString();
		switch (msg){
			case "ToQueue-true":
				//如果对象不一致,使用uid工作
				if (queueUser.getTimeTemp()<0)
					wq.add(queueUser);
				else
					qoe.add(queueUser);
				break;
			case "ToQueue-false":
				wq.remove(queueUser.getUid());
				qoe.remove(queueUser);
				break;
			//后续会由队列变化的回调完成
			//如果用户端前端判断是自己,会请求当前服务状态
			case "getMe":
				Collection<ThisServer> servers=rs.getServerMap();
				ThisServer thisServer = null;
				for (ThisServer ts:servers){
					if (ts.getCurrentUser()==queueUser) {
						thisServer = ts;
						break;
					}
				}
				if (thisServer==null)
					return;
				StringBuilder json=new StringBuilder(64);
				json.append("{code:202")
					.append(",state_ser:true")
					.append(",state_SP:").append(thisServer.isState_SP())
					.append(",tempTime:")
					.append(thisServer.getCutc().getTempAboveTime())
					.append("}");
				webSocketSession.sendMessage(new TextMessage(json));
				break;
			default:
				System.out.println("USS:unknown"+msg);
		}
	}

	@Override
	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

	}

	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
		if (socketSessions.contains(webSocketSession))
			socketSessions.remove(webSocketSession);
		System.out.println(webSocketSession.getRemoteAddress()+" 已断开");
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
	public void pushNoticeAll(Notice notice){
		pushNotice(socketSessions,notice);
	}
	private void pushNotice(Set<WebSocketSession> tempSocketSessions, Notice notice){
		StringBuilder json=new StringBuilder(128);
		json.append("{\"code\":203");
		json.append(",\"text\":\"");
		json.append(notice.getTitle());
		json.append(notice.getContent()).append("\"}");
		TextMessage msg=new TextMessage(json);
		//向组内返回
		for (WebSocketSession session: tempSocketSessions){
			try {
				session.sendMessage(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//推送状态信息
	public void pushQueueState(WebSocketSession session) {
		StringBuilder json=new StringBuilder(128);
		json.append("{\"code\":201");
		json.append(",\"state_ser\":true");
		json.append(",\"state_SP\":false");
		Iterator<ThisServer> iterator=rs.getServerMap().iterator();

		//Iterator引发异常,因为当前进行为空
		json.append(",\"isWhoId\":[").append(iterator.hasNext()?iterator.next().getCurrentUser().getUid():"");
		while (iterator.hasNext()){
			json.append(",");
			json.append(iterator.next().getCurrentUser().getUid());
		}
		json.append("]");
		if (qlp.getQueueJson()!=null)
			json.append(",").append(qlp.getQueueJson());
		json.append("}");
		TextMessage msg=new TextMessage(json);
		try {
			session.sendMessage(msg);
			System.out.println("pushQueueState:"+msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onQueueChange() {
		StringBuilder json=new StringBuilder(128);
		json.append("{\"code\":201");
		json.append(",\"state_ser\":true");
		json.append(",\"state_SP\":false");
		Iterator<ThisServer> iterator=rs.getServerMap().iterator();
		json.append(",\"isWhoId\":[").append(iterator.next().getCurrentUser().getUid());
			while (iterator.hasNext()){
				json.append(",");
				json.append(iterator.next().getCurrentUser().getUid());
			}
		json.append("]");
		json.append(",").append(qlp.getQueueJson()).append("}");
		TextMessage msg=new TextMessage(json);
		//向组内返回
		for (WebSocketSession session:socketSessions){
			try {
				session.sendMessage(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
