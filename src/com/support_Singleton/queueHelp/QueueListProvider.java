package com.support_Singleton.queueHelp;

import com.MVC.model.QueueUser;
import com.support.ThisServer;

import java.util.List;

/**
 * Created by Linco_S on 2017/8/2.
 * 包装modal的信息提供给两个端,包括list和list的信息
 */
public class QueueListProvider implements OnQueueChangeListener{
	private QueueInThisServer queueInThisServer;
	private List<QueueUser> uq;

	private String jsonQueue;

	public QueueListProvider(QueueInThisServer qits) {
		queueInThisServer = qits;
	}
	//队列改变是调用同步方法
	private void syncJson(){
		StringBuilder sb=new StringBuilder();
		sb.append("\"queue\":[");
		for(QueueUser u:uq){
			sb.append("\""+u.getUserName()+"\",");
		}
		if(sb.charAt(sb.length()-1)==',')
			sb.deleteCharAt(sb.length()-1);
		sb.append("]");
		System.out.println(jsonQueue=sb.toString());
	}
	public String getQueueJson(){
		return jsonQueue;
	}

	@Override
	public void onQueueChange() {
		syncJson();
	}
}
