package com.support;

import com.MVC.controller.Manager.ControlPadResponder;
import com.MVC.model.QueueUser;
import com.hibernate.dao.impl.AdminImpl;
import com.support.currentUserTimeHelp.CurrentUserTimeHelp;
import com.support.currentUserTimeHelp.CurrentUserTimeUpdateSupport;
import com.support_Singleton.RunningServer;
import com.support_Singleton.queueHelp.OnQueueChangeListener;
import com.support_Singleton.queueHelp.QueueInThisServer;
import com.support_Singleton.queueHelp.QueueListProvider;
import com.support_Singleton.queueHelp.QueueOperaEntrance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Linco_S on 2017/7/30.
 * 一些全局的状态和变量.可以直接引用
 * 本服务的对象,每个服务每种一个
 *
 * 本类是一个服务存在和运行的唯一标识,其他本服务所属的对象都由此直接或间接产生,不再涉及spring
 * 本类由spring作为多例对象管理,本类对象由RunningServer产生并储存和管理,除了queue的三个对象由spring注入
 */
public class ThisServer implements OnQueueChangeListener{
	private boolean state_ser;
	private boolean state_SP;
	private byte state_SF;
	private long serStart;
	private int loginTime;
	private String lastLogin;

	@Autowired
	RunningServer runningServer;
	@Autowired
	AdminImpl ai;
	private int aid;
	private long timeServerStart;

	WebSocketSession wss;
	HttpSession hs;
	Timer timer;
	TimerTask tt;
	private QueueUser currentUser;

	private final CurrentUserTimeHelp cutc;
	private QueueListProvider qlp;
	private QueueInThisServer qits;
	private QueueOperaEntrance qoe;
	private final CurrentUserTimeUpdateSupport cutus;

	public ThisServer() {
		cutc=new CurrentUserTimeHelp();
		cutus=new CurrentUserTimeUpdateSupport(this);
		timeServerStart=System.currentTimeMillis();
		timer=new Timer();
	}

	public boolean isState_ser() {
		return state_ser;
	}

	public void setState_ser(boolean state_ser) {
		this.state_ser = state_ser;
	}

	public byte getState_SF() {
		return state_SF;
	}

	public void setState_SF(byte state_SF) {
		this.state_SF = state_SF;
	}

	public boolean isState_SP() {
		return state_SP;
	}

	public void setState_SP(boolean state_SP) {
		this.state_SP = state_SP;
	}

	public long getSerStart() {
		return serStart;
	}

	public void setSerStart(long serStart) {
		this.serStart = serStart;
	}

	public int getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(int loginTime) {
		this.loginTime = loginTime;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public QueueUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(QueueUser currentUser) {
		this.currentUser = currentUser;
	}

	public void setAid(int aid) {
		this.aid = aid;
		//纪录登录
		// TODO: 2017/9/4
//		ai.recodeLogin(aid);
	}

	public CurrentUserTimeHelp getCutc() {
		return cutc;
	}

	public QueueListProvider getQlp() {
		return qlp;
	}

	public QueueInThisServer getQits() {
		return qits;
	}

	public QueueOperaEntrance getQoe() {
		return qoe;
	}

	public CurrentUserTimeUpdateSupport getCutus() {
		return cutus;
	}

	public void setQlp(QueueListProvider qlp) {
		this.qlp = qlp;
	}

	public void setQits(QueueInThisServer qits) {
		this.qits = qits;
	}

	public void setQoe(QueueOperaEntrance qoe) {
		this.qoe = qoe;
		qoe.addOnQueueChangeListener(this);
	}

	public void setWss(WebSocketSession wss) {
		//有新设置,去除定时
//		timer.cancel(); cancle会撤销整个对象,后面只能new新的
		if (tt!=null)
			tt.cancel();
		this.wss = wss;
		//断开连接的标志
		if (wss==null){

			tt=new TimerTask() {
				@Override
				public void run() {
					//释放服务
					doRemove();
				}
			};
			//180秒后执行
			timer.schedule(tt,180);
		}
	}

	public void setHs(HttpSession hs) {
		this.hs = hs;
	}

	//进入服务等待状态时调用,队列空时使currentUser=null
	public void onWait(){
		if (state_ser && state_SP&&currentUser!=null) {
			//开始当前用户服务等待
			state_SF = 0;
			cutc.startTimeTemp(currentUser.getTimeTemp());
			sendMsg();
		}
	}
	//进入服务进行状态时调用
	public void onServing(){
		if (state_ser && state_SP&&currentUser!=null) {
			state_SF=(byte) 1;
			cutus.updateCurrentUser(false);
			cutc.startTimeLineTiming();
		}
	}
	//完成当前服务时调用
	public void onFinish(){
		cutc.getTimeLine();		//写入日志,暂不
		currentUser=qoe.takeFirst();
		onWait();				//改变队首,不用再onWait,回调有了
	}
	//跳过此用户时调用
	public void onJump(){
		cutus.updateCurrentUser(true);
		currentUser=qoe.takeFirst();
		onWait();
	}
	//开启服务时
	public void onOpenSer(){
		if (!state_ser) {
			state_ser = true;
			onWait();        //如果个条件满足的话直接开始服务
		}
	}
	//关闭服务时
	public void onCloseSer(){
		state_ser=false;
		if (currentUser!=null)
			qoe.back(currentUser);
		//关闭Socket连接,触发Socket超时释放倒计时
		try {
			wss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Socket断开超时,直接移除所有服务信息
	public void doRemove(){
		//先按关闭服务善后
		onCloseSer();
		qoe.removeOnQueueChangeListener(this);
		hs.removeAttribute("thisServer");
		runningServer.StopServer(aid);
	}
	//总计时
	public int getAllServerTime(){
		return (int) ((System.currentTimeMillis()-timeServerStart)/1000);
	}
	@Override
	public void onQueueChange() {
		//改变了首用户,可能被其他服务线程拿走而变成空
		if (currentUser==null) {	//本来没有,才去理它的变化
			QueueUser firstUser = qoe.takeFirst();
			if (firstUser != null) {
				currentUser = firstUser;
				onWait();
			}
		}
	}
	//Socket告知获得用户
	private boolean sendMsg(){
		try {
			wss.sendMessage(new TextMessage("{code:204,tempTime:"+cutc.getTempAboveTime()+"}"));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void onException(){

	}
}

//让线程竞争用户?OK